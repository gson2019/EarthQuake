package com.example.bubble.earthquake.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.bubble.earthquake.R
import com.example.bubble.earthquake.model.EarthQuakeWrapper
import com.example.bubble.earthquake.model.Earthquake
import com.example.bubble.earthquake.view.EarthQuakeMapFragment.Companion.EARTH_QUAKE_ITEM
import com.example.bubble.earthquake.viewmodel.EarthQuakeViewModel
import kotlinx.android.synthetic.main.fragment_earthquake_list.*

/**
 * A fragment representing a list of Items.
 */
class EarthquakeListFragment : Fragment(), EarthquakeAdapter.OnEarthquakeClickListener {
    private lateinit var earthquakeViewModel : EarthQuakeViewModel
    private lateinit var mAdapter: EarthquakeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_earthquake_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpEarthquakeListRv()

        earthquakeViewModel = ViewModelProviders.of(this).get(EarthQuakeViewModel::class.java)
        earthquakeViewModel.earthQuakeLiveData.observe(viewLifecycleOwner, Observer {
           it.let { resource ->
               earthquakeListProg.visibility = View.INVISIBLE
               when (resource.status) {
                   EarthQuakeWrapper.Status.SUCCESS -> {
                       listToolbar.title = "Earthquake List"
                       listToolbar.visibility = View.VISIBLE
                       (activity as MainActivity).setSupportActionBar(listToolbar)
                       val earthquakeList = it.data!!.earthquakes as MutableList<Earthquake>
                       updateEarthquakeData(earthquakeList)
                   } else -> {
                        wifiReminder.visibility = View.VISIBLE
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                   }
               }
           }
        })
        earthquakeViewModel.getEarthQuakeList(requireActivity())
    }

    override fun onClick(position: Int, earthquake: Earthquake) {
        val earthquakeBundle = Bundle()
        earthquakeBundle.putParcelable(EARTH_QUAKE_ITEM, earthquake)
        findNavController(this).navigate(R.id.action_to_earthquake_map, earthquakeBundle)
    }

    private fun setUpEarthquakeListRv() {
        earthquakeRv.layoutManager = LinearLayoutManager(requireContext())
        mAdapter = EarthquakeAdapter(requireContext(), mutableListOf(), this)
        earthquakeRv.adapter = mAdapter
        earthquakeRv.addItemDecoration(DividerItemDecoration(earthquakeRv.context, DividerItemDecoration.VERTICAL))
    }

    private fun updateEarthquakeData(earthquakeList : MutableList<Earthquake>) {
        mAdapter.updateDataSource(earthquakeList)
    }
}