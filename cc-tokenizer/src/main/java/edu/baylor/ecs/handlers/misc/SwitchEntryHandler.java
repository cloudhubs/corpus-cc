package edu.baylor.ecs.handlers.misc;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.stmt.SwitchEntry;
import edu.baylor.ecs.handlers.BaseHandler;
import edu.baylor.ecs.handlers.HandlerFactory;
import edu.baylor.ecs.models.BCEToken;

import java.util.ArrayList;
import java.util.List;

public class SwitchEntryHandler extends BaseHandler {
    @Override
    public List<BCEToken> handle(Node node) {
        List<BCEToken> tokens = new ArrayList<>();
        SwitchEntry switchEntry = (SwitchEntry)node;

        NodeList<Expression> labels = switchEntry.getLabels();
        tokens.add(new BCEToken("CASE", node.getClass().getSimpleName()));
        for (Node child : labels) {
            BaseHandler handler = HandlerFactory.getHandler(child);
            if (handler != null) {
                tokens.addAll(handler.handle(child));
            } else {
                System.out.println(child.getClass().getSimpleName());
            }
        }

        NodeList<Statement> statements = switchEntry.getStatements();
        tokens.add(new BCEToken("DO", node.getClass().getSimpleName()));
        for (Node child : statements) {
            BaseHandler handler = HandlerFactory.getHandler(child);
            if (handler != null) {
                tokens.addAll(handler.handle(child));
            } else {
                System.out.println(child.getClass().getSimpleName());
            }
        }

        return tokens;
    }
}
