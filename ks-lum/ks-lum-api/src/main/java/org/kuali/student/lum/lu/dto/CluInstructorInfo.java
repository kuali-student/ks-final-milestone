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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.core.dto.HasAttributes;
import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;

/**
 *Information about a potential instructor for a clu.
 */ 
@XmlAccessorType(XmlAccessType.FIELD)
public class CluInstructorInfo implements Serializable, HasAttributes {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String orgId;

    @XmlElement
    private String personId;

    @XmlElement
    private String personInfoOverride;
    
    @XmlElement
	@XmlJavaTypeAdapter(JaxbAttributeMapListAdapter.class)
    private Map<String,String> attributes;

    /**
     * Unique identifier for an organization. This indicates which organization this individual is associated with for the purposes of this clu.
     */
    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    /**
     * Unique identifier for a person record.
     */
    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
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
     * @return the personInfoOverride
     */
    public String getPersonInfoOverride() {
        return personInfoOverride;
    }

    /**
     * @param personInfoOverride the personInfoOverride to set
     */
    public void setPersonInfoOverride(String personInfoOverride) {
        this.personInfoOverride = personInfoOverride;
    }     
}
