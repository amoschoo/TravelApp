package com.example.amos.travelapp.Backend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * Created by hj on 28/9/15.
 */
public class Compiled {
    public static void main(String[] args){
        Wordmap dict = new Wordmap();
        dict.generateMap();
        Word a = new Word("buudhha tooth relic temple");
        System.out.println(a.spellcheck(dict));

        HashMap<String, Location> library = Solvers.readLibrary("/Users/amos/desktop/LocList.txt");

        String start = "Marina Bay Sands";
        ArrayList<String> toGo = new ArrayList<>();
        toGo.add("Vivo City");
//        toGo.add("Zoo");
//        toGo.add("Resorts World Sentosa");
//        toGo.add("Buddha Tooth Relic Temple");
//        toGo.add("Singapore Flyer");
        double budget = 10;

        long now = System.nanoTime();
        System.out.println(Solvers.bruteForce(start, toGo, library, budget)[0]);


        HashMap<String, Integer> TransportType = (HashMap<String, Integer>)Solvers.bruteForce(start, toGo, library, budget)[2];
        LinkedList<String> Newlocationlist = (LinkedList<String>) Solvers.bruteForce(start, toGo, library, budget)[1];
        for (String L:Newlocationlist){
            System.out.println(L);
            System.out.println(TransportType.get(L));
        }


        long end = System.nanoTime();
        long ebf = TimeUnit.NANOSECONDS.toNanos(end - now);
        System.out.println(ebf);
        System.out.println("");

        now = System.nanoTime();
        System.out.println(Solvers.twoOpt(start, toGo, library, budget)[0]);
        end = System.nanoTime();
        long eto = TimeUnit.NANOSECONDS.toNanos(end - now);
        System.out.println(eto);
        System.out.println("");

        System.out.println(ebf/eto);
    }
}
