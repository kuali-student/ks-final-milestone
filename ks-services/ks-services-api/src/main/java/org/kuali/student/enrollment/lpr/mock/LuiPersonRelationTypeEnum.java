/*
 * Copyright 2011 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.enrollment.lpr.mock;

import org.kuali.student.common.infc.AttributeInfc;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.kuali.student.enrollment.lpr.infc.LuiPersonRelationTypeInfc;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationConstants;

/**
 * Types for Learning Person Relations
 * <p/>
 * See https://wiki.kuali.org/display/STUDENT/LuiPersonRelation+Types+and+States
 * 
 * @author nwright
 */
public enum LuiPersonRelationTypeEnum implements LuiPersonRelationTypeInfc, Serializable {

    INSTRUCTOR_MAIN(LuiPersonRelationConstants.INSTRUCTOR_MAIN_TYPE_KEY, "Main Instructor", "Main instructor(s) responsible for course or section ", asDate("20100101"), null, null),
    INSTRUCTOR_ASSISTANT(LuiPersonRelationConstants.INSTRUCTOR_ASSISTANT_TYPE_KEY, "Assistant Instructor", "Person who assists the main instructor but is still considered an \"instructor\"", asDate("20100101"), null, null),
    INSTRUCTOR_SUPPORT(LuiPersonRelationConstants.INSTRUCTOR_SUPPORT_TYPE_KEY, "Support Instructor", "Persons who support the course but not in any official teaching role", asDate("20100101"), null, null),
    REGISTRANT(LuiPersonRelationConstants.REGISTRANT_TYPE_KEY, "Registrant", "Registrant who is taking course or section", asDate("20010101"), null, null),
    /**
     * Programs
     */
    ENROLLEE(LuiPersonRelationConstants.ENROLLEE_TYPE_KEY, "Enrollee", "Enrollee in the program", asDate("20010101"), null, null),
    ADVISOR(LuiPersonRelationConstants.ADVISOR_TYPE_KEY, "Advisor", "Advisor to students in the program", asDate("20010101"), null, null);
    /**
     * Types used for isntructors of courses
     */
    public static final LuiPersonRelationTypeEnum[] COURSE_INSTRUCTOR_TYPES = {INSTRUCTOR_MAIN, INSTRUCTOR_ASSISTANT, INSTRUCTOR_SUPPORT};

    /**
     * REF OBJECT URI FOR LPR
     */
    public static final String REF_OBJECT_URI = "http://student.kuali.org/LuiPersonRelationService/LuiPersionRelationInfo";

    /**
     * Types used for students in courses
     */
    public static final LuiPersonRelationTypeEnum[] COURSE_STUDENT_TYPES = {REGISTRANT};
    private static final long serialVersionUID = 1L;
    private String name;
    private String descr;
    private Date effectiveDate;
    private Date expirationDate;
    private List<? extends AttributeInfc> attributes;
    private String key;

    LuiPersonRelationTypeEnum(String key, String name, String descr, Date effectiveDate, Date expirationDate, List<AttributeInfc> attributes) {
        this.key = key;
        this.name = name;
        this.descr = descr;
        this.effectiveDate = effectiveDate;
        this.expirationDate = expirationDate;
        this.attributes = attributes;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescr() {
        return this.descr;
    }

    @Override
    public Date getEffectiveDate() {
        return this.effectiveDate;
    }

    @Override
    public Date getExpirationDate() {
        return this.expirationDate;
    }

    @Override
    public String getRefObjectURI() {
        return REF_OBJECT_URI;        
    }

    @Override
    public List<? extends AttributeInfc> getAttributes() {
        return this.attributes;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    private static Date asDate(String dateStr) {
        if (dateStr == null) {
            return null;
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        try {
            return df.parse(dateStr);
        } catch (ParseException ex) {
            throw new IllegalArgumentException(ex);
        }
    }
}
