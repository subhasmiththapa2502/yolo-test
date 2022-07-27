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
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.yolo.test.Models.MovieResult;
import com.yolo.test.R;
import com.yolo.test.common.Constants;
import com.yolo.test.databinding.MovieItemBinding;
import com.yolo.test.presentation.detail.DetailFragment;

import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.PopularHolder>
{
    List<MovieResult> popularResult;
    LayoutInflater layoutInflater;
    FragmentManager mainFragmentManager;

    public PopularAdapter(List<MovieResult> popularResult, FragmentManager fragmentManager)
    {
        this.popularResult = popularResult;
        this.mainFragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public PopularHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        if(layoutInflater == null)
        {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        MovieItemBinding movieItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.movie_item,parent,false);

        return new PopularHolder(movieItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularHolder holder, @SuppressLint("RecyclerView") int position)
    {
        holder.bind(popularResult.get(position));

        holder.movieItemBinding.trendingPoster.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                DetailFragment bottomSheetFragment = new DetailFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.MOVIE_ID,popularResult.get(position).getId());
                bottomSheetFragment.setArguments(bundle);
                bottomSheetFragment.show(mainFragmentManager, popularResult.get(position).getId()+"");

            }
        });
    }

    @Override
    public int getItemCount()
    {
        return popularResult.size();
    }
    public void updatePopular(List<MovieResult> popularResult)
    {
        popularResult.addAll(popularResult);
        notifyDataSetChanged();
    }

    public static class  PopularHolder extends RecyclerView.ViewHolder
    {
        MovieItemBinding movieItemBinding;

        public PopularHolder(@NonNull MovieItemBinding movieItemBinding)
        {
            super(movieItemBinding.getRoot());
            this.movieItemBinding = movieItemBinding;

        }


        public void bind(MovieResult moviesResult)
        {
            loadPoster(moviesResult);
            loadRate(moviesResult);
            loadName(moviesResult);

            movieItemBinding.executePendingBindings();

        }


        private void loadPoster(MovieResult popularResult)
        {
            movieItemBinding.lottieLoader1.setVisibility(View.VISIBLE);
            Glide.with(movieItemBinding.getRoot().getContext())
                    .load(IMAGE_URL_500+ popularResult.getPosterPath())
                    .addListener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            movieItemBinding.lottieLoader1.setVisibility(View.VISIBLE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            movieItemBinding.lottieLoader1.setVisibility(View.VISIBLE);
                            return false;
                        }
                    })
                    .into(movieItemBinding.trendingPoster);



        }
        private void loadRate(MovieResult popularResult)
        {
          //  movieItemBinding.trendingRateButton.setText(String.format("%.1f" ,popularResult.getVoteAverage()));
        }
        private void loadName(MovieResult popularResult)
        {
            movieItemBinding.Moviename.setText(popularResult.getTitle());
        }




    }
}
