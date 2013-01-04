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

package org.kuali.student.r2.lum.clu.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.dto.IdNamelessEntityInfo;
import org.kuali.student.r2.lum.clu.infc.CluPublication;
import org.kuali.student.r2.lum.clu.infc.Field;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CluPublicationInfo", propOrder = {"id", "typeKey", "stateKey", "cluId", "variants", "startCycle", "endCycle", "effectiveDate",
        "expirationDate", "meta", "attributes" , "_futureElements" }) 
public class CluPublicationInfo extends IdNamelessEntityInfo implements Serializable, CluPublication {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String cluId;

    @XmlElement
    private List<FieldInfo> variants;

    @XmlElement
    private String startCycle;

    @XmlElement
    private String endCycle;

    @XmlElement
    private Date effectiveDate;

    @XmlElement
    private Date expirationDate;

    @XmlAnyElement
    private List<Object> _futureElements;  

    public CluPublicationInfo() {

    }

    public CluPublicationInfo(CluPublication cluPublication) {
        super(cluPublication);
        if (null != cluPublication) {
            this.cluId = cluPublication.getCluId();
            this.variants = new ArrayList<FieldInfo>();
            for (Field field : cluPublication.getVariants()) {
                this.variants.add(new FieldInfo(field));
            }
            this.startCycle = cluPublication.getStartCycle();
            this.endCycle = cluPublication.getEndCycle();
            this.effectiveDate = (null != cluPublication.getEffectiveDate()) ? new Date(cluPublication.getEffectiveDate().getTime()) : null;
            this.expirationDate = (null != cluPublication.getExpirationDate()) ? new Date(cluPublication.getExpirationDate().getTime()) : null;
        }
    }

    @Override
    public String getCluId() {
        return cluId;
    }

    public void setCluId(String cluId) {
        this.cluId = cluId;
    }

    @Override
    public List<FieldInfo> getVariants() {
        if (variants == null) {
            variants = new ArrayList<FieldInfo>(0);
        }
        return variants;
    }

    public void setVariants(List<FieldInfo> variants) {
        this.variants = variants;
    }

    @Override
    public String getStartCycle() {
        return startCycle;
    }

    public void setStartCycle(String startCycle) {
        this.startCycle = startCycle;
    }

    @Override
    public String getEndCycle() {
        return endCycle;
    }

    public void setEndCycle(String endCycle) {
        this.endCycle = endCycle;
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

    @Override
    public String toString() {
        return "CluPublicationInfo[id=" + this.getId() + ", cluId=" + this.getCluId() + ", type=" + this.getTypeKey() + "]";
    }

}