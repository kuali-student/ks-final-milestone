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

package org.kuali.student.lum.lu.dto;
 
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.common.dto.Idable;
import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;

/**
 *Detailed information about the human readable form of a CLU Identifier
 */ 
@XmlAccessorType(XmlAccessType.FIELD)
public class CluIdentifierInfo implements Serializable, Idable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String code;

    @XmlElement
    private String shortName;

    @XmlElement
    private String longName;

    @XmlElement
    private String level;

    @XmlElement
    private String division;

    @XmlElement
    private String variation;

    @XmlElement
    private String suffixCode;

    @XmlElement
    private String orgId;

    @XmlAttribute
    private String type;

    @XmlAttribute
    private String state;

    @XmlAttribute
    private String id;

    @XmlElement
    @XmlJavaTypeAdapter(JaxbAttributeMapListAdapter.class)
    private Map<String, String> attributes;
    
    /**
     * The composite string that is used to officially reference or publish the CLU. Note it may have an internal structure that each Institution may want to enforce. This structure may be composed from the other parts of the structure such as Level amp; Division, but may include items such as cluType.
     */
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Abbreviated name of the CLU, commonly used on transcripts
     */
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    /**
     * A code that indicates whether this is introductory, advanced, etc.
     */
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    /**
     * A code that indicates what school, program, major, subject area, etc. Examples: "Chem", "18"
     */
    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }
    
    /*
     * The "extra" portion of the code, which usually corresponds with the most detailed part of the number. 
     */    
    public String getSuffixCode() {
        return suffixCode;
    }

    public void setSuffixCode(String suffixCode) {
        this.suffixCode = suffixCode;
    }
    
    /**
     * A number that indicates the sequence or order of versions in cases where several different Clus have the same offical Identifier
     */
    public String getVariation() {
        return variation;
    }

    public void setVariation(String variation) {
        this.variation = variation;
    }

    /*
     * The identifier of the organization associated with this cluIdentifier.
     */
    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    /**
     * Identifies the type of usage for the identifier. While most usages will have the same data constraints, this may provide some context around what the specific intent is for this identifier. (Ex. Why does this alternate identifier exist?)
     */
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * Identifies the state of the identifier. Values for this field are constrained to values present within the cluIdentifierState enumeration.
     */
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    /**
     * Identifies the particular identifier structure. This is set by the service to be able to determine changes and alterations to the structure as well as provides a handle for searches. This structure is not accessible through unique operations, and it is strongly recommended that no external references to this particular identifier be maintained.
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, String> getAttributes() {
        if (attributes == null) {
            attributes = new HashMap<String, String>();
        }
        
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {       
        this.attributes = attributes;
    }    
}
