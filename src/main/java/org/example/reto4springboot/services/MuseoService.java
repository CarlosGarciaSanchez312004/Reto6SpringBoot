package org.example.reto4springboot.services;

import org.example.reto4springboot.entities.Museo;
import org.example.reto4springboot.repositories.MuseoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Servicio que contiene la lógica de negocio para la gestión de museos.
 * Actúa como intermediario entre el controlador y el repositorio.
 */
@Service
public class MuseoService {

    private final MuseoRepository museoRepository;

    /**
     * Constructor del servicio.
     *
     * @param museoRepository Repositorio de museos.
     */
    public MuseoService(MuseoRepository museoRepository) {
        this.museoRepository = museoRepository;
    }

    /**
     * Recupera todos los museos almacenados.
     *
     * @return Lista de todos los museos.
     */
    public List<Museo> findAll() {
        return museoRepository.findAll();
    }

    /**
     * Busca un museo por su ID.
     *
     * @param id Identificador del museo.
     * @return Un Optional que contiene el museo si se encuentra, o vacío si no.
     */
    public Optional<Museo> findById(String id) {
        return museoRepository.findById(id);
    }

    /**
     * Guarda un nuevo museo o actualiza uno existente.
     *
     * @param museo Objeto Museo a guardar.
     * @return El museo guardado.
     */
    public Museo save(Museo museo) {
        return museoRepository.save(museo);
    }

    /**
     * Elimina un museo por su ID.
     *
     * @param id Identificador del museo a eliminar.
     */
    public void deleteById(String id) {
        museoRepository.deleteById(id);
    }

    /**
     * Busca un museo por su nombre.
     *
     * @param nombre Nombre del museo.
     * @return Un Optional con el museo si existe.
     */
    public Optional<Museo> findMuseoByName(String nombre) {
        return museoRepository.findByNombre(nombre);
    }

    /**
     * Busca museos por provincia.
     *
     * @param province Nombre de la provincia.
     * @return Lista de museos en esa provincia.
     */
    public List<Museo> findByProvince(String province) {
        return museoRepository.findByProvince(province);
    }

    /**
     * Obtiene una lista de todas las provincias distintas que tienen museos.
     * Filtra valores nulos o vacíos.
     *
     * @return Lista ordenada de nombres de provincias.
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
     * Cuenta el número de museos por provincia.
     *
     * @return Mapa con el nombre de la provincia y la cantidad de museos.
     */
    public Map<String, Long> countMuseosByProvince() {
        return museoRepository.findAll().stream()
                .filter(p -> p.getProvince() != null)
                .collect(Collectors.groupingBy(Museo::getProvince, Collectors.counting()));
    }

    /**
     * Actualiza los datos de un museo existente.
     *
     * @param id Identificador del museo a actualizar.
     * @param museoDetails Objeto con los nuevos datos del museo.
     * @return El museo actualizado.
     * @throws org.example.reto4springboot.exceptions.MuseoNotFoundException Si el museo no existe.
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

            return museoRepository.save(museoExistente);
        }).orElseThrow(() -> new org.example.reto4springboot.exceptions.MuseoNotFoundException("No se puede actualizar. Museo no encontrado con ID: " + id));

    }
    // Añade esto a tu MuseoService.java
    public List<Museo> findByNombreContaining(String nombre) {
        return museoRepository.findByNombreContainingIgnoreCase(nombre);
    }
}
