package edu.baylor.ecs.handlers.stmt;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.ForStmt;
import edu.baylor.ecs.handlers.BaseHandler;
import edu.baylor.ecs.handlers.HandlerFactory;
import edu.baylor.ecs.models.BCEToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ForStmtHandler extends BaseHandler {
    @Override
    public List<BCEToken> handle(Node node) {
        List<BCEToken> tokens = new ArrayList<>();
        ForStmt forStmt = (ForStmt)node;
        tokens.add(new BCEToken("FOR", node));
        List<Expression> initialization = forStmt.getInitialization();
        for (Node child : initialization) {
            BaseHandler handler = HandlerFactory.getHandler(child);
            if (handler != null) {
                tokens.addAll(handler.handle(child));
            } else {
                System.out.println(child.getClass().getSimpleName());
            }
        }
        tokens.add(new BCEToken(";", node));

        Optional<Expression> compareOpt = forStmt.getCompare();
        if(compareOpt.isPresent()){
            Node compare = compareOpt.get();

            BaseHandler handler = HandlerFactory.getHandler(compare);
            if (handler != null) {
                tokens.addAll(handler.handle(compare));
            } else {
                System.out.println(compare.getClass().getSimpleName());
            }
        }
        tokens.add(new BCEToken(";", node));

        List<Expression> update = forStmt.getUpdate();
        for (Node child : update) {
            BaseHandler handler = HandlerFactory.getHandler(child);
            if (handler != null) {
                tokens.addAll(handler.handle(child));
            } else {
                System.out.println(child.getClass().getSimpleName());
            }
        }
        tokens.add(new BCEToken("DO", node));

        Node body = forStmt.getBody();
        BaseHandler handler = HandlerFactory.getHandler(body);
        if (handler != null) {
            tokens.addAll(handler.handle(body));
        } else {
            System.out.println(body.getClass().getSimpleName());
        }

        return tokens;
    }
}