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

public class UpComingAdapter extends RecyclerView.Adapter<UpComingAdapter.UpComingViewHolder>
{
    List<MovieResult> upComingList;
    LayoutInflater layoutInflater;

    public UpComingAdapter(List<MovieResult> upComingList)
    {
        this.upComingList = upComingList;
    }

    @NonNull
    @Override
    public UpComingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        if(layoutInflater == null)
        {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        MovieItemBinding movieItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.movie_item,parent,false);

        return new UpComingViewHolder(movieItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull UpComingViewHolder holder, @SuppressLint("RecyclerView") int position)
    {
        holder.bind(upComingList.get(position));

        holder.movieItemBinding.trendingPoster.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Bundle bundle = new Bundle();
                bundle.putParcelable("model",upComingList.get(position));
                //Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_detailFragment,bundle);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return upComingList.size();
    }
    public void updateUpcoming(List<MovieResult> upComingList)
    {
        upComingList.addAll(upComingList);
        notifyDataSetChanged();
    }

    public class UpComingViewHolder extends RecyclerView.ViewHolder
    {
        MovieItemBinding movieItemBinding;
        public UpComingViewHolder(@NonNull MovieItemBinding movieItemBinding)
        {
            super(movieItemBinding.getRoot());
            this.movieItemBinding = movieItemBinding;
        }


        public void bind(MovieResult upComingResult)
        {
            loadPoster(upComingResult);
            loadRate(upComingResult);
            loadName(upComingResult);

            movieItemBinding.executePendingBindings();

        }


        private void loadPoster(MovieResult upComingResult)
        {
            Glide.with(movieItemBinding.getRoot().getContext())
                    .load("https://image.tmdb.org/t/p/w500"+ upComingResult.getPosterPath())
                    //.thumbnail(Glide.with(movieItemBinding.getRoot().getContext()).load(R.drawable.loading))
                    .into(movieItemBinding.trendingPoster);



        }
        private void loadRate(MovieResult upComingResult)
        {
          //  movieItemBinding.trendingRateButton.setText(String.format("%.1f" ,upComingResult.getVoteAverage()));
        }

        private void loadName(MovieResult upComingResult)
        {
            movieItemBinding.Moviename.setText(upComingResult.getTitle());
        }



    }

}
