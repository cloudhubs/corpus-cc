package edu.baylor.ecs.service;

import edu.baylor.ecs.models.Clone;
import edu.baylor.ecs.models.CodeCloneType;
import edu.baylor.ecs.models.DiscoveryRequest;
import edu.baylor.ecs.models.MethodRepresentation;
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

    private final double th = 10;
    private final Integer MUL_FACTOR = 2;

    public CloneService(MethodService methodService, RestTemplate restTemplate){
        this.methodService = methodService;
        this.restTemplate = restTemplate;
    }

    private Clone findClone(MethodRepresentation methodRepresentation){
        double threshold = 0.05;

        int fileLines = methodRepresentation.getFileLines();
        int uniqueTokens = methodRepresentation.getUniqueTokens();
        int fileLinesMin = (int) Math.floor(Math.max(fileLines - fileLines * threshold, 0));
        int fileLinesMax = (int) Math.ceil(fileLines + fileLines * threshold);
        int uniqueTokensMin = (int) Math.floor(Math.max(uniqueTokens - uniqueTokens * threshold, 0));
        int uniqueTokensMax = (int) Math.ceil(uniqueTokens + uniqueTokens * threshold);
        Iterable<MethodRepresentation> similar = methodService.getByHeuristics(fileLinesMin, fileLinesMax, uniqueTokensMin, uniqueTokensMax);
        for(MethodRepresentation rep : similar){

            int computedThreshold = (int) Math.ceil((this.th * (rep.getTokens().size() + methodRepresentation.getTokens().size())) / (10 * this.MUL_FACTOR + this.th));

            // Exact String Type 1
            if(rep.getRaw().equals(methodRepresentation.getRaw())){
                System.out.println("\t" + methodRepresentation.getName() + " - " + rep.getName() + " - " + computedThreshold);
                return new Clone(methodRepresentation.getName(), CodeCloneType.ONE);
            }

            // Hashed Type 1
            if(rep.getBareHash().equals(methodRepresentation.getBareHash()) ||
               rep.getTrimmedHash().equals(methodRepresentation.getTrimmedHash()) ||
               rep.getFullHash().equals(methodRepresentation.getFullHash())){
                System.out.println("\t" + methodRepresentation.getName() + " - " + rep.getName() + " - " + computedThreshold);
                return new Clone(methodRepresentation.getName(), CodeCloneType.ONE);
            }

            if(rep.getZip().equals(methodRepresentation.getZip())){
                return new Clone(methodRepresentation.getName(), CodeCloneType.TWO);
            }

            // iterate on bagA
            int count = 0;
            for (Map.Entry<String, Integer> tokenFrequencyA : methodRepresentation.getZip().entrySet()) {
                for(Map.Entry<String, Integer> tokenFrequencyB : rep.getZip().entrySet()) {
                    // search this token in bagB
                    if (tokenFrequencyA.getKey().equals(tokenFrequencyB.getKey())) {
                        // token found.
                        count += Math.min(tokenFrequencyA.getValue(), tokenFrequencyB.getValue());
                        if (count >= computedThreshold) {
                            // report clone.
                            System.out.println("\t" + methodRepresentation.getName() + " - " + rep.getName() + " - " + computedThreshold);
                            return new Clone(methodRepresentation.getName(), CodeCloneType.THREE);
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
