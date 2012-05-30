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

package org.kuali.student.r2.core.statement.dto;

import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.IdNamelessEntityInfo;
import org.kuali.student.r2.core.statement.infc.ReqCompField;
import org.kuali.student.r2.core.statement.infc.ReqComponent;
//import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
//import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Information about a requirement component.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReqComponentInfo", propOrder = {"id", "typeKey", "stateKey", "descr",
        "reqCompFields", "naturalLanguageTranslation", "effectiveDate", "expirationDate", "meta", "attributes"})//, "_futureElements" }) TODO KSCM-372: Non-GWT translatable code})
public class ReqComponentInfo extends IdNamelessEntityInfo implements ReqComponent, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private List<ReqCompFieldInfo> reqCompFields;
    @XmlElement
    private RichTextInfo descr;
    @XmlAttribute
    private String naturalLanguageTranslation;
    @XmlElement
    private Date effectiveDate;
    @XmlElement
    private Date expirationDate;
//    TODO KSCM-372: Non-GWT translatable code
//    @XmlAnyElement
//    private List<Element> _futureElements;


    public ReqComponentInfo() {
    }

    public ReqComponentInfo(ReqComponent input) {
        super(input);
        if (null != input) {
            this.reqCompFields = new ArrayList<ReqCompFieldInfo>();
            for (ReqCompField reqCompField : input.getReqCompFields()) {
                this.reqCompFields.add(new ReqCompFieldInfo(reqCompField));
            }
            if (input.getDescr() != null) {
                this.descr = new RichTextInfo(input.getDescr());
            }
            this.effectiveDate = (null != input.getEffectiveDate()) ? new Date(input.getEffectiveDate().getTime()) : null;
            this.expirationDate = (null != input.getExpirationDate()) ? new Date(input.getExpirationDate().getTime()) : null;
            this.naturalLanguageTranslation = input.getNaturalLanguageTranslation();
        }
    }

    @Override
    public List<ReqCompFieldInfo> getReqCompFields() {
        if (null == reqCompFields) {
            reqCompFields = new ArrayList<ReqCompFieldInfo>();
        }
        return this.reqCompFields;
    }

    public void setReqCompFields(List<ReqCompFieldInfo> reqCompFields) {
        this.reqCompFields = reqCompFields;
    }

    @Override
    public Date getEffectiveDate() {
        return this.effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    @Override
    public Date getExpirationDate() {
        return this.expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
    
    @Override
    public RichTextInfo getDescr() {
        return descr;
    }

    public void setDescr(RichTextInfo descr) {
        this.descr = descr;
    }

    @Override
    public String getNaturalLanguageTranslation() {
        return this.naturalLanguageTranslation;
    }

    public void setNaturalLanguageTranslation(String naturalLanguageTranslation) {
        this.naturalLanguageTranslation = naturalLanguageTranslation;
    }

}
