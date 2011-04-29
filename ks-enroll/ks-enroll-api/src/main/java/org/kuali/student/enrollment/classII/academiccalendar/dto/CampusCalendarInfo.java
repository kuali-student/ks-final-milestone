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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.dto.KeyEntityInfo;
import org.kuali.student.r2.common.infc.ModelBuilder;
import org.kuali.student.enrollment.classII.academiccalendar.infc.CampusCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CampusCalendarInfo", propOrder = {"key", "typeKey", "stateKey", "name", "descr", "startDate", "endDate", "location", "metaInfo", "attributes", "_futureElements"})
public class CampusCalendarInfo extends KeyEntityInfo implements CampusCalendar, Serializable {

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
    public CampusCalendarInfo(CampusCalendar campusCalendar) {
        super(campusCalendar);
        this.startDate = null != campusCalendar.getStartDate() ? new Date(campusCalendar.getStartDate().getTime()) : null;
        this.endDate = null != campusCalendar.getEndDate() ? new Date(campusCalendar.getEndDate().getTime()) : null;
        this.location = campusCalendar.getLocation();
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
    public String getLocation() {
        return location;
    }

    /**
     * The builder class for this CampusCalendarInfo.
     */
    public static class Builder extends KeyEntityInfo.Builder implements ModelBuilder<CampusCalendarInfo>, CampusCalendar {

        private Date startDate;
        private Date endDate;
        private String location;

        /**
         * Constructs a new builder.
         */
        public Builder() {
        }

        /**
         * Constructs a new builder initialized from another CampusCalendar
         */
        public Builder(CampusCalendar campusCalendar) {
            super(campusCalendar);
            this.startDate = campusCalendar.getStartDate();
            this.endDate = campusCalendar.getEndDate();
            this.location = campusCalendar.getLocation();
        }

        @Override
        public CampusCalendarInfo build() {
            return new CampusCalendarInfo(this);
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
        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }
}
