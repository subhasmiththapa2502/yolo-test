package com.yolo.test.data.repository;

import com.yolo.test.Models.MovieModel.Movie;
import com.yolo.test.data.remote.ApiClient;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.rxjava3.core.Observable;


public class AppRepository {

    ApiClient apiClient;


    public AppRepository(ApiClient apiClient) {
        this.apiClient = apiClient;
    }


    public Future<Observable<Movie>> popularFutureCall() {
        final ExecutorService executorService = Executors.newSingleThreadExecutor();

        final Callable<Observable<Movie>> popularCallable = new Callable<Observable<Movie>>() {
            @Override
            public Observable<Movie> call() throws Exception {
                return apiClient.getPopular();
            }
        };


        final Future<Observable<Movie>> popularFuture = new Future<Observable<Movie>>() {
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


        return popularFuture;

    }


    public Future<Observable<Movie>> upComingFutureCall() {
        final ExecutorService executorService = Executors.newSingleThreadExecutor();

        final Callable<Observable<Movie>> popularCallable = new Callable<Observable<Movie>>() {
            @Override
            public Observable<Movie> call() throws Exception {
                return apiClient.getUpComing();
            }
        };


        final Future<Observable<Movie>> upComingFuture = new Future<Observable<Movie>>() {
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


        return upComingFuture;

    }


}
