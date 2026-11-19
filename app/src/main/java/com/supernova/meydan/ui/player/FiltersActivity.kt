package com.supernova.meydan.ui.player

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.slider.RangeSlider
import com.google.android.material.slider.Slider
import com.supernova.meydan.R
import com.supernova.meydan.demo.DemoData
import com.supernova.meydan.ui.rupees

class FiltersActivity : AppCompatActivity() {

    private lateinit var sliderPrice: RangeSlider
    private lateinit var sliderDistance: Slider
    private lateinit var txtPriceRange: TextView
    private lateinit var txtDistance: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filters)

        sliderPrice = findViewById(R.id.sliderPrice)
        sliderDistance = findViewById(R.id.sliderDistance)
        txtPriceRange = findViewById(R.id.txtPriceRange)
        txtDistance = findViewById(R.id.txtDistance)

        sliderPrice.addOnChangeListener { _, _, _ -> showPrice() }
        sliderDistance.addOnChangeListener { _, _, _ -> showDistance() }
        showPrice()
        showDistance()

        findViewById<MaterialButton>(R.id.btnShowResults).text = "Show ${DemoData.venues.size} grounds"
        findViewById<View>(R.id.btnShowResults).setOnClickListener { finish() }
        findViewById<View>(R.id.btnClose).setOnClickListener { finish() }
        findViewById<View>(R.id.btnReset).setOnClickListener {
            sliderPrice.setValues(1500f, 4500f)
            sliderDistance.value = 5f
        }
    }

    private fun showPrice() {
        val values = sliderPrice.values
        txtPriceRange.text = "${rupees(values[0].toInt())} – ${rupees(values[1].toInt()).removePrefix("Rs ")}"
    }

    private fun showDistance() {
        txtDistance.text = "Within ${sliderDistance.value.toInt()} km"
    }
}
