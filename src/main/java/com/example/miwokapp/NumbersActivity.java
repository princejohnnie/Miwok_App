package com.example.miwokapp;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {
    private AudioManager mAudioManager;

    // handles playback of all the sound files
    private MediaPlayer mediaPlayer;
    // this listener gets triggered when the MediaPlayer has completed playing the audio file
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    AudioManager.OnAudioFocusChangeListener audioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            switch (focusChange) {
                case AudioManager.AUDIOFOCUS_GAIN:
                    mediaPlayer.start();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS:
                    releaseMediaPlayer();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    mediaPlayer.pause();
                    mediaPlayer.seekTo(0);
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_list);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> wordsList = new ArrayList<>();

        wordsList.add(new Word("one", "lutti", R.drawable.number_one, R.raw.one));
        wordsList.add(new Word("two","otiiko", R.drawable.number_two, R.raw.two));
        wordsList.add(new Word("three", "tolookosu", R.drawable.number_three, R.raw.three));
        wordsList.add(new Word("four", "oyyisa", R.drawable.number_four, R.raw.four));
        wordsList.add(new Word("five", "massokka", R.drawable.number_five, R.raw.five));
        wordsList.add(new Word("six", "temmokka", R.drawable.number_six, R.raw.six));
        wordsList.add(new Word("seven", "kenekaku", R.drawable.number_seven, R.raw.seven));
        wordsList.add(new Word("eight", "kawinta", R.drawable.number_eight, R.raw.eight));
        wordsList.add(new Word("nine", "wo’e", R.drawable.number_nine, R.raw.nine));
        wordsList.add(new Word("ten", "na’aacha", R.drawable.number_ten, R.raw.ten));

        // Using the custom ArrayAdapter (NumbersAdapter) whose data source is a list of Strings.
        // The adapter knows how to create layouts for each item in the list, using the
        // list_item.drawable_xxxhdpi.png layout resource.
        // This list item layout contains two TextViews which the adapter will set to
        // display the two words
        NumbersAdapter numbersAdapter = new NumbersAdapter(this, wordsList, R.color.category_numbers);

        // Find the ListView object in the view hierarchy of the NumbersActivity.
        // There should be a ListView with the view ID called list, which is declared in the
        // activity_words_list.drawable_xxxhdpi.png layout file.
        ListView listView = findViewById(R.id.list);

        // Make the ListView use the NumbersAdapter we created above, so that the
        // ListView will display list items for each word in the list of words.
        // Do this by calling the setAdapter method on the ListView object and pass in
        // 1 argument, which is the NumbersAdapter with the variable name numbersAdapter.
        listView.setAdapter(numbersAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = wordsList.get(position);

                // release the media player if it currently exists because we are about to play a different audio file
                releaseMediaPlayer();

                // request audio focus for playback
                int result = mAudioManager.requestAudioFocus(audioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // we have audio focus now so we can start media player
                    // setup the media player
                    mediaPlayer = MediaPlayer.create(NumbersActivity.this, word.getAudioResourceId());
                    mediaPlayer.start();

                    // Setup a listener on the media player, so that we can stop and release
                    // the media player once it has finished playing an audio
                    mediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    /* Clean up the media player by releasing its resources
    * */
    private void releaseMediaPlayer() {
        // if the media player is not null then it may be currently playing a song
        if (mediaPlayer != null) {
            // regardless of the current state of the media player, release its resources because we no longer need it
            mediaPlayer.release();
            // set media player back to null after release
            mediaPlayer = null;

            // abandon audio focus when playback complete
            mAudioManager.abandonAudioFocus(audioFocusChangeListener);
        }
    }
}