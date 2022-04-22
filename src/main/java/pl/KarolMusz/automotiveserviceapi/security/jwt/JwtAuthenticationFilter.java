package pl.KarolMusz.automotiveserviceapi.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.KarolMusz.automotiveserviceapi.dto.AuthenticationRequestDTO;
import pl.KarolMusz.automotiveserviceapi.model.User;
import pl.KarolMusz.automotiveserviceapi.service.impl.UserServiceImpl;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static pl.KarolMusz.automotiveserviceapi.security.jwt.JwtFilterConstants.*;


public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl userService;
    private final String secretKey;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, UserServiceImpl userService, String secretKey) {
        super.setFilterProcessesUrl(URL);
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.secretKey = secretKey;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            AuthenticationRequestDTO authDTO = new ObjectMapper().readValue(request.getInputStream(), AuthenticationRequestDTO.class);

            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        authDTO.email,
                        authDTO.password,
                        new ArrayList<>()
                    ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        String userEmail = ((User) authResult.getPrincipal()).getUsername();
        String role = ((User) authResult.getPrincipal()).getRole().toString();
        String userId = ((User) authResult.getPrincipal()).getId().toString();

        String token = JWT.create()
                .withSubject(userEmail)
                .withClaim(ROLE, role)
                .withClaim(USER_ID, userId)
                .withExpiresAt(setTokenExpirationTime())
                .sign(Algorithm.HMAC512(this.secretKey.getBytes()));

        String authDtoAsString = userService.createAuthenticationJsonObjectAsString(userEmail, token);

        response.setContentType("application/json");
        response.getWriter().write(authDtoAsString);
        response.getWriter().flush();
    }

    private Date setTokenExpirationTime() {
        return new Date(System.currentTimeMillis() + TIME);
    }
}
