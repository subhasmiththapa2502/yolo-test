package com.yolo.test.data.remote;

import static com.yolo.test.common.Constants.BASE_URL;

import com.yolo.test.Models.MovieModel.Movie;


import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;


public interface ApiClient
{

    //->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->
    //Movies Api

    //Get Popular Movie
    @GET("movie/popular?api_key="+ BASE_URL)
    Observable<Movie> getPopular();

    //Get Upcoming Movie
    @GET("movie/upcoming?api_key="+BASE_URL)
    Observable<Movie> getUpComing();

}
