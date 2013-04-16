/*
 * Copyright 2012 The Kuali Foundation 
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

package org.kuali.student.enrollment.courseregistration.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.courseregistration.infc.CreditLoad;
import org.w3c.dom.Element;
 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreditLoadInfo", propOrder = {
                "studentId", "creditLoad", "creditLimit", "additionalCredits",
                "_futureElements"})

public class CreditLoadInfo 
    implements CreditLoad, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String studentId;

    @XmlElement
    private String creditLoad;

    @XmlElement
    private String creditLimit;

    @XmlElement
    private String additionalCredits;

    @XmlAnyElement
    private List<Element> _futureElements;


    /**
     * Constructs a new CreditLoadInfo.
     */
    public CreditLoadInfo() {
    }

    /**
     * Constructs a new CreditLoadInfo from another CreditLoad.
     *
     * @param creditLoad the CreditLoad to copy
     */

    public CreditLoadInfo(CreditLoad creditLoad) {
        if (creditLoad != null) {
            this.studentId = creditLoad.getStudentId();
            this.creditLoad = creditLoad.getCreditLoad();
            this.creditLimit = creditLoad.getCreditLimit();
            this.additionalCredits = creditLoad.getAdditionalCredits();
        }
    }
  
    @Override
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @Override
    public String getCreditLoad() {
        return creditLoad;
    }

    public void setCreditLoad(String creditLoad) {
        this.creditLoad = creditLoad;
    }

    @Override
    public String getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(String creditLimit) {
        this.creditLimit = creditLimit;
    }

    @Override
    public String getAdditionalCredits() {
        return additionalCredits;
    }

    public void setAdditionalCredits(String credits) {
        this.additionalCredits = credits;
    }
}
