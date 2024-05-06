package com.learningtech.config;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {
	private static final String SECRET_KEY = "C5mrvSF95FM3jvDUEMZVLXbT67I2jW8emmPxN4svpRUgb02dNovefluiIW0IlIJJ";

	public String extractUserName(String jwtToken) {
		return extractClaim(jwtToken, Claims::getSubject);
	}

	private Claims extractAllClaims(String token) {

		return Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims(token).getPayload();

	}

	private SecretKey getSignKey() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
	}

	public <T> T extractClaim(String token, Function<Claims, T> clameresolver) {
		Claims claims = extractAllClaims(token);
		return clameresolver.apply(claims);
	}

	public String generateToken(UserDetails user) {
		return generateToken(new HashMap<>(), user);
	}

	public String generateToken(Map<String, Object> extraClaim, UserDetails user) {

		return Jwts.builder().claims(extraClaim).subject(user.getUsername())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(Date.from(ZonedDateTime.now().plusMinutes(10).toInstant()))
				.signWith(getSignKey(), SIG.HS256).compact();

// 		This type Configuration got deprecate in spring boot 3	 
//		return Jwts.builder()
//				.setClaims(extraClaim)
//				.setSubject(user.getUsername())
//				.setIssuedAt(new Date(System.currentTimeMillis()))
//				.setExpiration(Date.from(ZonedDateTime.now().plusMinutes(5).toInstant()))
//				.signWith(getSignKey(), SignatureAlgorithm.HS256)
//				.compact();

	}

	public boolean isTokenValid(String token, UserDetails user) {
		String userName = extractUserName(token);
		return userName.equals(user.getUsername()) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		return checkTokenExpire(token).before(new Date());
	}

	private Date checkTokenExpire(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public long getExpiryTime(String token) {
		long diff = checkTokenExpire(token).getTime() - new Date().getTime();
		return TimeUnit.MILLISECONDS.toMinutes(diff) + 1;
	}
}
