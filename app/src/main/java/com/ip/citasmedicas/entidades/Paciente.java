package com.ip.citasmedicas.entidades;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Paciente {

    public static final String UID = "uid";
    public static final String USERNAME = "nombre";
    public static final String EMAIL = "email";
    public static final String PHOTO_PERFIL = "foto_perfil";
    public static final String TELEPHONE = "telefono";
    public static final String CI = "ci";
    public static final String REGISTRO = "registro";
    public static final String ESPECIALIDAD = "especialidades";
    public static final String HORAS = "horas";
    public static final String DOCTORES = "doctores";
    public static final String ESTADO = "estado";

    public String uid;
    public String username;
    public String email;
    public String photo_perfil;
    public String telephone;
    public String ci;
    public String registro;
    public String especialidades;
    public String horas;
    public String doctores;
    public boolean estado;

    public Paciente() {

    }

    public Paciente(String uid, String username, String email, String photo_perfil,
                    String telephone, String ci, String registro, boolean estado) {
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.photo_perfil = photo_perfil;
        this.telephone = telephone;
        this.ci = ci;
        this.registro = registro;
        this.estado = estado;
    }

    public Paciente(String uid, String username, String email, String photo_perfil,
                    String telephone, String ci, String registro, String especialidades,
                    String horas, String doctores, boolean estado) {
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.photo_perfil = photo_perfil;
        this.telephone = telephone;
        this.ci = ci;
        this.registro = registro;
        this.especialidades = especialidades;
        this.horas = horas;
        this.doctores = doctores;
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

    public String getHoras() {
        return horas;
    }

    public void setHoras(String horas) {
        this.horas = horas;
    }

    public String getDoctores() {
        return doctores;
    }

    public void setDoctores(String doctores) {
        this.doctores = doctores;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Exclude
    public Map<String, Object> toPaciente() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("nombre", username);
        result.put("email", email);
        result.put("foto_perfil", photo_perfil);
        result.put("telefono", telephone);
        result.put("ci", ci);
        result.put("registro", registro);
        result.put("especialidades", especialidades);
        result.put("horas", horas);
        result.put("doctores", doctores);
        result.put("estado", estado);

        return result;
    }
}
