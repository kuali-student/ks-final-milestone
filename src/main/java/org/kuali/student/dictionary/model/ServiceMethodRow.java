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
package org.kuali.student.dictionary.model;

/**
 *
 * @author nwright
 */
public class ServiceMethodRow
{

 private int rowNumber;

 public int getRowNumber ()
 {
  return rowNumber;
 }

 public void setRowNumber (int rowNumber)
 {
  this.rowNumber = rowNumber;
 }

 private String service;

 public String getService ()
 {
  return service;
 }

 public void setService (String service)
 {
  this.service = service;
 }

 private String key;

 public String getKey ()
 {
  return key;
 }

 public void setKey (String key)
 {
  this.key = key;
 }

 private String shortName;

 public String getShortName ()
 {
  return shortName;
 }

 public void setShortName (String shortName)
 {
  this.shortName = shortName;
 }

 protected String longName;

 /**
  * Get the value of longName
  *
  * @return the value of longName
  */
 public String getLongName ()
 {
  return longName;
 }

 /**
  * Set the value of longName
  *
  * @param longName new value of longName
  */
 public void setLongName (String longName)
 {
  this.longName = longName;
 }

 private String description;

 public String getDescription ()
 {
  return description;
 }

 public void setDescription (String description)
 {
  this.description = description;
 }

}
