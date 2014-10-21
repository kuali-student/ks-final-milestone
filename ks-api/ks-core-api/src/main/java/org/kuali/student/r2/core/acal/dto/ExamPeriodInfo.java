/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
 *
 * Created by mahtabme on 7/17/13
 */
package org.kuali.student.r2.core.acal.dto;

import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.core.acal.infc.ExamPeriod;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;
import java.util.List;

/**
 * This class represents an ExamPeriod.
 *
 * @author Kuali Student Team
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExamPeriodInfo", propOrder = {
        "id", "typeKey", "stateKey", "name", "descr",
        "code", "startDate", "endDate", "adminOrgId",
        "meta", "attributes", "_futureElements"})

public class ExamPeriodInfo extends IdEntityInfo implements ExamPeriod {

    ////////////////////////
    // CONSTANTS
    ////////////////////////

    private static final long serialVersionUID = 1L;

    ////////////////////////
    // DATA VARIABLES
    ////////////////////////

    @XmlElement
    private String code;

    @XmlElement
    private Date startDate;

    @XmlElement
    private Date endDate;

    @XmlElement
    private String adminOrgId;

    @XmlAnyElement
    private List<Element> _futureElements;

    ////////////////////////
    // GETTERS AND SETTERS
    ////////////////////////

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getAdminOrgId() {
        return adminOrgId;
    }

    public void setAdminOrgId(String adminOrgId) {
        this.adminOrgId = adminOrgId;
    }

    ////////////////////////
    // CONSTRUCTORS
    ////////////////////////

    /**
     * Constructs a new ExamPeriod.
     */
    public ExamPeriodInfo() {}

    /**
     * Constructs a new ExamPeriodInfo from another ExamPeriod.
     *
     * @param examPeriod the ExamPeriod to copy
     */
    public ExamPeriodInfo(ExamPeriod examPeriod) {
        super(examPeriod);
        if (examPeriod!= null) {
            this.code = examPeriod.getCode();
            this.adminOrgId = examPeriod.getAdminOrgId();
            if (examPeriod.getStartDate() != null) {
                this.startDate = new Date(examPeriod.getStartDate().getTime());
            }
            if (examPeriod.getEndDate() != null) {
                this.endDate = new Date(examPeriod.getEndDate().getTime());
            }
        }
    }


}
