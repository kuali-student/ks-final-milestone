package org.kuali.student.enrollment.kitchensink;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: aliabad4
 * Date: 8/30/12
 * Time: 3:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class KitchenSinkMockActivityData implements Serializable {

    private String code;
    private String type;
    private String days;
    private String time;

    public KitchenSinkMockActivityData(){}

    public KitchenSinkMockActivityData(String code, String type, String days, String time) {
        this.code = code;
        this.type = type;
        this.days = days;
        this.time = time;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
