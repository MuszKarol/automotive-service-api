package pl.KarolMusz.automotiveserviceapi.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static pl.KarolMusz.automotiveserviceapi.security.jwt.JwtFilterConstants.*;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final String secretKey;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, String secretKey) {
        super(authenticationManager);
        this.secretKey = secretKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(JwtFilterConstants.HEADER);

        if (isTokenPresent(header)) {
            chain.doFilter(request, response);
        } else {
            Optional<UsernamePasswordAuthenticationToken> tokenOptional = authorize(header);

            if (tokenOptional.isEmpty())
                throw new IOException();

            saveToken(tokenOptional.get());

            chain.doFilter(request, response);
        }
    }

    private void saveToken(UsernamePasswordAuthenticationToken token) {
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    private boolean isTokenPresent(String header) {
        return ((header == null) || !header.startsWith(TOKEN_PREFIX));
    }

    private Optional<UsernamePasswordAuthenticationToken> authorize(String requestHeader) {
        Optional<UsernamePasswordAuthenticationToken> tokenOptional = Optional.empty();

        if (!requestHeader.isEmpty()) {
            DecodedJWT decodedJWT = decodeToken(requestHeader);

            String email = decodedJWT.getSubject();
            String role = decodedJWT.getClaim(ROLE).asString();
            String userId = decodedJWT.getClaim(USER_ID).asString();

            tokenOptional = getToken(email, role, userId);
        }

        return tokenOptional;
    }

    private Optional<UsernamePasswordAuthenticationToken> getToken(String email, String role, String userId) {
        if (!email.isEmpty()) {
            return Optional.of(stringsToToken(email, role, userId));
        } else {
            return Optional.empty();
        }
    }

    private UsernamePasswordAuthenticationToken stringsToToken(String email, String role, String userId) {
        UsernamePasswordAuthenticationToken token;

        token = new UsernamePasswordAuthenticationToken(
                email,
                null,
                List.of(new SimpleGrantedAuthority(role))
        );

        token.setDetails(UUID.fromString(userId));

        return token;
    }

    private DecodedJWT decodeToken(String requestHeader) {
        return JWT.require(Algorithm.HMAC512(this.secretKey.getBytes()))
                .build()
                .verify(removeTokenPrefix(requestHeader));
    }

    private String removeTokenPrefix(String requestHeader) {
        return requestHeader.replace(TOKEN_PREFIX, "");
    }
}
