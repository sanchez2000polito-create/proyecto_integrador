package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table (name = "detalles_pedidos")
public class DetallesPedidos {
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long idDetalles;
    private long idPedido;
    private long idProducto;
    private int cantidad;
    private double precioUnitario;
    public DetallesPedidos() {
    
}
    
    public DetallesPedidos(long idDetalles, long idPedido, long idProducto) {
        this.idDetalles = idDetalles;
        this.idPedido = idPedido;
        this.idProducto = idProducto;
    }
    public DetallesPedidos(long idDetalles, long idPedido, long idProducto, int cantidad, double precioUnitario) {
        this.idDetalles = idDetalles;
        this.idPedido = idPedido;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }
    public void setIdDetalles(long idDetalles) {
        this.idDetalles = idDetalles;
    }
    public void setIdPedido(long idPedido) {
        this.idPedido = idPedido;
    }
    public void setIdProducto(long idProducto) {
        this.idProducto = idProducto;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
    public long getIdDetalles() {
        return idDetalles;
    }
    public long getIdPedido() {
        return idPedido;
    }
    public long getIdProducto() {
        return idProducto;
    }
    public int getCantidad() {
        return cantidad;
    }
    public double getPrecioUnitario() {
        return precioUnitario;
    }
}
