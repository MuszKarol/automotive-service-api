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
            AuthenticationRequestDTO authDTO = getAuthenticationDTO(request);
            return getAuthentication(authDTO);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        String userEmail = getPrincipal(authResult).getUsername();
        String role = getPrincipal(authResult).getRole().toString();

        String authDtoAsString = createToken(userEmail, role);

        responseWriteAuthenticationDTO(response, authDtoAsString);
    }

    private void responseWriteAuthenticationDTO(HttpServletResponse response, String authDtoAsString) throws IOException {
        response.setContentType("application/json");
        response.getWriter().write(authDtoAsString);
        response.getWriter().flush();
    }

    private Authentication getAuthentication(AuthenticationRequestDTO authDTO) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authDTO.email,
                        authDTO.password,
                        new ArrayList<>()
                )
        );
    }

    private AuthenticationRequestDTO getAuthenticationDTO(HttpServletRequest request) throws IOException {
        return new ObjectMapper().readValue(request.getInputStream(), AuthenticationRequestDTO.class);
    }

    private User getPrincipal(Authentication authResult) {
        return (User) authResult.getPrincipal();
    }

    private String createToken(String userEmail, String role) {
        String token = JWT.create()
                .withSubject(userEmail)
                .withClaim(ROLE, role)
                .withExpiresAt(setTokenExpirationTime())
                .sign(Algorithm.HMAC512(this.secretKey.getBytes()));

        return userService.createAuthenticationJsonObjectAsString(userEmail, token);
    }

    private Date setTokenExpirationTime() {
        return new Date(System.currentTimeMillis() + TIME);
    }
}
