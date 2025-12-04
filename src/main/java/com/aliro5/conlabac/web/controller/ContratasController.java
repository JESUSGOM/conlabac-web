package com.aliro5.conlabac.web.controller;

import com.aliro5.conlabac.web.dto.*;
import com.aliro5.conlabac.web.service.CentroService;
import com.aliro5.conlabac.web.service.ContratasService;
import com.aliro5.conlabac.web.service.ProveedorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/contratas")
public class ContratasController {

    @Autowired private ContratasService contratasService;
    @Autowired private ProveedorService proveedorService;
    @Autowired private CentroService centroService;

    @GetMapping
    public String panel(Model model, HttpSession session) {
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/";

        Integer idCentro = usuario.getIdCentro();

        // 1. Obtener movimientos activos
        List<MovimientoEmpleadoDTO> movimientos = contratasService.listarActivos(idCentro);

        // 2. Obtener datos maestros para traducir NIFs y CIFs a Nombres
        List<EmpleadoProveedorDTO> trabajadores = proveedorService.listarTodosTrabajadores(idCentro);
        List<ProveedorDTO> empresas = proveedorService.listar(idCentro);

        // Crear mapas para búsqueda rápida
        Map<String, String> mapaTrabajadores = trabajadores.stream()
                .collect(Collectors.toMap(
                        EmpleadoProveedorDTO::getNif,
                        e -> e.getNombre() + " " + e.getApellido1(),
                        (a, b) -> a));

        Map<String, String> mapaEmpresas = empresas.stream()
                .collect(Collectors.toMap(ProveedorDTO::getCif, ProveedorDTO::getDenominacion, (a, b) -> a));

        // 3. Rellenar los nombres en los DTOs de movimientos
        for (MovimientoEmpleadoDTO mov : movimientos) {
            mov.setNombreEmpleadoStr(mapaTrabajadores.getOrDefault(mov.getNifEmpleado(), mov.getNifEmpleado()));
            mov.setNombreEmpresaStr(mapaEmpresas.getOrDefault(mov.getCifProveedor(), mov.getCifProveedor()));
        }

        model.addAttribute("listaMovimientos", movimientos);

        CentroDTO centro = centroService.obtenerPorId(idCentro);
        model.addAttribute("nombreCentro", centro.getDenominacion());
        model.addAttribute("usuario", usuario);

        return "contratas-panel";
    }

    @GetMapping("/entrada")
    public String formEntrada(Model model, HttpSession session) {
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/";

        // Cargar lista de trabajadores autorizados para el combo
        List<EmpleadoProveedorDTO> trabajadores = proveedorService.listarTodosTrabajadores(usuario.getIdCentro());
        model.addAttribute("listaTrabajadores", trabajadores);

        return "contratas-form";
    }

    @PostMapping("/entrada")
    public String registrarEntrada(@RequestParam("empleadoSelect") String nifCifCombinado, HttpSession session) {
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/";

        // El combo envía "NIF|CIF" unidos para saber ambos datos
        String[] partes = nifCifCombinado.split("\\|");
        String nif = partes[0];
        String cif = partes[1];

        contratasService.registrarEntrada(nif, cif, usuario.getIdCentro());
        return "redirect:/contratas";
    }

    @GetMapping("/salida/{id}")
    public String registrarSalida(@PathVariable Integer id, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/";
        contratasService.registrarSalida(id);
        return "redirect:/contratas";
    }
}