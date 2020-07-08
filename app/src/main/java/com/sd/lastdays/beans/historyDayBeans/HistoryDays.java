package com.sd.lastdays.beans.historyDayBeans;

public class HistoryDays {
    private String year;
    private String event;

    public HistoryDays(String year, String event) {
        this.event = event;
        this.year = year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getYear() {
        return year;
    }

    public String getEvent() {
        return event;
    }
}
