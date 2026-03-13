package org.example.reto4springboot.security;

import org.example.reto4springboot.entities.UserDB;
import org.example.reto4springboot.repositories.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

/**
 * Implementación personalizada de UserDetailsService para Spring Security.
 * Carga los datos del usuario desde MongoDB para el proceso de autenticación.
 */
@Service
public class AppUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Constructor del servicio.
     * @param userRepository Repositorio de usuarios.
     */
    public AppUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Localiza al usuario en la base de datos basándose en el email proporcionado.
     * * @param username El email del usuario que intenta iniciar sesión.
     * @return UserDetails con la información de seguridad del usuario.
     * @throws UsernameNotFoundException Si el email no existe en la base de datos.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserDB> currentUser = userRepository.findUserDBByEmail(username);

        if (currentUser.isEmpty()) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }

        String rol = currentUser.get().isAdmin() ? "ADMIN" : "USER";

        return User.withUsername(username)
                .password("{noop}" + currentUser.get().getPassword())
                .roles(rol)
                .build();
    }
}