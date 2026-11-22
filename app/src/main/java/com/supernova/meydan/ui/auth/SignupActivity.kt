package com.supernova.meydan.ui.auth

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.card.MaterialCardView
import com.supernova.meydan.R
import com.supernova.meydan.ui.colorOf
import com.supernova.meydan.ui.owner.OwnerHomeActivity
import com.supernova.meydan.ui.player.HomeActivity
import com.supernova.meydan.ui.startFresh
import com.supernova.meydan.ui.tintBackground

class SignupActivity : AppCompatActivity() {

    private lateinit var cardPlayer: MaterialCardView
    private lateinit var cardOwner: MaterialCardView
    private var isOwner = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        cardPlayer = findViewById(R.id.cardRolePlayer)
        cardOwner = findViewById(R.id.cardRoleOwner)
        cardPlayer.setOnClickListener { selectRole(player = true) }
        cardOwner.setOnClickListener { selectRole(player = false) }
        selectRole(player = true)

        findViewById<View>(R.id.btnBack).setOnClickListener { finish() }
        findViewById<View>(R.id.txtGoToLogin).setOnClickListener { finish() }
        // Players land on Home, ground owners on Owner home
        findViewById<View>(R.id.btnCreateAccount).setOnClickListener {
            startFresh(if (isOwner) OwnerHomeActivity::class.java else HomeActivity::class.java)
        }
    }

    /** Selected card turns dark, the other turns light. */
    private fun selectRole(player: Boolean) {
        isOwner = !player
        styleCard(cardPlayer, R.id.imgRolePlayer, R.id.txtRolePlayerTitle, R.id.txtRolePlayerSub, player)
        styleCard(cardOwner, R.id.imgRoleOwner, R.id.txtRoleOwnerTitle, R.id.txtRoleOwnerSub, !player)
    }

    private fun styleCard(card: MaterialCardView, iconId: Int, titleId: Int, subId: Int, selected: Boolean) {
        card.isChecked = selected
        card.setCardBackgroundColor(colorOf(if (selected) R.color.obsidian else R.color.mist))
        findViewById<ImageView>(iconId).tintBackground(if (selected) R.color.mint else R.color.white)
        findViewById<TextView>(titleId).setTextColor(colorOf(if (selected) R.color.white else R.color.obsidian))
        findViewById<TextView>(subId).setTextColor(colorOf(if (selected) R.color.slate_light else R.color.slate))
    }
}
