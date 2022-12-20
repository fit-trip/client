package com.example.fittrip.search.adapter

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.fittrip.databinding.ItemMyScheduleBinding
import com.example.fittrip.databinding.ItemSearchResultBinding
import com.example.fittrip.map.SearchResult

class SearchResultViewHolder(val binding: ItemSearchResultBinding)
    : RecyclerView.ViewHolder(binding.root)

class SearchResultAdapter(val datas: MutableList<SearchResult>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var binding: ItemSearchResultBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
            = SearchResultViewHolder(ItemSearchResultBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        binding = (holder as SearchResultViewHolder).binding
        binding.textLocationName.text = datas[position].title

        binding.itemScheduleNameRoot.setOnClickListener {
            Log.d("ryu", "datas[position].title: ${datas[position].title}")
        }
    }

    override fun getItemCount(): Int = datas.size;
}