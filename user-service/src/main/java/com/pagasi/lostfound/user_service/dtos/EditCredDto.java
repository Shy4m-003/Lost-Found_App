package com.pagasi.lostfound.user_service.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditCredDto {
    private String existingPassword;
    private String newPassword;
}
