package com.ip.citasmedicas.entidades;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Medico {

    public static final String UID = "uid";
    public static final String USERNAME = "nombre";
    public static final String EMAIL = "email";
    public static final String PHOTO_PERFIL = "foto_perfil";
    public static final String TELEPHONE = "telefono";
    public static final String CI = "ci";
    public static final String ESPECIALIDADES = "especialidades";
    public static final String PACIENTES = "pacientes";
    public static final String FECHAS = "fechas";
    public static final String TURNOS = "turnos";
    public static final String ESTADO = "estado";

    public String uid;
    public String username;
    public String email;
    public String photo_perfil;
    public String telephone;
    public String ci;
    public String especialidades;
    public String pacientes;
    public String fechas;
    public String turnos;
    public boolean estado;

    public Medico() {

    }

    public Medico(String uid, String username, String email, String photo_perfil, String telephone,
                  String ci, boolean estado) {
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.photo_perfil = photo_perfil;
        this.telephone = telephone;
        this.ci = ci;
        this.estado = estado;
    }

    public Medico(String uid, String username, String email, String photo_perfil, String telephone,
                  String ci, String especialidades, String pacientes, String fechas, String turnos, boolean estado) {
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.photo_perfil = photo_perfil;
        this.telephone = telephone;
        this.ci = ci;
        this.especialidades = especialidades;
        this.pacientes = pacientes;
        this.fechas = fechas;
        this.turnos = turnos;
        this.estado = estado;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto_perfil() {
        return photo_perfil;
    }

    public void setPhoto_perfil(String photo_perfil) {
        this.photo_perfil = photo_perfil;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(String especialidades) {
        this.especialidades = especialidades;
    }

    public String getPacientes() {
        return pacientes;
    }

    public void setPacientes(String pacientes) {
        this.pacientes = pacientes;
    }

    public String getFechas() {
        return fechas;
    }

    public void setFechas(String fechas) {
        this.fechas = fechas;
    }

    public String getTurnos() {
        return turnos;
    }

    public void setTurnos(String turnos) {
        this.turnos = turnos;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Exclude
    public Map<String, Object> toDoctor() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("nombre", username);
        result.put("email", email);
        result.put("foto_perfil", photo_perfil);
        result.put("telefono", telephone);
        result.put("ci", ci);
        result.put("especialidades", especialidades);
        result.put("pacientes", pacientes);
        result.put("fechas", fechas);
        result.put("turnos", turnos);
        result.put("estado", estado);

        return result;
    }
}
