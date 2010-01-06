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
 * This models a constraint either an in-line one or one defined as part of the bank of constraints.
 * Note: Cross-object Constraints are not modeled here.
 *
 * @author nwright
 */
public class Constraint
{

 public static final String UNBOUNDED = "(unbounded)";
 public static final String NINE_NINES = "999999999";

 public Constraint ()
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

 private String key;

 /**
  * Get the value of key
  *
  * @return the value of key
  */
 public String getKey ()
 {
  return key;
 }

 /**
  * Set the value of key
  *
  * @param key new value of key
  */
 public void setKey (String key)
 {
  this.key = key;
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

 private String serverSide;

 /**
  * Get the value of serverSide
  *
  * @return the value of serverSide
  */
 public String getServerSide ()
 {
  return serverSide;
 }

 /**
  * Set the value of serverSide
  *
  * @param serverSide new value of serverSide
  */
 public void setServerSide (String serverSide)
 {
  this.serverSide = serverSide;
 }

 private String minLength;

 /**
  * Get the value of minLength
  *
  * @return the value of minLength
  */
 public String getMinLength ()
 {
  return minLength;
 }

 /**
  * Set the value of minLength
  *
  * @param minLength new value of minLength
  */
 public void setMinLength (String minLength)
 {
  this.minLength = minLength;
 }

 private String maxLength;

 /**
  * Get the value of maxLength
  *
  * @return the value of maxLength
  */
 public String getMaxLength ()
 {
  return maxLength;
 }

 /**
  * Set the value of maxLength
  *
  * @param maxLength new value of maxLength
  */
 public void setMaxLength (String maxLength)
 {
  this.maxLength = maxLength;
 }

 private String minValue;

 /**
  * Get the value of minValue
  *
  * @return the value of minValue
  */
 public String getMinValue ()
 {
  return minValue;
 }

 /**
  * Set the value of minValue
  *
  * @param minValue new value of minValue
  */
 public void setMinValue (String minValue)
 {
  this.minValue = minValue;
 }

 private String maxValue;

 /**
  * Get the value of maxValue
  *
  * @return the value of maxValue
  */
 public String getMaxValue ()
 {
  return maxValue;
 }

 /**
  * Set the value of maxValue
  *
  * @param maxValue new value of maxValue
  */
 public void setMaxValue (String maxValue)
 {
  this.maxValue = maxValue;
 }

 protected String minOccurs;

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

 private String validChars;

 /**
  * Get the value of validChars
  *
  * @return the value of validChars
  */
 public String getValidChars ()
 {
  return validChars;
 }

 /**
  * Set the value of validChars
  *
  * @param validChars new value of validChars
  */
 public void setValidChars (String validChars)
 {
  this.validChars = validChars;
 }

 private String lookup;

 /**
  * Get the value of lookup
  *
  * @return the value of lookup
  */
 public String getLookup ()
 {
  return lookup;
 }

 /**
  * Set the value of lookup
  *
  * @param lookup new value of lookup
  */
 public void setLookup (String lookup)
 {
  this.lookup = lookup;
 }

 protected String lookupContext;

 /**
  * Get the value of lookupContext
  *
  * @return the value of lookupContext
  */
 public String getLookupContext ()
 {
  return lookupContext;
 }

 /**
  * Set the value of lookupContext
  *
  * @param lookupContext new value of lookupContext
  */
 public void setLookupContext (String lookupContext)
 {
  this.lookupContext = lookupContext;
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

 private String className;

 /**
  * Get the value of className
  *
  * @return the value of className
  */
 public String getClassName ()
 {
  return className;
 }

 /**
  * Set the value of className
  *
  * @param className new value of className
  */
 public void setClassName (String className)
 {
  this.className = className;
 }

 private String messageId;

 public String getMessageId ()
 {
  return messageId;
 }

 public void setMessageId (String messageId)
 {
  this.messageId = messageId;
 }

 private boolean inline;

 public boolean isInline ()
 {
  return inline;
 }

 public void setInline (boolean inline)
 {
  this.inline = inline;
 }
}
