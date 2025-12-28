package com.pagasi.lostfound.connection_service.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Data
@Document(collection = "comments")
public class CommentsEntity {
    @Id
    private String id;
    private String requestId;
    private String userId;
    private String content;
    private CommentType type;
    private String referencedItemId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
