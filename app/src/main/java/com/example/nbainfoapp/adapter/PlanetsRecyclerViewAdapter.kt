package com.example.nbainfoapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nbainfoapp.R
import com.example.nbainfoapp.helper.AssetsPathConverter
import com.example.nbainfoapp.model.Planet
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader.createString
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.planets_row.view.*

class PlanetsRecyclerViewAdapter :
    RecyclerView.Adapter<PlanetsRecyclerViewAdapter.PlanetsViewHolder>() {

    private val listOfPlanets = mutableListOf<Planet>()
    var onRowClickListener: ((Planet, image: View) -> Unit)? = null
    var onRowLongClickListener: ((Planet, Int) -> Unit)? = null
    private val assetsPathConverter = AssetsPathConverter()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanetsViewHolder {
        return PlanetsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.planets_row,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = listOfPlanets.size

    override fun onBindViewHolder(holder: PlanetsViewHolder, position: Int) {
        val planet = listOfPlanets[position]

        holder.itemView.planetName.text = planet.name
        holder.itemView.setOnClickListener {
            onRowClickListener?.invoke(
                planet,
                holder.itemView.planetImage
            )
        }
        holder.itemView.setOnLongClickListener {
            onRowLongClickListener?.invoke(planet, position)
            true
        }
        Picasso.get()
            .load(assetsPathConverter.createAssetsAddress(planet.name))
            .into(holder.itemView.planetImage)
    }

    class PlanetsViewHolder(view: View) : RecyclerView.ViewHolder(view)

    fun swapPlanets(list: MutableList<Planet>) {
        listOfPlanets.clear()
        listOfPlanets.addAll(list)
        notifyDataSetChanged()
    }

    fun removePlanet(planet: Planet, position: Int) {
        listOfPlanets.remove(planet)
        notifyItemRemoved(position)
    }
}