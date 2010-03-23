package org.kuali.student.core.organization.ui.client.mvc.model;

import java.io.Serializable;

public class FieldInfoImpl implements FieldInfo, Serializable{
    private String label;
    private String key;
    private String widget;
    
    public String getLabel(){
        return label;
    }
    
    public void setLabel(String label){
        this.label=label;
    }
    
    public String getKey(){
        return key;
    }
    
    public void setKey(String key){
        this.key = key;
    }
    
    public String getWidget(){
        return widget;
    }
    public void setWidget(String widget){
        this.widget=widget;
    }
}
