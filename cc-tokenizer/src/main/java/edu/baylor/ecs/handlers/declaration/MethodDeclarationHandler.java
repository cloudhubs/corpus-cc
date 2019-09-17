package edu.baylor.ecs.handlers.declaration;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import edu.baylor.ecs.handlers.BaseHandler;
import edu.baylor.ecs.handlers.HandlerFactory;
import edu.baylor.ecs.models.BCEToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MethodDeclarationHandler extends BaseHandler {
    @Override
    public List<BCEToken> handle(Node node) {
        List<BCEToken> tokens = new ArrayList<>();
        MethodDeclaration declaration = (MethodDeclaration)node;

        for(Node n : declaration.getAnnotations()){
            BaseHandler handler = HandlerFactory.getHandler(n);
            if(handler != null) {
                tokens.addAll(handler.handle(n));
            } else {
                System.out.println(n.getClass().getSimpleName());
            }
        }

        for(Node n : declaration.getModifiers()){
            BaseHandler handler = HandlerFactory.getHandler(n);
            if(handler != null) {
                tokens.addAll(handler.handle(n));
            } else {
                System.out.println(n.getClass().getSimpleName());
            }
        }

        tokens.add(new BCEToken("METHOD_NAME", node.getClass().getSimpleName()));

        for(Node n : declaration.getParameters()){
            BaseHandler handler = HandlerFactory.getHandler(n);
            if(handler != null) {
                tokens.addAll(handler.handle(n));
            } else {
                System.out.println(n.getClass().getSimpleName());
            }
        }

        for(Node n : declaration.getThrownExceptions()){
            BaseHandler handler = HandlerFactory.getHandler(n);
            if(handler != null) {
                tokens.addAll(handler.handle(n));
            } else {
                System.out.println(n.getClass().getSimpleName());
            }
        }

        Optional<BlockStmt> bodyOpt = declaration.getBody();
        if(bodyOpt.isPresent()) {
            BaseHandler handler = HandlerFactory.getHandler(bodyOpt.get());
            if (handler != null) {
                tokens.addAll(handler.handle(bodyOpt.get()));
            } else {
                System.out.println(bodyOpt.get().getClass().getSimpleName());
            }
        }

        return tokens;
    }
}