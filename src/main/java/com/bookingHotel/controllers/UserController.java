package com.bookingHotel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookingHotel.annotations.Auth;
import com.bookingHotel.dtos.ResponseDto;
import com.bookingHotel.dtos.ResponseSpecification;
import com.bookingHotel.dtos.users.UserCreateDto;
import com.bookingHotel.dtos.users.UserFindDto;
import com.bookingHotel.dtos.users.UserResponseDto;
import com.bookingHotel.dtos.users.UserUpdateDto;
import com.bookingHotel.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("v1/users")
public class UserController {
	@Autowired
	private UserService userService;

	@PostMapping
	@Auth({ "Admin" })
	public ResponseEntity<ResponseDto<UserResponseDto>> create(@Valid @RequestBody UserCreateDto body) {
		return this.userService.create(body);
	}

	@PatchMapping("/{id}")
	@Auth({ "Admin" })
	public ResponseEntity<ResponseDto<UserResponseDto>> update(@PathVariable Long id,
			@Valid @RequestBody UserUpdateDto body) {
		return this.userService.update(id, body);
	}

	@DeleteMapping("/{id}")
	@Auth({ "Admin" })
	public ResponseEntity<ResponseDto<Object>> delete(@PathVariable Long id) {
		return this.userService.delete(id);
	}

	@GetMapping
	public ResponseEntity<ResponseDto<ResponseSpecification<UserResponseDto>>> find(UserFindDto query,
			Pageable pageable) {
		return this.userService.find(query, pageable);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponseDto<UserResponseDto>> findById(@PathVariable Long id) {
		return this.userService.findById(id);
	}
}
