package com.pagasi.lostfound.user_service.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {
    private String mobileNumber;
    private String password;
    private Long id;
}
