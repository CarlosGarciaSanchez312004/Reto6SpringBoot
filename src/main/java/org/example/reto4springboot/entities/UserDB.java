package org.example.reto4springboot.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Entidad que representa un usuario en la base de datos MongoDB.
 * Se utiliza para la autenticación y autorización en la aplicación.
 */
@Data
@Document(collection = "users")
public class UserDB {

    /**
     * Identificador único del usuario autogenerado por la base de datos.
     */
    @Id
    private String _id;

    /**
     * Correo electrónico del usuario, utilizado como nombre de usuario único.
     */
    private String email;

    /**
     * Contraseña del usuario almacenada de forma segura.
     */
    private String password;

    /**
     * Atributo que determina si el usuario posee privilegios de administrador.
     */
    private boolean admin;

    /**
     * Obtiene la contraseña del usuario.
     *
     * @return La contraseña en formato String.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Obtiene el correo electrónico del usuario.
     *
     * @return El correo electrónico del usuario.
     */
    public String getEmail() {
        return this.email;
    }
}