package com.future.future.controller;

import com.future.future.dto.request.RegisterRequest;
import com.future.future.dto.response.FutureResponse;
import com.future.future.dto.response.ResponseMessage;
import com.future.future.security.jwt.JwtUtils;
import com.future.future.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class UserJwtController {

    private UserService userService;

    private AuthenticationManager authManager;

    private JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<FutureResponse> register(@Valid @RequestBody RegisterRequest registerRequest){
        userService.register(registerRequest);

        FutureResponse response=new FutureResponse();
        response.setMessage(ResponseMessage.REGISTER_RESPONSE_MESSAGE);
        response.setSuccess(true);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
