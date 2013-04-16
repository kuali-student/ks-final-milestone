/**
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or
 * agreed to in writing, software distributed under the License is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.lum.lo.dto;

import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.lum.lo.infc.LoCategory;
//import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlType;

import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Detailed information about a learning objective category.
 * 
 * @author Kuali Student Team (sambitpa@kuali.org)
 */
@XmlType(name = "LoCategoryInfo", propOrder = {"id", "typeKey", "stateKey", "name", "descr", "loRepositoryKey", "effectiveDate", "expirationDate", "meta", "attributes", "_futureElements" }) 
@XmlAccessorType(XmlAccessType.FIELD)
public class LoCategoryInfo extends IdEntityInfo implements LoCategory, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String loRepositoryKey;

    @XmlElement
    private Date effectiveDate;

    @XmlElement
    private Date expirationDate;

    @XmlAnyElement
    private List<Object> _futureElements;  

    public LoCategoryInfo() {

    }

    public LoCategoryInfo(LoCategory loCategory) {
        super(loCategory);
        if (loCategory != null) {
            this.loRepositoryKey = loCategory.getLoRepositoryKey();
            this.effectiveDate = new Date(loCategory.getEffectiveDate().getTime());
            this.expirationDate = new Date(loCategory.getExpirationDate().getTime());
        }
    }

    @Override
    public String getLoRepositoryKey() {
        return loRepositoryKey;
    }

    public void setLoRepositoryKey(String loRepositoryKey) {
        this.loRepositoryKey = loRepositoryKey;
    }

    @Override
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    @Override
    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Deprecated
	public MetaInfo getMetaInfo() {
		return this.getMeta();
	}

    @Deprecated
	public void setMetaInfo(MetaInfo metaInfo) {
		this.setMeta(metaInfo);		
	}

}