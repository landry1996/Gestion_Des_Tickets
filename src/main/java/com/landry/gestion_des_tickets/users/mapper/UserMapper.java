package com.landry.gestion_des_tickets.users.mapper;

import com.landry.gestion_des_tickets.users.dto.UserDto;
import com.landry.gestion_des_tickets.users.models.Usr;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    Usr mapUserDtoToUser(UserDto userDto);
    UserDto mapUserToUserDto(Usr users);
}
