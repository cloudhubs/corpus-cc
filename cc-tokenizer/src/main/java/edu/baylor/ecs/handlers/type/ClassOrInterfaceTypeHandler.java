package edu.baylor.ecs.handlers.type;

import com.github.javaparser.ast.Node;
import edu.baylor.ecs.handlers.BaseHandler;
import edu.baylor.ecs.models.BCEToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClassOrInterfaceTypeHandler extends BaseHandler {
    @Override
    public List<BCEToken> handle(Node node) {
        List<BCEToken> tokens = new ArrayList<>();
        Optional<Node> parent = node.getParentNode();
        String parentName = parent.get().getClass().getSimpleName();
        switch (parentName){
            case "VariableDeclarator": tokens.add(new BCEToken("VARIABLE_TYPE", node.getClass().getSimpleName())); break;
            case "Parameter": tokens.add(new BCEToken("PARAMETER_TYPE", node.getClass().getSimpleName())); break;
            case "ObjectCreationExpr": tokens.add(new BCEToken("OBJECT", node.getClass().getSimpleName())); break;
            case "MethodDeclaration": tokens.add(new BCEToken("RETURN_TYPE", node.getClass().getSimpleName())); break;
            case "MethodCallExpr":
            case "ClassOrInterfaceDeclaration":
            case "MethodReferenceExpr":
            case "UnionType":
            case "TypeExpr":
            case "ClassExpr": tokens.add(new BCEToken("CLASS_TYPE", node.getClass().getSimpleName())); break;
            case "CastExpr": tokens.add(new BCEToken("CAST_TYPE", node.getClass().getSimpleName())); break;
            case "ArrayType":
            case "ArrayCreationExpr": tokens.add(new BCEToken("ARRAY_TYPE", node.getClass().getSimpleName())); break;
            case "ConstructorDeclaration": tokens.add(new BCEToken("SUPERCLASS_TYPE", node.getClass().getSimpleName())); break;
            default:
                System.err.println("ClassOrInterfaceTypeHandler - " + parentName);
                System.exit(-1);
        }
        return tokens;
    }
}