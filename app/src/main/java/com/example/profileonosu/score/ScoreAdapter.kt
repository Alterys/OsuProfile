package com.example.profileonosu.score

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.profileonosu.api.userinfo.Score
import com.example.profileonosu.databinding.ItemScoreBinding

class ScoreAdapter: RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder>() {

    var scores: List<Score> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemScoreBinding.inflate(inflater, parent, false)
        return ScoreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        val score = scores[position]
        with(holder.binding){
            performancePoint.text = performancePoint.toString()
        }
    }

    override fun getItemCount(): Int = scores.size

    class ScoreViewHolder(
        val binding: ItemScoreBinding
    ): RecyclerView.ViewHolder(binding.root)
}