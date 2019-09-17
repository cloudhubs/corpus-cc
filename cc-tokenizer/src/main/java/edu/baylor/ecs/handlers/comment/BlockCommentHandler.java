package edu.baylor.ecs.handlers.comment;

import com.github.javaparser.ast.Node;
import edu.baylor.ecs.handlers.BaseHandler;
import edu.baylor.ecs.models.BCEToken;

import java.util.ArrayList;
import java.util.List;

public class BlockCommentHandler extends BaseHandler {
    @Override
    public List<BCEToken> handle(Node node) {
        return new ArrayList<>();
    }
}