package com.learningtech.config;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.learningtech.genericmodel.ErrorResponse;
import com.learningtech.util.JSONConveter;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

	final private JWTService jwtservice;

	final private UserDetailsService userDetailsService;
	
	public static final String[] UN_PROTECTED_API_LIST= {
			"/api/v1/auth/register",
			"/api/v1/auth/login"
			};
	public static final String UNAUTHORIZED = "Unauthorized";
	

	@Override
	protected void doFilterInternal(
			@NonNull HttpServletRequest request, 
			@NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain) throws ServletException, IOException {

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		final String authHeader = request.getHeader("Authorization");
		final String jwtToken;
		final String userName;
		String path = request.getRequestURI().substring(request.getContextPath().length());
		
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			if (isUnprotected(path)) {
				filterChain.doFilter(request, response);
				return;
			}
			setResponse(response,UNAUTHORIZED);
			return;
		}
		jwtToken = authHeader.substring(7);
		try {
			userName = jwtservice.extractUserName(jwtToken);
			if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

				UserDetails user = this.userDetailsService.loadUserByUsername(userName);
				if (jwtservice.isTokenValid(jwtToken, user)) {
					UsernamePasswordAuthenticationToken authtoken = new UsernamePasswordAuthenticationToken(user, null,	user.getAuthorities());
					authtoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authtoken);
				} else {
					setResponse(response,UNAUTHORIZED);
				}
			}
		} catch (ExpiredJwtException e) {
			setResponse(response,UNAUTHORIZED);
			return;
		}
		filterChain.doFilter(request, response);
	}
	
	private boolean isUnprotected(String path) {
		return Arrays.asList(UN_PROTECTED_API_LIST).contains(path);
	}
	
	private void setResponse(HttpServletResponse response,String message) throws JsonProcessingException, IOException {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setStatus(Boolean.FALSE);
		errorResponse.setStatusCode(HttpStatus.UNAUTHORIZED.value());
		errorResponse.setMessage(message);
		response.getWriter().write(JSONConveter.objToJson(errorResponse));
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
	}

}
