package com.bookingHotel.services.impls;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bookingHotel.converters.AuthConverter;
import com.bookingHotel.converters.UserConverter;
import com.bookingHotel.dtos.ResponseDto;
import com.bookingHotel.dtos.auths.AuthLoginDto;
import com.bookingHotel.dtos.auths.AuthLoginResponseDto;
import com.bookingHotel.dtos.auths.AuthGoogleResponseDto;
import com.bookingHotel.dtos.auths.AuthRefreshTokenDto;
import com.bookingHotel.dtos.auths.AuthRefreshTokenResponseDto;
import com.bookingHotel.dtos.auths.AuthRegisterDto;
import com.bookingHotel.dtos.auths.AuthRegisterResponseDto;
import com.bookingHotel.repositories.RoleRepository;
import com.bookingHotel.repositories.UserRepository;
import com.bookingHotel.repositories.entities.RoleEntity;
import com.bookingHotel.repositories.entities.UserEntity;
import com.bookingHotel.services.AuthService;
import com.bookingHotel.utils.JwtUtil;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthServiceImpl implements AuthService {
  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private AuthConverter authConverter;

  @Autowired
  private UserConverter userConverter;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private UserRepository userRepository;

  @Value("${google.oauth.client_id:}")
  private String googleClientId;

  @Value("${google.oauth.client_secret:}")
  private String googleClientSecret;

  @Value("${google.oauth.redirect_uri:}")
  private String googleRedirectUri;

  @Value("${google.oauth.frontend_redirect:}")
  private String googleFrontendRedirect;

  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
  private static final String GOOGLE_AUTH_URL = "https://accounts.google.com/o/oauth2/v2/auth";
  private static final String GOOGLE_TOKEN_URL = "https://oauth2.googleapis.com/token";

  @Override
  public ResponseEntity<ResponseDto<AuthRegisterResponseDto>> register(AuthRegisterDto authRegisterDto) {
    Boolean existsByEmail = this.userRepository.existsByEmail(authRegisterDto.getEmail());
    if (existsByEmail) {
      return ResponseDto.badRequest(List.of("Email already exists"));
    }

    Boolean existsByPhone = this.userRepository.existsByPhone(authRegisterDto.getPhone());
    if (existsByPhone) {
      return ResponseDto.badRequest(List.of("Phone already exists"));
    }

    UserEntity userEntity = this.authConverter.toUserEntity(authRegisterDto);

    String encodedPassword = passwordEncoder.encode(authRegisterDto.getPassword());
    userEntity.setPassword(encodedPassword);

    RoleEntity roleEntity = this.roleRepository.findByName("User").orElse(null);
    if (roleEntity == null) {
      return ResponseDto.badRequest(List.of("Role not found"));
    }
    userEntity.setRole(roleEntity);

    userEntity = this.userRepository.save(userEntity);
    AuthRegisterResponseDto authRegisterResponseDto = this.authConverter.toAuthRegisterResponseDto(userEntity);
    return ResponseDto.created(authRegisterResponseDto);
  }

  @Override
  public ResponseEntity<ResponseDto<AuthLoginResponseDto>> login(AuthLoginDto authLoginDto) {
    UserEntity userEntity = this.userRepository.findByEmail(authLoginDto.getEmail()).orElse(null);
    if (userEntity == null) {
      return ResponseDto.notFound("Email or password wrong");
    }

    boolean isMatch = passwordEncoder.matches(authLoginDto.getPassword(), userEntity.getPassword());
    if (!isMatch) {
      return ResponseDto.notFound("Phone or password wrong");
    }

    String accessToken = this.jwtUtil.generateTokenAC(userEntity.getEmail(), userEntity.getRole().getName());
    String refreshToken = this.jwtUtil.generateTokenRF(userEntity.getEmail(), userEntity.getRole().getName());

    AuthLoginResponseDto userLoginResponseDto = AuthLoginResponseDto.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .build();
    return ResponseDto.success(userLoginResponseDto);
  }

  @Override
  public String buildGoogleAuthorizeUrl() {
    if (googleClientId == null || googleClientId.isBlank() || googleRedirectUri == null
        || googleRedirectUri.isBlank()) {
      throw new IllegalStateException("Google OAuth config is missing");
    }

    StringBuilder url = new StringBuilder(GOOGLE_AUTH_URL);
    url.append("?client_id=").append(encode(googleClientId));
    url.append("&redirect_uri=").append(encode(googleRedirectUri));
    url.append("&response_type=code");
    url.append("&scope=").append(encode("openid email profile"));
    url.append("&access_type=offline");
    url.append("&prompt=consent");
    return url.toString();
  }

  @Override
  public AuthGoogleResponseDto handleGoogleCallback(String code) {
    if (googleClientId == null || googleClientId.isBlank() || googleClientSecret == null
        || googleClientSecret.isBlank() || googleRedirectUri == null || googleRedirectUri.isBlank()) {
      throw new IllegalStateException("Google OAuth config is missing");
    }

    Map<String, Object> tokenResponse = exchangeCodeForToken(code);
    String idTokenValue = tokenResponse.get("id_token") != null ? tokenResponse.get("id_token").toString() : null;

    if (idTokenValue == null || idTokenValue.isBlank()) {
      throw new IllegalStateException("Google token exchange failed");
    }

    GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), JSON_FACTORY)
        .setAudience(List.of(googleClientId))
        .build();

    GoogleIdToken idToken;
    try {
      idToken = verifier.verify(idTokenValue);
    } catch (Exception ex) {
      idToken = null;
    }

    if (idToken == null) {
      throw new IllegalStateException("Invalid Google id token");
    }

    GoogleIdToken.Payload payload = idToken.getPayload();
    String email = payload.getEmail();
    String fullName = payload.get("name") != null ? payload.get("name").toString() : "User";

    if (email == null || email.isBlank()) {
      throw new IllegalStateException("Google account email is missing");
    }

    UserEntity userEntity = this.userRepository.findByEmail(email).orElse(null);
    if (userEntity == null) {
      RoleEntity roleEntity = this.roleRepository.findByName("User").orElse(null);
      if (roleEntity == null) {
        throw new IllegalStateException("Role not found");
      }

      userEntity = UserEntity.builder()
          .fullName(fullName)
          .email(email)
          .phone(null)
          .password(passwordEncoder.encode(UUID.randomUUID().toString()))
          .role(roleEntity)
          .build();

      userEntity = this.userRepository.save(userEntity);
    }

    String accessToken = this.jwtUtil.generateTokenAC(userEntity.getEmail(), userEntity.getRole().getName());
    String refreshToken = this.jwtUtil.generateTokenRF(userEntity.getEmail(), userEntity.getRole().getName());
    boolean needsProfile = userEntity.getPhone() == null || userEntity.getPhone().isBlank();

    return AuthGoogleResponseDto.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .needsProfile(needsProfile)
        .user(this.userConverter.toUserResponseDto(userEntity))
        .build();
  }

  @Override
  public String buildFrontendRedirect(AuthGoogleResponseDto authResponse) {
    String base = googleFrontendRedirect != null && !googleFrontendRedirect.isBlank()
        ? googleFrontendRedirect
        : "http://localhost:5173/google/callback";

    StringBuilder url = new StringBuilder(base);
    url.append("?accessToken=").append(encode(authResponse.getAccessToken()));
    url.append("&refreshToken=").append(encode(authResponse.getRefreshToken()));
    url.append("&needsProfile=").append(authResponse.getNeedsProfile() != null && authResponse.getNeedsProfile()
        ? "true"
        : "false");
    return url.toString();
  }

  @Override
  public String buildFrontendErrorRedirect(String error) {
    String base = googleFrontendRedirect != null && !googleFrontendRedirect.isBlank()
        ? googleFrontendRedirect
        : "http://localhost:5173/google/callback";
    return base + "?error=" + encode(error);
  }

  private Map<String, Object> exchangeCodeForToken(String code) {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
    form.add("code", code);
    form.add("client_id", googleClientId);
    form.add("client_secret", googleClientSecret);
    form.add("redirect_uri", googleRedirectUri);
    form.add("grant_type", "authorization_code");

    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);
    Map<String, Object> response = restTemplate.postForObject(GOOGLE_TOKEN_URL, request, Map.class);
    return response != null ? response : new HashMap<>();
  }

  private String encode(String value) {
    return URLEncoder.encode(value, StandardCharsets.UTF_8);
  }

  @Override
  public ResponseEntity<ResponseDto<AuthRefreshTokenResponseDto>> refreshToken(
      AuthRefreshTokenDto authRefreshTokenDto) {
    String accessToken = authRefreshTokenDto.getAccessToken();
    String refreshToken = authRefreshTokenDto.getRefreshToken();

    Map<String, Object> validationStatusAC = jwtUtil.validateAndExtractAC(accessToken);
    if (validationStatusAC.get("status") == "invalid") {
      return ResponseDto.unauthorized();
    }

    Map<String, Object> validationStatusRF = jwtUtil.validateAndExtractRF(refreshToken);
    if (validationStatusRF.get("status") != "success") {
      return ResponseDto.unauthorized();
    }

    String email = (String) validationStatusRF.get("email");
    String role = (String) validationStatusRF.get("role");

    String newAccessToken = this.jwtUtil.generateTokenAC(email, role);
    return ResponseDto.success(AuthRefreshTokenResponseDto.builder()
        .accessToken(newAccessToken)
        .build());
  }
}
