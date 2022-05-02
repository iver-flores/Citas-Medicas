package com.ip.citasmedicas.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.volley.RequestQueue;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ip.citasmedicas.R;
import com.ip.citasmedicas.common.dataAccess.FirebaseCloudMessagingAPI;
import com.ip.citasmedicas.entidades.Administrador;

import java.util.ArrayList;

public class CitasMedicasActivity extends AppCompatActivity {

    private static CitasMedicasActivity mInstance;

    private static long  INTERVALO = 0;
    private static final int REQUEST_CHECK_SETTINGS = 100;
    private static final int ACCESS_FINE_LOCATION_INTENT = 3;

    private LinearLayout llCerrarSesion, llVerPerfil, llAcercaDe;
    private FloatingActionButton fabEliminarCuenta, fabVerPerfil, fabAcercaDe, fabPrincipal1;
    private View fabBGLayout;
    private RelativeLayout rlMapa;

    private boolean isFABOpen1 = false;

    private FirebaseUser firebaseDomiciliario;
    private DatabaseReference mDatabase;
    private FirebaseCloudMessagingAPI mCloudMessagingAPI;
    private RequestQueue mRequestQueue;

    private Fragment fragment;

    private String uidDomiciliario = "";

    private Administrador administrador;

    ArrayList<String> uidAllies;
    ArrayList<String> latitud;
    ArrayList<String> longitud;
    private int tamaño = 0;
    private String geoJson;

    private Bundle args = new Bundle();

    private GoogleApiClient googleApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citas_medicas);

        mInstance = this;

        firebaseDomiciliario = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mCloudMessagingAPI = FirebaseCloudMessagingAPI.getInstance();

        uidAllies = new ArrayList<String>();
        latitud = new ArrayList<String>();
        longitud = new ArrayList<String>();

        uidDomiciliario = firebaseDomiciliario.getUid();

        init();

    }

    public static synchronized CitasMedicasActivity getInstance(){
        return mInstance;
    }

    private void init() {

        /*llCerrarSesion = findViewById(R.id.ll_eliminar_cuenta);
        llAcercaDe = findViewById(R.id.ll_acerca_de);
        llVerPerfil = findViewById(R.id.ll_ver_perfil);

        fabPrincipal1 = findViewById(R.id.fab_principal1);
        fabEliminarCuenta = findViewById(R.id.fab_eliminar_cuenta);
        fabAcercaDe = findViewById(R.id.fab_acerca_de);
        fabVerPerfil = findViewById(R.id.fab_ver_perfil);*/

        fabBGLayout = findViewById(R.id.fabBGLayout);
        rlMapa = findViewById(R.id.rl_map);

    }

}