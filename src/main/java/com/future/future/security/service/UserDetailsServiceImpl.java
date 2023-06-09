package com.future.future.security.service;

import com.future.future.domain.User;
import com.future.future.exception.message.ErrorMessage;
import com.future.future.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user=userRepository.findByEmail(email).orElseThrow(()->new
                UsernameNotFoundException(String.format(ErrorMessage.USER_NOT_FOUND_MESSAGE, email)));

        return UserDetailsImpl.build(user);
    }
}
