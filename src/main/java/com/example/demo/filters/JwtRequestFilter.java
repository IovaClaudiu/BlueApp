package com.example.demo.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.jwt.JWTUtil;
import com.example.demo.service.MyUserDetailsService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * JWT filter that check if the JWT is in header
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
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String authorizationHeader = request.getHeader(AUTHORIZATION);
		final InformationStructure structure = getRequireDetails(authorizationHeader);

		if (structure != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			doSecurityThing(request, structure);
		}
		filterChain.doFilter(request, response);
	}

	private void doSecurityThing(HttpServletRequest request, final InformationStructure structure) {
		UserDetails userDetails = this.userServiceDetails.loadUserByUsername(structure.getUsername());
		if (jwtUtil.validateToken(structure.getJwt(), userDetails)) {
			UsernamePasswordAuthenticationToken usernameAndPasswordToken = new UsernamePasswordAuthenticationToken(
					userDetails, null, userDetails.getAuthorities());
			usernameAndPasswordToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(usernameAndPasswordToken);
		}
	}

	private final InformationStructure getRequireDetails(String authorizationHeader) {
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			String jwt = authorizationHeader.substring(7);
			return new InformationStructure(jwtUtil.extractUsername(jwt), jwt);
		}
		return null;
	}

	@RequiredArgsConstructor
	@Getter
	private class InformationStructure {
		private final String username;
		private final String jwt;
	}

}
