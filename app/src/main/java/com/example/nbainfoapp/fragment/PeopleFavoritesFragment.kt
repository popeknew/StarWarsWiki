package com.example.nbainfoapp.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu

import com.example.nbainfoapp.R
import com.example.nbainfoapp.activity.PeopleDetailsActivity
import com.example.nbainfoapp.adapter.PeopleRecyclerViewAdapter
import com.example.nbainfoapp.model.Person
import com.example.nbainfoapp.repository.PeopleDatabaseRepository
import kotlinx.android.synthetic.main.fragment_people_favorites.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import org.kodein.di.generic.instance

class PeopleFavoritesFragment : Fragment(), KodeinAware {

    override val kodein by kodein()
    private val peopleDatabaseRepository: PeopleDatabaseRepository by instance()
    private val peopleRecyclerViewAdapter = PeopleRecyclerViewAdapter()
    private val deleteFromFavoritesDialogFragment = DeleteFromFavoritesDialogFragment()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_people_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        recycler_view.adapter = peopleRecyclerViewAdapter
        synchronizePeopleDatabase()
        peopleRecyclerViewAdapter.onRowLongClickListener = { person, position  ->
            deleteFromFavoritesDialogFragment.show(fragmentManager!!, "tag")
            deleteFromFavoritesDialogFragment.deleteDecision = { decision ->
                deletePersonFromDatabase(person, position, decision)
                synchronizePeopleDatabase()
            }
        }
        peopleRecyclerViewAdapter.onRowClickListener = { person ->
            startDetailsActivity(person)
        }
    }

    private fun synchronizePeopleDatabase() = GlobalScope.launch(Dispatchers.Main) {
        val list = peopleDatabaseRepository.getFavoritePeople()
        peopleRecyclerViewAdapter.swapPeople(list)
    }

    private fun deletePersonFromDatabase(person: Person, position: Int, decision: Boolean) {
        if (decision) {
            GlobalScope.launch(Dispatchers.Main) {
                peopleDatabaseRepository.deletePerson(person)
                peopleRecyclerViewAdapter.removePerson(person, position)
            }
        }
    }

    private fun startDetailsActivity(person: Person) {
        val list = arrayListOf<Person>()
        val intent = PeopleDetailsActivity.getIntent(context!!, person, list)
        startActivity(intent)
    }
}
