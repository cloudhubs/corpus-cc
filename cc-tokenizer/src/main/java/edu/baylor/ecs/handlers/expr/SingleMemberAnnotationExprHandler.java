package edu.baylor.ecs.handlers.expr;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import edu.baylor.ecs.handlers.BaseHandler;
import edu.baylor.ecs.handlers.HandlerFactory;
import edu.baylor.ecs.models.BCEToken;

import java.util.ArrayList;
import java.util.List;

public class SingleMemberAnnotationExprHandler extends BaseHandler {
    @Override
    public List<BCEToken> handle(Node node) {
        List<BCEToken> tokens = new ArrayList<>();
        tokens.add(new BCEToken("SM_ANNOTATION", node.getClass().getSimpleName()));
        SingleMemberAnnotationExpr singleMemberAnnotationExpr = (SingleMemberAnnotationExpr)node;
        Node memberValue = singleMemberAnnotationExpr.getMemberValue();
        BaseHandler handler = HandlerFactory.getHandler(memberValue);
        if(handler != null) {
            tokens.addAll(handler.handle(memberValue));
        } else {
            System.out.println(memberValue.getClass().getSimpleName());
        }

        return tokens;
    }
}
