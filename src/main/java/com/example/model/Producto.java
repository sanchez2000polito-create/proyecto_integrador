package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codigo;
    private String nombre;
    private String categoria;
    private Double precio;
    private Integer stock;
    private String estado;
    private Long idCategoria;
    private Long idFundacion;
    public Producto() {
    }
    public Producto(Long id, String codigo, String nombre, String categoria, Double precio, Integer stock,
            String estado, Long idCategoria, Long idFundacion) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
        this.estado = estado;
        this.idCategoria = idCategoria;
        this.idFundacion = idFundacion;
    }
    public Producto(String nombre, String categoria, Integer stock, String estado, Long idCategoria, Long idFundacion) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.stock = stock;
        this.estado = estado;
        this.idCategoria = idCategoria;
        this.idFundacion = idFundacion;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public void setPrecio(Double precio) {
        this.precio = precio;
    }
    public void setStock(Integer stock) {
        this.stock = stock;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }
    public void setIdFundacion(Long idFundacion) {
        this.idFundacion = idFundacion;
    }
    public Long getId() {
        return id;
    }
    public String getCodigo() {
        return codigo;
    }
    public String getNombre() {
        return nombre;
    }
    public String getCategoria() {
        return categoria;
    }
    public Double getPrecio() {
        return precio;
    }
    public Integer getStock() {
        return stock;
    }
    public String getEstado() {
        return estado;
    }
    public Long getIdCategoria() {
        return idCategoria;
    }
    public Long getIdFundacion() {
        return idFundacion;
    }

    




    }

