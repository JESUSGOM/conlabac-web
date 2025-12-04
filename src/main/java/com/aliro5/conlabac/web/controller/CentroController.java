package com.aliro5.conlabac.web.controller;

import com.aliro5.conlabac.web.dto.CentroDTO;
import com.aliro5.conlabac.web.service.CentroService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/centros")
public class CentroController {

    @Autowired
    private CentroService centroService;

    // --- LISTAR (READ) ---
    @GetMapping
    public String listarCentros(Model model, HttpSession session) {
        // 1. SEGURIDAD: Verificar si el usuario está logueado
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/"; // Si no hay sesión, volver al login
        }

        // 2. Lógica normal
        var lista = centroService.obtenerTodos();
        model.addAttribute("listaCentros", lista);

        // Pasamos también el usuario al modelo por si queremos mostrar su nombre en el HTML
        model.addAttribute("usuario", session.getAttribute("usuarioLogueado"));

        return "centros-list";
    }

    // --- FORMULARIO NUEVO (CREATE - VISTA) ---
    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/";
        }

        model.addAttribute("centro", new CentroDTO());
        return "centros-form";
    }

    // --- FORMULARIO EDITAR (UPDATE - VISTA) ---
    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable("id") Integer id, Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/";
        }

        CentroDTO centro = centroService.obtenerPorId(id);
        model.addAttribute("centro", centro);
        return "centros-form";
    }

    // --- GUARDAR (CREATE / UPDATE - ACCIÓN) ---
    @PostMapping("/guardar")
    public String guardarCentro(@ModelAttribute CentroDTO centro, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/";
        }

        // Debug: Para confirmar que llega el ID al editar
        System.out.println("--- Guardando Centro. ID: " + centro.getId() + " ---");

        centroService.guardar(centro);
        return "redirect:/centros";
    }

    // --- ELIMINAR (DELETE - ACCIÓN) ---
    @GetMapping("/eliminar/{id}")
    public String eliminarCentro(@PathVariable("id") Integer id, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/";
        }

        centroService.eliminar(id);
        return "redirect:/centros";
    }
}