package com.ip.citasmedicas.utils;
import android.annotation.SuppressLint;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class Fecha {

    public String sacarFechaHora(){

        String formato = "dd-MM-yyyy HH:mm:ss";
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formato);
        return simpleDateFormat.format(new Date());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public  String sacarDiaHora(){
        String formato = "MM-yyyy";
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formato);
        LocalDate date1 = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE", new Locale("es", "BO"));


        return (date1.format(formatter) + " " + date1.getDayOfMonth() +  "-" + simpleDateFormat.format(new Date()));
    }
}
