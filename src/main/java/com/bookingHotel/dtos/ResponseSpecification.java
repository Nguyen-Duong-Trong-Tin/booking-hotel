package com.bookingHotel.dtos;

import java.util.List;

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
public class ResponseSpecification <T> {
  private int page;
  private int size;
  private int totalPages;
  private List<T> items;
}
