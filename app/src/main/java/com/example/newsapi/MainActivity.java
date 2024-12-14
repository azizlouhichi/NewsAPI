package com.example.newsapi;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String API_KEY = "24d2f741b2a94845847c6879239a97a8"; // Replace with your API key
    private static final String BASE_URL = "https://newsapi.org/v2/";

    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NewsApiService apiService = retrofit.create(NewsApiService.class);

        // Set up category buttons
        setUpCategoryButtons(apiService);

        fetchNews(apiService, "general");
    }

    private void setUpCategoryButtons(NewsApiService apiService) {
        // Set up category buttons and their click listeners
        Button buttonBusiness = findViewById(R.id.buttonBusiness);
        Button buttonTechnology = findViewById(R.id.buttonTechnology);
        Button buttonSports = findViewById(R.id.buttonSports);
        Button buttonHealth = findViewById(R.id.buttonHealth);
        Button buttonEntertainment = findViewById(R.id.buttonEntertainment);

        buttonBusiness.setOnClickListener(v -> fetchNews(apiService, "business"));
        buttonTechnology.setOnClickListener(v -> fetchNews(apiService, "technology"));
        buttonSports.setOnClickListener(v -> fetchNews(apiService, "sports"));
        buttonHealth.setOnClickListener(v -> fetchNews(apiService, "health"));
        buttonEntertainment.setOnClickListener(v -> fetchNews(apiService, "entertainment"));
    }

    private void fetchNews(NewsApiService apiService, String category) {
        Call<NewsResponse> call = apiService.getTopHeadlines(API_KEY, category);

        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Set the adapter with the news articles
                    newsAdapter = new NewsAdapter(response.body().getArticles(), MainActivity.this);
                    recyclerView.setAdapter(newsAdapter);
                } else {
                    Toast.makeText(MainActivity.this, "Failed to load news", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Log.e("MainActivity", "Error: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.news_menu, menu);
        return true;
    }

}
