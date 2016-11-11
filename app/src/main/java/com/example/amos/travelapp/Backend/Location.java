package com.example.amos.travelapp.Backend;

import java.util.HashMap;

/**
 * Created by hj on 2/11/15.
 */
public class Location {
    private String name;
    private HashMap<String, double[]> time;
    private HashMap<String, double[]> cost;
    public Location(String name){
        this.name = name;
        this.cost = new HashMap<>();
        this.time = new HashMap<>();
    }

    public static Location makeLocation(String name){
        return new Location(name);
    }

    public void addCost(String other, double[] cost){
        this.cost.put(other, cost);
    }

    public void addTime(String other, double[] time){
        this.time.put(other, time);
    }

    public String getName(){
        return name;
    }

    public double[] getCost(String other){
        return this.cost.get(other);
    }

    public double[] getTime(String other){
        return this.time.get(other);
    }

    public String toString(){
        return name;
    }
}
