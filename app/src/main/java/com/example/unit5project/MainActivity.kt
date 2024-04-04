package com.example.unit5project

import FactAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import com.bumptech.glide.Glide
import org.json.JSONArray


class MainActivity : AppCompatActivity() {
    private lateinit var adapter: FactAdapter
    private var PODList: MutableList<MutableList<String>> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        PODList = mutableListOf()
        getFacts()

    }
    private fun getAPOD() {
        val client = AsyncHttpClient()
        client["https://api.nasa.gov/planetary/apod?count=20&api_key=UbTUblQDpWjEkKrd0UbctsIaAIJDq2Rvhr19wbNh", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.d("APOD", "response successful$json")
                val resultsArray = json.jsonArray
                println("Results = " + resultsArray)
                for (i in 0 until resultsArray.length()) {
                    val factList: MutableList<String> = mutableListOf()
                    val pod = resultsArray.getJSONObject(i)
                    println("pod = " + pod)
                    // Access 'hdurl' attribute from the JSON object in the array
                    val hdUrl = pod.getString("url")
                    factList.add(hdUrl)

                    factList.add(pod.getString("date"))
                    factList.add(pod.getString("explanation"))
                    PODList.add(factList)
                }
                setupRecyclerView()
                Log.d("imageURL", "image URL set")
            }
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("API error", errorResponse)
            }
        }]
    }
    private fun getFacts() {
        getAPOD()
    }
    private fun setupRecyclerView() {
        val recyclerView: RecyclerView = findViewById(R.id.myrecyclerView)
        println("Recycler view found")
        println(PODList)
        adapter = FactAdapter(PODList)
        println("Adapter created")
        recyclerView.adapter = adapter
        println("Adapter added to recycler view")
        recyclerView.layoutManager = LinearLayoutManager(this)
        println("Layout set")
    }
}
