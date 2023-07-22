package com.crio.starter.repository;

import java.util.List;
import java.util.Optional;
import com.crio.starter.data.MemeEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * This interface provides methods for accessing Meme entities from the database. 
 */
public interface MemeRepository extends MongoRepository<MemeEntity, String>{
    /**
     * Finds a MemeEntity by its ID.
     * @param id The ID of the MemeEntity to find.
     * @return An Optional containing the MemeEntity if it is found, or an empty Optional if it is not found. 
     */
    Optional<MemeEntity> findById(String id);

    /**
     * Checks if a MemeEntity with the given name, URL, and caption exists in the database.
     * @param name The name of the MemeEntity to check for.
     * @param url The URL of the MemeEntity to check for.
     * @param caption The caption of the MemeEntity to check for.
     * @return true if a MemeEntity with the given name, URL, and caption exists, false otherwise.
     */
    boolean existsByNameAndUrlAndCaption(String name, String url, String caption);

    /**
     * Finds the top 100 MemeEntities in the database, ordered by createdAt descending.
     * @return A List of the top 100 MemeEntities.
     */
    List<MemeEntity> findTop100ByOrderByCreatedAtDesc();
    
}
