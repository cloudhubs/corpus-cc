package edu.baylor.ecs.service;

import edu.baylor.ecs.models.MethodRepresentation;
import edu.baylor.ecs.repository.MethodRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MethodService {

    private MethodRepository methodRepository;

    public MethodService(MethodRepository methodRepository) {
        this.methodRepository = methodRepository;
    }

    public MethodRepresentation save(MethodRepresentation book) {
        return methodRepository.save(book);
    }

    public void delete(MethodRepresentation book) {
        methodRepository.delete(book);
    }

    public Iterable<MethodRepresentation> findAll() {
        return methodRepository.findAll();
    }

    Iterable<MethodRepresentation> getByHeuristics(int fileLineMin, int fileLineMax, int uniqueTokensMin, int uniqueTokensMax) {
            return methodRepository.getDistinctByFileLinesBetweenAndUniqueTokensBetween(fileLineMin, fileLineMax, uniqueTokensMin, uniqueTokensMax);
    }
}
