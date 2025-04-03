package com.mk.skincareorder.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Hours {
    // Kjo klasë përfaqëson orët e funksionimit të një dyqani për secilën ditë të javës.

    String Sunday;
    String Monday;
    String Tuesday;
    String Wednesday;
    String Thursday;
    String Friday;
    String Saturday;
    // Fushat që mbajnë orët e funksionimit për secilën ditë të javës.

    // Metodat getter dhe setter për secilën fushë.

    public String getSunday() {
        return Sunday;
    }

    public void setSunday(String sunday) {
        Sunday = sunday;
    }

    public String getMonday() {
        return Monday;
    }

    public void setMonday(String monday) {
        Monday = monday;
    }

    public String getTuesday() {
        return Tuesday;
    }

    public void setTuesday(String tuesday) {
        Tuesday = tuesday;
    }

    public String getWednesday() {
        return Wednesday;
    }

    public void setWednesday(String wednesday) {
        Wednesday = wednesday;
    }

    public String getThursday() {
        return Thursday;
    }

    public void setThursday(String thursday) {
        Thursday = thursday;
    }

    public String getFriday() {
        return Friday;
    }

    public void setFriday(String friday) {
        Friday = friday;
    }

    public String getSaturday() {
        return Saturday;
    }

    public void setSaturday(String saturday) {
        Saturday = saturday;
    }

    public String getTodaysHours(){
        // Kjo metodë kthen orët e funksionimit për ditën aktuale.

        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        // Merr datën dhe kohën aktuale.

        String day = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());
        // Formaton ditën aktuale në vargun e ditës në anglisht (p.sh., "Monday").

        switch (day){
            case "Sunday":
                return this.Sunday;
            case "Monday":
                return this.Monday;
            case "Tuesday":
                return this.Tuesday;
            case "Wednesday":
                return this.Wednesday;
            case "Thursday":
                return this.Thursday;
            case "Friday":
                return this.Friday;
            case "Saturday":
                return this.Saturday;
            default:
                return this.Sunday;
            // Nëse për ndonjë arsye nuk përputhet asnjë ditë, kthen orët për të Dielën si parazgjedhje.
        }
    }
}
