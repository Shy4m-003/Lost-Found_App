package com.pagasi.lostfound.user_service.controller;

import com.pagasi.lostfound.user_service.AuthenticationConfiguration.JwtService;
import com.pagasi.lostfound.user_service.customization.CustomUserDetailsImpl;
import com.pagasi.lostfound.user_service.customization.CustomUserDetailsServiceImpl;
import com.pagasi.lostfound.user_service.dtos.*;
import com.pagasi.lostfound.user_service.service.Implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/api/user-service/v1"})
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserServiceImpl userLogic;
    @Autowired
    private CustomUserDetailsServiceImpl customUserDetailsService;
    @Autowired
    private JwtService jwtService;
    @PostMapping({"/sign-up/user"})
    public ResponseEntity<SignInDTO> signup(@RequestBody SignInDTO signInDTO) {
        signInDTO = this.userLogic.signIn(signInDTO);
        CustomUserDetailsImpl userDetails = (CustomUserDetailsImpl) this.customUserDetailsService
                .loadUserByUsername(signInDTO.getMobileNumber());
        String token = this.jwtService.generateToken(userDetails);
        return new ResponseEntity(token, HttpStatus.OK);
    }

    @PostMapping({"/sign-in/user"})
    public String authenticateAndGetToken(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getMobileNumber(), loginDTO.getPassword()));
        if (authentication.isAuthenticated()) {
            CustomUserDetailsImpl userDetails = (CustomUserDetailsImpl)this.customUserDetailsService.loadUserByUsername(loginDTO.getMobileNumber());
            return this.jwtService.generateToken(userDetails);
        } else {
            throw new UsernameNotFoundException("Invalid Credentials");
        }
    }

    @PatchMapping({"/edit-credentials/{id}"})
    public void changePassword(@PathVariable Long id, @RequestBody EditCredDto credDto) {
        this.userLogic.changePassword(id, credDto);
    }

    @PatchMapping({"/edit/{id}"})
    public ResponseEntity<EditDetails> editDetails(@PathVariable Long id, @RequestBody EditDetails editDetails) {
        editDetails = this.userLogic.changeDetails(id, editDetails);
        return new ResponseEntity(editDetails, HttpStatus.OK);
    }

    @DeleteMapping({"/delete-profile/{id}"})
    public ResponseEntity<?> delete(@PathVariable Long id, @RequestBody DeleteDto dto) {
        this.userLogic.deleteAccount(id, dto);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @PostMapping({"/add-contactInfo/{id}"})
    public ResponseEntity<ContactInfoDto> addContactInfo(@PathVariable long id, @RequestBody ContactInfoDto contactInfoDto){
        contactInfoDto = this.userLogic.saveContactInfo(contactInfoDto, id);
        return new ResponseEntity<>(contactInfoDto,HttpStatus.OK);
    }
}

