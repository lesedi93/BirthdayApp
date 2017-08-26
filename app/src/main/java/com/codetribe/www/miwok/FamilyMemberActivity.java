package com.codetribe.www.miwok;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class FamilyMemberActivity extends AppCompatActivity {
    private ListView lsView;
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
    private MediaPlayer.OnCompletionListener mListener = new MediaPlayer.OnCompletionListener(){
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
        words.add(new Word("father", "әpә", R.drawable.family_father, R.raw.family_father));
        words.add(new Word("mother", "әṭa", R.drawable.family_mother, R.raw.family_mother));
        words.add(new Word("son", "angsi", R.drawable.family_son, R.raw.family_son));
        words.add(new Word("daughter", "tune", R.drawable.family_daughter, R.raw.family_daughter));
        words.add(new Word("older brother", "taachi", R.drawable.family_older_brother, R.raw.family_older_brother));
        words.add(new Word("younger brother", "chalitti", R.drawable.family_younger_brother, R.raw.family_younger_sister));
        words.add(new Word("older sister", "teṭe", R.drawable.family_older_sister, R.raw.family_older_sister));
        words.add(new Word("younger sister", "kolliti", R.drawable.family_younger_sister, R.raw.family_younger_sister));
        words.add(new Word("grandmother", "ama", R.drawable.family_grandmother, R.raw.family_grandmother));
        words.add(new Word("grandfather", "na'acha", R.drawable.family_grandfather, R.raw.family_grandfather));

        //Find the listView of the whole layout
        lsView = (ListView) findViewById(R.id.lsView);
        WordAdapter adapter;

        adapter = new WordAdapter(this, R.layout.list_item, words, R.color.category_family);

        lsView.setAdapter(adapter);

        lsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word audioWord = words.get(position);
                //Release the resource if the media player plays a different audio
                releaseMediaPlayer();

                int results = manager.requestAudioFocus(null,
                        //Use the music stream
                        AudioManager.STREAM_MUSIC,
                        //Request permanent focus
                        AudioManager.AUDIOFOCUS_GAIN);
                if(results == AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)
                {
                    //Start playback
                    //Create media player to play audio
                    mediaPlayer = MediaPlayer.create(FamilyMemberActivity.this, audioWord.getAudio_resource_id());
                    mediaPlayer.start();
                    //Method called when ever an audio is finished
                    mediaPlayer.setOnCompletionListener(mListener);
                }
                mediaPlayer = MediaPlayer.create(FamilyMemberActivity.this, audioWord.getAudio_resource_id());
                //Start the audio
                mediaPlayer.start();
                //Set up a listener on the media player, so that we can stop the media player once the sound has finished playing
                mediaPlayer.setOnCompletionListener(mListener);

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
     * Cleans up the media player by releasing the resources
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

