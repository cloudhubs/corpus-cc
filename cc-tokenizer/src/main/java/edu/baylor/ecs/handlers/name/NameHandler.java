package edu.baylor.ecs.handlers.name;

import com.github.javaparser.ast.Node;
import edu.baylor.ecs.handlers.BaseHandler;
import edu.baylor.ecs.models.BCEToken;

import java.util.ArrayList;
import java.util.List;

public class NameHandler extends BaseHandler {
    @Override
    public List<BCEToken> handle(Node node) {
        List<BCEToken> tokens = new ArrayList<>();
        String parent = node.getParentNode().getClass().getSimpleName();
        switch (parent){
            case "MarkerAnnotationExpr": tokens.add(new BCEToken("ANNOTATION_NAME", node.getClass().getSimpleName())); break;
            case "MethodCallExpr": tokens.add(new BCEToken("VARIABLE_NAME", node.getClass().getSimpleName())); break;
            default:
                System.err.println("NameHandler - " + parent);
                System.exit(-1);
        }
        return tokens;
    }
}