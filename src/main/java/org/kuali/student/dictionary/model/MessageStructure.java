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
 *
 * @author nwright
 */
public class MessageStructure
{

 private String id;

 public String getId ()
 {
  return id;
 }

 public void setId (String id)
 {
  this.id = id;
 }

 private String xmlObject;

 public String getXmlObject ()
 {
  return xmlObject;
 }

 public void setXmlObject (String xmlObject)
 {
  this.xmlObject = xmlObject;
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

 private String name;

 public String getName ()
 {
  return name;
 }

 public void setName (String name)
 {
  this.name = name;
 }

 private String type;

 public String getType ()
 {
  return type;
 }

 public void setType (String type)
 {
  this.type = type;
 }

 private String url;

 public String getUrl ()
 {
  return url;
 }

 public void setUrl (String url)
 {
  this.url = url;
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


 private String required;

 public String getRequired ()
 {
  return required;
 }

 public void setRequired (String required)
 {
  this.required = required;
 }


 private String cardinality;

 public String getCardinality ()
 {
  return cardinality;
 }

 public void setCardinality (String cardinality)
 {
  this.cardinality = cardinality;
 }

 private String xmlAttribute;

 public String getXmlAttribute ()
 {
  return xmlAttribute;
 }

 public void setXmlAttribute (String xmlAttribute)
 {
  this.xmlAttribute = xmlAttribute;
 }

 private String status;

 public String getStatus ()
 {
  return status;
 }

 public void setStatus (String status)
 {
  this.status = status;
 }

 private String feedback;

 public String getFeedback ()
 {
  return feedback;
 }

 public void setFeedback (String feedback)
 {
  this.feedback = feedback;
 }


}
