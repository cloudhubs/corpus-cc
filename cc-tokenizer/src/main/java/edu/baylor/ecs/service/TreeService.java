package edu.baylor.ecs.service;

import com.github.javaparser.ast.Node;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TreeService {

    List<Node> extractNodes(Node root, Class c){
        List<Node> nodes = new ArrayList<>();

        for(Node child : root.getChildNodes()){
            if(child.getClass().getSimpleName().equals(c.getSimpleName())){
                nodes.add(child);
            }
        }
        return nodes;
    }
}
