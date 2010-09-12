/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.lum.lu.dto;
 
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.core.dto.HasAttributes;
import org.kuali.student.core.dto.HasTypeState;
import org.kuali.student.core.dto.Idable;
import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;

/**
 *Detailed information about publishing a clu.
 */ 
@XmlAccessorType(XmlAccessType.FIELD)
public class CluPublishingInfo implements Serializable, Idable, HasTypeState, HasAttributes {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String startCycle;

    @XmlElement
    private String endCycle;

    @XmlElement
    private CluInstructorInfo primaryInstructor;

    @XmlElement
    private List<CluInstructorInfo> instructors;

    @XmlElement
    @XmlJavaTypeAdapter(JaxbAttributeMapListAdapter.class)
    private Map<String,String> attributes;

    @XmlAttribute
    private String type;

    @XmlAttribute
    private String state;

    @XmlAttribute
    private String id;

    /**
     * The start academic time period for when the CLU should be published in this type of usage. Should be less than or equal to endCycle.
     */
    public String getStartCycle() {
        return startCycle;
    }

    public void setStartCycle(String startCycle) {
        this.startCycle = startCycle;
    }

    /**
     * The end academic time period for when the CLU should be published in this type of usage. If specified, should be greater than or equal to startCycle.
     */
    public String getEndCycle() {
        return endCycle;
    }

    public void setEndCycle(String endCycle) {
        this.endCycle = endCycle;
    }

    /**
     * Primary potential instructor for the clu for the purpose of this publication.
     */
    public CluInstructorInfo getPrimaryInstructor() {
        return primaryInstructor;
    }

    public void setPrimaryInstructor(CluInstructorInfo primaryInstructor) {
        this.primaryInstructor = primaryInstructor;
    }

    public List<CluInstructorInfo> getInstructors() {
        if (instructors == null) {
            instructors = new ArrayList<CluInstructorInfo>();
        }
        return instructors;
    }

    public void setInstructors(List<CluInstructorInfo> instructors) {
        this.instructors = instructors;
    }

    /**
     * List of key/value pairs, typically used for dynamic attributes.
     */
    public Map<String,String> getAttributes() {
        if (attributes == null) {
            attributes = new HashMap<String,String>();
        }
        return attributes;
    }

    public void setAttributes(Map<String,String> attributes) {
        this.attributes = attributes;
    }

    /**
     * Type of publication for which this information should be used. This type should correspond more with usage than media.
     */
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * Current state of the information for this publication type. This value should be constrained to those within the cluPublishingState enumeration. In general, an "active" record for a type indicates that the clu should be published within that media, though that may be further constrained by the cycle information included.
     */
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    /**
     * Identifier for the publishing information. This is set by the service to be able to determine changes and alterations to the structure as well as provides a handle for searches. This structure is not currently accessible through unique operations, and it is strongly recommended that no external references to this particular identifier be maintained.
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
