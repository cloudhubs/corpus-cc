package edu.baylor.ecs.handlers.stmt;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.stmt.IfStmt;
import edu.baylor.ecs.handlers.BaseHandler;
import edu.baylor.ecs.handlers.HandlerFactory;
import edu.baylor.ecs.models.BCEToken;

import java.util.ArrayList;
import java.util.List;

public class IfStmtHandler extends BaseHandler {
    @Override
    public List<BCEToken> handle(Node node) {
        List<BCEToken> tokens = new ArrayList<>();
        IfStmt expr = (IfStmt)node;
        tokens.add(new BCEToken("IF", node.getClass().getSimpleName()));
        BaseHandler handler = HandlerFactory.getHandler(expr.getCondition());
        if(handler != null) {
            tokens.addAll(handler.handle(expr.getCondition()));
        } else {
            System.out.println(expr.getCondition().getClass().getSimpleName());
        }
        tokens.add(new BCEToken("THEN", node.getClass().getSimpleName()));
        for(Node child : expr.getThenStmt().getChildNodes()){
            handler = HandlerFactory.getHandler(child);
            if(handler != null) {
                tokens.addAll(handler.handle(child));
            } else {
                System.out.println(child.getClass().getSimpleName());
            }
        }
        if(expr.getElseStmt().isPresent()) {
            tokens.add(new BCEToken("ELSE", node.getClass().getSimpleName()));
            Node elseStmt = expr.getElseStmt().get();
            for (Node child : elseStmt.getChildNodes()) {
                handler = HandlerFactory.getHandler(child);
                if (handler != null) {
                    tokens.addAll(handler.handle(child));
                } else {
                    System.out.println(child.getClass().getSimpleName());
                }
            }
        }
        return tokens;
    }
}