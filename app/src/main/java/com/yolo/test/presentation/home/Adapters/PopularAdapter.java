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
import com.yolo.test.Models.MovieResult;
import com.yolo.test.R;
import com.yolo.test.databinding.MovieItemBinding;

import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.PopularHolder>
{
    List<MovieResult> popularResult;
    LayoutInflater layoutInflater;

    public PopularAdapter(List<MovieResult> popularResult)
    {
        this.popularResult = popularResult;
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
                Bundle bundle = new Bundle();
                bundle.putParcelable("model",popularResult.get(position));
                //Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_detailFragment,bundle);
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
            Glide.with(movieItemBinding.getRoot().getContext())
                    .load("https://image.tmdb.org/t/p/w500"+ popularResult.getPosterPath())
                    //.thumbnail(Glide.with(movieItemBinding.getRoot().getContext()).load(R.drawable.loading))
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
