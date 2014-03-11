/*
 * Copyright 2011 The Kuali Foundation 
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.enrollment.lpr.dto;

import org.kuali.rice.core.api.util.jaxb.KualiDecimalAdapter;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.student.enrollment.lpr.infc.Lpr;
import org.kuali.student.r2.common.dto.RelationshipInfo;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LprInfo", propOrder = {
        "id", 
        "typeKey", 
        "stateKey", 
        "effectiveDate", 
        "expirationDate", 
        "luiId", 
        "personId", 
        "atpId",
        "masterLprId", 
        "resultValuesGroupKeys", 
        "commitmentPercent", 
        "meta", 
        "attributes",
        "_futureElements"})

public class LprInfo 
    extends RelationshipInfo 
    implements Lpr, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String luiId;

    @XmlElement
    private String personId;

    @XmlElement
    private String atpId;
    
    @XmlElement
    private String masterLprId;

    @XmlElement
    private List<String> resultValuesGroupKeys;

    @XmlElement
    @XmlJavaTypeAdapter(KualiDecimalAdapter.class)
    private KualiDecimal commitmentPercent;

    @XmlAnyElement
    private List<Element> _futureElements;

    public LprInfo() {
    }

    public LprInfo(Lpr lpr) {
        super(lpr);
        if (lpr != null) {
            this.luiId = lpr.getLuiId();
            this.personId = lpr.getPersonId();
            this.atpId = lpr.getAtpId();
            this.masterLprId = lpr.getMasterLprId();
            if (lpr.getCommitmentPercent() != null) {
                this.commitmentPercent = new KualiDecimal(lpr.getCommitmentPercent().bigDecimalValue());
            }
            if (lpr.getResultValuesGroupKeys() != null) {
                this.resultValuesGroupKeys = new ArrayList<String>(lpr.getResultValuesGroupKeys());
            }
        }
    }

    @Override
    public String getLuiId() {
        return luiId;
    }

    public void setLuiId(String luiId) {
        this.luiId = luiId;
    }

    @Override
    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    @Override
    public String getAtpId() {
        return atpId;
    }

    public void setAtpId(String atpId) {
        this.atpId = atpId;
    }

    @Override
    public String getMasterLprId() {
        return masterLprId;
    }

    public void setMasterLprId(String masterLprId) {
        this.masterLprId = masterLprId;
    }    

    @Override
    public KualiDecimal getCommitmentPercent() {
        return commitmentPercent;
    }

    public void setCommitmentPercent(KualiDecimal commitmentPercent) {
        this.commitmentPercent = commitmentPercent;
    }

    @Override
    public List<String> getResultValuesGroupKeys() {
        if (this.resultValuesGroupKeys == null) {
            this.resultValuesGroupKeys = new ArrayList<String>();
        }
        return resultValuesGroupKeys;
    }

    public void setResultValuesGroupKeys(List<String> resultValuesGroupKeys) {
        this.resultValuesGroupKeys = resultValuesGroupKeys;
    }
}
