package edu.baylor.ecs.service;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import edu.baylor.ecs.handlers.BaseHandler;
import edu.baylor.ecs.handlers.HandlerFactory;
import edu.baylor.ecs.models.BCEToken;
import edu.baylor.ecs.models.MethodRepresentation;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TokenService {

    private final FileService fileService;
    private final TreeService treeService;

    public TokenService(FileService fileService, TreeService treeService){
        this.fileService = fileService;
        this.treeService = treeService;
    }

    public List<MethodRepresentation> tokenizeFiles(List<String> files) throws IOException {
        List<MethodRepresentation> reps = new ArrayList<>();
        for(String file : files) {
            String javaContent = fileService.readFile(file);
            CompilationUnit compilationUnit;
            try {
                compilationUnit = StaticJavaParser.parse(javaContent);
            } catch(Exception e){
                e.printStackTrace();
                continue;
            }
            List<Node> roots = compilationUnit.getChildNodes();
            for(Node root : roots) {
                List<Node> methodDeclarations = treeService.extractNodes(root, MethodDeclaration.class);
                for(Node dec : methodDeclarations) {
                    String method_name = ((MethodDeclaration) dec).getName().getIdentifier();

                    if (method_name.startsWith("set") || method_name.startsWith("get") || method_name.startsWith("is")) {
                        continue;
                    }

                    MethodRepresentation rep = new MethodRepresentation();
                    rep.setName(method_name);
                    String raw = ((MethodDeclaration)dec).toString();
                    rep.setRaw(raw);
                    fileService.countLines(rep);
                    fileService.hash(rep);
                    BaseHandler handler = HandlerFactory.getHandler(dec);
                    if (handler != null) {
                        List<BCEToken> tokens = new ArrayList<>(handler.handle(dec));
                        Map<String, Integer> zip = TokenService.zipTokens(tokens);
                        rep.setTokens(tokens);
                        rep.setZip(zip);
                    } else {
                        System.err.println("Unknown node type");
                        System.exit(-1);
                    }
                    rep.setUniqueTokens(TokenService.countUniqueTokens(rep.getTokens()));
                    reps.add(rep);
                }
            }
        }
        return reps;
    }

    private static Map<String, Integer> zipTokens(List<BCEToken> tokens) {
        Map<String, Integer> counts = new HashMap<>();
        for(BCEToken token : tokens) {
            counts.merge(token.getTokenValue(), 1, Integer::sum);
        }
        return counts;
    }

    private static int countUniqueTokens(List<BCEToken> tokens){
        int count = 0;
        for(BCEToken token : tokens){
            if(token.getTokenValue().contains("NAME")){
                count++;
            }
        }
        return count;
    }
}
