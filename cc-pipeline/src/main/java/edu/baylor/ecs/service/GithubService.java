package edu.baylor.ecs.service;

import edu.baylor.ecs.models.GitHubProperties;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Service
public class GithubService {

    @Value("classpath:github.yml")
    private Resource resource;

    private GitHubProperties properties;

    public void cloneRepositories() {
        if(this.properties == null){
            Yaml yaml = new Yaml();
            try (InputStream in = resource.getInputStream()) {
                this.properties = yaml.loadAs(in, GitHubProperties.class);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        for(String url : properties.getUrls()) {
            try {
                Git.cloneRepository()
                .setURI(url)
                .setDirectory(new File(properties.getPath()))
                .call();
            } catch (GitAPIException e) {
                e.printStackTrace();
            }
        }
    }
}
