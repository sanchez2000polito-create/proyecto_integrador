package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table (name = "tiendas")
public class Tiendas {
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long idTienda;
    private long idUsuario;
    private String nombreTienda;
    private String ciudad;

    public Tiendas() {
    }

    public Tiendas(long idTienda, long idUsuario, String nombreTienda, String ciudad) {
        this.idTienda = idTienda;
        this.idUsuario = idUsuario;
        this.nombreTienda = nombreTienda;
        this.ciudad = ciudad;
    }

    public void setIdTienda(long idTienda) {
        this.idTienda = idTienda;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setNombreTienda(String nombreTienda) {
        this.nombreTienda = nombreTienda;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public long getIdTienda() {
        return idTienda;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public String getNombreTienda() {
        return nombreTienda;
    }

    public String getCiudad() {
        return ciudad;
    }

    
    
}
