package com.example.amos.travelapp.Resources;

import android.app.Application;

/**
 * Created by Amos on 2/11/15.
 */
public class GlobalValue extends Application {
    double Budget = 0.0;
    String RouteDetails = "No Route Details";
    public double getBudget(){return this.Budget;}
    public void setBudget(double num){this.Budget=num;}
    public String getRouteDetails(){return this.RouteDetails;}
    public void setRouteDetails(String s){this.RouteDetails=s;}
}
