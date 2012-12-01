/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

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
