package com.ip.citasmedicas.entidades;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class ListaConsultas {

    public static final String UID = "uid";
    public static final String FOTO_DOCTOR = "foto_doctor";
    public static final String NOMBRE_DOCTOR = "nombre_doctor";
    public static final String FOTO_PACIENTE = "foto_paciente";
    public static final String NOMBRE_PACIENTE = "nombre_paciente";
    public static final String TURNO = "turno";
    public static final String FECHA = "fecha";
    public static final String ESPECIALIDAD = "especialidad";

    private String id;
    private String foto_doctor;
    private String nombre_doctor;
    private String foto_paciente;
    private String nombre_paciente;
    private String turno;
    private String fecha;
    private String especialidad;

    public ListaConsultas() {
    }

    public ListaConsultas(String id, String foto_doctor, String nombre_doctor, String foto_paciente,
                          String nombre_paciente, String turno, String fecha, String especialidad) {
        this.id = id;
        this.foto_doctor = foto_doctor;
        this.nombre_doctor = nombre_doctor;
        this.foto_paciente = foto_paciente;
        this.nombre_paciente = nombre_paciente;
        this.turno = turno;
        this.fecha = fecha;
        this.especialidad = especialidad;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFoto_doctor() {
        return foto_doctor;
    }

    public void setFoto_doctor(String foto_doctor) {
        this.foto_doctor = foto_doctor;
    }

    public String getNombre_doctor() {
        return nombre_doctor;
    }

    public void setNombre_doctor(String nombre_doctor) {
        this.nombre_doctor = nombre_doctor;
    }

    public String getFoto_paciente() {
        return foto_paciente;
    }

    public void setFoto_paciente(String foto_paciente) {
        this.foto_paciente = foto_paciente;
    }

    public String getNombre_paciente() {
        return nombre_paciente;
    }

    public void setNombre_paciente(String nombre_paciente) {
        this.nombre_paciente = nombre_paciente;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    @Exclude
    public Map<String, Object> toConsulta() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", id);
        result.put("foto_doctor", foto_doctor);
        result.put("nombre_doctor", nombre_doctor);
        result.put("foto_paciente", foto_paciente);
        result.put("nombre_paciente", nombre_paciente);
        result.put("turno", turno);
        result.put("fecha", fecha);
        result.put("especialidad", especialidad);

        return result;
    }
}
