package com.imwsoftware.microservice.service;

import com.imwsoftware.microservice.model.FizzBuzzResponse;
import com.imwsoftware.microservice.model.FizzBuzzType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
public class FizzBuzzService {

    private static final int ITERATION_SET_MAXIMUM = 100;

    public FizzBuzzResponse fizzBuzzStream(int number) {
        int limit = (number > 0) ? number : ITERATION_SET_MAXIMUM;

        Map<Integer, String> fizzBuzzMap = IntStream.rangeClosed(1, limit)
                .boxed()
                .collect(Collectors.toMap(
                        i -> i,
                        i -> i % 15 == 0 ? "FizzBuzz"
                                : i % 3 == 0 ? "Fizz"
                                : i % 5 == 0 ? "Buzz"
                                : String.valueOf(i),
                        (a, b) -> a,
                        LinkedHashMap::new
                ));

        // Logging (optional)
        fizzBuzzMap.values().forEach(log::info);

        return new FizzBuzzResponse(FizzBuzzType.FIZZBUZZ_STREAM, limit, fizzBuzzMap);
    }
    public FizzBuzzResponse fizzBuzzClassic(int number) {
        if (number <= 0) {
            number = 100;
        }

        for (int i = 1; i <= number; i++) {
            if (i % 15 == 0) {
                log.info("FizzBuzz");
            } else if (i % 3 == 0) {
                log.info("Fizz");
            } else if (i % 5 == 0) {
                log.info("Buzz");
            } else {
                log.info("{}", i);
            }
        }
        return new FizzBuzzResponse(FizzBuzzType.FIZZBUZZ_CLASSIC, number, new HashMap<>());
    }
}
