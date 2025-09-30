package com.pagasi.lostfound.user_service.repository;

import com.pagasi.lostfound.user_service.entity.ContactEntity;
import org.springframework.data.repository.CrudRepository;

public interface contactInfoRepository extends CrudRepository<ContactEntity, Long> {
}
