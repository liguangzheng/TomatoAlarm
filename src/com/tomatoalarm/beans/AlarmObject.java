package com.tomatoalarm.beans;

import java.io.Serializable;

public class AlarmObject implements Serializable {

    private static final long serialVersionUID = 1L;

    public String name;
    public String content;
    public long startTime;
    public long endTime;
}
