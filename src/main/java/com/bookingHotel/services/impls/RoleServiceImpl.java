package com.bookingHotel.services.impls;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bookingHotel.converters.RoleConverter;
import com.bookingHotel.dtos.ResponseDto;
import com.bookingHotel.dtos.ResponseSpecification;
import com.bookingHotel.dtos.roles.RoleCreateDto;
import com.bookingHotel.dtos.roles.RoleFindDto;
import com.bookingHotel.dtos.roles.RoleResponseDto;
import com.bookingHotel.dtos.roles.RoleUpdateDto;
import com.bookingHotel.repositories.RoleRepository;
import com.bookingHotel.repositories.entities.RoleEntity;
import com.bookingHotel.services.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
  @Autowired
  private RoleConverter roleConverter;

  @Autowired
  private RoleRepository roleRepository;

  @Override
  public ResponseEntity<ResponseDto<RoleResponseDto>> create(RoleCreateDto roleCreateDto) {
    RoleEntity roleEntity = this.roleConverter.toRoleEntity(roleCreateDto);

    roleEntity = this.roleRepository.save(roleEntity);

    RoleResponseDto roleResponseDto = this.roleConverter.toRoleResponseDto(roleEntity);

    return ResponseDto.success(roleResponseDto);
  }

  @Override
  public ResponseEntity<ResponseDto<RoleResponseDto>> update(Long id, RoleUpdateDto roleUpdateDto) {
    RoleEntity roleEntity = this.roleRepository.findById(id).orElse(null);

    if (roleEntity == null) {
      return ResponseDto.notFound("Role not found");
    }

    this.roleConverter.copyToRoleEntity(roleUpdateDto, roleEntity);
    roleEntity = this.roleRepository.save(roleEntity);

    RoleResponseDto roleResponseDto = this.roleConverter.toRoleResponseDto(roleEntity);
    return ResponseDto.success(roleResponseDto);
  }

  @Override
  public ResponseEntity<ResponseDto<Object>> delete(Long id) {
    RoleEntity roleEntity = this.roleRepository.findById(id).orElse(null);

    if (roleEntity == null) {
      return ResponseDto.notFound("Role not found");
    }

    this.roleRepository.delete(roleEntity);
    return ResponseDto.success(null);
  }

  @Override
  public ResponseEntity<ResponseDto<ResponseSpecification<RoleResponseDto>>> find(RoleFindDto query,
      Pageable pageable) {
    Specification<RoleEntity> roleSpec = this.roleRepository.hasCriteria(query);

    Page<RoleEntity> rolePage = this.roleRepository.findAll(roleSpec, pageable);

    int page = rolePage.getNumber();
    int size = rolePage.getSize();
    int totalPages = rolePage.getTotalPages();
    List<RoleEntity> items = rolePage.getContent();

    List<RoleResponseDto> roleResponseDtos = items.stream()
        .map(item -> this.roleConverter.toRoleResponseDto(item)).toList();
    ResponseSpecification<RoleResponseDto> roleResponseSpecification = ResponseSpecification
        .<RoleResponseDto>builder()
        .page(page)
        .size(size)
        .totalPages(totalPages)
        .items(roleResponseDtos)
        .build();
    return ResponseDto.success(roleResponseSpecification);
  }

  @Override
  public ResponseEntity<ResponseDto<RoleResponseDto>> findById(Long id) {
    RoleEntity roleEntity = this.roleRepository.findById(id).orElse(null);
    if (roleEntity == null) {
      return ResponseDto.notFound("Role not found");
    }

    RoleResponseDto roleResponseDto = this.roleConverter.toRoleResponseDto(roleEntity);
    return ResponseDto.success(roleResponseDto);
  }
}
