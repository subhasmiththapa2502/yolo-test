package com.yolo.test.presentation.home;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.thecode.aestheticdialogs.AestheticDialog;
import com.thecode.aestheticdialogs.DialogAnimation;
import com.thecode.aestheticdialogs.DialogStyle;
import com.thecode.aestheticdialogs.DialogType;
import com.yolo.test.MainActivity;
import com.yolo.test.Models.Movie;
import com.yolo.test.R;
import com.yolo.test.ViewModel.AppViewModel;
import com.yolo.test.databinding.FragmentHomeBinding;
import com.yolo.test.presentation.home.Adapters.LatestMovieAdapter;
import com.yolo.test.presentation.home.Adapters.PopularAdapter;
import com.yolo.test.presentation.home.Adapters.TopRatedMovieAdapter;
import com.yolo.test.presentation.home.Adapters.UpComingAdapter;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@AndroidEntryPoint
public class HomeFragment extends Fragment implements View.OnClickListener {
    FragmentHomeBinding fragmentHomeBinding;
    MainActivity mainActivity;
    AppViewModel appViewModel;
    CompositeDisposable compositeDisposable;

    LatestMovieAdapter latestMovieAdapter;
    TopRatedMovieAdapter topRatedMovieAdapter;
    PopularAdapter popularAdapter;
    UpComingAdapter upComingAdopter;

    @Inject
    ConnectivityManager connectivityManager;
    @Inject
    NetworkRequest networkRequest;

    AestheticDialog aestheticDialog;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);

        compositeDisposable = new CompositeDisposable();


        fragmentHomeBinding.latestMovieText.setOnClickListener(this);
        fragmentHomeBinding.popularText.setOnClickListener(this);
        fragmentHomeBinding.topRatedText.setOnClickListener(this);
        fragmentHomeBinding.upComingText.setOnClickListener(this);

        checkConnection();


        return fragmentHomeBinding.getRoot();
    }

    private void getLatestMovie() {

        Observable.interval(1, TimeUnit.MILLISECONDS)
                .flatMap(n -> appViewModel.makeLatestMovieFutureCall().get())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Movie>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        Log.d("######", "onSubscribe");
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull Movie trending) {
                        fragmentHomeBinding.latestMovieText.setVisibility(View.VISIBLE);
                        if (fragmentHomeBinding.latestMovieRecycleView.getAdapter() != null) {
                            latestMovieAdapter = (LatestMovieAdapter) fragmentHomeBinding.latestMovieRecycleView.getAdapter();
                            latestMovieAdapter.updateTrending(trending.getResults());

                        } else {
                            latestMovieAdapter = new LatestMovieAdapter(trending.getResults());
                            fragmentHomeBinding.latestMovieRecycleView.setAdapter(latestMovieAdapter);

                        }
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.d("######", "onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.d("######", "ERROR");
                    }
                });
    }

    private void getPopular() {
        Observable.interval(1, TimeUnit.MILLISECONDS)
                .flatMap(n -> appViewModel.makePopularFutureCall().get())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Movie>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull Movie popular) {
                        fragmentHomeBinding.popularText.setVisibility(View.VISIBLE);
                        if (fragmentHomeBinding.popularRecycleView.getAdapter() != null) {
                            popularAdapter = (PopularAdapter) fragmentHomeBinding.popularRecycleView.getAdapter();
                            popularAdapter.updatePopular(popular.getResults());

                        } else {
                            popularAdapter = new PopularAdapter(popular.getResults());
                            fragmentHomeBinding.popularRecycleView.setAdapter(popularAdapter);

                        }
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void getTopRated() {
        Observable.interval(1, TimeUnit.MILLISECONDS)
                .flatMap(n -> appViewModel.makeTopRatedFutureCall().get())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Movie>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull Movie popular) {
                        fragmentHomeBinding.topRatedText.setVisibility(View.VISIBLE);
                        if (fragmentHomeBinding.topRatedRecycleView.getAdapter() != null) {
                            topRatedMovieAdapter = (TopRatedMovieAdapter) fragmentHomeBinding.topRatedRecycleView.getAdapter();
                            topRatedMovieAdapter.updateTopRated(popular.getResults());

                        } else {
                            topRatedMovieAdapter = new TopRatedMovieAdapter(popular.getResults());
                            fragmentHomeBinding.topRatedRecycleView.setAdapter(topRatedMovieAdapter);

                        }
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private void getUpcoming() {
        Observable.interval(1, TimeUnit.MILLISECONDS)
                .flatMap(n -> appViewModel.makeUpComingFutureCall().get())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Movie>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull Movie upComing) {
                        fragmentHomeBinding.upComingText.setVisibility(View.VISIBLE);
                        if (fragmentHomeBinding.upComingRecycleView.getAdapter() != null) {
                            upComingAdopter = (UpComingAdapter) fragmentHomeBinding.upComingRecycleView.getAdapter();
                            upComingAdopter.updateUpcoming(upComing.getResults());

                        } else {
                            upComingAdopter = new UpComingAdapter(upComing.getResults());
                            fragmentHomeBinding.upComingRecycleView.setAdapter(upComingAdopter);

                        }
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.e("TAG", "Error:  " + e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private void checkConnection() {
        ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {

            @Override
            public void onAvailable(@androidx.annotation.NonNull Network network) {
                getLatestMovie();
                getPopular();
                getTopRated();
                getUpcoming();
            }

            @Override
            public void onLost(@androidx.annotation.NonNull Network network) {
                aestheticDialog = new AestheticDialog.Builder(requireActivity(), DialogStyle.CONNECTIFY, DialogType.ERROR)
                        .setTitle("No Available Connection")
                        .setMessage("internet connection has been interrupted")
                        .setDarkMode(true)
                        .setGravity(Gravity.CENTER)
                        .setAnimation(DialogAnimation.SPIN)
                        .show();
            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback);
        } else {
            connectivityManager.registerNetworkCallback(networkRequest, networkCallback);
        }

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.latestMovieText) {
            if (fragmentHomeBinding.expandableLatestMovies.getState() == ExpandableLayout.State.EXPANDED) {
                fragmentHomeBinding.expandableLatestMovies.collapse();
            } else {
                fragmentHomeBinding.expandableLatestMovies.expand();
            }
        } else if (v.getId() == R.id.popularText) {
            if (fragmentHomeBinding.expandablePopularMovies.getState() == ExpandableLayout.State.EXPANDED) {
                fragmentHomeBinding.expandablePopularMovies.collapse();
            } else {
                fragmentHomeBinding.expandablePopularMovies.expand();
            }
        } else if (v.getId() == R.id.topRatedText) {
            if (fragmentHomeBinding.expandableTopRatedMovies.getState() == ExpandableLayout.State.EXPANDED) {
                fragmentHomeBinding.expandableTopRatedMovies.collapse();
            } else {
                fragmentHomeBinding.expandableTopRatedMovies.expand();
            }
        } else if (v.getId() == R.id.upComingText) {
            if (fragmentHomeBinding.expandableUpcomingMovies.getState() == ExpandableLayout.State.EXPANDED) {
                fragmentHomeBinding.expandableUpcomingMovies.collapse();
            } else {
                fragmentHomeBinding.expandableUpcomingMovies.expand();
            }
        }
    }
}
