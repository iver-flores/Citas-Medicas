package com.ip.citasmedicas.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

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
import com.ip.citasmedicas.adapters.AdaptadorDoctor;
import com.ip.citasmedicas.adapters.AdaptadorHistorial;
import com.ip.citasmedicas.adapters.AdaptadorPaciente;
import com.ip.citasmedicas.entidades.ListaConsulta;
import com.ip.citasmedicas.entidades.ListaDoctor;
import com.ip.citasmedicas.entidades.ListaPaciente;
import com.ip.citasmedicas.entidades.RutasRealtime;

import java.util.ArrayList;

public class DialogFragmentVerFuncion extends DialogFragment implements View.OnClickListener,
        AdaptadorDoctor.verButtonListener{

    private ImageButton ibAtras;
    private TextView tvTitulo;
    private ListView lvDoctor, lvPaciente, lvHistorial;
    private ImageView ivDoctor;
    private TextView tvNombre, tvEspecialidad, tvUno, tvDos, tvTres, tvCuatro, tvCinco, tvSeis
            , tvSiete, tvOcho, tvNueve, tvDiez, tvOnce, tvDoce, tvTrece, tvCatorse;
    private Button btnGuardar;

    private LinearLayout llEspecialidades;

    AlertDialog.Builder builder;

    private FirebaseUser firebaseUser;
    private DatabaseReference mDatabase;

    private ArrayList<ListaDoctor> listaDoctors;
    private ArrayList<ListaPaciente> listaPacientes;
    private ArrayList<ListaConsulta> listaConsultas;

    private AdaptadorDoctor adaptadorDoctor;
    private AdaptadorPaciente adaptadorPaciente;
    private AdaptadorHistorial adaptadorHistorial;

    private ArrayList<String> listaEspecialidades;

    private String funcion = "", uidDoctor = "";
    private Boolean uno = false, dos = false, tres = false, cuatro = false, cinco = false,
            seis = false, siete = false, ocho = false, nueve = false, diez = false, once = false,
            doce = false, trece = false, catorse = false;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return crearDialogoFrag();
    }

    private AlertDialog crearDialogoFrag() {
        builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (requireActivity()).getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_fragment_ver_funcion, null);
        funcion = getArguments().getString("funcion");
        builder.setView(v);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        listaDoctors = new ArrayList<>();
        listaPacientes = new ArrayList<>();
        listaConsultas = new ArrayList<>();

        init(v);

        if (funcion.equals("doctor")) {
            tvTitulo.setText("Lista de doctores");
            lvDoctor.setVisibility(View.VISIBLE);
            mDatabase.child(RutasRealtime.PATH_DOCTOR).get().
                    addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                        @Override
                        public void onSuccess(DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot :dataSnapshot.getChildren()){
                                final ListaDoctor listaDoctor = snapshot.getValue(ListaDoctor.class);
                                listaDoctor.setId(snapshot.getKey());
                                listaDoctors.add(listaDoctor);
                            }
                            configAdapterDoctor();
                        }
                    });
        } else if (funcion.equals("paciente")){
            tvTitulo.setText("Lista de pacientes");
            lvPaciente.setVisibility(View.VISIBLE);
            mDatabase.child(RutasRealtime.PATH_PACIENTE).get().
                    addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                        @Override
                        public void onSuccess(DataSnapshot dataSnapshot) {

                            for (DataSnapshot snapshot :dataSnapshot.getChildren()){
                                final ListaPaciente listaPaciente = snapshot.getValue(ListaPaciente.class);
                                listaPaciente.setId(snapshot.getKey());
                                listaPacientes.add(listaPaciente);
                            }
                            configAdapterPaciente();
                        }
                    });
        } else if (funcion.equals("historial")){
            tvTitulo.setText("Lista de consultas");
            lvHistorial.setVisibility(View.VISIBLE);
            mDatabase.child(RutasRealtime.PATH_CONSULTAS).get().
                    addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                        @Override
                        public void onSuccess(DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot :dataSnapshot.getChildren()){
                                final ListaConsulta listaConsulta = snapshot.getValue(ListaConsulta.class);
                                listaConsulta.setId(snapshot.getKey());
                                listaConsultas.add(listaConsulta);
                            }
                            configAdapterConsulta();
                        }
                    });
        }

        return builder.create();
    }

    private void init( View view) {
        ibAtras = view.findViewById(R.id.ib_atras);
        tvTitulo = view.findViewById(R.id.tv_titulo);
        lvDoctor = view.findViewById(R.id.lv_medicos);
        lvPaciente = view.findViewById(R.id.lv_pacientes);
        lvHistorial = view.findViewById(R.id.lv_historial);
        ivDoctor = view.findViewById(R.id.iv_doctor);
        tvNombre = view.findViewById(R.id.tv_nombre_d);
        tvEspecialidad = view.findViewById(R.id.tv_especialidad_d);
        tvUno = view.findViewById(R.id.tv_uno);
        tvDos = view.findViewById(R.id.tv_dos);
        tvTres = view.findViewById(R.id.tv_tres);
        tvCuatro = view.findViewById(R.id.tv_cuatro);
        tvCinco = view.findViewById(R.id.tv_cinco);
        tvSeis = view.findViewById(R.id.tv_seis);
        tvSiete = view.findViewById(R.id.tv_siete);
        tvOcho = view.findViewById(R.id.tv_ocho);
        tvNueve = view.findViewById(R.id.tv_nueve);
        tvDiez = view.findViewById(R.id.tv_diez);
        tvOnce = view.findViewById(R.id.tv_once);
        tvDoce = view.findViewById(R.id.tv_doce);
        tvTrece = view.findViewById(R.id.tv_trece);
        tvCatorse = view.findViewById(R.id.tv_catorce);
        btnGuardar = view.findViewById(R.id.btn_guardar);

        llEspecialidades = view.findViewById(R.id.ll_especialidades);

        tvUno.setOnClickListener(this);
        tvDos.setOnClickListener(this);
        tvTres.setOnClickListener(this);
        tvCuatro.setOnClickListener(this);
        tvCinco.setOnClickListener(this);
        tvSeis.setOnClickListener(this);
        tvSiete.setOnClickListener(this);
        tvOcho.setOnClickListener(this);
        tvNueve.setOnClickListener(this);
        tvDiez.setOnClickListener(this);
        tvOnce.setOnClickListener(this);
        tvDoce.setOnClickListener(this);
        tvTrece.setOnClickListener(this);
        tvCatorse.setOnClickListener(this);

        ibAtras.setOnClickListener(this);
        btnGuardar.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_uno:
                if (uno = !uno){
                    tvUno.setTextColor(Color.RED);
                    listaEspecialidades.add(tvUno.getText().toString());
                }else {
                    tvUno.setTextColor(Color.WHITE);
                    listaEspecialidades.remove(tvUno.getText().toString());
                }
                tvEspecialidad.setText(listaEspecialidades.toString().substring(1,listaEspecialidades.toString().length()-1));
                break;
            case R.id.tv_dos:
                if (dos = !dos){
                    tvDos.setTextColor(Color.RED);
                    listaEspecialidades.add(tvDos.getText().toString());
                }else {
                    tvDos.setTextColor(Color.WHITE);
                    listaEspecialidades.remove(tvDos.getText().toString());
                }
                tvEspecialidad.setText(listaEspecialidades.toString().substring(1,listaEspecialidades.toString().length()-1));
                break;
            case R.id.tv_tres:
                if (tres = !tres){
                    tvTres.setTextColor(Color.RED);
                    listaEspecialidades.add(tvTres.getText().toString());
                }else {
                    tvTres.setTextColor(Color.WHITE);
                    listaEspecialidades.remove(tvTres.getText().toString());
                }
                tvEspecialidad.setText(listaEspecialidades.toString().substring(1,listaEspecialidades.toString().length()-1));
                break;
            case R.id.tv_cuatro:
                if (cuatro = !cuatro){
                    tvCuatro.setTextColor(Color.RED);
                    listaEspecialidades.add(tvCuatro.getText().toString());
                }else {
                    tvCuatro.setTextColor(Color.WHITE);
                    listaEspecialidades.remove(tvCuatro.getText().toString());
                }
                tvEspecialidad.setText(listaEspecialidades.toString().substring(1,listaEspecialidades.toString().length()-1));
                break;
            case R.id.tv_cinco:
                if (cinco = !cinco){
                    tvCinco.setTextColor(Color.RED);
                    listaEspecialidades.add(tvCinco.getText().toString());
                }else {
                    tvCinco.setTextColor(Color.WHITE);
                    listaEspecialidades.remove(tvCinco.getText().toString());
                }
                tvEspecialidad.setText(listaEspecialidades.toString().substring(1,listaEspecialidades.toString().length()-1));
                break;
            case R.id.tv_seis:
                if (seis = !seis){
                    tvSeis.setTextColor(Color.RED);
                    listaEspecialidades.add(tvSeis.getText().toString());
                }else {
                    tvSeis.setTextColor(Color.WHITE);
                    listaEspecialidades.remove(tvSeis.getText().toString());
                }
                tvEspecialidad.setText(listaEspecialidades.toString().substring(1,listaEspecialidades.toString().length()-1));
                break;
            case R.id.tv_siete:
                if (siete = !siete){
                    tvSiete.setTextColor(Color.RED);
                    listaEspecialidades.add(tvSiete.getText().toString());
                }else {
                    tvSiete.setTextColor(Color.WHITE);
                    listaEspecialidades.remove(tvSiete.getText().toString());
                }
                tvEspecialidad.setText(listaEspecialidades.toString().substring(1,listaEspecialidades.toString().length()-1));
                break;
            case R.id.tv_ocho:
                if (ocho = !ocho){
                    tvOcho.setTextColor(Color.RED);
                    listaEspecialidades.add(tvOcho.getText().toString());
                }else {
                    tvOcho.setTextColor(Color.WHITE);
                    listaEspecialidades.remove(tvOcho.getText().toString());
                }
                tvEspecialidad.setText(listaEspecialidades.toString().substring(1,listaEspecialidades.toString().length()-1));
                break;
            case R.id.tv_nueve:
                if (nueve = !nueve){
                    tvNueve.setTextColor(Color.RED);
                    listaEspecialidades.add(tvNueve.getText().toString());
                }else {
                    tvNueve.setTextColor(Color.WHITE);
                    listaEspecialidades.remove(tvNueve.getText().toString());
                }
                tvEspecialidad.setText(listaEspecialidades.toString().substring(1,listaEspecialidades.toString().length()-1));
                break;
            case R.id.tv_diez:
                if (diez = !diez){
                    tvDiez.setTextColor(Color.RED);
                    listaEspecialidades.add(tvDiez.getText().toString());
                }else {
                    tvDiez.setTextColor(Color.WHITE);
                    listaEspecialidades.remove(tvDiez.getText().toString());
                }
                tvEspecialidad.setText(listaEspecialidades.toString().substring(1,listaEspecialidades.toString().length()-1));
                break;
            case R.id.tv_once:
                if (once = !once){
                    tvOnce.setTextColor(Color.RED);
                    listaEspecialidades.add(tvOnce.getText().toString());
                }else {
                    tvOnce.setTextColor(Color.WHITE);
                    listaEspecialidades.remove(tvOnce.getText().toString());
                }
                tvEspecialidad.setText(listaEspecialidades.toString().substring(1,listaEspecialidades.toString().length()-1));
                break;
            case R.id.tv_doce:
                if (doce = !doce){
                    tvDoce.setTextColor(Color.RED);
                    listaEspecialidades.add(tvDoce.getText().toString());
                }else {
                    tvDoce.setTextColor(Color.WHITE);
                    listaEspecialidades.remove(tvDoce.getText().toString());
                }
                tvEspecialidad.setText(listaEspecialidades.toString().substring(1,listaEspecialidades.toString().length()-1));
                break;
            case R.id.tv_trece:
                if (trece = !trece){
                    tvTrece.setTextColor(Color.RED);
                    listaEspecialidades.add(tvTrece.getText().toString());
                }else {
                    tvTrece.setTextColor(Color.WHITE);
                    listaEspecialidades.remove(tvTrece.getText().toString());
                }
                tvEspecialidad.setText(listaEspecialidades.toString().substring(1,listaEspecialidades.toString().length()-1));
                break;
            case R.id.tv_catorce:
                if (catorse = !catorse){
                    tvCatorse.setTextColor(Color.RED);
                    listaEspecialidades.add(tvCatorse.getText().toString());
                }else {
                    tvCatorse.setTextColor(Color.WHITE);
                    listaEspecialidades.remove(tvCatorse.getText().toString());
                }
                tvEspecialidad.setText(listaEspecialidades.toString().substring(1,listaEspecialidades.toString().length()-1));
                break;
            case R.id.ib_atras:
                dismiss();
                break;
            case R.id.btn_guardar:
                mDatabase.child(RutasRealtime.PATH_DOCTOR).child(uidDoctor).
                        child(RutasRealtime.ESPECIALIDADES).setValue(tvEspecialidad.getText().toString());
                dismiss();
                break;
        }
        if (tvEspecialidad.getText().toString().equals("")){
            tvEspecialidad.setText("0");
        }
    }

    private void configAdapterDoctor(){
        if (listaDoctors.size() > 0){
            adaptadorDoctor = new AdaptadorDoctor(requireActivity(), listaDoctors);
            adaptadorDoctor.setCustomButtonListner(DialogFragmentVerFuncion.this);
            lvDoctor.setAdapter(adaptadorDoctor);
            lvDoctor.setSelection(adaptadorDoctor.getCount() - 1);
        }
    }

    private void configAdapterPaciente(){
        if (listaPacientes.size() > 0){
            adaptadorPaciente = new AdaptadorPaciente(getActivity(), listaPacientes);
            lvPaciente.setAdapter(adaptadorPaciente);
            lvPaciente.setSelection(adaptadorPaciente.getCount() - 1);
        }
    }

    private void configAdapterConsulta(){
        if (listaConsultas.size() > 0){
            adaptadorHistorial = new AdaptadorHistorial(getActivity(), listaConsultas);
            lvHistorial.setAdapter(adaptadorHistorial);
            lvHistorial.setSelection(adaptadorHistorial.getCount() - 1);
        }
    }

    @Override
    public void onButtonClickVerListner(ListaDoctor listaDoctores) {
        lvDoctor.setVisibility(View.GONE);
        llEspecialidades.setVisibility(View.VISIBLE);
        uidDoctor = listaDoctores.getId();

        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop();
        Glide.with(getActivity())
                .load(Uri.parse(listaDoctores.getFoto_perfil()))
                .apply(options)
                .into(ivDoctor);

        tvNombre.setText(listaDoctores.getNombre());
        tvEspecialidad.setText(listaDoctores.getEspecialidades());
        listaEspecialidades = new ArrayList<>();
        tvTitulo.setText("Añadir especialidad");
    }
}
