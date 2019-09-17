package edu.baylor.ecs.handlers;

import com.github.javaparser.ast.Node;
import edu.baylor.ecs.models.BCEToken;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseHandler {

    public abstract List<BCEToken> handle(Node node);

    public List<BCEToken> delegateToChildren(Node node){
        List<BCEToken> tokens = new ArrayList<>();
        for(Node child : node.getChildNodes()){
            BaseHandler handler = HandlerFactory.getHandler(child);
            if(handler != null) {
                tokens.addAll(handler.handle(child));
            } else {
                System.out.println(child.getClass().getSimpleName());
            }
        }
        return tokens;
    }
}
