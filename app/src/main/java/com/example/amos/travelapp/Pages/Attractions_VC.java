package com.example.amos.travelapp.Pages;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.amos.travelapp.R;

public class Attractions_VC extends AppCompatActivity {

    TextView details;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Vivo City");
        setContentView(R.layout.activity_attractions__vc);
        details= (TextView)findViewById(R.id.VCdetail);
        details.setText("VivoCity is the largest shopping mall in Singapore. " +
                "Located in the HarbourFront precinct, it was designed by the Japanese architect Toyo Ito. " +
                "Its name is derived from the word vivacity. According to Mapletree chairman Edmund Cheng, VivoCity \"evokes a lifestyle experience that is modern, stimulating and accessible to everyone, " +
                "a place bubbling with energy and flowing with vitality\".");
    }
    public boolean onOptionsItemSelected(MenuItem item){
        super.onBackPressed();
        return true;

    }
}
