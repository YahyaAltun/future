package com.future.future.service;

import com.future.future.domain.Role;
import com.future.future.domain.User;
import com.future.future.domain.enums.RoleType;
import com.future.future.dto.UserDTO;
import com.future.future.dto.mapper.UserMapper;
import com.future.future.dto.request.RegisterRequest;
import com.future.future.dto.request.UpdatePasswordRequest;
import com.future.future.dto.request.UserUpdateRequest;
import com.future.future.exception.BadRequestException;
import com.future.future.exception.ConflictException;
import com.future.future.exception.ResourceNotFoundException;
import com.future.future.exception.message.ErrorMessage;
import com.future.future.repository.RoleRepository;
import com.future.future.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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

    public List<UserDTO> findAllUsers(){
        List<User> users = userRepository.findAll();
        return userMapper.map(users);
    }

    public UserDTO findById(Long id){
        User user=userRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.USER_NOT_FOUND_MESSAGE,id)));
        return userMapper.userToUserDTO(user);
    }

    public void updatePassword(Long id, UpdatePasswordRequest passwordRequest){
        Optional<User> userOpt=userRepository.findById(id);
        User user = userOpt.get();

        if (user.getBuiltIn()){
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }

        if (passwordEncoder.matches(passwordRequest.getOldPassword(),user.getPassword())){
            throw new BadRequestException(ErrorMessage.PASSWORD_NOT_MATCHED);
        }

        String hashedPassword=passwordEncoder.encode(passwordRequest.getNewPassword());
        user.setPassword(hashedPassword);

        userRepository.save(user);
    }

    public void removeById(Long id){
        User user=userRepository.findById(id).orElseThrow(()-> new
                ResourceNotFoundException(String.format(ErrorMessage.USER_NOT_FOUND_MESSAGE,id)));

        if (user.getBuiltIn()){
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }
        userRepository.deleteById(id);
    }

    @Transactional
    public void updateUser(Long id, UserUpdateRequest userUpdateRequest){
        boolean emailExist=userRepository.existsByEmail(userUpdateRequest.getEmail());

        User user = userRepository.findById(id).get();

        if (user.getBuiltIn()){
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }
        if (emailExist&& !userUpdateRequest.getEmail().equals(user.getEmail())){
            throw new ConflictException(String.format(ErrorMessage.EMAIL_ALREADY_EXIST,user.getEmail()));
        }

        userRepository.update(id,userUpdateRequest.getFirstName(),userUpdateRequest.getLastName(),
                userUpdateRequest.getPhoneNumber(),userUpdateRequest.getEmail(),userUpdateRequest.getAddress());
    }
}
