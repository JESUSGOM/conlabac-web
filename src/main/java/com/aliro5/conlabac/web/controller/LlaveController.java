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
        // 1. Seguridad: Verificar si hay sesión activa
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/";
        }

        // 2. Obtener el ID del centro usando el método correcto de tu DTO
        Integer idCentro = usuario.getIdCentro();

        // 3. Cargar la lista de llaves desde el servicio
        model.addAttribute("listaLlaves", llaveService.listarPorCentro(idCentro));

        // 4. Obtener datos del centro para mostrar el nombre en la cabecera
        CentroDTO centro = centroService.obtenerPorId(idCentro);
        model.addAttribute("nombreCentro", (centro != null) ? centro.getDenominacion() : "Centro Aliros");

        // 5. Atributos necesarios para el menú (fragments.html)
        model.addAttribute("activeLink", "llaves");
        model.addAttribute("usuario", usuario);

        return "llaves-list";
    }
}