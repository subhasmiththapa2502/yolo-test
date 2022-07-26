package com.yolo.test;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;

import com.yolo.test.ViewModel.AppViewModel;
import com.yolo.test.databinding.ActivityMainBinding;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    ActivityMainBinding activityMainBinding;
    NavHostFragment navHostFragment;
    NavController navController;
    AppBarConfiguration appBarConfiguration;
    AppViewModel appViewModel;
    CompositeDisposable disposable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        disposable = new CompositeDisposable();

        setUpViewModel();
        setUpNavigationComponent();


    }

    private void setUpNavigationComponent() {
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);

        navController = navHostFragment != null ? navHostFragment.getNavController() : null;

        appBarConfiguration = new AppBarConfiguration.Builder(R.id.homeFragment)
                .build();
    }

    private void setUpViewModel() {
        appViewModel = new ViewModelProvider(MainActivity.this).get(AppViewModel.class);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}