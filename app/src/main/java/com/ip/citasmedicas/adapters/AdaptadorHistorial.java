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
import com.ip.citasmedicas.entidades.Paciente;

import java.util.ArrayList;

public class AdaptadorHistorial extends BaseAdapter {

    buyButtonListener buyButton;

    public interface buyButtonListener {
        void onButtonClickBuyListner(ArrayList<String> listaCompraimagenes,
                                     ArrayList<String> listaCompraPro,
                                     ArrayList<String> listaCompraPre);
    }

    public void setCustomButtonListner(buyButtonListener buyButton) {
        this.buyButton = buyButton;
    }

    //Propiedades
    private ArrayList<Paciente> pacientes;
    private ArrayList<String> listaCompraimagenes;
    private ArrayList<String> listaCompraProductos;
    private ArrayList<String> listaCompraPrecios;
    private Context context;
    private LayoutInflater inflater;

    //Constructor
    public AdaptadorHistorial(Context context, ArrayList<Paciente> paci) {
        this.pacientes = paci;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listaCompraimagenes = new ArrayList<>();
        this.listaCompraProductos = new ArrayList<>();
        this.listaCompraPrecios = new ArrayList<>();
    }

    //Base Adapter
    @Override
    public int getCount() {
        return pacientes.size();
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
        TextView tvNombrePaciente, tvNombreMedico, tvConsulta, tvAtendido;
    }


    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        @SuppressLint({"ViewHolder", "InflateParams"})
        View rowView = inflater.inflate(R.layout.item_historial_listview, null);
        Holder holder = new Holder();

        Paciente paciente = pacientes.get(position);

        //Init item_customlistivew
        holder.ivImagenPaciente =  rowView.findViewById(R.id.iv_paciente);
        holder.tvNombrePaciente =  rowView.findViewById(R.id.tv_nombre_paciente);
        holder.tvNombreMedico =  rowView.findViewById(R.id.tv_nombre_doctor);
        holder.tvConsulta =  rowView.findViewById(R.id.tv_consulta);
        holder.tvAtendido =  rowView.findViewById(R.id.tv_atendido);

        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop();
        Glide.with(context)
                .load(Uri.parse(pacientes.get(position).getPhoto_perfil()))
                .apply(options)
                .into(holder.ivImagenPaciente);

        holder.tvNombrePaciente.setText(pacientes.get(position).getUsername());
        holder.tvNombreMedico.setText(pacientes.get(position).getUsername());
        holder.tvConsulta.setText(pacientes.get(position).getUsername());
        holder.tvAtendido.setText(pacientes.get(position).getEspecialidades());

        return rowView;
    }
}
