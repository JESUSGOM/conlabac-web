package com.aliro5.conlabac.web.controller;

import com.aliro5.conlabac.web.dto.CentroDTO;
import com.aliro5.conlabac.web.dto.UsuarioDTO;
import com.aliro5.conlabac.web.service.CentroService;
import com.aliro5.conlabac.web.service.LlaveService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/llaves")
public class LlaveController {

    @Autowired
    private LlaveService llaveService;

    @Autowired
    private CentroService centroService;

    @GetMapping
    public String listarInventario(Model model, HttpSession session) {
        // 1. Seguridad: Verificar Login
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/";

        // 2. Obtener datos
        Integer idCentro = usuario.getIdCentro();
        model.addAttribute("listaLlaves", llaveService.listarPorCentro(idCentro));

        // 3. Obtener nombre del centro para el título
        CentroDTO centro = centroService.obtenerPorId(idCentro);
        model.addAttribute("nombreCentro", centro.getDenominacion());

        // 4. Pasar usuario a la vista (para el menú superior)
        model.addAttribute("usuario", usuario);

        return "llaves-list";
    }
}