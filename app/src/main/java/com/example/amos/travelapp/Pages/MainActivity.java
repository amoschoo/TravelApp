package com.example.amos.travelapp.Pages;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.amos.travelapp.Backend.Location;
import com.example.amos.travelapp.Backend.Solvers;
import com.example.amos.travelapp.Backend.Word;
import com.example.amos.travelapp.Backend.Wordmap;
import com.example.amos.travelapp.R;
import com.example.amos.travelapp.Resources.GlobalValue;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    private GoogleMap mMap;
    PolylineOptions lines;
    ArrayList<String> locationlist;
    HashMap<String,LatLng> hashmap= new HashMap<>();
    LatLng MBSlatlng;
    private Wordmap dict;
    HashMap<String, Location> library;
    String method;
    ArrayList<String> all_Locations = new ArrayList<String>();
    AutoCompleteTextView location_finder;
    int walk = 0;
    int pt = 0;
    int taxi = 0;
    Button buttonMBS,buttonRWS,buttonBTRT,buttonVC,buttonZOO,buttonSF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        method="non-optimised";
        library = readLibrary("LocList.txt");
        dict = new Wordmap();
        dict.generateMap();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        all_Locations.add("Marina Bay Sands");
        all_Locations.add("Singapore Flyer");
        all_Locations.add("Resorts World Sentosa");
        all_Locations.add("Vivo City");
        all_Locations.add("Buddha Tooth Relic Temple");
        all_Locations.add("Zoo");
        location_finder = (AutoCompleteTextView)findViewById(R.id.SearchBox);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.simple_list_item_1,all_Locations);
        location_finder.setAdapter(adapter);
        buttonMBS = (Button)findViewById(R.id.MBS_Location);
        buttonMBS.getBackground().setColorFilter(Color.parseColor("#9f9895"), PorterDuff.Mode.MULTIPLY);
        buttonBTRT = (Button)findViewById(R.id.BTRT_Location);
        buttonRWS = (Button)findViewById(R.id.RWS_Location);
        buttonSF = (Button)findViewById(R.id.SF_Location);
        buttonVC = (Button)findViewById(R.id.VC_Location);
        buttonZOO = (Button)findViewById(R.id.ZOO_Location);
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        try{
            plotPolyLine();
        }catch(Exception e) {
            try {
                mMap.addMarker(new MarkerOptions().position(MBSlatlng).title("MarinaBaySands").icon(BitmapDescriptorFactory.fromResource(R.drawable.hotel)));
            }catch (Exception Ex){
                Toast.makeText(getApplicationContext(),"GPS Location Error",Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override //Open Navigation Drawer
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //deleting all location/marker & reset all list, followed by placing the MarinaBaySands Marker
        if (id == R.id.action_clear) {
            try{
            clearmap();
            }
            catch(Exception e){
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        else if (id==R.id.optimisedRoute){
            try {
                method = "optimised";
                plotPolyLine();
            }catch (Exception e){
                mMap.addMarker(new MarkerOptions().position(MBSlatlng).title("MarinaBaySands").icon(BitmapDescriptorFactory.fromResource(R.drawable.hotel)));
                Toast.makeText(getApplicationContext(),"No route detected",Toast.LENGTH_SHORT).show();
            }
        }
        else if (id==R.id.defaultRoute){
            try {
                method = "non-optimised";
                Toast.makeText(getApplicationContext(),"Default route...",Toast.LENGTH_SHORT).show();
                plotPolyLine();
            }catch(Exception e){
                mMap.addMarker(new MarkerOptions().position(MBSlatlng).title("MarinaBaySands").icon(BitmapDescriptorFactory.fromResource(R.drawable.hotel)));
                Toast.makeText(getApplicationContext(),"No route detected",Toast.LENGTH_SHORT).show();
            }
        }
        else if (id==R.id.directions){
            Intent intent = new Intent(this,RouteDetails.class);
            startActivity(intent);
        }
        else if (id==R.id.wideview){
            LatLng Singapore = new LatLng(1.34,103.8);
            float zoomLevel = (float) 11.0;
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Singapore, zoomLevel));
        }
        else if (id==R.id.satelliteview){
            mMap.setMapType(mMap.MAP_TYPE_SATELLITE);
        }
        else if (id==R.id.normalview){
            mMap.setMapType(mMap.MAP_TYPE_NORMAL);
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_route) {
            Intent intent = new Intent(this, RouteDetails.class);
            startActivity(intent);
        } else if (id == R.id.nav_budget) {
            Intent intent = new Intent(this, Budget.class);
            startActivity(intent);
        } else if (id == R.id.nav_currency) {
            Intent intent = new Intent(this, Currency.class);
            startActivity(intent);
        } else if (id == R.id.nav_weather) {
            Intent intent = new Intent(this, Weather.class);
            startActivity(intent);
        } else if (id == R.id.nav_attraction) {
            Intent intent = new Intent(this, Attractions.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Starting google map
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng Singapore = new LatLng(1.3,103.8);
        float zoomLevel = (float) 10.0;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Singapore, zoomLevel));
        mMap.setMyLocationEnabled(true);
//      Adding MBS as starting point
        List<Address> addresslist = null;
        Geocoder geocoder = new Geocoder(this);
        try {
            addresslist = geocoder.getFromLocationName("Marina Bay Sands" ,1);
            Address address = addresslist.get(0);
            LatLng latlng = new LatLng(address.getLatitude(),address.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latlng).title("MarinaBaySands").icon(BitmapDescriptorFactory.fromResource(R.drawable.hotel)));
            locationlist=new ArrayList<String>();
            MBSlatlng=latlng; //set MBS latlng
            hashmap.put("MarinaBaySands",latlng);
            lines = new PolylineOptions();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //Search for location using the search bar
    public void onSearch(View view) {
        try {
            hide_keyboard(this);
            String location0 = location_finder.getText().toString();

            //Words check
            Word word = new Word(location0);
            String location = word.spellcheck(dict);
            //Words check

            if (locationlist.contains(location)) {//Check if location alrady added in the map
                Toast.makeText(getApplicationContext(), "Location already inserted", Toast.LENGTH_SHORT).show();
            }else if (location.equals("Marina Bay Sands")){
                Toast.makeText(getApplicationContext(), "Location already inserted", Toast.LENGTH_SHORT).show();
            } else {
                if (location != null || !location.equals("")) {
                    Geocoder geocoder = new Geocoder(this);
                    List<Address> addresslist = null;
                    try {
                        addresslist = geocoder.getFromLocationName(location, 1, mMap.getMyLocation().getLatitude() - 0.5, mMap.getMyLocation().getLongitude() - 0.5, mMap.getMyLocation().getLatitude() + 0.5, mMap.getMyLocation().getLongitude() + 0.5);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "No such place near by", Toast.LENGTH_SHORT).show();

                    }
                    try {
                        Address address = addresslist.get(0);
                        LatLng latlng = new LatLng(address.getLatitude(), address.getLongitude());
                        float zoomLevel = (float) 14.0;
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, zoomLevel));
                        //if location is part of the attraction list then it will add it to the route
                        if (all_Locations.contains(location)){
                            locationlist.add(location);
                            hashmap.put(location.replace(" ", ""), latlng);
                            method="non-optimised";
                            plotPolyLine();
                            Toast.makeText(getApplication(), location + " was added", Toast.LENGTH_SHORT).show();
                        }else{ //if location not part of the list, only marker is add
                            mMap.addMarker(new MarkerOptions().position(latlng).title(location));
                            Toast.makeText(getApplicationContext(), "Not an attraction", Toast.LENGTH_SHORT).show();
                        }
                        //                String s = "google.navigation:q="+location;
                        //                Uri gmmIntentUri = Uri.parse(s);
                        //                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        //                mapIntent.setPackage("com.google.android.apps.maps");
                        //                startActivity(mapIntent);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "INVALID REQUEST", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "GPS Location Error", Toast.LENGTH_SHORT).show();
        }
    }

    // When the image button is pressed it will input the Location
    public void onButtonPress(String location){
        try {
            if (locationlist.contains(location)) {//Check if location alrady added in the map
                Toast.makeText(getApplicationContext(), "Location already inserted", Toast.LENGTH_SHORT).show();
            } else {
                if (location != null || !location.equals("")) {
                    Geocoder geocoder = new Geocoder(this);

                    List<Address> addresslist = null;
                    try {
                        addresslist = geocoder.getFromLocationName(location, 1, mMap.getMyLocation().getLatitude() - 0.5, mMap.getMyLocation().getLongitude() - 0.5, mMap.getMyLocation().getLatitude() + 0.5, mMap.getMyLocation().getLongitude() + 0.5);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Try Again", Toast.LENGTH_SHORT).show();

                    }
                    try {
                        //Add location into the route
                        Address address = addresslist.get(0);
                        LatLng latlng = new LatLng(address.getLatitude(), address.getLongitude());
                        float zoomLevel = (float) 14.0;
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, zoomLevel));
                        locationlist.add(location);
                        hashmap.put(location.replace(" ", ""), latlng);
                        method="non-optimised";
                        plotPolyLine();
                        Toast.makeText(getApplication(), location + " was added", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "INVALID REQUEST", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        } catch (Exception e){
            Toast.makeText(getApplicationContext(), "GPS Location Error", Toast.LENGTH_SHORT).show();
        }
    }


    //Create the route & lines in the map
    public void plotPolyLine(){
        PolylineOptions[] PolylineArray = {new PolylineOptions(),new PolylineOptions(),new PolylineOptions(),new PolylineOptions(),new PolylineOptions(),new PolylineOptions(),new PolylineOptions(),new PolylineOptions(),new PolylineOptions(),new PolylineOptions(),new PolylineOptions(),new PolylineOptions(),new PolylineOptions(),new PolylineOptions(),
                new PolylineOptions(),new PolylineOptions(),new PolylineOptions(),new PolylineOptions(),new PolylineOptions(),new PolylineOptions(),new PolylineOptions(),new PolylineOptions(),new PolylineOptions(),new PolylineOptions(),new PolylineOptions(),new PolylineOptions(),new PolylineOptions(),new PolylineOptions(),
                new PolylineOptions(),new PolylineOptions(),new PolylineOptions(),new PolylineOptions(),new PolylineOptions(),new PolylineOptions(),new PolylineOptions(),new PolylineOptions(),new PolylineOptions(),new PolylineOptions(),new PolylineOptions(),new PolylineOptions(),new PolylineOptions(),new PolylineOptions()};
//        mMap.clear();
        lines = new PolylineOptions();
        int k=0;
        String start = "Marina Bay Sands";
        double budget=((GlobalValue)getApplicationContext()).getBudget();
        Object[] object;
        if (method.equals("optimised")){
            Toast.makeText(getApplicationContext(),"Optimising route...",Toast.LENGTH_LONG).show();
            object = Solvers.bruteForce(start, locationlist, library, budget);
            method="non-optimised";
        }else {
            object = Solvers.twoOpt(start, locationlist, library, budget);
        }
        LinkedList<String> Newlocationlist = (LinkedList<String>) object[1];
        HashMap<String, Integer> TransportType = (HashMap<String, Integer>) object[2];
        String string = (String) object[0];
        ((GlobalValue)getApplicationContext()).setRouteDetails(string);
        LatLng previous = MBSlatlng;
        String previousLocation="MarinaBaySands";
        mMap.clear();
        int Type;
        for (String L:Newlocationlist){
            Type = TransportType.get(previousLocation);
            LatLng LL =hashmap.get(L);
            switch (Type){ //Type of Transport
                case 0: //Walk
                    PolylineOptions lineG = PolylineArray[k];
                    if (walk==1) {
                        lineG.add(previous).color(Color.GREEN).width(20);
                        lineG.add(LL).color(Color.GREEN).width(20);
                    } else{
                        lineG.add(previous).color(Color.GREEN);
                        lineG.add(LL).color(Color.GREEN);
                    }
                    Polyline polylineG = mMap.addPolyline(lineG);
                    break;
                case 1: //Public Transport
                    PolylineOptions lineB = PolylineArray[k];
                    if (pt==1) {
                        lineB.add(previous).color(Color.BLUE).width(20);
                        lineB.add(LL).color(Color.BLUE).width(20);
                    } else{
                        lineB.add(previous).color(Color.BLUE);
                        lineB.add(LL).color(Color.BLUE);
                    }
                    Polyline polylineB = mMap.addPolyline(lineB);
                    break;
                case 2: //Taxi
                    PolylineOptions lineR = PolylineArray[k];
                    if(taxi==1) {
                        lineR.add(previous).color(Color.RED).width(20);
                        lineR.add(LL).color(Color.RED).width(20);
                    } else {
                        lineR.add(previous).color(Color.RED);
                        lineR.add(LL).color(Color.RED);
                    }
                    Polyline polylineR = mMap.addPolyline(lineR);
                    break;
            }
            if (LL==MBSlatlng){
                mMap.addMarker(new MarkerOptions().position(MBSlatlng).title("MarinaBaySands").icon(BitmapDescriptorFactory.fromResource(R.drawable.hotel)));
            } else if (k==1) {
                mMap.addMarker(new MarkerOptions().position(LL).title(L).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker1)));
            }else if (k==2) {
                mMap.addMarker(new MarkerOptions().position(LL).title(L).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker2)));
            }
            else if (k==3) {
                mMap.addMarker(new MarkerOptions().position(LL).title(L).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker3)));
            }
            else if (k==4) {
                mMap.addMarker(new MarkerOptions().position(LL).title(L).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker4)));
            }
            else if (k==5) {
                mMap.addMarker(new MarkerOptions().position(LL).title(L).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker5)));
            }
            else if (k==6) {
                mMap.addMarker(new MarkerOptions().position(LL).title(L).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker6)));
            } else{
                mMap.addMarker(new MarkerOptions().position(LL).title(L));
            }
            k++;
            previous=LL;
            previousLocation=L;
        }
        walk=0;
        pt=0;
        taxi=0;

    }


    //When MBS image button is pressed
    public void locationMBS(View v){
        Toast.makeText(getApplicationContext(), "This is the Starting Location", Toast.LENGTH_SHORT).show();
    }
    //When SF image button is pressed
    public void locationSF(View v){
        String Local = "Singapore Flyer";
        if (locationlist.contains(Local)){
            buttonSF.getBackground().clearColorFilter();
            locationlist.remove(Local);
            try{
            plotPolyLine();
            } catch(Exception e){
                clearmap();
            }
        } else {
            try {
                onButtonPress("Singapore Flyer");
                buttonSF.getBackground().setColorFilter(Color.parseColor("#9f9895"), PorterDuff.Mode.MULTIPLY);
            } catch (Exception e) {
                buttonSF.getBackground().clearColorFilter();
            }
        }
    }
    //When VC image button is pressed
    public void locationVC(View v){
        String Local = "Vivo City";
        if (locationlist.contains(Local)){
            buttonVC.getBackground().clearColorFilter();
            locationlist.remove(Local);
            try{
                plotPolyLine();
            } catch(Exception e){
                clearmap();
            }
        } else {
            try {
                onButtonPress("Vivo City");
                buttonVC.getBackground().setColorFilter(Color.parseColor("#9f9895"), PorterDuff.Mode.MULTIPLY);
            } catch (Exception e) {
                buttonVC.getBackground().clearColorFilter();
            }
        }
    }

    //When RWS image button is pressed
    public void locationRWS(View v){
        String Local = "Resorts World Sentosa";
        if (locationlist.contains(Local)){
            buttonRWS.getBackground().clearColorFilter();
            locationlist.remove(Local);
            try{
                plotPolyLine();
            } catch(Exception e){
                clearmap();
            }
        } else {
            try {
                onButtonPress("Resorts World Sentosa");
                buttonRWS.getBackground().setColorFilter(Color.parseColor("#9f9895"), PorterDuff.Mode.MULTIPLY);
            } catch (Exception e) {
                buttonRWS.getBackground().clearColorFilter();
            }
        }
    }
    //When BTRT image button is pressed
    public void locationBTRT(View v){
        String Local = "Buddha Tooth Relic Temple";
        if (locationlist.contains(Local)){
            buttonBTRT.getBackground().clearColorFilter();
            locationlist.remove(Local);
            try{
                plotPolyLine();
            } catch(Exception e){
                clearmap();
            }
        } else {
            try {
                onButtonPress("Buddha Tooth Relic Temple");
                buttonBTRT.getBackground().setColorFilter(Color.parseColor("#9f9895"), PorterDuff.Mode.MULTIPLY);
            } catch (Exception e) {
                buttonBTRT.getBackground().clearColorFilter();
            }
        }
    }
    //When Zoo image button is pressed
    public void locationZOO(View v){
        String Local = "Zoo";
        if (locationlist.contains(Local)){
            buttonZOO.getBackground().clearColorFilter();
            locationlist.remove(Local);
            try{
                plotPolyLine();
            } catch(Exception e){
                clearmap();
            }
        } else {
            try {
                onButtonPress("Zoo");
                buttonZOO.getBackground().setColorFilter(Color.parseColor("#9f9895"), PorterDuff.Mode.MULTIPLY);
            } catch (Exception e) {
                buttonZOO.getBackground().clearColorFilter();
            }
        }
    }
    //WalkimageButton pressed
    public void walkbtn(View v){
        walk = 1;
        try{
            plotPolyLine();
            Toast.makeText(getApplicationContext(),"Walk",Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            Toast.makeText(getApplicationContext(),"No route detected",Toast.LENGTH_SHORT).show();
            walk=0;
        }
    }
    //PTimageButton pressed
    public void ptbtn(View v){
        pt = 1;
        try{
            plotPolyLine();
            Toast.makeText(getApplicationContext(),"Public Transport",Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            Toast.makeText(getApplicationContext(),"No route detected",Toast.LENGTH_SHORT).show();
            pt=0;
        }
    }
    //TaxiimageButton pressed
    public void taxibtn(View v){
        taxi = 1;
        try{
            plotPolyLine();
            Toast.makeText(getApplicationContext(),"Taxi",Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            Toast.makeText(getApplicationContext(),"No route detected",Toast.LENGTH_SHORT).show();
            taxi=0;
        }
    }


    public void cancelbtn(View v){
        hide_keyboard(this);
        RelativeLayout RL = (RelativeLayout)findViewById(R.id.budgetLayout);
        RelativeLayout BL = (RelativeLayout)findViewById(R.id.backgroundLayout);
        RL.setVisibility(View.GONE);
        BL.setVisibility(View.GONE);
    }

    //Setting the budget to Global Value
    public void SetBudget(View v){
        EditText BudgetET = (EditText)findViewById(R.id.BudgeteditText);
        Double Budget;
        if (BudgetET.getText().toString().equals("")){
            Budget=0.0;
        }else {
            Budget = Double.parseDouble(BudgetET.getText().toString());
        }
        ((GlobalValue)getApplicationContext()).setBudget(Budget);
        Toast.makeText(this,"Budget set to SGD "+((GlobalValue)getApplicationContext()).getBudget(),Toast.LENGTH_SHORT).show();
        hide_keyboard(this);
        RelativeLayout RL = (RelativeLayout)findViewById(R.id.budgetLayout);
        RelativeLayout BL = (RelativeLayout)findViewById(R.id.backgroundLayout);
        RL.setVisibility(View.GONE);
        BL.setVisibility(View.GONE);

    }


    //Get data from file
    public HashMap<String, Location> readLibrary(String filename) {
        try {
            InputStream inputStream = getResources().getAssets().open(filename);
            HashMap<String, Location> allLocations = new HashMap<>();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            String prev = "ToDelete";
            Location newLoc = new Location("blank");
            while ((line = bufferedReader.readLine()) != null){
                line = line.trim();
                String[] things = line.split("\\s+");
                if (!prev.equals(things[0])) {
                    allLocations.put(prev, newLoc);
                    prev = things[0];
                    newLoc = Location.makeLocation(things[0]);
                }
                double[] time = new double[3];
                double[] cost = new double[3];
                time[0] = Double.parseDouble(things[2]);
                time[1] = Double.parseDouble(things[3]);
                time[2] = Double.parseDouble(things[4]);
                cost[0] = Double.parseDouble(things[5]);
                cost[1] = Double.parseDouble(things[6]);
                cost[2] = Double.parseDouble(things[7]);
                newLoc.addCost(things[1],cost);
                newLoc.addTime(things[1],time);
            }
            allLocations.put(prev, newLoc);
            allLocations.remove("ToDelete");
            return allLocations;
        }
        catch (Exception e){
            Log.i("File","error");
            buttonBTRT.getBackground().clearColorFilter();
            buttonRWS.getBackground().clearColorFilter();
            buttonVC.getBackground().clearColorFilter();
            buttonZOO.getBackground().clearColorFilter();
            buttonSF.getBackground().clearColorFilter();

        }
        return new HashMap<>();
    }

    //hiding keyboard
    public static void hide_keyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if(view == null) {
            view = new View(activity);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public void clearmap(){
        mMap.clear();
        lines=new PolylineOptions();
        LatLng latlng = MBSlatlng;
        mMap.addMarker(new MarkerOptions().position(latlng).title("MarinaBaySands").icon(BitmapDescriptorFactory.fromResource(R.drawable.hotel)));
        locationlist.clear();
        lines = new PolylineOptions();
        hashmap.put("MarinaBaySands", latlng);
        buttonBTRT.getBackground().clearColorFilter();
        buttonRWS.getBackground().clearColorFilter();
        buttonVC.getBackground().clearColorFilter();
        buttonZOO.getBackground().clearColorFilter();
        buttonSF.getBackground().clearColorFilter();
    }
}