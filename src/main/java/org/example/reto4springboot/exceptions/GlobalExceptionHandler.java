package org.example.reto4springboot.exceptions;

import org.example.reto4springboot.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Clase global para capturar y gestionar las excepciones de la API.
 * Proporciona respuestas estructuradas en formato JSON para el cliente.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Gestiona las excepciones del tipo MuseoNotFoundException cuando se solicita un museo inexistente.
     * * @param ex Instancia de la excepción capturada.
     * @return ResponseEntity con el objeto de error y el código HTTP 404.
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
     * Gestiona cualquier otra excepción no controlada explícitamente en el sistema.
     * * @param ex Instancia de la excepción genérica capturada.
     * @return ResponseEntity con el objeto de error y el código HTTP 500.
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