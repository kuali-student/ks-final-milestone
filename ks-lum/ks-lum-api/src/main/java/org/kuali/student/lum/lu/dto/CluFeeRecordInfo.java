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
    private CurrencyAmountInfo feeAmount;

    @XmlElement
    private List<AffiliatedOrgInfo> affiliatedOrgInfoList;

    @XmlElement
    @XmlJavaTypeAdapter(JaxbAttributeMapListAdapter.class)
    private Map<String, String> attributes;

    @XmlAttribute
    private String id;

    /**
     * Any finite sequence of characters with letters, numerals, symbols and punctuation marks. The length can be any natural number between zero or any positive integer.
     */
    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    /**
     * The page currencyAmount Structure v1.0-rc1 does not exist.
     */
    public CurrencyAmountInfo getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(CurrencyAmountInfo feeAmount) {
        this.feeAmount = feeAmount;
    }

    /**
     * List of affiliated organizations.
     */
    public List<AffiliatedOrgInfo> getAffiliatedOrgInfoList() {
        if (affiliatedOrgInfoList == null) {
            affiliatedOrgInfoList = new ArrayList<AffiliatedOrgInfo>(0);
        }
        return affiliatedOrgInfoList;
    }

    public void setAffiliatedOrgInfoList(List<AffiliatedOrgInfo> affiliatedOrgInfoList) {
        this.affiliatedOrgInfoList = affiliatedOrgInfoList;
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
     * Identifier for the clu fee record.
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}