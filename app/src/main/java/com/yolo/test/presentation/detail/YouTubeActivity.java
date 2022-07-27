package com.yolo.test.presentation.detail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.DefaultPlayerUiController;
import com.yolo.test.R;
import com.yolo.test.common.Constants;
import com.yolo.test.databinding.ActivityYouTubeBinding;

public class YouTubeActivity extends AppCompatActivity {

    ActivityYouTubeBinding activityYouTubeBinding;
    String youTubeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        activityYouTubeBinding = DataBindingUtil.setContentView(this, R.layout.activity_you_tube);
        youTubeId = getIntent().getExtras().getString(Constants.YOUTUBE_ID);


        getLifecycle().addObserver(activityYouTubeBinding.youtubePlayerView);

        activityYouTubeBinding.youtubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo(youTubeId, 0);
                activityYouTubeBinding.loadingYoutube.setVisibility(View.GONE);
                activityYouTubeBinding.youtubePlayerView.setVisibility(View.VISIBLE);
                DefaultPlayerUiController defaultPlayerUiController = new DefaultPlayerUiController(activityYouTubeBinding.youtubePlayerView, youTubePlayer);
                activityYouTubeBinding.youtubePlayerView.setCustomPlayerUi(defaultPlayerUiController.getRootView());
            }
        });
        activityYouTubeBinding.youtubePlayerView.enterFullScreen();
    }

    @Override
    protected void onStop() {
        super.onStop();
        activityYouTubeBinding.youtubePlayerView.release();
    }
}