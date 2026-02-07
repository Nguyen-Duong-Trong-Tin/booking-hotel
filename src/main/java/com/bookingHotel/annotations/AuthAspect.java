package com.bookingHotel.annotations;

import java.util.Arrays;
import java.util.Map;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bookingHotel.exceptions.ForbiddenException;
import com.bookingHotel.exceptions.UnauthorizedException;
import com.bookingHotel.utils.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

@Aspect
@Component
public class AuthAspect {

  @Autowired
  private HttpServletRequest request;

  @Autowired
  private JwtUtil jwtUtil;

  @Before("@annotation(auth)")
  public void checkAuth(Auth auth) {
    String authHeader = request.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      throw new UnauthorizedException();
    }

    String token = authHeader.substring(7);

    Map<String, Object> validation = jwtUtil.validateAndExtractAC(token);
    System.out.println(validation);
    String status = (String) validation.get("status");
    if (status != "success") {
      throw new UnauthorizedException();
    }

    Map<String, String> data = (Map<String, String>) validation.get("data");
    String role = (String) data.get("role");
    String requiredRoles[] = auth.value();

    if (requiredRoles.length > 0) {
      boolean hasRole = Arrays.asList(requiredRoles).contains(role);
      if (!hasRole) {
        throw new ForbiddenException();
      }
    }
  }
}
