package com.aybu9.aybualumni.core.exception;

import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.core.result.Result;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import javassist.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.Validation;
import java.util.HashMap;

@RestControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

    // TODO: 31.10.2021  https://www.youtube.com/watch?v=PzK4ZXa2Tbc
    // TODO: 31.10.2021  https://www.youtube.com/watch?v=6BKudk8mQm0
    // TODO: 31.10.2021  https://www.youtube.com/watch?v=7HJBE7l7BGs

    private final HttpStatus badRequest = HttpStatus.BAD_REQUEST;

    @Override
    @NonNull
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatus status,
                                                                  @NonNull WebRequest request) {
        var errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            var fieldName = ((FieldError) error).getField();
            var errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        var errorDataResult = new DataResult<>(errors, false, "validation errors");
        errorDataResult.setType("validation");
        errorDataResult.setDetailedType(Validation.class.getSimpleName());
        return new ResponseEntity<>(errorDataResult, badRequest);
    }

    @ExceptionHandler(value = CustomException.class)
    protected ResponseEntity<Object> handleCustom(CustomException ex) {
        var exceptionDetails = new ExceptionDetails(
                ex.getMessage(),
                "exception",
                ex.getClass().getSimpleName()
        );

        return new ResponseEntity<>(exceptionDetails, badRequest);
    }
}
