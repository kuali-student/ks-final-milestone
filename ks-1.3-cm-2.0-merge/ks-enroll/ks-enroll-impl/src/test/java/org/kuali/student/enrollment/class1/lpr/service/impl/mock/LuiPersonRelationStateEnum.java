/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.class1.lpr.service.impl.mock;

import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.infc.Meta;
import org.kuali.student.r2.common.infc.RichText;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.common.state.infc.State;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * States for Learning Person Relations
 * <p/>
 * See https://wiki.kuali.org/display/STUDENT/LuiPeronRelation+Types+and+States#LuiPeronRelationTypesandStates-References
 *
 * @author nwright
 */
public enum LuiPersonRelationStateEnum implements State, Serializable {

    /**
     * Student states to courses
     */
    PLANNED(LprServiceConstants.PLANNED_STATE_KEY, "Planned", "The student plans on taking this course or program", asDate("20100101"), null, null, null),
        REGISTERED(LprServiceConstants.REGISTERED_STATE_KEY, "Registered", "The student is officially registered for the course or section", asDate("20100101"), null, null, null),
    WAITLISTED(LprServiceConstants.WAITLISTED_STATE_KEY, "Waitlisted", "The student attempted to join but has been put on the waitlist", asDate("20100101"), null, null, null),
    DROPPED(LprServiceConstants.DROPPED_STATE_KEY, "Dropped Early", "Student dropped the course before the normal deadline", asDate("20010101"), null, null, null),
    DROPPED_LATE(LprServiceConstants.DROPPED_LATE_STATE_KEY, "Dropped Late", "The student was registered but subsequently dropped the course or section past the normally allotted time period, typically resulting in a special grade or mark to so indicate", asDate("20010101"), null, null, null),
    /**
     * Instructor states
     */
    TENTATIVE(LprServiceConstants.TENTATIVE_STATE_KEY, "Tentative", "The instructor is proposed to teach this course or section but it has not yet been confirmed", asDate("20010101"), null, null, null),
    ASSIGNED(LprServiceConstants.ASSIGNED_STATE_KEY, "Assigned", "The instructor is assigned to teach this course or section.", asDate("20010101"), null, null, null),
    UNASSIGNED(LprServiceConstants.UNASSIGNED_STATE_KEY, "Unassigned", "The instructor had been assigned but then that assignment was removed", asDate("20010101"), null, null, null),
    /**
     * Program states
     */
    INQUIRED(LprServiceConstants.INQUIRED_STATE_KEY, "Inquired", "The student took an active step in contacting the program indicating their plans", asDate("20100101"), null, null, null),
    APPLIED(LprServiceConstants.APPLIED_STATE_KEY, "Applied", "The student has applied for the program", asDate("20100101"), null, null, null),
    ADMITTED(LprServiceConstants.ADMITTED_STATE_KEY, "Admitted", "The student has been admitted to the program ", asDate("20100101"), null, null, null),
    DENIED(LprServiceConstants.DENIED_STATE_KEY, "Denied", "The student was denied admission to the program", asDate("20100101"), null, null, null),
    CONFIRMED(LprServiceConstants.CONFIRMED_STATE_KEY, "Confirmed", "The student has confirmed that she plans to matriculate ", asDate("20100101"), null, null, null),
    CANCELED(LprServiceConstants.CANCELED_STATE_KEY, "Canceled", "The student canceled prior to matriculation", asDate("20100101"), null, null, null),
    DEFERED(LprServiceConstants.DEFERED_STATE_KEY, "Deferred", "The student defers matriculation to a different term", asDate("20100101"), null, null, null),
    ENROLLED(LprServiceConstants.ENROLLED_STATE_KEY, "Enrolled", "The student is fully enrolled in the program ", asDate("20100101"), null, null, null),
    TEMPORARY_ABSENCE(LprServiceConstants.TEMPORARY_ABSENCE_STATE_KEY, "Temporary Absence", "The student has temporarily not matriculated but is expected to return", asDate("20100101"), null, null, null),
    WITHDRAWN(LprServiceConstants.WITHDRAWN_STATE_KEY, "Withdrawn", "The student was registered but then withdrew from the program", asDate("20100101"), null, null, null),
    PROBATION(LprServiceConstants.PROBATION_STATE_KEY, "Probation", "The student must fulfill certain requirements in order to stay in the program", asDate("20100101"), null, null, null);
    /**
     * States used for isntructors of courses
     */
    public static final LuiPersonRelationStateEnum[] COURSE_INSTRUCTOR_STATES = {TENTATIVE, ASSIGNED, UNASSIGNED};
    /**
     * Types used for students in courses
     */
    public static final LuiPersonRelationStateEnum[] COURSE_STUDENT_STATES = {PLANNED, REGISTERED, WAITLISTED, DROPPED, DROPPED_LATE};
    /**
     * States used for isntructors of PROGRAMS
     */
    public static final LuiPersonRelationStateEnum[] PROGRAM_ADVISOR_STATES = {TENTATIVE, ASSIGNED, UNASSIGNED};
    /**
     * Types used for students in PROGRAMS
     */
    public static final LuiPersonRelationStateEnum[] PROGRAM_STUDENT_STATES = {PLANNED, INQUIRED, APPLIED, WAITLISTED, DENIED, CONFIRMED, CANCELED, DEFERED, ENROLLED, TEMPORARY_ABSENCE, WITHDRAWN, PROBATION};
    private static final long serialVersionUID = 1L;
    private String name;
    private RichTextInfo descr;
    private Date effectiveDate;
    private Date expirationDate;
    private Meta meta;
    private List<? extends Attribute> attributes;
    private String key;

    LuiPersonRelationStateEnum(String key, String name, String descr, Date effectiveDate, Date expirationDate, Meta meta, List<? extends Attribute> attributes) {
        this.key = key;
        this.name = name;
        this.descr = new RichTextInfo();
        this.descr.setPlain(descr);
        this.effectiveDate = effectiveDate;
        this.expirationDate = expirationDate;
        this.attributes = attributes;
    }

    @Override
    public String getLifecycleKey() {
        return "norm";
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
    public Meta getMeta() {
        return this.meta;
    }

    @Override
    public List<? extends Attribute> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(List<? extends Attribute> attributes) {
        this.attributes = attributes;
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

    public void setKey(String key) {
        this.key = key;
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
}
