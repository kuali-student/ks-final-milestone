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
package org.kuali.student.enrollment.lui.infc;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.kuali.student.enrollment.common.infc.AttributeInfc;

/**
 * States for Learning Person Relations
 *
 * See https://wiki.kuali.org/display/STUDENT/LuiPeronRelation+Types+and+States#LuiPeronRelationTypesandStates-References
 *
 * @author nwright
 */
public enum LuiPersonRelationStateEnum implements LuiPersonRelationStateInfc, Serializable {

 /**
  * Student states to courses
  */
 PLANNED("kuali.lpr.state.planned", "Planned", "The student plans on taking this course or program", asDate("20100101"), null, null),
 REGISTERED ("kuali.lpr.state.registered ", "Registered", "The student is officially registered for the course or section", asDate("20100101"), null, null),
 WAITLISTED("kuali.lpr.state.waitlisted", "Waitlisted", "The student attempted to join but has been put on the waitlist", asDate("20100101"), null, null),
 DROPPED("kuali.lpr.state.dropped.early", "Student", "Student taking course or section for credit", asDate("20010101"), null, null),
 DROPPED_LATE("kuali.lpr.state.dropped.late", "Dropped Late", "The student was registered but subsequently dropped the course or section past the normally allotted time period, typically resulting in a special grade or mark to so indicate", asDate("20010101"), null, null),
 /**
  * Instructor states
  */
 TENATIVE("kuali.lpr.state.tentative", "Tentative", "The instructor is proposed to teach this course or section but it has not yet been confirmed", asDate("20010101"), null, null),
 ASSIGNED("kuali.lpr.state.assigned", "Assigned", "The instructor is assigned to teach this course or section.", asDate("20010101"), null, null),
 UNASSIGNED("kuali.lpr.state.unassigned", "Unassigned", "The instructor had been assigned but then that assignment was removed", asDate("20010101"), null, null),
 /**
  * Program states
  */
 INQUIRED("kuali.lpr.state.inquired", "Inquired", "The student took an active step in contacting the program indicating their plans", asDate("20100101"), null, null),
 APPLIED ("kuali.lpr.state.applied", "Applied", "The student has applied for the program", asDate("20100101"), null, null),
 ADMITTED ("kuali.lpr.state.admitted ", "Admitted", "The student has been admitted to the program ", asDate("20100101"), null, null),
 DENIED ("kuali.lpr.state.denied", "Denied", "The student was denied admission to the program", asDate("20100101"), null, null),
 CONFIRMED ("kuali.lpr.state.confirmed", "Confirmed", "The student has confirmed that she plans to matriculate ", asDate("20100101"), null, null),
 CANCELED ("kuali.lpr.state.canceled", "Canceled", "The student canceled prior to matriculation", asDate("20100101"), null, null),
 DEFERED ("kuali.lpr.state.defered", "Deferred", "The student defers matriculation to a different term", asDate("20100101"), null, null),
 ENROLLED ("kuali.lpr.state.enrolled", "Enrolled", "The student is fully enrolled in the program ", asDate("20100101"), null, null),
 TEMPORARY_ABSENCE ("kuali.lpr.state.temp.absence", "Temporary Absence", "The student has temporarily not matriculated but is expected to return", asDate("20100101"), null, null),
 WITHDRAWN ("kuali.lpr.state.withdrawn", "Withdrawn", "The student was registered but then withdrew from the program", asDate("20100101"), null, null),
 PROBATION ("kuali.lpr.state.probation", "Probation", "The student must fulfill certain requirements in order to stay in the program", asDate("20100101"), null, null);

 /**
  * States used for isntructors of courses
  */
 public static final LuiPersonRelationStateEnum[] COURSE_INSTRUCTOR_STATES = {TENATIVE, ASSIGNED, UNASSIGNED};
 /**
  * Types used for students in courses
  */
 public static final LuiPersonRelationStateEnum[] COURSE_STUDENT_STATES = {PLANNED, REGISTERED, WAITLISTED, DROPPED, DROPPED_LATE};
 /**
  * States used for isntructors of PROGRAMS
  */
 public static final LuiPersonRelationStateEnum[] PROGRAM_ADVISOR_STATES = {TENATIVE, ASSIGNED, UNASSIGNED};
 /**
  * Types used for students in PROGRAMS
  */
 public static final LuiPersonRelationStateEnum[] PROGRAM_STUDENT_STATES = {PLANNED, INQUIRED, APPLIED, WAITLISTED, DENIED, CONFIRMED, CANCELED, DEFERED, ENROLLED, TEMPORARY_ABSENCE, WITHDRAWN, PROBATION};
 private static final long serialVersionUID = 1L;
 private String name;
 private String descr;
 private Date effectiveDate;
 private Date expirationDate;
 private List<AttributeInfc> attributes;
 private String key;

 LuiPersonRelationStateEnum(String key, String name, String descr, Date effectiveDate, Date expirationDate, List<AttributeInfc> attributes) {
  this.key = key;
  this.name = name;
  this.descr = descr;
  this.effectiveDate = effectiveDate;
  this.expirationDate = expirationDate;
  this.attributes = attributes;
 }

 @Override
 public void setName(String name) {
  this.name = name;
 }

 @Override
 public String getName() {
  return this.name;
 }

 @Override
 public void setDescr(String descr) {
  this.descr = descr;
 }

 @Override
 public String getDescr() {
  return this.descr;
 }

 @Override
 public void setEffectiveDate(Date effectiveDate) {
  this.effectiveDate = effectiveDate;
 }

 @Override
 public Date getEffectiveDate() {
  return this.effectiveDate;
 }

 @Override
 public void setExpirationDate(Date expirationDate) {
  this.expirationDate = expirationDate;
 }

 @Override
 public Date getExpirationDate() {
  return this.expirationDate;
 }

 @Override
 public void setAttributes(List<AttributeInfc> attributes) {
  this.attributes = attributes;
 }

 @Override
 public List<AttributeInfc> getAttributes() {
  return this.attributes;
 }

 @Override
 public void setKey(String key) {
  this.key = key;
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
