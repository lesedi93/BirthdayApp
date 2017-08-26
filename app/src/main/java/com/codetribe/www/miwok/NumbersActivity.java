package com.codetribe.www.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

//Declaration of variables
private ListView lsView;
private MediaPlayer mediaPlayer;
private MediaPlayer.OnCompletionListener mListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };


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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);

        //Initialising audio manager to request audio focus
        manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        // String [] words = new String[10];
        final ArrayList<Word> words = new ArrayList<Word>();

        //words.add("one");
        words.add(new Word("one", "lutti", R.drawable.number_one, R.raw.number_one));
        words.add(new Word("two", "ottiko", R.drawable.number_two, R.raw.number_two));
        words.add(new Word("three", "tolookuso", R.drawable.number_three, R.raw.number_three));
        words.add(new Word("four", "oyyisa", R.drawable.number_four, R.raw.number_four));
        words.add(new Word("five", "massokka", R.drawable.number_five, R.raw.number_five));
        words.add(new Word("six", "temmokka", R.drawable.number_six, R.raw.number_six));
        words.add(new Word("seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven));
        words.add(new Word("eight", "kawinta", R.drawable.number_eight, R.raw.number_eight));
        words.add(new Word("nine", "wo'e", R.drawable.number_nine, R.raw.number_nine));
        words.add(new Word("ten", "na'acha", R.drawable.number_ten, R.raw.number_ten));

        //Find the listView of the whole layout
        lsView = (ListView) findViewById(R.id.lsView);
        WordAdapter adapter;
        //initialising and setting the resources into the list item to visibily displays values
        adapter = new WordAdapter(this, R.layout.list_item, words, R.color.category_numbers);

        lsView.setAdapter(adapter);
        //Implemented method used to click each listView item which plays an audio
        lsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               //Initialisation of each object to get the index
                Word audioWord = words.get(position);
                //Clean up the mediaPlayer for other resources to get efficient memory
                releaseMediaPlayer();
                //We have audio focus now
                int results = manager.requestAudioFocus(null,
                        //Use the music stream
                        AudioManager.STREAM_MUSIC,
                        //Request permanent focus
                        AudioManager.AUDIOFOCUS_GAIN);
                if(results == AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
                {
                    //Start playback
                    //Create media player to play audio
                    mediaPlayer = MediaPlayer.create(NumbersActivity.this, audioWord.getAudio_resource_id());
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

    /**
     * Helper method called to clean up resources being used after the audio has finished playing
     */
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
