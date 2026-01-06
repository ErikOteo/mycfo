package pronostico.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. APLICAR CORS NATIVO AQUI MISMO
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            
            // 2. DESACTIVAR CSRF (No necesario para APIs REST con JWT)
            .csrf(csrf -> csrf.disable())
            
            // 3. STATELESS (No guardar sesiones en memoria)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            // 4. REGLAS DE AUTORIZACION
            .authorizeHttpRequests(auth -> auth
                // Permitir OPTIONS explícitamente (Preflight requests)
                .requestMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll()
                // Permitir health checks
                .requestMatchers("/actuator/**", "/public/**").permitAll()
                // Todo lo demás requiere autenticación
                .anyRequest().authenticated()
            )
            
            // 5. VALIDAR EL TOKEN JWT
            .oauth2ResourceServer(oauth2 -> oauth2.jwt());

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // PERMITIR ORÍGENES (Tu Netlify y Localhost)
        configuration.setAllowedOrigins(Arrays.asList(
            "https://mycfoar.netlify.app",
            "http://localhost:3000",
            "https://*.netlify.app"
        ));
        
        // PERMITIR MÉTODOS
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));
        
        // PERMITIR HEADERS (Importante: Authorization y X-Usuario-Sub)
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Usuario-Sub", "X-Organizacion-Id"));
        
        // PERMITIR CREDENCIALES
        configuration.setAllowCredentials(true);
        
        // Exponer headers si fuera necesario
        configuration.addExposedHeader("Authorization");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
