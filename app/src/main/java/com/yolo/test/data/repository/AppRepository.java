package com.yolo.test.data.repository;

import com.yolo.test.models.Credits.Credits;
import com.yolo.test.models.Movie;
import com.yolo.test.models.MovieResult;
import com.yolo.test.models.Trailer.Trailer;
import com.yolo.test.data.remote.ApiClient;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;


public class AppRepository {

    ApiClient apiClient;

    public AppRepository(ApiClient apiClient) {
        this.apiClient = apiClient;
    }


    /***
     *
     * @return the list of latest movie in an interval of specified time.
     */
    public Future<Observable<Movie>> latestMovieFutureCall() {
        final ExecutorService executorService = Executors.newSingleThreadExecutor();


        final Callable<Observable<Movie>> latestMovieCallable = () -> apiClient.getLatestMovie();


        return new Future<Observable<Movie>>() {
            @Override
            public boolean cancel(boolean b) {
                if (b) {
                    return executorService.isShutdown();
                }
                return false;
            }

            @Override
            public boolean isCancelled() {
                return executorService.isShutdown();
            }

            @Override
            public boolean isDone() {
                return executorService.isTerminated();
            }

            @Override
            public Observable<Movie> get() throws ExecutionException, InterruptedException {
                return executorService.submit(latestMovieCallable).get();
            }

            @Override
            public Observable<Movie> get(long timeout, TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
                return executorService.submit(latestMovieCallable).get(timeout, timeUnit);
            }
        };
    }


    /***
     *
     * @return the popular movie list in an interval of specified time.
     */
    public Future<Observable<Movie>> popularFutureCall() {
        final ExecutorService executorService = Executors.newSingleThreadExecutor();

        final Callable<Observable<Movie>> popularCallable = () -> apiClient.getPopular();


        return new Future<Observable<Movie>>() {
            @Override
            public boolean cancel(boolean b) {
                if (b) {
                    return executorService.isShutdown();
                }
                return false;
            }

            @Override
            public boolean isCancelled() {
                return executorService.isShutdown();
            }

            @Override
            public boolean isDone() {
                return executorService.isTerminated();
            }

            @Override
            public Observable<Movie> get() throws ExecutionException, InterruptedException {
                return executorService.submit(popularCallable).get();
            }

            @Override
            public Observable<Movie> get(long timeOut, TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
                return executorService.submit(popularCallable).get(timeOut, timeUnit);
            }
        };

    }

    /***
     *
     * @return the top rated movie in an interval of specified time.
     */
    public Future<Observable<Movie>> topRatedFutureCall() {
        final ExecutorService executorService = Executors.newSingleThreadExecutor();

        final Callable<Observable<Movie>> topRatedCallable = () -> apiClient.getTopRated();


        return new Future<Observable<Movie>>() {
            @Override
            public boolean cancel(boolean b) {
                if (b) {
                    return executorService.isShutdown();
                }
                return false;
            }

            @Override
            public boolean isCancelled() {
                return executorService.isShutdown();
            }

            @Override
            public boolean isDone() {
                return executorService.isTerminated();
            }

            @Override
            public Observable<Movie> get() throws ExecutionException, InterruptedException {
                return executorService.submit(topRatedCallable).get();
            }

            @Override
            public Observable<Movie> get(long timeOut, TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
                return executorService.submit(topRatedCallable).get(timeOut, timeUnit);
            }
        };

    }

    /***
     *
     * @return return the upcoming movie list in an interval of specified time.
     */
    public Future<Observable<Movie>> upComingFutureCall() {
        final ExecutorService executorService = Executors.newSingleThreadExecutor();

        final Callable<Observable<Movie>> popularCallable = () -> apiClient.getUpComing();


        return new Future<Observable<Movie>>() {
            @Override
            public boolean cancel(boolean b) {
                if (b) {
                    return executorService.isShutdown();
                }
                return false;
            }

            @Override
            public boolean isCancelled() {
                return executorService.isShutdown();
            }

            @Override
            public boolean isDone() {
                return executorService.isTerminated();
            }

            @Override
            public Observable<Movie> get() throws ExecutionException, InterruptedException {
                return executorService.submit(popularCallable).get();
            }

            @Override
            public Observable<Movie> get(long timeOut, TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
                return executorService.submit(popularCallable).get(timeOut, timeUnit);
            }
        };

    }

    /***
     *
     * @param id movie Id to fetch the movie credits for
     * @return the movie credits of the requested movie.
     */
    public Call<Credits> getMovieCredits(int id) {
        return apiClient.getMovieCredits(id);
    }

    /***
     *
     * @return return popular movie list.
     */
    public Call<Movie> getPopularOnce() {
        return apiClient.getPopularOnce();
    }


    /***
     *
     * @return return top rated movie list.
     */
    public Call<Movie> getTopRatedOnce() {
        return apiClient.getTopRatedOnce();
    }

    /***
     *
     * @return return upcoming movie list.
     */
    public Call<Movie> getUpComingOnce() {
        return apiClient.getUpComingOnce();
    }

    /***
     *
     * @param id movie Id to fetch the movie details for
     * @return details of movie
     */
    public Call<MovieResult> getMovieDetails(int id) {
        return apiClient.getMovieDetails(id);
    }

    /***
     *
     * @return the list of latest movies.
     */
    public Call<Movie> getLatestMovieOnce() {
        return apiClient.getLatestMovieOnce();
    }

    /***
     *
     * @param id movie Id to fetch the movie trailer for
     * @return gets the list of trailers and youtube links
     */
    public Call<Trailer> getMovieTrailer(int id) {
        return apiClient.getMovieTrailer(id);
    }

}
