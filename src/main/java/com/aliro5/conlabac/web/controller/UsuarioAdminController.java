package com.aliro5.conlabac.web.controller;

import com.aliro5.conlabac.web.dto.UsuarioDTO;
import com.aliro5.conlabac.web.service.CentroService;
import com.aliro5.conlabac.web.service.UsuarioAdminService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/usuarios")
public class UsuarioAdminController {

    @Autowired private UsuarioAdminService usuarioService;
    @Autowired private CentroService centroService;

    // Método auxiliar de seguridad
    private boolean esAdmin(HttpSession session) {
        UsuarioDTO u = (UsuarioDTO) session.getAttribute("usuarioLogueado");
        // SOLO ENTRA SI ES TIPO 'Z'
        return u != null && "Z".equalsIgnoreCase(u.getTipo());
    }

    @GetMapping
    public String listar(Model model, HttpSession session) {
        if (!esAdmin(session)) return "redirect:/home";

        model.addAttribute("listaUsuarios", usuarioService.listarTodos());
        return "usuarios-list";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model, HttpSession session) {
        if (!esAdmin(session)) return "redirect:/home";

        UsuarioDTO u = new UsuarioDTO();
        // Valores por defecto
        u.setTipo("U"); // Por defecto creamos usuarios normales (U), el admin lo cambia si quiere

        model.addAttribute("usuarioEdicion", u);
        model.addAttribute("listaCentros", centroService.obtenerTodos()); // Necesitamos elegir centro
        return "usuarios-form";
    }

    @GetMapping("/editar/{dni}")
    public String editar(@PathVariable String dni, Model model, HttpSession session) {
        if (!esAdmin(session)) return "redirect:/home";

        UsuarioDTO u = usuarioService.obtenerPorDni(dni);
        // La clave no la mostramos por seguridad (viajará vacía si no la tocan)
        u.setClave(null);

        model.addAttribute("usuarioEdicion", u);
        model.addAttribute("listaCentros", centroService.obtenerTodos());
        return "usuarios-form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute UsuarioDTO usuario, HttpSession session) {
        if (!esAdmin(session)) return "redirect:/home";

        // Si es una edición y la clave viene vacía, la API sabrá ignorarla
        // (o si la API es simple, sobreescribirá. Ojo con esto en producción).
        // En nuestro caso, como es un admin reseteando claves, siempre enviamos lo que escriba.

        usuarioService.guardar(usuario);
        return "redirect:/admin/usuarios";
    }

    @GetMapping("/eliminar/{dni}")
    public String eliminar(@PathVariable String dni, HttpSession session) {
        if (!esAdmin(session)) return "redirect:/home";

        // Evitar auto-borrado
        UsuarioDTO yo = (UsuarioDTO) session.getAttribute("usuarioLogueado");
        if (yo.getDni().equals(dni)) {
            return "redirect:/admin/usuarios?error=nospuicide";
        }

        usuarioService.eliminar(dni);
        return "redirect:/admin/usuarios";
    }
}