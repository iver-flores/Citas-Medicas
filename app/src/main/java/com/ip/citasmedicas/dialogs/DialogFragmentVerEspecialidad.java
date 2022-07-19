package com.ip.citasmedicas.dialogs;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ip.citasmedicas.R;
import com.ip.citasmedicas.entidades.ListaConsulta;
import com.ip.citasmedicas.entidades.ListaDoctor;
import com.ip.citasmedicas.entidades.ListaPaciente;
import com.ip.citasmedicas.entidades.RutasRealtime;
import com.ip.citasmedicas.fragments.DatePickerFragment;
import com.ip.citasmedicas.fragments.PacienteFragment;

import java.time.LocalDate;
import java.util.ArrayList;
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

    private ArrayList<ListaDoctor> listaDoctors;
    private ArrayList<ListaPaciente> listaPacientes;


    private String especialidad = "", uidDoctor = "", fotoMedico = "", nombreMedico = "",
            fechaMedico = "", turnoMedico = "", fotoPaciente = "", nombrePaciente = "",
            uidPaciente = "", consulta = "";

    private int turno = 0;
    private String fechaConsulta = "0";

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
        uidDoctor = getArguments().getString("uidDoctor");
        uidPaciente = getArguments().getString("uidPaciente");
        fechaMedico = getArguments().getString("fechaMedico");
        turnoMedico = getArguments().getString("turnoMedico");
        fotoPaciente = getArguments().getString("fotoPaciente");
        nombrePaciente = getArguments().getString("nombrePaciente");

        consulta = getArguments().getString("Consulta");

        builder.setView(v);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        listaDoctors = new ArrayList<>();
        listaPacientes = new ArrayList<>();

        init(v);

        tvTitulo.setText(especialidad.toUpperCase());

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
            builder.setCancelable(false);
            tvTitulo.setText(("Consulta pendiente para la especialidad " + especialidad).toUpperCase());
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
                if (!fechaConsulta.equals("0") && turno > 0){
                    iniciarTransaccion();
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
                        iniciarTransaccion(selectedDate);

                    }else {
                        Toast.makeText(getActivity(), "FECHA NO DISPONIBLE", Toast.LENGTH_LONG).show();
                        tvFechaHoraPaciente.setText("Fecha y Hora");
                        tvTurno.setText("Turno");
                    }
                }else {
                    Toast.makeText(getActivity(), "FECHA NO DISPONIBLE", Toast.LENGTH_LONG).show();
                    tvFechaHoraPaciente.setText("Fecha y Hora");
                    tvTurno.setText("Turno");
                }

            }
        });

        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }

    private void iniciarTransaccion(String fecha) {
        mDatabase.child(RutasRealtime.PATH_CONSULTAS).get().
                addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot :dataSnapshot.getChildren()){
                            ListaConsulta listaConsulta = snapshot.getValue(ListaConsulta.class);
                            if (snapshot.child(RutasRealtime.UID_DOCTOR).getValue().equals(uidDoctor) &&
                                    !snapshot.child(RutasRealtime.ESTADO).getValue().equals("Cita realizada")){
                                if (listaConsulta.getFecha().equals(fecha)){
                                    if (Integer.parseInt(listaConsulta.getTurno()) < 8){
                                        turno = Integer.parseInt(listaConsulta.getTurno()) + 1;
                                        fechaConsulta = fecha;
                                        if (turno <= 4){
                                            tvTurno.setText("Turno " + turno + " Mañana");
                                        }else if (turno <= 8){
                                            tvTurno.setText("Turno " + turno + " Tarde");
                                        }
                                    }
                                }
                            }
                        }

                        if (turno == 0){
                            turno = 1;
                            fechaConsulta = fecha;
                            tvTurno.setText("Turno " + turno + " Mañana");
                        }

                    }
                });
    }

    private void iniciarTransaccion(){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(requireActivity());
        builder.setTitle("Esta seguro de realizar la consulta");
        builder.setCancelable(false);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String id = mDatabase.child(RutasRealtime.PATH_CONSULTAS).push().getKey();
                ListaConsulta listaConsulta = new ListaConsulta(
                        String.valueOf(id), uidDoctor, uidPaciente, fotoMedico, nombreMedico,
                        fotoPaciente, nombrePaciente, String.valueOf(turno), String.valueOf(fechaConsulta),
                        especialidad, "En espera");

                Map<String, Object> consultaValues = listaConsulta.toConsulta();
                mDatabase.child(RutasRealtime.PATH_CONSULTAS).child(id).updateChildren(consultaValues);

                mDatabase.child(RutasRealtime.PATH_PACIENTE).child(uidPaciente).
                        child(RutasRealtime.ESPECIALIDADES).setValue(id);

                btnProgramar.setEnabled(false);
                dismiss();
                Fragment fragment = new PacienteFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().
                        beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        builder.show();
    }
}
