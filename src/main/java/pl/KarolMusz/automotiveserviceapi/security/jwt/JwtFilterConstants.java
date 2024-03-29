package pl.KarolMusz.automotiveserviceapi.security.jwt;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtFilterConstants {
    public static String URL = "/users/auth";
    public static String HEADER = "Authorization";
    public static String TOKEN_PREFIX = "Bearer ";
    public static String ROLE = "role";
    public static int TIME = 1_800_000;

    @Value("${secret.key}")
    public String SECRET_KEY;
}
