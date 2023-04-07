package com.future.future.service;

import com.future.future.domain.Role;
import com.future.future.domain.User;
import com.future.future.domain.enums.RoleType;
import com.future.future.dto.mapper.UserMapper;
import com.future.future.dto.request.RegisterRequest;
import com.future.future.exception.ConflictException;
import com.future.future.exception.ResourceNotFoundException;
import com.future.future.exception.message.ErrorMessage;
import com.future.future.repository.RoleRepository;
import com.future.future.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private UserMapper userMapper;

    public void register(RegisterRequest registerRequest){
        if (userRepository.existsByEmail(registerRequest.getEmail())){
            throw new ConflictException(String.format(ErrorMessage.EMAIL_ALREADY_EXIST, registerRequest.getEmail()));
        }

        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());

        Role role = roleRepository.findByName(RoleType.ROLE_CUSTOMER).
                orElseThrow(()->new ResourceNotFoundException
                        (String.format(ErrorMessage.ROLE_NOT_FOUND_MESSAGE,RoleType.ROLE_CUSTOMER.name())));

        Set<Role> roles=new HashSet<>();
        roles.add(role);

        User user = userMapper.registerRequestToUser(registerRequest);
        user.setRoles(roles);
        user.setPassword(encodedPassword);

        userRepository.save(user);
    }
}
