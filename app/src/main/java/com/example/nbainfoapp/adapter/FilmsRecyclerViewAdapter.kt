package com.example.nbainfoapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nbainfoapp.R
import com.example.nbainfoapp.helper.AssetsPathConverter
import com.example.nbainfoapp.model.Film
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader.createString
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.films_row.view.*

class FilmsRecyclerViewAdapter : RecyclerView.Adapter<FilmsRecyclerViewAdapter.FilmsViewHolder>() {

    private val listOfFilms = mutableListOf<Film>()
    var onRowClickListener: ((Film, image: View) -> Unit)? = null
    var onRowLongClickListener: ((Film, Int) -> Unit)? = null
    private val assetsPathConverter = AssetsPathConverter()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmsViewHolder {
        return FilmsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.films_row,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = listOfFilms.size

    override fun onBindViewHolder(holder: FilmsViewHolder, position: Int) {
        val film = listOfFilms[position]
        holder.itemView.filmTitle.text = film.title
        holder.itemView.setOnClickListener {
            onRowClickListener?.invoke(
                film,
                holder.itemView.filmImage
            )
        }
        holder.itemView.setOnLongClickListener {
            onRowLongClickListener?.invoke(film, position)
            true
        }
        Picasso.get()
            .load(assetsPathConverter.createAssetsAddress(film.title))
            .into(holder.itemView.filmImage)
    }

    class FilmsViewHolder(view: View) : RecyclerView.ViewHolder(view)

    fun swapFilms(list: MutableList<Film>) {
        listOfFilms.clear()
        listOfFilms.addAll(list)
        notifyDataSetChanged()
    }

    fun removePerson(film: Film, position: Int) {
        listOfFilms.remove(film)
        notifyItemRemoved(position)
    }
}