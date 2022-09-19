package com.edu.ulab.app.facade;

import com.edu.ulab.app.dao.BookDao;
import com.edu.ulab.app.dao.FormDao;
import com.edu.ulab.app.dao.UserDao;
import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.service.BookService;
import com.edu.ulab.app.service.UserService;
import com.edu.ulab.app.web.request.UserBookRequest;
import com.edu.ulab.app.web.response.UserBookResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class UserDataFacade {
    private final UserService userService;
    private final BookService bookService;
    private final UserMapper userMapper;
    private final BookMapper bookMapper;

    //Использую паттерн DAO для связи с БД (пока вымышленной)
    private final FormDao formDao = new FormDao();
    private final UserDao userDao = new UserDao();
    private final BookDao bookDao = new BookDao();

    public UserDataFacade(UserService userService,
                          BookService bookService,
                          UserMapper userMapper,
                          BookMapper bookMapper) {
        this.userService = userService;
        this.bookService = bookService;
        this.userMapper = userMapper;
        this.bookMapper = bookMapper;
    }

    public UserBookResponse createUserWithBooks(UserBookRequest userBookRequest) {
        log.info("Got user book create request: {}", userBookRequest);
        UserDto userDto = userMapper.userRequestToUserDto(userBookRequest.getUserRequest());
        log.info("Mapped user request: {}", userDto);

        UserDto createdUser = userService.createUser(userDto);
        log.info("Created user: {}", createdUser);

        userDao.create(createdUser);
        log.info("Created user in Data Base: {}", createdUser);

        List<Long> bookIdList = userBookRequest.getBookRequests()
                .stream()
                .filter(Objects::nonNull)
                .map(bookMapper::bookRequestToBookDto)
                .peek(bookDto -> bookDto.setUserId(createdUser.getId()))
                .peek(mappedBookDto -> log.info("mapped book: {}", mappedBookDto))
                .map(bookService::createBook)
                .peek(createdBook -> log.info("Created book: {}", createdBook))
                .peek(bookDao::create)
                .peek(createdBook -> log.info("Created book in Data Base: {}", createdBook))
                .map(BookDto::getId)
                .toList();
        log.info("Collected book ids: {}", bookIdList);

        formDao.createUserWithBooks(userDto, bookIdList);
        log.info("Create user with books in Data Base: {} {}", userDto, bookIdList);


        return UserBookResponse.builder()
                .userId(createdUser.getId())
                .booksIdList(bookIdList)
                .build();
    }

    public UserBookResponse updateUserWithBooks(Long userId, UserBookRequest userBookRequest) {
        log.info("Got user book update request: {}", userBookRequest);
        UserDto userDto = userMapper.userRequestToUserDto(userBookRequest.getUserRequest());
        log.info("Mapped user request: {}", userDto);

        UserDto updatedUser = userService.updateUser(userId, userDto);
        log.info("Updated user: {}", updatedUser);

        userDao.update(userId, userDto);
        log.info("Updated user in Data Base: {}", updatedUser);

        formDao.getForms().get(userId)
                .stream()
                .filter(Objects::nonNull)
                .peek(bookId -> log.info("Deleted book from Data Base: bookId={}", bookId))
                .peek(bookService::deleteBookById)
                .forEach(bookDao::delete);

        List<Long> bookIdList = userBookRequest.getBookRequests()
                .stream()
                .filter(Objects::nonNull)
                .map(bookMapper::bookRequestToBookDto)
                .peek(bookDto -> bookDto.setUserId(userId))
                .peek(mappedBookDto -> log.info("mapped book: {}", mappedBookDto))
                .map(bookService::createBook)
                .peek(createdBook -> log.info("Created book: {}", createdBook))
                .peek(bookDao::create)
                .peek(createdBook -> log.info("Created book in Data Base: {}", createdBook))
                .map(BookDto::getId)
                .toList();

        formDao.updateUserWithBooks(userId, bookIdList);
        log.info("Updated user with books in Data Base: {} {}", updatedUser, bookIdList);

        return UserBookResponse.builder()
                .userId(userId)
                .booksIdList(bookIdList)
                .build();
    }

    public UserBookResponse getUserWithBooks(Long userId) {
        log.info("Got id of user: {}", userId);
        List<Long> usersBooks = formDao.getUserWithBooks(userId);
        log.info("Got user with his books from Data Base: {}", usersBooks);

        return UserBookResponse.builder()
                .userId(userId)
                .booksIdList(usersBooks)
                .build();
    }

    public void deleteUserWithBooks(Long userId) {
        log.info("Got id of user: {}", userId);

        userDao.delete(userId);
        userService.deleteUserById(userId);
        log.info("Delete user from userDao: userId {}", userId);

        formDao.getForms().get(userId).stream()
                .peek(deletedBook -> log.info("Delete book: bookId {}", deletedBook))
                .peek(bookService::deleteBookById)
                .forEach(bookDao::delete);

        log.info("Delete connection from Data Base: userId={} -> listIdBook={}", userId, formDao.getForms().get(userId));
        formDao.deleteUserWithBooks(userId);
    }
}
