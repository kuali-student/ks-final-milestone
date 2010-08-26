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

/**
 * Models a cross-object constraint
 * @author nwright
 */
public class CrossObjectConstraint
{

 public static String IMPLEMENTATION_TYPE_STATE_WHEN = "typeStateWhen";

 public CrossObjectConstraint ()
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

 private String implementation;

 /**
  * Get the value of implementation
  *
  * @return the value of implementation
  */
 public String getImplementation ()
 {
  return implementation;
 }

 /**
  * Set the value of implementation
  *
  * @param implementation new value of implementation
  */
 public void setImplementation (String implementation)
 {
  this.implementation = implementation;
 }

 private String dictionaryId;

 /**
  * Get the value of dictionaryId
  *
  * @return the value of dictionaryId
  */
 public String getDictionaryId ()
 {
  return dictionaryId;
 }

 /**
  * Set the value of dictionaryId
  *
  * @param dictionaryId new value of dictionaryId
  */
 public void setDictionaryId (String dictionaryId)
 {
  this.dictionaryId = dictionaryId;
 }

 private String object1;

 /**
  * Get the value of object1
  *
  * @return the value of object1
  */
 public String getObject1 ()
 {
  return object1;
 }

 /**
  * Set the value of object1
  *
  * @param object1 new value of object1
  */
 public void setObject1 (String object1)
 {
  this.object1 = object1;
 }

 private String type1;

 /**
  * Get the value of type1
  *
  * @return the value of type1
  */
 public String getType1 ()
 {
  return type1;
 }

 /**
  * Set the value of type1
  *
  * @param type1 new value of type1
  */
 public void setType1 (String type1)
 {
  this.type1 = type1;
 }

 private String state1;

 /**
  * Get the value of state1
  *
  * @return the value of state1
  */
 public String getState1 ()
 {
  return state1;
 }

 /**
  * Set the value of state1
  *
  * @param state1 new value of state1
  */
 public void setState1 (String state1)
 {
  this.state1 = state1;
 }

 private String relationType;

 /**
  * Get the value of relationType
  *
  * @return the value of relationType
  */
 public String getRelationType ()
 {
  return relationType;
 }

 /**
  * Set the value of relationType
  *
  * @param relationType new value of relationType
  */
 public void setRelationType (String relationType)
 {
  this.relationType = relationType;
 }

 private String cardinalityType;

 /**
  * Get the value of cardinalityType
  *
  * @return the value of cardinalityType
  */
 public String getCardinalityType ()
 {
  return cardinalityType;
 }

 /**
  * Set the value of cardinalityType
  *
  * @param cardinalityType new value of cardinalityType
  */
 public void setCardinalityType (String cardinalityType)
 {
  this.cardinalityType = cardinalityType;
 }

 private String object2;

 /**
  * Get the value of object2
  *
  * @return the value of object2
  */
 public String getObject2 ()
 {
  return object2;
 }

 /**
  * Set the value of object2
  *
  * @param object2 new value of object2
  */
 public void setObject2 (String object2)
 {
  this.object2 = object2;
 }

 private String type2;

 /**
  * Get the value of type2
  *
  * @return the value of type2
  */
 public String getType2 ()
 {
  return type2;
 }

 /**
  * Set the value of type2
  *
  * @param type2 new value of type2
  */
 public void setType2 (String type2)
 {
  this.type2 = type2;
 }

 private String state2;

 /**
  * Get the value of state2
  *
  * @return the value of state2
  */
 public String getState2 ()
 {
  return state2;
 }

 /**
  * Set the value of state2
  *
  * @param state2 new value of state2
  */
 public void setState2 (String state2)
 {
  this.state2 = state2;
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

 private String minOccurs;

 /**
  * Get the value of minOccurs
  *
  * @return the value of minOccurs
  */
 public String getMinOccurs ()
 {
  return minOccurs;
 }

 /**
  * Set the value of minOccurs
  *
  * @param minOccurs new value of minOccurs
  */
 public void setMinOccurs (String minOccurs)
 {
  this.minOccurs = minOccurs;
 }

 private String maxOccurs;

 /**
  * Get the value of maxOccurs
  *
  * @return the value of maxOccurs
  */
 public String getMaxOccurs ()
 {
  return maxOccurs;
 }

 /**
  * Set the value of maxOccurs
  *
  * @param maxOccurs new value of maxOccurs
  */
 public void setMaxOccurs (String maxOccurs)
 {
  this.maxOccurs = maxOccurs;
 }

}
