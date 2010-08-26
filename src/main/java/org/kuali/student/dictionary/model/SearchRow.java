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

/**
 * Models a single dictionary entry
 * @author nwright
 */
public class SearchRow implements Serializable,
                                  Cloneable
{

 public SearchRow ()
 {
  super ();
 }

 private int rowNumber;

 /**
  * Get the value of rowNumber
  *
  * @return the value of row
  */
 public int getRowNumber ()
 {
  return rowNumber;
 }

 /**
  * Set the value of rowNumber
  *
  * @param rowNumber new value of rowNumber
  */
 public void setRowNumber (int rowNumber)
 {
  this.rowNumber = rowNumber;
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

 private String lookupKey;

 /**
  * Get the value of lookupKey
  *
  * @return the value of lookupKey
  */
 public String getLookupKey ()
 {
  return lookupKey;
 }

 /**
  * Set the value of lookupKey
  *
  * @param lookupKey new value of lookupKey
  */
 public void setLookupKey (String lookupKey)
 {
  this.lookupKey = lookupKey;
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

 /**
  * Set the value of key
  *
  * @param key new value of key
  */
 public void setKey (String key)
 {
  this.key = key;
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

 private String description;
 private String dataType;

 /**
  * Get the value of dataType
  *
  * @return the value of dataType
  */
 public String getDataType ()
 {
  return dataType;
 }

 /**
  * Set the value of dataType
  *
  * @param dataType new value of dataType
  */
 public void setDataType (String dataType)
 {
  this.dataType = dataType;
 }

 private String hidden;

 /**
  * Get the value of hidden
  *
  * @return the value of hidden
  */
 public String getHidden ()
 {
  return hidden;
 }

 private String usage;

 /**
  * Get the value of usage
  *
  * @return the value of usage
  */
 public String getUsage ()
 {
  return usage;
 }

 private String widget;

 /**
  * Get the value of widget
  *
  * @return the value of widget
  */
 public String getWidget ()
 {
  return widget;
 }

 /**
  * Set the value of widget
  *
  * @param widget new value of widget
  */
 public void setWidget (String widget)
 {
  this.widget = widget;
 }

 private String optional;

 /**
  * Get the value of optional
  *
  * @return the value of optional
  */
 public String getOptional ()
 {
  return optional;
 }

 private String caseSensitive;

 /**
  * Get the value of caseSensitive
  *
  * @return the value of caseSensitive
  */
 public String getCaseSensitive ()
 {
  return caseSensitive;
 }

 /**
  * Set the value of caseSensitive
  *
  * @param caseSensitive new value of caseSensitive
  */
 public void setCaseSensitive (String caseSensitive)
 {
  this.caseSensitive = caseSensitive;
 }

 private String writeAccess;

 /**
  * Get the value of writeAccess
  *
  * @return the value of writeAccess
  */
 public String getWriteAccess ()
 {
  return writeAccess;
 }

 /**
  * Set the value of writeAccess
  *
  * @param writeAccess new value of writeAccess
  */
 public void setWriteAccess (String writeAccess)
 {
  this.writeAccess = writeAccess;
 }

 private String defaultValue;

 /**
  * Get the value of defaultValue
  *
  * @return the value of defaultValue
  */
 public String getDefaultValue ()
 {
  return defaultValue;
 }

 /**
  * Set the value of defaultValue
  *
  * @param defaultValue new value of defaultValue
  */
 public void setDefaultValue (String defaultValue)
 {
  this.defaultValue = defaultValue;
 }

 private String childLookup;

 /**
  * Get the value of childLookup
  *
  * @return the value of childLookup
  */
 public String getChildLookup ()
 {
  return childLookup;
 }

 /**
  * Set the value of childLookup
  *
  * @param childLookup new value of childLookup
  */
 public void setChildLookup (String childLookup)
 {
  this.childLookup = childLookup;
 }

 /**
  * Set the value of optional
  *
  * @param optional new value of optional
  */
 public void setOptional (String optional)
 {
  this.optional = optional;
 }

 /**
  * Set the value of usage
  *
  * @param usage new value of usage
  */
 public void setUsage (String usage)
 {
  this.usage = usage;
 }

 /**
  * Set the value of hidden
  *
  * @param hidden new value of hidden
  */
 public void setHidden (String hidden)
 {
  this.hidden = hidden;
 }

 /**
  * Get the value of description
  *
  * @return the value of description
  */
 public String getDescription ()
 {
  return description;
 }

 /**
  * Set the value of description
  *
  * @param description new value of description
  */
 public void setDescription (String description)
 {
  this.description = description;
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

 @Override
 protected Object clone ()
  throws CloneNotSupportedException
 {
  return super.clone ();
 }

 private String service;

 /**
  * Get the value of service
  *
  * @return the value of service
  */
 public String getService ()
 {
  return service;
 }

 /**
  * Set the value of service
  *
  * @param service new value of service
  */
 public void setService (String service)
 {
  this.service = service;
 }

}
