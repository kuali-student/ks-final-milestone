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

import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.dto.IdNamelessEntityInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.lum.clu.infc.ResultOption;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Date;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResultOptionInfo", propOrder = {"id", "descr", "typeKey", "stateKey", "resultUsageTypeKey", "resultComponentId", "effectiveDate",
        "expirationDate", "meta", "attributes" })//, "_futureElements" }) TODO KSCM-372: Non-GWT translatable code})
public class ResultOptionInfo extends IdNamelessEntityInfo implements ResultOption, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private RichTextInfo descr;

    @XmlElement
    private String resultUsageTypeKey;

    @XmlElement
    private String resultComponentId;

    @XmlElement
    private Date effectiveDate;

    @XmlElement
    private Date expirationDate;

//    TODO KSCM-372: Non-GWT translatable code
//    @XmlAnyElement
//    private List<Element> _futureElements;

    public ResultOptionInfo() {

    }

    public ResultOptionInfo(ResultOption resultOption) {
        super(resultOption);
        if (null != resultOption) {
            this.resultUsageTypeKey = resultOption.getResultUsageTypeKey();
            this.resultComponentId = resultOption.getResultComponentId();
            this.effectiveDate = (null != resultOption.getEffectiveDate()) ? new Date(resultOption.getEffectiveDate().getTime()) : null;
            this.expirationDate = (null != resultOption.getExpirationDate()) ? new Date(resultOption.getExpirationDate().getTime()) : null;
        }
    }

    @Override
    public RichTextInfo getDescr() {
        return descr;
    }

    public void setDescr(RichTextInfo descr) {
        this.descr = descr;
    }

    @Override
    public String getResultUsageTypeKey() {
        return resultUsageTypeKey;
    }

    public void setResultUsageTypeKey(String resultUsageTypeKey) {
        this.resultUsageTypeKey = resultUsageTypeKey;
    }

    @Override
    public String getResultComponentId() {
        return resultComponentId;
    }

    public void setResultComponentId(String resultComponentId) {
        this.resultComponentId = resultComponentId;
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

}