package com.joachim.books.shelf.googlebooks;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GoogleBooksScheduler {

    private final GoogleBooksService googleBooksService;

    @Scheduled(fixedDelay = 120000, initialDelay = 5000)
    public void performGoogleBooksSchedule() {
        googleBooksService.queryGoogleBooks();
    }
}
