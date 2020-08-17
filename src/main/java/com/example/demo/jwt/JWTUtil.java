package com.example.demo.jwt;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * JWT util class that will permit to create/check/update a JWT Token.
 * 
 * @author ClaudiuIova
 *
 */
@Service
public final class JWTUtil {

	private static final String MY_VALUE = "myValue";

	@Value("com.example.demo.jwt.secret.key:secret")
	private String SECRET_KEY;

	/**
	 * Get the username from the token
	 * 
	 * @param token JWT token
	 * @return the username.
	 */
	public final String extractUsername(final String token) {
		return extractClaim(token, Claims::getSubject);
	}

	/**
	 * Get the expiration date from token.
	 * 
	 * @param token the JWT token
	 * @return the expiration {@link Date}
	 */
	public final Date extractExpiration(final String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	private final <T> T extractClaim(final String token, final Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private final Claims extractAllClaims(final String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}

	/**
	 * Generate a new JWT token.
	 * 
	 * @param userDetails The user details
	 * @return the JWT token
	 */
	public String generateToken(final UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(MY_VALUE, "testing value");
		return createToken(claims, userDetails.getUsername());
	}

	private final String createToken(final Map<String, Object> claims, final String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(Date.from(Instant.now()))
				.setExpiration(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}

	/**
	 * Validate if the passing token is valid or not.
	 * 
	 * @param token       JWT token
	 * @param userDetails User Details
	 * @return true, if the token is still valid, false otherwise.
	 */
	public final boolean validateToken(final String token, final UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private final boolean isTokenExpired(final String token) {
		return extractExpiration(token).before(new Date());
	}

}
