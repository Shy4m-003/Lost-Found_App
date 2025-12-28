package com.pagasi.lostfound.connection_service.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteDto {
    private String id;
    private String postId;
    private String commentId;
}
