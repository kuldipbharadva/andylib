package com.example.libusage.exoplayerUse;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.libusage.R;
import com.google.android.exoplayer2.ui.PlayerView;

import java.util.Objects;

@SuppressWarnings("ALL")
public class VideoPlayerActivity extends AppCompatActivity {

    private Context mContext;
    private VideoView videoView;
    private PlayerView playerView;
    private PlayerManager player;

    private int seekTo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        initBasicTask();
    }

    private void initBasicTask() {
        mContext = VideoPlayerActivity.this;

        videoView = findViewById(R.id.videoview);
        playerView = findViewById(R.id.playerView);

        String urlLink = "https://wpstage.a2hosted.com/talent-spring/assets/Lession_Video/14_Minds_Website_Video_1568983864.mp4";
        String urlLink1 = "https://wpstage.a2hosted.com/talent-spring/assets/Lession_Video/001_1568984015.mp4";

        player = new PlayerManager(mContext);
        player.init(this, playerView, urlLink1);
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        player.reset();
    }

    @Override
    public void onDestroy() {
        player.release();
        super.onDestroy();
    }

    public void handleVideoTask() {

        Uri uri = Uri.parse("https://wpstage.a2hosted.com/talent-spring/assets/Lession_Video/14_Minds_Website_Video_1568983864.mp4");
        videoView.setVideoURI(uri);
        final MediaController mMedia = new MediaController(mContext);

        final ProgressDialog dialog = new ProgressDialog(mContext);
        Objects.requireNonNull(dialog.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setMessage("Please Wait...");
        dialog.show();


        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                dialog.dismiss();
                mp.setLooping(false);
                mMedia.setMediaPlayer(videoView);
                mMedia.setAnchorView(videoView);
                videoView.setMediaController(mMedia);
                videoView.start();

                if (seekTo != 0) {
                    videoView.seekTo(seekTo);
                }
            }
        });
    }
}
