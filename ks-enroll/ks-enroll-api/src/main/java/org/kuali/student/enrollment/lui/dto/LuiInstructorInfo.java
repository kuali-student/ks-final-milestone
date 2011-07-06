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

package org.kuali.student.enrollment.lui.dto;
 
import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.lui.infc.LuiInstructor;
import org.kuali.student.r2.common.dto.HasAttributesAndMetaInfo;
import org.w3c.dom.Element;

/**
 *Information about a potential instructor for a clu.
 */ 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CourseOfferingInfo", propOrder = {"Id", "orgId", "personId", "personInfoOverride", 
        "percentageEffort", "meta", "attributes", "_futureElements"})
public class LuiInstructorInfo extends HasAttributesAndMetaInfo implements LuiInstructor, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlAttribute
    private String id;
    
    @XmlElement
    private String orgId;

    @XmlElement
    private String personId;

    @XmlElement
    private String personInfoOverride;
    
    @XmlElement 
    private Float percentageEffort; 
    
    @XmlAnyElement
    private List<Element> _futureElements;

    public LuiInstructorInfo() {
        this.id = null;
        this.orgId = null;
        this.personId = null;
        this.personInfoOverride = null;
        this.percentageEffort = null;
        this._futureElements  = null;
    }
    
    public LuiInstructorInfo(LuiInstructor instructor) {
        
        super(instructor);
        
        if(null == instructor) return;
        this.id = instructor.getId();
        this.orgId = instructor.getOrgId();
        this.personId = instructor.getPersonId();
        this.personInfoOverride = instructor.getPersonInfoOverride();
        this.percentageEffort = instructor.getPercentageEffort();
        this._futureElements = null;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
        
    
    @Override
    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    @Override
    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    @Override
    public String getPersonInfoOverride() {
        return personInfoOverride;
    }

    public void setPersonInfoOverride(String personInfoOverride) {
        this.personInfoOverride = personInfoOverride;
    }

    @Override
    public Float getPercentageEffort() {
        return percentageEffort;
    }

    public void setPercentageEffort(Float percentageEffort) {
        this.percentageEffort = percentageEffort;
    }     
}
