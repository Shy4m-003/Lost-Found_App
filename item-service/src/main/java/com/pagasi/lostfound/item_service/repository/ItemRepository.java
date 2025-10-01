package com.pagasi.lostfound.item_service.repository;

import com.pagasi.lostfound.item_service.entity.ItemEntity;
import com.pagasi.lostfound.item_service.entity.ItemStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ItemRepository extends MongoRepository<ItemEntity, String> {
    Optional<ItemEntity> findByNameAndCategoryAndDescription(String name, String category, String Description);
    List<ItemEntity> findByStatusAndScheduledDeletionAtBefore(ItemStatus itemStatus, LocalDateTime now);
}
