package com.pagasi.lostfound.connection_service.repository;

import com.pagasi.lostfound.connection_service.entities.CommentsEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CommentRepository extends MongoRepository<CommentsEntity, String> {
    Optional<CommentsEntity> findByRequestIdAndUserId(String id, String userId);
    Optional<CommentsEntity> findByUserId(String userId);
}
