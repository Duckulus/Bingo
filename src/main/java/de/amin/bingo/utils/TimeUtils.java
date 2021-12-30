package de.amin.bingo.utils;

public class TimeUtils {

    public static String formatTime(int seconds){
        if(seconds <= 0)return "";
        int m = seconds / 60;
        int s = seconds % 60;
        String formattedM;
        String formattedS;
        if(m>99){
            formattedM = String.format("%03d", m);
        }else {
            formattedM = String.format("%02d", m);
        }
        formattedS = String.format("%02d", s);

        return formattedM+":"+formattedS;
    }

}
