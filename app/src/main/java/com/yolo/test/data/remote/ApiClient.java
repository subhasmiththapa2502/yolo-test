package com.yolo.test.data.remote;

import static com.yolo.test.common.Constants.API_KEY;

import com.yolo.test.models.Credits.Credits;
import com.yolo.test.models.Movie;
import com.yolo.test.models.MovieResult;
import com.yolo.test.models.Trailer.Trailer;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface ApiClient {

    //->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->

    //Get Latest Movie Repeatedly.
    @GET("movie/now_playing?api_key=" + API_KEY)
    Observable<Movie> getLatestMovie();

    //Get Latest Movie
    @GET("movie/now_playing?api_key=" + API_KEY)
    Call<Movie> getLatestMovieOnce();


    //Get Popular Movie
    @GET("movie/popular?api_key=" + API_KEY)
    Observable<Movie> getPopular();


    //Get Popular Movie Once
    @GET("movie/popular?api_key=" + API_KEY)
    Call<Movie> getPopularOnce();

    //Get Popular Movie
    @GET("movie/top_rated?api_key=" + API_KEY)
    Observable<Movie> getTopRated();

    //Get Popular Movie Once
    @GET("movie/top_rated?api_key=" + API_KEY)
    Call<Movie> getTopRatedOnce();

    //Get Upcoming Movie
    @GET("movie/upcoming?api_key=" + API_KEY)
    Observable<Movie> getUpComing();

    //Get Upcoming Movie Once
    @GET("movie/upcoming?api_key=" + API_KEY)
    Call<Movie> getUpComingOnce();


    //Get Movie Details
    @GET("movie/{movie_id}?api_key=" + API_KEY)
    Call<MovieResult> getMovieDetails(@Path("movie_id") int id);


    //Get Movie Credits
    @GET("movie/{id}/credits?api_key=" + API_KEY)
    Call<Credits> getMovieCredits(@Path("id") int id);

    //Get Movie Trailer
    @GET("movie/{id}/videos?api_key=" + API_KEY)
    Call<Trailer> getMovieTrailer(@Path("id") int id);

    //->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->


}
