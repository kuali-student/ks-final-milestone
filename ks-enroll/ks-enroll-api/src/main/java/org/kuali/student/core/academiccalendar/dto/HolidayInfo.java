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

package org.kuali.student.core.academiccalendar.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.common.infc.ModelBuilder;
import org.kuali.student.r2.common.dto.KeyEntityInfo;
import org.kuali.student.core.academiccalendar.infc.Holiday;


/**
 * Information about a holiday.
 *
 * @Author tom
 * @Since Tue Apr 05 14:22:34 EDT 2011
 */ 

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HolidayInfo", propOrder = {"key", "typeKey", "stateKey", "name", "descr", "isDateRange", "startDate", "endDate", "isInstructionalDay", "isExamDay", "metaInfo", "attributes", "_futureElements"})

public class HolidayInfo extends KeyEntityInfo implements Holiday, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private final Boolean isDateRange;

    @XmlElement
    private final Date startDate;

    @XmlElement
    private final Date endDate;

    @XmlElement
    private final Boolean isInstructionalDay;

    @XmlElement
    private final Boolean isExamDay;

    @XmlAnyElement
    private final List<Element> _futureElements;  

    private HolidayInfo() {
	isDateRange = false;
	startDate = null;
	endDate = null;
	isInstructionalDay = false;
	isExamDay = false;
	_futureElements = null;
    }

    /**
     * Constructs a new HolidayInfo from another Holiday.
     *
     * @param holiday the Holiday to copy
     */
    public HolidayInfo(Holiday holiday) {
        super(holiday);
	this.isDateRange = holiday.getIsDateRange();
        this.startDate = null != holiday.getStartDate() ? new Date(holiday.getStartDate().getTime()) : null;
        this.endDate = null != holiday.getEndDate() ? new Date(holiday.getEndDate().getTime()) : null;
	this.isInstructionalDay = holiday.getIsInstructionalDay();
	this.isExamDay = holiday.getIsExamDay();
	_futureElements = null;
    }

    /**
     * Name: IsDateRange
     * Tests if this holiday has a date range. If true, the end date
     * value follows the start date.
     *
     * @return true if this Holiday has different start end end
     *         dates, false if this Holiday represents a single date
     */
    @Override
    public Boolean getIsDateRange() {
        return isDateRange;
    }

    /**
     * Name: StartDate
     * Gets the start Date and time of the holiday.
     *
     * @return the holiday start
     */
    @Override
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Name: EndDate
     * Gets the end Date and time of the holiday.
     *
     * @return the holiday end
     */
    @Override
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Name: IsInstructionalDay
     * Tests if this holiday is an instructional day. 
     *
     * @return true if this holiday is an instructional day, false if
     *         it does not count as an instructional day
     */
    @Override
    public Boolean getIsInstructionalDay() {
	return isInstructionalDay;
    }

    /**
     * Name: IsExamDay
     * Tests if exams are permitted on this holiday.
     *
     * @return true if this holiday is an exam day, false otherwise
     */
    @Override
    public Boolean getIsExamDay() {
	return isExamDay;
    }

    /**
     * The builder class for this HolidayInfo.
     */
    public static class Builder extends KeyEntityInfo.Builder implements ModelBuilder<HolidayInfo>, Holiday {

	private Boolean isDateRange;
        private Date startDate;
        private Date endDate;
	private Boolean isInstructionalDay;
	private Boolean isExamDay;

	/**
	 * Constructs a new builder.
	 */
        public Builder() {
        }

	/**
	 *  Constructs a new builder initialized from another
	 *  Holiday.
	 */
        public Builder(Holiday holiday) {
            super(holiday);
	    this.isDateRange = holiday.getIsDateRange();
	    this.startDate = null != holiday.getStartDate() ? new Date(holiday.getStartDate().getTime()) : null;
	    this.endDate = null != holiday.getEndDate() ? new Date(holiday.getEndDate().getTime()) : null;
	    this.isInstructionalDay = holiday.getIsInstructionalDay();
	    this.isExamDay = holiday.getIsExamDay();
        }

	/**
	 * Builds the Holiday.
	 *
	 * @return a new Holiday
	 */
        public HolidayInfo build() {
            return new HolidayInfo(this);
        }

	/**
	 * Tests if this holiday has a date range. If true, the end date
	 * value follows the start date.
	 *
	 * @return true if this Holiday has different start end end
	 *         dates, false if this Holiday represents a single date
	 */
	@Override
	public Boolean getIsDateRange() {
	    return isDateRange;
	}

	/**
	 * Sets the date range flag (should this flag be inferred from
	 * the dates?)
	 *
	 * @param isDateRange true if this Holiday has different
	 *         start end end dates, false if this Holiday
	 *         represents a single date
	 */
	public void setIsDateRange(Boolean isDateRange) {
	    this.isDateRange = isDateRange;
	}

	/**
	 * Gets the start date.
	 *
	 * @return the Holiday start date
	 */
        @Override
        public Date getStartDate() {
            return startDate;
        }

	/**
	 * Sets the Holiday start date.
	 *
	 * @param endDate the start date
	 */
        public void setStartDate(Date startDate) {
            this.startDate = new Date(startDate.getTime());
        }

	/**
	 * Gets the start date.
	 *
	 * @return the Holiday end date
	 */
        @Override
        public Date getEndDate() {
            return endDate;
        }

	/**
	 * Sets the Holiday end date.
	 *
	 * @param endDate the end date
	 */
        public void setEndDate(Date endDate) {
            this.endDate = new Date(endDate.getTime());
        }

	/**
	 * Tests if this holiday is an instructional day. 
	 *
	 * @return true if this holiday is an instructional day, false if
	 *         it does not count as an instructional day
	 */
	public Boolean getIsInstructionalDay() {
	    return isInstructionalDay;
	}

	public void setIsInstructionalDay(Boolean isInstructionalDay) {
	    this.isInstructionalDay = isInstructionalDay;
	}

	/**
	 * Tests if this holiday is an exam day. 
	 *
	 * @return true if this holiday is an exam day, false if
	 *         it does not count as an exam day
	 */
	public Boolean getIsExamDay() {
	    return isExamDay;
	}

	public void setIsExamDay(Boolean isExamDay) {
	    this.isExamDay = isExamDay;
	}
    }
}
