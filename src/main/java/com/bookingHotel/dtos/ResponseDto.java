package com.bookingHotel.dtos;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResponseDto<T> {
  private Integer status;
  private String message;
  private T data;

  public static <T> ResponseEntity<ResponseDto<T>> created(T data) {
    return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.<T>builder()
        .status(201)
        .message("Created successfully")
        .data(data)
        .build());
  }

  public static <T> ResponseEntity<ResponseDto<T>> notFound(String message) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseDto.<T>builder()
        .status(404)
        .message(message)
        .data(null)
        .build());
  }

  public static <T> ResponseEntity<ResponseDto<T>> success(T data) {
    return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.<T>builder()
        .status(200)
        .message("Success")
        .data(data)
        .build());
  }
}
