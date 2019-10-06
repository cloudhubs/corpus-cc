package edu.baylor.ecs.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Clone {
    private MethodRepresentation methodRepresentationA;
    private MethodRepresentation methodRepresentationB;
    private CodeCloneType type;
}
