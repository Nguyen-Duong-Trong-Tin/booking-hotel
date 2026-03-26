package com.bookingHotel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
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
import com.bookingHotel.dtos.users.UserProfileUpdateDto;
import com.bookingHotel.dtos.users.UserResponseDto;
import com.bookingHotel.dtos.users.UserUpdateDto;
import com.bookingHotel.services.UserService;
import com.bookingHotel.utils.JwtUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("v1/users")
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private jakarta.servlet.http.HttpServletRequest request;

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

	@PatchMapping("/me")
	@Auth({ "User", "Admin" })
	public ResponseEntity<ResponseDto<UserResponseDto>> updateProfile(
			@Valid @RequestBody UserProfileUpdateDto body) {
		String authHeader = request.getHeader("Authorization");
		String token = authHeader != null && authHeader.startsWith("Bearer ")
				? authHeader.substring(7)
				: null;
		if (token == null) {
			return ResponseDto.unauthorized();
		}
		java.util.Map<String, Object> validation = jwtUtil.validateAndExtractAC(token);
		String status = (String) validation.get("status");
		if (!"success".equals(status)) {
			return ResponseDto.unauthorized();
		}
		Object dataObj = validation.get("data");
		String email = null;
		if (dataObj instanceof java.util.Map<?, ?> dataMap) {
			Object emailObj = dataMap.get("email");
			if (emailObj != null) {
				email = emailObj.toString();
			}
		}
		return this.userService.updateProfile(email, body);
	}

	@DeleteMapping("/{id}")
	@Auth({ "Admin" })
	public ResponseEntity<ResponseDto<Object>> delete(@PathVariable Long id) {
		return this.userService.delete(id);
	}

	@GetMapping
	public ResponseEntity<ResponseDto<ResponseSpecification<UserResponseDto>>> find(UserFindDto query,
			@PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable) {
		return this.userService.find(query, pageable);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponseDto<UserResponseDto>> findById(@PathVariable Long id) {
		return this.userService.findById(id);
	}
}
