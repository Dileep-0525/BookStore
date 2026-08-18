package com.dileep.ecommerce.ms.config;


import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.dileep.ecommerce.ms.dto.ErrorResponse;
import com.dileep.ecommerce.ms.util.CustomUserDetailsService;
import com.dileep.ecommerce.ms.util.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.databind.ObjectMapper;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {
		
		String authHeader = request.getHeader("Authorization");

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {

			filterChain.doFilter(request, response);
			return;
		}

		String token = authHeader.substring(7);

		jwtUtil.validateToken(token);

			String username = jwtUtil.extractUsername(token);

			UserDetails userDetails = userDetailsService.loadUserByUsername(username);

			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null,
					userDetails.getAuthorities());

			SecurityContextHolder.getContext().setAuthentication(auth);
		}catch (ExpiredJwtException ex) {
			ErrorResponse err = ErrorResponse.builder().statusCode(HttpServletResponse.SC_UNAUTHORIZED).message("Token has Expired").timestamp(LocalDateTime.now()).build();
			
	        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	        response.setContentType("application/json");

	        response.getWriter().write(
	                new ObjectMapper()
	                        .writeValueAsString(err) 
	        );


			
			return ;
		} catch (JwtException ex) {
			ErrorResponse err = ErrorResponse.builder().statusCode(HttpServletResponse.SC_UNAUTHORIZED).message("Invalid Token").timestamp(LocalDateTime.now()).build();

	        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	        response.setContentType("application/json");

	        response.getWriter().write(
	                new ObjectMapper()
	                        .writeValueAsString(err));

			
			return ;
		}
		filterChain.doFilter(request, response);
	}
    
//    private Mono<Void> handleError(ServerWebExchange exchange, String message) {
//
//		exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//
//		exchange.getResponse().getHeaders().add("Content-Type", "application/json");
//
//		String body = """
//				{
//				  "status": 401,
//				  "error": "%s"
//				}
//				""".formatted(message);
//
//		DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(body.getBytes());
//
//		return exchange.getResponse().writeWith(Mono.just(buffer));
//	}
    
    
//try {
//	
//}catch (ExpiredJwtException ex) {
//	return handleError(ex.getMessage(), "Token Expired");
//} catch (JwtException ex) {
//	return handleError(ex.getMessage(), "Invalid Token");
//}catch (Exception e) {
//	// TODO: handle exception
//}
//// validate token
//if(jwtUtil.validateToken(token)) {
//
//    UserDetails userDetails =
//            userService.loadUserByUsername(
//                jwtService.extractUsername(token));
//
//    UsernamePasswordAuthenticationToken authentication =
//            new UsernamePasswordAuthenticationToken(
//                    userDetails,
//                    null,
//                    userDetails.getAuthorities());
//
//    SecurityContextHolder
//            .getContext()
//            .setAuthentication(authentication);
//}
    
}
