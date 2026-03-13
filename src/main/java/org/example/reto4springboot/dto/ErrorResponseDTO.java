package org.example.reto4springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para estructurar las respuestas de error de la API.
 * Contiene información sobre el mensaje, el tipo de error y el código de estado HTTP.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDTO {

    /**
     * Mensaje detallado que explica la causa del error.
     */
    private String message;

    /**
     * Descripción breve del error o categoría del mismo.
     */
    private String error;

    /**
     * Código numérico del estado HTTP asociado al error.
     */
    private int status;
}