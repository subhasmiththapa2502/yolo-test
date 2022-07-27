package com.yolo.test.presentation.detail;

import static com.yolo.test.common.Constants.IMAGE_URL_600_900;
import static com.yolo.test.common.Constants.IMAGE_URL_780;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.yolo.test.Models.Credits.Cast;
import com.yolo.test.Models.Credits.Credits;
import com.yolo.test.Models.Credits.Crew;
import com.yolo.test.Models.MovieResult;
import com.yolo.test.Models.Trailer.Trailer;
import com.yolo.test.Models.Trailer.TrailerResult;
import com.yolo.test.R;
import com.yolo.test.ViewModel.AppViewModel;
import com.yolo.test.common.Constants;
import com.yolo.test.databinding.FragmentDetailBinding;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
@AndroidEntryPoint
public class DetailFragment extends BottomSheetDialogFragment implements View.OnClickListener{


    FragmentDetailBinding fragmentDetailBinding;
    AppViewModel appViewModel;
    int movieId;
    String youTubeId;

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment getInstance() {
        return new DetailFragment();
    }

    @Override
    public int getTheme() {
        return R.style.AppBottomSheetDialogTheme;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false);

        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);
        int movieId;
        if (getArguments() != null) {
            movieId = getArguments().getInt(Constants.MOVIE_ID);
            getMovieDetails(movieId);
        }

        fragmentDetailBinding.playTrailer.setOnClickListener(this);
        return fragmentDetailBinding.getRoot();
    }


    private void getMovieDetails(int id) {
        appViewModel.getMovieDetails(id).enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(@NonNull Call<MovieResult> call, @NonNull Response<MovieResult> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    movieId = response.body().getId();
                    setUpDetails(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResult> call, @NonNull Throwable t) {

            }
        });
    }

    private void getMovieTrailer(int id){
        appViewModel.makeMovieTrailerCall(id).enqueue(new Callback<Trailer>() {
            @Override
            public void onResponse(@NonNull Call<Trailer> call, @NonNull Response<Trailer> response) {
                boolean isTrailerPresent = false;
                if (response.isSuccessful()){
                    assert response.body() != null;
                    for (TrailerResult trailerResult:
                         response.body().getTrailerResults()) {
                        if (trailerResult.getType().equalsIgnoreCase(Constants.TRAILER)){
                            isTrailerPresent = true;
                            youTubeId = trailerResult.getKey();
                        }
                    }

                    if (isTrailerPresent){

                        openYouTubePage(youTubeId);
                    }

                }


            }

            @Override
            public void onFailure(@NonNull Call<Trailer> call, @NonNull Throwable t) {

            }
        });
    }

    private void openYouTubePage(String youTubeId){
        Intent intent = new Intent(requireActivity(), YouTubeActivity.class);
        intent.putExtra(Constants.YOUTUBE_ID, youTubeId);
        startActivity(intent);
    }
    private void setUpDetails(MovieResult moviesResult) {


        if(moviesResult.getPosterPath() !=null || moviesResult.getBackdropPath() !=null)
        {

            Glide.with(fragmentDetailBinding.getRoot().getContext())
                    .load(IMAGE_URL_600_900 +moviesResult.getPosterPath())
                    .into(fragmentDetailBinding.portrait);

            Glide.with(fragmentDetailBinding.getRoot().getContext())
                    .load(IMAGE_URL_780+  moviesResult.getBackdropPath())
                    .into(fragmentDetailBinding.landscape);
        }else
        {
            fragmentDetailBinding.landscape.setImageResource(R.drawable.joker);
        }

        if (moviesResult.getOriginalTitle() != null){
            fragmentDetailBinding.movieName.setText(moviesResult.getOriginalTitle());
        }

        if (moviesResult.getTagline() != null){
            fragmentDetailBinding.description.setText(moviesResult.getTagline());
        }

        if (moviesResult.getTagline() != null){
            fragmentDetailBinding.description.setText(moviesResult.getTagline());
        }



    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.playTrailer){
            getMovieTrailer(movieId);
            /*Intent intent = new Intent(requireActivity(), YouTubeActivity.class);
            intent.putExtra(Constants.MOVIE_ID, movieId);
            startActivity(intent);*/
        }
    }
}