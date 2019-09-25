package com.joachim.books.shelf;

import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @GetMapping
    public HelloDto greeting() {
        HelloDto helloDto = new HelloDto();
        helloDto.setName("Hoi Nik :-)");
        return helloDto;
    }
}
