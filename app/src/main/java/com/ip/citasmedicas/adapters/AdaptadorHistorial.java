package com.ip.citasmedicas.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ip.citasmedicas.R;
import com.ip.citasmedicas.entidades.ListaConsulta;

import java.util.ArrayList;

public class AdaptadorHistorial extends BaseAdapter {

    //Propiedades
    private ArrayList<ListaConsulta> listaConsultas;
    private Context context;
    private LayoutInflater inflater;

    //Constructor
    public AdaptadorHistorial(Context context, ArrayList<ListaConsulta> listaConsultas) {
        this.listaConsultas = listaConsultas;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //Base Adapter
    @Override
    public int getCount() {
        return listaConsultas.size();
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
        ImageView ivPaciente;
        TextView tvNombrePaciente, tvNombreDoctor, tvConsulta, tvAtendido;
    }


    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        @SuppressLint({"ViewHolder", "InflateParams"})
        View rowView = inflater.inflate(R.layout.item_historial_listview, null);
        Holder holder = new Holder();

        ListaConsulta listaConsulta = listaConsultas.get(position);

        //Init item_customlistivew
        holder.ivPaciente =  rowView.findViewById(R.id.iv_paciente);
        holder.tvNombrePaciente =  rowView.findViewById(R.id.tv_nombre_paciente);
        holder.tvNombreDoctor =  rowView.findViewById(R.id.tv_nombre_doctor);
        holder.tvConsulta =  rowView.findViewById(R.id.tv_consulta);
        holder.tvAtendido =  rowView.findViewById(R.id.tv_atendido);

        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop();
        Glide.with(context)
                .load(Uri.parse(listaConsulta.getFoto_paciente()))
                .apply(options)
                .into(holder.ivPaciente);

        holder.tvNombrePaciente.setText("Paciente: "+listaConsulta.getNombre_paciente());
        holder.tvNombreDoctor.setText("Doctor: "+listaConsulta.getNombre_doctor());
        holder.tvConsulta.setText(listaConsulta.getEspecialidad());
        holder.tvAtendido.setText(listaConsulta.getEstado());

        return rowView;
    }
}
