package com.example.fittrip

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SearchView
import com.example.fittrip.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    fun searchPlace(query: String) {
        Log.d("SearchActivity", "query: $query")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySearchBinding.inflate(layoutInflater)

        val search_view = binding.searchView
        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("검색어 입력", query.toString())
                searchPlace(query.toString())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
//                Log.d("검색어 변경", newText.toString())
                return false
            }
        })


        setContentView(binding.root)
    }
}