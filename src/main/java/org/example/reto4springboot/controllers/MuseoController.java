package org.example.reto4springboot.controllers;

import org.example.reto4springboot.entities.Museo;
import org.example.reto4springboot.services.MuseoService;
import org.example.reto4springboot.exceptions.MuseoNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controlador REST para gestionar las operaciones relacionadas con los museos.
 * Proporciona endpoints para crear, leer, actualizar y eliminar museos,
 * así como consultas específicas por provincia y estadísticas.
 */
@RestController
@RequestMapping("/api/museos")
public class MuseoController {

    private final MuseoService museoService;

    /**
     * Constructor del controlador para la inyección de dependencias.
     *
     * @param museoService Servicio de lógica de negocio para museos.
     */
    public MuseoController(MuseoService museoService) {
        this.museoService = museoService;
    }

    /**
     * Obtiene la lista de todos los museos registrados en el sistema.
     *
     * @return Lista de objetos Museo.
     */
    @GetMapping
    public List<Museo> getAll() {
        return museoService.findAll();
    }

    /**
     * Busca un museo por su identificador único.
     *
     * @param id Identificador del museo.
     * @return ResponseEntity con el museo encontrado.
     * @throws MuseoNotFoundException si no se encuentra el museo con el ID proporcionado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Museo> getById(@PathVariable String id) {
        return museoService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new MuseoNotFoundException("No existe el museo con ID: " + id));
    }

    /**
     * Crea un nuevo museo en el sistema.
     *
     * @param museo Objeto Museo con la información a guardar proporcionado en el cuerpo de la petición.
     * @return ResponseEntity con el museo creado y el estado HTTP 201 (CREATED).
     */
    @PostMapping
    public ResponseEntity<Museo> create(@RequestBody Museo museo) {
        return new ResponseEntity<>(museoService.save(museo), HttpStatus.CREATED);
    }

    /**
     * Elimina un museo existente por su identificador.
     *
     * @param id Identificador del museo a eliminar.
     * @return ResponseEntity con contenido vacío (No Content) si la eliminación es exitosa.
     * @throws MuseoNotFoundException si el ID no corresponde a ningún museo registrado.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if(museoService.findById(id).isEmpty()){
            throw new MuseoNotFoundException("No se puede borrar. ID no encontrado: " + id);
        }
        museoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Busca museos ubicados en una provincia específica.
     *
     * @param provincia Nombre de la provincia para filtrar.
     * @return Lista de museos pertenecientes a la provincia indicada.
     */
    @GetMapping("/provincia/{provincia}")
    public List<Museo> getByProvince(@PathVariable String provincia) {
        return museoService.findByProvince(provincia);
    }

    /**
     * Busca un museo por su nombre exacto.
     *
     * @param nombre Nombre del museo a buscar.
     * @return ResponseEntity con el museo encontrado.
     * @throws MuseoNotFoundException si no existe un museo con dicho nombre.
     */
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Museo> getByName(@PathVariable String nombre) {
        return museoService.findMuseoByName(nombre)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new MuseoNotFoundException("No se encontró el museo: " + nombre));
    }

    /**
     * Obtiene una lista con los nombres de todas las provincias que tienen museos registrados.
     *
     * @return Lista de Strings con los nombres de las provincias.
     */
    @GetMapping("/provincias/lista")
    public List<String> getProvincesList() {
        return museoService.findAllProvinces();
    }

    /**
     * Obtiene estadísticas sobre la cantidad de museos por cada provincia.
     *
     * @return Un Mapa donde la clave es el nombre de la provincia y el valor es el recuento de museos.
     */
    @GetMapping("/stats/provincias")
    public Map<String, Long> getStatsByProvince() {
        return museoService.countMuseosByProvince();
    }

    /**
     * Actualiza la información de un museo existente basándose en su ID.
     *
     * @param id Identificador del museo que se desea modificar.
     * @param museo Objeto Museo que contiene los nuevos datos.
     * @return ResponseEntity con el objeto Museo actualizado y estado HTTP 200.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Museo> update(@PathVariable String id, @RequestBody Museo museo) {
        Museo museoActualizado = museoService.update(id, museo);
        return ResponseEntity.ok(museoActualizado);
    }
}