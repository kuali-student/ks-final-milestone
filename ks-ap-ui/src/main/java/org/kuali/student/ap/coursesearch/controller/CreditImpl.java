package org.kuali.student.ap.coursesearch.controller;

import org.kuali.student.ap.coursesearch.CourseSearchItem;
import org.kuali.student.ap.coursesearch.Credit;

public class CreditImpl implements Credit {
    private String id;
    private String display;
    private float min;
    private float max;
    private float[] multiple;
    private CourseSearchItem.CreditType type;

    public String getId() {
        return id;
    }

    public String getDisplay() {
        return display;
    }

    public float getMin() {
        return min;
    }

    public float getMax() {
        return max;
    }

    public float[] getMultiple() {
        return multiple;
    }

    public CourseSearchItem.CreditType getType() {
        return type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public void setMultiple(float[] multiple) {
        this.multiple = multiple;
    }
    public void setMultiple(int i, float multiple) {
        this.multiple[i] = multiple;
    }

    public void setType(CourseSearchItem.CreditType type) {
        this.type = type;
    }
}
