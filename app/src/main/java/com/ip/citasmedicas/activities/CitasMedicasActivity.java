package com.ip.citasmedicas.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.animation.Animator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.android.volley.RequestQueue;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ip.citasmedicas.R;
import com.ip.citasmedicas.common.dataAccess.FirebaseCloudMessagingAPI;
import com.ip.citasmedicas.dialogs.DialogFragmentAccountAdministrador;
import com.ip.citasmedicas.dialogs.DialogFragmentAccountDoctor;
import com.ip.citasmedicas.dialogs.DialogFragmentAccountPaciente;
import com.ip.citasmedicas.entidades.Administrador;
import com.ip.citasmedicas.entidades.Doctor;
import com.ip.citasmedicas.entidades.Paciente;
import com.ip.citasmedicas.entidades.RutasRealtime;
import com.ip.citasmedicas.fragments.AdministradorFragment;
import com.ip.citasmedicas.fragments.DoctorFragment;
import com.ip.citasmedicas.fragments.PacienteFragment;

import java.util.Map;

public class CitasMedicasActivity extends AppCompatActivity implements View.OnClickListener {

    private static CitasMedicasActivity mInstance;

    private static long  INTERVALO = 0;

    private LinearLayout llFuncion;
    private Button btnAdministrador, btnDoctor, btnPaciente;

    private LinearLayout llEliminarCuenta, llVerPerfil, llAcercaDe, llSalir;
    private FloatingActionButton fabEliminarCuenta, fabVerPerfil, fabAcercaDe, fabSalir, fabPrincipal1;
    private View fabBGLayout;

    private boolean isFABOpen1 = false;

    private FirebaseUser firebaseUser;
    private DatabaseReference mDatabase;
    private FirebaseCloudMessagingAPI mCloudMessagingAPI;
    private RequestQueue mRequestQueue;

    private Fragment fragment;

    private String uid="", user = "", email="";

    private Bundle args = new Bundle();

    private Paciente paciente;
    private Doctor doctor;
    private Administrador administrador;

    private int registro = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citas_medicas);

        uid = getIntent().getExtras().getString("uid");

        mInstance = this;

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mCloudMessagingAPI = FirebaseCloudMessagingAPI.getInstance();

        init();

        leerPaciente(uid);
    }

    private void leerPaciente(String uid) {
        if (!uid.equals("")){
            mDatabase.child(RutasRealtime.PATH_PACIENTE).child(uid).get().
                    addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                        @Override
                        public void onSuccess(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(RutasRealtime.PATH_UID).getValue() == null ||
                                    !dataSnapshot.child(RutasRealtime.PATH_UID).getValue().equals(uid)){
                                registro = 1;
                                for (UserInfo profile : firebaseUser.getProviderData()) {
                                    user = profile.getDisplayName();
                                    email = profile.getEmail();
                                }
                                leerDoctor(uid);
                            }else if (dataSnapshot.child(RutasRealtime.PATH_UID).getValue().equals(uid)){
                                registro = 2;
                                paciente = new Paciente(
                                        dataSnapshot.child(paciente.UID).getValue().toString(),
                                        dataSnapshot.child(paciente.USERNAME).getValue().toString(),
                                        dataSnapshot.child(paciente.EMAIL).getValue().toString(),
                                        dataSnapshot.child(paciente.PHOTO_PERFIL).getValue().toString(),
                                        dataSnapshot.child(paciente.TELEPHONE).getValue().toString(),
                                        dataSnapshot.child(paciente.CI).getValue().toString(),
                                        dataSnapshot.child(paciente.REGISTRO).getValue().toString(),
                                        (Boolean) dataSnapshot.child(paciente.ESTADO).getValue()
                                );
                                if (paciente.isEstado()){
                                    fragment = new PacienteFragment();
                                    replaceFragment(fragment);
                                }else {
                                    leerEstado(RutasRealtime.PATH_PACIENTE);
                                    DialogFragmentAccountPaciente dialogFragmentAccountPaciente =
                                            new DialogFragmentAccountPaciente();
                                    args.putString(RutasRealtime.UID, uid);
                                    args.putString(RutasRealtime.USERNAME, paciente.getUsername());
                                    args.putString(RutasRealtime.EMAIL, paciente.getEmail());
                                    args.putString(RutasRealtime.PHOTO_PERFIL, paciente.getPhoto_perfil());
                                    args.putString(RutasRealtime.TELEPHONE, paciente.getTelephone());
                                    args.putString(RutasRealtime.CI, paciente.getCi());
                                    args.putString(RutasRealtime.REGISTRO, paciente.getRegistro());
                                    args.putBoolean(RutasRealtime.ESTADO, false);
                                    abrirFuncion(dialogFragmentAccountPaciente, args);

                                }
                            }else {
                                finishAffinity();
                            }
                        }
                    });
        }else {
            finishAffinity();
        }
    }

    private void leerDoctor(String uid) {
        if (!uid.equals("")){
            mDatabase.child(RutasRealtime.PATH_DOCTOR).child(uid).get().
                    addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                        @Override
                        public void onSuccess(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(RutasRealtime.PATH_UID).getValue() == null ||
                                    !dataSnapshot.child(RutasRealtime.PATH_UID).getValue().equals(uid)){
                                leerAdministrador(uid);
                            }else if (dataSnapshot.child(RutasRealtime.PATH_UID).getValue().equals(uid)){
                                registro = 3;
                                doctor = new Doctor(
                                        dataSnapshot.child(doctor.UID).getValue().toString(),
                                        dataSnapshot.child(doctor.USERNAME).getValue().toString(),
                                        dataSnapshot.child(doctor.EMAIL).getValue().toString(),
                                        dataSnapshot.child(doctor.PHOTO_PERFIL).getValue().toString(),
                                        dataSnapshot.child(doctor.TELEPHONE).getValue().toString(),
                                        dataSnapshot.child(doctor.CI).getValue().toString(),
                                        (Boolean) dataSnapshot.child(doctor.ESTADO).getValue()
                                );
                                if (doctor.isEstado()){
                                    fragment = new DoctorFragment();
                                    replaceFragment(fragment);
                                }else {
                                    leerEstado(RutasRealtime.PATH_DOCTOR);
                                    DialogFragmentAccountDoctor dialogFragmentAccountDoctor =
                                            new DialogFragmentAccountDoctor();
                                    args.putString(RutasRealtime.UID, uid);
                                    args.putString(RutasRealtime.USERNAME, doctor.getUsername());
                                    args.putString(RutasRealtime.EMAIL, doctor.getEmail());
                                    args.putString(RutasRealtime.PHOTO_PERFIL, doctor.getPhoto_perfil());
                                    args.putString(RutasRealtime.TELEPHONE, doctor.getTelephone());
                                    args.putString(RutasRealtime.CI, doctor.getCi());
                                    args.putBoolean(RutasRealtime.ESTADO, false);
                                    abrirFuncion(dialogFragmentAccountDoctor, args);

                                }
                            }else {
                                finishAffinity();
                            }
                        }
                    });
        }else {
            finishAffinity();
        }
    }

    private void leerAdministrador(String uid) {
        if (!uid.equals("")){
            mDatabase.child(RutasRealtime.PATH_ADMINISTRADOR).child(uid).get().
                    addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                        @Override
                        public void onSuccess(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(RutasRealtime.PATH_UID).getValue() == null ||
                                    !dataSnapshot.child(RutasRealtime.PATH_UID).getValue().equals(uid)){
                                llFuncion.setVisibility(View.VISIBLE);
                            }else if (dataSnapshot.child(RutasRealtime.PATH_UID).getValue().equals(uid)){
                                registro = 4;
                                administrador = new Administrador(
                                        dataSnapshot.child(administrador.UID).getValue().toString(),
                                        dataSnapshot.child(administrador.USERNAME).getValue().toString(),
                                        dataSnapshot.child(administrador.EMAIL).getValue().toString(),
                                        dataSnapshot.child(administrador.PHOTO_PERFIL).getValue().toString(),
                                        dataSnapshot.child(administrador.TELEPHONE).getValue().toString(),
                                        dataSnapshot.child(administrador.CI).getValue().toString(),
                                        (Boolean) dataSnapshot.child(administrador.ESTADO).getValue()
                                );
                                if (administrador.isEstado()){
                                    fragment = new AdministradorFragment();
                                    replaceFragment(fragment);
                                }else {
                                    leerEstado(RutasRealtime.PATH_ADMINISTRADOR);
                                    DialogFragmentAccountAdministrador dialogFragmentAccountadministrador =
                                            new DialogFragmentAccountAdministrador();
                                    args.putString(RutasRealtime.UID, uid);
                                    args.putString(RutasRealtime.USERNAME, administrador.getUsername());
                                    args.putString(RutasRealtime.EMAIL, administrador.getEmail());
                                    args.putString(RutasRealtime.PHOTO_PERFIL, administrador.getPhoto_perfil());
                                    args.putString(RutasRealtime.TELEPHONE, administrador.getTelephone());
                                    args.putString(RutasRealtime.CI, administrador.getCi());
                                    args.putBoolean(RutasRealtime.ESTADO, false);
                                    abrirFuncion(dialogFragmentAccountadministrador, args);

                                }
                            }else {
                                finishAffinity();
                            }
                        }
                    });
        }else {
            finishAffinity();
        }
    }

    public static synchronized CitasMedicasActivity getInstance(){
        return mInstance;
    }

    private void init() {
        llEliminarCuenta = findViewById(R.id.ll_eliminar_cuenta);
        llAcercaDe = findViewById(R.id.ll_acerca_de);
        llVerPerfil = findViewById(R.id.ll_ver_perfil);
        llSalir = findViewById(R.id.ll_salir);

        fabPrincipal1 = findViewById(R.id.fab_principal1);
        fabEliminarCuenta = findViewById(R.id.fab_eliminar_cuenta);
        fabAcercaDe = findViewById(R.id.fab_acerca_de);
        fabVerPerfil = findViewById(R.id.fab_ver_perfil);
        fabSalir = findViewById(R.id.fab_salir);

        llFuncion = findViewById(R.id.ll_funcion);
        btnAdministrador = findViewById(R.id.btn_administrador);
        btnDoctor = findViewById(R.id.btn_doctor);
        btnPaciente = findViewById(R.id.btn_paciente);

        fabBGLayout = findViewById(R.id.fabBGLayout);

        fabPrincipal1.setOnClickListener(this);
        fabEliminarCuenta.setOnClickListener(this);
        fabAcercaDe.setOnClickListener(this);
        fabVerPerfil.setOnClickListener(this);
        fabSalir.setOnClickListener(this);

        btnAdministrador.setOnClickListener(this);
        btnDoctor.setOnClickListener(this);
        btnPaciente.setOnClickListener(this);

        fabBGLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab_principal1:
                if (!isFABOpen1) {
                    showFABMenu(fabPrincipal1);
                } else {
                    closeFABMenu(fabPrincipal1);
                }
                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                mDatabase = FirebaseDatabase.getInstance().getReference();

            break;

            case R.id.fab_eliminar_cuenta:
                if (registro == 2){
                    eliminarCuenta(RutasRealtime.PATH_PACIENTE,paciente.getEmail());
                }else if (registro == 3){
                    eliminarCuenta(RutasRealtime.PATH_PACIENTE, doctor.getEmail());
                }else if (registro == 4){
                    eliminarCuenta(RutasRealtime.PATH_PACIENTE, administrador.getEmail());
                }
                break;
            case R.id.fab_acerca_de:
                openAbout();
                break;
            case R.id.fab_ver_perfil:
                if (registro == 2){
                    DialogFragmentAccountPaciente dialogFragmentAccountPaciente =
                            new DialogFragmentAccountPaciente();
                    args.putString(RutasRealtime.UID, uid);
                    args.putString(RutasRealtime.USERNAME, paciente.getUsername());
                    args.putString(RutasRealtime.EMAIL, paciente.getEmail());
                    args.putString(RutasRealtime.PHOTO_PERFIL, paciente.getPhoto_perfil());
                    args.putString(RutasRealtime.TELEPHONE, paciente.getTelephone());
                    args.putString(RutasRealtime.CI, paciente.getCi());
                    args.putString(RutasRealtime.REGISTRO, paciente.getRegistro());
                    args.putBoolean(RutasRealtime.ESTADO, false);
                    abrirFuncion(dialogFragmentAccountPaciente, args);
                }else if (registro == 3){
                    DialogFragmentAccountDoctor dialogFragmentAccountDoctor =
                            new DialogFragmentAccountDoctor();
                    args.putString(RutasRealtime.UID, uid);
                    args.putString(RutasRealtime.USERNAME, doctor.getUsername());
                    args.putString(RutasRealtime.EMAIL, doctor.getEmail());
                    args.putString(RutasRealtime.PHOTO_PERFIL, doctor.getPhoto_perfil());
                    args.putString(RutasRealtime.TELEPHONE, doctor.getTelephone());
                    args.putString(RutasRealtime.CI, doctor.getCi());
                    args.putBoolean(RutasRealtime.ESTADO, false);
                    abrirFuncion(dialogFragmentAccountDoctor, args);
                }else if (registro == 4) {
                    DialogFragmentAccountAdministrador dialogFragmentAccountadministrador =
                            new DialogFragmentAccountAdministrador();
                    args.putString(RutasRealtime.UID, uid);
                    args.putString(RutasRealtime.USERNAME, administrador.getUsername());
                    args.putString(RutasRealtime.EMAIL, administrador.getEmail());
                    args.putString(RutasRealtime.PHOTO_PERFIL, administrador.getPhoto_perfil());
                    args.putString(RutasRealtime.TELEPHONE, administrador.getTelephone());
                    args.putString(RutasRealtime.CI, administrador.getCi());
                    args.putBoolean(RutasRealtime.ESTADO, false);
                    abrirFuncion(dialogFragmentAccountadministrador, args);
                }
                break;
            case R.id.fab_salir:
                finishAffinity();
                break;

            case R.id.fabBGLayout:
                if (isFABOpen1){
                    closeFABMenu(fabPrincipal1);
                }
                break;

            case R.id.btn_administrador:
                DialogFragmentAccountAdministrador dialogFragmentAccountAdministrador = new DialogFragmentAccountAdministrador();
                if (registro == 1) {
                    Administrador administrador = new Administrador(uid, user, email, "0",
                            "+57", "0", false);
                    Map<String, Object> administradorValues = administrador.toAdministrador();
                    mDatabase.child(RutasRealtime.PATH_ADMINISTRADOR).child(uid).updateChildren(administradorValues);
                    args.putString(RutasRealtime.UID, uid);
                    args.putString(RutasRealtime.USERNAME, user);
                    args.putString(RutasRealtime.EMAIL, email);
                    args.putString(RutasRealtime.PHOTO_PERFIL, "0");
                    args.putString(RutasRealtime.TELEPHONE, "+57");
                    args.putString(RutasRealtime.CI, "0");
                    args.putBoolean(RutasRealtime.ESTADO, false);
                    leerEstado(RutasRealtime.PATH_ADMINISTRADOR);
                }
                abrirFuncion(dialogFragmentAccountAdministrador, args);
                break;
            case R.id.btn_doctor:
                DialogFragmentAccountDoctor dialogFragmentAccountDoctor = new DialogFragmentAccountDoctor();
                if (registro == 1) {
                    Doctor doctor = new Doctor(uid, user, email, "0", "+57", "0",
                            "0", "0", "0", false);
                    Map<String, Object> doctorValues = doctor.toDoctor();
                    mDatabase.child(RutasRealtime.PATH_DOCTOR).child(uid).updateChildren(doctorValues);
                    args.putString(RutasRealtime.UID, uid);
                    args.putString(RutasRealtime.USERNAME, user);
                    args.putString(RutasRealtime.EMAIL, email);
                    args.putString(RutasRealtime.PHOTO_PERFIL, "0");
                    args.putString(RutasRealtime.TELEPHONE, "+57");
                    args.putString(RutasRealtime.CI, "0");
                    args.putString(RutasRealtime.ESPECIALIDADES, "0");
                    args.putString(RutasRealtime.PACIENTES, "0");
                    args.putString(RutasRealtime.HORAS, "0");
                    args.putBoolean(RutasRealtime.ESTADO, false);
                    leerEstado(RutasRealtime.PATH_DOCTOR);
                }
                abrirFuncion(dialogFragmentAccountDoctor, args);
                break;
            case R.id.btn_paciente:
                DialogFragmentAccountPaciente dialogFragmentAccountPaciente = new DialogFragmentAccountPaciente();
                if (registro == 1) {
                    Paciente paciente = new Paciente(uid, user, email, "0",
                            "+57", "0", "0", "0", "0", "0", false);
                    Map<String, Object> pacienteValues = paciente.toPaciente();
                    mDatabase.child(RutasRealtime.PATH_PACIENTE).child(uid).updateChildren(pacienteValues);
                    args.putString(RutasRealtime.UID, uid);
                    args.putString(RutasRealtime.USERNAME, user);
                    args.putString(RutasRealtime.EMAIL, email);
                    args.putString(RutasRealtime.PHOTO_PERFIL, "0");
                    args.putString(RutasRealtime.TELEPHONE, "+57");
                    args.putString(RutasRealtime.CI, "0");
                    args.putString(RutasRealtime.REGISTRO, "0");
                    args.putString(RutasRealtime.ESPECIALIDADES, "0");
                    args.putString(RutasRealtime.HORAS, "0");
                    args.putString(RutasRealtime.PATH_DOCTOR, "0");
                    args.putBoolean(RutasRealtime.ESTADO, false);
                    leerEstado(RutasRealtime.PATH_PACIENTE);
                }
                abrirFuncion(dialogFragmentAccountPaciente, args);
                break;
        }
    }

    private void showFABMenu(View view) {
        if (view == fabPrincipal1) {
            isFABOpen1 = true;
            llEliminarCuenta.setVisibility(View.VISIBLE);
            llAcercaDe.setVisibility(View.VISIBLE);
            llVerPerfil.setVisibility(View.VISIBLE);
            llSalir.setVisibility(View.VISIBLE);
            fabBGLayout.setVisibility(View.VISIBLE);
            fabPrincipal1.animate().rotationBy(360);
            llEliminarCuenta.animate().translationY(-getResources().getDimension(R.dimen.standard__55));
            llAcercaDe.animate().translationY(-getResources().getDimension(R.dimen.standard__100));
            llVerPerfil.animate().translationY(-getResources().getDimension(R.dimen.standard__145));
            llSalir.animate().translationY(-getResources().getDimension(R.dimen.standard__190));
        }
    }

    private void closeFABMenu(View view) {
        if (view == fabPrincipal1) {
            isFABOpen1 = false;
            fabBGLayout.setVisibility(View.GONE);
            fabPrincipal1.animate().rotation(0);
            llEliminarCuenta.animate().translationY(0);
            llAcercaDe.animate().translationY(0);
            llVerPerfil.animate().translationY(0);
            llSalir.animate().translationY(0);
            llSalir.animate().translationY(0).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    if (!isFABOpen1) {
                        llEliminarCuenta.setVisibility(View.GONE);
                        llAcercaDe.setVisibility(View.GONE);
                        llVerPerfil.setVisibility(View.GONE);
                        llSalir.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - INTERVALO > 2000) {
            Snackbar.make(findViewById(android.R.id.content),
                    "Presione atrás nuevamente para salir.", Snackbar.LENGTH_SHORT).show();
            INTERVALO = System.currentTimeMillis();
        } else {
            finish();
            super.onBackPressed();
        }
        if (isFABOpen1) {
            closeFABMenu(fabPrincipal1);
        }
    }

    private void abrirFuncion(DialogFragment fragment, Bundle argsF){
        fragment.setArguments(argsF);
        assert getFragmentManager() != null;
        fragment.show(getSupportFragmentManager(), String.valueOf(fragment));
    }

    public void actualizarPaciente() {
        mDatabase.child(RutasRealtime.PATH_PACIENTE).child(uid).get().
                addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    paciente = new Paciente(
                            task.getResult().child(paciente.UID).getValue().toString(),
                            task.getResult().child(paciente.USERNAME).getValue().toString(),
                            task.getResult().child(paciente.EMAIL).getValue().toString(),
                            task.getResult().child(paciente.PHOTO_PERFIL).getValue().toString(),
                            task.getResult().child(paciente.TELEPHONE).getValue().toString(),
                            task.getResult().child(paciente.CI).getValue().toString(),
                            task.getResult().child(paciente.REGISTRO).getValue().toString(),
                            (Boolean) task.getResult().child(paciente.ESTADO).getValue()
                    );
                }
                registro = 2;
                llFuncion.setVisibility(View.GONE);
            }
        });
    }

    public void actualizarDoctor() {
        mDatabase.child(RutasRealtime.PATH_DOCTOR).child(uid).get().
                addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            doctor = new Doctor(
                                    task.getResult().child(doctor.UID).getValue().toString(),
                                    task.getResult().child(doctor.USERNAME).getValue().toString(),
                                    task.getResult().child(doctor.EMAIL).getValue().toString(),
                                    task.getResult().child(doctor.PHOTO_PERFIL).getValue().toString(),
                                    task.getResult().child(doctor.TELEPHONE).getValue().toString(),
                                    task.getResult().child(doctor.CI).getValue().toString(),
                                    (Boolean) task.getResult().child(doctor.ESTADO).getValue()
                            );
                        }
                        registro = 3;
                        llFuncion.setVisibility(View.GONE);
                    }
                });
    }

    public void actualizarAdministrador() {
        mDatabase.child(RutasRealtime.PATH_ADMINISTRADOR).child(uid).get().
                addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            administrador = new Administrador(
                                    task.getResult().child(administrador.UID).getValue().toString(),
                                    task.getResult().child(administrador.USERNAME).getValue().toString(),
                                    task.getResult().child(administrador.EMAIL).getValue().toString(),
                                    task.getResult().child(administrador.PHOTO_PERFIL).getValue().toString(),
                                    task.getResult().child(administrador.TELEPHONE).getValue().toString(),
                                    task.getResult().child(administrador.CI).getValue().toString(),
                                    (Boolean) task.getResult().child(administrador.ESTADO).getValue()
                            );
                        }
                        registro = 4;
                        llFuncion.setVisibility(View.GONE);
                    }
                });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void leerEstado(String funcion) {
        final DatabaseReference mPostReference = FirebaseDatabase.getInstance().getReference().
                child(funcion).child(uid).child(RutasRealtime.PATH_ESTADO);
        mPostReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue().equals(true)){
                    if (registro == 2){
                        leerPaciente(uid);
                    } else if (registro == 3){
                        leerDoctor(uid);
                    } else if (registro == 4){
                        leerAdministrador(uid);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void openAbout() {
        LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_about, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.DialogFragmentTheme)
                .setTitle(R.string.main_menu_about)
                .setView(view)
                .setPositiveButton(R.string.common_label_ok, null)
                .setNeutralButton(R.string.about_privacy, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://sites.google.com/d/1v-KCj2GdWsIOJx7PG52qKs2SGffD4g0K/p/1ps4JNj4rpkkuEjoFXcinkENCqStODvur/edit"));
                        startActivity(intent);
                    }
                });
        builder.show();
    }

    private void eliminarCuenta(String rutaE, String emailE){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Eliminar cuenta?");
        builder.setMessage("Si elimina la cuenta no podra volver a ingresar hasta que el administrador lo autorize. ");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mCloudMessagingAPI.unsubscribeToMyTopic(emailE);
                AuthUI.getInstance().signOut(CitasMedicasActivity.this);
                mDatabase.child(rutaE).child(uid).
                        child(RutasRealtime.PATH_ESTADO).setValue(false);
                finishAffinity();
            }
        }).setNegativeButton("Cancelar", null);
        builder.show();
    }
}