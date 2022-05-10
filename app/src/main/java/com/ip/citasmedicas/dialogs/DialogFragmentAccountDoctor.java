package com.ip.citasmedicas.dialogs;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ip.citasmedicas.R;
import com.ip.citasmedicas.activities.CitasMedicasActivity;
import com.ip.citasmedicas.entidades.Medico;
import com.ip.citasmedicas.entidades.Paciente;
import com.ip.citasmedicas.entidades.RutasRealtime;
import com.ip.citasmedicas.utils.Validacion;

import de.hdodenhof.circleimageview.CircleImageView;

public class DialogFragmentAccountDoctor extends DialogFragment implements View.OnClickListener {

    private static final int RC_FROM_GALLERY = 124;

    private AlertDialog.Builder builder;

    private ImageButton ibAtras;
    private CircleImageView civFotoPerfil;
    private TextView tvNombre, tvEmail, tvEstado;
    private TextInputLayout tilCi, tilTelephone;
    private EditText etCi, etTelephone;
    private Button btnGuardar;
    private Validacion validacion;

    private String uid="", user="", email="",ci="", telefono="", fotoPerfil ="", especialidad;

    private View v;

    private DatabaseReference mDatabase;

    private boolean estado = false;

    public DialogFragmentAccountDoctor(){}

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return crearDialogoFrag(savedInstanceState);
    }

    private AlertDialog crearDialogoFrag(Bundle savedInstanceState) {
        builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (requireActivity()).getLayoutInflater();
        v = inflater.inflate(R.layout.dialog_fragment_account_doctor, null);
        builder.setView(v);

        init(v);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        uid = getArguments().getString(Medico.UID);
        user = getArguments().getString(Medico.USERNAME);
        email = getArguments().getString(Medico.EMAIL);
        ci = getArguments().getString(Medico.CI);
        telefono = getArguments().getString(Medico.TELEPHONE);
        fotoPerfil = getArguments().getString(Medico.PHOTO_PERFIL);
        estado = getArguments().getBoolean(Medico.ESTADO);

        validacion = new Validacion();

        entradaDatos();
        mostrarCuenta(v);

        return builder.create();
    }

    private void mostrarCuenta(View v) {
        tvNombre.setText("Nombre: " + user);
        tvEmail.setText("Email: " + email);
        if (estado){
            tvEstado.setText("Estado: Habilitado");
        }else {
            tvEstado.setText("Estado: Deshabilitado");
        }
        etTelephone.setText(telefono);
        etCi.setText(ci);
        if (!fotoPerfil.equals("0")){
            verImage(Uri.parse(fotoPerfil));
        }

    }

    private void init( View view) {
        ibAtras = view.findViewById(R.id.ib_atras);
        civFotoPerfil = view.findViewById(R.id.civ_registro);
        tvNombre = view.findViewById(R.id.tv_user_name);
        tvEmail = view.findViewById(R.id.tv_email);
        tvEstado = view.findViewById(R.id.tv_estado);
        tilCi = view.findViewById(R.id.til_ci);
        tilTelephone = view.findViewById(R.id.til_telephone);
        etCi = view.findViewById(R.id.et_ci);
        etTelephone = view.findViewById(R.id.et_telephone);
        btnGuardar = view.findViewById(R.id.btn_guardar_cambios);

        ibAtras.setOnClickListener(this);
        civFotoPerfil.setOnClickListener(this);
        btnGuardar.setOnClickListener(this);

    }

    private void entradaDatos() {
        etTelephone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!validacion.esTelefonoValido(String.valueOf(s))){
                    tilTelephone.setError("Teléfono inválido");
                }else {
                    tilTelephone.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        etCi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!validacion.esTextoValido(String.valueOf(s))){
                    tilCi.setError("Cédula inválida");
                }else {
                    tilCi.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_atras:
                dismiss();
                break;
            case R.id.civ_registro:
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RC_FROM_GALLERY);
                break;
            case R.id.btn_guardar_cambios:
                telefono = etTelephone.getText().toString();
                ci = etCi.getText().toString();
                if (validacion.esTelefonoValido(telefono) && validacion.esTextoValido(ci)){
                    if (!fotoPerfil.equals("0")){
                        actualizarDatos(telefono, ci, fotoPerfil);
                        ((CitasMedicasActivity)getActivity()).actualizarDoctor();
                        dialogoRegistro();
                    }
                }
                break;
        }
    }

    private void verImage(Uri photoUrl) {
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop();
        Glide.with(this)
                .load(photoUrl)
                .apply(options)
                .into(civFotoPerfil);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_FROM_GALLERY && resultCode == RESULT_OK){
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference reference = storage.getReference();
            reference = storage.getReference().child(RutasRealtime.PATH_DOCTOR).child(email).
                    child("doctor");
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null){
                StorageReference finalReference = reference;
                reference.putFile(selectedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        finalReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                fotoPerfil = uri.toString();
                                verImage(uri);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "Error......", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
            }
        }
    }

    private void actualizarDatos(String telefonoA, String ciA, String fotoPerfilA){
        mDatabase.child(RutasRealtime.PATH_DOCTOR).child(uid).child(Paciente.PHOTO_PERFIL).setValue(fotoPerfilA);
        mDatabase.child(RutasRealtime.PATH_DOCTOR).child(uid).child(Medico.TELEPHONE).setValue(telefonoA);
        mDatabase.child(RutasRealtime.PATH_DOCTOR).child(uid).child(Medico.CI).setValue(ciA);
        mDatabase.child(RutasRealtime.PATH_DOCTOR).child(uid).child(Paciente.ESTADO).setValue(true);
    }

    private void dialogoRegistro(){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(requireActivity());
        builder.setTitle("Esperando Verificación");
        builder.setMessage("¿Debe esperar que el administrador habilite su cuenta?");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
        builder.show();
    }


}