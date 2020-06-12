package com.example.almonte.DataSource;

public class Plans {
    String _id;
    String name;
    String steps;
    String interval;
    String interest;
    String date;

    public Plans(String _id, String name, String steps, String interval, String interest, String date) {
        this._id = _id;
        this.name = name;
        this.steps = steps;
        this.interval = interval;
        this.interest = interest;
        this.date = date;
    }

    public String get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public String getSteps() {
        return steps;
    }

    public String getInterval() {
        return interval;
    }

    public String getInterest() {
        return interest;
    }

    public String getDate() {
        return date;
    }
}
