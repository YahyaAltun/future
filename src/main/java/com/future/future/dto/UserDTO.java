package com.future.future.dto;

import com.future.future.domain.Role;
import com.future.future.domain.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String address;

    private Boolean builtIn;

    private Set<String> roles;

    public void setRoles(Set<Role> roles){
        Set<String> rolesStr=new HashSet<>();

        roles.forEach(r->{
            if (r.getName().equals(RoleType.ROLE_ADMIN))
                rolesStr.add("Administrator");
            else
                rolesStr.add("Customer");
        });

        this.roles=rolesStr;
    }
}
