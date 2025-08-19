// FizzBuzzResponse.java
package com.imwsoftware.microservice.model;

import java.util.Map;

public record FizzBuzzResponse(FizzBuzzType fizzBuzzType, int iterationSetMaximum, Map<Integer, String> fizzBuzzMap) {}
