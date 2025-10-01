package com.pagasi.lostfound.item_service.converter;

import com.pagasi.lostfound.item_service.entity.ItemEntity;
import com.pagasi.lostfound.item_service.model.ItemDto;
import org.springframework.stereotype.Component;

@Component
public class ItemConverter {

    public ItemEntity dtoToEntity(ItemDto dto){
        ItemEntity entity = new ItemEntity();
        entity.setCategory(dto.getCategory());
        entity.setDescription(dto.getDescription());
        entity.setName(dto.getName());
        entity.setCurrentLocation(dto.getCurrentLocation());
        entity.setLastSeenLocation(dto.getLastSeenLocation());
        entity.setImages(dto.getImages());
        return entity;
    }
}
