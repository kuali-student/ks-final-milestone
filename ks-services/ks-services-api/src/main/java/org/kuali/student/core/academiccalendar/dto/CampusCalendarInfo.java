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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.common.infc.ModelBuilder;
import org.kuali.student.common.dto.KeyEntityInfo;
import org.kuali.student.core.academiccalendar.infc.CampusCalendarInfc;


/**
 * Information about an campus calendar.
 *
 * @Author tom
 * @Since Tue Apr 05 14:22:34 EDT 2011
 */ 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CampusCalendarInfo", propOrder = {"key", "typeKey", "stateKey", "name", "descr", "startDate", "endDate", "location", "metaInfo", "attributes", "_futureElements"})
public class CampusCalendarInfo extends KeyEntityInfo implements CampusCalendarInfc, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private final Date startDate;

    @XmlElement
    private final Date endDate;

    @XmlElement
    private final String location;

    @XmlAnyElement
    private final List<Element> _futureElements;  

    private CampusCalendarInfo() {
        startDate = null;
        endDate = null;
        location = null;
        _futureElements = null;
    }

    /**
     * Constructs a new CampusCalendarInfo from another
     * CampusCalendar.
     *
     * @param campusCalendar the Campus Calendar to copy
     */
    public CampusCalendarInfo(CampusCalendarInfc campusCalendar) {
        super(campusCalendar);
        this.startDate = null != campusCalendar.getStartDate() ? new Date(campusCalendar.getStartDate().getTime()) : null;
        this.endDate = null != campusCalendar.getEndDate() ? new Date(campusCalendar.getEndDate().getTime()) : null;
        this.location = campusCalendar.getLocation();
        _futureElements = null;
    }

    /**
     * Name: StartDate
     * Date and time the campus time period becomes effective. This
     * does not provide a bound on date ranges or milestones
     * associated with this time period, but instead indicates the
     * time period proper. This is a similar concept to the effective
     * date on enumerated values. When an end date has been specified,
     * this field must be less than or equal to the end date.
     *
     * @return the Campus Calendar start date
     */
    @Override
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Name: EndDate
     * Date and time the campus time period becomes
     * ineffective. This does not provide a bound on date ranges or
     * milestones associated with this time period, but instead
     * indicates the time period proper. If specified, this must be
     * greater than or equal to the start date. If this field is not
     * specified, then no end date has been currently defined
     * and should automatically be considered greater than the
     * effective date.
     *
     * @return the Campus Calendar end date
     */
    @Override
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Name: Location
     * The campus or location to which this calendar pertains.
     */
    @Override
    public String getLocation() {
        return location;
    }

    /**
     * The builder class for this CampusCalendarInfo.
     */
    public static class Builder extends KeyEntityInfo.Builder implements ModelBuilder<CampusCalendarInfo>, CampusCalendarInfc {

        private Date startDate;
        private Date endDate;
        private String location;

        /**
         * Constructs a new builder.
         */
        public Builder() {}

        /**
         * Constructs a new builder initialized from another CampusCalendar
         */
        public Builder(CampusCalendarInfc campusCalendar) {
            super(campusCalendar);
            this.startDate = campusCalendar.getStartDate();
            this.endDate = campusCalendar.getEndDate();
            this.location = campusCalendar.getLocation();
        }

        /**
         * Builds the CampusCalendar.
         *
         * @return a new CampusCalendar
         */
        public CampusCalendarInfo build() {
            return new CampusCalendarInfo(this);
        }

        /**
         * Gets the start date.
         *
         * @return the Campus Calendar start date
         */
        @Override
        public Date getStartDate() {
            return startDate;
        }

        /**
         * Sets the Campus Calendar start date.
         *
         * @param startDate the start date for the Campus Calendar
         */
        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }

        /**
         * Gets the start date.
         *
         * @return the Campus Calendar end date
         */
        @Override
        public Date getEndDate() {
            return endDate;
        }

        /**
         * Sets the Campus Calendar end date.
         *
         * @param endDate the end date for the Campus Calendar
         */

        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }

        /**
         * The campus or location to which this calendar pertains.
         */
        @Override
        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }
}
