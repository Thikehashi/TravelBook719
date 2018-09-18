package com.example.a171y034.travelbook719.Belongings.Add;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 171y034 on 2018/07/26.
 */

public class AddBelongings{

    private long createdTime;

    private String value;

    public static interface ColorLabel {

        public static final int NONE = 1;
        public static final int PINK = 2;
        public static final int INDIGO = 3;
        public static final int GREEN = 4;
        public static final int AMBER = 5;
    }

    public AddBelongings(String value, long createdTime) {
        this.value = value;
        this.createdTime = createdTime;
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

    /**
     * 表示リストアイテムを作成.
     */
    public static List<AddBelongings> Item() {
        List<AddBelongings> items = new ArrayList<>();
        return items;
    }

}
