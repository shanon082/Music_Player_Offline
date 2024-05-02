package com.spisabirye.music;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class NewActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private String musicPath;
    private boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        // Retrieve music details from intent
        String title = getIntent().getStringExtra("title");
        String artist = getIntent().getStringExtra("artist");
        musicPath = getIntent().getStringExtra("path");

        // Set title and artist TextViews
        TextView titleTextView = findViewById(R.id.music_title_text_view);
        TextView artistTextView = findViewById(R.id.music_artist_text_view);
        titleTextView.setText(title);
        artistTextView.setText(artist);

        // Initialize MediaPlayer
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(musicPath);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set click listener for play button
        Button playButton = findViewById(R.id.play_button);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    pausePlayback();
                } else {
                    startPlayback();
                }
            }
        });
    }

    private void startPlayback() {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            isPlaying = true;
            updatePlayButtonState();
        }
    }

    private void pausePlayback() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isPlaying = false;
            updatePlayButtonState();
        }
    }

    private void updatePlayButtonState() {
        Button playButton = findViewById(R.id.play_button);
        if (isPlaying) {
            playButton.setText("Pause");
        } else {
            playButton.setText("Play");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Release MediaPlayer resources when activity is stopped
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
