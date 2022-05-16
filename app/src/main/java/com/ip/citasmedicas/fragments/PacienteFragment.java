package com.ip.citasmedicas.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
import com.ip.citasmedicas.entidades.ListaConsultas;
import com.ip.citasmedicas.entidades.Medico;
import com.ip.citasmedicas.entidades.Paciente;
import com.ip.citasmedicas.entidades.RutasRealtime;

import java.util.ArrayList;

public class PacienteFragment extends Fragment implements View.OnClickListener {

    private View view;

    private LinearLayout llEspecialidades;

    private Button btnMedicinaFamiliar, btnMedicinaInterna, btnPediatria, btnGinecoObstetricia,
                    btnCirugia, btnPsiquiatria, btnCardiologia, btnDermatologia, btnEndocrinologia,
                    btnGastroenterologia, btnInfectologia, btnNefrologia, btnOftalmologia,
                    btnOtorrinolaringologia, btnNeumologia;

    private FirebaseUser firebaseUser;
    private DatabaseReference mDatabase;

    private Paciente paciente;

    private String uidPaciente = "";

    int tamaño = 0;

    private ArrayList<String> uidMedico;
    private ArrayList<String> nombreMedico;
    private ArrayList<String> photoMedico;
    private ArrayList<String> especialidadMedico;
    private ArrayList<String> fechasMedico;
    private ArrayList<String> turnoMedico;

    private Bundle args = new Bundle();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_paciente, container, false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        uidPaciente = firebaseUser.getUid();

        uidMedico = new ArrayList<String>();
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
        btnOtorrinolaringologia= v.findViewById(R.id.btn_otorrinolaringologia);
        btnNeumologia= view.findViewById(R.id.btn_neumologia);

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
        btnOtorrinolaringologia.setOnClickListener(this);
        btnNeumologia.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        DialogFragmentVerEspecialidad dialogFragmentVerEspecialidad = new DialogFragmentVerEspecialidad();
        switch (view.getId()){
            case R.id.btn_familiar:
                args.putString("especialidad", btnMedicinaFamiliar.getText().toString());

                for (int i = 0; i < tamaño; i++) {
                    if (btnMedicinaFamiliar.getText().toString().equals(especialidadMedico.get(i))){
                        args.putString("fotoMedico", photoMedico.get(i));
                        args.putString("nombreMedico", nombreMedico.get(i));
                        args.putString("fechaMedico", fechasMedico.get(i));
                        args.putString("turnoMedico", turnoMedico.get(i));
                        args.putString("uidPaciente", uidPaciente);
                        args.putString("fotoPaciente", paciente.getPhoto_perfil());
                        args.putString("nombrePaciente", paciente.getUsername());
                        assert getFragmentManager() != null;
                        dialogFragmentVerEspecialidad.setArguments(args);
                        dialogFragmentVerEspecialidad.show(requireActivity().getSupportFragmentManager(),
                                "DialogFragmentVerFuncion");
                        break;
                    }
                }
                break;
            case R.id.btn_interna:
                break;
            case R.id.btn_pediatria:
                break;
            case R.id.btn_gineco_obstetricia:
                break;
            case R.id.btn_cirugia:
                break;
            case R.id.btn_psiquiatria:
                break;
            case R.id.btn_cardiologia:
                break;
            case R.id.btn_dermatologia:
                break;
            case R.id.btn_endocrinologia:
                break;
            case R.id.btn_gastroenterologia:
                break;
            case R.id.btn_infectologia:
                break;
            case R.id.btn_nefrologia:
                break;
            case R.id.btn_oftalmologia:
                break;
            case R.id.btn_otorrinolaringologia:
                break;
            case R.id.btn_neumologia:
                break;
        }
    }

    private void leerMedicos() {
        mDatabase.child(RutasRealtime.PATH_DOCTOR).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.child(Medico.ESTADO).getValue().equals(true)){

                        uidMedico.add(snapshot.child(Medico.UID).getValue().toString());
                        nombreMedico.add(snapshot.child(Medico.USERNAME).getValue().toString());
                        photoMedico.add(snapshot.child(Medico.PHOTO_PERFIL).getValue().toString());
                        especialidadMedico.add(snapshot.child(Medico.ESPECIALIDADES).getValue().toString());
                        fechasMedico.add(snapshot.child(Medico.FECHAS).getValue().toString());
                        turnoMedico.add(snapshot.child(Medico.TURNOS).getValue().toString());
                        tamaño++;
                    }
                }
                if (tamaño > 0) {
                    leerPaciente();
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
                                            ListaConsultas listaConsultas = new ListaConsultas(
                                                    dataSnapshot.child(ListaConsultas.UID).getValue().toString(),
                                                    dataSnapshot.child(ListaConsultas.FOTO_DOCTOR).getValue().toString(),
                                                    dataSnapshot.child(ListaConsultas.NOMBRE_DOCTOR).getValue().toString(),
                                                    dataSnapshot.child(ListaConsultas.FOTO_PACIENTE).getValue().toString(),
                                                    dataSnapshot.child(ListaConsultas.NOMBRE_PACIENTE).getValue().toString(),
                                                    dataSnapshot.child(ListaConsultas.TURNO).getValue().toString(),
                                                    dataSnapshot.child(ListaConsultas.FECHA).getValue().toString(),
                                                    dataSnapshot.child(ListaConsultas.ESPECIALIDAD).getValue().toString()
                                            );

                                            args.putString("especialidad", btnMedicinaFamiliar.getText().toString());
                                            args.putString("fotoMedico", listaConsultas.getFoto_doctor());
                                            args.putString("nombreMedico", listaConsultas.getNombre_doctor());
                                            args.putString("fechaMedico", listaConsultas.getFecha());
                                            args.putString("turnoMedico", listaConsultas.getTurno());
                                            args.putString("fotoPaciente", listaConsultas.getFoto_paciente());
                                            args.putString("nombrePaciente", listaConsultas.getNombre_paciente());
                                            DialogFragmentVerEspecialidad dialogFragmentVerEspecialidad = new DialogFragmentVerEspecialidad();
                                            assert getFragmentManager() != null;
                                            dialogFragmentVerEspecialidad.setArguments(args);
                                            dialogFragmentVerEspecialidad.show(requireActivity().getSupportFragmentManager(),
                                                    "DialogFragmentVerFuncion");
                                        }
                                    });

                        }else {
                            llEspecialidades.setVisibility(View.VISIBLE);
                        }
                    }
                });

    }
}