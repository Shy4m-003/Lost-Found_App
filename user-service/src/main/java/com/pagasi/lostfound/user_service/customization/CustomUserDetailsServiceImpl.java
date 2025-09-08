package com.pagasi.lostfound.user_service.customization;

import com.pagasi.lostfound.user_service.entity.UserEntity;
import com.pagasi.lostfound.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Primary
public class CustomUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = (UserEntity)this.userRepository.findByMobileNumber(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new CustomUserDetailsImpl(user);
    }
}
