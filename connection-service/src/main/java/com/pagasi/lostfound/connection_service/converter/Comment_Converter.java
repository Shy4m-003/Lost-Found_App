package com.pagasi.lostfound.connection_service.converter;

import com.pagasi.lostfound.connection_service.entities.CommentType;
import com.pagasi.lostfound.connection_service.entities.CommentsEntity;
import com.pagasi.lostfound.connection_service.model.Comments;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Component
public class Comment_Converter {
    public CommentsEntity toEntity(Comments dto) {
        CommentsEntity e = new CommentsEntity();
        e.setRequestId(dto.getRequestId());
        e.setUserId(dto.getUserId());
        e.setContent(dto.getContent());
        e.setType(dto.getType() != null ? dto.getType() : CommentType.TEXT);
        if(dto.getType() == null){
            e.setType(CommentType.TEXT);
        }
        e.setReferencedItemId(dto.getReferencedItemId());
        e.setCreatedAt(LocalDateTime.now());
        e.setUpdatedAt(LocalDateTime.now());
        return e;
    }
    public Comments toDTO(CommentsEntity e) {
        Comments dto = new Comments();
        dto.setRequestId(e.getRequestId());
        dto.setUserId(e.getUserId());
        dto.setContent(e.getContent());
        dto.setReferencedItemId(e.getReferencedItemId());
        return dto;
    }

}
