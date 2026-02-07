package com.bookingHotel.utils;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

@Component
public class JwtUtil {
  @Value("${jwt.issuer}")
  private String issuer;

  @Value("${jwt.signing_key_ac}")
  private String signingKeyAC;

  @Value("${jwt.signing_key_rf}")
  private String signingKeyRF;

  @Value("${jwt.expiration_ms_ac}")
  private Long expirationMsAC;

  @Value("${jwt.expiration_ms_rf}")
  private Long expirationMsRF;

  private String generateToken(String email, String role, String signingKey, Long expirationMs) {
    try {
      JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);

      JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
          .subject(email)
          .issuer(issuer)
          .issueTime(new Date())
          .expirationTime(new Date(System.currentTimeMillis() + expirationMs))
          .claim("role", role)
          .build();

      SignedJWT signedJWT = new SignedJWT(header, claimsSet);
      JWSSigner signer = new MACSigner(signingKey);

      signedJWT.sign(signer);
      return signedJWT.serialize();
    } catch (JOSEException e) {
      throw new RuntimeException("Error creating JWT", e);
    }
  }

  private Map<String, Object> validateAndExtract(String token, String signingKey) {
    Map<String, Object> response = new HashMap<>();

    try {
      SignedJWT signedJWT = SignedJWT.parse(token);
      JWSVerifier verifier = new MACVerifier(signingKey);

      boolean isVerified = signedJWT.verify(verifier);
      if (!isVerified) {
        response.put("status", "invalid");
        response.put("data", null);
        return response;
      }

      Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
      if (expirationTime == null) {
        response.put("status", "invalid");
        response.put("data", null);
        return response;
      }

      if (new Date().after(expirationTime)) {
        response.put("status", "expired");
        response.put("data", null);
        return response;
      }

      response.put("status", "success");

      Map<String, String> userData = new HashMap<>();
      userData.put("email", signedJWT.getJWTClaimsSet().getSubject());

      Object role = signedJWT.getJWTClaimsSet().getClaim("role");
      if (role != null) {
        userData.put("role", role.toString());
      }

      response.put("data", userData);
      return response;

    } catch (ParseException | JOSEException e) {
      response.put("status", "invalid");
      response.put("data", null);
      return response;
    }
  }

  public String generateTokenAC(String email, String role) {
    return this.generateToken(email, role, signingKeyAC, expirationMsAC);
  }

  public String generateTokenRF(String email, String role) {
    return this.generateToken(email, role, signingKeyRF, expirationMsRF);
  }

  public Map<String, Object> validateAndExtractAC(String token) {
    return this.validateAndExtract(token, signingKeyAC);
  }

  public Map<String, Object> validateAndExtractRF(String token) {
    return this.validateAndExtract(token, signingKeyRF);
  }
}
