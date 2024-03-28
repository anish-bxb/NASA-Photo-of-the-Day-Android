package com.example.unit5project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import com.bumptech.glide.Glide


class MainActivity : AppCompatActivity() {
    var imageURL = ""
    var dateText = "0000-00-00"
    var descText = "Really interesting information"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.button)
        var image = findViewById<ImageView>(R.id.image)
        var date = findViewById<TextView>(R.id.textView3)
        var desc = findViewById<TextView>(R.id.textView4)
        date.setText(dateText)
        desc.setText(descText)
        getNext(button, image, date, desc)
    }
    private fun getAPOD() {
        val client = AsyncHttpClient()
        client["https://api.nasa.gov/planetary/apod?count=1&api_key=UbTUblQDpWjEkKrd0UbctsIaAIJDq2Rvhr19wbNh", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                Log.d("APOD", "response successful$json")
                var resultsJSON = json.jsonArray.getJSONObject(0)
                imageURL = resultsJSON.getString("hdurl")
                dateText = resultsJSON.getString("date")
                descText = resultsJSON.getString("explanation")
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
    private fun getNext(button : Button, image: ImageView, date:TextView, desc:TextView) {
        button.setOnClickListener{
            getAPOD()
            Glide.with(this)
                .load(imageURL)
                .fitCenter()
                .into(image)
            date.setText(dateText)
            desc.setText(descText)
        }
    }
}