package edu.baylor.ecs.handlers.expr;

import com.github.javaparser.ast.Node;
import edu.baylor.ecs.handlers.BaseHandler;
import edu.baylor.ecs.models.BCEToken;

import java.util.ArrayList;
import java.util.List;

public class ClassExprHandler extends BaseHandler {
    @Override
    public List<BCEToken> handle(Node node) {
        List<BCEToken> tokens = new ArrayList<>();
        tokens.add(new BCEToken("CLASS_LITERAL", node.getClass().getSimpleName()));
        return tokens;
    }
}