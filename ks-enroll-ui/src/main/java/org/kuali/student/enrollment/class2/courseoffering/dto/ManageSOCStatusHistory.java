package org.kuali.student.enrollment.class2.courseoffering.dto;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: venkat
 * Date: 9/14/12
 * Time: 3:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class ManageSOCStatusHistory implements Comparable<ManageSOCStatusHistory>{

    private String state;
    private String stateKey;
    private String date;
    private Date dateObject;
    private boolean highlightUI;
    private boolean greyText;

    public ManageSOCStatusHistory(){
    }

    public ManageSOCStatusHistory(String stateName,String stateKey,String date,Date dateObject){
        this.state = stateName;
        this.stateKey = stateKey;
        this.date = date;
        this.dateObject = dateObject;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getState() {
        return state;
    }

    public String getDate() {
        return date;
    }

    public void setHighlightUI(boolean highlightUI) {
        this.highlightUI = highlightUI;
    }

    public boolean isHighlightUI() {
        return highlightUI;
    }

    public Date getDateObject() {
        return dateObject;
    }

    public boolean isGreyText() {
        return greyText;
    }

    public void setGreyText(boolean greyText) {
        this.greyText = greyText;
    }

    public String getStateKey() {
        return stateKey;
    }

    @Override
    public int compareTo(ManageSOCStatusHistory manageSOCStatusHistory) {
        if (this.getDateObject() != null && manageSOCStatusHistory.getDateObject() != null){
            return getDateObject().compareTo(manageSOCStatusHistory.getDateObject());
        }else if (manageSOCStatusHistory.getDateObject() == null){
            return -1;
        }else{
            return 1;
        }
    }

}
