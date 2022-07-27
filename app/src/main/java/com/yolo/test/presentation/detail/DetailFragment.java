package com.yolo.test.presentation.detail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.yolo.test.databinding.FragmentHomeBinding;

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
    List<Cast> castList;
    List<Crew> crewList;
    List<MovieResult> recommendedMovie;
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

    private void setUpMovieCreditsDetail(int id)
    {

        appViewModel.makeCreditsCall(id).enqueue(new Callback<Credits>()
        {
            @Override
            public void onResponse(Call<Credits> call, Response<Credits> response)
            {
                castList = response.body().getCast();
                crewList = response.body().getCrew();


/*                if(fragmentDetailBinding.castRecyclerView.getAdapter() != null)
                {
                    creditsAdopter = (CreditsAdopter) fragmentDetailBinding.castRecyclerView.getAdapter();
                }else
                {
                    creditsAdopter = new CreditsAdopter(castList);
                    fragmentDetailBinding.castRecyclerView.setAdapter(creditsAdopter);
                }
                for (int i = 0; i < crewList.size(); i++)
                {
                    if (crewList.get(i).getJob().equals("Director"))
                    {
                        fragmentDetailBinding.directerName.setText(crewList.get(i).getName());
                    }
                }*/
            }

            @Override
            public void onFailure(Call<Credits> call, Throwable t)
            {

            }
        });




    }

    private void getMovieDetails(int id) {
        appViewModel.getMovieDetails(id).enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(@NonNull Call<MovieResult> call, @NonNull Response<MovieResult> response) {
                if (response.isSuccessful()) {
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
            public void onResponse(Call<Trailer> call, Response<Trailer> response) {
                boolean isTrailerPresent = false;
                if (response.isSuccessful()){
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
            public void onFailure(Call<Trailer> call, Throwable t) {

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
                    .load("https://image.tmdb.org/t/p/w600_and_h900_bestv2" +moviesResult.getPosterPath())
                    .into(fragmentDetailBinding.portrait);

            Log.d("######", "POSTER "+ "https://image.tmdb.org/t/p/w600_and_h900_bestv2"+moviesResult.getPosterPath());
            Log.d("######", "BACK DROP "+ "https://image.tmdb.org/t/p/w780"+moviesResult.getBackdropPath());
            Glide.with(fragmentDetailBinding.getRoot().getContext())
                    .load("https://image.tmdb.org/t/p/w780" +  moviesResult.getBackdropPath())
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