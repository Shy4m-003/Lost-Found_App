package com.pagasi.lostfound.item_service.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LogDto {
    private String claimedBy;
    private String itemId;
    private String claimerUserId;
}
