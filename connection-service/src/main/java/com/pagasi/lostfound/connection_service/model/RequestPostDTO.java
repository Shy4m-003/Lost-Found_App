package com.pagasi.lostfound.connection_service.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class RequestPostDTO {
    private String userId;
    private String content;
    private List<String> images;
    private String category;
    private LocalDateTime createdAt;
    private String locationTag;
    private double latitude;
    private double longitude;
}
