package com.yolo.test.data;

import com.yolo.test.Models.MovieModel.Movie;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;


public interface ApiClient
{
    String apiKey = "78db45e6e8498a79b6d6876fddba1e27";
    //->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->
    //Movies Api

    //Get Popular Movie
    @GET("movie/popular?api_key="+apiKey)
    Observable<Movie> getPopular();

    //Get Upcoming Movie
    @GET("movie/upcoming?api_key="+apiKey)
    Observable<Movie> getUpComing();

}
