/*
 * Copyright 2009 The Kuali Foundation
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

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Models the type object in the spreadsheets
 * @author nwright
 */
public class Type implements Serializable
{
 public static final String GROUPING = "Grouping";
 public static final String DEFAULT = "(default)";
 
 public Type ()
 {
 }

 private String name;

 /**
  * Get the value of name
  *
  * @return the value of name
  */
 public String getName ()
 {
  return name;
 }

 /**
  * Set the value of name
  *
  * @param name new value of name
  */
 public void setName (String name)
 {
  this.name = name;
 }

 private String desc;

 /**
  * Get the value of desc
  *
  * @return the value of desc
  */
 public String getDesc ()
 {
  return desc;
 }

 /**
  * Set the value of desc
  *
  * @param desc new value of desc
  */
 public void setDesc (String desc)
 {
  this.desc = desc;
 }

 private String xmlObject;

 /**
  * Get the value of xmlObject
  *
  * @return the value of xmlObject
  */
 public String getXmlObject ()
 {
  return xmlObject;
 }

 /**
  * Set the value of xmlObject
  *
  * @param xmlObject new value of xmlObject
  */
 public void setXmlObject (String xmlObject)
 {
  this.xmlObject = xmlObject;
 }

 private String primitive;

 /**
  * Get the value of primitive
  *
  * @return the value of primitive
  */
 public String getPrimitive ()
 {
  return primitive;
 }

 /**
  * Set the value of primitive
  *
  * @param xmlTypeDesc new value of primitive
  */
 public void setPrimitive (String primitive)
 {
  this.primitive = primitive;
 }

 private boolean include;

 /**
  * Get the value of include
  *
  * @return the value of include
  */
 public boolean getInclude ()
 {
  return include;
 }

 /**
  * Set the value of include
  *
  * @param include new value of include
  */
 public void setInclude (boolean include)
 {
  this.include = include;
 }

 private String typeKey;

 /**
  * Get the value of typeKey
  *
  * @return the value of typeKey
  */
 public String getTypeKey ()
 {
  return typeKey;
 }

 /**
  * Set the value of typeKey
  *
  * @param typeKey new value of typeKey
  */
 public void setTypeKey (String typeKey)
 {
  this.typeKey = typeKey;
 }

 private String aliases;

 /**
  * Get the value of aliases
  *
  * @return the value of aliases
  */
 public String getAliases ()
 {
  return aliases;
 }

 /**
  * Set the value of aliases
  *
  * @param aliases new value of aliases
  */
 public void setAliases (String aliases)
 {
  this.aliases = aliases;
 }

 private String status;

 /**
  * Get the value of status
  *
  * @return the value of status
  */
 public String getStatus ()
 {
  return status;
 }

 /**
  * Set the value of status
  *
  * @param status new value of status
  */
 public void setStatus (String status)
 {
  this.status = status;
 }

 private String comments;

 /**
  * Get the value of comments
  *
  * @return the value of comments
  */
 public String getComments ()
 {
  return comments;
 }

 /**
  * Set the value of comments
  *
  * @param comments new value of comments
  */
 public void setComments (String comments)
 {
  this.comments = comments;
 }

 private Map<String, String> attributes;

 public Map<String, String> getAttributes ()
 {
  if (attributes == null)
  {
   attributes = new LinkedHashMap ();
  }
  return attributes;
 }

 public void setAttributes (Map<String, String> attributes)
 {
  this.attributes = attributes;
 }

 private Date effectiveDate;

 public Date getEffectiveDate ()
 {
  return effectiveDate;
 }

 public void setEffectiveDate (Date effectiveDate)
 {
  this.effectiveDate = effectiveDate;
 }

 private Date expirationDate;

 public Date getExpirationDate ()
 {
  return expirationDate;
 }

 public void setExpirationDate (Date expirationDate)
 {
  this.expirationDate = expirationDate;
 }

}
