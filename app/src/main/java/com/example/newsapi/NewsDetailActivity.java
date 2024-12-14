package com.example.newsapi;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import androidx.appcompat.app.AppCompatActivity;

public class NewsDetailActivity extends AppCompatActivity {

    private TextView titleTextView, descriptionTextView, authorTextView, publishedAtTextView, contentTextView, sourceNameTextView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        // Initialize views
        titleTextView = findViewById(R.id.title);
        descriptionTextView = findViewById(R.id.description);
        authorTextView = findViewById(R.id.author);
        publishedAtTextView = findViewById(R.id.publishedAt);
        contentTextView = findViewById(R.id.content);
        sourceNameTextView = findViewById(R.id.sourceName);
        imageView = findViewById(R.id.image);

        // Get data passed from MainActivity
        String title = getIntent().getStringExtra("title");
        String description = getIntent().getStringExtra("description");
        String author = getIntent().getStringExtra("author");
        String imageUrl = getIntent().getStringExtra("imageUrl");
        String publishedAt = getIntent().getStringExtra("publishedAt");
        String content = getIntent().getStringExtra("content");
        String sourceName = getIntent().getStringExtra("sourceName");


        // Set the data into views
        titleTextView.setText(title);
        descriptionTextView.setText(description);
        authorTextView.setText("Author: " + "\n" + author);
        publishedAtTextView.setText("Published at: " + "\n" + publishedAt.substring(0,10));
        contentTextView.setText(content != null ? content : "Content not available.");
        sourceNameTextView.setText(sourceName != null ? "Source: " + sourceName: "");
        Picasso.get().load(imageUrl).into(imageView);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.details_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();
        if (id == R.id.action_share) {
            String noteContent = "Title : " + getIntent().getStringExtra("title") + "\n\n" +
                    "Auther : " + getIntent().getStringExtra("author") + "\n\n" +
                    "Published at : " + getIntent().getStringExtra("publishedAt").substring(0,10)+ "\n\n" +
                    "Description : " + getIntent().getStringExtra("description");
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, noteContent.toString());

            Intent chooser = Intent.createChooser(shareIntent, "Share your note via");
            if (shareIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(chooser);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
