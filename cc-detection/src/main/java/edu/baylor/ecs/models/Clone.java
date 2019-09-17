package edu.baylor.ecs.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Clone {
    private String MethodName;
    private CodeCloneType type;
}
