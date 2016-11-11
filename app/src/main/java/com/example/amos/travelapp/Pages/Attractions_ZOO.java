package com.example.amos.travelapp.Pages;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.amos.travelapp.R;

public class Attractions_ZOO extends AppCompatActivity {

    TextView details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Singapore Zoo");
        setContentView(R.layout.activity_attractions__zoo);
        details = (TextView) findViewById(R.id.ZOOdetail);
        details.setText("Singapore’s zoo is a beautiful, award-winning wildlife park, where animals can roam freely in their natural habitats. " +
                "At this beautifully designed zoo where animals live in naturalistic habitats, you can trek along foot tracks or hop on to a guided tram to see natural wonders like the endangered white tiger and the world’s largest collection of proboscis monkeys. " +
                "The zoo is ideal for families. The kids will be kept busy with activities like the Kidzranger Tour, which lets them try their hand at being a zoo keeper.");
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        return true;

    }
}
