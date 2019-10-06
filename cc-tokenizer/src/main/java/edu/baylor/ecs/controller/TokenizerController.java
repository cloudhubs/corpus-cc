package edu.baylor.ecs.controller;

import edu.baylor.ecs.models.DiscoveryRequest;
import edu.baylor.ecs.models.MethodRepresentation;
import edu.baylor.ecs.service.TokenService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

@RestController
public class TokenizerController {

    private final RestTemplate restTemplate;
    private final TokenService tokenService;

    public TokenizerController(RestTemplate restTemplate, TokenService tokenService) {
        this.restTemplate = restTemplate;
        this.tokenService = tokenService;
    }

    @GetMapping("/handshake")
    public String home() {
        return "Hello from [TokenizerController]";
    }

    @PostMapping("/tokens")
    public List<MethodRepresentation> tokens(@RequestBody DiscoveryRequest request) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String url = "http://localhost:7001/sourceFiles";
        HttpEntity<Object> requestEntity = new HttpEntity<>(request, headers);
        ResponseEntity<List<String>> filesResponse = restTemplate.exchange(url, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<>() {});
        if(filesResponse.getStatusCode() == HttpStatus.OK){
            List<String> files = Objects.requireNonNull(filesResponse.getBody());
            return tokenService.tokenizeFiles(files);
        }
        return new ArrayList<>();
    }

    @PostMapping("/tokensFromSnippets")
    public List<MethodRepresentation> tokensFromSnippets(@RequestBody DiscoveryRequest request) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String url = "http://localhost:7001/snippetFiles";
        HttpEntity<Object> requestEntity = new HttpEntity<>(request, headers);
        ResponseEntity<List<String>> filesResponse = restTemplate.exchange(url, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<>() {});
        if(filesResponse.getStatusCode() == HttpStatus.OK){
            List<String> files = Objects.requireNonNull(filesResponse.getBody());
            return tokenService.tokenizeSnippets(files);
        }
        return new ArrayList<>();
    }
}
