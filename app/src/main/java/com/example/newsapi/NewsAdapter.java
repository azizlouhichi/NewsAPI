package com.example.newsapi;

import android.view.LayoutInflater;
import android.view.View;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import android.content.Intent;

import retrofit2.Callback;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private List<Article> articles;
    private Context context;

    public NewsAdapter(List<Article> articles, Context context) {
        this.articles = filterArticles(articles);
        this.context = context;
    }

    private List<Article> filterArticles(List<Article> articles) {
        List<Article> filteredArticles = new ArrayList<>();
        for (Article article : articles) {
            if (article.getDescription() != null && !article.getDescription().trim().isEmpty()) {
                filteredArticles.add(article);
            }
        }
        return filteredArticles;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.title.setText(article.getTitle());
        holder.description.setText(article.getDescription());
        Picasso.get().load(article.getUrlToImage()).into(holder.imageView);

        // Set item click listener
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, NewsDetailActivity.class);
            // Pass additional data to the new activity
            intent.putExtra("title", article.getTitle());
            intent.putExtra("description", article.getDescription());
            intent.putExtra("author", article.getAuthor());
            intent.putExtra("url", article.getUrl());
            intent.putExtra("imageUrl", article.getUrlToImage());
            intent.putExtra("publishedAt", article.getPublishedAt());
            intent.putExtra("content", article.getContent());
            intent.putExtra("sourceName", article.getSourceName());
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return articles.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {

        LinearLayout layoutItem;
        TextView title, description;
        ImageView imageView;

        public NewsViewHolder(View itemView) {
            super(itemView);
            layoutItem = itemView.findViewById(R.id.news_item);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            imageView = itemView.findViewById(R.id.image);
        }
    }
}
