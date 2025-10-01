package com.pagasi.lostfound.item_service.service;

import com.pagasi.lostfound.item_service.model.ItemDto;

import java.util.Map;

public interface ItemService {
    void post(ItemDto itemDto);
    void updateItem(String id, Map<String, Object> updates,String currentId);
    void deleteItem(String id, String currentUserId);
    void deleteClaimedItems();
}
