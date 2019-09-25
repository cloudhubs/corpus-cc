package edu.baylor.ecs.repository;

import edu.baylor.ecs.models.MethodRepresentation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface MethodRepository extends MongoRepository<MethodRepresentation, String> {

    Iterable<MethodRepresentation> getDistinctByFileLinesBetweenAndUniqueTokensBetween(int fileLineMin, int fileLineMax, int uniqueTokensMin, int uniqueTokensMax);

}
