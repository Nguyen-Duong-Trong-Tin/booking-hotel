package com.bookingHotel.exceptions;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bookingHotel.dtos.ResponseDto;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ResponseDto<Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
    List<String> errors = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(FieldError::getDefaultMessage)
        .toList();

    return ResponseDto.badRequest(errors);
  }

  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<ResponseDto<Object>> handleUnauthorized(UnauthorizedException ex) {
    return ResponseDto.unauthorized();
  }

  @ExceptionHandler(ForbiddenException.class)
  public ResponseEntity<ResponseDto<Object>> handleForbidden(ForbiddenException ex) {
    return ResponseDto.forbidden();
  }
}
