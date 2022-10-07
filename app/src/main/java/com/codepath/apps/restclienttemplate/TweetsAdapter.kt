package com.codepath.apps.restclienttemplate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepath.apps.restclienttemplate.models.Tweet
import java.util.*
import kotlin.collections.ArrayList

class TweetsAdapter(val tweets: ArrayList<Tweet>) : RecyclerView.Adapter<TweetsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetsAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        val view = inflater.inflate(R.layout.item_tweet, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: TweetsAdapter.ViewHolder, position: Int) {
        val tweet: Tweet = tweets.get(position)

        holder.tvUserName.text = tweet.user?.name
        holder.tvTweetBody.text = tweet.body
        holder.tvTime.text = generateTime(tweet.createdAt)

        Glide.with(holder.itemView).load(tweet.user?.publicImageUrl).into(holder.ivProfileImage)
    }

    override fun getItemCount(): Int {
        return tweets.size
    }

    fun clear() {
        tweets.clear()
        notifyDataSetChanged()
    }

    // Add a list of items -- change to type used
    fun addAll(tweetList: List<Tweet>) {
        tweets.addAll(tweetList)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivProfileImage = itemView.findViewById<ImageView>(R.id.ivProfileImage)
        val tvUserName = itemView.findViewById<TextView>(R.id.tvUsername)
        val tvTweetBody = itemView.findViewById<TextView>(R.id.tvTweetBody)
        val tvTime = itemView.findViewById<TextView>(R.id.tvTime)
    }

    companion object {
        fun generateTime(tstring: String):String {
            val createTime = Date(tstring)
            val currentTime = Date()
            val diff = currentTime.time - createTime.time
            currentTime.toString()

            val nd = (1000 * 24 * 60 * 60).toLong()
            val nh = (1000 * 60 * 60).toLong()
            val nm = (1000 * 60).toLong()
            val ns = 1000.toLong()

            val day = diff / nd
            val hour = diff % nd / nh
            val min = diff % nd % nh / nm
            val sec = diff % nd % nh % nm / ns

            var ret = ""
            if (day>0) ret+="$day"+"d"
            if (hour>0) ret+="$hour"+"h"
            if (min>0) ret+="$min"+"m"
            return ret
        }
    }
}