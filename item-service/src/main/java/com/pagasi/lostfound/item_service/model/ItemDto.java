package com.pagasi.lostfound.item_service.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter

public class ItemDto {
    private String name;
    private String category;
    private String description;
    private String location;
    private String organization;
    private String lastSeenLocation;
    private String currentLocation;
    private List<String> images;
    private String postedByUserId;
    private String message;
}
