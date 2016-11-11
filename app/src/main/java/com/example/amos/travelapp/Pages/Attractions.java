package com.example.amos.travelapp.Pages;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.amos.travelapp.R;
import com.example.amos.travelapp.Resources.AttractionDataProvider;
import com.example.amos.travelapp.Resources.AttractionsAdapter;

public class Attractions extends AppCompatActivity {
    ListView listview;
    int[] picture_resource={R.drawable.mbs,R.drawable.sf,R.drawable.vc,R.drawable.rws,R.drawable.btrt,R.drawable.zoo};
    String[] ratings;
    String[] attraction_name;
    String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attractions);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Attractions");
        listview = (ListView)findViewById(R.id.list_view);
        ratings = getResources().getStringArray(R.array.ratings);
        AttractionsAdapter adapter;
        attraction_name = getResources().getStringArray(R.array.attractions);
        int i =0;
        adapter=new AttractionsAdapter(getApplicationContext(),R.layout.row_layout);
        listview.setAdapter(adapter);
        for(String names:attraction_name){
            AttractionDataProvider dataProvider = new AttractionDataProvider(picture_resource[i],
                    names,ratings[i]);
            adapter.add(dataProvider);
            i++;
        }
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                location=attraction_name[position];
                switch (location){
                    case "Marina Bay Sands":
                        Intent intent = new Intent(getApplication(), Attractions_MBS.class);
                        startActivity(intent);
                        break;
                    case "Singapore Flyer":
                        Intent intent1 = new Intent(getApplication(), Attractions_SF.class);
                        startActivity(intent1);
                        break;
                    case "Vivo City":
                        Intent intent2 = new Intent(getApplication(), Attractions_VC.class);
                        startActivity(intent2);
                        break;
                    case "Resorts World Sentosa":
                        Intent intent3 = new Intent(getApplication(), Attractions_RWS.class);
                        startActivity(intent3);
                        break;
                    case "Buddha Tooth Relic Temple":
                        Intent intent4 = new Intent(getApplication(), Attractions_BTRT.class);
                        startActivity(intent4);
                        break;
                    case "Singapore Zoo":
                        Intent intent5 = new Intent(getApplication(), Attractions_ZOO.class);
                        startActivity(intent5);
                        break;

                }
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item){
        super.onBackPressed();
        return true;

    }
}
