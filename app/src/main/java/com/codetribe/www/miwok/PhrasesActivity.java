package com.codetribe.www.miwok;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {
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


    private MediaPlayer.OnCompletionListener mListener = new MediaPlayer.OnCompletionListener()
    {
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
        words.add(new Word("Where are you going?", "minto wuksus", R.raw.phrase_where_are_you_going));
        words.add(new Word("What is your name?", "tinnә oyaase'nә", R.raw.phrase_what_is_your_name));
        words.add(new Word("My name is...", "oyaaset...", R.raw.phrase_my_name_is));
        words.add(new Word("How are you feeling?", "michәksәs?", R.raw.phrase_how_are_you_feeling));
        words.add(new Word("I’m feeling good.", "kuchi achit", R.raw.phrase_im_feeling_good));
        words.add(new Word("Are you coming?", "әәnәs'aa?", R.raw.phrase_are_you_coming));
        words.add(new Word("Yes, I’m coming.", "hәә’ әәnәm", R.raw.phrase_yes_im_coming));
        words.add(new Word("I’m coming.", "әәnәm", R.raw.phrase_im_coming));
        words.add(new Word("Let’s go.", "yoowutis", R.raw.phrase_lets_go));
        words.add(new Word("Come here.", "әnni'nem", R.raw.phrase_come_here));

        //Find the listView of the whole layout
        lsView = (ListView) findViewById(R.id.lsView);
        WordAdapter adapter;

        adapter = new WordAdapter(this, R.layout.list_item, words, R.color.category_phrases);

        lsView.setAdapter(adapter);

        lsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Get the position of each word on the list item
                Word audioWord = words.get(position);
                //Release resource if media player plays a different audio
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
                    mediaPlayer = MediaPlayer.create(PhrasesActivity.this, audioWord.getAudio_resource_id());
                    mediaPlayer.start();
                    //Method called when ever an audio is finished
                    mediaPlayer.setOnCompletionListener(mListener);
                }
                mediaPlayer = MediaPlayer.create(PhrasesActivity.this, audioWord.getAudio_resource_id());
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

