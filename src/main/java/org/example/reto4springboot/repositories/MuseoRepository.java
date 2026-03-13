package org.example.reto4springboot.repositories;

import org.example.reto4springboot.entities.Museo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Museo.
 * Proporciona acceso a los datos en MongoDB incluyendo operaciones CRUD y filtros avanzados.
 */
@Repository
public interface MuseoRepository extends MongoRepository<Museo, String> {

    /**
     * Busca un museo cuyo nombre coincida exactamente con el proporcionado.
     *
     * @param nombre Nombre del museo a localizar.
     * @return Un Optional que contiene el museo si se encuentra.
     */
    Optional<Museo> findByNombre(String nombre);

    /**
     * Recupera una lista de museos filtrada por el municipio.
     *
     * @param municipality Nombre del municipio.
     * @return Lista de museos que se encuentran en dicho municipio.
     */
    List<Museo> findByMunicipality(String municipality);

    /**
     * Recupera una lista de museos filtrada por la provincia.
     *
     * @param province Nombre de la provincia.
     * @return Lista de museos que pertenecen a la provincia especificada.
     */
    List<Museo> findByProvince(String province);

    /**
     * Busca museos cuyo nombre contenga una cadena de texto, ignorando mayúsculas y minúsculas.
     * * @param nombre Cadena de texto a buscar dentro del nombre.
     * @return Lista de museos que coinciden parcialmente con el nombre dado.
     */
    List<Museo> findByNombreContainingIgnoreCase(String nombre);
}