package com.future.future.controller;

import com.future.future.dto.UserDTO;
import com.future.future.dto.request.UpdatePasswordRequest;
import com.future.future.dto.request.UserUpdateRequest;
import com.future.future.dto.response.FutureResponse;
import com.future.future.dto.response.ResponseMessage;
import com.future.future.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping("/auth/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        List<UserDTO> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    public ResponseEntity<UserDTO> getUserById(HttpServletRequest request){
        Long id =(Long) request.getAttribute("id");
        UserDTO userDTO=userService.findById(id);

        return ResponseEntity.ok(userDTO);
    }

    @PatchMapping("/auth")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    public ResponseEntity<FutureResponse> updatePassword(HttpServletRequest httpServletRequest, @RequestBody UpdatePasswordRequest passwordRequest){
        Long id= (Long) httpServletRequest.getAttribute("id");
        userService.updatePassword(id,passwordRequest);

        FutureResponse response=new FutureResponse();
        response.setMessage(ResponseMessage.PASSWORD_CHANGED_MESSAGE);
        response.setSuccess(true);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}/auth")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FutureResponse> deleteUser(@PathVariable Long id){
        userService.removeById(id);

        FutureResponse response=new FutureResponse();
        response.setMessage(ResponseMessage.DELETE_RESPONSE_MESSAGE);
        response.setSuccess(true);

        return ResponseEntity.ok(response);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    public ResponseEntity<FutureResponse> updateUser(HttpServletRequest httpServletRequest,@Valid @RequestBody UserUpdateRequest userUpdateRequest){
        Long id= (Long) httpServletRequest.getAttribute("id");
        userService.updateUser(id,userUpdateRequest);

        FutureResponse response=new FutureResponse();
        response.setMessage(ResponseMessage.UPDATE_RESPONSE_MESSAGE);
        response.setSuccess(true);

        return ResponseEntity.ok(response);
    }
}
