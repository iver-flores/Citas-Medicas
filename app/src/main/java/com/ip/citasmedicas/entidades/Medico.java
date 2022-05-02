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
    public static final String PHOTO_CI = "foto_ci";
    public static final String REGISTRO = "registro";
    public static final String ESPECIALIDADES = "especialidades";
    public static final String PACIENTES = "pacientes";
    public static final String HORAS = "horas";
    public static final String ESTADO = "estado";

    public String uid;
    public String username;
    public String email;
    public String photo_perfil;
    public String telephone;
    public String photo_ci;
    public String registro;
    public String especialidades;
    public String pacientes;
    public String horas;
    public Boolean estado;

    public Medico() {

    }

    public Medico(String uid, String username, String email, String photo_perfil, String telephone,
                  String photo_ci, String registro, String especialidades, String pacientes,
                  String horas, Boolean estado) {
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.photo_perfil = photo_perfil;
        this.telephone = telephone;
        this.photo_ci = photo_ci;
        this.registro = registro;
        this.especialidades = especialidades;
        this.pacientes = pacientes;
        this.horas = horas;
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

    public String getPhoto_ci() {
        return photo_ci;
    }

    public void setPhoto_ci(String photo_ci) {
        this.photo_ci = photo_ci;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
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

    public String getHoras() {
        return horas;
    }

    public void setHoras(String horas) {
        this.horas = horas;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    @Exclude
    public Map<String, Object> toDomiciliario() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("nombre", username);
        result.put("email", email);
        result.put("foto_perfil", photo_perfil);
        result.put("telefono", telephone);
        result.put("foto_ci", photo_ci);
        result.put("registro", registro);
        result.put("especialidades", especialidades);
        result.put("pacientes", pacientes);
        result.put("horas", horas);
        result.put("estado", estado);

        return result;
    }
}
