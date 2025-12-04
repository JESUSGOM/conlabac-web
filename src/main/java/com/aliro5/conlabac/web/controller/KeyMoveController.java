package com.aliro5.conlabac.web.controller;

import com.aliro5.conlabac.web.dto.CentroDTO;
import com.aliro5.conlabac.web.dto.KeyMoveDTO;
import com.aliro5.conlabac.web.dto.UsuarioDTO;
import com.aliro5.conlabac.web.service.CentroService;
import com.aliro5.conlabac.web.service.KeyMoveService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/keymoves")
public class KeyMoveController {

    @Autowired private KeyMoveService keyMoveService;
    @Autowired private CentroService centroService;

    // 1. PANTALLA PRINCIPAL: Lista de préstamos activos
    @GetMapping
    public String panelControl(Model model, HttpSession session) {
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/";

        // CAMBIO AQUÍ: Usamos listarActivosHoy en vez de listarActivos
        model.addAttribute("listaPrestamos", keyMoveService.listarActivosHoy(usuario.getIdCentro()));

        CentroDTO centro = centroService.obtenerPorId(usuario.getIdCentro());
        model.addAttribute("nombreCentro", centro.getDenominacion());
        model.addAttribute("usuario", usuario);

        return "keymoves-panel";
    }

    // 2. FORMULARIO: Entregar llave
    @GetMapping("/entregar")
    public String formEntrega(Model model, HttpSession session) {
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/";

        KeyMoveDTO dto = new KeyMoveDTO();
        dto.setIdCentro(usuario.getIdCentro());

        model.addAttribute("keymove", dto);
        // IMPORTANTE: Cargamos solo las llaves LIBRES para el desplegable
        model.addAttribute("llavesLibres", keyMoveService.listarDisponibles(usuario.getIdCentro()));

        return "keymoves-form";
    }

    // 3. ACCIÓN: Guardar entrega
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute KeyMoveDTO keyMove, HttpSession session) {
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/";

        keyMove.setIdCentro(usuario.getIdCentro());
        keyMoveService.entregar(keyMove);
        return "redirect:/keymoves";
    }

    // 4. ACCIÓN: Devolver llave
    @GetMapping("/devolver/{id}")
    public String devolver(@PathVariable Integer id, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/";
        keyMoveService.devolver(id);
        return "redirect:/keymoves";
    }
}