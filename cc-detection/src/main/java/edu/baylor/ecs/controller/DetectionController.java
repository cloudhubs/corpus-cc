package edu.baylor.ecs.controller;

import edu.baylor.ecs.models.Clone;
import edu.baylor.ecs.models.DiscoveryRequest;
import edu.baylor.ecs.models.MethodRepresentation;
import edu.baylor.ecs.service.CloneService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DetectionController {

    private final CloneService cloneService;

    public DetectionController(CloneService cloneService) {
        this.cloneService = cloneService;
    }

    @GetMapping("/handshake")
    public String home() {
        return "Hello from [PipelineApplication]";
    }

    @PostMapping("/findClones")
    public List<Clone> findClones(@RequestBody DiscoveryRequest request){
        return this.cloneService.findClonesForRepository(request);
    }
}
