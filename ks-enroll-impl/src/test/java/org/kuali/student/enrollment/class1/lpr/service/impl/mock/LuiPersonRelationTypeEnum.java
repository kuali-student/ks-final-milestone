/*
 * Copyright 2011 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.enrollment.class1.lpr.service.impl.mock;

import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.infc.Meta;
import org.kuali.student.r2.common.infc.RichText;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.class1.type.infc.Type;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Types for Learning Person Relations
 * <p/>
 * See https://wiki.kuali.org/display/STUDENT/LuiPersonRelation+Types+and+States
 * 
 * @author nwright
 */
public enum LuiPersonRelationTypeEnum implements Type, Serializable {

    INSTRUCTOR_MAIN(LprServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY, "Main Instructor", "Main instructor(s) responsible for course or section ", asDate("20100101"), null, null, null),
        INSTRUCTOR_ASSISTANT(LprServiceConstants.INSTRUCTOR_ASSISTANT_TYPE_KEY, "Assistant Instructor", "Person who assists the main instructor but is still considered an \"instructor\"", asDate("20100101"), null, null, null),
        INSTRUCTOR_SUPPORT(LprServiceConstants.INSTRUCTOR_SUPPORT_TYPE_KEY, "Support Instructor", "Persons who support the course but not in any official teaching role", asDate("20100101"), null, null, null),
        REGISTRANT(LprServiceConstants.REGISTRANT_TYPE_KEY, "Registrant", "Registrant who is taking course or section", asDate("20010101"), null, null, null),
    /**
     * Programs
     */
        ENROLLEE(LprServiceConstants.ENROLLEE_TYPE_KEY, "Enrollee", "Enrollee in the program", asDate("20010101"), null, null, null),
        ADVISOR(LprServiceConstants.ADVISOR_TYPE_KEY, "Advisor", "Advisor to students in the program", asDate("20010101"), null, null, null);
    /**
     * Types used for isntructors of courses
     */
    public static final LuiPersonRelationTypeEnum[] COURSE_INSTRUCTOR_TYPES = {INSTRUCTOR_MAIN, INSTRUCTOR_ASSISTANT, INSTRUCTOR_SUPPORT};

    /**
     * REF OBJECT URI FOR LPR
     */
    public static final String REF_OBJECT_URI = "http://student.kuali.org/LprService/LuiPersionRelationInfo";
    public static final String SERVICE_URI = LprServiceConstants.REF_OBJECT_URI_LUI_PERSON_RELATION;

    /**
     * Types used for students in courses
     */
    public static final LuiPersonRelationTypeEnum[] COURSE_STUDENT_TYPES = {REGISTRANT};
    private static final long serialVersionUID = 1L;
    private String name;
    private RichTextInfo descr;
    private Date effectiveDate;
    private Date expirationDate;
    private Meta meta;
    private List<? extends Attribute> attributes;
    private String key;

    LuiPersonRelationTypeEnum(String key, String name, String descr, Date effectiveDate, Date expirationDate, Meta meta, List<Attribute> attributes) {
        this.key = key;
        this.name = name;
        this.descr = new RichTextInfo();
        this.descr.setPlain(descr);
        this.effectiveDate = effectiveDate;
        this.expirationDate = expirationDate;
        this.meta = meta;
        this.attributes = attributes;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public RichText getDescr() {
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
    public String getRefObjectUri() {
        return REF_OBJECT_URI;        
    }

    @Override
    public Meta getMeta() {
        return this.meta;
    }

    @Override
    public List<? extends Attribute> getAttributes() {
        return this.attributes;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getServiceUri(){
        return SERVICE_URI;
    }

    private static Date asDate(String dateStr) {
        if (dateStr == null) {
            return null;
        }
        return DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse(dateStr);
    }

    public void setKey(String key) {
        this.key = key;
    }

    
    public void setAttributes(List<? extends Attribute> attributes) {
        this.attributes = new ArrayList<Attribute>(attributes);
    }

    
    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = new Date(effectiveDate.getTime());
    }

    
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = new Date(expirationDate.getTime());
    }

    
    public void setName(String name) {
        this.name = name;
    }

    
    public void setDescr(RichTextInfo descr) {
        this.descr = descr;
    }

    
    public void setRefObjectUri(String refObjectUri) {
    }

    public void setServiceUri(String serviceUri){
    }
}
