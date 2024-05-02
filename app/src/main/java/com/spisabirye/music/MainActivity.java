package com.spisabirye.music;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MusicAdapter.OnMusicClickListener {

    private RecyclerView recyclerView;
    int position;
    private static final int REQUEST_PERMISSION_CODE = 123;
    private MusicAdapter adapter;
    private List<Music> musicList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        musicList = new ArrayList<>();
        adapter = new MusicAdapter(this, musicList, this);
        recyclerView.setAdapter(adapter);

        loadMusic();
    }

    private void loadMusic() {
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = { MediaStore.Audio.AudioColumns.DATA,
                MediaStore.Audio.AudioColumns.TITLE,
                MediaStore.Audio.AudioColumns.ARTIST };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String path = cursor.getString(0);
                String title = cursor.getString(1);
                String artist = cursor.getString(2);
                musicList.add(new Music(title, artist, path));
            }
            cursor.close();
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onMusicClick(int position) {
        Music selectedMusic = musicList.get(position);
        Intent intent = new Intent(MainActivity.this, NewActivity.class);
        intent.putExtra("title", selectedMusic.getTitle());
        intent.putExtra("artist", selectedMusic.getArtist());
        intent.putExtra("path", selectedMusic.getPath());
        startActivity(intent);
    }

}
