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

public class UpComingAdapter extends RecyclerView.Adapter<UpComingAdapter.UpComingViewHolder>
{
    List<MovieResult> upComingList;
    LayoutInflater layoutInflater;
    FragmentManager mainFragmentManager;

    public UpComingAdapter(List<MovieResult> upComingList, FragmentManager fragmentManager)
    {
        this.upComingList = upComingList;
        this.mainFragmentManager = fragmentManager;
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

        holder.movieItemBinding.trendingPoster.setOnClickListener(view -> {
            DetailFragment bottomSheetFragment = new DetailFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.MOVIE_ID,upComingList.get(position).getId());
            bottomSheetFragment.setArguments(bundle);
            bottomSheetFragment.show(mainFragmentManager, upComingList.get(position).getId()+"");            });
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
            movieItemBinding.lottieLoader1.setVisibility(View.VISIBLE);
            Glide.with(movieItemBinding.getRoot().getContext())
                    .load(IMAGE_URL_500+ upComingResult.getPosterPath())
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
