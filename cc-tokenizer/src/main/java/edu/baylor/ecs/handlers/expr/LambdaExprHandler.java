package edu.baylor.ecs.handlers.expr;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.LambdaExpr;
import edu.baylor.ecs.handlers.BaseHandler;
import edu.baylor.ecs.handlers.HandlerFactory;
import edu.baylor.ecs.models.BCEToken;

import java.util.ArrayList;
import java.util.List;

public class LambdaExprHandler extends BaseHandler {
    @Override
    public List<BCEToken> handle(Node node) {
        List<BCEToken> tokens = new ArrayList<>();
        LambdaExpr expr = (LambdaExpr)node;
        tokens.add(new BCEToken("LAMBDA", node.getClass().getSimpleName()));
        NodeList<Parameter> parameters = expr.getParameters();
        for(Node param : parameters){
            BaseHandler handler = HandlerFactory.getHandler(param);
            if(handler != null) {
                tokens.addAll(handler.handle(param));
            } else {
                System.out.println(param.getClass().getSimpleName());
            }
        }
        tokens.add(new BCEToken("->", node.getClass().getSimpleName()));
        BaseHandler handler = HandlerFactory.getHandler(expr.getBody());
        if(handler != null) {
            tokens.addAll(handler.handle(expr.getBody()));
        } else {
            System.out.println(expr.getBody().getClass().getSimpleName());
        }
        return tokens;
    }
}
