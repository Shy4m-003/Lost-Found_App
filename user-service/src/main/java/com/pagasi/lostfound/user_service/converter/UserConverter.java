package com.pagasi.lostfound.user_service.converter;

import com.pagasi.lostfound.user_service.dtos.LoginDTO;
import com.pagasi.lostfound.user_service.dtos.SignInDTO;
import com.pagasi.lostfound.user_service.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    @Autowired
    private PasswordEncoder passwordEncoder;

    public SignInDTO entityToDto(UserEntity entity) {
        SignInDTO signInDTO = new SignInDTO();
        signInDTO.setId(entity.getId());
        signInDTO.setName(entity.getName());
        signInDTO.setMobileNumber(entity.getMobileNumber());
        return signInDTO;
    }

    public UserEntity dtoToEntity(SignInDTO signInDTO) {
        UserEntity entity = new UserEntity();
        entity.setId(signInDTO.getId());
        entity.setName(signInDTO.getName());
        entity.setMobileNumber(signInDTO.getMobileNumber());
        entity.setPassword(this.passwordEncoder.encode(signInDTO.getPassword()));
        return entity;
    }

    public UserDetails dtoToUserDetails(LoginDTO loginDTO) {
        return User.builder().username(loginDTO.getMobileNumber()).password(loginDTO.getPassword()).roles(new String[]{"USER"}).build();
    }
}
