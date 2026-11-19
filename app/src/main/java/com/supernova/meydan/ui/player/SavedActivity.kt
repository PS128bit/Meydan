package com.supernova.meydan.ui.player

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.supernova.meydan.R
import com.supernova.meydan.demo.DemoData
import com.supernova.meydan.demo.SavedAdapter
import com.supernova.meydan.ui.EXTRA_VENUE
import com.supernova.meydan.ui.navigate
import com.supernova.meydan.ui.setupPlayerNav

class SavedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved)
        setupPlayerNav(R.id.nav_saved)

        val txtCount = findViewById<TextView>(R.id.txtSavedCount)
        val list = findViewById<RecyclerView>(R.id.rvSaved)
        val empty = findViewById<View>(R.id.layoutSavedEmpty)

        fun showCount(count: Int) {
            txtCount.text = if (count == 1) "1 ground" else "$count grounds"
            empty.visibility = if (count == 0) View.VISIBLE else View.GONE
        }

        val saved = DemoData.venues.toMutableList()
        list.adapter = SavedAdapter(
            saved,
            onClick = { index -> navigate(VenueDetailsActivity::class.java) { putExtra(EXTRA_VENUE, index) } },
            onChanged = { count -> showCount(count) },
        )
        showCount(saved.size)
    }
}
