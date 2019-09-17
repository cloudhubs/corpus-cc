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

    private final boolean useJaccardSimilarity = true;
    private final double th = 0.1;
    private final Integer MUL_FACTOR = 100;

    public CloneService(MethodService methodService, RestTemplate restTemplate){
        this.methodService = methodService;
        this.restTemplate = restTemplate;
    }

    public Clone findClone(MethodRepresentation methodRepresentation){
        List<MethodRepresentation> similar = methodService.findBySimilarity();
        for(MethodRepresentation rep : similar){
            int computedThreshold;
            if (useJaccardSimilarity) {
                computedThreshold = (int) Math.ceil((this.th * (rep.getTokens().size() + methodRepresentation.getTokens().size())) / (10 * this.MUL_FACTOR + this.th));
            } else {
                int maxLength = Math.max(rep.getTokens().size(), methodRepresentation.getTokens().size());
                computedThreshold = (int) Math.ceil((this.th * maxLength) / (10 * this.MUL_FACTOR));
            }

            // Exact String Type 1
            if(rep.getRaw().equals(methodRepresentation.getRaw())){
                return new Clone(methodRepresentation.getName(), CodeCloneType.ONE);
            }

            // Hashed Type 1
            if(rep.getBareHash().equals(methodRepresentation.getBareHash()) ||
               rep.getTrimmedHash().equals(methodRepresentation.getTrimmedHash()) ||
               rep.getFullHash().equals(methodRepresentation.getFullHash())){
                return new Clone(methodRepresentation.getName(), CodeCloneType.ONE);
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
            for(MethodRepresentation entry : methods){
                Clone clone = findClone(entry);
                if(clone != null){
                    clones.add(clone);
                }
            }
            return clones;
        }
        return new ArrayList<>();
    }
}
