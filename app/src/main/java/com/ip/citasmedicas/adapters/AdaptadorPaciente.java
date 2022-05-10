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
import com.ip.citasmedicas.entidades.ListaPaciente;

import java.util.ArrayList;

public class AdaptadorPaciente extends BaseAdapter {

    //Propiedades
    private ArrayList<ListaPaciente> listaPacientes;
    private Context context;
    private LayoutInflater inflater;

    //Constructor
    public AdaptadorPaciente(Context context, ArrayList<ListaPaciente> listaPacientes) {
        this.listaPacientes = listaPacientes;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //Base Adapter
    @Override
    public int getCount() {
        return listaPacientes.size();
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
        ImageView ivImagenPaciente;
        TextView tvNombre, tvEspecialidad, tvHora;
    }


    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        @SuppressLint({"ViewHolder", "InflateParams"})
        View rowView = inflater.inflate(R.layout.item_paciente_listview, null);
        Holder holder = new Holder();

        ListaPaciente listaPaciente = listaPacientes.get(position);

        //Init item_customlistivew
        holder.ivImagenPaciente =  rowView.findViewById(R.id.iv_paciente);
        holder.tvNombre =  rowView.findViewById(R.id.tv_nombre_paciente);
        holder.tvEspecialidad =  rowView.findViewById(R.id.tv_consulta);
        holder.tvHora =  rowView.findViewById(R.id.tv_hora);

        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop();
        Glide.with(context)
                .load(Uri.parse(listaPaciente.getFoto_perfil()))
                .apply(options)
                .into(holder.ivImagenPaciente);

        holder.tvNombre.setText(listaPaciente.getNombre());
        holder.tvEspecialidad.setText(listaPaciente.getEspecialidades());
        holder.tvHora.setText(listaPaciente.getHoras());

        return rowView;
    }
}
