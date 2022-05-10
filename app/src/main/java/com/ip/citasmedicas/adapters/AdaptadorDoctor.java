package com.ip.citasmedicas.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ip.citasmedicas.R;
import com.ip.citasmedicas.entidades.ListaDoctor;

import java.util.ArrayList;

public class AdaptadorDoctor extends BaseAdapter {

    verButtonListener verButton;

    public interface verButtonListener {
        void onButtonClickVerListner(ListaDoctor listaDoctores);
    }

    public void setCustomButtonListner(verButtonListener verButton) {
        this.verButton = verButton;
    }

    //Propiedades
    private ArrayList<ListaDoctor> listaDoctors;
    private Context context;
    private LayoutInflater inflater;

    //Constructor
    public AdaptadorDoctor(Context context, ArrayList<ListaDoctor> listaDoctors) {
        this.listaDoctors = listaDoctors;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //Base Adapter
    @Override
    public int getCount() {
        return listaDoctors.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    //Holder para crear View de cada item de la lista

    static class Holder {
        //Propiedades
        ImageView ivImagenDoctor;
        TextView tvNombre, tvEspecialidad;
        Button btnVer;
    }


    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        @SuppressLint({"ViewHolder", "InflateParams"})
        View rowView = inflater.inflate(R.layout.item_doctor_listview, null);
        Holder holder = new Holder();

        ListaDoctor listaDoctor = listaDoctors.get(position);

        //Init item_customlistivew
        holder.ivImagenDoctor =  rowView.findViewById(R.id.iv_doctor);
        holder.tvNombre =  rowView.findViewById(R.id.tv_nombre_doctor);
        holder.tvEspecialidad =  rowView.findViewById(R.id.tv_especialidad);
        holder.btnVer =  rowView.findViewById(R.id.btn_ver);

        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop();
        Glide.with(context)
                .load(Uri.parse(listaDoctor.getFoto_perfil()))
                .apply(options)
                .into(holder.ivImagenDoctor);

        holder.tvNombre.setText(listaDoctor.getNombre());
        holder.tvEspecialidad.setText(listaDoctor.getEspecialidades());

        holder.btnVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verButton.onButtonClickVerListner(listaDoctor);
            }
        });


        return rowView;
    }
}