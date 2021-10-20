package com.smdev.gearbybe.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

public class JWTUtils {
    private static final String SECRET_KEY = "LJFRG894GH4895G9H4GHSJOEIFOW8OhHF3o8hf8fsg34g";
    private static final SignatureAlgorithm ALGORITHM = SignatureAlgorithm.HS256;
    private static final int EXPIRATION_TIME = 1000 * 60 * 60 * 10;

    public static final String TOKEN_PREFIX = "JWT";

    private static boolean isExpired(String token){
        return getClaim(token, Claims::getExpiration).before(new Date());
    }

    private static <T> T getClaim(String token, Function<Claims, T> func){
        return func.apply(Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody());
    }

    public static String generate(UserDetails userDetails){
        return TOKEN_PREFIX + Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(userDetails.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(ALGORITHM, SECRET_KEY)
                .compact();
    }

    public static boolean validate(String token, UserDetails userDetails){
        return (!isExpired(token) && getClaim(token, Claims::getSubject).equals(userDetails.getUsername()));
    }

    public static String getEmail(String token){
        return getClaim(token, Claims::getSubject);
    }
}
