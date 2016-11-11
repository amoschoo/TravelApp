package com.example.amos.travelapp.Pages;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.amos.travelapp.R;

public class Attractions_MBS extends AppCompatActivity {

    TextView details;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Marina Bay Sands");
        setContentView(R.layout.activity_attractions__mbs);
        details= (TextView)findViewById(R.id.MBSdetail);
        details.setText("Marina Bay Sands is an integrated resort fronting Marina Bay in Singapore. " +
                "With the casino complete, the resort includes a 2,561-room hotel, a 1,300,000-square-foot (120,000 m2) convention-exhibition centre, the 800,000-square-foot (74,000 m2) " +
                "The Shoppes at Marina Bay Sands mall, a museum, two large theatres, seven \"celebrity chef\" restaurants, two floating Crystal Pavilions, a skating rink, and the world's largest atrium casino with 500 tables and 1,600 slot machines.");
    }
    public boolean onOptionsItemSelected(MenuItem item){
        super.onBackPressed();
        return true;

    }
}
