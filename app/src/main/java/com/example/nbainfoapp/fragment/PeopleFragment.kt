package com.example.nbainfoapp.fragment


import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.nbainfoapp.R
import com.example.nbainfoapp.activity.NavigationActivity
import com.example.nbainfoapp.adapter.PeopleRecyclerViewAdapter
import com.example.nbainfoapp.model.PersonModel
import com.example.nbainfoapp.repository.RepositoryRetrofit
import kotlinx.android.synthetic.main.fragment_people.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import org.kodein.di.generic.instance


class PeopleFragment : Fragment(), KodeinAware {

    override val kodein by kodein()
    private val repositoryRetrofit: RepositoryRetrofit by instance()

    private val peopleRecyclerViewAdapter = PeopleRecyclerViewAdapter()

    var currentPage = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_people, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
        setupNextPageButton()
        setupPreviousPageButton()
    }

    private fun setupRecyclerView() {
        recycler_view.adapter = peopleRecyclerViewAdapter
        getPeopleFromServer(repositoryRetrofit, currentPage)
    }

    private fun getPeopleFromServer(repositoryRetrofit: RepositoryRetrofit, numberOfPages: Int) {
        (activity as NavigationActivity).showProgress()

        GlobalScope.launch {
            val list = repositoryRetrofit.getPeople(numberOfPages)
            withContext(Dispatchers.Main) {
                createListOfPeople(list)
               //
            }
        }.invokeOnCompletion { (activity as NavigationActivity).hideProgress() }
    }

    private fun createListOfPeople(list: MutableList<PersonModel>) {
        peopleRecyclerViewAdapter.swapPeople(list)
    }

    private fun setupNextPageButton() {
        if (currentPage < 9) {
            nextPageButton.setOnClickListener {
                currentPage += 1
                getPeopleFromServer(repositoryRetrofit, currentPage)

                setButtonVisible(nextPageButton)
                println(currentPage)
            }
        } else {
            setButtonGone(nextPageButton)
        }
    }

    private fun setupPreviousPageButton() {
        if (currentPage > 1) {
            previousPageButton.setOnClickListener {
                currentPage -= 1
                getPeopleFromServer(repositoryRetrofit, currentPage)

                setButtonVisible(previousPageButton)
                println(currentPage)
            }
        } else {
            setButtonGone(previousPageButton)
        }
    }

    fun setButtonVisible(view: View) {
        view.visibility = View.VISIBLE
    }

    fun setButtonGone(view: View) {
        view.visibility = View.GONE
    }
}
