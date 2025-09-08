package com.pagasi.lostfound.user_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ContactInfo")
public class ContactEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String emailId;
    private String additionNumber;
    private String organization;
    private String address;
    private String socialMediaId;
    @OneToOne(mappedBy = "contact")
    private UserEntity user;
}
