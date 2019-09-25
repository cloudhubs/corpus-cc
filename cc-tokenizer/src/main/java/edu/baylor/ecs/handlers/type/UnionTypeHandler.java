package edu.baylor.ecs.handlers.type;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.type.ReferenceType;
import com.github.javaparser.ast.type.UnionType;
import edu.baylor.ecs.handlers.BaseHandler;
import edu.baylor.ecs.handlers.HandlerFactory;
import edu.baylor.ecs.models.BCEToken;

import java.util.ArrayList;
import java.util.List;

public class UnionTypeHandler extends BaseHandler {
    @Override
    public List<BCEToken> handle(Node node) {
        List<BCEToken> tokens = new ArrayList<>();
        tokens.add(new BCEToken("UNION", node.getClass().getSimpleName()));
        UnionType unionType = (UnionType) node;
        NodeList<ReferenceType> list = unionType.getElements();
        for(Node n : list) {
            BaseHandler handler = HandlerFactory.getHandler(n);
            if (handler != null) {
                tokens.addAll(handler.handle(n));
            } else {
                System.out.println(n.getClass().getSimpleName());
            }
        }
        return tokens;
    }
}
