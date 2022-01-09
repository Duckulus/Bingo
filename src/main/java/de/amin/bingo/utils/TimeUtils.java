package de.amin.bingo.utils;

public class TimeUtils {

    public static String formatTime(int seconds){
        if(seconds <= 0)return "";
        int h = seconds / 3600;
        int m = (seconds / 60)%60;
        int s = seconds % 60;
        String formattedH;
        String formattedM;
        String formattedS;

        formattedM = String.format("%02d", m);
        formattedS = String.format("%02d", s);
        if(h>0){
            formattedH = String.format("%02d", h);
            return formattedH + ":" +formattedM+":"+formattedS;
        }else {
            formattedH = null;
            return formattedM+":"+formattedS;
        }
    }

}
