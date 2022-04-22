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

        if ((header == null) || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response);
        }
        else {
            Optional<UsernamePasswordAuthenticationToken> authenticationTokenOptional = getAuthenticationToken(header);

            if (authenticationTokenOptional.isEmpty())
                throw new IOException();

            SecurityContextHolder.getContext().setAuthentication(authenticationTokenOptional.get());

            chain.doFilter(request, response);
        }
    }

    private Optional<UsernamePasswordAuthenticationToken> getAuthenticationToken(String requestHeader) {
        Optional<UsernamePasswordAuthenticationToken> tokenOptional = Optional.empty();

        if (!requestHeader.isEmpty()) {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(this.secretKey.getBytes()))
                                    .build()
                                    .verify(requestHeader.replace(TOKEN_PREFIX, ""));

            String email = decodedJWT.getSubject();
            String role = decodedJWT.getClaim(ROLE).asString();
            String userId = decodedJWT.getClaim(USER_ID).asString();

            if (!email.isEmpty()) {
                List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, null, authorities);
                token.setDetails(UUID.fromString(userId));
                tokenOptional = Optional.of(token);
            }
        }

        return tokenOptional;
    }
}
