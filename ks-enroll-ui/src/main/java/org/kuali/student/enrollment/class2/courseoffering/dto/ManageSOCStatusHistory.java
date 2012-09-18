package org.kuali.student.enrollment.class2.courseoffering.dto;

/**
 * Created by IntelliJ IDEA.
 * User: venkat
 * Date: 9/14/12
 * Time: 3:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class ManageSOCStatusHistory {

    private String state;
    private String date;

    public ManageSOCStatusHistory(){
    }

    public ManageSOCStatusHistory(String state,String date){
        this.state = state;
        this.date = date;
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

}
