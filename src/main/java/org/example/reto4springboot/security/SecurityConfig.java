package org.example.reto4springboot.security;

import jakarta.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.example.reto4springboot.dto.ErrorResponseDTO;

/**
 * Clase de configuración de seguridad para la aplicación.
 * Define las reglas de acceso, la gestión de sesiones y el comportamiento
 * de inicio y cierre de sesión.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configura la cadena de filtros de seguridad (Security Filter Chain).
     * Define qué rutas son públicas, cuáles requieren autenticación y
     * personaliza el formulario de login y el proceso de logout.
     * * @param http Objeto HttpSecurity para configurar la seguridad web.
     * @return El filtro de seguridad configurado.
     * @throws Exception Si ocurre un error en la configuración.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                        .requestMatchers("/login", "/registro", "/web/museos/lista", "/web/museos/detalle/**", "/api/museos/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/web/museos/lista", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/web/museos/lista")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );
        return http.build();
    }

    /**
     * Define un punto de entrada personalizado para manejar errores de autenticación.
     * Devuelve una respuesta JSON con código 401 en lugar de una redirección por defecto.
     * * @return Instancia de AuthenticationEntryPoint configurada.
     */
    @Bean
    public AuthenticationEntryPoint customAuthenticationEntryPoint() {
        return (request, response, e) -> {
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            ErrorResponseDTO error = new ErrorResponseDTO(
                    "No autorizado",
                    "Debes iniciar sesión para realizar cambios.",
                    401
            );
            ObjectMapper mapper = new ObjectMapper();
            response.getWriter().write(mapper.writeValueAsString(error));
        };
    }
}