package com.pagasi.lostfound.item_service.repository;

import com.pagasi.lostfound.item_service.entity.ClaimLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogRepository extends MongoRepository<ClaimLog , String> {
}
