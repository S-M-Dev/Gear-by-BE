package com.smdev.gearbybe.filter;

import com.smdev.gearbybe.exception.TokenException;
import com.smdev.gearbybe.util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtFilter(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("token");
        if(Objects.nonNull(token) && token.startsWith(JWTUtils.TOKEN_PREFIX)){
            token = token.substring(JWTUtils.TOKEN_PREFIX.length());
            String email = JWTUtils.getEmail(token);
            UserDetails userDetails;
            try {
                userDetails = userDetailsService.loadUserByUsername(email);
            }catch (UsernameNotFoundException e){
                throw new TokenException("Invalid token");
            }
            if(JWTUtils.validate(token, userDetails)){
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
                                                                userDetails.getPassword(),
                                                                userDetails.getAuthorities());
                WebAuthenticationDetails webAuthenticationDetails = new WebAuthenticationDetailsSource().buildDetails(request);
                authToken.setDetails(webAuthenticationDetails);
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }else{
                throw new TokenException("Token expired");
            }
        }

        filterChain.doFilter(request, response);
    }
}
