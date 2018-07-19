package com.example.a171y034.travelbook719;

/**
 * Created by 171y034 on 2018/07/19.
 */

public class Add {

    private int colorLabel;

    private long createdTime;

    private String value;

    public static interface ColorLabel {

        public static final int NONE = 1;
        public static final int PINK = 2;
        public static final int INDIGO = 3;
        public static final int GREEN = 4;
        public static final int AMBER = 5;
    }

    public Add(int colorLabel, String value, long createdTime) {
        this.colorLabel = colorLabel;
        this.value = value;
        this.createdTime = createdTime;
    }

    public int getColorLabel() {
        return colorLabel;
    }

    public void setColorLabel(int colorLabel) {
        this.colorLabel = colorLabel;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

}
