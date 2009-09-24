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
package org.kuali.student.search;

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

}
