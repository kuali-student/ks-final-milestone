/*
 * Copyright 2010 The Kuali Foundation
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
package org.kuali.student.loader;

import java.util.Date;

/**
 *
 * @author nwright
 */
public class Organization
{

private String grouping;
private String shortName;
private String longName;
private String sortName;
private String subjectArea1;
private String subjectArea2;
private String shortDesc;
private String typeName;
private String type;
private String state;
private String source;
private String notes;
private String longDesc;
private Date effectiveDate;
private Date expirationDate;

 public Date getEffectiveDate ()
 {
  return effectiveDate;
 }

 public void setEffectiveDate (Date effectiveDate)
 {
  this.effectiveDate = effectiveDate;
 }

 public Date getExpirationDate ()
 {
  return expirationDate;
 }

 public void setExpirationDate (Date expirationDate)
 {
  this.expirationDate = expirationDate;
 }

 public String getGrouping ()
 {
  return grouping;
 }

 public void setGrouping (String grouping)
 {
  this.grouping = grouping;
 }

 public String getLongDesc ()
 {
  return longDesc;
 }

 public void setLongDesc (String longDesc)
 {
  this.longDesc = longDesc;
 }

 public String getLongName ()
 {
  return longName;
 }

 public void setLongName (String longName)
 {
  this.longName = longName;
 }

 public String getNotes ()
 {
  return notes;
 }

 public void setNotes (String notes)
 {
  this.notes = notes;
 }

 public String getShortDesc ()
 {
  return shortDesc;
 }

 public void setShortDesc (String shortDesc)
 {
  this.shortDesc = shortDesc;
 }

 public String getShortName ()
 {
  return shortName;
 }

 public void setShortName (String shortName)
 {
  this.shortName = shortName;
 }

 public String getSortName ()
 {
  return sortName;
 }

 public void setSortName (String sortName)
 {
  this.sortName = sortName;
 }

 public String getSource ()
 {
  return source;
 }

 public void setSource (String source)
 {
  this.source = source;
 }

 public String getState ()
 {
  return state;
 }

 public void setState (String state)
 {
  this.state = state;
 }

 public String getSubjectArea1 ()
 {
  return subjectArea1;
 }

 public void setSubjectArea1 (String subjectArea1)
 {
  this.subjectArea1 = subjectArea1;
 }

 public String getSubjectArea2 ()
 {
  return subjectArea2;
 }

 public void setSubjectArea2 (String subjectArea2)
 {
  this.subjectArea2 = subjectArea2;
 }

 public String getType ()
 {
  return type;
 }

 public void setType (String type)
 {
  this.type = type;
 }

 public String getTypeName ()
 {
  return typeName;
 }

 public void setTypeName (String typeName)
 {
  this.typeName = typeName;
 }


 
}
