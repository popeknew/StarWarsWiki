package com.example.nbainfoapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nbainfoapp.R
import com.example.nbainfoapp.model.Film
import kotlinx.android.synthetic.main.films_row.view.*

class FilmsRecyclerViewAdapter : RecyclerView.Adapter<FilmsRecyclerViewAdapter.FilmsViewHolder>() {

    private val listOfFilms = mutableListOf<Film>()
    var onRowClickListener: ((Film) -> Unit)? = null
    var onRowLongClickListener: ((Film, Int) -> Unit)? = null

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
        holder.itemView.setOnClickListener { onRowClickListener?.invoke(film) }
        holder.itemView.setOnLongClickListener {
            onRowLongClickListener?.invoke(film, position)
            true
        }
    }

    class FilmsViewHolder(view: View) : RecyclerView.ViewHolder(view)

    fun swapFilms(list: MutableList<Film>) {
        listOfFilms.clear()
        listOfFilms.addAll(list)
        notifyDataSetChanged()
    }

    fun addFilmsList(list: MutableList<Film>) {
        listOfFilms.addAll(list)
        notifyItemInserted(listOfFilms.size)
    }

    fun removePerson(film: Film, position: Int) {
        listOfFilms.remove(film)
        notifyItemRemoved(position)
    }
}