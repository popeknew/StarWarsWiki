package com.example.nbainfoapp.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nbainfoapp.R
import com.example.nbainfoapp.helper.AssetsPathConverter
import com.example.nbainfoapp.model.Person
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader.createString
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.people_row.view.*

class PeopleRecyclerViewAdapter :
    RecyclerView.Adapter<PeopleRecyclerViewAdapter.PeopleViewHolder>() {

    private val listOfPeople = mutableListOf<Person>()
    var onRowClickListener: ((Person, image: View) -> Unit)? = null
    var onRowLongClickListener: ((Person, Int) -> Unit)? = null
    private val assetsPathConverter = AssetsPathConverter()

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
        holder.itemView.setOnClickListener {
            onRowClickListener?.invoke(
                person,
                holder.itemView.personImage
            )
        }
        holder.itemView.setOnLongClickListener {
            onRowLongClickListener?.invoke(person, position)
            true
        }
        Picasso.get()
            .load(assetsPathConverter.createAssetsAddress(person.name))
            .into(holder.itemView.personImage)
    }

    class PeopleViewHolder(view: View) : RecyclerView.ViewHolder(view)

    fun swapPeople(list: MutableList<Person>) {
        listOfPeople.clear()
        listOfPeople.addAll(list)
        notifyDataSetChanged()
    }

    fun removePerson(person: Person, position: Int) {
        listOfPeople.remove(person)
        notifyItemRemoved(position)
    }
}