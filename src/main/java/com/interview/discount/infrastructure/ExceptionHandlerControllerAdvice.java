package com.interview.discount.infrastructure;

import com.interview.discount.domain.exception.DiscountAlreadyExistsException;
import com.interview.discount.domain.exception.DiscountAlreadyUsed;
import com.interview.discount.domain.exception.DiscountNotEntitledInUserCountryException;
import com.interview.discount.domain.exception.DiscountNotFoundException;
import com.interview.discount.domain.exception.DiscountUsageLimitReachedException;
import com.interview.discount.domain.exception.NonResolvableIpException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleAccountNotFound(ConstraintViolationException exception) {
        return ResponseEntity.badRequest()
                .body(exception.getMessage());
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<Object> handleMissingRequestHeaderException(MissingRequestHeaderException exception) {
        return ResponseEntity.badRequest()
                .body(exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        String errors = exception.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return ResponseEntity.badRequest()
                .body(errors);
    }

    @ExceptionHandler({DiscountAlreadyExistsException.class})
    public ResponseEntity<Object> handleDiscountAlreadyExistsException(DiscountAlreadyExistsException exception) {
        return ResponseEntity.badRequest()
                .body(exception.getMessage());
    }

    @ExceptionHandler({DiscountAlreadyUsed.class})
    public ResponseEntity<Object> handleDiscountAlreadyUsed(DiscountAlreadyUsed exception) {
        return ResponseEntity.badRequest()
                .body(exception.getMessage());
    }

    @ExceptionHandler({DiscountNotEntitledInUserCountryException.class})
    public ResponseEntity<Object> handleDiscountNotEntitledInCountryException(DiscountNotEntitledInUserCountryException exception) {
        return ResponseEntity.badRequest()
                .body(exception.getMessage());
    }

    @ExceptionHandler({DiscountNotFoundException.class})
    public ResponseEntity<Object> handleDiscountNotFoundException(DiscountNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler({DiscountUsageLimitReachedException.class})
    public ResponseEntity<Object> handleDiscountUsageLimitReachedException(DiscountUsageLimitReachedException exception) {
        return ResponseEntity.badRequest()
                .body(exception.getMessage());
    }

    @ExceptionHandler({NonResolvableIpException.class})
    public ResponseEntity<Object> handleNonResolvableIpException(NonResolvableIpException exception) {
        return ResponseEntity.badRequest()
                .body(exception.getMessage());
    }
}
