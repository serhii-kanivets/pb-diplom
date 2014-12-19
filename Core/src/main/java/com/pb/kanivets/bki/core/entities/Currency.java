package com.pb.kanivets.bki.core.entities;

public class Currency {
    private String currId;
    private String name;

    public String getCurrId() {
        return currId;
    }

    public void setCurrId(String currId) {
        this.currId = currId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Currency{" + "currId=" + currId + ", name=" + name + '}';
    }
    
    
    
}
