package org.kuali.student.enrollment.class2.courseoffering.dto;

/**
 * Created by IntelliJ IDEA.
 * User: venkat
 * Date: 9/14/12
 * Time: 3:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class ManageSOCStatusHistory {

    private String status;
    private String date;

    public ManageSOCStatusHistory(String status,String date){
        this.status = status;
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public String getDate() {
        return date;
    }

}
