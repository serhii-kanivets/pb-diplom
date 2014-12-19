package com.pb.kanivets.bki.core.entities;

import java.math.BigDecimal;
import java.util.Date;

public class Credit {
     private int id;
     private String currId;
     private int clientId;
     private int mfo;
     private BigDecimal initSum;
     private Date issueDate;
     private BigDecimal body ;
     private Date closeDate;
     private String delay;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCurrId() {
        return currId;
    }

    public void setCurrId(String currId) {
        this.currId = currId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getMfo() {
        return mfo;
    }

    public void setMfo(int mfo) {
        this.mfo = mfo;
    }

    public BigDecimal getInitSum() {
        return initSum;
    }

    public void setInitSum(BigDecimal initSum) {
        this.initSum = initSum;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public BigDecimal getBody() {
        return body;
    }

    public void setBody(BigDecimal body) {
        this.body = body;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public String getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

    @Override
    public String toString() {
        return "Credit{" + "id=" + id + ", currId=" + currId + ", clientId=" + clientId + ", mfo=" + mfo + ", initSum=" + initSum + ", issueDate=" + issueDate + ", body=" + body + ", closeDate=" + closeDate + ", delay=" + delay + '}';
    }
     
}
