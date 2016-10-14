package com.haisenhong.flicker.data.models.responses;

/**
 * Created by hison7463 on 10/12/16.
 */

public class Date {

    private String maximum;
    private String minimum;

    public Date() {
    }

    public Date(String maximum, String minimum) {
        this.maximum = maximum;
        this.minimum = minimum;
    }

    public String getMinimum() {
        return minimum;
    }

    public void setMinimum(String minimum) {
        this.minimum = minimum;
    }

    public String getMaximum() {
        return maximum;
    }

    public void setMaximum(String maximum) {
        this.maximum = maximum;
    }

    @Override
    public String toString() {
        return "Date{" +
                "maximum='" + maximum + '\'' +
                ", minimum='" + minimum + '\'' +
                '}';
    }
}
