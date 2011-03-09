/**
 * Copyright 2010 The Kuali Foundation 
 *
 * Licensed under the the Educational Community License, Version 1.0
 * (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kuali.student.lum.lu.dto;
 
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.core.dto.HasAttributes;
import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;


/**
 * Detailed information about accounting for a clu.
 */ 

@XmlAccessorType(XmlAccessType.FIELD)
public class CluAccountingInfo implements Serializable, HasAttributes {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private List<AffiliatedOrgInfo> affiliatedOrgs;    
    
    @XmlElement
    @XmlJavaTypeAdapter(JaxbAttributeMapListAdapter.class)
    private Map<String,String> attributes;
    
    @XmlAttribute
    private String id;


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

    public List<AffiliatedOrgInfo> getAffiliatedOrgs() {
        return affiliatedOrgs;
    }

    public void setAffiliatedOrgs(List<AffiliatedOrgInfo> affiliatedOrgs) {
        this.affiliatedOrgs = affiliatedOrgs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }        
}
