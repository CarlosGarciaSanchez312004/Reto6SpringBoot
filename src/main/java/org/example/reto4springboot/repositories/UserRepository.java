package org.example.reto4springboot.repositories;

import org.example.reto4springboot.entities.UserDB;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para la entidad UserDB.
 * Permite gestionar la colección de usuarios registrados para procesos de seguridad y login.
 */
@Repository
public interface UserRepository extends MongoRepository<UserDB, String> {

    /**
     * Localiza a un usuario en la base de datos a través de su correo electrónico.
     *
     * @param email Dirección de correo electrónico del usuario.
     * @return Un Optional con el usuario si la búsqueda tiene éxito.
     */
    Optional<UserDB> findUserDBByEmail(String email);
}