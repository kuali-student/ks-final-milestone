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
import java.util.Stack;

/**
 * Models a single dictionary entry
 * @author nwright
 */
public class Dictionary implements Serializable, Cloneable
{

 public Dictionary ()
 {
  super ();
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

 private String type;

 /**
  * Get the value of type
  *
  * @return the value of type
  */
 public String getType ()
 {
  return type;
 }

 /**
  * Set the value of type
  *
  * @param type new value of type
  */
 public void setType (String type)
 {
  this.type = type;
 }


 private String state;

 /**
  * Get the value of state
  *
  * @return the value of state
  */
 public String getState ()
 {
  return state;
 }

 /**
  * Set the value of state
  *
  * @param state new value of state
  */
 public void setState (String state)
 {
  this.state = state;
 }

 private Dictionary parent;

 public Dictionary getParent ()
 {
  return parent;
 }

 public void setParent (Dictionary parent)
 {
  this.parent = parent;
 }


 private String subType;

 /**
  * Get the value of subType
  *
  * @return the value of subType
  */
 public String getSubType ()
 {
  return subType;
 }

 /**
  * Set the value of subType
  *
  * @param subType new value of subType
  */
 public void setSubType (String subType)
 {
  this.subType = subType;
 }

 private String subState;

 /**
  * Get the value of subState
  *
  * @return the value of subState
  */
 public String getSubState ()
 {
  return subState;
 }

 /**
  * Set the value of subState
  *
  * @param subState new value of subState
  */
 public void setSubState (String subState)
 {
  this.subState = subState;
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

 private String baseConstraintDescription;

 /**
  * Get the value of baseConstraintDescription
  *
  * @return the value of baseConstraintDescription
  */
 public String getBaseConstraintDescription ()
 {
  return baseConstraintDescription;
 }

 /**
  * Set the value of baseConstraintDescription
  *
  * @param baseConstraintDescription new value of baseConstraintDescription
  */
 public void setBaseConstraintDescription (String baseConstraintDescription)
 {
  this.baseConstraintDescription = baseConstraintDescription;
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

 public String desc;

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


 private boolean dynamic;

 public boolean isDynamic ()
 {
  return dynamic;
 }

 public void setDynamic (boolean dynamic)
 {
  this.dynamic = dynamic;
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

 protected List<String> additionalConstraintIds;

 /**
  * Get the value of additionalConstraintIds
  *
  * @return the value of additionalConstraintIds
  */
 public List<String> getAdditionalConstraintIds ()
 {
  return additionalConstraintIds;
 }

 /**
  * Set the value of additionalConstraintIds
  *
  * @param additionalConstraintIds new value of additionalConstraintIds
  */
 public void setAdditionalConstraintIds (List<String> additionalConstraintIds)
 {
  this.additionalConstraintIds = additionalConstraintIds;
 }

 protected String additionalConstraintDescription;

 /**
  * Get the value of additionalConstraintDescription
  *
  * @return the value of additionalConstraintDescription
  */
 public String getAdditionalConstraintDescription ()
 {
  return additionalConstraintDescription;
 }

 /**
  * Set the value of additionalConstraintDescription
  *
  * @param additionalConstraintDescription new value of additionalConstraintDescription
  */
 public void setAdditionalConstraintDescription (
  String additionalConstraintDescription)
 {
  this.additionalConstraintDescription = additionalConstraintDescription;
 }

 protected String combinedConstraintDescription;

 /**
  * Get the value of combinedConstraintDescription
  *
  * @return the value of combinedConstraintDescription
  */
 public String getCombinedConstraintDescription ()
 {
  return combinedConstraintDescription;
 }

 /**
  * Set the value of combinedConstraintDescription
  *
  * @param combinedConstraintDescription new value of combinedConstraintDescription
  */
 public void setCombinedConstraintDescription (
  String combinedConstraintDescription)
 {
  this.combinedConstraintDescription = combinedConstraintDescription;
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

 @Override
 public Object clone ()
  throws CloneNotSupportedException
 {
  return super.clone ();
 }

}
