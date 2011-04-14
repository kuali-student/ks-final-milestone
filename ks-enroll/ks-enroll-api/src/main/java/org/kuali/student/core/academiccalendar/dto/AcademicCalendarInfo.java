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
import org.kuali.student.r2.common.dto.KeyEntityInfo;

import org.kuali.student.core.academiccalendar.infc.AcademicCalendar;


/**
 * Information about an academic calendar.
 *
 * @Author tom
 * @Since Tue Apr 05 14:22:34 EDT 2011
 */ 

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AcademicCalendarInfo", propOrder = {"key", "typeKey", "stateKey", "name", "descr", "startDate", "endDate", "campusCalendarKey", "credentialProgramTypeKey", "metaInfo", "attributes", "_futureElements"})

public class AcademicCalendarInfo extends KeyEntityInfo implements AcademicCalendar, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private final Date startDate;

    @XmlElement
    private final Date endDate;

    @XmlElement
    private final String campusCalendarKey;

    @XmlElement 
    private final String credentialProgramTypeKey;

    @XmlAnyElement
    private final List<Element> _futureElements;  

    private AcademicCalendarInfo() {
        startDate = null;
        endDate = null;
        campusCalendarKey = null;
        credentialProgramTypeKey = null;
        _futureElements = null;
    }

    /**
     * Constructs a new AcademicCalendarInfo from another
     * AcademicCalendar.
     *
     * @param academicCalendar the Academic Calendar to copy
     */
    public AcademicCalendarInfo(AcademicCalendar academicCalendar) {
        super(academicCalendar);

        this.startDate = null != academicCalendar.getStartDate() ? new Date(academicCalendar.getStartDate().getTime()) : null;
        this.endDate = null != academicCalendar.getEndDate() ? new Date(academicCalendar.getEndDate().getTime()) : null;
        this.campusCalendarKey = academicCalendar.getCampusCalendarKey();
        this.credentialProgramTypeKey = academicCalendar.getCredentialProgramTypeKey();

        _futureElements = null;
    }

    /**
     * Date and time the academic calendar becomes effective. This
     * does not provide a bound on date ranges or milestones
     * associated with this time calendar, but instead indicates the
     * calendar proper. This is a similar concept to the effective
     * date on enumerated values. When an end date has been specified,
     * this field must be less than or equal to the end date.
     *
     * @return the Academic Calendar start date
     */
    @Override
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Date and time the academic calendar becomes
     * ineffective. This does not provide a bound on date ranges or
     * milestones associated with this calendar, but instead
     * indicates the calendar proper. If specified, this must be
     * greater than or equal to the start date. If this field is not
     * specified, then no end date has been currently defined
     * and should automatically be considered greater than the
     * effective date.
     *
     * @return the Academic Calendar end date
     */
    @Override
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Gets the campus calendar key corresponding to this academic
     * calendar.
     */
    @Override
    public String getCampusCalendarKey() {
        return campusCalendarKey;
    }

    /**
     * Gets the credential program type key to which this calendar
     * relates.
     */
    public String getCredentialProgramTypeKey() {
        return credentialProgramTypeKey;
    }

    /**
     * The builder class for this AcademicCalendarInfo.
     */
    public static class Builder extends KeyEntityInfo.Builder implements ModelBuilder<AcademicCalendarInfo>, AcademicCalendar {

        private Date startDate;
        private Date endDate;
        private String campusCalendarKey;
        private String credentialProgramTypeKey;


        /**
         * Constructs a new builder.
         */
        public Builder() {}

        /**
         * Constructs a new builder initialized from another AcademicCalendar
         */
        public Builder(AcademicCalendar academicCalendar) {
            super(academicCalendar);

            this.startDate = academicCalendar.getStartDate();
            this.endDate = academicCalendar.getEndDate();
            this.campusCalendarKey =  academicCalendar.getCampusCalendarKey();
            this.credentialProgramTypeKey = academicCalendar.getCredentialProgramTypeKey();
        }

        /**
         * Builds the AcademicCalendar.
         *
         * @return a new AcademicCalendar
         */
        public AcademicCalendarInfo build() {
            return new AcademicCalendarInfo(this);
        }

        /**
         * Gets the start date.
         *
         * @return the Academic Calendar start date
         */
        @Override
        public Date getStartDate() {
            return startDate;
        }

        /**
         * Sets the Academic Calendar start date.
         *
         * @param startDate the start date for the Academic Calendar
         */
        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }

        /**
         * Gets the start date.
         *
         * @return the Academic Calendar end date
         */
        @Override
        public Date getEndDate() {
            return endDate;
        }

        /**
         * Sets the Academic Calendar end date.
         *
         * @param endDate the end date for the Academic Calendar
         */

        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }

        /**
         * Gets the campus calendar key corresponding to this academic
         * calendar.
         */
        public String getCampusCalendarKey() {
            return campusCalendarKey;
        }

        public void setCampusCalendarKey(String campusCalendarKey) {
            this.campusCalendarKey = campusCalendarKey;
        }

        /**
         * Gets the credential program type key to which this calendar
         * relates.
         */
        public String getCredentialProgramTypeKey() {
            return credentialProgramTypeKey;
        }

        public void setCredentialProgramTypeKey(String credentialProgramTypeKey) {
            this.credentialProgramTypeKey = credentialProgramTypeKey;
        }
    }
}
