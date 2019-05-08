package com.example.nbainfoapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nbainfoapp.R
import com.example.nbainfoapp.model.FilmModel
import kotlinx.android.synthetic.main.films_row.view.*

class FilmsRecyclerViewAdapter : RecyclerView.Adapter<FilmsRecyclerViewAdapter.FilmsViewHolder>() {

    private val listOfFilms = mutableListOf<FilmModel>()
    var onRowClickListener: ((FilmModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmsViewHolder {
        return FilmsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.films_row, parent, false))
    }

    override fun getItemCount(): Int = listOfFilms.size

    override fun onBindViewHolder(holder: FilmsViewHolder, position: Int) {
        val film = listOfFilms[position]
        holder.itemView.filmTitle.text = film.title
        holder.itemView.setOnClickListener { onRowClickListener?.invoke(film) }
    }

    class FilmsViewHolder(view: View) : RecyclerView.ViewHolder(view)

    fun swapFilms(list: MutableList<FilmModel>) {
        listOfFilms.clear()
        listOfFilms.addAll(list)
        notifyDataSetChanged()
    }

    fun addFilmsList(list: MutableList<FilmModel>) {
        listOfFilms.addAll(list)
        notifyItemInserted(listOfFilms.size)
    }
}