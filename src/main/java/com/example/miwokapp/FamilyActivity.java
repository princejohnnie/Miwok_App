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

public class FamilyActivity extends AppCompatActivity {
    private AudioManager mAudioManager;

    private MediaPlayer mediaPlayer;

    private MediaPlayer.OnCompletionListener mOnCompleted = new MediaPlayer.OnCompletionListener() {
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

        wordsList.add(new Word("father", "әpә", R.drawable.family_father, R.raw.one));
        wordsList.add(new Word("mother","әṭa", R.drawable.family_mother, R.raw.two));
        wordsList.add(new Word("son", "angsi", R.drawable.family_son, R.raw.three));
        wordsList.add(new Word("daughter", "tune", R.drawable.family_daughter, R.raw.four));
        wordsList.add(new Word("older brother", "taachi", R.drawable.family_older_brother, R.raw.five));
        wordsList.add(new Word("younger brother", "chalitti", R.drawable.family_younger_brother, R.raw.six));
        wordsList.add(new Word("older sister", "teṭe", R.drawable.family_older_sister, R.raw.seven));
        wordsList.add(new Word("younger sister", "kolliti", R.drawable.family_younger_sister, R.raw.eight));
        wordsList.add(new Word("grandmother", "ama", R.drawable.family_grandmother, R.raw.nine));
        wordsList.add(new Word("grandfather", "paapa", R.drawable.family_grandfather, R.raw.ten));

        // Using the custom ArrayAdapter (NumbersAdapter) whose data source is a list of Strings.
        // The adapter knows how to create layouts for each item in the list, using the
        // list_item.drawable_xxxhdpi.png layout resource.
        // This list item layout contains two TextViews which the adapter will set to
        // display the two words
        NumbersAdapter numbersAdapter = new NumbersAdapter(this, wordsList, R.color.category_family);

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

                releaseMediaPlayer();

                int result = mAudioManager.requestAudioFocus(audioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer = MediaPlayer.create(FamilyActivity.this, word.getAudioResourceId());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(mOnCompleted);
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

            mediaPlayer = null;

            mAudioManager.abandonAudioFocus(audioFocusChangeListener);
        }
    }
}