package org.example.reto4springboot.controllers;

import org.example.reto4springboot.entities.Museo;
import org.example.reto4springboot.services.MuseoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/web/museos")
public class MuseoWebController {

    private final MuseoService museoService;

    public MuseoWebController(MuseoService museoService) {
        this.museoService = museoService;
    }

    // Ruta para el listado, con parámetro opcional de provincia
    @GetMapping("/lista")
    public String listarMuseos(@RequestParam(required = false) String provincia,
                               @RequestParam(required = false) String nombre, // <-- Añade este parámetro
                               Model model) {
        List<Museo> lista;

        if (nombre != null && !nombre.isEmpty()) {
            // Ahora el service ya conoce este método
            lista = museoService.findByNombreContaining(nombre);
        } else if (provincia != null && !provincia.isEmpty()) {
            lista = museoService.findByProvince(provincia);
        } else {
            lista = museoService.findAll();
        }

        List<String> provincias = museoService.findAllProvinces();
        model.addAttribute("museos", lista);
        model.addAttribute("provincias", provincias);
        return "museos-listado";
    }

    // Ruta para ver el detalle de un museo concreto
    @GetMapping("/detalle/{id}")
    public String detalleMuseo(@PathVariable String id, Model model) {
        Optional<Museo> museoOpt = museoService.findById(id);

        if (museoOpt.isPresent()) {
            model.addAttribute("museo", museoOpt.get());
            return "museo-detalle";
        } else {
            // Si no existe, redirigimos al listado
            return "redirect:/web/museos/lista";
        }
    }
    // Mostrar el formulario para añadir un nuevo museo
    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("museo", new Museo());
        model.addAttribute("provincias", museoService.findAllProvinces());
        return "museo-form";
    }

    // Guardar el museo en la base de datos
    @PostMapping("/guardar")
    public String guardarMuseo(Museo museo) {
        museoService.save(museo);
        return "redirect:/web/museos/lista"; // Volvemos a la lista tras guardar
    }

    // Borrar un museo
    @PostMapping("/eliminar/{id}")
    public String eliminarMuseo(@PathVariable String id) {
        museoService.deleteById(id);
        return "redirect:/web/museos/lista";
    }

}