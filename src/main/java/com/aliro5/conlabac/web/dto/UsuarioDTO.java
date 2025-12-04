package com.aliro5.conlabac.web.dto;

public class UsuarioDTO {
    private String dni;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String tipo;      // Aquí es donde viaja el valor 'Z' (Admin) o 'U' (Vigilante)
    private Integer idCentro;

    // --- NUEVO CAMPO IMPRESCINDIBLE PARA EL MÓDULO ADMIN ---
    // Este campo se usa para enviar la contraseña nueva al crear o resetear un usuario.
    // Al leer usuarios de la BD, este campo vendrá vacío por seguridad.
    private String clave;

    public UsuarioDTO() {
    }

    // --- GETTERS Y SETTERS ---

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido1() { return apellido1; }
    public void setApellido1(String apellido1) { this.apellido1 = apellido1; }

    public String getApellido2() { return apellido2; }
    public void setApellido2(String apellido2) { this.apellido2 = apellido2; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public Integer getIdCentro() { return idCentro; }
    public void setIdCentro(Integer idCentro) { this.idCentro = idCentro; }

    // --- Getters y Setters para la Clave ---
    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }
}