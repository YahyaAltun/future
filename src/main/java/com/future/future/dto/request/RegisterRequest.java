package com.future.future.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @Size(max = 40)
    @NotNull(message = "Please provide your first name")
    private String firstName;

    @Size(max = 30)
    @NotNull(message = "Please provide your last name")
    private String lastName;

    @Email(message = "Please provide valid email")
    @Size(min = 5, max = 50)
    @NotNull(message = "Please provide your email")
    private String email;

    @Size(min = 4, max = 20,message="Please Provide Correct Size for Password")
    @NotNull(message = "Please provide your password")
    private String password;

    @Pattern(regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$",
            message = "Please provide valid phone number")
    @Size(min = 14, max = 14)
    @NotNull(message = "Please provide your phone number")
    private String phoneNumber;

    @Size(max = 100)
    @NotNull(message = "Please provide your address")
    private String address;

}
