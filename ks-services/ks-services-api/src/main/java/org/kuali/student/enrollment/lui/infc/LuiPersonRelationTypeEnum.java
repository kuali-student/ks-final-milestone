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
 * Types for Learning Person Relations
 *
 * See https://wiki.kuali.org/display/STUDENT/LuiPeronRelation+Types+and+States#LuiPeronRelationTypesandStates-References
 *
 * @author nwright
 */
public enum LuiPersonRelationTypeEnum implements LuiPersonRelationTypeInfc, Serializable {

 INSTRUCTOR_MAIN("kuali.lpr.type.instructor.main", "Main Instructor", "Main instructor(s) responsible for course or section ", asDate("20100101"), null, null),
 INSTRUCTOR_ASSISTANT("kuali.lpr.type.instructor.assistant", "Assistant Instructor", "Person who assists the main instructor but is still considered an \"instructor\"", asDate("20100101"), null, null),
 INSTRUCTOR_SUPPORT("kuali.lpr.type.instructor.support", "Support Instructor", "Persons who support the course but not in any official teaching role", asDate("20100101"), null, null),
 STUDENT("kuali.lpr.type.student.credit", "Student", "Student taking course or section for credit", asDate("20010101"), null, null),
 AUDITOR("kuali.lpr.type.student.audit", "Auditor", "Student who is not taking the course for credit", asDate("20010101"), null, null),
 /**
  * Programs
  */
 ENROLLEE("kuali.lpr.type.enrollee", "Enrollee", "Enrollee in the program", asDate("20010101"), null, null),
 ADVISOR("kuali.lpr.type.advisor", "Advisor", "Advisor to students in the program", asDate("20010101"), null, null);
 /**
  * Types used for isntructors of courses
  */
 public static final LuiPersonRelationTypeEnum[] COURSE_INSTRUCTOR_TYPES = {INSTRUCTOR_MAIN, INSTRUCTOR_ASSISTANT, INSTRUCTOR_SUPPORT};
 /**
  * Types used for students in courses
  */
 public static final LuiPersonRelationTypeEnum[] COURSE_STUDENT_TYPES = {STUDENT, AUDITOR};
 private static final long serialVersionUID = 1L;
 private String name;
 private String descr;
 private Date effectiveDate;
 private Date expirationDate;
 private List<AttributeInfc> attributes;
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
