package com.pagasi.lostfound.user_service.service.Implementation;

import com.pagasi.lostfound.user_service.converter.ContactInfoConverter;
import com.pagasi.lostfound.user_service.converter.UserConverter;
import com.pagasi.lostfound.user_service.dtos.*;
import com.pagasi.lostfound.user_service.entity.ContactEntity;
import com.pagasi.lostfound.user_service.entity.UserEntity;
import com.pagasi.lostfound.user_service.repository.UserRepository;
import com.pagasi.lostfound.user_service.repository.contactInfoRepository;
import com.pagasi.lostfound.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private ContactInfoConverter contactInfoConverter;
    @Autowired
    private contactInfoRepository contactInfoRepository;
    @Autowired
    private UserConverter userConverter;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public SignInDTO login(LoginDTO loginDTO) {
        UserEntity user = (UserEntity)this.userRepository.findByMobileNumber(loginDTO.getMobileNumber()).orElseThrow(() -> new RuntimeException("Please Create Account"));
        if (!this.passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        } else {
            return this.userConverter.entityToDto(user);
        }
    }

    @Override
    public SignInDTO signIn(SignInDTO signIn) {
        Optional<UserEntity> optionalUserEntity = this.userRepository.findByMobileNumber(signIn.getMobileNumber());
        if (optionalUserEntity.isPresent()) {
            System.out.println("User Already Exit");
        }
        UserEntity entity = this.userConverter.dtoToEntity(signIn);
        String username = generateUniqueUsername(entity.getName());
        entity.setUsername(username);
        entity = (UserEntity)this.userRepository.save(entity);
        signIn = this.userConverter.entityToDto(entity);
        return signIn;
    }

    @Override
    public void changePassword(Long id, EditCredDto credDto) {
        UserEntity user = (UserEntity)this.userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        if (!this.passwordEncoder.matches(credDto.getExistingPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid Existing password");
        } else if (this.passwordEncoder.matches(credDto.getNewPassword(), user.getPassword())) {
            throw new RuntimeException("New password and old password should not be same");
        } else {
            user.setPassword(this.passwordEncoder.encode(credDto.getNewPassword()));
            this.userRepository.save(user);
        }
    }

    @Override
    public EditDetails changeDetails(Long id, EditDetails editDetails) {
        UserEntity user = (UserEntity)this.userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        if (editDetails.getName() != null && !editDetails.getName().isBlank()) {
            user.setName(editDetails.getName());
        }

        if (editDetails.getMobileNumber() != null && !editDetails.getMobileNumber().isBlank()) {
            user.setMobileNumber(editDetails.getMobileNumber());
        }

        user = (UserEntity)this.userRepository.save(user);
        editDetails.setMobileNumber(user.getMobileNumber());
        editDetails.setName(user.getName());
        return editDetails;
    }

    @Override
    public void deleteAccount(Long id, DeleteDto dto) {
        UserEntity user = (UserEntity)this.userRepository.findById(id).orElseThrow(() -> new RuntimeException("user not found"));
        if (this.passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            this.userRepository.delete(user);
        } else {
            throw new RuntimeException("Invalid Password");
        }
    }

    @Override
    public String generateUniqueUsername(String name) {
        String base = name.toLowerCase().replaceAll("\\s+", "");
        base = base.length() >= 3 ? base.substring(0, 3) : String.format("%-3s", base).replace(' ', 'x');

        String username;
        Random random = new Random();

        do {
            // Generate 5 random alphanumeric characters
            String randomSuffix = random.ints(48, 122)
                    .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                    .limit(5)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
            username = base + randomSuffix; // total length = 8
        } while (userRepository.existsByUsername(username)); // ensure uniqueness

        return username;
    }
    public ContactInfoDto saveContactInfo(ContactInfoDto contactInfoDto,Long id) {
        Optional<UserEntity> optionalContactEntity = userRepository.findById(id);
        if(optionalContactEntity.isEmpty()) {
            throw new RuntimeException("Register first");
        }
        Optional<ContactEntity> existingDetailsOpt = contactInfoRepository.findByUser_Id(id);
        ContactEntity savedEntity;
        if(existingDetailsOpt.isPresent()){
            ContactEntity existingDetails = existingDetailsOpt.get();
            existingDetails.setAddress(contactInfoDto.getAddress());
            existingDetails.setOrganization(contactInfoDto.getOrganization());
            existingDetails.setEmailId(contactInfoDto.getEmailId());
            existingDetails.setAdditionNumber(contactInfoDto.getAdditionNumber());
            existingDetails.setSocialMediaId(contactInfoDto.getSocialMediaId());
            savedEntity = contactInfoRepository.save(existingDetails);
        }else{
            ContactEntity newDetails = contactInfoConverter.dtoToEntity(contactInfoDto);
            newDetails.setUser(optionalContactEntity.get());  // Link with UserEntity
            savedEntity = contactInfoRepository.save(newDetails);
        }
        return contactInfoConverter.entityToDto(savedEntity);
    }
}
