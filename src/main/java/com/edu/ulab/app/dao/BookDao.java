package com.edu.ulab.app.dao;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.BookEntity;
import com.edu.ulab.app.entity.creator.BookCreator;
import com.edu.ulab.app.entity.creator.impl.BookCreatorImpl;

import java.util.HashMap;
import java.util.Objects;


public class BookDao {

    private final BookCreator bookCreator = new BookCreatorImpl();

    private final HashMap<Long, BookEntity> books = new HashMap<>();

    public HashMap<Long, BookEntity> getBooks() {
        return books;
    }

    public void create(BookDto bookDto) {
        BookEntity bookEntity = bookCreator.bookDtoToBookEntity(bookDto);
        books.put(bookEntity.getId(), bookEntity);
    }

    public void update(Long id, BookEntity book) {
        books.replace(id, book);
    }

    public BookEntity getBook(Long id) {
        return books.get(id);
    }

    public void delete(Long id) {
        books.entrySet().removeIf(form -> Objects.equals(form.getKey(), id));
    }

}
