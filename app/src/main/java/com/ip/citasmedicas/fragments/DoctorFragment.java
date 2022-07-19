package com.ip.citasmedicas.fragments;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

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
import com.ip.citasmedicas.adapters.AdaptadorConsulta;
import com.ip.citasmedicas.adapters.AdaptadorDoctor;
import com.ip.citasmedicas.adapters.AdaptadorHistorial;
import com.ip.citasmedicas.dialogs.DialogFragmentDetalleConsulta;
import com.ip.citasmedicas.dialogs.DialogFragmentVerFuncion;
import com.ip.citasmedicas.entidades.ListaConsulta;
import com.ip.citasmedicas.entidades.ListaDoctor;
import com.ip.citasmedicas.entidades.RutasRealtime;

import java.util.ArrayList;

public class DoctorFragment extends Fragment implements AdaptadorConsulta.verButtonListener{

    private ListView lvConsultas;

    private View view;

    private FirebaseUser firebaseUser;
    private DatabaseReference mDatabase;

    private String uidDoctor = "";

    private ArrayList<ListaConsulta> listaConsultas;
    private AdaptadorConsulta adaptadorConsulta;

    int tamaño = 0;
    private Bundle args = new Bundle();

    public static final long PERIODO = 5000;
    private Handler handler;
    private Runnable runnable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_doctor, container, false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        listaConsultas = new ArrayList<>();

        adaptadorConsulta = new AdaptadorConsulta(getActivity(), listaConsultas);
        adaptadorConsulta.setCustomButtonListner(DoctorFragment.this);

        init(view);

        uidDoctor = firebaseUser.getUid();

        leerConsultas();

        return view;
    }

    private void init( View view) {
        lvConsultas = view.findViewById(R.id.lv_consultas);
    }

    private void leerConsultas() {
        mDatabase.child(RutasRealtime.PATH_CONSULTAS).get().
                addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        listaConsultas.clear();
                        for (DataSnapshot snapshot :dataSnapshot.getChildren()){
                            if (snapshot.child(RutasRealtime.UID_DOCTOR).getValue().equals(uidDoctor)
                                && snapshot.child(RutasRealtime.ESTADO).getValue().equals("En espera")){
                                final ListaConsulta listaConsulta = snapshot.getValue(ListaConsulta.class);
                                listaConsulta.setId(snapshot.getKey());
                                listaConsulta.setId_paciente((String) snapshot.child(RutasRealtime.UID_PACIENTE).getValue());
                                listaConsultas.add(listaConsulta);
                            }
                        }
                        configAdapterConsulta();
                    }
                });
    }

    private void configAdapterConsulta(){
        if (listaConsultas.size() > 0){
            lvConsultas.setAdapter(adaptadorConsulta);
            adaptadorConsulta.notifyDataSetChanged();
        }else {
            adaptadorConsulta.notifyDataSetChanged();
        }
    }

    @Override
    public void onButtonClickVerListner(ListaConsulta listaConsulta) {
        args.putString("fotoMedico", listaConsulta.getFoto_doctor());
        args.putString("nombreMedico", listaConsulta.getNombre_doctor());
        args.putString("especialidad", listaConsulta.getEspecialidad());
        args.putString("fechaMedico", listaConsulta.getFecha());
        args.putString("turnoMedico", listaConsulta.getTurno());
        args.putString("uid", listaConsulta.getId());
        args.putString("uidDoctor", listaConsulta.getId_doctor());
        args.putString("uidPaciente", listaConsulta.getId_paciente());
        args.putString("fotoPaciente", listaConsulta.getFoto_paciente());
        args.putString("nombrePaciente", listaConsulta.getNombre_paciente());
        assert getFragmentManager() != null;
        DialogFragmentDetalleConsulta dialogFragmentDetalleConsulta = new DialogFragmentDetalleConsulta();
        dialogFragmentDetalleConsulta.setArguments(args);
        dialogFragmentDetalleConsulta.show(requireActivity().getSupportFragmentManager(),
                "DialogFragmentDetalleConsulta");
    }

    @Override
    public void onResume() {
        super.onResume();
        handler = new Handler();
        runnable = new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                leerConsultas();
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