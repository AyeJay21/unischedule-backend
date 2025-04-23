package com.example.unischedule.security;

import com.example.unischedule.user.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    public AuthTokenFilter(JwtUtils jwtUtils, UserDetailsServiceImpl userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    public AuthTokenFilter() {
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if(jwt != null && jwtUtils.validateJwtToken(jwt)){
                String username = jwtUtils.getUsernameFromToken(jwt);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                logger.debug("JWT found: " + jwt);
                logger.debug("Username from token: " + username);
                logger.debug("Setting authentication: " + authentication);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }catch(Exception e){
            logger.error("Cannot set user authentication: {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
        System.out.println("HELLO");
    }
    private String parseJwt(HttpServletRequest request) {
        String token = jwtUtils.getJwtFromCookies(request);
        logger.debug("Parsed JWT token from request: " + (token != null ? "token found" : "token not found"));
        return token;
    }
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request,
//                                            HttpServletResponse response,
//                                            FilterChain chain,
//                                            Authentication authResult)
//            throws IOException, ServletException {
//        // Set the Authentication in the SecurityContextHolder
//        SecurityContextHolder.getContext().setAuthentication(authResult);
//
//        // Optionally create a session if needed (session-based auth)
//        if (getAllowSessionCreation()) {
//            request.getSession().setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
//        }
//
//        // Trigger the authentication success handler (which you already set)
//        getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
//
//        // Optionally proceed with the filter chain if needed (for JWT-based applications)
//        // chain.doFilter(request, response); // Uncomment if continuing the filter chain is required
//    }
//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request) {
//        return request.getServletPath().equals("/users/timetable");
//    }
}
