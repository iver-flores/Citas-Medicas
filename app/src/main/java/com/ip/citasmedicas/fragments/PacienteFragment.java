package com.ip.citasmedicas.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.FirebaseDatabase;
import com.ip.citasmedicas.R;

public class PacienteFragment extends Fragment implements View.OnClickListener {

    private View view;

    private Button btnMedicinaFamiliar, btnMedicinaInterna, btnPediatria, btnGinecoObstetricia,
                    btnCirugia, btnPsiquiatria, btnCardiologia, btnDermatologia, btnEndocrinologia,
                    btnGastroenterologia, btnInfectologia, btnNefrologia, btnOftalmologia,
                    btnOtorrinolaringologia, btnNeumologia;

    private FirebaseUser firebaseUser;
    private DatabaseReference mDatabase;

    private String uidUsuario = "";

    int tamaño = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_paciente, container, false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        uidUsuario = firebaseUser.getUid();

        init(view);

        return view;
    }

    void init(View v){
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
        switch (view.getId()){
            case R.id.btn_familiar:
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
}