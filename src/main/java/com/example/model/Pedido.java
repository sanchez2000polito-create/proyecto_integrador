package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;



@Entity 
@Table (name = "pedidos")  
public class Pedido {
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long idPedido;
    private long idUsuario;
    private String fecha;
    private String estado;
    private String metodoPago;
    private String dirreccionEntrega;
    public Pedido() {
    }

    public Pedido(long idPedido, long idUsuario, String fecha, String estado, String metodoPago, String dirreccionEntrega) {
        this.idPedido = idPedido;
        this.idUsuario = idUsuario;
        this.fecha = fecha;
        this.estado = estado;
        this.metodoPago = metodoPago;
        this.dirreccionEntrega = dirreccionEntrega;
    }

    public void setIdPedido(long idPedido) {
        this.idPedido = idPedido;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public void setDirreccionEntrega(String dirreccionEntrega) {
        this.dirreccionEntrega = dirreccionEntrega;
    }

    public long getIdPedido() {
        return idPedido;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public String getFecha() {
        return fecha;
    }

    public String getEstado() {
        return estado;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public String getDirreccionEntrega() {
        return dirreccionEntrega;
    }
    
    
    
}
