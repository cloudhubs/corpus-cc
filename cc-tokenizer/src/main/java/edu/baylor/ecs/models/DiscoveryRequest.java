package edu.baylor.ecs.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DiscoveryRequest {
    private String baseFolder;
    private int depth;
    private String basePackage;
}
