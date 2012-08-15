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

import org.kuali.student.r2.common.dto.IdNamelessEntityInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.lum.clu.infc.CluResult;
import org.kuali.student.r2.lum.clu.infc.ResultOption;

import javax.xml.bind.Element;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CluResultInfo", propOrder = {"id", "descr", "typeKey", "stateKey", "cluId", "resultOptions", "effectiveDate",
        "expirationDate", "meta", "attributes", "_futureElements"})
public class CluResultInfo extends IdNamelessEntityInfo implements Serializable, CluResult {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String cluId;

    @XmlElement
    private RichTextInfo descr;

    @XmlElement
    private List<ResultOptionInfo> resultOptions;

    @XmlElement
    private Date effectiveDate;

    @XmlElement
    private Date expirationDate;

    @XmlAnyElement
    private List<Element> _futureElements;

    public CluResultInfo() {

    }

    public CluResultInfo(CluResult cluResult) {
        super(cluResult);
        if (null != cluResult) {
            this.cluId = cluResult.getCluId();
            this.resultOptions = new ArrayList<ResultOptionInfo>();
            for (ResultOption resultOption : cluResult.getResultOptions()) {
                this.resultOptions.add(new ResultOptionInfo(resultOption));
            }
            this.effectiveDate = (null != cluResult.getEffectiveDate()) ? new Date(cluResult.getEffectiveDate().getTime()) : null;
            this.expirationDate = (null != cluResult.getExpirationDate()) ? new Date(cluResult.getExpirationDate().getTime()) : null;
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
    public RichTextInfo getDescr() {
        return descr;
    }

    public void setDescr(RichTextInfo descr) {
        this.descr = descr;
    }

    @Override
    public List<ResultOptionInfo> getResultOptions() {
        if (resultOptions == null) {
            resultOptions = new ArrayList<ResultOptionInfo>(0);
        }
        return resultOptions;
    }

    public void setResultOptions(List<ResultOptionInfo> resultOptions) {
        this.resultOptions = resultOptions;
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
        return "CluResultInfo[id=" + this.getId() + ", cluId=" + this.getCluId() + ", type=" + this.getTypeKey() + "]";
    }
}