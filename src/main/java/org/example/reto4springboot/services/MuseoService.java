package org.example.reto4springboot.services;

import org.example.reto4springboot.entities.Museo;
import org.example.reto4springboot.repositories.MuseoRepository;
import org.example.reto4springboot.exceptions.MuseoNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Servicio encargado de la lógica de negocio relacionada con la entidad Museo.
 * Coordina las operaciones entre los controladores y el repositorio de datos.
 */
@Service
public class MuseoService {

    private final MuseoRepository museoRepository;

    /**
     * Constructor del servicio para inyección de dependencias.
     * @param museoRepository Repositorio de museos.
     */
    public MuseoService(MuseoRepository museoRepository) {
        this.museoRepository = museoRepository;
    }

    /**
     * Obtiene todos los museos de la base de datos.
     * @return Lista de museos.
     */
    public List<Museo> findAll() {
        return museoRepository.findAll();
    }

    /**
     * Busca un museo por su ID.
     * @param id Identificador único del museo.
     * @return Optional con el museo encontrado.
     */
    public Optional<Museo> findById(String id) {
        return museoRepository.findById(id);
    }

    /**
     * Guarda un museo en la base de datos.
     * @param museo Objeto museo a persistir.
     * @return El museo guardado.
     */
    public Museo save(Museo museo) {
        return museoRepository.save(museo);
    }

    /**
     * Elimina un museo por su identificador.
     * @param id ID del museo a eliminar.
     */
    public void deleteById(String id) {
        museoRepository.deleteById(id);
    }

    /**
     * Filtra los museos por provincia.
     * @param province Nombre de la provincia.
     * @return Lista de museos en esa provincia.
     */
    public List<Museo> findByProvince(String province) {
        return museoRepository.findByProvince(province);
    }

    /**
     * Busca un museo por su nombre exacto.
     * @param name Nombre del museo.
     * @return Optional con el museo encontrado.
     */
    public Optional<Museo> findMuseoByName(String name) {
        return museoRepository.findByNombre(name);
    }

    /**
     * Obtiene una lista única de todas las provincias registradas.
     * @return Lista de nombres de provincias.
     */
    public List<String> findAllProvinces() {
        return museoRepository.findAll().stream()
                .map(Museo::getProvince)
                .filter(p -> p != null && !p.isEmpty())
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    /**
     * Genera estadísticas de conteo de museos por provincia.
     * @return Mapa con provincia y cantidad de museos.
     */
    public Map<String, Long> countMuseosByProvince() {
        return museoRepository.findAll().stream()
                .filter(m -> m.getProvince() != null)
                .collect(Collectors.groupingBy(Museo::getProvince, Collectors.counting()));
    }

    /**
     * Actualiza los datos de un museo existente.
     * @param id ID del museo a modificar.
     * @param museoDetails Nuevos datos.
     * @return El museo actualizado.
     * @throws MuseoNotFoundException Si el ID no existe.
     */
    public Museo update(String id, Museo museoDetails) {
        return museoRepository.findById(id).map(museoExistente -> {
            museoExistente.setNombre(museoDetails.getNombre());
            museoExistente.setAddress(museoDetails.getAddress());
            museoExistente.setMunicipality(museoDetails.getMunicipality());
            museoExistente.setProvince(museoDetails.getProvince());
            museoExistente.setPhone(museoDetails.getPhone());
            museoExistente.setEmail(museoDetails.getEmail());
            museoExistente.setWeb(museoDetails.getWeb());
            museoExistente.setOpening_hours(museoDetails.getOpening_hours());
            museoExistente.setLatitude(museoDetails.getLatitude());
            museoExistente.setLongitude(museoDetails.getLongitude());
            return museoRepository.save(museoExistente);
        }).orElseThrow(() -> new MuseoNotFoundException("No se encontró el ID: " + id));
    }

    /**
     * Busca museos que contengan una cadena en su nombre.
     * @param nombre Texto a buscar.
     * @return Lista de coincidencias.
     */
    public List<Museo> findByNombreContaining(String nombre) {
        return museoRepository.findByNombreContainingIgnoreCase(nombre);
    }
}