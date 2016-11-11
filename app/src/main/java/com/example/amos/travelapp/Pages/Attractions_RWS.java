package com.example.amos.travelapp.Pages;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.amos.travelapp.R;

public class Attractions_RWS extends AppCompatActivity {

    TextView details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Resorts World Sentosa");
        setContentView(R.layout.activity_attractions__rws);
        details = (TextView) findViewById(R.id.RWSdetail);
        details.setText("Experience endless excitement at Resorts World Sentosa with a variety of attractions and entertainment hot spots. With no lack of things to do, be prepared for a wild time at Resorts World™ Sentosa, an integrated resort experience designed to keep the whole family enthralled.\n" +
                "\n" +
                "The highlight for the kids will be Universal Studios Singapore™ theme park, where you will enter the world of the silver screen in seven movie-themed zones. These are filled with rides, outdoor shows and “celebrity sightings”. ");
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        return true;

    }
}