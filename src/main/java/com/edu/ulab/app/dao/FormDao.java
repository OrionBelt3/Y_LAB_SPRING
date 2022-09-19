package com.edu.ulab.app.dao;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.UserEntity;
import com.edu.ulab.app.entity.creator.UserCreator;
import com.edu.ulab.app.entity.creator.impl.UserCreatorImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/*
 * формуляр читателя:
 * один пользователь может иметь много книг,
 * но эти книги не может иметь другой пользователь.
 * По логике устройства библиотеки: книги считаются разными, если разные id
 * (если у двух книг одинаковое название и автор, но разный id, то значит, что книги разные)
 * */
@Slf4j
public class FormDao {

    private final UserCreator userCreator = new UserCreatorImpl();

    private final Map<Long, List<Long>> forms = new HashMap<>();

    public Map<Long, List<Long>> getForms() {
        return forms;
    }

    public void createUserWithBooks(UserDto userDto, List<Long> bookIdList) {
        UserEntity userEntity = userCreator.userDtoToUserEntity(userDto);
        forms.put(userEntity.getId(), bookIdList);
    }

    public void updateUserWithBooks(Long userId, List<Long> bookIdList) {
        forms.replace(userId, bookIdList);
    }

    public List<Long> getUserWithBooks(Long userId) {
        return forms.get(userId);
    }

    public void deleteUserWithBooks(Long userId) {
        forms.entrySet().removeIf(form -> Objects.equals(form.getKey(), userId));
    }
}
