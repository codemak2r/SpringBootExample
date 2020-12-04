package com.example.springbootsecurityjpa.security;

import com.example.springbootsecurityjpa.services.impl.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @DATA: 2020/12/4
 */
@Slf4j
public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try{
            String token  = parse(httpServletRequest);
            log.info("token:{}",token);

            if(token != null && jwtUtil.validate(token)) {
                String username = jwtUtil.getUserFromToken(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails.getUsername(),
                        null,
                        userDetails.getAuthorities()
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }catch (Exception e){
            log.error("cannot set authentication :{}",e.getMessage());
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

    private static String parse(HttpServletRequest httpServletRequest){
        String headers = httpServletRequest.getHeader("Authorization");
        if(StringUtils.hasText(headers) && headers.startsWith("Bearer")){
            String token = headers.substring(7, headers.length());
            return token;
        }

        return null;

    }
}
