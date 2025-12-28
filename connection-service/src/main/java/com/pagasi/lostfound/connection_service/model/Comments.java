package com.pagasi.lostfound.connection_service.model;


import com.pagasi.lostfound.connection_service.entities.CommentType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Comments {

    private String requestId;     // link to parent RequestEntity
    private String userId;        // commenter
    private String content;       // message text
    private CommentType type;
    private String referencedItemId;
    private LocalDateTime createdAt;

}
