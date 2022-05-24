package com.fnfbuzz.jarvis_model;

import java.util.Calendar;

public class Function {
    public  static  String string()
    {
        String s="";
        Calendar calendar=Calendar.getInstance();
        int time=calendar.get(Calendar.HOUR_OF_DAY);
        if (time>=0 && time<12) {
            s="Good Morning sir.";
        }
        else if (time>=12 && time<16) {
            s="Good AfterNoon sir";
        }
        else if (time>=16 && time<21) {
            s="Good Evening sir";
        }
        else if (time>=21 && time<0) {
            s="Good Night sir";
        }
        else {
            s="Hello sir?I am Jarvis";
        }
        return  s;
    }
}
