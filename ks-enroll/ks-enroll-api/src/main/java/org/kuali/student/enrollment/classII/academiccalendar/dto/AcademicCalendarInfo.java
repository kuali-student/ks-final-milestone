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

package org.kuali.student.enrollment.classII.academiccalendar.dto;

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

import org.kuali.student.r2.common.dto.KeyEntityInfo;
import org.kuali.student.r2.common.infc.ModelBuilder;

import org.kuali.student.enrollment.classII.academiccalendar.infc.AcademicCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AcademicCalendarInfo", propOrder = {"key", "typeKey", "stateKey", "name", "descr", "startDate", "endDate", "campusCalendarKeys", "credentialProgramTypeKey", "metaInfo", "attributes", "_futureElements"})

public class AcademicCalendarInfo extends KeyEntityInfo implements AcademicCalendar, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private final Date startDate;

    @XmlElement
    private final Date endDate;

    @XmlElement
    private final List<String> campusCalendarKeys;

    @XmlElement 
    private final String credentialProgramTypeKey;

    @XmlAnyElement
    private final List<Element> _futureElements;  

    private AcademicCalendarInfo() {
        startDate = null;
        endDate = null;
        campusCalendarKeys = null;
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
        this.campusCalendarKeys = academicCalendar.getCampusCalendarKeys();
        this.credentialProgramTypeKey = academicCalendar.getCredentialProgramTypeKey();

        _futureElements = null;
    }


    @Override
    public Date getStartDate() {
        return startDate;
    }


    @Override
    public Date getEndDate() {
        return endDate;
    }


    @Override
    public List<String> getCampusCalendarKeys() {
        return campusCalendarKeys;
    }

    @Override
    public String getCredentialProgramTypeKey() {
        return credentialProgramTypeKey;
    }

    /**
     * The builder class for this AcademicCalendarInfo.
     */
    public static class Builder extends KeyEntityInfo.Builder implements ModelBuilder<AcademicCalendarInfo>, AcademicCalendar {

        private Date startDate;
        private Date endDate;
        private List<String> campusCalendarKeys;
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
            this.campusCalendarKeys =  academicCalendar.getCampusCalendarKeys();
            this.credentialProgramTypeKey = academicCalendar.getCredentialProgramTypeKey();
        }

 
        public AcademicCalendarInfo build() {
            return new AcademicCalendarInfo(this);
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


        @Override
        public List<String> getCampusCalendarKeys() {
            return campusCalendarKeys;
        }

        public void setCampusCalendarKeys(List<String> campusCalendarKeys) {
            this.campusCalendarKeys = campusCalendarKeys;
        }

        public void addCampusCalendarKey(String campusCalendarKey) {
            this.campusCalendarKeys.add(campusCalendarKey);
        }

        @Override
        public String getCredentialProgramTypeKey() {
            return credentialProgramTypeKey;
        }

        public void setCredentialProgramTypeKey(String credentialProgramTypeKey) {
            this.credentialProgramTypeKey = credentialProgramTypeKey;
        }
    }
}
