package ua.a5.mybusinessplan.model;

/**
 * Created by Lenovo on 06.04.2017.
 */

public class Event {
    private String title;
    private String location;
    private int isImportant;
    private String beginDay;
    private String beginMonth;
    private String beginHour;
    private String beginMinute;
    private String endDay;
    private String endMonth;
    private String endHour;
    private String endMinute;
    private String description;


    public Event(String title, String location, int isImportant, String beginDay, String beginMonth, String beginHour, String beginMinute, String endDay, String endMonth, String endHour, String endMinute, String description) {
        this.title = title;
        this.location = location;
        this.isImportant = isImportant;
        this.beginDay = beginDay;
        this.beginMonth = beginMonth;
        this.beginHour = beginHour;
        this.beginMinute = beginMinute;
        this.endDay = endDay;
        this.endMonth = endMonth;
        this.endHour = endHour;
        this.endMinute = endMinute;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getIsImportant() {
        return isImportant;
    }

    public void setIsImportant(int isImportant) {
        this.isImportant = isImportant;
    }

    public String getBeginDay() {
        return beginDay;
    }

    public void setBeginDay(String beginDay) {
        this.beginDay = beginDay;
    }

    public String getBeginMonth() {
        return beginMonth;
    }

    public void setBeginMonth(String beginMonth) {
        this.beginMonth = beginMonth;
    }

    public String getBeginHour() {
        return beginHour;
    }

    public void setBeginHour(String beginHour) {
        this.beginHour = beginHour;
    }

    public String getBeginMinute() {
        return beginMinute;
    }

    public void setBeginMinute(String beginMinute) {
        this.beginMinute = beginMinute;
    }

    public String getEndDay() {
        return endDay;
    }

    public void setEndDay(String endDay) {
        this.endDay = endDay;
    }

    public String getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(String endMonth) {
        this.endMonth = endMonth;
    }

    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }

    public String getEndMinute() {
        return endMinute;
    }

    public void setEndMinute(String endMinute) {
        this.endMinute = endMinute;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return "Event{" +
                "title=" + title +
                ", location=" + location +
                ", isImportant=" + isImportant +
                ", begin=" + beginDay + "." + beginMonth +
                ", (" + beginHour + " : " + beginMinute + ")" +
                ", end=" + endDay + "." + endMonth +
                ", (" + endHour + " : " + endMinute + ")" +
                ", description=" + description +
                '}';
    }
}
