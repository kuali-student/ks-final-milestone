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

import java.util.List;

/**
 *
 * @author nwright
 */
public class OrchObj
{

 private String parent;

 public String getParent ()
 {
  return parent;
 }

 public void setParent (String parent)
 {
  this.parent = parent;
 }

 private String card1;

 public String getCard1 ()
 {
  return card1;
 }

 public void setCard1 (String card1)
 {
  this.card1 = card1;
 }

 private String child;

 public String getChild ()
 {
  return child;
 }

 public void setChild (String child)
 {
  this.child = child;
 }

 private String card2;

 public String getCard2 ()
 {
  return card2;
 }

 public void setCard2 (String card2)
 {
  this.card2 = card2;
 }

 private String grandChild;

 public String getGrandChild ()
 {
  return grandChild;
 }

 public void setGrandChild (String grandChild)
 {
  this.grandChild = grandChild;
 }

 private String desc;

 public String getDesc ()
 {
  return desc;
 }

 public void setDesc (String desc)
 {
  this.desc = desc;
 }

 private String xmlType;

 public String getXmlType ()
 {
  return xmlType;
 }

 public void setXmlType (String xmlType)
 {
  this.xmlType = xmlType;
 }

 private String recursions;

 public String getRecursions ()
 {
  return recursions;
 }

 public void setRecursions (String recursions)
 {
  this.recursions = recursions;
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

 private String defaultValue;

 public String getDefaultValue ()
 {
  return defaultValue;
 }

 public void setDefaultValue (String defaultValue)
 {
  this.defaultValue = defaultValue;
 }

 private String defaultValuePath;

 public String getDefaultValuePath ()
 {
  return defaultValuePath;
 }

 public void setDefaultValuePath (String defaultValuePath)
 {
  this.defaultValuePath = defaultValuePath;
 }

 private String lookup;

 public String getLookup ()
 {
  return lookup;
 }

 public void setLookup (String lookup)
 {
  this.lookup = lookup;
 }

 private String lookupContextPath;

 public String getLookupContextPath ()
 {
  return lookupContextPath;
 }

 public void setLookupContextPath (String lookupContextPath)
 {
  this.lookupContextPath = lookupContextPath;
 }


 private String dictionaryId;

 public String getDictionaryId ()
 {
  return dictionaryId;
 }

 public void setDictionaryId (String dictionaryId)
 {
  this.dictionaryId = dictionaryId;
 }

 private List<String> constraintIds;

 public List<String> getConstraintIds ()
 {
  return constraintIds;
 }

 public void setConstraintIds (List<String> constraintIds)
 {
  this.constraintIds = constraintIds;
 }

 private String messageStructureKey;

 public String getMessageStructureKey ()
 {
  return messageStructureKey;
 }

 public void setMessageStructureKey (String messageStructureKey)
 {
  this.messageStructureKey = messageStructureKey;
 }

  private String selector;


 public String getSelector ()
 {
  return selector;
 }


 public void setSelector (String selector)
 {
  this.selector = selector;
 }

  private Constraint inlineConstraint;


 public Constraint getInlineConstraint ()
 {
  return inlineConstraint;
 }

 public void setInlineConstraint (Constraint inlineConstraint)
 {
  this.inlineConstraint = inlineConstraint;
 }

 private String comments;

 public String getComments ()
 {
  return comments;
 }

 public void setComments (String comments)
 {
  this.comments = comments;
 }


 private String id;

 public String getId ()
 {
  return id;
 }

 public void setId (String id)
 {
  this.id = id;
 }


 private String writeAccess;

 public String getWriteAccess ()
 {
  return writeAccess;
 }

 public void setWriteAccess (String writeAccess)
 {
  this.writeAccess = writeAccess;
 }

}
