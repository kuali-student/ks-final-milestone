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

package org.kuali.student.enrollment.courseoffering.dto;
 
import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.courseoffering.infc.OfferingInstructor;
import org.kuali.student.r2.common.dto.TypeStateEntityInfo;
import org.w3c.dom.Element;

/**
 * Information about an instructor.
 */
 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OfferingInstructorInfo", propOrder = {"id", "personId", 
                "percentageEffort","personName", "typeKey", "stateKey", 
                "meta", "attributes", "_futureElements"})

public class OfferingInstructorInfo 
    extends TypeStateEntityInfo 
    implements OfferingInstructor, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlAttribute
    private String id;
    
    @XmlElement
    private String personId;

    @XmlElement
    private String personName;
    
    @XmlElement 
    private Float percentageEffort; 
    
    @XmlAnyElement
    private List<Element> _futureElements;

    public OfferingInstructorInfo() {
        this.id = null;
        this.personId = null;
        this.percentageEffort = null;
        this._futureElements  = null;
        this.personName = null;
    }
    
    public OfferingInstructorInfo(OfferingInstructor instructor) {
        
        super(instructor);
        
        if(null == instructor) return;
        this.id = instructor.getId();
        this.personId = instructor.getPersonId();
        this.percentageEffort = instructor.getPercentageEffort();
        this.personName = instructor.getPersonName();
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
    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    @Override
    public Float getPercentageEffort() {
        return percentageEffort;
    }

    public void setPercentageEffort(Float percentageEffort) {
        this.percentageEffort = percentageEffort;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }
    @Override
    public String getPersonName() {
        return personName;
    }

}
