package com.ip.citasmedicas.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ip.citasmedicas.R;
import com.ip.citasmedicas.dialogs.DialogFragmentAccountDoctor;
import com.ip.citasmedicas.dialogs.DialogFragmentAccountPaciente;
import com.ip.citasmedicas.dialogs.DialogFragmentVerFuncion;

public class AdministradorFragment extends Fragment implements View.OnClickListener {

    private ImageButton ibVerMedico, ibVerPaciente, ibHistorial;

    private View view;

    private FirebaseUser firebaseUser;
    private DatabaseReference mDatabase;

    private String uidAdministrador = "";

    int tamaño = 0;

    private Bundle args = new Bundle();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_administrador, container, false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        uidAdministrador = firebaseUser.getUid();

        init(view);

        return view;
    }

    private void init(View view) {
        ibVerMedico = view.findViewById(R.id.ib_ver_doctor);
        ibVerPaciente = view.findViewById(R.id.ib_ver_paciente);
        ibHistorial = view.findViewById(R.id.ib_ver_historial);

        ibVerMedico.setOnClickListener(this);
        ibVerPaciente.setOnClickListener(this);
        ibHistorial.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        DialogFragmentVerFuncion dialogFragmentVerFuncion = new DialogFragmentVerFuncion();
        switch (view.getId()){
            case R.id.ib_ver_doctor:
                args.putString("funcion", "doctor");
                assert getFragmentManager() != null;
                dialogFragmentVerFuncion.setArguments(args);
                dialogFragmentVerFuncion.show(requireActivity().getSupportFragmentManager(),
                        "DialogFragmentVerFuncion");
                break;
            case R.id.ib_ver_paciente:
                args.putString("funcion", "paciente");
                assert getFragmentManager() != null;
                dialogFragmentVerFuncion.setArguments(args);
                dialogFragmentVerFuncion.show(requireActivity().getSupportFragmentManager(),
                        "DialogFragmentVerFuncion");
                break;
            case R.id.ib_ver_historial:
                args.putString("funcion", "historial");
                assert getFragmentManager() != null;
                dialogFragmentVerFuncion.setArguments(args);
                dialogFragmentVerFuncion.show(requireActivity().getSupportFragmentManager(),
                        "DialogFragmentVerFuncion");
                break;

        }

    }
}