package org.kuali.student.core.organization.ui.client.mvc.model;

import java.io.Serializable;
import java.util.List;

public class SectionViewInfo implements Serializable{
    private String viewName;
    private String tab;
    private String menu;
    private String sectionName;
    private String sectionEnum;
    private List<FieldInfo> fields;
    
    public String getViewName(){
        return viewName;
    }
    public void setViewName(String viewName){
        this.viewName = viewName;
    }
    
    public String getTab(){
        return tab;
    }
    public void setTab(String tab){
        this.tab=tab;
    }
    
    public String getMenu(){
        return menu;
    }
    public void setMenu(String menu){
        this.menu=menu;
    }
    
    public String getSectionName(){
        return sectionName;
    }
    public void setSectionName(String sectionName){
        this.sectionName=sectionName;
    }
    
    public String getSectionEnum(){
        return sectionEnum;
    }
    public void setSectionEnum(String sectionEnum){
        this.sectionEnum=sectionEnum;
    }
    
    public List<FieldInfo> getfields(){
        return fields;
    }
    public void setfields(List<FieldInfo> fields){
        this.fields=fields;
    }
}
