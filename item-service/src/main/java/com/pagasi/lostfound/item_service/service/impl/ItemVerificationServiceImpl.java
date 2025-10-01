package com.pagasi.lostfound.item_service.service.impl;

import com.pagasi.lostfound.item_service.entity.ClaimLog;
import com.pagasi.lostfound.item_service.entity.ItemEntity;
import com.pagasi.lostfound.item_service.entity.ItemStatus;
import com.pagasi.lostfound.item_service.exceptions.InvalidOperationException;
import com.pagasi.lostfound.item_service.exceptions.ResourceNotFoundException;
import com.pagasi.lostfound.item_service.model.LogDto;
import com.pagasi.lostfound.item_service.repository.ItemRepository;
import com.pagasi.lostfound.item_service.repository.LogRepository;
import com.pagasi.lostfound.item_service.service.ItemVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ItemVerificationServiceImpl implements ItemVerificationService {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private LogRepository logRepository;
    @Override
    public void claimedBy(LogDto logDto) {
        ItemEntity item = itemRepository.findById(logDto.getItemId())
                .orElseThrow(()-> new ResourceNotFoundException("Item Not Found"));
        if(item.getStatus() == ItemStatus.CLAIMED){
            throw new InvalidOperationException("The Item is Already Claimed");
        }
        else{
            item.setStatus(ItemStatus.CLAIMED);
            item.setScheduledDeletionAt(LocalDateTime.now().plusDays(7));
            ClaimLog claimed = new ClaimLog();
            claimed.setClaimedBy(logDto.getClaimedBy());
            claimed.setClaimerUserId(logDto.getClaimerUserId());
            claimed.setItemId(logDto.getItemId());
            claimed.setPostedId(item.getPostedByUserId());
            claimed.setVerifiedAt(LocalDateTime.now());
            itemRepository.save(item);
            logRepository.save(claimed);
        }
    }
}
