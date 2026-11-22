package com.supernova.meydan.ui.owner

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.card.MaterialCardView
import com.supernova.meydan.R
import com.supernova.meydan.ui.colorOf
import com.supernova.meydan.ui.navigate
import com.supernova.meydan.ui.tintImage
import com.supernova.meydan.ui.toast

class AddGroundDetailsActivity : AppCompatActivity() {

    // card, court drawing, name for each sport tile
    private val sports by lazy {
        listOf(
            Triple(R.id.pickFutsal, R.id.imgPickFutsal, R.id.txtPickFutsal),
            Triple(R.id.pickCricket, R.id.imgPickCricket, R.id.txtPickCricket),
            Triple(R.id.pickPadel, R.id.imgPickPadel, R.id.txtPickPadel),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner_add_ground_details)

        sports.forEachIndexed { i, (cardId, _, _) ->
            findViewById<View>(cardId).setOnClickListener { selectSport(i) }
        }
        selectSport(0)

        findViewById<View>(R.id.pickMore).setOnClickListener { toast("Squash, table tennis, swimming and halls come next") }
        findViewById<View>(R.id.txtPickOnMap).setOnClickListener { toast("Map picker comes with location support") }
        findViewById<View>(R.id.btnBack).setOnClickListener { finish() }
        findViewById<View>(R.id.btnNextPhotos).setOnClickListener { navigate(AddGroundPhotosActivity::class.java) }
    }

    /** Selected tile turns obsidian with a mint court; the others go light. */
    private fun selectSport(selected: Int) {
        sports.forEachIndexed { i, (cardId, imgId, nameId) ->
            val on = i == selected
            findViewById<MaterialCardView>(cardId).setCardBackgroundColor(colorOf(if (on) R.color.obsidian else R.color.mist))
            findViewById<ImageView>(imgId).apply {
                tintImage(if (on) R.color.mint else R.color.obsidian)
                alpha = if (on) 1f else 0.45f
            }
            findViewById<TextView>(nameId).setTextColor(colorOf(if (on) R.color.white else R.color.obsidian))
        }
    }
}
