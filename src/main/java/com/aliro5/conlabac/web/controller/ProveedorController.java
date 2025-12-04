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

    @GetMapping
    public String listar(Model model, HttpSession session) {
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/";

        model.addAttribute("listaProveedores", service.listar(usuario.getIdCentro()));

        CentroDTO centro = centroService.obtenerPorId(usuario.getIdCentro());
        model.addAttribute("nombreCentro", centro.getDenominacion());
        model.addAttribute("usuario", usuario);

        return "proveedores-panel";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model, HttpSession session) {
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/";

        ProveedorDTO dto = new ProveedorDTO();
        dto.setIdCentro(usuario.getIdCentro());

        model.addAttribute("proveedor", dto);
        return "proveedores-form";
    }

    @GetMapping("/editar/{cif}")
    public String editar(@PathVariable String cif, Model model, HttpSession session) {
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/";

        model.addAttribute("proveedor", service.obtener(cif, usuario.getIdCentro()));
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

    // 1. Listar empleados de un proveedor
    @GetMapping("/{cif}/empleados")
    public String listarEmpleados(@PathVariable String cif, Model model, HttpSession session) {
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/";

        // Obtenemos los empleados
        model.addAttribute("listaEmpleados", service.listarEmpleados(cif, usuario.getIdCentro()));

        // Obtenemos datos de la empresa para el título
        model.addAttribute("proveedor", service.obtener(cif, usuario.getIdCentro()));

        model.addAttribute("usuario", usuario);
        return "proveedores-empleados-list"; // Nueva vista
    }

    // 2. Nuevo Empleado (Pre-rellenamos CIF y Centro)
    @GetMapping("/{cif}/empleados/nuevo")
    public String nuevoEmpleado(@PathVariable String cif, Model model, HttpSession session) {
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/";

        EmpleadoProveedorDTO emp = new EmpleadoProveedorDTO();
        emp.setCifProveedor(cif); // Vinculamos a la empresa
        emp.setIdCentro(usuario.getIdCentro());

        model.addAttribute("empleado", emp);
        // Pasamos datos del proveedor para mostrar el nombre en el formulario
        model.addAttribute("proveedor", service.obtener(cif, usuario.getIdCentro()));

        return "proveedores-empleados-form"; // Nueva vista
    }

    // 3. Guardar Empleado
    @PostMapping("/empleados/guardar")
    public String guardarEmpleado(@ModelAttribute EmpleadoProveedorDTO empleado, HttpSession session) {
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/";

        empleado.setIdCentro(usuario.getIdCentro());
        service.guardarEmpleado(empleado);

        // Redirigimos a la lista de empleados de ESA empresa
        return "redirect:/proveedores/" + empleado.getCifProveedor() + "/empleados";
    }

    // 4. Eliminar Empleado
    @GetMapping("/empleados/eliminar/{id}")
    public String eliminarEmpleado(@PathVariable Integer id, @RequestParam String cif, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/";

        service.eliminarEmpleado(id);
        return "redirect:/proveedores/" + cif + "/empleados";
    }
}