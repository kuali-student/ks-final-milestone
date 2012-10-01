/*
 * Copyright 2010 The Kuali Foundation 
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

package org.kuali.student.r2.common.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.infc.DateRange;
//import org.w3c.dom.Element;

/**
 * A DTO for a date range.
 *
 * @author tom
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DateRangeInfo", propOrder = {
                "startDate", "endDate" })//, "_futureElements" }) TODO KSCM-372: Non-GWT translatable code})

public class DateRangeInfo 
    implements DateRange, Serializable {
	
    private static final long serialVersionUID = 1L;

    @XmlElement
    private Date startDate;
    
    @XmlElement
    private Date endDate;
    
//    TODO KSCM-372: Non-GWT translatable code
//    @XmlAnyElement
//    private List<Element> _futureElements;    
    

    /**
     *  Constructs a new DateRangeInfo.
     */
    public DateRangeInfo() {
    }
	
    /**
     * Constructs a new DateRangeInfo from another DateRange.
     *
     * @param dateRange the DateRange to copy
     */
    public DateRangeInfo(DateRange dateRange) {
        if (dateRange != null) {
            if (dateRange.getStartDate() != null) {
                this.startDate = new Date(dateRange.getStartDate().getTime());
            }

            if (dateRange.getEndDate() != null) {
                this.endDate = new Date(dateRange.getEndDate().getTime());
            }
	}
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
