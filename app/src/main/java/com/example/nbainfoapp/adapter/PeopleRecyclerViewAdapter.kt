package com.example.nbainfoapp.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nbainfoapp.R
import com.example.nbainfoapp.model.Person
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.people_row.view.*

class PeopleRecyclerViewAdapter :
    RecyclerView.Adapter<PeopleRecyclerViewAdapter.PeopleViewHolder>() {

    private val listOfPeople = mutableListOf<Person>()
    var onRowClickListener: ((Person) -> Unit)? = null
    var onRowLongClickListener: ((Person, Int) -> Unit)? = null
    val list = listOf<Drawable>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleViewHolder {
        return PeopleViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.people_row,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = listOfPeople.size

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {

        val person = listOfPeople[position]
        holder.itemView.personName.text = person.name
        holder.itemView.setOnClickListener { onRowClickListener?.invoke(person) }
        holder.itemView.setOnLongClickListener {
            onRowLongClickListener?.invoke(person, position)
            true
        }
        val imageName = nazwa(person.name)
//        Picasso.get()
//            .load(R.drawable.{imageName})
    }

    class PeopleViewHolder(view: View) : RecyclerView.ViewHolder(view)

    fun swapPeople(list: MutableList<Person>) {
        listOfPeople.clear()
        listOfPeople.addAll(list)
        notifyDataSetChanged()
    }

    fun addPeopleList(list: MutableList<Person>) {
        listOfPeople.addAll(list)
        notifyItemInserted(listOfPeople.size)
    }

    fun removePerson(person: Person, position: Int) {
        listOfPeople.remove(person)
        notifyItemRemoved(position)
    }

    private fun nazwa(text: String): String {
       val newText = text.replace("-", "")
        newText.trim()
        return newText
    }
}