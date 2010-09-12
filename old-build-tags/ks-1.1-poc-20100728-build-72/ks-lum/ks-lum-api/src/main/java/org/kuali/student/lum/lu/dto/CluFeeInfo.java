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
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.core.dto.HasAttributes;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;

/**
 *Detailed information associated with this CLU related to the calculation of fees. 
 */ 
@XmlAccessorType(XmlAccessType.FIELD)
public class CluFeeInfo implements Serializable, HasAttributes {

    private static final long serialVersionUID = 1L;

    @XmlAttribute
    private String id;    
    
    @XmlElement
    private RichTextInfo descr;
        
    @XmlElement
    private List<CluFeeRecordInfo> cluFeeRecords;
    
    @XmlElement
	@XmlJavaTypeAdapter(JaxbAttributeMapListAdapter.class)
    private Map<String,String> attributes;

    @XmlElement
    private MetaInfo metaInfo;
    
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

	public List<CluFeeRecordInfo> getCluFeeRecords() {
		return cluFeeRecords;
	}

	public void setCluFeeRecords(List<CluFeeRecordInfo> cluFeeRecords) {
		this.cluFeeRecords = cluFeeRecords;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Narrative description of the CLU Fee
	 */
	public RichTextInfo getDescr() {
		return descr;
	}

	public void setDescr(RichTextInfo descr) {
		this.descr = descr;
	}

	public MetaInfo getMetaInfo() {
		return metaInfo;
	}

	public void setMetaInfo(MetaInfo metaInfo) {
		this.metaInfo = metaInfo;
	}
}
