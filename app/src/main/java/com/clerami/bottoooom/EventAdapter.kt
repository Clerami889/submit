package com.clerami.bottoooom

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.clerami.bottoooom.data.remote.response.ListEventsItem

class EventAdapter(private var events: List<ListEventsItem>, private val context: Context) :
    RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvEvent: TextView = itemView.findViewById(R.id.TvEvent)
        val ivEventLogo: ImageView = itemView.findViewById(R.id.ivEventLogo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.tvEvent.text = event.name
        Glide.with(holder.itemView.context).load(event.imageLogo).into(holder.ivEventLogo)
        Log.d("EventAdapter", "Binding event: ${event.name}")
        holder.itemView.setOnClickListener {
            val availableQuota = (event.quota ?: 0) - (event.registrants ?: 0)
            val intent = Intent(context, EventDetailActivity::class.java).apply {
                putExtra("eventLogo", event.imageLogo)
                putExtra("eventName", event.name)
                putExtra("eventOwner", event.ownerName)
                putExtra("eventTime", event.beginTime)
                putExtra("eventDescription", event.description)
                putExtra("availableQuota", availableQuota)
                putExtra("Web", event.link)
                putExtra("eventId", event.id) // Add eventId here
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = events.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newEvents: List<ListEventsItem>) {
        events = newEvents
        notifyDataSetChanged()
    }
}
