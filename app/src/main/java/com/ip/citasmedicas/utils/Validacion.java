package com.ip.citasmedicas.utils;

import java.util.regex.Pattern;

public class Validacion {

    public boolean esNumeroValido(String numero) {
        Pattern patron = Pattern.compile("^[0-9]+$");
        if (numero.equals("")){
            return false;
        }else {
            if(!patron.matcher(numero).matches()) {
                return false;
            }
        }
        return true;
    }

    public boolean esDecimalValido(String numero) {
        Pattern patron = Pattern.compile("^[0-9]+(\\.[0-9]+)?$");
        if (numero.equals("")){
            return false;
        }else {
            if(!patron.matcher(numero).matches() || numero.length() > 4
                    || Float.parseFloat(numero) > 50){
                return false;
            }
        }
        return true;
    }


    public boolean esIpValido(String ip) {
        Pattern patron = Pattern.compile("^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$");
        if (ip.equals("")){
            return false;
        }else {
            if(!patron.matcher(ip).matches() || ip.length() < 10 || ip.length() > 15) {
                return false;
            }
        }
        return true;
    }

    public boolean esTextoValido(String texto) {
        Pattern patron = Pattern.compile("^[a-zA-Z-0-9\u00F1\u00D1 ]+$");
        if (texto.equals("")){
            return false;
        }else {
            if(!patron.matcher(texto).matches() ) {
                return false;
            }
        }
        return true;
    }

    public boolean esTelefonoValido(String telefono) {
        Pattern patron = Pattern.compile("^[+][0-9 ]+$");
        if (telefono.equals("")){
            return false;
        }else {
            if(!patron.matcher(telefono).matches() || telefono.length() < 8 || telefono.length() > 16 ) {
                return false;
            }
        }
        return true;
    }

    public boolean esSsidValido(String ssid) {
        Pattern patron = Pattern.compile("^[a-zA-Z-0-9 ]+$");
        if (ssid.equals("")){
            return false;
        }else {
            if(!patron.matcher(ssid).matches() || ssid.length() < 5 || ssid.length() > 10 ) {
                return false;
            }
        }
        return true;
    }

    public boolean esPasswordValido(String password) {
        Pattern patron = Pattern.compile("^[a-zA-Z-0-9 ]+$");
        if (password.equals("")){
            return false;
        }else {
            if(!patron.matcher(password).matches() || password.length() < 8 || password.length() > 18 ) {
                return false;
            }
        }
        return true;
    }
}
