package edu.baylor.ecs.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class MethodRepresentation {
    private String className;
    private String methodName;
    private List<BCEToken> tokens;
    private Map<String, Integer> zip;
    private int uniqueTokens;
    private String raw;
    private String fullHash;
    private String trimmedHash;
    private String bareHash;
    private int fileLines;
    private int linesOfCode;
    private int logicalLinesOfCode;
}
