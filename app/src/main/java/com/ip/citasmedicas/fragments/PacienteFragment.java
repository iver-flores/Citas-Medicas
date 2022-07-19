package com.ip.citasmedicas.fragments;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.FirebaseDatabase;
import com.ip.citasmedicas.R;
import com.ip.citasmedicas.dialogs.DialogFragmentVerEspecialidad;
import com.ip.citasmedicas.entidades.Doctor;
import com.ip.citasmedicas.entidades.ListaConsulta;
import com.ip.citasmedicas.entidades.Paciente;
import com.ip.citasmedicas.entidades.RutasRealtime;

import java.util.ArrayList;

public class PacienteFragment extends Fragment implements View.OnClickListener {

    private View view;

    private LinearLayout llEspecialidades;

    private Button btnMedicinaFamiliar, btnMedicinaInterna, btnPediatria, btnGinecoObstetricia,
                    btnCirugia, btnPsiquiatria, btnCardiologia, btnDermatologia, btnEndocrinologia,
                    btnGastroenterologia, btnInfectologia, btnNefrologia, btnOftalmologia,
                    btnNeumologia, btnVerConsulta;

    private FirebaseUser firebaseUser;
    private DatabaseReference mDatabase;

    private Paciente paciente;

    private String uidPaciente = "";

    private int tamaño = 0, contador  = 0;

    private ArrayList<String> uidDoctor;
    private ArrayList<String> nombreMedico;
    private ArrayList<String> photoMedico;
    private ArrayList<String> especialidadMedico;
    private ArrayList<String> fechasMedico;
    private ArrayList<String> turnoMedico;

    private Bundle args = new Bundle();

    public static final long PERIODO = 5000;
    private Handler handler;
    private Runnable runnable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_paciente, container, false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        uidPaciente = firebaseUser.getUid();

        uidDoctor = new ArrayList<String>();
        nombreMedico = new ArrayList<String>();
        photoMedico = new ArrayList<String>();
        especialidadMedico = new ArrayList<String>();
        fechasMedico = new ArrayList<String>();
        turnoMedico = new ArrayList<String>();

        init(view);

        leerMedicos();

        return view;
    }

    void init(View v){
        llEspecialidades = v.findViewById(R.id.ll_especialidades);

        btnMedicinaFamiliar = v.findViewById(R.id.btn_familiar);
        btnMedicinaInterna = v.findViewById(R.id.btn_interna);
        btnPediatria = v.findViewById(R.id.btn_pediatria);
        btnGinecoObstetricia = v.findViewById(R.id.btn_gineco_obstetricia);
        btnCirugia = v.findViewById(R.id.btn_cirugia);
        btnPsiquiatria = v.findViewById(R.id.btn_psiquiatria);
        btnCardiologia = v.findViewById(R.id.btn_cardiologia);
        btnDermatologia = v.findViewById(R.id.btn_dermatologia);
        btnEndocrinologia = v.findViewById(R.id.btn_endocrinologia);
        btnGastroenterologia = v.findViewById(R.id.btn_gastroenterologia);
        btnInfectologia = v.findViewById(R.id.btn_infectologia);
        btnNefrologia = v.findViewById(R.id.btn_nefrologia);
        btnOftalmologia = v.findViewById(R.id.btn_oftalmologia);
        btnNeumologia = view.findViewById(R.id.btn_neumologia);
        btnVerConsulta = view.findViewById(R.id.btn_ver_consulta);

        btnMedicinaFamiliar.setOnClickListener(this);
        btnMedicinaInterna.setOnClickListener(this);
        btnPediatria.setOnClickListener(this);
        btnGinecoObstetricia.setOnClickListener(this);
        btnCirugia.setOnClickListener(this);
        btnPsiquiatria.setOnClickListener(this);
        btnCardiologia.setOnClickListener(this);
        btnDermatologia.setOnClickListener(this);
        btnEndocrinologia.setOnClickListener(this);
        btnGastroenterologia.setOnClickListener(this);
        btnInfectologia.setOnClickListener(this);
        btnNefrologia.setOnClickListener(this);
        btnOftalmologia.setOnClickListener(this);
        btnNeumologia.setOnClickListener(this);
        btnVerConsulta.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_familiar:
                verEspecialidad(btnMedicinaFamiliar.getText());
                break;
            case R.id.btn_interna:
                verEspecialidad(btnMedicinaInterna.getText());
                break;
            case R.id.btn_pediatria:
                verEspecialidad(btnPediatria.getText());
                break;
            case R.id.btn_gineco_obstetricia:
                verEspecialidad(btnGinecoObstetricia.getText());
                break;
            case R.id.btn_cirugia:
                verEspecialidad(btnCirugia.getText());
                break;
            case R.id.btn_psiquiatria:
                verEspecialidad(btnPsiquiatria.getText());
                break;
            case R.id.btn_cardiologia:
                verEspecialidad(btnCardiologia.getText());
                break;
            case R.id.btn_dermatologia:
                verEspecialidad(btnDermatologia.getText());
                break;
            case R.id.btn_endocrinologia:
                verEspecialidad(btnEndocrinologia.getText());
                break;
            case R.id.btn_gastroenterologia:
                verEspecialidad(btnGastroenterologia.getText());
                break;
            case R.id.btn_infectologia:
                verEspecialidad(btnInfectologia.getText());
                break;
            case R.id.btn_nefrologia:
                verEspecialidad(btnNefrologia.getText());
                break;
            case R.id.btn_oftalmologia:
                verEspecialidad(btnOftalmologia.getText());
                break;
            case R.id.btn_neumologia:
                verEspecialidad(btnNeumologia.getText());
                break;
            case R.id.btn_ver_consulta:
                if (tamaño > 0) {
                    leerPaciente();
                }
                break;
        }
    }

    private void verEspecialidad(CharSequence text) {
        DialogFragmentVerEspecialidad dialogFragmentVerEspecialidad = new DialogFragmentVerEspecialidad();
        args.putString("especialidad", text.toString());
        contador = 0;
        for (int i = 0; i < tamaño; i++) {
            if ((especialidadMedico.get(i).contains(text))
                    && !turnoMedico.get(i).contains(",")){
                contador = 1;
                args.putString("fotoMedico", photoMedico.get(i));
                args.putString("nombreMedico", nombreMedico.get(i));
                args.putString("fechaMedico", fechasMedico.get(i));
                args.putString("turnoMedico", turnoMedico.get(i));
                args.putString("uidDoctor", uidDoctor.get(i));
                args.putString("uidPaciente", uidPaciente);
                args.putString("fotoPaciente", paciente.getPhoto_perfil());
                args.putString("nombrePaciente", paciente.getUsername());
                args.putString("Consulta", null);
                assert getFragmentManager() != null;
                dialogFragmentVerEspecialidad.setArguments(args);
                dialogFragmentVerEspecialidad.show(requireActivity().getSupportFragmentManager(),
                        "DialogFragmentVerFuncion");
                break;
            }
        }
        if (contador != 1){
            dialogoNoEspecialidad();
        }
    }

    private void dialogoNoEspecialidad(){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.
                AlertDialog.Builder(getActivity());
        builder.setTitle("No se encuentra disponible un doctor para la especialidad");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }

    private void leerMedicos() {
        mDatabase.child(RutasRealtime.PATH_DOCTOR).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.child(Doctor.ESTADO).getValue().equals(true)){
                        uidDoctor.add(snapshot.child(Doctor.UID).getValue().toString());
                        nombreMedico.add(snapshot.child(Doctor.USERNAME).getValue().toString());
                        photoMedico.add(snapshot.child(Doctor.PHOTO_PERFIL).getValue().toString());
                        especialidadMedico.add(snapshot.child(Doctor.ESPECIALIDADES).getValue().toString());
                        fechasMedico.add(snapshot.child(Doctor.FECHAS).getValue().toString());
                        turnoMedico.add(snapshot.child(Doctor.TURNOS).getValue().toString());
                        tamaño++;
                    }
                }
                if (tamaño > 0) {
                    btnVerConsulta.setVisibility(View.VISIBLE);
                    llEspecialidades.setVisibility(View.GONE);
                    leerPaciente();
                }else {
                    btnVerConsulta.setVisibility(View.GONE);
                    llEspecialidades.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void leerPaciente() {
        mDatabase.child(RutasRealtime.PATH_PACIENTE).child(uidPaciente).get().
                addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        paciente = new Paciente(
                                dataSnapshot.child(paciente.UID).getValue().toString(),
                                dataSnapshot.child(paciente.USERNAME).getValue().toString(),
                                dataSnapshot.child(paciente.EMAIL).getValue().toString(),
                                dataSnapshot.child(paciente.PHOTO_PERFIL).getValue().toString(),
                                dataSnapshot.child(paciente.TELEPHONE).getValue().toString(),
                                dataSnapshot.child(paciente.CI).getValue().toString(),
                                dataSnapshot.child(paciente.REGISTRO).getValue().toString(),
                                dataSnapshot.child(paciente.ESPECIALIDAD).getValue().toString(),
                                (Boolean) dataSnapshot.child(paciente.ESTADO).getValue()
                        );
                        if (!paciente.getEspecialidades().equals("0")){
                            args.putString("Consulta", "consulta");
                            mDatabase.child(RutasRealtime.PATH_CONSULTAS).
                                    child(paciente.getEspecialidades()).get().
                                    addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                                        @Override
                                        public void onSuccess(DataSnapshot dataSnapshot) {
                                            llEspecialidades.setVisibility(View.GONE);
                                            ListaConsulta listaConsulta = new ListaConsulta(
                                                    dataSnapshot.child(ListaConsulta.UID).getValue().toString(),
                                                    dataSnapshot.child(ListaConsulta.UID_DOCTOR).getValue().toString(),
                                                    dataSnapshot.child(ListaConsulta.UID_PACIENTE).getValue().toString(),
                                                    dataSnapshot.child(ListaConsulta.FOTO_DOCTOR).getValue().toString(),
                                                    dataSnapshot.child(ListaConsulta.NOMBRE_DOCTOR).getValue().toString(),
                                                    dataSnapshot.child(ListaConsulta.FOTO_PACIENTE).getValue().toString(),
                                                    dataSnapshot.child(ListaConsulta.NOMBRE_PACIENTE).getValue().toString(),
                                                    dataSnapshot.child(ListaConsulta.TURNO).getValue().toString(),
                                                    dataSnapshot.child(ListaConsulta.FECHA).getValue().toString(),
                                                    dataSnapshot.child(ListaConsulta.ESPECIALIDAD).getValue().toString(),
                                                    dataSnapshot.child(ListaConsulta.ESTADO).getValue().toString()
                                            );

                                            args.putString("especialidad", btnMedicinaFamiliar.getText().toString());
                                            args.putString("fotoMedico", listaConsulta.getFoto_doctor());
                                            args.putString("nombreMedico", listaConsulta.getNombre_doctor());
                                            args.putString("fechaMedico", listaConsulta.getFecha());
                                            args.putString("turnoMedico", listaConsulta.getTurno());
                                            args.putString("fotoPaciente", listaConsulta.getFoto_paciente());
                                            args.putString("nombrePaciente", listaConsulta.getNombre_paciente());
                                            DialogFragmentVerEspecialidad dialogFragmentVerEspecialidad = new DialogFragmentVerEspecialidad();
                                            assert getFragmentManager() != null;
                                            dialogFragmentVerEspecialidad.setArguments(args);
                                            dialogFragmentVerEspecialidad.show(requireActivity().getSupportFragmentManager(),
                                                    "DialogFragmentVerFuncion");
                                        }
                                    });

                        }else {
                            btnVerConsulta.setVisibility(View.GONE);
                            llEspecialidades.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        handler = new Handler();
        runnable = new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                //leerPaciente();
                handler.postDelayed(this, PERIODO);
            }
        };
        handler.postDelayed(runnable, PERIODO);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}