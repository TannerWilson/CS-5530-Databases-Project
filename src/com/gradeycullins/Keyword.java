package com.gradeycullins;

/**
 * Created by Gradey Cullins on 3/15/17.
 */
public class Keyword {
    protected int kid; // primary key
    protected int tid; // foreign key -> th
    protected String word;
    protected String language;

    public Keyword(int kid, int tid, String word, String language) {
        this.kid = kid;
        this.tid = tid;
        this.word = word;
        this.language = language;
    }
}
