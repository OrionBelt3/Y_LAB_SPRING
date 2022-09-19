package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Queue;

@Slf4j
@Service
public class BookServiceImpl implements BookService {

    // В очереди храню удаленные id,
    // чтобы их использовать для новых пользователей,
    // уменьшая вероятноть переполнения
    private final Queue<Long> deletedIdBooks = new LinkedList<>();
    private static Long BOOK_ID = 0L;

    @Override
    public BookDto createBook(BookDto bookDto) {
        if (deletedIdBooks.isEmpty()) {
            bookDto.setId(++BOOK_ID);
        } else {
            bookDto.setId(deletedIdBooks.peek());
            deletedIdBooks.remove();
        }

        return bookDto;
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {
        return null;
    }

    @Override
    public BookDto getBookById(Long id) {
        return null;
    }

    @Override
    public void deleteBookById(Long id) {
        deletedIdBooks.add(id);
    }
}
