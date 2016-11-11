package com.example.amos.travelapp.Pages;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.amos.travelapp.R;

public class Attractions_SF extends AppCompatActivity {

    TextView details;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Singapore Flyer");
        setContentView(R.layout.activity_attractions__sf);
        details= (TextView)findViewById(R.id.SFdetail);
        details.setText("The Singapore Flyer offers 360-degree city views, " +
                "a panorama that stretches to parts of Malaysia and Indonesia on a clear day. " +
                "Sticking out among the skyscrapers in the Singapore skyline, the Singapore Flyer is no ordinary orb. " +
                "Instead, this is where to go for the most magnificent views of the city. More importantly, it is high on thrills for tourists and locals alike. " +
                "Step into one of the 28 fully air-conditioned glass capsules, and be transported on a 30-minute journey of stunning day and night scenes.");
    }
    public boolean onOptionsItemSelected(MenuItem item){
        super.onBackPressed();
        return true;

    }
}
