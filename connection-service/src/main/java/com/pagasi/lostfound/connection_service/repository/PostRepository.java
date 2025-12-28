package com.pagasi.lostfound.connection_service.repository;

import com.pagasi.lostfound.connection_service.entities.RequestPostEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PostRepository extends MongoRepository<RequestPostEntity, String> {
    Optional<RequestPostEntity> findByUserIdAndContentAndCategoryAndLocationTag(String userId,String content,String description,String locationTag);

}
