package com.edu.ulab.app.dao;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.UserEntity;
import com.edu.ulab.app.entity.creator.UserCreator;
import com.edu.ulab.app.entity.creator.impl.UserCreatorImpl;

import java.util.HashMap;
import java.util.Objects;

public class UserDao {

    private final UserCreator userCreator = new UserCreatorImpl();

    private final HashMap<Long, UserEntity> users = new HashMap<>();

    public HashMap<Long, UserEntity> getUsers() {
        return users;
    }

    public void create(UserDto userDto) {
        UserEntity userEntity = userCreator.userDtoToUserEntity(userDto);
        users.put(userEntity.getId(), userEntity);
    }

    public void update(Long id, UserDto userDto) {
        UserEntity userEntity = userCreator.userDtoToUserEntity(userDto);
        users.replace(id, userEntity);
    }

    public UserEntity getUser(Long id) {
        return users.get(id);
    }

    public void delete(Long id) {
        users.entrySet().removeIf(form -> Objects.equals(form.getKey(), id));
    }
}
