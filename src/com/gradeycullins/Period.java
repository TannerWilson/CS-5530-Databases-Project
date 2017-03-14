package com.gradeycullins;

import java.security.Timestamp;

/**
 * Created by Gradey Cullins on 3/14/17.
 */
public class Period {
    protected int pid;
    protected int tid;
    protected Timestamp from;
    protected Timestamp to;
    protected int price;

    public Period(int pid, int tid, Timestamp from, Timestamp to, int price) {
        this.pid = pid;
        this.tid = tid;
        this.from = from;
        this.to = to;
        this.price = price;
    }
}
