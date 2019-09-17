package edu.baylor.ecs.repository;

import edu.baylor.ecs.models.MethodRepresentation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface MethodRepository extends MongoRepository<MethodRepresentation, String> {

    //public List<MethodRepresentation> getBySimilarity();

}
