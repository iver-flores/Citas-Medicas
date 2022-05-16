package com.ip.citasmedicas.dialogs;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ip.citasmedicas.R;
import com.ip.citasmedicas.entidades.ListaConsultas;
import com.ip.citasmedicas.entidades.ListaMedico;
import com.ip.citasmedicas.entidades.ListaPaciente;
import com.ip.citasmedicas.entidades.RutasRealtime;
import com.ip.citasmedicas.fragments.DatePickerFragment;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

public class DialogFragmentVerEspecialidad extends DialogFragment implements View.OnClickListener {

    private ImageButton ibAtras;
    private TextView tvTitulo, tvTurno;
    private ImageView ivDoctor, ivPaciente;
    private TextView tvNombreMedico, tvEspecialidadMedico, tvNombrePaciente, tvEspecialidadPaciente,
                        tvFechaHoraPaciente;
    private Button btnVerFecha, btnProgramar;

    AlertDialog.Builder builder;

    private FirebaseUser firebaseUser;
    private DatabaseReference mDatabase;

    private ArrayList<ListaMedico> listaMedicos;
    private ArrayList<ListaPaciente> listaPacientes;


    private String especialidad = "", uidDoctor = "", fotoMedico = "", nombreMedico = "",
            fechaMedico = "", turnoMedico = "", fotoPaciente = "", nombrePaciente = "",
            uidPaciente = "", consulta = "";

    private int d = 0, cont = 0;
    private String dia = "";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return crearDialogoFrag();
    }

    private AlertDialog crearDialogoFrag() {
        builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (requireActivity()).getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_fragment_ver_especialidad, null);
        fotoMedico = getArguments().getString("fotoMedico");
        nombreMedico = getArguments().getString("nombreMedico");
        especialidad = getArguments().getString("especialidad");
        uidPaciente = getArguments().getString("uidPaciente");
        fechaMedico = getArguments().getString("fechaMedico");
        turnoMedico = getArguments().getString("turnoMedico");
        fotoPaciente = getArguments().getString("fotoPaciente");
        nombrePaciente = getArguments().getString("nombrePaciente");

        consulta = getArguments().getString("Consulta");

        builder.setView(v);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        listaMedicos = new ArrayList<>();
        listaPacientes = new ArrayList<>();

        init(v);

        tvTitulo.setText(especialidad);

        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop();
        Glide.with(getActivity())
                .load(Uri.parse(fotoMedico))
                .apply(options)
                .into(ivDoctor);

        Glide.with(getActivity())
                .load(Uri.parse(fotoPaciente))
                .apply(options)
                .into(ivPaciente);

        tvNombreMedico.setText(nombreMedico);
        tvNombrePaciente.setText(nombrePaciente);

        tvEspecialidadMedico.setText(especialidad);
        tvEspecialidadPaciente.setText(especialidad);

        if (consulta != null){
            tvTitulo.setText("Consulta pendiente para la especialidad " + especialidad);
            if (Integer.parseInt(turnoMedico) <= 10){
                tvTurno.setText("Turno " + turnoMedico + ", Mañana");
            }else {
                tvTurno.setText("Turno " + turnoMedico + ", Tarde");
            }
            tvFechaHoraPaciente.setText(fechaMedico);
            btnProgramar.setVisibility(View.GONE);
            btnVerFecha.setVisibility(View.GONE);
        }

        return builder.create();
    }

    private void init( View view) {
        ibAtras = view.findViewById(R.id.ib_atras);
        tvTitulo = view.findViewById(R.id.tv_titulo);
        tvTurno = view.findViewById(R.id.tv_turno);
        ivDoctor = view.findViewById(R.id.iv_doctor);
        ivPaciente = view.findViewById(R.id.iv_paciente);
        tvNombreMedico = view.findViewById(R.id.tv_nombre_doctor);
        tvEspecialidadMedico = view.findViewById(R.id.tv_especialidad_doctor);
        tvNombrePaciente = view.findViewById(R.id.tv_nombre_paciente);
        tvEspecialidadPaciente = view.findViewById(R.id.tv_especialidad_paciente);
        tvFechaHoraPaciente = view.findViewById(R.id.tv_hora_consulta);

        btnVerFecha = view.findViewById(R.id.btn_fijar_fecha);
        btnProgramar = view.findViewById(R.id.btn_programar_consulta);

        ibAtras.setOnClickListener(this);
        btnVerFecha.setOnClickListener(this);
        btnProgramar.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ib_atras:
                dismiss();
                break;
            case R.id.btn_fijar_fecha:
                showDatePickerDialog();
                break;
            case R.id.btn_programar_consulta:
                if (d != 0){
                    String id = mDatabase.child(RutasRealtime.PATH_CONSULTAS).push().getKey();
                    ListaConsultas listaConsultas = new ListaConsultas(
                            String.valueOf(id), fotoMedico, nombreMedico, fotoPaciente, nombrePaciente,
                            String.valueOf(d), String.valueOf(dia), especialidad
                    );

                    Map<String, Object> consultaValues = listaConsultas.toConsulta();
                    mDatabase.child(RutasRealtime.PATH_CONSULTAS).child(id).updateChildren(consultaValues);

                    mDatabase.child(RutasRealtime.PATH_PACIENTE).child(uidPaciente).
                            child(RutasRealtime.ESPECIALIDADES).setValue(id);

                    btnProgramar.setEnabled(false);
                    dismiss();
                }else {
                    Toast.makeText(getActivity(), "DATOS INCORRECTOS", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = day + " - " + (month+1) + " - " + year;

                LocalDate date1 = LocalDate.now();

                if (month+1 >= date1.getMonthValue()){
                    if (day > date1.getDayOfMonth()){
                        tvFechaHoraPaciente.setText(selectedDate);
                        d = cont = 0;

                        if (turnoMedico.indexOf(",") > 0){
                            String[] arrTurno = turnoMedico.split(",");
                            String[] arrFecha = fechaMedico.split(",");

                            for (int i = 0; i < arrTurno.length; i++) {
                                if (arrFecha[i].equals(String.valueOf(day))){
                                    d = Integer.parseInt(arrTurno[i]);
                                    cont = d;
                                }
                            }
                            if (d != 0 && d < 20){
                                if ((d+1) <= 10){
                                    tvTurno.setText("Turno " + (d+1) + ", Mañana");
                                    dia = selectedDate;
                                }else {
                                    tvTurno.setText("Turno " + (d+1) + ", Tarde");
                                    dia = selectedDate;
                                }
                            }else {
                                d = 1;
                                tvTurno.setText("Turno " + d + ", Mañana");
                                dia = selectedDate;
                            }
                        }else {
                            d = 1;
                            tvTurno.setText("Turno " + d + ", Mañana");
                            dia = selectedDate;
                        }

                    }else {
                        Toast.makeText(getActivity(), "FECHA NO DISPONIBLE", Toast.LENGTH_LONG).show();
                        d = cont = 0;
                    }
                }else {
                    Toast.makeText(getActivity(), "FECHA NO DISPONIBLE", Toast.LENGTH_LONG).show();
                    d = cont = 0;
                }

            }
        });

        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }


}
