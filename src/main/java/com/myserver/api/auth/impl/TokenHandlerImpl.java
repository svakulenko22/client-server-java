package com.myserver.api.auth.impl;

import com.myserver.api.auth.TokenHandler;
import com.myserver.api.exception.NoSuchEntityException;
import com.myserver.api.exception.NotAuthorizedException;
import com.myserver.api.model.User;
import com.myserver.api.service.UserService;
import com.myserver.api.service.impl.UserServiceImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Date;

@PropertySource("classpath:security.properties")
@Service
public class TokenHandlerImpl implements TokenHandler {

    @Value("${app.jwt.secret}")
    private String secret;

    @Autowired
    private UserService userService;

    @Override
    public void parseUserFromToken(String token) {
        String userId = Jwts.parser()
            .setSigningKey(secret)
            .parseClaimsJws(token)
            .getBody()
            .get("id")
            .toString();
        Integer id = Integer.parseInt(userId);
        final User user = userService.findOne(id);
        if (user == null) {
            throw new NotAuthorizedException("Unauthorized");
        }
    }

    @Override
    public String createTokenForUser(User user) {
        ZonedDateTime afterOneWeek = ZonedDateTime.now().plusDays(1);

        return Jwts.builder()
            .claim("id", user.getId())
            .signWith(SignatureAlgorithm.HS256, secret)
            .setExpiration(Date.from(afterOneWeek.toInstant()))
            .compact();
    }
}
