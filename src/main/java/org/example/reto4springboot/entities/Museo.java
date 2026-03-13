package org.example.reto4springboot.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Entidad que representa la estructura de un Museo almacenado en MongoDB.
 * Utiliza anotaciones de Spring Data MongoDB y Lombok para simplificar el código.
 */
@Data
@Document(collection = "museos")
public class Museo {

    /**
     * Identificador interno generado por MongoDB (_id).
     */
    @Id
    private String _id;

    /**
     * Identificador numérico de referencia externa.
     */
    @Field("id")
    private Integer referenciaId;

    /**
     * Nombre descriptivo del museo.
     */
    @Field("name")
    private String nombre;

    /**
     * Ubicación o descripción geográfica.
     */
    private String location;

    /**
     * Código postal de la ubicación.
     */
    private Integer postcode;

    /**
     * Municipio al que pertenece el museo.
     */
    private String municipality;

    /**
     * Observaciones adicionales o comentarios.
     */
    private String observations;

    /**
     * Dirección física completa.
     */
    private String address;

    /**
     * Horario de apertura del centro.
     */
    private String opening_hours;

    /**
     * Dirección de la página web oficial.
     */
    private String web;

    /**
     * Provincia donde reside el museo.
     */
    private String province;

    /**
     * Teléfono de contacto.
     */
    private String phone;

    /**
     * Dirección de correo electrónico de contacto.
     */
    private String email;

    /**
     * Tipo de unidad o categoría del museo.
     */
    @Field("unit_type")
    private String unitType;

    /**
     * Coordenada de latitud para geolocalización.
     */
    private String latitude;

    /**
     * Coordenada de longitud para geolocalización.
     */
    private String longitude;

    /**
     * Estado administrativo o de actividad.
     */
    private String state;

    /**
     * Número de fax de contacto.
     */
    private String fax;
}