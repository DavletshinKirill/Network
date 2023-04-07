package dev.vorstu.mappers;

import dev.vorstu.db.entities.AuthUserEntity;
import dev.vorstu.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper( UserMapper.class );

    UserDTO toDto(AuthUserEntity user);

    AuthUserEntity toEntity(UserDTO user);

}
