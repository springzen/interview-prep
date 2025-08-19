package com.imwsoftware.microservice.controller;

import com.imwsoftware.microservice.service.FizzBuzzService;
import com.imwsoftware.microservice.model.FizzBuzzResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/fizzbuzz")
public class FizzBuzzController {
    private final FizzBuzzService fizzBuzzService;

    public FizzBuzzController(FizzBuzzService fizzBuzzService) {
        this.fizzBuzzService = fizzBuzzService;
    }

    @GetMapping("/classic")
    public FizzBuzzResponse fizzBuzzClassic() {
        return fizzBuzzService.fizzBuzzClassic(100);
    }

    @GetMapping("/stream")
    public FizzBuzzResponse fizzBuzzStream() {
        return fizzBuzzService.fizzBuzzStream(100);
    }
}
