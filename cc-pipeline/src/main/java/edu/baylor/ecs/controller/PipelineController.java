package edu.baylor.ecs.controller;

import edu.baylor.ecs.models.DiscoveryRequest;
import edu.baylor.ecs.models.MethodRepresentation;
import edu.baylor.ecs.service.MethodService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class PipelineController {

    private final MethodService methodService;
    private final RestTemplate restTemplate;

    public PipelineController(MethodService methodService, RestTemplate restTemplate) {
        this.methodService = methodService;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/handshake")
    public String home() {
        return "Hello from [PipelineController]";
    }

    @PostMapping("/generateCorpus")
    public void generateCorpus(@RequestBody DiscoveryRequest request){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String url = "http://localhost:7002/tokens";
        HttpEntity<Object> requestEntity = new HttpEntity<>(request, headers);
        ResponseEntity<List<MethodRepresentation>> filesResponse = restTemplate.exchange(url, HttpMethod.POST, requestEntity,new ParameterizedTypeReference<>() {});
        if(filesResponse.getStatusCode() == HttpStatus.OK){
            List<MethodRepresentation> methods = Objects.requireNonNull(filesResponse.getBody());
            for(MethodRepresentation rep : methods){
                this.methodService.save(rep);
            }

        }
    }

    @PostMapping("/addToCorpus")
    public MethodRepresentation addToCorpus(@RequestBody MethodRepresentation request){
        return this.methodService.save(request);
    }
}
