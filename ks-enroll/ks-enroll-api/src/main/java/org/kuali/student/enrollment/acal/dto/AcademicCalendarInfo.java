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
import org.kuali.student.r2.common.dto.KeyEntityInfo;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.infc.Meta;
import org.kuali.student.r2.common.infc.RichText;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AcademicCalendarInfo", propOrder = {"key", "typeKey", "stateKey", "name", "descr", "startDate", "endDate", "campusCalendarKeys", "credentialProgramTypeKey", "metaInfo", "attributes", "_futureElements"})

public class AcademicCalendarInfo extends TermInfo implements AcademicCalendar, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private List<String> campusCalendarKeys;

    @XmlElement 
    private String credentialProgramTypeKey;

    
    public static AcademicCalendarInfo newInstance() {
        return new AcademicCalendarInfo();
    }
    
    public static AcademicCalendarInfo getInstance(AcademicCalendarInfo academicCalendarInfo) {
        return new AcademicCalendarInfo(academicCalendarInfo);
    }
    
    public static AcademicCalendarInfo getInstance(String key, String name, RichText descr,
                                                    Date startDate, Date endDate,
                                                    String typeKey, String stateKey,
                                                    List<String> campusCalendarKeys, String credentialProgramTypeKey,
                                                    List<? extends Attribute> attributes, Meta metaInfo) {
        return new AcademicCalendarInfo(key, name, descr, startDate, endDate, typeKey, stateKey, campusCalendarKeys, credentialProgramTypeKey, attributes, metaInfo);
    }
    
    private AcademicCalendarInfo() {
        campusCalendarKeys = null;
        credentialProgramTypeKey = null;
    }

    /**
     * Constructs a new AcademicCalendarInfo from another
     * AcademicCalendar.
     *
     * @param academicCalendar the Academic Calendar to copy
     */
    private AcademicCalendarInfo(AcademicCalendar academicCalendar) {
        super(academicCalendar);
        if (null != academicCalendar) {
	
	        this.campusCalendarKeys = null != academicCalendar.getCampusCalendarKeys() ? new ArrayList<String>(academicCalendar.getCampusCalendarKeys()) : null;
	        this.credentialProgramTypeKey = academicCalendar.getCredentialProgramTypeKey();
        }
    }

    private AcademicCalendarInfo(String key, String name, RichText descr,
                                    Date startDate, Date endDate,
                                    String typeKey, String stateKey,
                                    List<String> campusCalendarKeys, String credentialProgramTypeKey,
                                    List<? extends Attribute> attributes, Meta metaInfo) {
        super(key, name, descr, endDate, endDate, typeKey, stateKey, attributes, metaInfo);
        this.campusCalendarKeys = new ArrayList<String>(campusCalendarKeys);
        this.credentialProgramTypeKey = credentialProgramTypeKey;
    }

    @Override
    public List<String> getCampusCalendarKeys() {
        return campusCalendarKeys;
    }

    @Override
    public void setCampusCalendarKeys(List<String> campusCalendarKeys) {
        this.campusCalendarKeys = campusCalendarKeys;
    }

    @Override
    public String getCredentialProgramTypeKey() {
        return credentialProgramTypeKey;
    }

    @Override
    public void setCredentialProgramTypeKey(String credentialProgramTypeKey) {
        this.credentialProgramTypeKey = credentialProgramTypeKey;
    }
}
