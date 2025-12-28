package com.pagasi.lostfound.connection_service.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "requests")
@Data
public class RequestPostEntity {
    @Id
    private String id;
    private String userId;
    private String content;
    private List<String> images;
    private String category;
    private LocalDateTime createdAt;
    private PostStatus status;
    private Long commentCount = 0L;
    private String locationTag;
    private double latitude;
    private double longitude;
}
