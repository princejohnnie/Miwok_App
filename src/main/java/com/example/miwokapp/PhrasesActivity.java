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

public class PhrasesActivity extends AppCompatActivity {
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

        wordsList.add(new Word("Where are you going?", "minto wuksus", R.raw.one));
        wordsList.add(new Word("What is your name?","tinnә oyaase'nә", R.raw.two));
        wordsList.add(new Word("My name is...", "oyaaset...", R.raw.three));
        wordsList.add(new Word("How are you feeling?", "michәksәs?", R.raw.four));
        wordsList.add(new Word("I’m feeling good.", "kuchi achit", R.raw.five));
        wordsList.add(new Word("Are you coming?", "әәnәs'aa?", R.raw.six));
        wordsList.add(new Word("Yes, I’m coming.", "hәә’ әәnәm", R.raw.seven));
        wordsList.add(new Word("I’m coming.", "әәnәm", R.raw.eight));
        wordsList.add(new Word("Let’s go.", "yoowutis", R.raw.nine));
        wordsList.add(new Word("Come here.", "әnni'nem", R.raw.ten));

        // Using the custom ArrayAdapter (NumbersAdapter) whose data source is a list of Strings.
        // The adapter knows how to create layouts for each item in the list, using the
        // list_item.drawable_xxxhdpi.png layout resource.
        // This list item layout contains two TextViews which the adapter will set to
        // display the two words
        NumbersAdapter numbersAdapter = new NumbersAdapter(this, wordsList, R.color.category_phrases);

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

                    mediaPlayer = MediaPlayer.create(PhrasesActivity.this, word.getAudioResourceId());
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