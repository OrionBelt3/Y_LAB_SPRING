package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Queue;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    // В очереди храню удаленные id,
    // чтобы их использовать для новых пользователей
    private final Queue<Long> deletedIdUsers = new LinkedList<>();
    private static Long USER_ID = 0L;

    @Override
    public UserDto createUser(UserDto userDto) {
        // сгенерировать идентификатор
        // создать пользователя
        // вернуть сохраненного пользователя со всеми необходимыми полями id
        if (deletedIdUsers.isEmpty()) {
            userDto.setId(++USER_ID);
        } else {
            userDto.setId(deletedIdUsers.peek());
            deletedIdUsers.remove();
        }

        return userDto;
    }

    @Override
    public UserDto updateUser(Long userId, UserDto userDto) {
        userDto.setId(userId);
        return userDto;
    }

    @Override
    public UserDto getUserById(Long id) {
        return null;
    }

    @Override
    public void deleteUserById(Long id) {
        deletedIdUsers.add(id);
    }
}
