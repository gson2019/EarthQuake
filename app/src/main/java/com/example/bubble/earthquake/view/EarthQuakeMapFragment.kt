package com.example.bubble.earthquake.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.bubble.earthquake.R
import com.example.bubble.earthquake.model.Earthquake
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_earthquake_map.*

class EarthQuakeMapFragment : Fragment() {
    private var earthquake : Earthquake? = null
    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        if (earthquake == null) {
            arguments?.get(EARTH_QUAKE_ITEM)?.let{
                earthquake = it as Earthquake
            }
        }
        mapToolbar.title = "Earthquake at (${earthquake!!.lng}, ${earthquake!!.lat})"
        (activity as MainActivity).supportActionBar?.title = "Earthquake at (${earthquake!!.lng}, ${earthquake!!.lat})"
        mapProgressBar.visibility = View.INVISIBLE
        val earthquakeLoc = LatLng(earthquake!!.lat, earthquake!!.lng)
        googleMap.addMarker(MarkerOptions().position(earthquakeLoc).title("Earthquake Magnitude ${earthquake!!.magnitude}"))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(earthquakeLoc, 4.0f))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_earthquake_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapToolbar.setNavigationOnClickListener(View.OnClickListener {
            // back button pressed
            Navigation.findNavController(
                requireActivity(),
                R.id.nav_host_fragment
            ).navigateUp()
        })
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    companion object {
        const val EARTH_QUAKE_ITEM = "EARTH_QUAKE_ITEM"
    }
}