package com.yolo.test.ViewModel;

import android.app.Application;

import androidx.lifecycle.ViewModel;

import com.yolo.test.Models.Credits.Credits;
import com.yolo.test.Models.Movie;
import com.yolo.test.Models.MovieResult;
import com.yolo.test.Models.Trailer.Trailer;
import com.yolo.test.data.repository.AppRepository;

import java.util.concurrent.Future;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;

@HiltViewModel
public class AppViewModel extends ViewModel
{

    @Inject
    AppRepository appRepository;


    @Inject
    public AppViewModel(Application application)
    {
        super();
    }


    public Future<Observable<Movie>> makeLatestMovieFutureCall()
    {
        return appRepository.latestMovieFutureCall();
    }

    public Future<Observable<Movie>> makePopularFutureCall()
    {
       return appRepository.popularFutureCall();
    }
    public Future<Observable<Movie>> makeTopRatedFutureCall()
    {
       return appRepository.topRatedFutureCall();
    }

    public Future<Observable<Movie>> makeUpComingFutureCall()
    {
        return appRepository.upComingFutureCall();
    }

    public Call<Movie> makePopularCall()
    {
        return appRepository.getPopularOnce();
    }

    public Call<Movie> makeTopRatedCall()
    {
        return appRepository.getTopRatedOnce();
    }

    public Call<Movie> makeUpComingCall()
    {
        return appRepository.getUpComingOnce();
    }
    public Call<MovieResult> getMovieDetails(int id)
    {
        return appRepository.getMovieDetails(id);
    }

    public Call<Credits> makeCreditsCall(int id)
    {
        return appRepository.getMovieCredits(id);
    }

    public Call<Movie> makeLatestMovieCall()
    {
        return appRepository.getLatestMovieOnce();
    }
    public Call<Trailer> makeMovieTrailerCall(int id)
    {
        return appRepository.getMovieTrailer(id);
    }




}
