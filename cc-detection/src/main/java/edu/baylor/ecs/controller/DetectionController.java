package edu.baylor.ecs.controller;

import edu.baylor.ecs.models.*;
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
        return "Hello from [DetectionApplication]";
    }

    @PostMapping("/findClones")
    public List<Clone> findClones(@RequestBody DiscoveryRequest request){
        return this.cloneService.findClonesForRepository(request);
    }

    @PostMapping("/findRankedClones")
    public SeverityRanking findRankedClones(@RequestBody DiscoveryRequest request){
        return new SeverityRanking(this.cloneService.findClonesForRepository(request));
    }

    @PostMapping("/severity")
    public SeverityRanking severity(@RequestBody SeverityRequest request) {
        return new SeverityRanking(request.getClones());
    }
}
