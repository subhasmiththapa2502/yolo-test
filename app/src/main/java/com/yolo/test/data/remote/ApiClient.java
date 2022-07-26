package com.yolo.test.data.remote;

import static com.yolo.test.common.Constants.API_KEY;

import com.yolo.test.Models.Movie;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;


public interface ApiClient {

    //->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->

    //Get Latest Movie
    @GET("movie/now_playing?api_key=" + API_KEY)
    Observable<Movie> getLatestMovie();

    //Get Latest Movie
    @GET("movie/now_playing?api_key=" + API_KEY)
    Call<Movie> getLatestMovieOnce();


    //Get Popular Movie
    @GET("movie/popular?api_key=" + API_KEY)
    Observable<Movie> getPopular();


    //Get Popular Movie
    @GET("movie/popular?api_key=" + API_KEY)
    Call<Movie> getPopularOnce();

    //Get Popular Movie
    @GET("movie/top_rated?api_key=" + API_KEY)
    Observable<Movie> getTopRated();
    //Get Popular Movie
    @GET("movie/top_rated?api_key=" + API_KEY)
    Call<Movie> getTopRatedOnce();

    //Get Upcoming Movie
    @GET("movie/upcoming?api_key=" + API_KEY)
    Observable<Movie> getUpComing();

    //Get Upcoming Movie
    @GET("movie/upcoming?api_key=" + API_KEY)
    Call<Movie> getUpComingOnce();

    //->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->


}
