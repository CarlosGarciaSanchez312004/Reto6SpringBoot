package org.example.reto4springboot.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "museos")
public class Museo {

    @Id
    private String _id;

    @Field("id") // Mapea el campo "id: 2716" de tu JSON
    private Integer referenciaId;

    @Field("name") // Mapea "name" de la DB a tu variable "nombre"
    private String nombre;

    private String location;
    private Integer postcode;
    private String municipality;
    private String observations;
    private String address;
    private String opening_hours;
    private String web;
    private String province;
    private String phone;
    private String email;

    @Field("unit_type")
    private String unitType;

    private String latitude;
    private String longitude;
    private String state;
    private String fax;
}