package com.pagasi.lostfound.user_service.repository;

import com.pagasi.lostfound.user_service.entity.ContactEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface contactInfoRepository extends CrudRepository<ContactEntity, Long> {
    Optional<ContactEntity> findByUser_Id(Long userId);
}
