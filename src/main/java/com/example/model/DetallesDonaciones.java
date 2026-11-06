package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "detalles_donaciones")
public class DetallesDonaciones {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idDetaallesDonaciones;
    private long idDonacion;
    private long idProducto;
    private int cantidad;
    public DetallesDonaciones() {
    }
    public DetallesDonaciones(long idDetaallesDonaciones, long idDonacion, long idProducto) {
        this.idDetaallesDonaciones = idDetaallesDonaciones;
        this.idDonacion = idDonacion;
        this.idProducto = idProducto;
    }
    public DetallesDonaciones(long idDetaallesDonaciones, long idDonacion, long idProducto, int cantidad) {
        this.idDetaallesDonaciones = idDetaallesDonaciones;
        this.idDonacion = idDonacion;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
    }
    public void setIdDetaallesDonaciones(long idDetaallesDonaciones) {
        this.idDetaallesDonaciones = idDetaallesDonaciones;
    }
    public void setIdDonacion(long idDonacion) {
        this.idDonacion = idDonacion;
    }
    public void setIdProducto(long idProducto) {
        this.idProducto = idProducto;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    public long getIdDetaallesDonaciones() {
        return idDetaallesDonaciones;
    }
    public long getIdDonacion() {
        return idDonacion;
    }
    public long getIdProducto() {
        return idProducto;
    }
    public int getCantidad() {
        return cantidad;
    }

    

}
