package com.example.news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomePage : AppCompatActivity()
{
    var BASE_URL = "https://newsapi.org/"

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_page)

        var searchView = findViewById<SearchView>(R.id.searchView)

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener
        {
            override fun onQueryTextSubmit(query: String?): Boolean
            {
                if(query != null)
                    getNewsAbout(query)
                else
                    Toast.makeText(this@HomePage, "Enter a valid query", Toast.LENGTH_LONG).show()

                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean
            {
                return false
            }


        })

        getTopNews()
    }

    fun getTopNews()
    {
        val serviceBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(NewsAPIs::class.java)

        val requestCall = serviceBuilder.getTopNews()

        requestCall.enqueue(object: Callback<NewsAPIResponse>
        {
            override fun onResponse(call: Call<NewsAPIResponse>, response: Response<NewsAPIResponse>)
            {

                if(response.body() != null)
                {
                    val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)

                    val layoutManager = LinearLayoutManager(this@HomePage)
                    val adapter = NewsAdapter(baseContext, response.body()!!.articles)

                    recyclerView.layoutManager = layoutManager
                    recyclerView.adapter = adapter
                }
                else
                    Toast.makeText(this@HomePage, "Something went wrong try again : ", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<NewsAPIResponse>, t: Throwable)
            {
                Toast.makeText(this@HomePage, "Error : " + t.message, Toast.LENGTH_LONG).show()
            }

        })
    }

    fun getNewsAbout(query: String)
    {
        val serviceBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(NewsAPIs::class.java)

        val requestCall = serviceBuilder.getNewsAbout(query)

        requestCall.enqueue(object: Callback<NewsAPIResponse>{
            override fun onResponse(call: Call<NewsAPIResponse>, response: Response<NewsAPIResponse>)
            {
                if(response.body() != null)
                {
                    val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)

                    val layoutManager = LinearLayoutManager(this@HomePage)
                    val adapter = NewsAdapter(baseContext, response.body()!!.articles)

                    recyclerView.layoutManager = layoutManager
                    recyclerView.adapter = adapter
                }
                else
                    Toast.makeText(this@HomePage, "Something went wrong try again : ", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<NewsAPIResponse>, t: Throwable)
            {
                Toast.makeText(this@HomePage, "Error : " + t.message, Toast.LENGTH_LONG).show()
            }

        })
    }
}