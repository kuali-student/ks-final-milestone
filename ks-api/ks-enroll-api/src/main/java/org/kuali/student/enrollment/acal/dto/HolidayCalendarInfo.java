/*
 * Copyright 2010 The Kuali Foundation Licensed under the Educational
 * Community License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a
 * copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.enrollment.acal.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.acal.infc.HolidayCalendar;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HolidayCalendarInfo", propOrder = { 
                "id", "typeKey", "stateKey", "name", "descr", 
                "campusKeys", "adminOrgId", "startDate", "endDate", 
                "meta", "attributes", "_futureElements" })

public class HolidayCalendarInfo 
    extends IdEntityInfo 
    implements HolidayCalendar, Serializable {
    
    private static final long serialVersionUID = 1L;

    @XmlElement
    private List<String> campusKeys;

    @XmlElement
    private String adminOrgId;

    @XmlElement
    private Date startDate;
	
    @XmlElement
    private Date endDate;
	
    @XmlAnyElement
    private List<Element> _futureElements;


    /**
     * Constructs a new HolidayCalendarInfo.
     */
    public HolidayCalendarInfo() {
    }
    
    /**
     * Constructs a new HolidayCalendarInfo from another
     * HolidayCalendar.
     * 
     * @param holidayCalendar the Holiday Calendar to copy
     */
    public HolidayCalendarInfo(HolidayCalendar holidayCalendar) {
        super(holidayCalendar);
        if (holidayCalendar != null) {
            if (holidayCalendar.getCampusKeys() != null) {
                campusKeys = new ArrayList<String>(holidayCalendar.getCampusKeys());
            }
            
            this.adminOrgId = holidayCalendar.getAdminOrgId();
            if (holidayCalendar.getStartDate() != null) {
                this.startDate = new Date (holidayCalendar.getStartDate().getTime());
            }

            if (holidayCalendar.getEndDate() != null) {
                this.endDate = new Date(holidayCalendar.getEndDate().getTime());
            }
        }
    }

    @Override
    public List<String> getCampusKeys() {
        if (campusKeys == null) {
            campusKeys = new ArrayList<String>();
        }

        return campusKeys;
    }

    public void setCampusKeys(List<String> campusKeys) {
        this.campusKeys = campusKeys;
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
