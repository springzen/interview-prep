package com.imwsoftware.microservice.controller;

import com.imwsoftware.microservice.service.RestClientService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/external")
public class ExternalServiceController {

    private final RestClientService restClientService;
    public ExternalServiceController(RestClientService restClientService) {
        this.restClientService = restClientService;
    }

    @RequestMapping("/call")
    public String callExternalService() {
        return restClientService.callExternalService();
    }
}
