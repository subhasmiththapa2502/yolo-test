package com.yolo.test.presentation.home;

import static com.yolo.test.common.Constants.STATIC_COLLAPSING_TOOLBAR_IMAGE;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.snackbar.Snackbar;
import com.thecode.aestheticdialogs.AestheticDialog;
import com.thecode.aestheticdialogs.DialogAnimation;
import com.thecode.aestheticdialogs.DialogStyle;
import com.thecode.aestheticdialogs.DialogType;
import com.yolo.test.MainActivity;
import com.yolo.test.Models.Movie;
import com.yolo.test.R;
import com.yolo.test.ViewModel.AppViewModel;
import com.yolo.test.databinding.FragmentHomeBinding;
import com.yolo.test.presentation.home.adapters.LatestMovieAdapter;
import com.yolo.test.presentation.home.adapters.PopularAdapter;
import com.yolo.test.presentation.home.adapters.TopRatedMovieAdapter;
import com.yolo.test.presentation.home.adapters.UpComingAdapter;

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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class HomeFragment extends Fragment implements View.OnClickListener {
    FragmentHomeBinding fragmentHomeBinding;
    MainActivity mainActivity;
    AppViewModel appViewModel;
    CompositeDisposable compositeDisposable;
    CompositeDisposable compositeDisposableLatestMovies;

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
        setUpToolBar(view);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);

        compositeDisposable = new CompositeDisposable();
        compositeDisposableLatestMovies = new CompositeDisposable();


        fragmentHomeBinding.latestMovieText.setOnClickListener(this);
        fragmentHomeBinding.popularText.setOnClickListener(this);
        fragmentHomeBinding.topRatedText.setOnClickListener(this);
        fragmentHomeBinding.upComingText.setOnClickListener(this);

        checkConnection();
        showImage();
        fragmentHomeBinding.lottieLoader.setVisibility(View.VISIBLE);
        return fragmentHomeBinding.getRoot();
    }

    private void showImage() {
        fragmentHomeBinding.lottieLoader1.setVisibility(View.VISIBLE);
        Glide.with(requireActivity())
                .load(STATIC_COLLAPSING_TOOLBAR_IMAGE)
                .transition(DrawableTransitionOptions.withCrossFade())
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        fragmentHomeBinding.lottieLoader1.setVisibility(View.GONE);
                        showSnackBar(getString(R.string.error_loading_image));
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        fragmentHomeBinding.lottieLoader1.setVisibility(View.GONE);
                        return false;

                    }
                })
                .into(fragmentHomeBinding.expandedImage);
    }

    private void hideLoader() {
        fragmentHomeBinding.lottieLoader.setVisibility(View.GONE);
    }

    private void setUpToolBar(View view) {
        NavController navController = Navigation.findNavController(view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.homeFragment)
                .build();

        Toolbar toolbar = view.findViewById(R.id.toolbar);

        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);


        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.homeFragment) {
                toolbar.setCollapseIcon(R.drawable.ic_baseline_star_24);
            }

        });


    }


    private void getLatestMovieOnce() {
        appViewModel.makeLatestMovieCall().enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(@NonNull Call<Movie> call, @NonNull Response<Movie> trending) {
                if (trending.isSuccessful()) {
                    hideLoader();
                    fragmentHomeBinding.latestMovieText.setVisibility(View.VISIBLE);
                    if (fragmentHomeBinding.latestMovieRecycleView.getAdapter() != null) {
                        latestMovieAdapter = (LatestMovieAdapter) fragmentHomeBinding.latestMovieRecycleView.getAdapter();
                        assert trending.body() != null;
                        latestMovieAdapter.updateTrending(trending.body().getResults());
                    } else {
                        assert trending.body() != null;
                        latestMovieAdapter = new LatestMovieAdapter(trending.body().getResults(), requireActivity().getSupportFragmentManager());
                        fragmentHomeBinding.latestMovieRecycleView.setAdapter(latestMovieAdapter);
                    }
                } else {
                    hideLoader();
                    showSnackBar(getString(R.string.something_went_wrong));

                }
            }

            @Override
            public void onFailure(@NonNull Call<Movie> call, @NonNull Throwable t) {
                hideLoader();
                showSnackBar(t.getMessage());

            }
        });
    }

    private void getLatestMovie() {

        Observable.interval(30, TimeUnit.SECONDS)
                .flatMap(n -> appViewModel.makeLatestMovieFutureCall().get())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Movie>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        compositeDisposableLatestMovies.add(d);
                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull Movie trending) {

                        fragmentHomeBinding.latestMovieText.setVisibility(View.VISIBLE);
                        if (fragmentHomeBinding.latestMovieRecycleView.getAdapter() != null) {
                            latestMovieAdapter = (LatestMovieAdapter) fragmentHomeBinding.latestMovieRecycleView.getAdapter();
                            latestMovieAdapter.updateTrending(trending.getResults());

                        } else {
                            latestMovieAdapter = new LatestMovieAdapter(trending.getResults(), requireActivity().getSupportFragmentManager());
                            fragmentHomeBinding.latestMovieRecycleView.setAdapter(latestMovieAdapter);
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        showSnackBar(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void showSnackBar(String message) {

        Snackbar snackbar = Snackbar
                .make(fragmentHomeBinding.coordinator, message, Snackbar.LENGTH_LONG)
                .setAction("OK", view -> {
                });

        View sbView = snackbar.getView();
        sbView.setBackgroundColor(Color.BLACK);
        snackbar.setActionTextColor(Color.WHITE);
        snackbar.setTextColor(Color.WHITE);
        snackbar.show();
    }

    private void getPopularOnce() {
        appViewModel.makePopularCall().enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(@NonNull Call<Movie> call, @NonNull Response<Movie> popular) {
                if (popular.isSuccessful()) {
                    hideLoader();
                    fragmentHomeBinding.popularText.setVisibility(View.VISIBLE);
                    if (fragmentHomeBinding.popularRecycleView.getAdapter() != null) {
                        popularAdapter = (PopularAdapter) fragmentHomeBinding.popularRecycleView.getAdapter();
                        assert popular.body() != null;
                        popularAdapter.updatePopular(popular.body().getResults());

                    } else {
                        assert popular.body() != null;
                        popularAdapter = new PopularAdapter(popular.body().getResults(), requireActivity().getSupportFragmentManager());
                        fragmentHomeBinding.popularRecycleView.setAdapter(popularAdapter);

                    }
                } else {
                    hideLoader();
                    showSnackBar(getString(R.string.something_went_wrong));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Movie> call, @NonNull Throwable t) {
                showSnackBar(t.getMessage());
                hideLoader();
            }
        });
    }


    private void getTopRatedOnce() {
        appViewModel.makeTopRatedCall().enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(@NonNull Call<Movie> call, @NonNull Response<Movie> popular) {
                if (popular.isSuccessful()) {
                    hideLoader();
                    fragmentHomeBinding.loadingTopRatedMovies.setVisibility(View.GONE);
                    fragmentHomeBinding.topRatedText.setVisibility(View.VISIBLE);
                    if (fragmentHomeBinding.topRatedRecycleView.getAdapter() != null) {
                        topRatedMovieAdapter = (TopRatedMovieAdapter) fragmentHomeBinding.topRatedRecycleView.getAdapter();
                        assert popular.body() != null;
                        topRatedMovieAdapter.updateTopRated(popular.body().getResults());
                    } else {
                        assert popular.body() != null;
                        topRatedMovieAdapter = new TopRatedMovieAdapter(popular.body().getResults(), requireActivity().getSupportFragmentManager());
                        fragmentHomeBinding.topRatedRecycleView.setAdapter(topRatedMovieAdapter);

                    }
                } else {
                    hideLoader();
                    showSnackBar(getString(R.string.something_went_wrong));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Movie> call, @NonNull Throwable t) {
                hideLoader();
                showSnackBar(getString(R.string.something_went_wrong));
            }
        });
    }


    private void getUpComingOnce() {
        appViewModel.makeUpComingCall().enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(@NonNull Call<Movie> call, @NonNull Response<Movie> upComing) {
                if (upComing.isSuccessful()) {
                    hideLoader();
                    fragmentHomeBinding.upComingText.setVisibility(View.VISIBLE);
                    fragmentHomeBinding.loadingUpComingMovies.setVisibility(View.GONE);
                    if (fragmentHomeBinding.upComingRecycleView.getAdapter() != null) {
                        upComingAdopter = (UpComingAdapter) fragmentHomeBinding.upComingRecycleView.getAdapter();
                        assert upComing.body() != null;
                        upComingAdopter.updateUpcoming(upComing.body().getResults());
                    } else {
                        assert upComing.body() != null;
                        upComingAdopter = new UpComingAdapter(upComing.body().getResults(), requireActivity().getSupportFragmentManager());
                        fragmentHomeBinding.upComingRecycleView.setAdapter(upComingAdopter);
                    }
                } else {
                    hideLoader();
                    showSnackBar(getString(R.string.something_went_wrong));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Movie> call, @NonNull Throwable t) {
                hideLoader();
                showSnackBar(t.getMessage());
            }
        });
    }


    private void checkConnection() {
        ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {

            @Override
            public void onAvailable(@androidx.annotation.NonNull Network network) {
                getLatestMovieOnce();
                getLatestMovie();
                getPopularOnce();
            }

            @Override
            public void onLost(@androidx.annotation.NonNull Network network) {
                hideLoader();
                showSnackBar(getString(R.string.no_internet));
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
                compositeDisposableLatestMovies.clear();
            } else {
                getLatestMovie();
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
                fragmentHomeBinding.loadingTopRatedMovies.setVisibility(View.GONE);
            } else {
                fragmentHomeBinding.loadingTopRatedMovies.setVisibility(View.VISIBLE);
                //getTopRated();
                getTopRatedOnce();
                fragmentHomeBinding.expandableTopRatedMovies.expand();
            }
        } else if (v.getId() == R.id.upComingText) {
            if (fragmentHomeBinding.expandableUpcomingMovies.getState() == ExpandableLayout.State.EXPANDED) {
                fragmentHomeBinding.expandableUpcomingMovies.collapse();
                fragmentHomeBinding.loadingUpComingMovies.setVisibility(View.GONE);
            } else {
                fragmentHomeBinding.loadingUpComingMovies.setVisibility(View.VISIBLE);
                //getUpcoming();
                getUpComingOnce();
                fragmentHomeBinding.expandableUpcomingMovies.expand();
            }
        }
    }
}
