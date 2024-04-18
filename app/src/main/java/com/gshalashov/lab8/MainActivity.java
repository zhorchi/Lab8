package com.gshalashov.lab8;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;


public class MainActivity extends AppCompatActivity {

    VideoView videoPlayer;
    AudioManager audioManager;

    private ImageView imageView;
    private int currentImageIndex = 0; // Индекс текущего изображения

    private static final int[] IMAGE_RESOURCES = {
            R.raw.cat1,
            R.raw.cat2,
            R.raw.cat3,
            R.raw.cat4,
            R.raw.cat5,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView img = findViewById(R.id.img);
        InputStream imageStream = this.getResources().openRawResource(R.raw.cat1);
        Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
        img.setImageBitmap(bitmap);

        imageView = findViewById(R.id.img);
        loadInitialImage();

        Button prevButton = findViewById(R.id.prev_button);
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousImage();
            }
        });

        Button nextButton = findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextImage();
            }
        });


        videoPlayer = findViewById(R.id.video_player);
        Uri myVideoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video);
        videoPlayer.setVideoURI(myVideoUri);
        MediaController mediaController = new MediaController(this);

        mediaController.setMediaPlayer(videoPlayer);
        videoPlayer.setMediaController(mediaController);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        SeekBar volumeSeekBar = findViewById(R.id.volume_seek_bar);
        volumeSeekBar.setMax(maxVolume);
        volumeSeekBar.setProgress(currentVolume);

        volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress,0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void loadInitialImage() {
        imageView.setImageBitmap(BitmapFactory.decodeStream(getResources().openRawResource(IMAGE_RESOURCES[currentImageIndex])));
    }

    private void previousImage() {
        currentImageIndex = (currentImageIndex - 1 + IMAGE_RESOURCES.length) % IMAGE_RESOURCES.length;
        imageView.setImageBitmap(BitmapFactory.decodeStream(getResources().openRawResource(IMAGE_RESOURCES[currentImageIndex])));
    }

    private void nextImage() {
        currentImageIndex = (currentImageIndex + 1) % IMAGE_RESOURCES.length;
        imageView.setImageBitmap(BitmapFactory.decodeStream(getResources().openRawResource(IMAGE_RESOURCES[currentImageIndex])));
    }
}