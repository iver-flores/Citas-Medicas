package com.ip.citasmedicas.entidades;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Pacientes {

    public static final String UID = "uid";
    public static final String USERNAME = "nombre";
    public static final String EMAIL = "email";
    public static final String PHOTO_PERFIL = "foto_perfil";
    public static final String TELEPHONE = "telefono";
    public static final String PHOTO_CI = "foto_ci";
    public static final String REGISTRO = "registro";
    public static final String CONSULTAS = "consultas";
    public static final String HORAS = "horas";
    public static final String COSTOS = "costos";
    public static final String MEDICOS = "medicos";
    public static final String ESTADO = "estado";

    public String uid;
    public String username;
    public String email;
    public String photo_perfil;
    public String telephone;
    public String photo_ci;
    public String registro;
    public String consultas;
    public String horas;
    public String costos;
    public String medicos;
    public Boolean estado;

    public Pacientes() {

    }

    public Pacientes(String uid, String username, String email, String photo_perfil,
                     String telephone, String photo_ci, String registro, String consultas,
                     String horas, String costos, String medicos, Boolean estado) {
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.photo_perfil = photo_perfil;
        this.telephone = telephone;
        this.photo_ci = photo_ci;
        this.registro = registro;
        this.consultas = consultas;
        this.horas = horas;
        this.costos = costos;
        this.medicos = medicos;
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

    public String getConsultas() {
        return consultas;
    }

    public void setConsultas(String consultas) {
        this.consultas = consultas;
    }

    public String getHoras() {
        return horas;
    }

    public void setHoras(String horas) {
        this.horas = horas;
    }

    public String getCostos() {
        return costos;
    }

    public void setCostos(String costos) {
        this.costos = costos;
    }

    public String getMedicos() {
        return medicos;
    }

    public void setMedicos(String medicos) {
        this.medicos = medicos;
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
        result.put("consultas", consultas);
        result.put("horas", horas);
        result.put("costos", costos);
        result.put("medicos", medicos);
        result.put("estado", estado);

        return result;
    }
}
