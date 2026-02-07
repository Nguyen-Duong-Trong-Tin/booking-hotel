package com.bookingHotel.repositories.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "fullName", length = 100, nullable = false)
  private String fullName;

  @Column(name = "email", length = 100, nullable = false, unique = true)
  private String email;

  @Column(name = "phone", length = 10, nullable = false, unique = true)
  private String phone;

  @Column(name = "password", length = 255, nullable = false)
  private String password;

  @JoinColumn(name = "roleId", nullable = false)
  @ManyToOne()
  private RoleEntity role;
}
