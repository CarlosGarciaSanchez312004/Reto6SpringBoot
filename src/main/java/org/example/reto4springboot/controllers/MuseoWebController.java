package org.example.reto4springboot.controllers;

import org.example.reto4springboot.entities.Museo;
import org.example.reto4springboot.services.MuseoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Optional;

/**
 * Controlador que gestiona las vistas web para la entidad Museo.
 * Se encarga de la interacción entre la lógica de negocio y las plantillas Thymeleaf.
 */
@Controller
@RequestMapping("/web/museos")
public class MuseoWebController {

    private final MuseoService museoService;

    /**
     * Constructor para la inyección del servicio de museos.
     * * @param museoService El servicio encargado de la lógica de museos.
     */
    public MuseoWebController(MuseoService museoService) {
        this.museoService = museoService;
    }

    /**
     * Obtiene y muestra el listado de museos, permitiendo filtrar por provincia o por nombre.
     * * @param provincia Parámetro opcional para filtrar por provincia.
     * @param nombre Parámetro opcional para filtrar por coincidencia en el nombre.
     * @param model Atributos para pasar a la vista web.
     * @return El nombre de la plantilla de listado de museos.
     */
    @GetMapping("/lista")
    public String listarMuseos(@RequestParam(required = false) String provincia,
                               @RequestParam(required = false) String nombre,
                               Model model) {
        List<Museo> lista;

        if (nombre != null && !nombre.isEmpty()) {
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

    /**
     * Muestra la vista detallada de un museo específico según su identificador.
     * * @param id Identificador del museo.
     * @param model Atributos para la vista.
     * @return El nombre de la plantilla de detalle o una redirección al listado si no se encuentra.
     */
    @GetMapping("/detalle/{id}")
    public String detalleMuseo(@PathVariable String id, Model model) {
        Optional<Museo> museoOpt = museoService.findById(id);

        if (museoOpt.isPresent()) {
            model.addAttribute("museo", museoOpt.get());
            return "museo-detalle";
        } else {
            return "redirect:/web/museos/lista";
        }
    }

    /**
     * Prepara el formulario para la creación de un nuevo museo.
     * * @param model Atributos para inicializar el formulario.
     * @return El nombre de la plantilla del formulario del museo.
     */
    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("museo", new Museo());
        model.addAttribute("provincias", museoService.findAllProvinces());
        return "museo-form";
    }

    /**
     * Recibe los datos de un museo y decide si debe crearlo o actualizarlo en la base de datos.
     * * @param museo El objeto museo vinculado al formulario.
     * @return Redirección al listado de museos una vez guardado.
     */
    @PostMapping("/guardar")
    public String guardarMuseo(@ModelAttribute Museo museo) {
        if (museo.get_id() != null && !museo.get_id().isEmpty()) {
            museoService.update(museo.get_id(), museo);
        } else {
            museoService.save(museo);
        }
        return "redirect:/web/museos/lista";
    }

    /**
     * Elimina un museo de la base de datos mediante su identificador.
     * * @param id El ID del museo a borrar.
     * @return Redirección al listado de museos.
     */
    @PostMapping("/eliminar/{id}")
    public String eliminarMuseo(@PathVariable String id) {
        museoService.deleteById(id);
        return "redirect:/web/museos/lista";
    }

    /**
     * Muestra el formulario de edición para un museo existente.
     * * @param id El identificador del museo que se desea editar.
     * @param model Atributos de la vista con los datos actuales del museo.
     * @return El nombre de la plantilla del formulario o redirección al listado si no se encuentra.
     */
    @GetMapping("/editar/{id}")
    public String editarMuseo(@PathVariable String id, Model model) {
        Optional<Museo> museoOpt = museoService.findById(id);

        if (museoOpt.isPresent()) {
            model.addAttribute("museo", museoOpt.get());
            model.addAttribute("provincias", museoService.findAllProvinces());
            return "museo-form";
        } else {
            return "redirect:/web/museos/lista";
        }
    }

    /**
     * Intenta redirigir a la URL oficial de la web del museo tras verificar su disponibilidad.
     * Si la web no responde correctamente, redirige a una vista de error personalizada.
     * * @param id Identificador del museo.
     * @param model Atributos para pasar a la vista de error si es necesario.
     * @return Redirección a la web externa, a la página de error, o al listado general.
     */
    @GetMapping("/visitar-web/{id}")
    public String visitarWebMuseo(@PathVariable String id, Model model) {
        Optional<Museo> museoOpt = museoService.findById(id);

        if (museoOpt.isPresent() && museoOpt.get().getWeb() != null) {
            String urlWeb = museoOpt.get().getWeb();
            try {
                URL url = new URL(urlWeb);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("HEAD");
                connection.setConnectTimeout(3000);
                int responseCode = connection.getResponseCode();

                if (responseCode >= 200 && responseCode < 400) {
                    return "redirect:" + urlWeb;
                }
            } catch (Exception e) {

            }

            model.addAttribute("museo", museoOpt.get());
            return "museo-error-web";
        }
        return "redirect:/web/museos/lista";
    }
}