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
 * Servicio personalizado para cargar los detalles del usuario desde la base de datos.
 * Implementa la interfaz UserDetailsService de Spring Security.
 */
@Service
public class AppUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Constructor del servicio.
     *
     * @param userRepository Repositorio de usuarios.
     */
    public AppUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Carga los detalles de un usuario dado su nombre de usuario (email).
     *
     * @param username Nombre de usuario (email) a buscar.
     * @return Objeto UserDetails con la información del usuario.
     * @throws UsernameNotFoundException Si el usuario no se encuentra en la base de datos.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserDB> currentUser = userRepository.findUserDBByEmail(username);

        if (currentUser.isEmpty()) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }

        String rol = username.toLowerCase().contains("admin") ? "ADMIN" : "USER";

        return User.withUsername(username)
                .password("{noop}" + currentUser.get().getPassword())
                .roles(rol)
                .build();
    }
}
