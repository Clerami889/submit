package com.clerami.bottoooom

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.clerami.bottoooom.data.local.entity.FavoriteEvent
import com.clerami.bottoooom.databinding.ActivityEventDetailBinding
import com.clerami.bottoooom.di.Injection
import com.clerami.bottoooom.ui.favorite.FavoriteEventViewModel
import com.clerami.bottoooom.ui.favorite.FavoriteEventViewModelFactory

class EventDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEventDetailBinding

    private val favoriteEventViewModel: FavoriteEventViewModel by viewModels {
        FavoriteEventViewModelFactory(Injection.provideFavoriteRepository(this))
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val eventLogo = intent.getStringExtra("eventLogo")
        val eventName = intent.getStringExtra("eventName")
        val eventOwner = intent.getStringExtra("eventOwner")
        val eventTime = intent.getStringExtra("eventTime")
        val eventDescription = intent.getStringExtra("eventDescription")
        val availableQuota = intent.getIntExtra("availableQuota", 0)
        val link = intent.getStringExtra("Web")
        val eventId = intent.getIntExtra("eventId", -1)

        if (eventId == -1) {
            Log.e("EventDetailActivity", "Invalid event ID")
            finish()
            return
        }

        Glide.with(this).load(eventLogo).into(binding.eventLogoDetail)
        binding.eventNameDetail.text = eventName
        binding.eventOwnerDetail.text = eventOwner
        binding.eventTimeDetail.text = eventTime
        binding.eventDescriptionDetail.text = HtmlCompat.fromHtml(eventDescription.orEmpty(), HtmlCompat.FROM_HTML_MODE_LEGACY)
        binding.availableQuotaDetail.text = "Available Quota: $availableQuota"
        binding.LinkButton.setOnClickListener {
            if (!link.isNullOrEmpty()) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                startActivity(intent)
            }
        }


        binding.Favorite.setOnClickListener {
            val favoriteEvent = FavoriteEvent(eventId, eventName ?: "", eventLogo)
            favoriteEventViewModel.addFavorite(favoriteEvent)
        }


    }
}
