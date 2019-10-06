package edu.baylor.ecs.controllers;

import edu.baylor.ecs.models.DiscoveryRequest;
import edu.baylor.ecs.service.DiscoveryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DiscoveryController {
    private final DiscoveryService discoveryService;

    public DiscoveryController(DiscoveryService discoveryService) {
        this.discoveryService = discoveryService;
    }

    @GetMapping("/handshake")
    public String home() {
        return "Hello from [DiscoveryController]";
    }

    @PostMapping("/sourceFiles")
    public List<String> retrieveSourceFiles(@RequestBody DiscoveryRequest request){
        return this.discoveryService.findAllSourceFiles(request.getBaseFolder(), request.getDepth());
    }

    @PostMapping("/snippetFiles")
    public List<String> retrieveSnippetFiles(@RequestBody DiscoveryRequest request){
        return this.discoveryService.findAllSnippetFiles(request.getBaseFolder(), request.getDepth());
    }

    @PostMapping("/jarFiles")
    public List<String> retrieveJarFiles(@RequestBody DiscoveryRequest request){
        return this.discoveryService.findAllJarFiles(request.getBaseFolder(), request.getDepth());
    }
}
