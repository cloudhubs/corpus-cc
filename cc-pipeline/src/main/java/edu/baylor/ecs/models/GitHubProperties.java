package edu.baylor.ecs.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GitHubProperties {

    private List<String> urls;
    private String path;

}
