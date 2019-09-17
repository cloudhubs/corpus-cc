package edu.baylor.ecs.handlers.expr;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.BinaryExpr;
import edu.baylor.ecs.handlers.BaseHandler;
import edu.baylor.ecs.handlers.HandlerFactory;
import edu.baylor.ecs.models.BCEToken;

import java.util.ArrayList;
import java.util.List;

public class BinaryExprHandler extends BaseHandler {
    @Override
    public List<BCEToken> handle(Node node) {
        List<BCEToken> tokens = new ArrayList<>();

        BinaryExpr expr = (BinaryExpr)node;
        BaseHandler handler = HandlerFactory.getHandler(expr.getLeft());
        if(handler != null) {
            tokens.addAll(handler.handle(expr.getLeft()));
        } else {
            System.out.println(expr.getLeft().getClass().getSimpleName());
        }

        tokens.add(new BCEToken(expr.getOperator().asString(), node.getClass().getSimpleName()));

        handler = HandlerFactory.getHandler(expr.getRight());
        if(handler != null) {
            tokens.addAll(handler.handle(expr.getRight()));
        } else {
            System.out.println(expr.getRight().getClass().getSimpleName());
        }

        return tokens;
    }
}