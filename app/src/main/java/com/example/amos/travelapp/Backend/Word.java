package com.example.amos.travelapp.Backend;
import java.util.*;

/**
 * Created by hj on 28/9/15.
 */
public class Word {
    private String word;
    int checkStr = 2;
    ArrayList<String> words = new ArrayList<>();
    ArrayList<String> tempwords = new ArrayList<>();
    public Word(String w) {
        word = w.toLowerCase();
        words.add(word);
    }

    public String spellcheck(Wordmap wm){
        generateMap();
        for (String item: words) {
            if (wm.wordmap.containsKey(item)) {
                if (wm.linkmap.containsKey(wm.wordmap.get(item))) {
                    return (String) wm.linkmap.get(wm.wordmap.get(item));
                } else {
                    return (String) wm.wordmap.get(item);
                }
            }
        }
        return word;
    }

    public void generateMap(){
        while (checkStr!=0){
            extendWord();
        }
    }

    public void extendWord(){
        int i;
        checkStr -= 1;
        String newstr;
        String current;
        Iterator<String> it = words.iterator();
        while (it.hasNext()){
            current = it.next();
            if (current.length() > 2) {
                for (i = 0; i <= current.length() - 1; i++) {
                    newstr = current.substring(0, i) + current.substring(i + 1);
                    tempwords.add(newstr);
                }
            }
        }
        words.addAll(tempwords);
        tempwords.clear();
    }
}
