package edu.baylor.ecs.service;

import edu.baylor.ecs.models.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class CloneService {

    private final MethodService methodService;
    private final RestTemplate restTemplate;

    private static final double th = 10;
    private static final Integer MUL_FACTOR = 2;

    public CloneService(MethodService methodService, RestTemplate restTemplate){
        this.methodService = methodService;
        this.restTemplate = restTemplate;
    }

    private Clone findClone(MethodRepresentation methodRepresentationA){
        double threshold = 0.05;

        int fileLines = methodRepresentationA.getFileLines();
        int uniqueTokens = methodRepresentationA.getUniqueTokens();
        int fileLinesMin = (int) Math.floor(Math.max(fileLines - fileLines * threshold, 0));
        int fileLinesMax = (int) Math.ceil(fileLines + fileLines * threshold);
        int uniqueTokensMin = (int) Math.floor(Math.max(uniqueTokens - uniqueTokens * threshold, 0));
        int uniqueTokensMax = (int) Math.ceil(uniqueTokens + uniqueTokens * threshold);
        Iterable<MethodRepresentation> similar = methodService.getByHeuristics(fileLinesMin, fileLinesMax, uniqueTokensMin, uniqueTokensMax);
        for(MethodRepresentation methodRepresentationB : similar){

            int computedThreshold = (int) Math.ceil((th * (methodRepresentationB.getTokens().size() + methodRepresentationA.getTokens().size())) / (10 * MUL_FACTOR + th));

            // Exact String Type 1
            if(methodRepresentationB.getRaw().equals(methodRepresentationA.getRaw())){
                System.out.println("\t" + methodRepresentationA.getMethodName() + " - " + methodRepresentationB.getMethodName() + " - " + computedThreshold);
                return new Clone(methodRepresentationA, methodRepresentationB, CodeCloneType.ONE);
            }

            // Hashed Type 1
            if(methodRepresentationB.getBareHash().equals(methodRepresentationA.getBareHash()) ||
               methodRepresentationB.getTrimmedHash().equals(methodRepresentationA.getTrimmedHash()) ||
               methodRepresentationB.getFullHash().equals(methodRepresentationA.getFullHash())){
                System.out.println("\t" + methodRepresentationA.getMethodName() + " - " + methodRepresentationB.getMethodName() + " - " + computedThreshold);
                return new Clone(methodRepresentationA, methodRepresentationB, CodeCloneType.ONE);
            }

            if(methodRepresentationB.getZip().equals(methodRepresentationA.getZip())){
                return new Clone(methodRepresentationA, methodRepresentationB, CodeCloneType.TWO);
            }

            // iterate on bagA
            int count = 0;
            for (Map.Entry<String, Integer> tokenFrequencyA : methodRepresentationA.getZip().entrySet()) {
                for(Map.Entry<String, Integer> tokenFrequencyB : methodRepresentationB.getZip().entrySet()) {
                    // search this token in bagB
                    if (tokenFrequencyA.getKey().equals(tokenFrequencyB.getKey())) {
                        // token found.
                        count += Math.min(tokenFrequencyA.getValue(), tokenFrequencyB.getValue());
                        if (count >= computedThreshold) {
                            // report clone.
                            System.out.println("\t" + methodRepresentationA.getMethodName() + " - " + methodRepresentationB.getMethodName() + " - " + computedThreshold);
                            return new Clone(methodRepresentationA, methodRepresentationB, CodeCloneType.THREE);
                        }
                    }
                }
            }
        }

        return null;
    }

    public List<Clone> findClonesForRepository(DiscoveryRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String url = "http://localhost:7002/tokens";
        HttpEntity<Object> requestEntity = new HttpEntity<>(request, headers);
        ResponseEntity<List<MethodRepresentation>> filesResponse = restTemplate.exchange(url, HttpMethod.POST, requestEntity,new ParameterizedTypeReference<>() {});
        if(filesResponse.getStatusCode() == HttpStatus.OK){
            List<MethodRepresentation> methods = Objects.requireNonNull(filesResponse.getBody());
            List<Clone> clones = new ArrayList<>();
            int count = 0;
            for(MethodRepresentation entry : methods){
                System.out.println(String.format("REP (%d / %d )", count, methods.size()));
                Clone clone = findClone(entry);
                if(clone != null){
                    clones.add(clone);
                }
                count++;
            }
            return clones;
        }
        return new ArrayList<>();
    }
}
