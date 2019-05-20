package com.example.nbainfoapp.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import com.example.nbainfoapp.R
import com.example.nbainfoapp.activity.NavigationActivity
import com.example.nbainfoapp.activity.PlanetsDetailsActivity
import com.example.nbainfoapp.adapter.PlanetsRecyclerViewAdapter
import com.example.nbainfoapp.model.Planet
import com.example.nbainfoapp.repository.RepositoryRetrofit
import kotlinx.android.synthetic.main.fragment_planets.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import org.kodein.di.generic.instance


class PlanetsFragment : Fragment(), KodeinAware {

    override val kodein by kodein()
    private val repositoryRetrofit: RepositoryRetrofit by instance()
    private val planetsRecyclerViewAdapter = PlanetsRecyclerViewAdapter()
    private var currentPage = 1
    private val pagesNumber = 7

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_planets, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
        setupNextPageButton()
        setupPreviousPageButton()
    }

    private fun setupRecyclerView() {
        recycler_view.scheduleLayoutAnimation()
        recycler_view.adapter = planetsRecyclerViewAdapter
        getPlanetsFromServer(repositoryRetrofit, currentPage)
        planetsRecyclerViewAdapter.onRowClickListener = { planetModel, image ->
            startDetailsActivity(planetModel, image)
        }
        setupCurrentPageNumber(currentPage, pagesNumber)
    }

    private fun getPlanetsFromServer(repositoryRetrofit: RepositoryRetrofit, numberOfPages: Int) {
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                loading_spinner.visibility = View.VISIBLE
                recycler_view.visibility = View.GONE
                val list = repositoryRetrofit.getPlanets(numberOfPages)
                createListOfPlanets(list)
                recycler_view.visibility = View.VISIBLE
                loading_spinner.visibility = View.GONE
            }
        }
    }

    private fun createListOfPlanets(list: MutableList<Planet>) {
        recycler_view.scheduleLayoutAnimation()
        planetsRecyclerViewAdapter.swapPlanets(list)
    }

    private fun startDetailsActivity(planet: Planet, image: View) {
        val intent = PlanetsDetailsActivity.getIntent(context!!, planet)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            activity as NavigationActivity,
            image,
            "sendImage"
        )
        startActivity(intent, options.toBundle())
    }

    private fun setupCurrentPageNumber(currentPage: Int, pagesNumber: Int) {
        currentPageNumber.text = "$currentPage/$pagesNumber"
    }

    private fun setupNextPageButton() {
        nextPageButton.setOnClickListener {
            if (currentPage == pagesNumber) {
                currentPage = 1
                getPlanetsFromServer(repositoryRetrofit, currentPage)
                setupCurrentPageNumber(currentPage, pagesNumber)
            } else {
                currentPage += 1
                getPlanetsFromServer(repositoryRetrofit, currentPage)
                setupCurrentPageNumber(currentPage, pagesNumber)
            }
        }
    }

    private fun setupPreviousPageButton() {
        previousPageButton.setOnClickListener {
            if (currentPage == 1) {
                currentPage = pagesNumber
                getPlanetsFromServer(repositoryRetrofit, currentPage)
                setupCurrentPageNumber(currentPage, pagesNumber)
            } else {
                currentPage -= 1
                getPlanetsFromServer(repositoryRetrofit, currentPage)
                setupCurrentPageNumber(currentPage, pagesNumber)
            }
        }
    }
}
