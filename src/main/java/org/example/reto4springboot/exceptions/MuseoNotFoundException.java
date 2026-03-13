package org.example.reto4springboot.exceptions;

/**
 * Excepción personalizada que se lanza cuando las operaciones de búsqueda no encuentran un museo.
 */
public class MuseoNotFoundException extends RuntimeException {

    /**
     * Constructor que permite definir un mensaje personalizado de error.
     *
     * @param message Mensaje descriptivo del motivo del error.
     */
    public MuseoNotFoundException(String message) {
        super(message);
    }
}