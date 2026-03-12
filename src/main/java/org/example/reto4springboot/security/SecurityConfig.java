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
 * Configuración de seguridad de la aplicación utilizando Spring Security.
 * Define las reglas de autorización y autenticación para los endpoints.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configura la cadena de filtros de seguridad.
     *
     * @param http Objeto HttpSecurity para configurar la seguridad web.
     * @return La cadena de filtros de seguridad construida.
     * @throws Exception Si ocurre un error durante la configuración.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Nota: en un entorno de producción real, es mejor habilitar CSRF
                .authorizeHttpRequests(auth -> auth
                        // 1. Recursos estáticos públicos para todos
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()

                        // 2. Rutas públicas (ver la lista y el detalle de los museos)
                        .requestMatchers("/web/museos/lista", "/web/museos/detalle/**").permitAll()

                        // 3. RUTAS RESTRINGIDAS SOLO PARA ADMINISTRADORES
                        .requestMatchers("/web/museos/nuevo", "/web/museos/guardar", "/web/museos/eliminar/**").hasRole("ADMIN")

                        // 4. Cualquier otra petición (como la API REST) requiere estar autenticado
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
     * Define un punto de entrada de autenticación personalizado para devolver respuestas JSON en caso de error 401.
     *
     * @return Un AuthenticationEntryPoint personalizado.
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
