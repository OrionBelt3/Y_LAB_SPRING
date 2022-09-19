package com.edu.ulab.app.entity.creator;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.UserEntity;

public interface UserCreator {
    UserEntity userDtoToUserEntity (UserDto userDto);
}
