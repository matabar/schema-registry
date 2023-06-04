package matan.schemaregistry.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;


@ControllerAdvice
public class ResponseEntityExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException exception) {
        return ResponseEntity.badRequest()
                .body(extractFirstConstraintMessage(exception)
                        .orElse(exception.getMessage()));
    }

    private static Optional<String> extractFirstConstraintMessage(ConstraintViolationException exception) {
        return Optional.of(exception)
                .map(ConstraintViolationException::getConstraintViolations)
                .map(Collection::stream)
                .flatMap(Stream::findAny)
                .map(ConstraintViolation::getMessage);
    }
}
