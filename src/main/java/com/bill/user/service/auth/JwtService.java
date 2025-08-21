package com.bill.user.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import com.bill.user.service.auth.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

	@Value("${jwt.secret-key}")
	private String secretKey;

	public String generateToken(CustomUserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("userId", userDetails.getUserId());
		return buildToken(claims, userDetails.getUsername());
	}

	private String buildToken(Map<String, Object> claims, String subject) {
		return Jwts.builder()
				.claims(claims)
				.subject(subject)
				.issuer("DCB")
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 60 * 10 * 1000)) // 10
				// minutes
				.signWith(generateKey())
				.compact();
	}

	public Long extractUserId(String token) {
		Claims claims = extractAllClaims(token);
		return claims.get("userId", Long.class);
	}

	private SecretKey generateKey() {
		byte[] decode = Decoders.BASE64.decode(getSecretKey());
		return Keys.hmacShaKeyFor(decode);
	}

	public String getSecretKey() {
		return secretKey;
	}

	public String extractUserName(String token) {
		return extractClaims(token, Claims::getSubject);
	}

	private <T> T extractClaims(String token, Function<Claims, T> claimResolver) {
		Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser()
				.verifyWith(generateKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String userName = extractUserName(token);
		return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaims(token, Claims::getExpiration);
	}

}