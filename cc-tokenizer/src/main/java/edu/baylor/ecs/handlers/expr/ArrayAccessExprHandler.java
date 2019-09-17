package edu.baylor.ecs.handlers.expr;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.ArrayAccessExpr;
import edu.baylor.ecs.handlers.BaseHandler;
import edu.baylor.ecs.handlers.HandlerFactory;
import edu.baylor.ecs.models.BCEToken;

import java.util.ArrayList;
import java.util.List;

public class ArrayAccessExprHandler extends BaseHandler {
    @Override
    public List<BCEToken> handle(Node node) {
        List<BCEToken> tokens = new ArrayList<>();

        ArrayAccessExpr arrayAccessExpr = (ArrayAccessExpr) node;
        Node name = arrayAccessExpr.getName();
        Node index = arrayAccessExpr.getIndex();

        BaseHandler handler = HandlerFactory.getHandler(name);
        if(handler != null) {
            tokens.addAll(handler.handle(name));
        } else {
            System.out.println(name.getClass().getSimpleName());
        }

        tokens.add(new BCEToken("[", node.getClass().getSimpleName()));

        handler = HandlerFactory.getHandler(index);
        if(handler != null) {
            tokens.addAll(handler.handle(index));
        } else {
            System.out.println(index.getClass().getSimpleName());
        }

        tokens.add(new BCEToken("]", node.getClass().getSimpleName()));

        return tokens;
    }
}