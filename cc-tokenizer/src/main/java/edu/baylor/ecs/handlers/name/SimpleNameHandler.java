package edu.baylor.ecs.handlers.name;

import com.github.javaparser.ast.Node;
import edu.baylor.ecs.handlers.BaseHandler;
import edu.baylor.ecs.models.BCEToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SimpleNameHandler extends BaseHandler {
    @Override
    public List<BCEToken> handle(Node node) {
        List<BCEToken> tokens = new ArrayList<>();
        Optional<Node> parent = node.getParentNode();
        String parentName = parent.get().getClass().getSimpleName();
        switch (parentName){
            case "ClassOrInterfaceDeclaration": tokens.add(new BCEToken("CLASS_NAME", node.getClass().getSimpleName())); break;
            case "EnumDeclaration": tokens.add(new BCEToken("ENUM_NAME", node.getClass().getSimpleName())); break;
            case "NameExpr":
            case "VariableDeclarator": tokens.add(new BCEToken("VARIABLE_NAME", node.getClass().getSimpleName())); break;
            case "ConstructorDeclaration": tokens.add(new BCEToken("CONSTRUCTOR_NAME", node.getClass().getSimpleName())); break;
            case "Parameter": tokens.add(new BCEToken("PARAMETER_NAME", node.getClass().getSimpleName())); break;
            case "FieldAccessExpr": tokens.add(new BCEToken("FIELD_NAME", node.getClass().getSimpleName())); break;
            case "MethodDeclaration": tokens.add(new BCEToken("METHOD_NAME", node.getClass().getSimpleName())); break;
            case "MethodCallExpr": tokens.add(new BCEToken("METHOD_CALL_NAME", node.getClass().getSimpleName())); break;
            case "TypeParameter": tokens.add(new BCEToken("GENERICS_NAME", node.getClass().getSimpleName())); break;
            case "ContinueStmt": tokens.add(new BCEToken("CONTINUE_NAME", node.getClass().getSimpleName())); break;
            case "LabeledStmt": tokens.add(new BCEToken("LABEL_NAME", node.getClass().getSimpleName())); break;
            default:
                System.err.println("SimpleNameHandler - " + parentName);
                System.exit(-1);
        }
        return tokens;
    }
}