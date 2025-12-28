package com.pagasi.lostfound.item_service.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter

@Document(collection = "items")
public class ItemEntity {

    @Id
    private String itemId;
    private String name;
    private String category;
    private String description;
    private ItemStatus status = ItemStatus.POSTED;
    private String location;
    private String organization;
    private String lastSeenLocation;
    private String currentLocation;
    private List<String> images;
    private String postedByUserId;
    private LocalDateTime postedTime;
    private LocalDateTime scheduledDeletionAt;
    private String message;
}
