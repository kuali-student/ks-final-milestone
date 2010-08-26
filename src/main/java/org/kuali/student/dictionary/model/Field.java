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
import java.util.List;

/**
 * This models a single field definition in the spreadsheet
 * @author nwright
 */
public class Field implements Serializable
{

 public Field ()
 {
 }

 private String id;

 /**
  * Get the value of id
  *
  * @return the value of id
  */
 public String getId ()
 {
  return id;
 }

 /**
  * Set the value of id
  *
  * @param id new value of id
  */
 public void setId (String id)
 {
  this.id = id;
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

 private String shortName;

 /**
  * Get the value of shortName
  *
  * @return the value of shortName
  */
 public String getShortName ()
 {
  return shortName;
 }

 /**
  * Set the value of shortName
  *
  * @param shortName new value of shortName
  */
 public void setShortName (String shortName)
 {
  this.shortName = shortName;
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

 private String xmlType;

 /**
  * Get the value of xmlType
  *
  * @return the value of xmlType
  */
 public String getXmlType ()
 {
  return xmlType;
 }

 /**
  * Set the value of xmlType
  *
  * @param xmlType new value of xmlType
  */
 public void setXmlType (String xmlType)
 {
  this.xmlType = xmlType;
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
  * @param primitive new value of primitive
  */
 public void setPrimitive (String primitive)
 {
  this.primitive = primitive;
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

 private List<String> constraintIds;

 /**
  * Get the value of constraintIds
  *
  * @return the value of constraintIds
  */
 public List<String> getConstraintIds ()
 {
  return constraintIds;
 }

 /**
  * Set the value of constraintIds
  *
  * @param constraintIds new value of constraintIds
  */
 public void setConstraintIds (List<String> constraintIds)
 {
  this.constraintIds = constraintIds;
 }

 private String constraintDescription;

 /**
  * Get the value of constraintDescription
  *
  * @return the value of constraintDescription
  */
 public String getConstraintDescription ()
 {
  return constraintDescription;
 }

 /**
  * Set the value of constraintDescription
  *
  * @param constraintDescription new value of constraintDescription
  */
 public void setConstraintDescription (String constraintDescription)
 {
  this.constraintDescription = constraintDescription;
 }


  private boolean dynamic;

 /**
  * Get the value of dynamic
  *
  * @return the value of dynamic
  */
 public boolean isDynamic ()
 {
  return dynamic;
 }

 /**
  * Set the value of dynamic
  *
  * @param dynamic new value of dynamic
  */
 public void setDynamic (boolean dynamic)
 {
  this.dynamic = dynamic;
 }
 
 private boolean selector;

 /**
  * Get the value of selector
  *
  * @return the value of selector
  */
 public boolean isSelector ()
 {
  return selector;
 }

 /**
  * Set the value of selector
  *
  * @param selector new value of selector
  */
 public void setSelector (boolean selector)
 {
  this.selector = selector;
 }

 private Constraint inlineConstraint;

 /**
  * Get the value of inlineConstraint
  *
  * @return the value of inlineConstraint
  */
 public Constraint getInlineConstraint ()
 {
  return inlineConstraint;
 }

 /**
  * Set the value of inlineConstraint
  *
  * @param inlineConstraint new value of inlineConstraint
  */
 public void setInlineConstraint (Constraint inlineConstraint)
 {
  this.inlineConstraint = inlineConstraint;
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
