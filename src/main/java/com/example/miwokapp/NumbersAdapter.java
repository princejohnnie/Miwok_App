package com.example.miwokapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class NumbersAdapter extends ArrayAdapter<Word> {
    int mColorResourceId;

    public NumbersAdapter(Context context, ArrayList<Word> words, int colorResourceId) {
        super(context, 0, words);
        mColorResourceId = colorResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        // Get the data item for this position
        Word currentWord = getItem(position);
        // Lookup view for data population
        TextView tvMiwok = listItemView.findViewById(R.id.miwok_text_view);
        TextView tvDefault = listItemView.findViewById(R.id.default_text_view);
        ImageView imageView = listItemView.findViewById(R.id.imageView);

        // Populate the data into the template view using the data object
        assert currentWord != null;
        tvMiwok.setText(currentWord.getMiwokTranslation());
        tvDefault.setText(currentWord.getDefaultTranslation());

        if (currentWord.hasImage()) {
            imageView.setImageResource(currentWord.getImageResourceId());
            // make sure the view is visible
            imageView.setVisibility(View.VISIBLE);
        } else {
            // otherwise hide the image view
            imageView.setVisibility(View.GONE);
        }

        // set the theme color for the list item
        View textContainer = listItemView.findViewById(R.id.text_container);
        // find the color that the resource id maps to
        int color = ContextCompat.getColor(getContext(), mColorResourceId);
        // Set the background color of the text_container View
        textContainer.setBackgroundColor(color);

        // Return the completed view (the whole list item layout) to render on screen
        return listItemView;
    }
}
