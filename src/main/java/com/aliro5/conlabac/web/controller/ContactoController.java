package com.aliro5.conlabac.web.controller;

import com.aliro5.conlabac.web.dto.CentroDTO;
import com.aliro5.conlabac.web.dto.ContactoDTO;
import com.aliro5.conlabac.web.dto.UsuarioDTO;
import com.aliro5.conlabac.web.service.CentroService;
import com.aliro5.conlabac.web.service.ContactoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/contactos")
public class ContactoController {

    @Autowired private ContactoService service;
    @Autowired private CentroService centroService;

    @GetMapping
    public String panel(Model model, HttpSession session) {
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/";

        model.addAttribute("listaContactos", service.listar(usuario.getIdCentro()));

        CentroDTO centro = centroService.obtenerPorId(usuario.getIdCentro());
        model.addAttribute("nombreCentro", centro.getDenominacion());
        model.addAttribute("usuario", usuario);

        return "contactos-panel";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model, HttpSession session) {
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/";

        ContactoDTO dto = new ContactoDTO();
        dto.setIdCentro(usuario.getIdCentro());

        model.addAttribute("contacto", dto);
        return "contactos-form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/";

        model.addAttribute("contacto", service.obtener(id));
        return "contactos-form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute ContactoDTO contacto, HttpSession session) {
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/";

        contacto.setIdCentro(usuario.getIdCentro());
        service.guardar(contacto);
        return "redirect:/contactos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/";
        service.eliminar(id);
        return "redirect:/contactos";
    }
}