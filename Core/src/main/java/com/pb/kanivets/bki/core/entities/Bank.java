package com.pb.kanivets.bki.core.entities;

public class Bank {
    private int mfo;
    private String name;

    @Override
    public String toString() {
        return "Bank{" + "mfo=" + mfo + ", name=" + name + '}';
    }

    public int getMfo() {
        return mfo;
    }

    public void setMfo(int mfo) {
        this.mfo = mfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
