package com.example.miwokapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miwokapp.ColorsActivity;
import com.example.miwokapp.FamilyActivity;
import com.example.miwokapp.NumbersActivity;
import com.example.miwokapp.PhrasesActivity;
import com.example.miwokapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linear_layout);

        // Find the View that shows the numbers category
        TextView numbers = findViewById(R.id.numbers);

        // Set a click listener on that View
        numbers.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                Intent numbersIntent = new Intent(MainActivity.this, NumbersActivity.class);
                startActivity(numbersIntent);
            }
        });

        // Find the View that shows the family category
        TextView family = findViewById(R.id.family);

        // Set a click listener on that View
        family.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the family View is clicked on
            @Override
            public void onClick(View v) {
                Intent familyIntent = new Intent(MainActivity.this, FamilyActivity.class);
                startActivity(familyIntent);
            }
        });

        TextView colors = findViewById(R.id.colors);

        //Set a click listener to that View
        colors.setOnClickListener(new View.OnClickListener() {
            //This code will be executed when the colors View is clicked on
            @Override
            public void onClick(View v) {
                Intent colorsIntent = new Intent(MainActivity.this, ColorsActivity.class);
                startActivity(colorsIntent);
            }
        });

        TextView phrases = findViewById(R.id.phrases);
        phrases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent phrasesIntent = new Intent(MainActivity.this, PhrasesActivity.class);
                startActivity(phrasesIntent);

                //Toast.makeText(v.getContext(), "Opening Phrases Activity", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /*public void openNumbers(View view) {
        Intent intent = new Intent(this, NumbersActivity.class);
        startActivity(intent);
    }*/
}