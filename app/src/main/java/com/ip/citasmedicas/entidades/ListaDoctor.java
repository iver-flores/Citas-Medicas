package com.ip.citasmedicas.entidades;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class ListaDoctor {
    private String id;
    private String foto_perfil;
    private String nombre;
    private String especialidades;

    public ListaDoctor() {
    }

    public ListaDoctor(String id, String foto_perfil, String nombre, String especialidades) {
        this.id = id;
        this.foto_perfil = foto_perfil;
        this.nombre = nombre;
        this.especialidades = especialidades;
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

    public String getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(String especialidades) {
        this.especialidades = especialidades;
    }

    @Exclude
    public Map<String, Object> toProducto() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("foto_perfil", foto_perfil);
        result.put("nombre", nombre);
        result.put("especialidades", especialidades);

        return result;
    }
}
