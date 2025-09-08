package com.pagasi.lostfound.user_service.repository;

import com.pagasi.lostfound.user_service.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

    Optional<UserEntity> findByMobileNumber(String mobileNumber);
    Optional<UserEntity> findByMobileNumberAndPassword(String mobileNumber, String password);
    boolean existsByUsername(String username);
}
