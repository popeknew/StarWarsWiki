package com.example.nbainfoapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nbainfoapp.R
import com.example.nbainfoapp.model.PersonModel
import com.example.nbainfoapp.model.PlanetModel
import kotlinx.android.synthetic.main.people_row.view.*
import kotlinx.android.synthetic.main.planets_row.view.*

class PlanetsRecyclerViewAdapter : RecyclerView.Adapter<PlanetsRecyclerViewAdapter.PlanetsViewHolder>() {

    private val listOfPlanets = mutableListOf<PlanetModel>()
    var onRowClickListener: ((PlanetModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanetsViewHolder {
        return PlanetsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.planets_row, parent, false))
    }

    override fun getItemCount(): Int = listOfPlanets.size

    override fun onBindViewHolder(holder: PlanetsViewHolder, position: Int) {
        val planet = listOfPlanets[position]

        holder.itemView.planetName.text = planet.name
        holder.itemView.setOnClickListener { onRowClickListener?.invoke(planet) }
    }

    class PlanetsViewHolder(view: View) : RecyclerView.ViewHolder(view)

    fun swapPlanets(list: MutableList<PlanetModel>) {
        listOfPlanets.clear()
        listOfPlanets.addAll(list)
        notifyDataSetChanged()
    }

    fun addPlanetsList(list: MutableList<PlanetModel>) {
        listOfPlanets.addAll(list)
        notifyItemInserted(listOfPlanets.size)
    }
}