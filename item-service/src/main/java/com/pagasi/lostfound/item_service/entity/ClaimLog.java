package com.pagasi.lostfound.item_service.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "claim_logs")
public class ClaimLog {
    @Id
    private String id;
    private String claimedBy;
    private String itemId;
    private String claimerUserId;
    private LocalDateTime verifiedAt;
    private String postedId;
}
