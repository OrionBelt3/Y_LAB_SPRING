package com.edu.ulab.app.entity.creator.impl;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.UserEntity;
import com.edu.ulab.app.entity.creator.UserCreator;

public class UserCreatorImpl implements UserCreator {

    @Override
    public UserEntity userDtoToUserEntity(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDto.getId());
        userEntity.setFullName(userDto.getFullName());
        userEntity.setTitle(userDto.getTitle());
        userEntity.setAge(userDto.getAge());

        return userEntity;
    }
}
