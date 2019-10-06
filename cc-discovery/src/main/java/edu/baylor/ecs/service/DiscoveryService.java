package edu.baylor.ecs.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class DiscoveryService {

    private DirectoryService directoryService;

    public DiscoveryService(DirectoryService directoryService){
        this.directoryService = directoryService;
    }

    public List<String> findAllSourceFiles(String baseFolder, int depth) {
        return directoryService.getResourcePaths(baseFolder, depth, ".java");
    }

    public List<String> findAllJarFiles(String baseFolder, int depth) {
        return directoryService.getResourcePaths(baseFolder, depth, ".jar");
    }

    public List<String> findAllSnippetFiles(String baseFolder, int depth) {
        return directoryService.getResourcePaths(baseFolder, depth, ".json");
    }
}
