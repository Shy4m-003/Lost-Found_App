package com.pagasi.lostfound.item_service.service.impl;

import com.pagasi.lostfound.item_service.converter.ItemConverter;
import com.pagasi.lostfound.item_service.entity.ItemEntity;
import com.pagasi.lostfound.item_service.entity.ItemStatus;
import com.pagasi.lostfound.item_service.exceptions.InvalidOperationException;
import com.pagasi.lostfound.item_service.exceptions.ResourceNotFoundException;
import com.pagasi.lostfound.item_service.exceptions.UnauthorizedActionException;
import com.pagasi.lostfound.item_service.model.ItemDto;
import com.pagasi.lostfound.item_service.repository.ItemRepository;
import com.pagasi.lostfound.item_service.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//import static jdk.internal.org.jline.reader.impl.LineReaderImpl.CompletionType.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemConverter itemConverter;

    @Override
    public void post(ItemDto itemDto) {
        Optional<ItemEntity> existing = itemRepository.findByNameAndCategoryAndDescription(itemDto.getName(), itemDto.getCategory(), itemDto.getDescription());
        if(existing.isPresent()){
            throw new IllegalStateException("Duplicate Item");
        }
        ItemEntity itemEntity = itemConverter.dtoToEntity(itemDto);
        itemEntity.setPostedByUserId(itemDto.getPostedByUserId());
        itemEntity.setPostedTime(LocalDateTime.now());
        itemEntity.setScheduledDeletionAt(null);
        itemRepository.save(itemEntity);
    }

    @Override
    public void updateItem(String id, Map<String, Object> updates, String currentUserId) {
        ItemEntity item = itemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Item not found"));
        if (!item.getPostedByUserId().equals(currentUserId)) {
            throw new UnauthorizedActionException("You cannot edit this item");
        }

        if (item.getStatus() == ItemStatus.CLAIMED) {
            throw new InvalidOperationException("Cannot edit claimed or deleted items");
        }
        updates.forEach((key, value) -> {
            switch (key) {
                case "name":
                    item.setName((String) value);
                    break;
                case "description":
                    item.setDescription((String) value);
                    break;
                case "lastSeenLocation":
                    item.setLastSeenLocation((String) value);
                    break;
                case "imageUrl":
                    item.setImages((List<String>) value);
                    break;
                case "currentLocation":
                    item.setCurrentLocation((String) value);
                    break;
                case "category":
                    item.setCategory((String) value);
                    break;
                    // You can ignore restricted fields like status, postedByUserId, etc.
            }
            itemRepository.save(item);
        });
    }

    @Override
    public void deleteItem(String id, String currentUserId) {
        ItemEntity item = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found"));

        if (!item.getPostedByUserId().equals(currentUserId)) {
            throw new UnauthorizedActionException("You cannot delete this item");
        }

        if (item.getStatus() == ItemStatus.CLAIMED) {
            throw new InvalidOperationException("Claimed items cannot be deleted");
        }

        item.setStatus(ItemStatus.DELETED);
        itemRepository.save(item);
    }
    @Scheduled(cron = "0 0 * * * *") // every hour
    public void deleteClaimedItems() {
        LocalDateTime now = LocalDateTime.now();
        List<ItemEntity> expired = itemRepository.findByStatusAndScheduledDeletionAtBefore(ItemStatus.CLAIMED, now);
        for (ItemEntity item : expired) {
            itemRepository.delete(item);
        }
    }

}
