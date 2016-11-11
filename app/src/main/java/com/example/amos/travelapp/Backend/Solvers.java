package com.example.amos.travelapp.Backend;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.google.android.gms.wearable.Asset;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by hj on 30/10/15.
 */
public class Solvers {

    private static HashMap<String, Integer> transportMode;

    public static boolean isValid(HashMap<String, Location> library, ArrayList<String> toCheck){
        for (String item: toCheck){
            if (!library.containsKey(item)){
                return false;
            }
        }
        return true;
    }

    public static HashMap<String, Location> readLibrary(String filename) {
        try {
            HashMap<String, Location> allLocations = new HashMap<>();
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
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
        catch (FileNotFoundException e){
            System.out.println("File not found!");
        }
        catch (IOException e){
            System.out.println("Error reading file!");
        }
        return new HashMap<>();
    }

    public static LinkedList<String> initial(String start, ArrayList<String> toGo){
        LinkedList<String> fin = new LinkedList<>();
        for (String place: toGo) {
            fin.add(place);
        }
        fin.add(0, start);
        fin.add(start);
        return fin;
    }

    public static double calcPathTime(LinkedList<String> list, HashMap<String, Location> library){
        int n = list.size();
        double res = 0;
        for (int i = 0; i < n - 1; i++){
            Location here = library.get(list.get(i));
            String there = list.get(i+1);
            double[] times = here.getTime(there);
            double time = times[transportMode.get(here.getName())];
            res += time;
        }
        return res;
    }

    public static double calcPathCost(LinkedList<String> list, HashMap<String, Location> library){
        int n = list.size();
        double res = 0;
        for (int i = 0; i < n - 1; i++){
            Location here = library.get(list.get(i));
            String there = list.get(i+1);
            double[] costs = here.getCost(there);
            double cost = costs[transportMode.get(here.getName())];
            res += cost;
        }
        return res;
    }

    public static Object[] twoOpt(String start, ArrayList<String> originalToGo, HashMap<String, Location> library, double budget) {
        try {
            ArrayList<String> toGo = (ArrayList) originalToGo.clone();
            budget *= 100;
            int n = toGo.size() + 2;
            HashMap<String, String> converter  = new HashMap<>();
            converter.put(start.replaceAll("\\s",""), start);
            start = start.replaceAll("\\s","");
            for (int i = 0; i < n - 2; i++){
                converter.put(toGo.get(i).replaceAll("\\s", ""), toGo.get(i));
                toGo.set(i, toGo.get(i).replaceAll("\\s", ""));
            }
            if (!isValid(library, toGo)){
                throw new invalidLocList();
            }
            transportMode = new HashMap<>();
            transportMode.put(start, 1);
            for (String place: toGo){
                transportMode.put(place, 1);
            }
            LinkedList<String> in = initial(start, toGo);
            int rev = 0;
            for (int i = 1; i < n - 2; i++) {
                for (int j = i + 1; j < n - 1; j++) {
                    LinkedList<String> first = new LinkedList<>();
                    LinkedList<String> intermediate = new LinkedList<>();
                    String[] switchThese = {in.get(i), in.get(j)};
                    double currentTime = calcPathTime(in, library);
                    for (int q = 0; q < n; q++) {
                        if (in.get(q).equals(switchThese[0])) {
                            rev = 1;
                        }
                        if (rev == 1) {
                            intermediate.add(0, in.get(q));
                            if (in.get(q).equals(switchThese[1])) {
                                rev = 0;
                                first.addAll(intermediate);
                            }
                        } else {
                            first.add(in.get(q));
                        }
                    }
                    double newTime = calcPathTime(first, library);
                    if (newTime < currentTime) {
                        in = (LinkedList<String>) first.clone();
                    }
                }
            }
            double currentCost = calcPathCost(in, library);
            int change;
            ArrayList<String[]> minimize = new ArrayList<>();
            ArrayList<String[]> maximize = new ArrayList<>();
            if (currentCost <= budget){
                change = 2;
                for (int i = 0; i < n - 1; i++){
                    String[] addThis = {in.get(i),in.get(i+1)};
                    minimize.add(addThis);
                }
                Collections.sort(minimize, new TaxiPubComp(library));
            }
            else{
                change = 0;
                for (int i = 0; i < n - 1; i++){
                    String[] addThis = {in.get(i),in.get(i+1)};
                    maximize.add(addThis);
                }
                Collections.sort(maximize, new FootPubComp(library));
            }
            int i = n - 2;
            while (i > -1){
                if (change == 0) {
                    transportMode.put(maximize.get(i)[0], change);
                }
                else{
                    transportMode.put(minimize.get(i)[0], change);
                }
                double newCost = calcPathCost(in, library);
                if (newCost >= budget && change == 2){
                    transportMode.put(minimize.get(i)[0],1);
                }
                if (newCost <= budget && change == 0){
                    i = -n;
                }
                i--;
            }
            double time = (calcPathTime(in, library));
            double mins = time % 60;
            double hours = (time - mins) / 60;
            String hourstring;
            String minutestring;
            if (hours == 1) {
                hourstring = Double.toString(hours) + " hour and ";
            }
            else{
                hourstring = Double.toString(hours) + " hours and ";
            }
            if (mins == 1) {
                minutestring = Double.toString(mins) + " minute";
            }
            else{
                minutestring = Double.toString(mins) + " minutes";
            }
            String instructions = "";
            instructions += ("Journey takes " + hourstring + minutestring) + "\n";
            instructions += ("Journey costs $" + Double.toString(calcPathCost(in, library)/100)) + "\n"+"\n";
            String[] modes = {"Walk from ", "Take public transport from ", "Take a taxi from "};
            for (i = 0; i < n - 1; i++){
                instructions += modes[transportMode.get(in.get(i))] + converter.get(in.get(i)) + " to " + converter.get(in.get(i + 1)) + "\n";
            }
            Object[] returnList = {instructions, in, transportMode};
            return returnList;
        }
        catch (invalidLocList e){
            Object[] returnList = {"Invalid Location List!", new ArrayList<>(), new HashMap<>()};
            return returnList;
        }
    }

    public static Object[] bruteForce(String start, ArrayList<String> originalToGo, HashMap<String, Location> library, double budget) {
        try {
            ArrayList<String> toGo = (ArrayList) originalToGo.clone();
            budget *= 100;
            int n = toGo.size() + 2;
            HashMap<String, String> converter  = new HashMap<>();
            converter.put(start.replaceAll("\\s",""), start);
            start = start.replaceAll("\\s","");
            for (int i = 0; i < n - 2; i++){
                converter.put(toGo.get(i).replaceAll("\\s", ""), toGo.get(i));
                toGo.set(i, toGo.get(i).replaceAll("\\s", ""));
            }
            if (!isValid(library, toGo)){
                throw new invalidLocList();
            }
            transportMode = new HashMap<>();
            transportMode.put(start, 0);
            for (String place: toGo){
                transportMode.put(place, 0);
            }
            LinkedList<String> in = initial(start, toGo);
            LinkedList<String> fin = (LinkedList<String>) in.clone();
            double lowest = calcPathTime(fin, library);
            double bestPrice = calcPathCost(fin, library);
            boolean endInner = false;
            boolean endOuter = false;
            HashMap<String, Integer> how = (HashMap<String, Integer>) transportMode.clone();
            int carry;
            int pointer;
            int modeCarry;
            int modePointer;
            int[] modeCounters = new int[n - 1];
            for (int i = 0; i < n - 1; i++) {
                modeCounters[i] = 0;
            }
            int[] counters = new int[n - 2];
            while (!endOuter) {
                modeCarry = 1;
                modePointer = 0;
                while (modeCarry != 0) {
                    if (modeCounters[modePointer] == 2) {
                        modeCounters[modePointer] = 0;
                        modePointer += 1;
                        if (modePointer == n - 1) {
                            modeCarry = 0;
                            endOuter = true;
                        }
                    } else {
                        modeCounters[modePointer] += 1;
                        for (int i = 0; i < n - 1; i++){
                            transportMode.put(in.get(i),modeCounters[i]);
                        }
                        for (int i = 0; i < n - 2; i++) {
                            counters[i] = 0;
                        }
                        while (!endInner) {
                            carry = 1;
                            pointer = 0;
                            while (carry != 0) {
                                if (counters[pointer] == n - 3 - pointer) {
                                    counters[pointer] = 0;
                                    pointer += 1;
                                    if (pointer >= n - 3) {
                                        carry = 0;
                                        endInner = true;
                                    }
                                } else {
                                    counters[pointer] += 1;
                                    carry = 0;
                                }
                            }
                            LinkedList<String> original = (LinkedList<String>) in.clone();
                            LinkedList<String> newPath = constructList(counters, original);
                            double timeCheck = calcPathTime(newPath, library);
                            double costCheck = calcPathCost(newPath, library);
                            if (timeCheck < lowest && costCheck <= budget || timeCheck == lowest && costCheck < bestPrice) {
                                fin = (LinkedList<String>) newPath.clone();
                                how = (HashMap<String, Integer>) transportMode.clone();
                                lowest = timeCheck;
                                bestPrice = costCheck;
                            }
                        }
                        endInner = false;
                        modeCarry = 0;
                    }
                }
            }
            transportMode = how;
            double time = (calcPathTime(fin, library));
            double mins = time % 60;
            double hours = (time - mins) / 60;
            String hourstring;
            String minutestring;
            if (hours == 1) {
                hourstring = Double.toString(hours) + " hour and ";
            }
            else{
                hourstring = Double.toString(hours) + " hours and ";
            }
            if (mins == 1) {
                minutestring = Double.toString(mins) + " minute";
            }
            else{
                minutestring = Double.toString(mins) + " minutes";
            }
            String instructions = "";
            instructions += ("Journey takes " + hourstring + minutestring) + "\n";
            instructions += ("Journey costs $" + Double.toString(calcPathCost(fin, library)/100)) + "\n" + "\n";
            String[] modes = {"Walk from ", "Take public transport from ", "Take a taxi from "};
            for (int i = 0; i < n - 1; i++){
                instructions += modes[how.get(fin.get(i))] + converter.get(fin.get(i)) + " to " + converter.get(fin.get(i + 1)) + "\n";
            }
            Object[] returnList = {instructions, fin, how};
            return returnList;
        }
        catch (invalidLocList e){
            Object[] returnList = {"Invalid Location List!", new ArrayList<>(), new HashMap<>()};
            return returnList;
        }
    }

    public static LinkedList<String> constructList(int[] counters, LinkedList<String> original){
        LinkedList<String> fin = new LinkedList<>();
        int n = original.size();
        fin.add(original.get(0));
        original.remove(0);
        for (int i = 0; i < n - 2; i++){
            fin.add(original.get(counters[i]));
            original.remove(counters[i]);
        }
        fin.add(original.get(0));
        return fin;
    }

    public static class invalidLocList extends Exception{
        public invalidLocList(){
            super("ERROR! Invalid Location List");
        }
    }

    public static class TaxiPubComp implements Comparator<String[]> {
        private HashMap<String, Location> library;
        TaxiPubComp (HashMap<String, Location> library){
            this.library = library;
        }
        public int compare(String[] a, String[] b){
            double timeA = (library.get(a[0]).getTime(a[1])[2] - library.get(a[0]).getTime(a[1])[1]);
            double timeB = (library.get(b[0]).getTime(b[1])[2] - library.get(b[0]).getTime(b[1])[1]);
            double costA = (library.get(a[0]).getCost(a[1])[2] - library.get(a[0]).getCost(a[1])[1]);
            double costB = (library.get(b[0]).getCost(b[1])[2] - library.get(b[0]).getCost(b[1])[1]);
            if ((timeA/costA) > (timeB/costB)){
                return -1;
            }
            return 1;
        }
    }

    public static class FootPubComp implements Comparator<String[]> {
        private HashMap<String, Location> library;
        FootPubComp (HashMap<String, Location> library){
            this.library = library;
        }
        public int compare(String[] a, String[] b){
            double timeA = (library.get(a[0]).getTime(a[1])[0] - library.get(a[0]).getTime(a[1])[1]);
            double timeB = (library.get(b[0]).getTime(b[1])[0] - library.get(b[0]).getTime(b[1])[1]);
            double costA = (library.get(a[0]).getCost(a[1])[0] - library.get(a[0]).getCost(a[1])[1]);
            double costB = (library.get(b[0]).getCost(b[1])[0] - library.get(b[0]).getCost(b[1])[1]);
            if ((timeA/costA) > (timeB/costB)){
                return 1;
            }
            return -1;
        }
    }
}
