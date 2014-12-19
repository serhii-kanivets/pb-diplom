package com.pb.kanivets.bki.core.entities;

import java.util.Date;

public class Client {
    private int clientId;    
    private String inn;
    private String sName;
    private String fName;
    private String mName;
    private Date birthDate;
    private String passp;
    private Date deleted;

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPassp() {
        return passp;
    }

    public void setPassp(String passp) {
        this.passp = passp;
    }

    public Date getDeleted() {
        return deleted;
    }

    public void setDeleted(Date deleted) {
        this.deleted = deleted;
    }

 
    

    @Override
    public String toString() {
        return "Client{" + "clientId=" + clientId + ", inn=" + inn + ", sName=" + sName + ", fName=" + fName + ", mName=" + mName + ", birthDate=" + birthDate + ", passp=" + passp + ", deleted=" + deleted + '}';
    }    
    
}
