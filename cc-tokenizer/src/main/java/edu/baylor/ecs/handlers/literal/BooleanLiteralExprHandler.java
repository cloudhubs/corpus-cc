package edu.baylor.ecs.handlers.literal;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import edu.baylor.ecs.handlers.BaseHandler;
import edu.baylor.ecs.models.BCEToken;

import java.util.ArrayList;
import java.util.List;

public class BooleanLiteralExprHandler extends BaseHandler {
    @Override
    public List<BCEToken> handle(Node node) {
        List<BCEToken> tokens = new ArrayList<>();
        String literal = ((BooleanLiteralExpr) node).getValue() ? "TRUE" : "FALSE";
        tokens.add(new BCEToken("BL_" + literal, node.getClass().getSimpleName()));
        return tokens;
    }
}