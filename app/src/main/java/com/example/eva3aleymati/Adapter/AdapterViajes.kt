package com.example.eva3aleymati.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eva3aleymati.Models.Viajes
import com.example.eva3aleymati.R

class AdapterViajes(private var viajes: ArrayList<Viajes>):
    RecyclerView.Adapter<AdapterViajes.ViewHolder>() {
    // ViewHolder para los elementos de viaje
    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val origen: TextView = itemView.findViewById(R.id.tvOrigen)
        val destino: TextView = itemView.findViewById(R.id.tvDestino)
        val descripcion: TextView = itemView.findViewById(R.id.tvDescripcion)
    }

    // Inflar el layout para cada item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViajes.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_viajes, parent, false)
        return ViewHolder(view)
    }

    // Asignar los datos a los views
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val viaje = viajes[position]

        holder.origen.text = viaje.origen
        holder.destino.text = viaje.destino
        holder.descripcion.text = viaje.descripcion
    }

    // Obtener la cantidad de elementos
    override fun getItemCount(): Int {
        return viajes.size
    }
}