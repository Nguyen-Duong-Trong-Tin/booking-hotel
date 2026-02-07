package com.bookingHotel.services.impls;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bookingHotel.converters.UserConverter;
import com.bookingHotel.dtos.ResponseDto;
import com.bookingHotel.dtos.ResponseSpecification;
import com.bookingHotel.dtos.users.UserCreateDto;
import com.bookingHotel.dtos.users.UserFindDto;
import com.bookingHotel.dtos.users.UserResponseDto;
import com.bookingHotel.dtos.users.UserUpdateDto;
import com.bookingHotel.repositories.RoleRepository;
import com.bookingHotel.repositories.UserRepository;
import com.bookingHotel.repositories.entities.RoleEntity;
import com.bookingHotel.repositories.entities.UserEntity;
import com.bookingHotel.services.UserService;
import com.bookingHotel.utils.ValidationUtil;

@Service
public class UserServiceImpl implements UserService {
  @Autowired
  private UserConverter userConverter;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public ResponseEntity<ResponseDto<UserResponseDto>> create(UserCreateDto body) {
    if (this.userRepository.existsByEmail(body.getEmail())) {
      return ResponseDto.badRequest(List.of("Email already exists"));
    }

    if (this.userRepository.existsByPhone(body.getPhone())) {
      return ResponseDto.badRequest(List.of("Phone already exists"));
    }

    RoleEntity roleEntity = this.roleRepository.findById(body.getRoleId()).orElse(null);
    if (roleEntity == null) {
      return ResponseDto.badRequest(List.of("Role not found"));
    }

    UserEntity userEntity = this.userConverter.toUserEntity(body);
    userEntity.setPassword(passwordEncoder.encode(body.getPassword()));
    userEntity.setRole(roleEntity);

    userEntity = this.userRepository.save(userEntity);

    UserResponseDto userResponseDto = this.userConverter.toUserResponseDto(userEntity);
    return ResponseDto.created(userResponseDto);
  }

  @Override
  public ResponseEntity<ResponseDto<UserResponseDto>> update(Long id, UserUpdateDto body) {
    UserEntity userEntity = this.userRepository.findById(id).orElse(null);
    if (userEntity == null) {
      return ResponseDto.notFound("User not found");
    }

    if (body.getEmail() != null && !body.getEmail().equals(userEntity.getEmail())) {
      if (this.userRepository.existsByEmail(body.getEmail())) {
        return ResponseDto.badRequest(List.of("Email already exists"));
      }
    }

    if (body.getPhone() != null && !body.getPhone().equals(userEntity.getPhone())) {
      if (this.userRepository.existsByPhone(body.getPhone())) {
        return ResponseDto.badRequest(List.of("Phone already exists"));
      }
    }

    if (body.getRoleId() != null) {
      RoleEntity roleEntity = this.roleRepository.findById(body.getRoleId()).orElse(null);
      if (roleEntity == null) {
        return ResponseDto.badRequest(List.of("Role not found"));
      }
      userEntity.setRole(roleEntity);
    }

    if (!ValidationUtil.isNullOrEmpty(body.getPassword())) {
      userEntity.setPassword(passwordEncoder.encode(body.getPassword()));
    }

    this.userConverter.copyToUserEntity(body, userEntity);

    userEntity = this.userRepository.save(userEntity);
    UserResponseDto userResponseDto = this.userConverter.toUserResponseDto(userEntity);
    return ResponseDto.success(userResponseDto);
  }

  @Override
  public ResponseEntity<ResponseDto<Object>> delete(Long id) {
    UserEntity userEntity = this.userRepository.findById(id).orElse(null);
    if (userEntity == null) {
      return ResponseDto.notFound("User not found");
    }

    this.userRepository.delete(userEntity);
    return ResponseDto.success(null);
  }

  @Override
  public ResponseEntity<ResponseDto<ResponseSpecification<UserResponseDto>>> find(UserFindDto query,
      Pageable pageable) {
    Specification<UserEntity> userSpec = this.userRepository.hasCriteria(query);

    Page<UserEntity> userPage = this.userRepository.findAll(userSpec, pageable);

    int page = userPage.getNumber();
    int size = userPage.getSize();
    int totalPages = userPage.getTotalPages();
    List<UserEntity> items = userPage.getContent();

    List<UserResponseDto> userResponseDtos = items.stream()
        .map(item -> this.userConverter.toUserResponseDto(item)).toList();
    ResponseSpecification<UserResponseDto> responseSpecification = ResponseSpecification
        .<UserResponseDto>builder()
        .page(page)
        .size(size)
        .totalPages(totalPages)
        .items(userResponseDtos)
        .build();
    return ResponseDto.success(responseSpecification);
  }

  @Override
  public ResponseEntity<ResponseDto<UserResponseDto>> findById(Long id) {
    UserEntity userEntity = this.userRepository.findById(id).orElse(null);
    if (userEntity == null) {
      return ResponseDto.notFound("User not found");
    }

    UserResponseDto userResponseDto = this.userConverter.toUserResponseDto(userEntity);
    return ResponseDto.success(userResponseDto);
  }
}
