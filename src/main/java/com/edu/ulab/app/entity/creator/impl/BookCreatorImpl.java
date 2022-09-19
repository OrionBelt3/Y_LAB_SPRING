package com.edu.ulab.app.entity.creator.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.BookEntity;
import com.edu.ulab.app.entity.creator.BookCreator;

public class BookCreatorImpl implements BookCreator {

    @Override
    public BookEntity bookDtoToBookEntity(BookDto bookDto) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(bookDto.getId());
        bookEntity.setUserId(bookDto.getUserId());
        bookEntity.setTitle(bookDto.getTitle());
        bookEntity.setAuthor(bookDto.getAuthor());
        bookEntity.setPageCount(bookDto.getPageCount());

        return bookEntity;
    }
}
