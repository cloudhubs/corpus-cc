package edu.baylor.ecs.handlers.stmt;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.TryStmt;
import edu.baylor.ecs.handlers.BaseHandler;
import edu.baylor.ecs.handlers.HandlerFactory;
import edu.baylor.ecs.models.BCEToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TryStmtHandler extends BaseHandler {
    @Override
    public List<BCEToken> handle(Node node) {
        List<BCEToken> tokens = new ArrayList<>();
        tokens.add(new BCEToken("TRY", node.getClass().getSimpleName()));

        Node tryBlock = ((TryStmt) node).getTryBlock();
        BaseHandler handler = HandlerFactory.getHandler(tryBlock);
        if(handler != null) {
            tokens.addAll(handler.handle(tryBlock));
        } else {
            System.out.println(tryBlock.getClass().getSimpleName());
        }

        for(Node child : ((TryStmt) node).getCatchClauses()){
            handler = HandlerFactory.getHandler(child);
            if(handler != null) {
                tokens.addAll(handler.handle(child));
            } else {
                System.out.println(child.getClass().getSimpleName());
            }
        }

        Optional<BlockStmt> finallyBlockOpt = ((TryStmt) node).getFinallyBlock();
        if(finallyBlockOpt.isPresent()){
            BlockStmt finallyBlock = finallyBlockOpt.get();
            handler = HandlerFactory.getHandler(finallyBlock);
            if(handler != null) {
                tokens.addAll(handler.handle(finallyBlock));
            } else {
                System.out.println(finallyBlock.getClass().getSimpleName());
            }
        }

        return tokens;
    }
}