package edu.baylor.ecs.handlers.stmt;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.stmt.DoStmt;
import edu.baylor.ecs.handlers.BaseHandler;
import edu.baylor.ecs.handlers.HandlerFactory;
import edu.baylor.ecs.models.BCEToken;

import java.util.ArrayList;
import java.util.List;

public class DoStmtHandler extends BaseHandler {
    @Override
    public List<BCEToken> handle(Node node) {
        List<BCEToken> tokens = new ArrayList<>();
        tokens.add(new BCEToken("DO", node.getClass().getSimpleName()));
        DoStmt doStmt = (DoStmt)node;
        Node body = doStmt.getBody();
        BaseHandler handler = HandlerFactory.getHandler(body);
        if(handler != null) {
            tokens.addAll(handler.handle(body));
        } else {
            System.out.println(body.getClass().getSimpleName());
        }
        tokens.add(new BCEToken("WHILE", node.getClass().getSimpleName()));
        Node condition = doStmt.getCondition();
        handler = HandlerFactory.getHandler(condition);
        if(handler != null) {
            tokens.addAll(handler.handle(condition));
        } else {
            System.out.println(condition.getClass().getSimpleName());
        }

        return tokens;
    }
}