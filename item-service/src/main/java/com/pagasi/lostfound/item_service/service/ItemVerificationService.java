package com.pagasi.lostfound.item_service.service;
import com.pagasi.lostfound.item_service.model.LogDto;

public interface ItemVerificationService {
    void claimedBy (LogDto logDto);
}
