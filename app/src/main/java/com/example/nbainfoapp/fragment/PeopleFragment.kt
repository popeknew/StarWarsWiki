package com.example.nbainfoapp.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nbainfoapp.R
import com.example.nbainfoapp.activity.NavigationActivity
import com.example.nbainfoapp.activity.PeopleDetailsActivity
import com.example.nbainfoapp.adapter.PeopleRecyclerViewAdapter
import com.example.nbainfoapp.model.Person
import com.example.nbainfoapp.repository.RepositoryRetrofit
import kotlinx.android.synthetic.main.fragment_people.*
import kotlinx.android.synthetic.main.fragment_people.view.*
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
    private val remotePeopleArray = arrayListOf<Person>()
    var currentPage = 1
    val pagesNumber = 9

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getPeopleFromServer(repositoryRetrofit, currentPage)
    }

    private fun setupRecyclerView() {
        recycler_view.adapter = peopleRecyclerViewAdapter
        peopleRecyclerViewAdapter.onRowClickListener = { personModel ->
            startDetailsActivity(personModel, remotePeopleArray)
        }
        setupCurrentPageNumber(currentPage, pagesNumber)
    }

    private fun getPeopleFromServer(repositoryRetrofit: RepositoryRetrofit, numberOfPages: Int) {
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                loading_spinner.visibility = View.VISIBLE
                val list = repositoryRetrofit.getPeople(numberOfPages)
                remotePeopleArray.addAll(list)
                createListOfPeople(list)
                loading_spinner.visibility = View.GONE
            }
        }
    }

    private fun createListOfPeople(list: MutableList<Person>) {
        peopleRecyclerViewAdapter.swapPeople(list)
    }

    private fun startDetailsActivity(person: Person, list: ArrayList<Person>) {
        val intent = PeopleDetailsActivity.getIntent(context!!, person, list)
        startActivity(intent)
    }

    private fun setupCurrentPageNumber(currentPage: Int, pagesNumber: Int) {
        currentPageNumber.text = "$currentPage/$pagesNumber"
    }

    private fun setupNextPageButton() {
        nextPageButton.setOnClickListener {
            if (currentPage == pagesNumber) {
                currentPage = 1
                getPeopleFromServer(repositoryRetrofit, currentPage)
                setupCurrentPageNumber(currentPage, pagesNumber)
            } else {
                currentPage += 1
                getPeopleFromServer(repositoryRetrofit, currentPage)
                setupCurrentPageNumber(currentPage, pagesNumber)
            }
        }
    }

    private fun setupPreviousPageButton() {
        previousPageButton.setOnClickListener {
            if (currentPage == 1) {
                currentPage = pagesNumber
                getPeopleFromServer(repositoryRetrofit, currentPage)
                setupCurrentPageNumber(currentPage, pagesNumber)
            } else {
                currentPage -= 1
                getPeopleFromServer(repositoryRetrofit, currentPage)
                setupCurrentPageNumber(currentPage, pagesNumber)
            }
        }
    }
}
