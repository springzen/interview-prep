package com.imwsoftware.exception;

import com.imwsoftware.model.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class RestErrorController implements ErrorController {

    private final ErrorAttributes errorAttributes;

    public RestErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping("/error")
    public ResponseEntity<ApiErrorResponse> handleError(HttpServletRequest request) {
        var webRequest = new org.springframework.web.context.request.ServletWebRequest(request);
        var attrs = errorAttributes.getErrorAttributes(
                webRequest, ErrorAttributeOptions.defaults()
        );

        String path = (String) attrs.getOrDefault("path", request.getRequestURI());
        int status = (int) attrs.getOrDefault("status", 500);
        String error = (String) attrs.getOrDefault("error", "Internal Server Error");
        String message = (String) attrs.getOrDefault("message", "Unexpected error");

        ApiErrorResponse response = new ApiErrorResponse(
                error,
                message,
                status,
                path
        );

        return new ResponseEntity<>(response, HttpStatus.valueOf(status));
    }
}