package com.karthik.delivery.deliverymap.view

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.karthik.delivery.R
import com.karthik.delivery.base.util.loadImage
import com.karthik.delivery.databinding.ActivityDeliveryMapBinding
import com.karthik.delivery.deliverylist.data.Delivery
import kotlinx.android.synthetic.main.activity_delivery_map.*

class DeliveryMapActivity : AppCompatActivity(), OnMapReadyCallback {


    private lateinit var binding: ActivityDeliveryMapBinding
    private var deliveryData: Delivery? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_delivery_map)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        unWrapIntent()
        setUpActionListeners()
    }

    private fun setUpActionListeners() {
        back_btn.setOnClickListener {
            onBackPressed()
        }
    }

    private fun unWrapIntent() {
        deliveryData = intent.getParcelableExtra<Delivery>("delivery")
        deliveryData?.let { showDeliveryCard() }
    }

    private fun showDeliveryCard() {

        binding.delivery = deliveryData
        binding.root.findViewById<ImageView>(R.id.recipient_image).loadImage(deliveryData?.imageUrl)

        delivery_info_card.apply {
            alpha = 0f
            visibility = View.VISIBLE
            animate()
                .alpha(1f)
                .setDuration(500)
                .setListener(null)
        }

    }

    override fun onMapReady(map: GoogleMap?) {
        showDeliveryLocationOnMap(map)
    }

    private fun showDeliveryLocationOnMap(map: GoogleMap?) {

        map?.let {
            var deliveryLocation = LatLng(deliveryData?.location?.lat!!, deliveryData?.location?.lng!!)

            val marker = map.addMarker(MarkerOptions().position(deliveryLocation))
            marker.tag = deliveryData
            marker.setAnchor(0.5f, 0.5f)
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(deliveryLocation, 14f))
        }


    }
}
