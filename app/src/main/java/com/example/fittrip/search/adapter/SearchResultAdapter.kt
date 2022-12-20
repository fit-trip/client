package com.example.fittrip.search.adapter

import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.fittrip.databinding.ItemSearchResultBinding
import com.example.fittrip.map.SearchResult
import com.example.fittrip.map.activity.SelectLocationActivity

class SearchResultViewHolder(val binding: ItemSearchResultBinding): RecyclerView.ViewHolder(binding.root)

class SearchResultAdapter(val scheduleName: String, private val datas: MutableList<SearchResult>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var binding: ItemSearchResultBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
            = SearchResultViewHolder(ItemSearchResultBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        binding = (holder as SearchResultViewHolder).binding
        binding.textLocationName.text = datas[position].title
//        binding.positionLat.text = datas[position].latitude.toString()
//        binding.positionLng.text = datas[position].longitude.toString()
        binding.positionCategory.text = datas[position].category
        binding.positionAddress.text = datas[position].roadAddress

        binding.positionCardView.setOnClickListener {
            Log.d("ryu", "datas[position].title: ${datas[position].title}")
            Log.d("ryu", "datas[position].latitude: ${datas[position].latitude}")
            Log.d("ryu", "datas[position].longitude: ${datas[position].longitude}")

            // SelectLocationActivity로 이동
            val intent = Intent(it.context, SelectLocationActivity::class.java)
            intent.putExtra("scheduleName", scheduleName)
            intent.putExtra("title", datas[position].title)
            intent.putExtra("lat", datas[position].latitude)
            intent.putExtra("lng", datas[position].longitude)
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = datas.size;
}