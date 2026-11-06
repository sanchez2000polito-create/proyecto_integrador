package com.example;

import java.time.LocalDateTime;

import com.example.model.Categoria;
import com.example.model.DetallesDonaciones;
import com.example.model.DetallesPedidos;
import com.example.model.Fundacion;
import com.example.model.Pedido;
import com.example.model.Producto;
import com.example.model.Proveedor;
import com.example.model.Rol;
import com.example.model.Tiendas;
import com.example.model.Usuario;
import com.example.model.UsuarioRol;
import com.example.service.CrudService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Gestión CRUD - Sistema de Inventario")
@Route("")
public class MainView extends VerticalLayout {

    private final CrudService crudService;

    public MainView(CrudService crudService) {
        this.crudService = crudService;
        setSizeFull();
        setPadding(true);
        setSpacing(true);

        TabSheet tabs = new TabSheet();
        tabs.setWidthFull();
        tabs.add("Productos", crearSeccionProductos());
        tabs.add("Categorías", crearSeccionCategorias());
        tabs.add("Tiendas", crearSeccionTiendas());
        tabs.add("Pedidos", crearSeccionPedidos());
        tabs.add("Detalles de pedidos", crearSeccionDetallesPedidos());
        tabs.add("Detalles de donaciones", crearSeccionDetallesDonaciones());
        tabs.add("Fundaciones", crearSeccionFundaciones());
        tabs.add("Proveedores", crearSeccionProveedores());
        tabs.add("Usuarios", crearSeccionUsuarios());
        tabs.add("Roles", crearSeccionRoles());
        tabs.add("Usuarios-Roles", crearSeccionUsuariosRoles());

        add(new H2("Sistema de Inventario - CRUD"), tabs);
        //<theme-editor-local-classname>
        addClassName("main-view-vertical-layout-1");
    }

    // ==========================================
    // 1. PRODUCTOS
    // ==========================================
    private Component crearSeccionProductos() {
        VerticalLayout layout = new VerticalLayout();
        TextField idField = new TextField("ID");
        TextField codigoField = new TextField("Código");
        TextField nombreField = new TextField("Nombre");
        TextField categoriaField = new TextField("Categoría");
        TextField precioField = new TextField("Precio");
        TextField stockField = new TextField("Stock");
        TextField estadoField = new TextField("Estado");
        TextField idCategoriaField = new TextField("ID de categoría");
        TextField idFundacionField = new TextField("ID de fundación");

        FormLayout form = new FormLayout(
            idField, codigoField, nombreField, categoriaField, precioField, stockField,
            estadoField, idCategoriaField, idFundacionField
        );

        Grid<Producto> grid = new Grid<>(Producto.class, false);
        grid.addColumn(Producto::getId).setHeader("ID").setAutoWidth(true);
        grid.addColumn(Producto::getCodigo).setHeader("Código").setAutoWidth(true);
        grid.addColumn(Producto::getNombre).setHeader("Nombre").setAutoWidth(true);
        grid.addColumn(Producto::getCategoria).setHeader("Categoría").setAutoWidth(true);
        grid.addColumn(Producto::getPrecio).setHeader("Precio").setAutoWidth(true);
        grid.addColumn(Producto::getStock).setHeader("Stock").setAutoWidth(true);
        grid.addColumn(Producto::getEstado).setHeader("Estado").setAutoWidth(true);
        grid.addColumn(Producto::getIdCategoria).setHeader("ID de categoría").setAutoWidth(true);
        grid.addColumn(Producto::getIdFundacion).setHeader("ID de fundación").setAutoWidth(true);

        Runnable refrescar = () -> grid.setItems(crudService.listarTodos(Producto.class));
        refrescar.run();

        grid.asSingleSelect().addValueChangeListener(e -> {
            Producto p = e.getValue();
            if (p != null) {
                idField.setValue(p.getId() != null ? p.getId().toString() : "");
                codigoField.setValue(p.getCodigo() != null ? p.getCodigo() : "");
                nombreField.setValue(p.getNombre() != null ? p.getNombre() : "");
                categoriaField.setValue(p.getCategoria() != null ? p.getCategoria() : "");
                precioField.setValue(p.getPrecio() != null ? p.getPrecio().toString() : "");
                stockField.setValue(p.getStock() != null ? p.getStock().toString() : "");
                estadoField.setValue(p.getEstado() != null ? p.getEstado() : "");
                idCategoriaField.setValue(p.getIdCategoria() != null ? p.getIdCategoria().toString() : "");
                idFundacionField.setValue(p.getIdFundacion() != null ? p.getIdFundacion().toString() : "");
            }
        });

        Button btnCrear = new Button("Crear", e -> {
            Producto p = new Producto();
            p.setCodigo(codigoField.getValue());
            p.setNombre(nombreField.getValue());
            p.setCategoria(categoriaField.getValue());
            p.setPrecio(parseDouble(precioField.getValue()));
            p.setStock(parseInt(stockField.getValue()));
            p.setEstado(estadoField.getValue());
            p.setIdCategoria(parseId(idCategoriaField));
            p.setIdFundacion(parseId(idFundacionField));
            crudService.guardar(p);
            refrescar.run();
            idField.setValue(p.getId() != null ? p.getId().toString() : "");
            Notification.show("Producto guardado con ID: " + p.getId());
        });
        btnCrear.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button btnConsultar = new Button("Consultar", e -> {
            Producto p = crudService.buscarPorId(Producto.class, parseId(idField));
            if (p != null) {
                grid.asSingleSelect().setValue(p);
                Notification.show("Encontrado: " + p.getNombre());
            } else {
                Notification.show("No encontrado");
            }
        });

        Button btnActualizar = new Button("Actualizar", e -> {
            Producto p = crudService.buscarPorId(Producto.class, parseId(idField));
            if (p != null) {
                p.setCodigo(codigoField.getValue());
                p.setNombre(nombreField.getValue());
                p.setCategoria(categoriaField.getValue());
                p.setPrecio(parseDouble(precioField.getValue()));
                p.setStock(parseInt(stockField.getValue()));
                p.setEstado(estadoField.getValue());
                p.setIdCategoria(parseId(idCategoriaField));
                p.setIdFundacion(parseId(idFundacionField));
                crudService.actualizar(p);
                refrescar.run();
                Notification.show("Producto actualizado");
            } else {
                Notification.show("ID no encontrado");
            }
        });

        Button btnEliminar = new Button("Eliminar", e -> {
            crudService.eliminar(Producto.class, parseId(idField));
            refrescar.run();
            limpiar(
                idField, codigoField, nombreField, categoriaField, precioField, stockField,
                estadoField, idCategoriaField, idFundacionField
            );
            Notification.show("Producto eliminado");
        });
        btnEliminar.addThemeVariants(ButtonVariant.LUMO_ERROR);

        Button btnLimpiar = new Button("Limpiar", e -> {
            limpiar(
                idField, codigoField, nombreField, categoriaField, precioField, stockField,
                estadoField, idCategoriaField, idFundacionField
            );
            grid.asSingleSelect().clear();
        });

        layout.add(form, crearAcciones(btnCrear, btnConsultar, btnActualizar, btnEliminar, btnLimpiar), grid);
        return layout;
    }

    // ==========================================
    // 2. CATEGORÍAS
    // ==========================================
    private Component crearSeccionCategorias() {
        VerticalLayout layout = new VerticalLayout();
        TextField idField = new TextField("ID");
        TextField nombreField = new TextField("Nombre");
        TextField descripcionField = new TextField("Descripción");
        TextField estadoField = new TextField("Estado");

        FormLayout form = new FormLayout(idField, nombreField, descripcionField, estadoField);

        Grid<Categoria> grid = new Grid<>(Categoria.class, false);
        grid.addColumn(Categoria::getId).setHeader("ID").setAutoWidth(true);
        grid.addColumn(Categoria::getNombre).setHeader("Nombre").setAutoWidth(true);
        grid.addColumn(Categoria::getDescripcion).setHeader("Descripción").setAutoWidth(true);
        grid.addColumn(Categoria::getEstado).setHeader("Estado").setAutoWidth(true);

        Runnable refrescar = () -> grid.setItems(crudService.listarTodos(Categoria.class));
        refrescar.run();

        grid.asSingleSelect().addValueChangeListener(e -> {
            Categoria c = e.getValue();
            if (c != null) {
                idField.setValue(c.getId() != null ? c.getId().toString() : "");
                nombreField.setValue(c.getNombre() != null ? c.getNombre() : "");
                descripcionField.setValue(c.getDescripcion() != null ? c.getDescripcion() : "");
                estadoField.setValue(c.getEstado() != null ? c.getEstado() : "");
            }
        });

        Button btnCrear = new Button("Crear", e -> {
            Categoria c = new Categoria(nombreField.getValue(), descripcionField.getValue(), estadoField.getValue());
            crudService.guardar(c);
            refrescar.run();
            idField.setValue(c.getId() != null ? c.getId().toString() : "");
            Notification.show("Categoría guardada con ID: " + c.getId());
        });
        btnCrear.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button btnConsultar = new Button("Consultar", e -> {
            Categoria c = crudService.buscarPorId(Categoria.class, parseId(idField));
            if (c != null) {
                grid.asSingleSelect().setValue(c);
                Notification.show("Encontrada: " + c.getNombre());
            } else {
                Notification.show("No encontrada");
            }
        });

        Button btnActualizar = new Button("Actualizar", e -> {
            Categoria c = crudService.buscarPorId(Categoria.class, parseId(idField));
            if (c != null) {
                c.setNombre(nombreField.getValue());
                c.setDescripcion(descripcionField.getValue());
                c.setEstado(estadoField.getValue());
                crudService.actualizar(c);
                refrescar.run();
                Notification.show("Categoría actualizada");
            } else {
                Notification.show("ID no encontrado");
            }
        });

        Button btnEliminar = new Button("Eliminar", e -> {
            crudService.eliminar(Categoria.class, parseId(idField));
            refrescar.run();
            limpiar(idField, nombreField, descripcionField, estadoField);
            Notification.show("Categoría eliminada");
        });
        btnEliminar.addThemeVariants(ButtonVariant.LUMO_ERROR);

        Button btnLimpiar = new Button("Limpiar", e -> {
            limpiar(idField, nombreField, descripcionField, estadoField);
            grid.asSingleSelect().clear();
        });

        layout.add(form, crearAcciones(btnCrear, btnConsultar, btnActualizar, btnEliminar, btnLimpiar), grid);
        return layout;
    }

    // ==========================================
    // 3. PROVEEDORES
    // ==========================================
    private Component crearSeccionProveedores() {
        VerticalLayout layout = new VerticalLayout();
        TextField idField = new TextField("ID");
        TextField nombreField = new TextField("Nombre");
        TextField contactoField = new TextField("Contacto");
        TextField telefonoField = new TextField("Teléfono");
        TextField emailField = new TextField("Email");
        TextField direccionField = new TextField("Dirección");

        FormLayout form = new FormLayout(idField, nombreField, contactoField, telefonoField, emailField, direccionField);

        Grid<Proveedor> grid = new Grid<>(Proveedor.class, false);
        grid.addColumn(Proveedor::getId).setHeader("ID").setAutoWidth(true);
        grid.addColumn(Proveedor::getNombre).setHeader("Nombre").setAutoWidth(true);
        grid.addColumn(Proveedor::getContacto).setHeader("Contacto").setAutoWidth(true);
        grid.addColumn(Proveedor::getTelefono).setHeader("Teléfono").setAutoWidth(true);
        grid.addColumn(Proveedor::getEmail).setHeader("Email").setAutoWidth(true);
        grid.addColumn(Proveedor::getDireccion).setHeader("Dirección").setAutoWidth(true);

        Runnable refrescar = () -> grid.setItems(crudService.listarTodos(Proveedor.class));
        refrescar.run();

        grid.asSingleSelect().addValueChangeListener(e -> {
            Proveedor p = e.getValue();
            if (p != null) {
                idField.setValue(p.getId() != null ? p.getId().toString() : "");
                nombreField.setValue(p.getNombre() != null ? p.getNombre() : "");
                contactoField.setValue(p.getContacto() != null ? p.getContacto() : "");
                telefonoField.setValue(p.getTelefono() != null ? p.getTelefono() : "");
                emailField.setValue(p.getEmail() != null ? p.getEmail() : "");
                direccionField.setValue(p.getDireccion() != null ? p.getDireccion() : "");
            }
        });

        Button btnCrear = new Button("Crear", e -> {
            Proveedor p = new Proveedor(
                nombreField.getValue(),
                contactoField.getValue(),
                telefonoField.getValue(),
                emailField.getValue(),
                direccionField.getValue()
            );
            crudService.guardar(p);
            refrescar.run();
            idField.setValue(p.getId() != null ? p.getId().toString() : "");
            Notification.show("Proveedor guardado con ID: " + p.getId());
        });
        btnCrear.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button btnConsultar = new Button("Consultar", e -> {
            Proveedor p = crudService.buscarPorId(Proveedor.class, parseId(idField));
            if (p != null) {
                grid.asSingleSelect().setValue(p);
                Notification.show("Encontrado: " + p.getNombre());
            } else {
                Notification.show("No encontrado");
            }
        });

        Button btnActualizar = new Button("Actualizar", e -> {
            Proveedor p = crudService.buscarPorId(Proveedor.class, parseId(idField));
            if (p != null) {
                p.setNombre(nombreField.getValue());
                p.setContacto(contactoField.getValue());
                p.setTelefono(telefonoField.getValue());
                p.setEmail(emailField.getValue());
                p.setDireccion(direccionField.getValue());
                crudService.actualizar(p);
                refrescar.run();
                Notification.show("Proveedor actualizado");
            } else {
                Notification.show("ID no encontrado");
            }
        });

        Button btnEliminar = new Button("Eliminar", e -> {
            crudService.eliminar(Proveedor.class, parseId(idField));
            refrescar.run();
            limpiar(idField, nombreField, contactoField, telefonoField, emailField, direccionField);
            Notification.show("Proveedor eliminado");
        });
        btnEliminar.addThemeVariants(ButtonVariant.LUMO_ERROR);

        Button btnLimpiar = new Button("Limpiar", e -> {
            limpiar(idField, nombreField, contactoField, telefonoField, emailField, direccionField);
            grid.asSingleSelect().clear();
        });

        layout.add(form, crearAcciones(btnCrear, btnConsultar, btnActualizar, btnEliminar, btnLimpiar), grid);
        return layout;
    }

    // ==========================================
    // 4. USUARIOS
    // ==========================================
    private Component crearSeccionUsuarios() {
        VerticalLayout layout = new VerticalLayout();
        TextField idField = new TextField("ID");
        TextField nombreField = new TextField("Nombre");
        TextField apellidoField = new TextField("Apellido");
        TextField emailField = new TextField("Email");
        TextField telefonoField = new TextField("Teléfono");
        TextField direccionField = new TextField("Dirección");
        TextField estadoField = new TextField("Estado");
        TextField fechaRegistroField = new TextField("Fecha de registro");
        fechaRegistroField.setReadOnly(true);

        FormLayout form = new FormLayout(
            idField, nombreField, apellidoField, emailField, telefonoField, direccionField,
            estadoField, fechaRegistroField
        );

        Grid<Usuario> grid = new Grid<>(Usuario.class, false);
        grid.addColumn(Usuario::getId).setHeader("ID").setAutoWidth(true);
        grid.addColumn(Usuario::getNombre).setHeader("Nombre").setAutoWidth(true);
        grid.addColumn(Usuario::getApellido).setHeader("Apellido").setAutoWidth(true);
        grid.addColumn(Usuario::getEmail).setHeader("Email").setAutoWidth(true);
        grid.addColumn(Usuario::getTelefono).setHeader("Teléfono").setAutoWidth(true);
        grid.addColumn(Usuario::getEstado).setHeader("Estado").setAutoWidth(true);
        grid.addColumn(Usuario::getFechaRegistro).setHeader("Fecha de registro").setAutoWidth(true);

        Runnable refrescar = () -> grid.setItems(crudService.listarTodos(Usuario.class));
        refrescar.run();

        grid.asSingleSelect().addValueChangeListener(e -> {
            Usuario u = e.getValue();
            if (u != null) {
                idField.setValue(u.getId() != null ? u.getId().toString() : "");
                nombreField.setValue(u.getNombre() != null ? u.getNombre() : "");
                apellidoField.setValue(u.getApellido() != null ? u.getApellido() : "");
                emailField.setValue(u.getEmail() != null ? u.getEmail() : "");
                telefonoField.setValue(u.getTelefono() != null ? u.getTelefono() : "");
                direccionField.setValue(u.getDireccion() != null ? u.getDireccion() : "");
                estadoField.setValue(u.getEstado() != null ? u.getEstado() : "");
                fechaRegistroField.setValue(
                    u.getFechaRegistro() != null ? u.getFechaRegistro().toString() : ""
                );
            }
        });

        Button btnCrear = new Button("Crear", e -> {
            LocalDateTime fechaRegistro = LocalDateTime.now();
            Usuario u = new Usuario(
                nombreField.getValue(),
                apellidoField.getValue(),
                emailField.getValue(),
                telefonoField.getValue(),
                direccionField.getValue(),
                estadoField.getValue(),
                fechaRegistro
            );
            crudService.guardar(u);
            refrescar.run();
            idField.setValue(u.getId() != null ? u.getId().toString() : "");
            fechaRegistroField.setValue(fechaRegistro.toString());
            Notification.show("Usuario guardado con ID: " + u.getId());
        });
        btnCrear.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button btnConsultar = new Button("Consultar", e -> {
            Usuario u = crudService.buscarPorId(Usuario.class, parseId(idField));
            if (u != null) {
                grid.asSingleSelect().setValue(u);
                Notification.show("Encontrado: " + u.getNombre() + " " + u.getApellido());
            } else {
                Notification.show("No encontrado");
            }
        });

        Button btnActualizar = new Button("Actualizar", e -> {
            Usuario u = crudService.buscarPorId(Usuario.class, parseId(idField));
            if (u != null) {
                u.setNombre(nombreField.getValue());
                u.setApellido(apellidoField.getValue());
                u.setEmail(emailField.getValue());
                u.setTelefono(telefonoField.getValue());
                u.setDireccion(direccionField.getValue());
                u.setEstado(estadoField.getValue());
                crudService.actualizar(u);
                refrescar.run();
                Notification.show("Usuario actualizado");
            } else {
                Notification.show("ID no encontrado");
            }
        });

        Button btnEliminar = new Button("Eliminar", e -> {
            crudService.eliminar(Usuario.class, parseId(idField));
            refrescar.run();
            limpiar(
                idField, nombreField, apellidoField, emailField, telefonoField, direccionField,
                estadoField, fechaRegistroField
            );
            Notification.show("Usuario eliminado");
        });
        btnEliminar.addThemeVariants(ButtonVariant.LUMO_ERROR);

        Button btnLimpiar = new Button("Limpiar", e -> {
            limpiar(
                idField, nombreField, apellidoField, emailField, telefonoField, direccionField,
                estadoField, fechaRegistroField
            );
            grid.asSingleSelect().clear();
        });

        layout.add(form, crearAcciones(btnCrear, btnConsultar, btnActualizar, btnEliminar, btnLimpiar), grid);
        return layout;
    }

    // ==========================================
    // 5. FUNDACIONES
    // ==========================================
    private Component crearSeccionFundaciones() {
        VerticalLayout layout = new VerticalLayout();
        TextField idField = new TextField("ID");
        TextField nombreField = new TextField("Nombre");
        TextField nitField = new TextField("NIT");
        TextField telefonoField = new TextField("Teléfono");
        TextField direccionField = new TextField("Dirección");
        TextField descripcionField = new TextField("Descripción");
        TextField estadoField = new TextField("Estado");

        FormLayout form = new FormLayout(
            idField, nombreField, nitField, telefonoField, direccionField, descripcionField, estadoField
        );

        Grid<Fundacion> grid = new Grid<>(Fundacion.class, false);
        grid.addColumn(Fundacion::getId).setHeader("ID").setAutoWidth(true);
        grid.addColumn(Fundacion::getNombre).setHeader("Nombre").setAutoWidth(true);
        grid.addColumn(Fundacion::getNit).setHeader("NIT").setAutoWidth(true);
        grid.addColumn(Fundacion::getTelefono).setHeader("Teléfono").setAutoWidth(true);
        grid.addColumn(Fundacion::getDireccion).setHeader("Dirección").setAutoWidth(true);
        grid.addColumn(Fundacion::getDescripcion).setHeader("Descripción").setAutoWidth(true);
        grid.addColumn(Fundacion::getEstado).setHeader("Estado").setAutoWidth(true);

        Runnable refrescar = () -> grid.setItems(crudService.listarTodos(Fundacion.class));
        refrescar.run();

        grid.asSingleSelect().addValueChangeListener(e -> {
            Fundacion f = e.getValue();
            if (f != null) {
                idField.setValue(f.getId() != null ? f.getId().toString() : "");
                nombreField.setValue(f.getNombre() != null ? f.getNombre() : "");
                nitField.setValue(f.getNit() != null ? f.getNit() : "");
                telefonoField.setValue(f.getTelefono() != null ? f.getTelefono() : "");
                direccionField.setValue(f.getDireccion() != null ? f.getDireccion() : "");
                descripcionField.setValue(f.getDescripcion() != null ? f.getDescripcion() : "");
                estadoField.setValue(f.getEstado() != null ? f.getEstado() : "");
            }
        });

        Button btnCrear = new Button("Crear", e -> {
            Fundacion f = new Fundacion(
                nombreField.getValue(), nitField.getValue(), telefonoField.getValue(),
                direccionField.getValue(), descripcionField.getValue(), estadoField.getValue()
            );
            crudService.guardar(f);
            refrescar.run();
            idField.setValue(f.getId() != null ? f.getId().toString() : "");
            Notification.show("Fundación guardada con ID: " + f.getId());
        });
        btnCrear.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button btnConsultar = new Button("Consultar", e -> {
            Fundacion f = crudService.buscarPorId(Fundacion.class, parseId(idField));
            if (f != null) {
                grid.asSingleSelect().setValue(f);
                Notification.show("Encontrada: " + f.getNombre());
            } else {
                Notification.show("No encontrada");
            }
        });

        Button btnActualizar = new Button("Actualizar", e -> {
            Fundacion f = crudService.buscarPorId(Fundacion.class, parseId(idField));
            if (f != null) {
                f.setNombre(nombreField.getValue());
                f.setNit(nitField.getValue());
                f.setTelefono(telefonoField.getValue());
                f.setDireccion(direccionField.getValue());
                f.setDescripcion(descripcionField.getValue());
                f.setEstado(estadoField.getValue());
                crudService.actualizar(f);
                refrescar.run();
                Notification.show("Fundación actualizada");
            } else {
                Notification.show("ID no encontrado");
            }
        });

        Button btnEliminar = new Button("Eliminar", e -> {
            crudService.eliminar(Fundacion.class, parseId(idField));
            refrescar.run();
            limpiar(idField, nombreField, nitField, telefonoField, direccionField, descripcionField, estadoField);
            Notification.show("Fundación eliminada");
        });
        btnEliminar.addThemeVariants(ButtonVariant.LUMO_ERROR);

        Button btnLimpiar = new Button("Limpiar", e -> {
            limpiar(idField, nombreField, nitField, telefonoField, direccionField, descripcionField, estadoField);
            grid.asSingleSelect().clear();
        });

        layout.add(form, crearAcciones(btnCrear, btnConsultar, btnActualizar, btnEliminar, btnLimpiar), grid);
        return layout;
    }

    // ==========================================
    // 6. ROLES
    // ==========================================
    private Component crearSeccionRoles() {
        VerticalLayout layout = new VerticalLayout();
        TextField idField = new TextField("ID");
        TextField nombreField = new TextField("Nombre");
        TextField descripcionField = new TextField("Descripción");
        TextField estadoField = new TextField("Estado");

        FormLayout form = new FormLayout(idField, nombreField, descripcionField, estadoField);

        Grid<Rol> grid = new Grid<>(Rol.class, false);
        grid.addColumn(Rol::getId).setHeader("ID").setAutoWidth(true);
        grid.addColumn(Rol::getNombre).setHeader("Nombre").setAutoWidth(true);
        grid.addColumn(Rol::getDescripcion).setHeader("Descripción").setAutoWidth(true);
        grid.addColumn(Rol::getEstado).setHeader("Estado").setAutoWidth(true);

        Runnable refrescar = () -> grid.setItems(crudService.listarTodos(Rol.class));
        refrescar.run();

        grid.asSingleSelect().addValueChangeListener(e -> {
            Rol r = e.getValue();
            if (r != null) {
                idField.setValue(r.getId() != null ? r.getId().toString() : "");
                nombreField.setValue(r.getNombre() != null ? r.getNombre() : "");
                descripcionField.setValue(r.getDescripcion() != null ? r.getDescripcion() : "");
                estadoField.setValue(r.getEstado() != null ? r.getEstado() : "");
            }
        });

        Button btnCrear = new Button("Crear", e -> {
            Rol r = new Rol(nombreField.getValue(), descripcionField.getValue(), estadoField.getValue());
            crudService.guardar(r);
            refrescar.run();
            idField.setValue(r.getId() != null ? r.getId().toString() : "");
            Notification.show("Rol guardado con ID: " + r.getId());
        });
        btnCrear.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button btnConsultar = new Button("Consultar", e -> {
            Rol r = crudService.buscarPorId(Rol.class, parseId(idField));
            if (r != null) {
                grid.asSingleSelect().setValue(r);
                Notification.show("Encontrado: " + r.getNombre());
            } else {
                Notification.show("No encontrado");
            }
        });

        Button btnActualizar = new Button("Actualizar", e -> {
            Rol r = crudService.buscarPorId(Rol.class, parseId(idField));
            if (r != null) {
                r.setNombre(nombreField.getValue());
                r.setDescripcion(descripcionField.getValue());
                r.setEstado(estadoField.getValue());
                crudService.actualizar(r);
                refrescar.run();
                Notification.show("Rol actualizado");
            } else {
                Notification.show("ID no encontrado");
            }
        });

        Button btnEliminar = new Button("Eliminar", e -> {
            crudService.eliminar(Rol.class, parseId(idField));
            refrescar.run();
            limpiar(idField, nombreField, descripcionField, estadoField);
            Notification.show("Rol eliminado");
        });
        btnEliminar.addThemeVariants(ButtonVariant.LUMO_ERROR);

        Button btnLimpiar = new Button("Limpiar", e -> {
            limpiar(idField, nombreField, descripcionField, estadoField);
            grid.asSingleSelect().clear();
        });

        layout.add(form, crearAcciones(btnCrear, btnConsultar, btnActualizar, btnEliminar, btnLimpiar), grid);
        return layout;
    }

    private Component crearSeccionTiendas() {
        VerticalLayout layout = new VerticalLayout();
        TextField idField = new TextField("ID de tienda");
        TextField idUsuarioField = new TextField("ID de usuario");
        TextField nombreField = new TextField("Nombre de tienda");
        TextField ciudadField = new TextField("Ciudad");
        FormLayout form = new FormLayout(idField, idUsuarioField, nombreField, ciudadField);

        Grid<Tiendas> grid = new Grid<>(Tiendas.class, false);
        grid.addColumn(Tiendas::getIdTienda).setHeader("ID de tienda").setAutoWidth(true);
        grid.addColumn(Tiendas::getIdUsuario).setHeader("ID de usuario").setAutoWidth(true);
        grid.addColumn(Tiendas::getNombreTienda).setHeader("Nombre de tienda").setAutoWidth(true);
        grid.addColumn(Tiendas::getCiudad).setHeader("Ciudad").setAutoWidth(true);
        Runnable refrescar = () -> grid.setItems(crudService.listarTodos(Tiendas.class));
        refrescar.run();

        grid.asSingleSelect().addValueChangeListener(e -> {
            Tiendas tienda = e.getValue();
            if (tienda != null) {
                idField.setValue(String.valueOf(tienda.getIdTienda()));
                idUsuarioField.setValue(String.valueOf(tienda.getIdUsuario()));
                nombreField.setValue(tienda.getNombreTienda() != null ? tienda.getNombreTienda() : "");
                ciudadField.setValue(tienda.getCiudad() != null ? tienda.getCiudad() : "");
            }
        });

        Button btnCrear = new Button("Crear", e -> {
            Long idUsuario = parseId(idUsuarioField);
            if (idUsuario == null) {
                Notification.show("Ingresa un ID de usuario válido");
                return;
            }
            Tiendas tienda = new Tiendas();
            tienda.setIdUsuario(idUsuario);
            tienda.setNombreTienda(nombreField.getValue());
            tienda.setCiudad(ciudadField.getValue());
            crudService.guardar(tienda);
            refrescar.run();
            idField.setValue(String.valueOf(tienda.getIdTienda()));
            Notification.show("Tienda guardada con ID: " + tienda.getIdTienda());
        });
        btnCrear.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button btnConsultar = new Button("Consultar", e -> {
            Long id = parseId(idField);
            if (id == null) {
                Notification.show("Ingresa un ID de tienda válido");
                return;
            }
            Tiendas tienda = crudService.buscarPorId(Tiendas.class, id);
            if (tienda != null) {
                grid.asSingleSelect().setValue(tienda);
                Notification.show("Tienda encontrada: " + tienda.getNombreTienda());
            } else {
                Notification.show("Tienda no encontrada");
            }
        });

        Button btnActualizar = new Button("Actualizar", e -> {
            Long id = parseId(idField);
            Long idUsuario = parseId(idUsuarioField);
            if (id == null || idUsuario == null) {
                Notification.show("Ingresa IDs válidos");
                return;
            }
            Tiendas tienda = crudService.buscarPorId(Tiendas.class, id);
            if (tienda != null) {
                tienda.setIdUsuario(idUsuario);
                tienda.setNombreTienda(nombreField.getValue());
                tienda.setCiudad(ciudadField.getValue());
                crudService.actualizar(tienda);
                refrescar.run();
                Notification.show("Tienda actualizada");
            } else {
                Notification.show("ID de tienda no encontrado");
            }
        });

        Button btnEliminar = new Button("Eliminar", e -> {
            Long id = parseId(idField);
            if (id == null) {
                Notification.show("Ingresa un ID de tienda válido");
                return;
            }
            crudService.eliminar(Tiendas.class, id);
            refrescar.run();
            limpiar(idField, idUsuarioField, nombreField, ciudadField);
            Notification.show("Tienda eliminada");
        });
        btnEliminar.addThemeVariants(ButtonVariant.LUMO_ERROR);

        Button btnLimpiar = new Button("Limpiar", e -> {
            limpiar(idField, idUsuarioField, nombreField, ciudadField);
            grid.asSingleSelect().clear();
        });
        layout.add(form, crearAcciones(btnCrear, btnConsultar, btnActualizar, btnEliminar, btnLimpiar), grid);
        return layout;
    }

    private Component crearSeccionPedidos() {
        VerticalLayout layout = new VerticalLayout();
        TextField idField = new TextField("ID de pedido");
        TextField idUsuarioField = new TextField("ID de usuario");
        TextField fechaField = new TextField("Fecha");
        TextField estadoField = new TextField("Estado");
        TextField metodoPagoField = new TextField("Método de pago");
        TextField direccionField = new TextField("Dirección de entrega");
        FormLayout form = new FormLayout(
            idField, idUsuarioField, fechaField, estadoField, metodoPagoField, direccionField
        );

        Grid<Pedido> grid = new Grid<>(Pedido.class, false);
        grid.addColumn(Pedido::getIdPedido).setHeader("ID de pedido").setAutoWidth(true);
        grid.addColumn(Pedido::getIdUsuario).setHeader("ID de usuario").setAutoWidth(true);
        grid.addColumn(Pedido::getFecha).setHeader("Fecha").setAutoWidth(true);
        grid.addColumn(Pedido::getEstado).setHeader("Estado").setAutoWidth(true);
        grid.addColumn(Pedido::getMetodoPago).setHeader("Método de pago").setAutoWidth(true);
        grid.addColumn(Pedido::getDirreccionEntrega).setHeader("Dirección de entrega").setAutoWidth(true);
        Runnable refrescar = () -> grid.setItems(crudService.listarTodos(Pedido.class));
        refrescar.run();

        grid.asSingleSelect().addValueChangeListener(e -> {
            Pedido pedido = e.getValue();
            if (pedido != null) {
                idField.setValue(String.valueOf(pedido.getIdPedido()));
                idUsuarioField.setValue(String.valueOf(pedido.getIdUsuario()));
                fechaField.setValue(pedido.getFecha() != null ? pedido.getFecha() : "");
                estadoField.setValue(pedido.getEstado() != null ? pedido.getEstado() : "");
                metodoPagoField.setValue(pedido.getMetodoPago() != null ? pedido.getMetodoPago() : "");
                direccionField.setValue(
                    pedido.getDirreccionEntrega() != null ? pedido.getDirreccionEntrega() : ""
                );
            }
        });

        Button btnCrear = new Button("Crear", e -> {
            Long idUsuario = parseId(idUsuarioField);
            if (idUsuario == null) {
                Notification.show("Ingresa un ID de usuario válido");
                return;
            }
            Pedido pedido = new Pedido();
            pedido.setIdUsuario(idUsuario);
            pedido.setFecha(fechaField.getValue());
            pedido.setEstado(estadoField.getValue());
            pedido.setMetodoPago(metodoPagoField.getValue());
            pedido.setDirreccionEntrega(direccionField.getValue());
            crudService.guardar(pedido);
            refrescar.run();
            idField.setValue(String.valueOf(pedido.getIdPedido()));
            Notification.show("Pedido guardado con ID: " + pedido.getIdPedido());
        });
        btnCrear.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button btnConsultar = new Button("Consultar", e -> {
            Long id = parseId(idField);
            if (id == null) {
                Notification.show("Ingresa un ID de pedido válido");
                return;
            }
            Pedido pedido = crudService.buscarPorId(Pedido.class, id);
            if (pedido != null) {
                grid.asSingleSelect().setValue(pedido);
                Notification.show("Pedido encontrado");
            } else {
                Notification.show("Pedido no encontrado");
            }
        });

        Button btnActualizar = new Button("Actualizar", e -> {
            Long id = parseId(idField);
            Long idUsuario = parseId(idUsuarioField);
            if (id == null || idUsuario == null) {
                Notification.show("Ingresa IDs válidos");
                return;
            }
            Pedido pedido = crudService.buscarPorId(Pedido.class, id);
            if (pedido != null) {
                pedido.setIdUsuario(idUsuario);
                pedido.setFecha(fechaField.getValue());
                pedido.setEstado(estadoField.getValue());
                pedido.setMetodoPago(metodoPagoField.getValue());
                pedido.setDirreccionEntrega(direccionField.getValue());
                crudService.actualizar(pedido);
                refrescar.run();
                Notification.show("Pedido actualizado");
            } else {
                Notification.show("ID de pedido no encontrado");
            }
        });

        Button btnEliminar = new Button("Eliminar", e -> {
            Long id = parseId(idField);
            if (id == null) {
                Notification.show("Ingresa un ID de pedido válido");
                return;
            }
            crudService.eliminar(Pedido.class, id);
            refrescar.run();
            limpiar(idField, idUsuarioField, fechaField, estadoField, metodoPagoField, direccionField);
            Notification.show("Pedido eliminado");
        });
        btnEliminar.addThemeVariants(ButtonVariant.LUMO_ERROR);

        Button btnLimpiar = new Button("Limpiar", e -> {
            limpiar(idField, idUsuarioField, fechaField, estadoField, metodoPagoField, direccionField);
            grid.asSingleSelect().clear();
        });
        layout.add(form, crearAcciones(btnCrear, btnConsultar, btnActualizar, btnEliminar, btnLimpiar), grid);
        return layout;
    }

    private Component crearSeccionDetallesPedidos() {
        VerticalLayout layout = new VerticalLayout();
        TextField idField = new TextField("ID de detalle");
        TextField idPedidoField = new TextField("ID de pedido");
        TextField idProductoField = new TextField("ID de producto");
        TextField cantidadField = new TextField("Cantidad");
        TextField precioUnitarioField = new TextField("Precio unitario");
        FormLayout form = new FormLayout(
            idField, idPedidoField, idProductoField, cantidadField, precioUnitarioField
        );

        Grid<DetallesPedidos> grid = new Grid<>(DetallesPedidos.class, false);
        grid.addColumn(DetallesPedidos::getIdDetalles).setHeader("ID de detalle").setAutoWidth(true);
        grid.addColumn(DetallesPedidos::getIdPedido).setHeader("ID de pedido").setAutoWidth(true);
        grid.addColumn(DetallesPedidos::getIdProducto).setHeader("ID de producto").setAutoWidth(true);
        grid.addColumn(DetallesPedidos::getCantidad).setHeader("Cantidad").setAutoWidth(true);
        grid.addColumn(DetallesPedidos::getPrecioUnitario).setHeader("Precio unitario").setAutoWidth(true);
        Runnable refrescar = () -> grid.setItems(crudService.listarTodos(DetallesPedidos.class));
        refrescar.run();

        grid.asSingleSelect().addValueChangeListener(e -> {
            DetallesPedidos detalle = e.getValue();
            if (detalle != null) {
                idField.setValue(String.valueOf(detalle.getIdDetalles()));
                idPedidoField.setValue(String.valueOf(detalle.getIdPedido()));
                idProductoField.setValue(String.valueOf(detalle.getIdProducto()));
                cantidadField.setValue(String.valueOf(detalle.getCantidad()));
                precioUnitarioField.setValue(String.valueOf(detalle.getPrecioUnitario()));
            }
        });

        Button btnCrear = new Button("Crear", e -> {
            Long idPedido = parseId(idPedidoField);
            Long idProducto = parseId(idProductoField);
            if (idPedido == null || idProducto == null) {
                Notification.show("Ingresa IDs de pedido y producto válidos");
                return;
            }
            DetallesPedidos detalle = new DetallesPedidos();
            detalle.setIdPedido(idPedido);
            detalle.setIdProducto(idProducto);
            detalle.setCantidad(parseInt(cantidadField.getValue()));
            detalle.setPrecioUnitario(parseDouble(precioUnitarioField.getValue()));
            crudService.guardar(detalle);
            refrescar.run();
            idField.setValue(String.valueOf(detalle.getIdDetalles()));
            Notification.show("Detalle de pedido guardado con ID: " + detalle.getIdDetalles());
        });
        btnCrear.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button btnConsultar = new Button("Consultar", e -> {
            Long id = parseId(idField);
            if (id == null) {
                Notification.show("Ingresa un ID de detalle válido");
                return;
            }
            DetallesPedidos detalle = crudService.buscarPorId(DetallesPedidos.class, id);
            if (detalle != null) {
                grid.asSingleSelect().setValue(detalle);
                Notification.show("Detalle de pedido encontrado");
            } else {
                Notification.show("Detalle de pedido no encontrado");
            }
        });

        Button btnActualizar = new Button("Actualizar", e -> {
            Long id = parseId(idField);
            Long idPedido = parseId(idPedidoField);
            Long idProducto = parseId(idProductoField);
            if (id == null || idPedido == null || idProducto == null) {
                Notification.show("Ingresa IDs válidos");
                return;
            }
            DetallesPedidos detalle = crudService.buscarPorId(DetallesPedidos.class, id);
            if (detalle != null) {
                detalle.setIdPedido(idPedido);
                detalle.setIdProducto(idProducto);
                detalle.setCantidad(parseInt(cantidadField.getValue()));
                detalle.setPrecioUnitario(parseDouble(precioUnitarioField.getValue()));
                crudService.actualizar(detalle);
                refrescar.run();
                Notification.show("Detalle de pedido actualizado");
            } else {
                Notification.show("ID de detalle no encontrado");
            }
        });

        Button btnEliminar = new Button("Eliminar", e -> {
            Long id = parseId(idField);
            if (id == null) {
                Notification.show("Ingresa un ID de detalle válido");
                return;
            }
            crudService.eliminar(DetallesPedidos.class, id);
            refrescar.run();
            limpiar(idField, idPedidoField, idProductoField, cantidadField, precioUnitarioField);
            Notification.show("Detalle de pedido eliminado");
        });
        btnEliminar.addThemeVariants(ButtonVariant.LUMO_ERROR);

        Button btnLimpiar = new Button("Limpiar", e -> {
            limpiar(idField, idPedidoField, idProductoField, cantidadField, precioUnitarioField);
            grid.asSingleSelect().clear();
        });
        layout.add(form, crearAcciones(btnCrear, btnConsultar, btnActualizar, btnEliminar, btnLimpiar), grid);
        return layout;
    }

    private Component crearSeccionDetallesDonaciones() {
        VerticalLayout layout = new VerticalLayout();
        TextField idField = new TextField("ID de detalle");
        TextField idDonacionField = new TextField("ID de donación");
        TextField idProductoField = new TextField("ID de producto");
        TextField cantidadField = new TextField("Cantidad");
        FormLayout form = new FormLayout(idField, idDonacionField, idProductoField, cantidadField);

        Grid<DetallesDonaciones> grid = new Grid<>(DetallesDonaciones.class, false);
        grid.addColumn(DetallesDonaciones::getIdDetaallesDonaciones).setHeader("ID de detalle").setAutoWidth(true);
        grid.addColumn(DetallesDonaciones::getIdDonacion).setHeader("ID de donación").setAutoWidth(true);
        grid.addColumn(DetallesDonaciones::getIdProducto).setHeader("ID de producto").setAutoWidth(true);
        grid.addColumn(DetallesDonaciones::getCantidad).setHeader("Cantidad").setAutoWidth(true);
        Runnable refrescar = () -> grid.setItems(crudService.listarTodos(DetallesDonaciones.class));
        refrescar.run();

        grid.asSingleSelect().addValueChangeListener(e -> {
            DetallesDonaciones detalle = e.getValue();
            if (detalle != null) {
                idField.setValue(String.valueOf(detalle.getIdDetaallesDonaciones()));
                idDonacionField.setValue(String.valueOf(detalle.getIdDonacion()));
                idProductoField.setValue(String.valueOf(detalle.getIdProducto()));
                cantidadField.setValue(String.valueOf(detalle.getCantidad()));
            }
        });

        Button btnCrear = new Button("Crear", e -> {
            Long idDonacion = parseId(idDonacionField);
            Long idProducto = parseId(idProductoField);
            if (idDonacion == null || idProducto == null) {
                Notification.show("Ingresa IDs de donación y producto válidos");
                return;
            }
            DetallesDonaciones detalle = new DetallesDonaciones();
            detalle.setIdDonacion(idDonacion);
            detalle.setIdProducto(idProducto);
            detalle.setCantidad(parseInt(cantidadField.getValue()));
            crudService.guardar(detalle);
            refrescar.run();
            idField.setValue(String.valueOf(detalle.getIdDetaallesDonaciones()));
            Notification.show("Detalle de donación guardado con ID: " + detalle.getIdDetaallesDonaciones());
        });
        btnCrear.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button btnConsultar = new Button("Consultar", e -> {
            Long id = parseId(idField);
            if (id == null) {
                Notification.show("Ingresa un ID de detalle válido");
                return;
            }
            DetallesDonaciones detalle = crudService.buscarPorId(DetallesDonaciones.class, id);
            if (detalle != null) {
                grid.asSingleSelect().setValue(detalle);
                Notification.show("Detalle de donación encontrado");
            } else {
                Notification.show("Detalle de donación no encontrado");
            }
        });

        Button btnActualizar = new Button("Actualizar", e -> {
            Long id = parseId(idField);
            Long idDonacion = parseId(idDonacionField);
            Long idProducto = parseId(idProductoField);
            if (id == null || idDonacion == null || idProducto == null) {
                Notification.show("Ingresa IDs válidos");
                return;
            }
            DetallesDonaciones detalle = crudService.buscarPorId(DetallesDonaciones.class, id);
            if (detalle != null) {
                detalle.setIdDonacion(idDonacion);
                detalle.setIdProducto(idProducto);
                detalle.setCantidad(parseInt(cantidadField.getValue()));
                crudService.actualizar(detalle);
                refrescar.run();
                Notification.show("Detalle de donación actualizado");
            } else {
                Notification.show("ID de detalle no encontrado");
            }
        });

        Button btnEliminar = new Button("Eliminar", e -> {
            Long id = parseId(idField);
            if (id == null) {
                Notification.show("Ingresa un ID de detalle válido");
                return;
            }
            crudService.eliminar(DetallesDonaciones.class, id);
            refrescar.run();
            limpiar(idField, idDonacionField, idProductoField, cantidadField);
            Notification.show("Detalle de donación eliminado");
        });
        btnEliminar.addThemeVariants(ButtonVariant.LUMO_ERROR);

        Button btnLimpiar = new Button("Limpiar", e -> {
            limpiar(idField, idDonacionField, idProductoField, cantidadField);
            grid.asSingleSelect().clear();
        });
        layout.add(form, crearAcciones(btnCrear, btnConsultar, btnActualizar, btnEliminar, btnLimpiar), grid);
        return layout;
    }

    // ==========================================
    // 7. ASIGNACIÓN DE ROLES A USUARIOS
    // ==========================================
    private Component crearSeccionUsuariosRoles() {
        VerticalLayout layout = new VerticalLayout();
        TextField idUsuarioField = new TextField("ID de usuario");
        TextField idRolField = new TextField("ID de rol");

        FormLayout form = new FormLayout(idUsuarioField, idRolField);

        Grid<UsuarioRol> grid = new Grid<>(UsuarioRol.class, false);
        grid.addColumn(UsuarioRol::getIdUsuario).setHeader("ID de usuario").setAutoWidth(true);
        grid.addColumn(UsuarioRol::getIdRol).setHeader("ID de rol").setAutoWidth(true);

        Runnable refrescar = () -> grid.setItems(crudService.listarTodos(UsuarioRol.class));
        refrescar.run();

        grid.asSingleSelect().addValueChangeListener(e -> {
            UsuarioRol usuarioRol = e.getValue();
            if (usuarioRol != null) {
                idUsuarioField.setValue(
                    usuarioRol.getIdUsuario() != null ? usuarioRol.getIdUsuario().toString() : ""
                );
                idRolField.setValue(usuarioRol.getIdRol() != null ? usuarioRol.getIdRol().toString() : "");
            }
        });

        Button btnCrear = new Button("Asignar", e -> {
            UsuarioRol usuarioRol = new UsuarioRol(parseId(idUsuarioField), parseId(idRolField));
            crudService.guardar(usuarioRol);
            refrescar.run();
            Notification.show("Rol asignado al usuario con ID: " + usuarioRol.getIdUsuario());
        });
        btnCrear.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button btnConsultar = new Button("Consultar", e -> {
            UsuarioRol usuarioRol = crudService.buscarPorId(UsuarioRol.class, parseId(idUsuarioField));
            if (usuarioRol != null) {
                grid.asSingleSelect().setValue(usuarioRol);
                Notification.show("Asignación encontrada");
            } else {
                Notification.show("No encontrada");
            }
        });

        Button btnActualizar = new Button("Actualizar", e -> {
            UsuarioRol usuarioRol = crudService.buscarPorId(UsuarioRol.class, parseId(idUsuarioField));
            if (usuarioRol != null) {
                usuarioRol.setIdRol(parseId(idRolField));
                crudService.actualizar(usuarioRol);
                refrescar.run();
                Notification.show("Asignación actualizada");
            } else {
                Notification.show("ID de usuario no encontrado");
            }
        });

        Button btnEliminar = new Button("Eliminar", e -> {
            crudService.eliminar(UsuarioRol.class, parseId(idUsuarioField));
            refrescar.run();
            limpiar(idUsuarioField, idRolField);
            Notification.show("Asignación eliminada");
        });
        btnEliminar.addThemeVariants(ButtonVariant.LUMO_ERROR);

        Button btnLimpiar = new Button("Limpiar", e -> {
            limpiar(idUsuarioField, idRolField);
            grid.asSingleSelect().clear();
        });

        layout.add(form, crearAcciones(btnCrear, btnConsultar, btnActualizar, btnEliminar, btnLimpiar), grid);
        return layout;
    }

    // ==========================================
    // MÉTODOS AUXILIARES
    // ==========================================
    private Long parseId(TextField field) {
        try {
            return Long.parseLong(field.getValue().trim());
        } catch (Exception e) {
            return null;
        }
    }

    private Double parseDouble(String val) {
        try {
            return Double.parseDouble(val.trim());
        } catch (Exception e) {
            return 0.0;
        }
    }

    private Integer parseInt(String val) {
        try {
            return Integer.parseInt(val.trim());
        } catch (Exception e) {
            return 0;
        }
    }

    private void limpiar(TextField... campos) {
        for (TextField c : campos) {
            c.clear();
        }
    }

    private HorizontalLayout crearAcciones(Button... botones) {
        HorizontalLayout layout = new HorizontalLayout(botones);
        layout.getStyle().set("flex-wrap", "wrap");
        return layout;
    }
}
