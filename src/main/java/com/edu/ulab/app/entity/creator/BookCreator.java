package com.edu.ulab.app.entity.creator;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.BookEntity;

public interface BookCreator {
    BookEntity bookDtoToBookEntity(BookDto bookDto);
}
