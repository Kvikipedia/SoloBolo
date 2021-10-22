package com.example.solobolo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.solobolo.DatabaseTables.Word;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;

    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {

        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        Bundle extras = getIntent().getExtras();
        String category_name = extras.getString("category_name");

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(category_name);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ArrayList<Word> words = new ArrayList<Word>();
        wordAdapter adapter;
        switch (category_name) {
            case "Numbers":
                words.add(new Word("one", "un", R.raw.number_one,
                        R.drawable.number_one));
                words.add(new Word("two", "deux", R.raw.number_two,
                        R.drawable.number_two));
                words.add(new Word("three", "trois", R.raw.number_three,
                        R.drawable.number_three));
                words.add(new Word("four", "quatre", R.raw.number_four,
                        R.drawable.number_four));
                words.add(new Word("five", "cinq", R.raw.number_five,
                        R.drawable.number_five));
                words.add(new Word("six", "six", R.raw.number_six,
                        R.drawable.number_six));
                words.add(new Word("seven", "sept", R.raw.number_seven,
                        R.drawable.number_seven));
                words.add(new Word("eight", "huit", R.raw.number_eight,
                        R.drawable.number_eight));
                words.add(new Word("nine", "neuve", R.raw.number_nine,
                        R.drawable.number_nine));
                words.add(new Word("ten", "dix", R.raw.number_ten
                        , R.drawable.number_ten));
                adapter = new wordAdapter(this, words, R.color.category_numbers);
                break;
            case "Family":
                words.add(new Word("father", "père", R.raw.family_father,
                        R.drawable.family_father));
                words.add(new Word("mother", "mère", R.raw.family_mother,
                        R.drawable.family_mother));
                words.add(new Word("son", "fils", R.raw.family_son,
                        R.drawable.family_son));
                words.add(new Word("daughter", "fille", R.raw.family_daughter,
                        R.drawable.family_daughter));
                words.add(new Word("older brother", "grand frère", R.raw.family_older_brother,
                        R.drawable.family_older_brother));
                words.add(new Word("younger brother", "frère cadet", R.raw.family_younger_brother,
                        R.drawable.family_younger_brother));
                words.add(new Word("older sister", "sœur aînée", R.raw.family_older_sister,
                        R.drawable.family_older_sister));
                words.add(new Word("younger sister", "sœur cadette", R.raw.family_younger_sister,
                        R.drawable.family_younger_sister));
                words.add(new Word("grandmother", "grand-mère", R.raw.family_grandmother,
                        R.drawable.family_grandmother));
                words.add(new Word("grandfather", "grand-père", R.raw.family_grandfather,
                        R.drawable.family_grandfather));
                adapter = new wordAdapter(this, words, R.color.category_family);
                break;
            case "Colours":
                words.add(new Word("red", "rouge", R.raw.color_red,
                        R.drawable.color_red));
                words.add(new Word("green", "verte", R.raw.color_green,
                        R.drawable.color_green));
                words.add(new Word("brown", "brun", R.raw.color_brown,
                        R.drawable.color_brown));
                words.add(new Word("gray", "grise", R.raw.color_gray,
                        R.drawable.color_gray));
                words.add(new Word("black", "noire", R.raw.color_black,
                        R.drawable.color_black));
                words.add(new Word("white", "blanche", R.raw.color_white,
                        R.drawable.color_white));
                words.add(new Word("dusty yellow", "jaune poussiéreux", R.raw.color_dusty_yellow,
                        R.drawable.color_dusty_yellow));
                words.add(new Word("mustard yellow", "jaune moutarde", R.raw.color_mustard_yellow,
                        R.drawable.color_mustard_yellow));
                adapter = new wordAdapter(this, words, R.color.category_colors);
                break;
            case "Phrases":
                words.add(new Word("Where are you going?", "Où allez-vous?",
                        R.raw.phrase_where_are_you_going));
                words.add(new Word("What is your name?", "Quel est ton nom?",
                        R.raw.phrase_what_is_your_name));
                words.add(new Word("My name is...", "Mon nom est...",
                        R.raw.phrase_my_name_is));
                words.add(new Word("How are you feeling?", "Comment allez-vous?",
                        R.raw.phrase_how_are_you_feeling));
                words.add(new Word("I’m feeling good.", "Je me sens bien.",
                        R.raw.phrase_im_feeling_good));
                words.add(new Word("Are you coming?", "Viens-tu?",
                        R.raw.phrase_are_you_coming));
                words.add(new Word("Yes, I’m coming.", "Oui, je viens.",
                        R.raw.phrase_yes_im_coming));
                words.add(new Word("I’m coming.", "J'arrive.",
                        R.raw.phrase_im_coming));
                words.add(new Word("Let’s go.", "Allons-y.",
                        R.raw.phrase_lets_go));
                words.add(new Word("Come here.", "Viens ici.",
                        R.raw.phrase_come_here));
                adapter = new wordAdapter(this, words, R.color.category_phrases);
                break;
            default:
                adapter = new wordAdapter(this, words, R.color.default_background);
                break;
        }

        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = words.get(position);
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaPlayer = MediaPlayer.create(CategoryActivity.this, word.getAudioResourceId());
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}