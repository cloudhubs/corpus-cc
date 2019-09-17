package edu.baylor.ecs.handlers.parameter;

import com.github.javaparser.ast.Node;
import edu.baylor.ecs.handlers.BaseHandler;
import edu.baylor.ecs.models.BCEToken;

import java.util.List;

public class ParameterHandler extends BaseHandler {
    @Override
    public List<BCEToken> handle(Node node) {
        return delegateToChildren(node);
    }
}