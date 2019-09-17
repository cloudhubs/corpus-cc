package edu.baylor.ecs.handlers.expr;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.ArrayCreationExpr;
import edu.baylor.ecs.handlers.BaseHandler;
import edu.baylor.ecs.handlers.HandlerFactory;
import edu.baylor.ecs.models.BCEToken;

import java.util.ArrayList;
import java.util.List;

public class ArrayCreationExprHandler extends BaseHandler {
    @Override
    public List<BCEToken> handle(Node node) {
        List<BCEToken> tokens = new ArrayList<>();

        tokens.add(new BCEToken("NEW", node.getClass().getSimpleName()));
        ArrayCreationExpr arrayCreationExpr = (ArrayCreationExpr)node;
        Node elementType = arrayCreationExpr.getElementType();
        BaseHandler handler = HandlerFactory.getHandler(elementType);
        if(handler != null) {
            tokens.addAll(handler.handle(elementType));
        } else {
            System.out.println(elementType.getClass().getSimpleName());
        }
        tokens.add(new BCEToken("ARRAY", node.getClass().getSimpleName()));

        for(Node child : arrayCreationExpr.getLevels()){
            handler = HandlerFactory.getHandler(child);
            if(handler != null) {
                tokens.addAll(handler.handle(child));
            } else {
                System.out.println(child.getClass().getSimpleName());
            }
        }
        return tokens;
    }
}