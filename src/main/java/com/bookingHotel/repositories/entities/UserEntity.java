package com.bookingHotel.repositories.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

  @Column(name = "fullName", nullable = false, length = 100)
  private String fullName;

  @Column(nullable = false, unique = true, length = 100)
  private String email;

  @Column(nullable = true, unique = true, length = 15)
  private String phone;

  @Column(nullable = false)
  private String password;

  @ManyToOne
  @JoinColumn(name = "roleId", nullable = false)
  private RoleEntity role;

  @OneToMany(mappedBy = "user")
  private List<BookingEntity> bookings;
}
