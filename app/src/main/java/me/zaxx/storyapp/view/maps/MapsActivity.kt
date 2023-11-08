package me.zaxx.storyapp.view.maps

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import me.zaxx.storyapp.R
import me.zaxx.storyapp.databinding.ActivityMapsBinding
import me.zaxx.storyapp.view.ViewModelFactory

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val mapsViewModel by viewModels<MapsViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        setMapStyle()
        addManyMarker()
        mMap.uiSettings.isZoomControlsEnabled = true


        val indonesia = LatLng(0.7893, 113.9213)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(indonesia))
    }

    private fun addManyMarker(){
        mapsViewModel.getSession().observe(this){
            val getToken = it.token
            mapsViewModel.getMapsData(getToken)
        }
        mapsViewModel.listItem.observe(this){
            it.forEach {
                val latLang = LatLng(it.lat!!,it.lon!!)
                mMap.addMarker(
                    MarkerOptions()
                        .position(latLang)
                        .title(it.name)
                        .snippet(it.description)
                )
            }
        }
    }

    private fun setMapStyle(){
        try {
            val success =mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this,R.raw.map_style))
            if (!success){
                Log.e(TAG, "setMapStyle: Failed To parsing Style" )
            }
        } catch (e: Resources.NotFoundException){
            Log.e(TAG, "Can't find Style", e)
        }
    }


    companion object {
        const val TAG = "MapsActivity"
    }
}