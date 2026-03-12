package org.example.reto4springboot.repositories;

import org.example.reto4springboot.entities.Museo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Museo.
 * Extiende MongoRepository para proporcionar operaciones CRUD básicas y consultas personalizadas.
 */
@Repository
public interface MuseoRepository extends MongoRepository<Museo, String> {

    /**
     * Busca un museo por su nombre exacto.
     *
     * @param nombre Nombre del museo.
     * @return Un Optional con el museo si se encuentra.
     */
    Optional<Museo> findByNombre(String nombre);
    /**
     * Busca museos por municipio.
     *
     * @param municipality Nombre del municipio.
     * @return Lista de museos en el municipio.
     */
    List<Museo> findByMunicipality(String municipality);

    /**
     * Busca museos por provincia.
     *
     * @param province Nombre de la provincia.
     * @return Lista de museos en la provincia.
     */
    List<Museo> findByProvince(String province);
    List<Museo> findByNombreContainingIgnoreCase(String nombre);}
