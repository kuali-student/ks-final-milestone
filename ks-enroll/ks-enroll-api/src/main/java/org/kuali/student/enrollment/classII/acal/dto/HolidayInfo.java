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
package org.kuali.student.enrollment.classII.acal.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.classII.acal.infc.Holiday;
import org.kuali.student.r2.common.dto.KeyEntityInfo;
import org.kuali.student.r2.common.infc.ModelBuilder;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HolidayInfo", propOrder = {"key", "typeKey", "stateKey", "name", "descr", "isInstructionalDay", "isAllDay", "isDateRange", "startDate", "endDate", "metaInfo", "attributes", "_futureElements"})
public class HolidayInfo extends KeyEntityInfo implements Holiday, Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private final Boolean isInstructionalDay;
    @XmlElement
    private final Boolean isAllDay;
    @XmlElement
    private final Boolean isDateRange;
    @XmlElement
    private final Date startDate;
    @XmlElement
    private final Date endDate;
    @XmlAnyElement
    private final List<Element> _futureElements;

    protected HolidayInfo() {
        isInstructionalDay = false;
        isAllDay = false;
        isDateRange = false;
        startDate = null;
        endDate = null;
        _futureElements = null;
    }

    /**
     * Constructs a new HolidayInfo from another Holiday.
     *
     * @param holiday the Holiday to copy
     */
    public HolidayInfo(Holiday holiday) {
        super(holiday);
        this.isInstructionalDay = holiday.getIsInstructionalDay();
        this.isAllDay = holiday.getIsAllDay();
        this.isDateRange = holiday.getIsDateRange();
        this.startDate = null != holiday.getStartDate() ? new Date(holiday.getStartDate().getTime()) : null;
        this.endDate = null != holiday.getEndDate() ? new Date(holiday.getEndDate().getTime()) : null;
        _futureElements = null;
    }

    @Override
    public Boolean getIsInstructionalDay() {
        return isInstructionalDay;
    }

    @Override
    public Boolean getIsAllDay() {
        return isAllDay;
    }

    @Override
    public Boolean getIsDateRange() {
        return isDateRange;
    }

    @Override
    public Date getStartDate() {
        return startDate;
    }

    @Override
    public Date getEndDate() {
        return endDate;
    }

    /**
     * The builder class for this HolidayInfo.
     */
    public static class Builder extends KeyEntityInfo.Builder implements ModelBuilder<HolidayInfo>, Holiday {

        private Boolean isInstructionalDay;
        private Boolean isAllDay;
        private Boolean isDateRange;
        private Date startDate;
        private Date endDate;

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
            this.isInstructionalDay = holiday.getIsInstructionalDay();
            this.isAllDay = holiday.getIsAllDay();
            this.isDateRange = holiday.getIsDateRange();
            this.startDate = null != holiday.getStartDate() ? new Date(holiday.getStartDate().getTime()) : null;
            this.endDate = null != holiday.getEndDate() ? new Date(holiday.getEndDate().getTime()) : null;
        }

        @Override
        public HolidayInfo build() {
            return new HolidayInfo(this);
        }

        @Override
        public Boolean getIsInstructionalDay() {
            return isInstructionalDay;
        }

        public void setIsInstructionalDay(Boolean isInstructionalDay) {
            this.isInstructionalDay = isInstructionalDay;
        }

        @Override
        public Boolean getIsAllDay() {
            return isAllDay;
        }

        public void setIsAllDay(Boolean isAllDay) {
            this.isAllDay = isAllDay;
        }

        @Override
        public Boolean getIsDateRange() {
            return isDateRange;
        }

        public void setIsDateRange(Boolean isDateRange) {
            this.isDateRange = isDateRange;
        }

        @Override
        public Date getStartDate() {
            return startDate;
        }

        public void setStartDate(Date startDate) {
            this.startDate = new Date(startDate.getTime());
        }

        @Override
        public Date getEndDate() {
            return endDate;
        }

        public void setEndDate(Date endDate) {
            this.endDate = new Date(endDate.getTime());
        }
    }
}
