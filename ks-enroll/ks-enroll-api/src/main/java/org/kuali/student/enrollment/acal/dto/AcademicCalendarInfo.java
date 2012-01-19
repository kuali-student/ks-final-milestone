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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.acal.infc.AcademicCalendar;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AcademicCalendarInfo", propOrder = { 
                "id", "typeKey", "stateKey", "name", "descr", 
                "holidayCalendarIds", "adminOrgId", "startDate", "endDate", 
		"meta", "attributes", "_futureElements" })

public class AcademicCalendarInfo 
    extends IdEntityInfo 
    implements AcademicCalendar, Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @XmlElement
    private List<String> holidayCalendarIds;

    @XmlElement
    private String adminOrgId;
    
    @XmlElement
    private Date startDate; 
    
    @XmlElement
    private Date endDate;
    
    @XmlAnyElement
    private List<Element> _futureElements;
    
    /**
     * Constructs a new AcademicCalendarInfo.
     */
    public AcademicCalendarInfo() {
    }

    /**
     * Constructs a new AcademicCalendarInfo from another AcademicCalendar.
     * 
     * @param acal the Academic Calendar to copy
     */
    public AcademicCalendarInfo(AcademicCalendar acal) {
        super(acal);

        if (acal != null) {
            if (acal.getHolidayCalendarIds() != null) {
                this.holidayCalendarIds = new ArrayList<String>(acal.getHolidayCalendarIds());
            }
            this.adminOrgId = acal.getAdminOrgId();
            if (acal.getStartDate() != null) {
                this.startDate = new Date(acal.getStartDate().getTime());
            }

            if (acal.getEndDate() != null) {
                this.endDate = new Date(acal.getEndDate().getTime());
            }
            this.adminOrgId = acal.getAdminOrgId();
            
            this.holidayCalendarIds = new ArrayList<String>(acal.getHolidayCalendarIds());
        }
    }

    @Override
    public List<String> getHolidayCalendarIds() {
        if (holidayCalendarIds == null) {
            holidayCalendarIds = new ArrayList<String>();
        }

        return holidayCalendarIds;
    }

    public void setHolidayCalendarIds(List<String> holidayCalendarIds) {
        this.holidayCalendarIds = holidayCalendarIds;
    }

    @Override
    public String getAdminOrgId() {
        return adminOrgId;
    }

    public void setAdminOrgId(String adminOrgId) {
        this.adminOrgId = adminOrgId;
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
