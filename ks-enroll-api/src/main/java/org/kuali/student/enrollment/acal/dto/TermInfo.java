/*
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
package org.kuali.student.enrollment.acal.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.acal.infc.Term;
import org.kuali.student.r2.common.dto.IdEntityInfo;

import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TermInfo", propOrder = {
                "id", "typeKey", "stateKey", "name", "descr", 
                "code", "startDate", "endDate", 
                "meta", "attributes", "_futureElements"})

public class TermInfo 
    extends IdEntityInfo 
    implements Term, Serializable {

    private static final long serialVersionUID = 1L;
    
    @XmlElement
    private String code;
    
    @XmlElement
    private Date startDate;
    
    @XmlElement
    private Date endDate;
    
    @XmlAnyElement
    private List<Element> _futureElements;

    /**
     * Constructs a new TermInfo.
     */
    public TermInfo() {
    }

    /**
     * Constructs a new TermInfo from another Term.
     *
     * @param term the Term to copy
     */
    public TermInfo(Term term) {
        super(term);

        if (term != null) {
            this.code = term.getCode();
            if (term.getStartDate() != null) {
                this.startDate = new Date(term.getStartDate().getTime());
            }

            if (term.getEndDate() != null) {
                this.endDate = new Date(term.getEndDate().getTime());
            }
        }
    }

    @Override
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    @Override
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public Date getEndDate() {
        return endDate;
    }
    
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
