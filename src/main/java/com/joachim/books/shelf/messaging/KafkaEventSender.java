package com.joachim.books.shelf.messaging;

import com.joachim.books.shelf.model.Book;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaEventSender {

    private final KafkaTemplate<String, Book> kafkaTemplate;

    @Value("${spring.kafka.topic.book-added-event}")
    private String topic;

    @EventListener
    public void onNewBookAdded(NewBookAddedEvent event) {
        Book book = (Book) event.getSource();
        log.info("sending payload='{}' to topic='{}'", book, topic);
        kafkaTemplate.send(topic, book);
    }

    @KafkaListener(topics = "${spring.kafka.topic.book-added-event}")
    public void receiveBookLendEvent(Book addedBook) {
        log.info("received payload='{}'", addedBook);
    }
}
