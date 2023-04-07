package com.future.future.dto.mapper;

import com.future.future.domain.User;
import com.future.future.dto.request.RegisterRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id",ignore = true)
    @Mapping(target = "builtIn",ignore = true)
    @Mapping(target = "password",ignore = true)
    User registerRequestToUser(RegisterRequest registerRequest);
}
