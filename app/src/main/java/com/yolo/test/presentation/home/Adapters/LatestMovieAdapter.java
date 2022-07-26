package com.yolo.test.presentation.home.Adapters;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.yolo.test.Models.MovieResult;
import com.yolo.test.R;
import com.yolo.test.databinding.MovieItemBinding;

import java.util.List;


public class LatestMovieAdapter extends RecyclerView.Adapter<LatestMovieAdapter.LatestHolder> {
    List<MovieResult> latestResults;
    LayoutInflater layoutInflater;

    public LatestMovieAdapter(List<MovieResult> latestResults) {
        this.latestResults = latestResults;
    }

    @NonNull
    @Override
    public LatestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        MovieItemBinding movieItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.movie_item, parent, false);
        return new LatestHolder(movieItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull LatestHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.bind(latestResults.get(position));
        holder.movieItemBinding.trendingPoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("model", latestResults.get(position));

                //Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_detailFragment,bundle);
            }
        });
    }


    @Override
    public int getItemCount() {
        return latestResults.size();
    }

    public void updateTrending(List<MovieResult> trendingMovie) {
        trendingMovie.addAll(trendingMovie);
        notifyDataSetChanged();
    }


    public class LatestHolder extends RecyclerView.ViewHolder {
        MovieItemBinding movieItemBinding;

        public LatestHolder(@NonNull MovieItemBinding movieItemBinding) {
            super(movieItemBinding.getRoot());
            this.movieItemBinding = movieItemBinding;

        }

        public void bind(MovieResult moviesResult) {
            loadPoster(moviesResult);
            loadRate(moviesResult);
            loadName(moviesResult);

            movieItemBinding.executePendingBindings();

        }


        private void loadPoster(MovieResult trendingResult) {
            Glide.with(movieItemBinding.getRoot().getContext())
                    .load("https://image.tmdb.org/t/p/w500" + trendingResult.getPosterPath())
                    //.thumbnail(Glide.with(movieItemBinding.getRoot().getContext()).load(R.drawable.loading))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(movieItemBinding.trendingPoster);


        }

        private void loadRate(MovieResult latestResult) {
            // movieItemBinding.trendingRateButton.setText(String.format("%.1f" ,latestResult.getVoteAverage()));
        }

        private void loadName(MovieResult latestResult) {
            movieItemBinding.Moviename.setText(latestResult.getTitle());
        }


    }


}