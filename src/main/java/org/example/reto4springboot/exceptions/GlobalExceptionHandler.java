package org.example.reto4springboot.exceptions;

import org.example.reto4springboot.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Clase global para capturar y gestionar las excepciones de la API.
 * Proporciona respuestas estructuradas en formato JSON.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Gestiona el error cuando no se encuentra un museo específico.
     */
    @ExceptionHandler(MuseoNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleMuseoNotFound(MuseoNotFoundException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(
                ex.getMessage(),
                "Recurso no encontrado",
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Gestiona cualquier otra excepción no controlada.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGeneralException(Exception ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(
                "Ha ocurrido un error inesperado en el servidor.",
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}