package com.example.demo.filters;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.jwt.JWTUtil;
import com.example.demo.service.MyUserDetailsService;

import io.jsonwebtoken.JwtException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * JWT filter that check if the JWT is present in the header of the request.
 * 
 * @author ClaudiuIova
 *
 */
@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

	private final JWTUtil jwtUtil;
	private final MyUserDetailsService userServiceDetails;

	private static final String AUTHORIZATION = "Authorization";

	@Override
	protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
			final FilterChain filterChain) throws ServletException, IOException {

		try {
			final String authorizationHeader = request.getHeader(AUTHORIZATION);
			final Optional<InformationStructure> structure = checkAuthorizationHeader(authorizationHeader);

			if (!structure.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null) {
				doSecurityAuthentication(request, structure.get());
			}
			filterChain.doFilter(request, response);
		} catch (JwtException exception) {
			handleJwtException(response, exception);
		}
	}

	private Optional<InformationStructure> checkAuthorizationHeader(final String authorizationHeader) {
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			String jwt = authorizationHeader.substring(7);
			return Optional.of(new InformationStructure(jwtUtil.extractUsername(jwt), jwt));
		}
		return Optional.empty();
	}

	private void handleJwtException(final HttpServletResponse response, final JwtException exception)
			throws IOException {
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.getWriter().write(exception.getMessage());
	}

	private void doSecurityAuthentication(final HttpServletRequest request, final InformationStructure structure) {
		UserDetails userDetails = this.userServiceDetails.loadUserByUsername(structure.getEmail());
		if (jwtUtil.validateToken(structure.getJwt(), userDetails)) {
			UsernamePasswordAuthenticationToken usernameAndPasswordToken = new UsernamePasswordAuthenticationToken(
					userDetails, null, userDetails.getAuthorities());
			usernameAndPasswordToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(usernameAndPasswordToken);
		}
	}

	@RequiredArgsConstructor
	@Getter
	private class InformationStructure {
		private final String email;
		private final String jwt;
	}

}
