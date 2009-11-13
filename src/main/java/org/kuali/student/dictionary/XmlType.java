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
package org.kuali.student.dictionary;

import java.io.Serializable;

/**
 * Implements the XML type definitions in the spreadsheet
 * @author nwright
 */
public class XmlType implements Serializable
{

 public XmlType ()
 {
 }

 private String name;

 /**
  * Get the value of name
  *
  * @return the value of object
  */
 public String getName ()
 {
  return name;
 }

 /**
  * Set the value of name
  *
  * @param object new value of object
  */
 public void setName (String name)
 {
  this.name = name;
 }

 private String desc;

 /**
  * Get the value of description
  *
  * @return the value of description
  */
 public String getDesc ()
 {
  return desc;
 }

 /**
  * Set the value of description
  *
  * @param description new value of description
  */
 public void setDesc (String desc)
 {
  this.desc = desc;
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
  * Set the value of category
  *
  * @param primitive new value of primitive
  */
 public void setPrimitive (String primitive)
 {
  this.primitive = primitive;
 }

 private String examples;

 /**
  * Get the value of examples
  *
  * @return the value of examples
  */
 public String getExamples ()
 {
  return examples;
 }

 /**
  * Set the value of examples
  *
  * @param examples new value of examples
  */
 public void setExamples (String examples)
 {
  this.examples = examples;
 }

  private String hasOwnType;

 /**
  * Get the value of hasOwnType
  *
  * @return the value of hasOwnType
  */
 public String getHasOwnType ()
 {
  return hasOwnType;
 }

 /**
  * Set the value of hasOwnType
  *
  * @param hasOwnType new value of hasOwnType
  */
 public void setHasOwnType (String hasOwnType)
 {
  this.hasOwnState = hasOwnType;
 }



 private String hasOwnState;

 /**
  * Get the value of hasOwnState
  *
  * @return the value of hasOwnState
  */
 public String getHasOwnState ()
 {
  return hasOwnState;
 }

 /**
  * Set the value of hasOwnState
  *
  * @param hasOwnState new value of hasOwnState
  */
 public void setHasOwnState (String hasOwnState)
 {
  this.hasOwnState = hasOwnState;
 }



 private String hasOwnCreateUpdate;

 /**
  * Get the value of hasOwnCreateUpdate
  *
  * @return the value of hasOwnCreateUpdate
  */
 public String getHasOwnCreateUpdate ()
 {
  return hasOwnCreateUpdate;
 }

 /**
  * Set the value of hasOwnCreateUpdate
  *
  * @param hasOwnCreateUpdate new value of hasOwnCreateUpdate
  */
 public void setHasOwnCreateUpdate (String hasOwnCreateUpdate)
 {
  this.hasOwnCreateUpdate = hasOwnCreateUpdate;
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


 private String javaPackage;

 /**
  * Get the value of javaPackage
  *
  * @return the value of javaPackage
  */
 public String getJavaPackage ()
 {
  return javaPackage;
 }

 /**
  * Set the value of javaPackage
  *
  * @param javaPackage new value of javaPackage
  */
 public void setJavaPackage (String javaPackage)
 {
  this.javaPackage = javaPackage;
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

}
