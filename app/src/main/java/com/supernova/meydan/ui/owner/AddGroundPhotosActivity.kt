package com.supernova.meydan.ui.owner

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.supernova.meydan.R
import com.supernova.meydan.ui.navigate
import com.supernova.meydan.ui.toast

class AddGroundPhotosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner_add_ground_photos)

        findViewById<TextView>(R.id.txtUploadPercent).text = "60%"
        findViewById<TextView>(R.id.txtPriceStandard).text = "4,000"
        findViewById<TextView>(R.id.txtPricePeak).text = "5,000"

        // Photo removal and picking come with image upload in the next phase
        findViewById<View>(R.id.btnRemovePhoto2).setOnClickListener { toast("Photo removed") }
        findViewById<View>(R.id.btnRemovePhoto3).setOnClickListener { toast("Photo removed") }
        findViewById<View>(R.id.btnAddPhoto).setOnClickListener { toast("Opens your gallery in the next phase") }
        findViewById<View>(R.id.cardPriceStandard).setOnClickListener { toast("Edit standard price") }
        findViewById<View>(R.id.cardPricePeak).setOnClickListener { toast("Edit peak price") }

        findViewById<View>(R.id.btnBack).setOnClickListener { finish() }
        findViewById<View>(R.id.btnNextAvailability).setOnClickListener {
            navigate(AddGroundAvailabilityActivity::class.java)
        }
    }
}
