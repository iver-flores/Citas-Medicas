package com.ip.citasmedicas.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ip.citasmedicas.R;
import com.ip.citasmedicas.common.dataAccess.FirebaseCloudMessagingAPI;
import com.ip.citasmedicas.utils.Conexion;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    private ProgressBar progressBar;

    private static final int RC_SING_IN = 123;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseUser firebaseUser;
    private FirebaseCloudMessagingAPI cloudMessagingAPI;

    private Conexion conexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        conexion = new Conexion();

        if (!conexion.executeCommand()){
            dialogoPago();
        }else {
            mFirebaseAuth = FirebaseAuth.getInstance();
            cloudMessagingAPI = FirebaseCloudMessagingAPI.getInstance();
            init();

            if (setProgress()){
                getStatusAuth();
            }
        }

    }

    private boolean setProgress() {
        showProgress();
        return true;
    }

    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    public void getStatusAuth() {
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    cloudMessagingAPI.subscribeToMyTopic(firebaseUser.getEmail());
                    hideProgress();
                    openMainActivity();
                }else {
                    openUILogin();
                }
            }
        };
    }

    public void openMainActivity() {
        Intent intent = new Intent(this, CitasMedicasActivity.class);
        intent.putExtra("uid", firebaseUser.getUid());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public void openUILogin() {
        AuthUI.IdpConfig googleIdp = new AuthUI.IdpConfig.GoogleBuilder().build();

        startActivityForResult(AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setIsSmartLockEnabled(false)
                .setTosAndPrivacyPolicyUrls("https://sites.google.com/d/1v-KCj2GdWsIOJx7PG52qKs2SGffD4g0K/p/1ps4JNj4rpkkuEjoFXcinkENCqStODvur/edit",
                        "https://sites.google.com/d/1v-KCj2GdWsIOJx7PG52qKs2SGffD4g0K/p/1ps4JNj4rpkkuEjoFXcinkENCqStODvur/edit")
                .setAvailableProviders(Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build(),
                        googleIdp))
                .setTheme(R.style.BaseTheme)
                .setLogo(R.mipmap.ic_launcher)
                .build(), RC_SING_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        result(requestCode, resultCode, data);
    }


    public void result(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK){
            switch (requestCode){
                case RC_SING_IN:
                    if (data != null){
                        showLoginSuccessfully(data);
                    }
                    break;
            }
        } else {
            showError(R.string.login_message_error);
        }
    }

    public void showLoginSuccessfully(Intent data) {
        IdpResponse response = IdpResponse.fromResultIntent(data);
    }

    public void showError(int resMsg) { Toast.makeText(this, resMsg, Toast.LENGTH_LONG).show(); }

    private void init() { progressBar = findViewById(R.id.progressBar); }

    private void dialogoPago(){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Sin Conexion");
        builder.setMessage("¿Debe conectar su dispositivo a internet primero?");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            super.finishAffinity();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAuthStateListener != null) {
            mFirebaseAuth.addAuthStateListener(mAuthStateListener);
        }
    }

    @Override
    protected void onStart() { super.onStart(); }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
    }
}
