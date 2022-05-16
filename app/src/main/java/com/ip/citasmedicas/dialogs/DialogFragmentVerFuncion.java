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
import com.ip.citasmedicas.entidades.ListaMedico;
import com.ip.citasmedicas.entidades.ListaPaciente;
import com.ip.citasmedicas.entidades.RutasRealtime;

import java.util.ArrayList;

public class DialogFragmentVerFuncion extends DialogFragment implements View.OnClickListener,
        AdaptadorDoctor.verButtonListener{

    private ImageButton ibAtras;
    private TextView tvTitulo;
    private ListView lvDoctor, lvPaciente, lvHistorial;
    private ImageView ivDoctor;
    private TextView tvNombre, tvEspecialidad, tvUno, tvDos, tvTres, tvCuatro, tvCinco, tvSeis;
    private Button btnGuardar;

    private LinearLayout llEspecialidades;

    AlertDialog.Builder builder;

    private FirebaseUser firebaseUser;
    private DatabaseReference mDatabase;

    private ArrayList<ListaMedico> listaMedicos;
    private ArrayList<ListaPaciente> listaPacientes;

    private AdaptadorDoctor adaptadorDoctor;
    private AdaptadorPaciente adaptadorPaciente;
    private AdaptadorHistorial adaptadorHistorial;

    private ArrayList<String> listaEspecialidades;

    private String funcion = "", uidDoctor = "";
    private Boolean uno = false, dos = false, tres = false, cuatro = false, cinco = false, seis = false;

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

        listaMedicos = new ArrayList<>();
        listaPacientes = new ArrayList<>();

        init(v);

        if (funcion.equals("doctor")) {
            tvTitulo.setText("Lista de doctores");
            lvDoctor.setVisibility(View.VISIBLE);
            mDatabase.child(RutasRealtime.PATH_DOCTOR).get().
                    addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                        @Override
                        public void onSuccess(DataSnapshot dataSnapshot) {

                            for (DataSnapshot snapshot :dataSnapshot.getChildren()){
                                final ListaMedico listaMedico = snapshot.getValue(ListaMedico.class);
                                listaMedico.setId(snapshot.getKey());
                                listaMedicos.add(listaMedico);
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
        btnGuardar = view.findViewById(R.id.btn_guardar);

        llEspecialidades = view.findViewById(R.id.ll_especialidades);

        tvUno.setOnClickListener(this);
        tvDos.setOnClickListener(this);
        tvTres.setOnClickListener(this);
        tvCuatro.setOnClickListener(this);
        tvCinco.setOnClickListener(this);
        tvSeis.setOnClickListener(this);

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
            case R.id.ib_atras:
                dismiss();
                break;
            case R.id.btn_guardar:
                mDatabase.child(RutasRealtime.PATH_DOCTOR).child(uidDoctor).
                        child(RutasRealtime.ESPECIALIDADES).setValue(tvEspecialidad.getText().toString());
                break;
        }
        if (tvEspecialidad.getText().toString().equals("")){
            tvEspecialidad.setText("0");
        }
    }

    private void configAdapterDoctor(){
        if (listaMedicos.size() > 0){
            adaptadorDoctor = new AdaptadorDoctor(requireActivity(), listaMedicos);
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

    @Override
    public void onButtonClickVerListner(ListaMedico listaDoctores) {
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
