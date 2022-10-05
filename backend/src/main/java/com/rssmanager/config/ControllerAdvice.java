package com.rssmanager.config;

import com.rssmanager.exception.BadRequestException;
import com.rssmanager.exception.NotFoundException;
import com.rssmanager.exception.UnauthorizedException;
import com.rssmanager.exception.dto.ExceptionResponse;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
    private final Logger logger = LoggerFactory.getLogger(ControllerAdvice.class);

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> badRequest(final BadRequestException e) {
        logger.error("BadRequestException : ", e);
        return ResponseEntity.badRequest().body(new ExceptionResponse(e.getMessage()));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ExceptionResponse> unauthorized(final UnauthorizedException e) {
        logger.error("UnauthorizedException : ", e);
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ExceptionResponse> notFound(final NotFoundException e) {
        logger.error("NotFoundException : ", e);
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> runtime(final RuntimeException e) {
        logger.error("RuntimeException : ", e);
        return ResponseEntity.badRequest().body(new ExceptionResponse("확인되지 않은 오류가 발생했어요.. 잠시 후에 다시 시도해주시겠어요? 🥲"));
    }

    @ExceptionHandler({BindException.class})
    public ResponseEntity<ExceptionResponse> requestValidationException(final BindException e) {
        logger.error("BindException: ", e);
        final String errorMessage = parseErrorMessage(e);

        return new ResponseEntity<>(new ExceptionResponse(errorMessage), HttpStatus.BAD_REQUEST);
    }

    private String parseErrorMessage(final BindException e) {
        return e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> ((FieldError) error).getField() + " : " + error.getDefaultMessage())
                .collect(Collectors.joining(System.lineSeparator()));
    }
}

