package com.codetribe.www.miwok;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ColourActivity extends AppCompatActivity {
private  ListView lsView;
private MediaPlayer mediaPlayer;
 private AudioManager manager;


    AudioManager.OnAudioFocusChangeListener afChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        // Permanent loss of audio focus
                    }
                    else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        // Pause playback
                        mediaPlayer.pause();
                        mediaPlayer.seekTo(0);
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        // Lower the volume, keep playing
                        releaseMediaPlayer();
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        // Your app has been granted audio focus again
                        // resume playback
                        mediaPlayer.start();
                    }
                }
            };
private MediaPlayer.OnCompletionListener mListner = new MediaPlayer.OnCompletionListener() {
    @Override
    public void onCompletion(MediaPlayer mp) {
        releaseMediaPlayer();;
    }
};

    private MediaPlayer.OnCompletionListener mListener  = new MediaPlayer.OnCompletionListener(){
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);

        final ArrayList<Word> words = new ArrayList<Word>();

        //words.add("one");
        words.add(new Word("red", "weṭeṭṭi", R.drawable.color_red, R.raw.color_red));
        words.add(new Word("green", "chokokki", R.drawable.color_green, R.raw.color_green));
        words.add(new Word("brown", "ṭakaakki", R.drawable.color_brown, R.raw.color_brown));
        words.add(new Word("gray", "ṭopoppi", R.drawable.color_gray, R.raw.color_gray));
        words.add(new Word("black", "kululli", R.drawable.color_black, R.raw.color_black));
        words.add(new Word("white", "kelelli", R.drawable.color_white, R.raw.color_white));
        words.add(new Word("dusty yellow", "opiisә", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        words.add(new Word("mustard yellow", "chiwiiṭә", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));


        //Find the listView of the whole layout
        lsView = (ListView) findViewById(R.id.lsView);
        WordAdapter adapter;

        adapter = new WordAdapter(this, R.layout.list_item, words, R.color.category_colors);

        lsView.setAdapter(adapter);

        lsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word wordAudio = words.get(position);

                releaseMediaPlayer();
                //We have audio focus now
                //We have audio focus now
                int results = manager.requestAudioFocus(null,
                        //Use the music stream
                        AudioManager.STREAM_MUSIC,
                        //Request permanent focus
                        AudioManager.AUDIOFOCUS_GAIN);
                if (results == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    //Start playback
                    //Create media player to play audio
                    mediaPlayer = MediaPlayer.create(ColourActivity.this, wordAudio.getAudio_resource_id());
                    mediaPlayer.start();
                    //Method called when ever an audio is finished
                    mediaPlayer.setOnCompletionListener(mListener);
                }
            }
        });
    }



    //Whenever the acitvity is on the onStop() state the audio being played must immediately stop
    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }


    public void releaseMediaPlayer()
        {
            if(mediaPlayer != null)
            {
                mediaPlayer.release();

                mediaPlayer = null;
                //Abandon audio focus when playback complete
                manager.abandonAudioFocus(afChangeListener);
            }
        }
    }





