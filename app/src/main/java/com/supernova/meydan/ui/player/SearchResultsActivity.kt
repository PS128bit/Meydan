package com.supernova.meydan.ui.player

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.supernova.meydan.R
import com.supernova.meydan.demo.DemoData
import com.supernova.meydan.demo.VenueAdapter
import com.supernova.meydan.ui.EXTRA_SPORT
import com.supernova.meydan.ui.EXTRA_VENUE
import com.supernova.meydan.ui.navigate
import com.supernova.meydan.ui.toast

class SearchResultsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)

        // Coming from a sport tile on Home: show only that sport
        val sport = intent.getStringExtra(EXTRA_SPORT)
        val results = if (sport == null) DemoData.venues else DemoData.venues.filter { it.sport == sport }
        if (sport != null) findViewById<EditText>(R.id.etSearch).setText(sport)

        val count = results.size
        findViewById<TextView>(R.id.txtResultCount).text =
            if (count == 1) "1 ground near F-11" else "$count grounds near F-11"

        findViewById<RecyclerView>(R.id.rvResults).adapter = VenueAdapter(results) { index ->
            navigate(VenueDetailsActivity::class.java) { putExtra(EXTRA_VENUE, index) }
        }
        findViewById<View>(R.id.rvResults).visibility = if (count == 0) View.GONE else View.VISIBLE
        findViewById<View>(R.id.layoutEmpty).visibility = if (count == 0) View.VISIBLE else View.GONE

        findViewById<View>(R.id.btnBack).setOnClickListener { finish() }
        findViewById<View>(R.id.chipOpenFilters).setOnClickListener { navigate(FiltersActivity::class.java) }
        findViewById<View>(R.id.btnSort).setOnClickListener { toast("Sorted by price, low to high") }
    }
}
