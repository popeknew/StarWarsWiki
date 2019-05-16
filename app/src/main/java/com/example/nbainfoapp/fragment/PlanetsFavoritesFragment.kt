package com.example.nbainfoapp.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import com.example.nbainfoapp.R
import com.example.nbainfoapp.activity.FavoritesActivity
import com.example.nbainfoapp.activity.PeopleDetailsActivity
import com.example.nbainfoapp.activity.PlanetsDetailsActivity
import com.example.nbainfoapp.adapter.PlanetsRecyclerViewAdapter
import com.example.nbainfoapp.model.Film
import com.example.nbainfoapp.model.Planet
import com.example.nbainfoapp.repository.PlanetsDatabaseRepository
import kotlinx.android.synthetic.main.fragment_planets_favorites.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import org.kodein.di.generic.instance

class PlanetsFavoritesFragment : Fragment(), KodeinAware {

    override val kodein by kodein()
    private val planetsDatabaseRepository: PlanetsDatabaseRepository by instance()
    private val planetsRecyclerViewAdapter = PlanetsRecyclerViewAdapter()
    private val deleteFromFavoritesDialogFragment = DeleteFromFavoritesDialogFragment()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_planets_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        recycler_view.scheduleLayoutAnimation()
        recycler_view.adapter = planetsRecyclerViewAdapter
        synchoronizePlanetsDatabase()
        planetsRecyclerViewAdapter.onRowLongClickListener = { planet, position ->
            deleteFromFavoritesDialogFragment.show(fragmentManager!!, "tag")
            deleteFromFavoritesDialogFragment.deleteDecision = { decision ->
                deletePlanetFromDatabase(planet, position, decision)
                synchoronizePlanetsDatabase()
            }
        }
        planetsRecyclerViewAdapter.onRowClickListener = { planet, image ->
            startDetailsActivity(planet, image)
        }
    }

    private fun synchoronizePlanetsDatabase() = GlobalScope.launch(Dispatchers.Main) {
        val list = planetsDatabaseRepository.getFavoritePlanets()
        recycler_view.scheduleLayoutAnimation()
        planetsRecyclerViewAdapter.swapPlanets(list)
    }

    private fun deletePlanetFromDatabase(planet: Planet, position: Int, decision: Boolean) {
        if (decision) {
            GlobalScope.launch(Dispatchers.Main) {
                planetsDatabaseRepository.deletePlanet(planet)
                planetsRecyclerViewAdapter.removePlanet(planet, position)
            }
        }
    }

    private fun startDetailsActivity(planet: Planet, image: View) {
        val intent = PlanetsDetailsActivity.getIntent(context!!, planet)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity as FavoritesActivity, image, "sendImage")
        startActivity(intent)
    }
}
