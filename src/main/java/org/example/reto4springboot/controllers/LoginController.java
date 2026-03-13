package org.example.reto4springboot.controllers;

import org.example.reto4springboot.entities.UserDB;
import org.example.reto4springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controlador encargado de gestionar las peticiones de autenticación y registro de usuarios.
 * Maneja la navegación entre las vistas de login y los formularios de alta de nuevos usuarios.
 */
@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    /**
     * Muestra la vista del formulario de inicio de sesión.
     * * @return El nombre de la plantilla HTML de login.
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * Muestra la vista del formulario de registro de nuevos usuarios.
     * * @return El nombre de la plantilla HTML de registro.
     */
    @GetMapping("/registro")
    public String mostrarRegistro() {
        return "registro";
    }

    /**
     * Procesa la solicitud de registro de un nuevo usuario.
     * Valida si el email ya existe antes de persistir los datos.
     * * @param email El correo electrónico proporcionado por el usuario.
     * @param password La contraseña elegida por el usuario.
     * @return Una redirección al registro con error si el usuario existe,
     * o al login con éxito si el proceso es correcto.
     */
    @PostMapping("/registro")
    public String registrarUsuario(@RequestParam String email, @RequestParam String password) {
        if (userRepository.findUserDBByEmail(email).isPresent()) {
            return "redirect:/registro?error=existe";
        }

        UserDB nuevoUsuario = new UserDB();
        nuevoUsuario.setEmail(email);
        nuevoUsuario.setPassword(password);
        nuevoUsuario.setAdmin(false);

        userRepository.save(nuevoUsuario);

        return "redirect:/login?registrado=true";
    }
}