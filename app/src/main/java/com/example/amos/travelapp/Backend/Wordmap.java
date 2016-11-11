package com.example.amos.travelapp.Backend;

import java.util.*;

/**
 * Created by hj on 28/9/15.
 */
public class Wordmap {
    private ArrayList<String> words;
    public Map wordmap = new HashMap();
    Map tempwordmap = new HashMap();
    public Map linkmap = new HashMap();
    int checkStr;
    public Wordmap(){
    }

    public void generateMap(){
        words = new ArrayList<>();
        words.add("Sentosa");
        words.add("Resorts World Sentosa");
        words.add("Marina Bay Sands");
        words.add("Buddha Tooth Relic Temple");
        words.add("Vivo City");
        words.add("Zoo");
        linkmap.put("Sentosa","Resorts World Sentosa");
        linkmap.put("Vivo","Vivo City");
        for (String word : words){
            String lowerWord = word.toLowerCase();
                wordmap.put(lowerWord, word);
        }
        checkStr = 2;
        while (checkStr!=0){
            extendMap();
        }
    }
    public void extendMap(){
        int i;
        checkStr -= 1;
        String newstr;
        String realkey;
        String current;
        Iterator<String> it = wordmap.keySet().iterator();
        while (it.hasNext()){
            current = it.next();
            if (current.length() > 2) {
                realkey = (String) wordmap.get(current);
                for (i = 0; i <= current.length() - 1; i++) {
                    newstr = current.substring(0, i) + current.substring(i + 1);
                    tempwordmap.put(newstr, realkey);
                }
            }
        }
        wordmap.putAll(tempwordmap);
        tempwordmap.clear();
    }

}
