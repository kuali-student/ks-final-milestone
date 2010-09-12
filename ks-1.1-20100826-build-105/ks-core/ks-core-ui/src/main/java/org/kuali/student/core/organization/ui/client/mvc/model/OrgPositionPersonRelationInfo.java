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
import java.util.ArrayList;

public class OrgPositionPersonRelationInfo implements Serializable{
    private static final long serialVersionUID = 1L;
    private ArrayList<String> personId;
    private String orgId;
    private String title;
    private String orgPersonRelationTypeKey;
    private String desc;
    private String minNumRelations;
    private String maxNumRelations;
    
    public ArrayList<String> getPersonId(){
        return this.personId;
    }
    
    public void setPersonId(ArrayList<String> personId){
        this.personId=personId;
    }
    
    public String getOrgId(){
        return this.orgId;
    }
    
    public void setOrgId(String orgId){
        this.orgId=orgId;
    }
    
    public String getTitle(){
        return this.title;
    }
    
    public void setTitle(String title){
        this.title=title;
    }
    
    public String getOrgPersonRelationTypeKey(){
        return this.orgPersonRelationTypeKey;
    }
    
    public void setOrgPersonRelationTypeKey(String orgPersonRelationTypeKey){
        this.orgPersonRelationTypeKey=orgPersonRelationTypeKey;
    }
    
    public String getDesc(){
        return this.desc;
    }
    
    public void setDesc(String desc){
        this.desc=desc;
    }
    
    public String getMinNumRelations(){
        return this.minNumRelations;
    }
    
    public void setMinNumRelations(String minNumRelations){
        this.minNumRelations=minNumRelations;
    }
    
    public String getMaxNumRelations(){
        return this.maxNumRelations;
    }
    
    public void setMaxNumRelations(String maxNumRelations){
        this.maxNumRelations=maxNumRelations;
    }
    
    
}
