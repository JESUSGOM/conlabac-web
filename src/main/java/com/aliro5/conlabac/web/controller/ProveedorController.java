package com.aliro5.conlabac.web.controller;

import com.aliro5.conlabac.web.dto.CentroDTO;
import com.aliro5.conlabac.web.dto.EmpleadoProveedorDTO;
import com.aliro5.conlabac.web.dto.ProveedorDTO;
import com.aliro5.conlabac.web.dto.UsuarioDTO;
import com.aliro5.conlabac.web.service.CentroService;
import com.aliro5.conlabac.web.service.ProveedorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/proveedores")
public class ProveedorController {

    @Autowired private ProveedorService service;
    @Autowired private CentroService centroService;

    private void addCommonData(Model model, UsuarioDTO usuario) {
        CentroDTO centro = centroService.obtenerPorId(usuario.getIdCentro());
        model.addAttribute("nombreCentro", (centro != null) ? centro.getDenominacion() : "Centro Aliros");
        model.addAttribute("usuario", usuario);
        model.addAttribute("activeLink", "proveedores");
    }

    @GetMapping
    public String listar(Model model, HttpSession session) {
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/";

        model.addAttribute("listaProveedores", service.listar(usuario.getIdCentro()));
        addCommonData(model, usuario);
        return "proveedores-panel";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model, HttpSession session) {
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/";

        ProveedorDTO dto = new ProveedorDTO();
        dto.setIdCentro(usuario.getIdCentro());
        model.addAttribute("proveedor", dto);
        addCommonData(model, usuario);
        return "proveedores-form";
    }

    @GetMapping("/editar/{cif}")
    public String editar(@PathVariable String cif, Model model, HttpSession session) {
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/";

        model.addAttribute("proveedor", service.obtener(cif, usuario.getIdCentro()));
        addCommonData(model, usuario);
        return "proveedores-form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute ProveedorDTO proveedor, HttpSession session) {
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/";

        proveedor.setIdCentro(usuario.getIdCentro());
        service.guardar(proveedor);
        return "redirect:/proveedores";
    }

    @GetMapping("/{cif}/empleados")
    public String listarEmpleados(@PathVariable String cif, Model model, HttpSession session) {
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/";

        model.addAttribute("listaEmpleados", service.listarEmpleados(cif, usuario.getIdCentro()));
        model.addAttribute("proveedor", service.obtener(cif, usuario.getIdCentro()));
        addCommonData(model, usuario);
        return "proveedores-empleados-list";
    }

    @GetMapping("/{cif}/empleados/nuevo")
    public String nuevoEmpleado(@PathVariable String cif, Model model, HttpSession session) {
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/";

        EmpleadoProveedorDTO emp = new EmpleadoProveedorDTO();
        emp.setCifProveedor(cif);
        emp.setIdCentro(usuario.getIdCentro());

        model.addAttribute("empleado", emp);
        model.addAttribute("proveedor", service.obtener(cif, usuario.getIdCentro()));
        addCommonData(model, usuario);
        return "proveedores-empleados-form";
    }

    @PostMapping("/empleados/guardar")
    public String guardarEmpleado(@ModelAttribute EmpleadoProveedorDTO empleado, HttpSession session) {
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/";

        empleado.setIdCentro(usuario.getIdCentro());
        service.guardarEmpleado(empleado);
        return "redirect:/proveedores/" + empleado.getCifProveedor() + "/empleados";
    }

    @GetMapping("/empleados/eliminar/{id}")
    public String eliminarEmpleado(@PathVariable Integer id, @RequestParam String cif, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/";
        service.eliminarEmpleado(id);
        return "redirect:/proveedores/" + cif + "/empleados";
    }
}