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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.core.dto.CurrencyAmountInfo;
import org.kuali.student.core.dto.HasAttributes;
import org.kuali.student.core.dto.Idable;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;

/**
 * Information about a fee related to a clu.
 *
 * @Author KSContractMojo
 * @Author Kamal
 * @Since Mon Jan 11 15:20:48 PST 2010
 * @See <a href="https://test.kuali.org/confluence/display/KULSTU/cluFeeRecordInfo+Structure+v1.0-rc2">CluFeeRecordInfo</>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class CluFeeRecordInfo implements Serializable, Idable, HasAttributes {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String feeType;

    @XmlElement
    private String rateType;

    @XmlElement
    private List<CurrencyAmountInfo> feeAmounts;
        
    @XmlElement
    private List<AffiliatedOrgInfo> affiliatedOrgs;

    @XmlElement
    private RichTextInfo descr;
    
    @XmlElement
    @XmlJavaTypeAdapter(JaxbAttributeMapListAdapter.class)
    private Map<String, String> attributes;

    @XmlElement
    private MetaInfo metaInfo;

    @XmlAttribute
    private String id;

    /**
     * A code that identifies the type of the fee. For example: Lab Fee or Tuition Fee or CMF for Course Materials Fee.
     */
    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    /**
     * Indicates the structure and interpretation of the fee amounts, i.e. Fixed, Variable, Multiple.
     */
    public String getRateType() {
		return rateType;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	/**
     * The amount or amounts associated with the fee. The number fee amounts and interpretation depends on the rate type.
     */
    public List<CurrencyAmountInfo> getFeeAmounts() {
    	if(feeAmounts==null){
    		feeAmounts = new ArrayList<CurrencyAmountInfo>();
    	}
        return feeAmounts;
    }

    public void setFeeAmounts(List<CurrencyAmountInfo> feeAmounts) {
        this.feeAmounts = feeAmounts;
    }

    /**
     * List of affiliated organizations.
     */
    public List<AffiliatedOrgInfo> getAffiliatedOrgs() {
        if (affiliatedOrgs == null) {
            affiliatedOrgs = new ArrayList<AffiliatedOrgInfo>(0);
        }
        return affiliatedOrgs;
    }

    public void setAffiliatedOrgs(List<AffiliatedOrgInfo> affiliatedOrgs) {
        this.affiliatedOrgs = affiliatedOrgs;
    }

	/**
	 * 	Narrative description of the CLU Fee Record. 
	 */
    public RichTextInfo getDescr() {
		return descr;
	}

	public void setDescr(RichTextInfo descr) {
		this.descr = descr;
	}

	/**
     * List of key/value pairs, typically used for dynamic attributes.
     */
    public Map<String, String> getAttributes() {
        if (attributes == null) {
            attributes = new HashMap<String, String>();
        }
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    
    /**
	 * Create and last update info for the structure. This is optional and treated as read only since the 
	 * data is set by the internals of the service during maintenance operations.     
	 */
    public MetaInfo getMetaInfo() {
		return metaInfo;
	}

	public void setMetaInfo(MetaInfo metaInfo) {
		this.metaInfo = metaInfo;
	}

	/**
     * Identifier for the clu fee record.
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}