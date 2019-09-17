package edu.baylor.ecs.handlers.expr;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import edu.baylor.ecs.handlers.BaseHandler;
import edu.baylor.ecs.handlers.HandlerFactory;
import edu.baylor.ecs.models.BCEToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MethodCallExprHandler extends BaseHandler {
    @Override
    public List<BCEToken> handle(Node node) {
        List<BCEToken> tokens = new ArrayList<>();
        MethodCallExpr expr = (MethodCallExpr)node;
        Optional<Expression> scopeOpt = expr.getScope();
        if(scopeOpt.isPresent()) {
            Node scope = scopeOpt.get();
            BaseHandler handler = HandlerFactory.getHandler(scope);
            if (handler != null) {
                tokens.addAll(handler.handle(scope));
            } else {
                System.out.println(scope.getClass().getSimpleName());
            }
        }

        BaseHandler handler = HandlerFactory.getHandler(expr.getName());
        if (handler != null) {
            tokens.addAll(handler.handle(expr.getName()));
        } else {
            System.out.println(expr.getName().getClass().getSimpleName());
        }

        for(Node n : expr.getArguments()){
            tokens.add(new BCEToken("PARAMETER", node.getClass().getSimpleName()));
        }
        return tokens;
    }
}