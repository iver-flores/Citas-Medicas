package com.ip.citasmedicas.entidades;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class ListaPaciente {
    private String id;
    private String foto_perfil;
    private String nombre;
    private String telefono;
    private String registro;

    public ListaPaciente() {
    }

    public ListaPaciente(String id, String foto_perfil, String nombre, String telefono, String registro) {
        this.id = id;
        this.foto_perfil = foto_perfil;
        this.nombre = nombre;
        this.telefono = telefono;
        this.registro = registro;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFoto_perfil() {
        return foto_perfil;
    }

    public void setFoto_perfil(String foto_perfil) {
        this.foto_perfil = foto_perfil;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    @Exclude
    public Map<String, Object> toProducto() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("foto_perfil", foto_perfil);
        result.put("nombre", nombre);
        result.put("telefono", telefono);
        result.put("registro", registro);

        return result;
    }
}
