package org.kuali.student.core.organization.ui.client.mvc.model;

import java.io.Serializable;
import java.util.List;

public class MultipleFieldInfoImpl implements FieldInfo,Serializable{

    private String itemLabel;
    private String addItemLabel;
    private String key;
    private String widget;
    private List<FieldInfo> fields;

    public String getLabel(){
        return itemLabel;
    }
    
    public void setLabel(String itemLabel){
        this.itemLabel=itemLabel;
    }
    
    public String getAddItemLabel(){
        return addItemLabel;
    }
    
    public void setAddItemLabel(String addItemLabel){
        this.addItemLabel=addItemLabel;
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
    
    public List<FieldInfo> getFields(){
        return fields;
    }
    
    public void setFields(List<FieldInfo> fields){
        this.fields=fields;
    }
    
}
