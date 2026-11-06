package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table (name = "usuario_rol")
public class UsuarioRol {
    
    @Id
    private Long idUsuario;
    private Long idRol;

    // Constructor vacío
    public UsuarioRol() {
    }

    // Constructor completo
    public UsuarioRol(Long idUsuario, Long idRol) {
        this.idUsuario = idUsuario;
        this.idRol = idRol;
    }

    // Getters y setters
    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

}
