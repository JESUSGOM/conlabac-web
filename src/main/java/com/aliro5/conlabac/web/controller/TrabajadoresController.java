package com.aliro5.conlabac.web.controller;

import com.aliro5.conlabac.web.dto.CentroDTO;
import com.aliro5.conlabac.web.dto.ProveedorDTO;
import com.aliro5.conlabac.web.dto.UsuarioDTO;
import com.aliro5.conlabac.web.service.CentroService;
import com.aliro5.conlabac.web.service.ProveedorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/trabajadores")
public class TrabajadoresController {

    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private CentroService centroService;

    @GetMapping
    public String listarGlobal(Model model, HttpSession session) {
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/";

        Integer idCentro = usuario.getIdCentro();

        model.addAttribute("listaTrabajadores", proveedorService.listarTodosTrabajadores(idCentro));

        List<ProveedorDTO> empresas = proveedorService.listar(idCentro);
        Map<String, String> mapaEmpresas = empresas.stream()
                .collect(Collectors.toMap(
                        ProveedorDTO::getCif,
                        ProveedorDTO::getDenominacion,
                        (existente, reemplazo) -> existente
                ));

        model.addAttribute("mapaEmpresas", mapaEmpresas);

        CentroDTO centro = centroService.obtenerPorId(idCentro);
        model.addAttribute("nombreCentro", (centro != null) ? centro.getDenominacion() : "Centro Aliros");
        model.addAttribute("usuario", usuario);
        model.addAttribute("activeLink", "trabajadores");

        return "trabajadores-list";
    }
}