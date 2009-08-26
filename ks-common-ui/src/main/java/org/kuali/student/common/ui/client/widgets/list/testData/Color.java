package org.kuali.student.common.ui.client.widgets.list.testData;

import org.kuali.student.core.dto.Idable;

public class Color implements Idable{
    private String id;
    private String color;
    private String warmth;
    private String type;
    
    public Color(String id, String color, String warmth, String type) {
        super();
        this.color = color;
        this.warmth = warmth;
        this.id = id;
        this.type = type;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public void setWarmth(String warmth) {
        this.warmth = warmth;
    }
    public String getWarmth() {
        return warmth;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }
    
}
