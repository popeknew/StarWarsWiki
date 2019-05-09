package com.example.nbainfoapp.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nbainfoapp.R
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
    val repositoryRetrofit: RepositoryRetrofit by instance()

    val planetsRecyclerViewAdapter = PlanetsRecyclerViewAdapter()

    var currentPage = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_planets, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        recycler_view.adapter = planetsRecyclerViewAdapter
        getPlanetsFromServer(repositoryRetrofit, currentPage)
        planetsRecyclerViewAdapter.onRowClickListener = {planetModel ->
            startDetailsActivity(planetModel)
        }
    }

    private fun getPlanetsFromServer(repositoryRetrofit: RepositoryRetrofit, numberOfPages: Int) {
        // (activity as NavigationActivity).showProgress()

        GlobalScope.launch {
            val list = repositoryRetrofit.getPlanets(numberOfPages)
            withContext(Dispatchers.Main) {
                createListOfPlanets(list)
                // (activity as NavigationActivity).hideProgress()
            }
        }
    }

    private fun createListOfPlanets(list: MutableList<Planet>) {
        planetsRecyclerViewAdapter.swapPlanets(list)
    }

    private fun startDetailsActivity(planet: Planet) {
        val intent = PlanetsDetailsActivity.getIntent(context!!, planet)
        startActivity(intent)
    }
}
