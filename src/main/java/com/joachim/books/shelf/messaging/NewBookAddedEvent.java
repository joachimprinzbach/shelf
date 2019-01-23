package com.joachim.books.shelf.messaging;

import com.joachim.books.shelf.model.Book;
import org.springframework.context.ApplicationEvent;

public class NewBookAddedEvent extends ApplicationEvent {

    public NewBookAddedEvent(Book source) {
        super(source);
    }

}
