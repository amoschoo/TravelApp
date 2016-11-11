package com.example.amos.travelapp.Pages;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.amos.travelapp.R;

public class Attractions_BTRT extends AppCompatActivity {

    TextView details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Buddhe Tooth Relic Temple");
        setContentView(R.layout.activity_attractions__btrt);
        details = (TextView) findViewById(R.id.BTRTdetail);
        details.setText("This Tang-styled Chinese Buddhist temple in Chinatown gets its name from what the Buddhists regard as the Sacred Buddha Tooth Relic. " +
                "The Buddha Tooth Relic Temple & Museum gets its name from what the Buddhists regard as the Sacred Buddha Tooth Relic. " +
                "Built in 2007, the Buddha Tooth Relic Temple & Museum may be a recent addition to the historic Chinatown district. " +
                "Yet the temple is definitely worth a visit, with its rich features and exhibits on Buddhist art and culture.");
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        return true;

    }
}
