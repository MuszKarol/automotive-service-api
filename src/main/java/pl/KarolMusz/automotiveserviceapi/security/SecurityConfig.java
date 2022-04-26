package pl.KarolMusz.automotiveserviceapi.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import pl.KarolMusz.automotiveserviceapi.model.enums.Role;
import pl.KarolMusz.automotiveserviceapi.security.jwt.JwtAuthenticationFilter;
import pl.KarolMusz.automotiveserviceapi.security.jwt.JwtAuthorizationFilter;
import pl.KarolMusz.automotiveserviceapi.security.jwt.JwtFilterConstants;
import pl.KarolMusz.automotiveserviceapi.service.impl.UserServiceImpl;

import java.util.List;

@AllArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserServiceImpl userService;
    private final JwtFilterConstants requestConstants;

    @Bean
    public static BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.applyPermitDefaultValues();
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PATCH", "PUT", "DELETE"));

        configurationSource.registerCorsConfiguration("/**", corsConfiguration);

        return configurationSource;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.cors()
            .and()
            .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/users/auth").permitAll()
                .antMatchers(HttpMethod.POST, "/users/new").permitAll()
                .antMatchers(HttpMethod.GET, "/about").permitAll()
                .antMatchers(HttpMethod.POST, "/about/new").hasAuthority(Role.ADMIN.toString())
                .antMatchers(HttpMethod.GET, "/visits/user").hasAuthority(Role.CLIENT.toString())
                .antMatchers(HttpMethod.GET, "/visits/new").hasAuthority(Role.ADMIN.toString())
                .antMatchers(HttpMethod.GET, "/visits/accepted").hasAuthority(Role.ADMIN.toString())
                .antMatchers(HttpMethod.POST, "/visits/new").hasAuthority(Role.CLIENT.toString())
                .antMatchers(HttpMethod.PATCH, "/visits").hasAuthority(Role.ADMIN.toString())
                .antMatchers(HttpMethod.GET, "/users").hasAuthority(Role.CLIENT.toString())
                .antMatchers(HttpMethod.PUT, "/users").hasAuthority(Role.CLIENT.toString())
                .antMatchers(HttpMethod.POST, "/users/{id}/vehicle")
                    .hasAuthority(Role.CLIENT.toString())
                .antMatchers(HttpMethod.DELETE, "/users/{user-id}/vehicle/{vehicle-vin}")
                    .hasAuthority(Role.CLIENT.toString())
                .antMatchers(HttpMethod.GET, "/order/parts").hasAuthority(Role.ADMIN.toString())
                .antMatchers(HttpMethod.POST, "/order/parts").hasAuthority(Role.ADMIN.toString())
                .antMatchers(HttpMethod.DELETE, "/order/parts/{partCode}")
                    .hasAuthority(Role.ADMIN.toString())
                .anyRequest().authenticated()
            .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), userService, requestConstants.SECRET_KEY))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), requestConstants.SECRET_KEY))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userService)
                .passwordEncoder(bCryptPasswordEncoder());
    }
}
