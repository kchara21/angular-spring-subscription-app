package com.example.practica.security;

import com.example.practica.exceptions.PracticaAppException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpirationInMs;

    public String generateToken(Authentication authentication){
        String email = authentication.getName();
        Date currentDate =  new Date();
        Date expirationDate = new Date(currentDate.getTime() + jwtExpirationInMs);
        String token = Jwts.builder().setSubject(email).setIssuedAt(new Date()).setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512,jwtSecret).compact();

        return token;
    }


    public String obtainEmailFromJWT(String token){
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        }catch (SignatureException ex){
            throw new PracticaAppException(HttpStatus.BAD_REQUEST,"Signature JWT not valid");
        }
        catch (MalformedJwtException ex){
            throw new PracticaAppException(HttpStatus.BAD_REQUEST,"Token JWT not valid");
        }
        catch (ExpiredJwtException ex){
            throw new PracticaAppException(HttpStatus.BAD_REQUEST,"Token JWT expired");
        }
        catch (UnsupportedJwtException ex){
            throw new PracticaAppException(HttpStatus.BAD_REQUEST,"Token JWT not supported");
        }
        catch (IllegalArgumentException ex){
            throw new PracticaAppException(HttpStatus.BAD_REQUEST,"The claims string is empty");
        }



    }


}
