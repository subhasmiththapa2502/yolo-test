package com.yolo.test.presentation.home.adapters;

import static com.yolo.test.common.Constants.IMAGE_URL_500;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.yolo.test.models.MovieResult;
import com.yolo.test.R;
import com.yolo.test.common.Constants;
import com.yolo.test.databinding.MovieItemBinding;
import com.yolo.test.presentation.detail.DetailFragment;

import java.util.List;


public class TopRatedMovieAdapter extends RecyclerView.Adapter<TopRatedMovieAdapter.TopRatedHolder> {
    List<MovieResult> latestResults;
    LayoutInflater layoutInflater;
    FragmentManager mainFragmentManager;

    public TopRatedMovieAdapter(List<MovieResult> latestResults, FragmentManager fragmentManager) {
        this.latestResults = latestResults;
        this.mainFragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public TopRatedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        MovieItemBinding movieItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.movie_item, parent, false);
        return new TopRatedHolder(movieItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TopRatedHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.bind(latestResults.get(position));
        holder.movieItemBinding.trendingPoster.setOnClickListener(view -> {
            DetailFragment bottomSheetFragment = new DetailFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.MOVIE_ID, latestResults.get(position).getId());
            bottomSheetFragment.setArguments(bundle);
            bottomSheetFragment.show(mainFragmentManager, latestResults.get(position).getId() + "");
        });
    }


    @Override
    public int getItemCount() {
        return latestResults.size();
    }

    public void updateTopRated(List<MovieResult> trendingMovie) {
        trendingMovie.addAll(trendingMovie);
        notifyDataSetChanged();
    }


    public class TopRatedHolder extends RecyclerView.ViewHolder {
        MovieItemBinding movieItemBinding;

        public TopRatedHolder(@NonNull MovieItemBinding movieItemBinding) {
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
            movieItemBinding.lottieLoader1.setVisibility(View.VISIBLE);
            Glide.with(movieItemBinding.getRoot().getContext())
                    .load(IMAGE_URL_500 + trendingResult.getPosterPath())
                    .addListener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            movieItemBinding.lottieLoader1.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            movieItemBinding.lottieLoader1.setVisibility(View.GONE);
                            return false;
                        }
                    })
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