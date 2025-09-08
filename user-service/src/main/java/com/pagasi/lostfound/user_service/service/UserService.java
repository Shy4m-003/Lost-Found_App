package com.pagasi.lostfound.user_service.service;

import com.pagasi.lostfound.user_service.dtos.*;

public interface UserService {

    SignInDTO login(LoginDTO loginDTO);
    SignInDTO signIn(SignInDTO signIn);
    void changePassword(Long id, EditCredDto editCredDto);
    EditDetails changeDetails(Long id, EditDetails editDetails);
    void deleteAccount(Long id, DeleteDto dto);
    String generateUniqueUsername(String username);
}
