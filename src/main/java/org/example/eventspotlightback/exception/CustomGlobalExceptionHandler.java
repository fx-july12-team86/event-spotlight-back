package org.example.eventspotlightback.exception;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomGlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationError>> handleValidationExceptions(
            MethodArgumentNotValidException ex
    ) {
        List<ValidationError> errors = ex.getBindingResult()
                .getAllErrors().stream()
                .map(error -> {
                    String field =
                            error instanceof FieldError ? ((FieldError) error)
                                    .getField() : "Unknown Field";
                    String message = error.getDefaultMessage();
                    return new ValidationError(field, message);
                })
                .collect(Collectors.toList());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception ex) {
        return new ResponseEntity<>(
                "An unexpected error occurred\n" + ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(NullPointerException ex) {
        return new ResponseEntity<>(
                "Null pointer exception occurred",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
