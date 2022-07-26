package com.yolo.test.ViewModel;

import android.app.Application;

import androidx.lifecycle.ViewModel;

import com.yolo.test.Models.Movie;
import com.yolo.test.data.repository.AppRepository;

import java.util.concurrent.Future;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Observable;

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




}
