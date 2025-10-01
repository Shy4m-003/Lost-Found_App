package com.pagasi.lostfound.user_service.converter;

import com.pagasi.lostfound.user_service.dtos.ContactInfoDto;
import com.pagasi.lostfound.user_service.entity.ContactEntity;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContactInfoConverter {

    public ContactEntity dtoToEntity(ContactInfoDto dto){
        ContactEntity entity = new ContactEntity();
        entity.setAddress(dto.getAddress());
        entity.setAdditionNumber(dto.getAdditionNumber());
        entity.setOrganization(dto.getOrganization());
        entity.setEmailId(dto.getEmailId());
        entity.setSocialMediaId(dto.getSocialMediaId());
        return entity;
    }

    public ContactInfoDto entityToDto(ContactEntity entity){
        ContactInfoDto dto = new ContactInfoDto();
        dto.setAdditionNumber(entity.getAdditionNumber());
        dto.setAddress(entity.getAddress());
        dto.setOrganization(entity.getOrganization());
        dto.setEmailId(entity.getEmailId());
        dto.setSocialMediaId(entity.getSocialMediaId());
        return dto;
    }
}
