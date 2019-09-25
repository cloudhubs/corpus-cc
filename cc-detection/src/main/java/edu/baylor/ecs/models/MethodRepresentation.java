package edu.baylor.ecs.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@Document(collection = "github")
public class MethodRepresentation {
    private String name;
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
