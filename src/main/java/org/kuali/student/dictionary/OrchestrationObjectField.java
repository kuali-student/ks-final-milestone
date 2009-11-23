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

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nwright
 */
public class OrchestrationObjectField
{

 private OrchestrationObject parent;
 private String name;
 private String type;

 public enum FieldTypeCategory
 {

  LIST, PRIMITIVE, MAPPED_STRING, DYNAMIC_ATTRIBUTE, COMPLEX, COMPLEX_INLINE;
 }

 public OrchestrationObject getParent ()
 {
  return parent;
 }

 public void setParent (OrchestrationObject parent)
 {
  this.parent = parent;
 }

 public void setName (String name)
 {
  this.name = name;
 }

 public String getName ()
 {
  return name;
 }

 public void setType (String type)
 {
  this.type = type;
 }

 public String getType ()
 {
  return type;
 }

 private FieldTypeCategory fieldTypeCategory;

 public FieldTypeCategory getFieldTypeCategory ()
 {
  return fieldTypeCategory;
 }

 public void setFieldTypeCategory (
  FieldTypeCategory fieldTypeCategory)
 {
  this.fieldTypeCategory = fieldTypeCategory;
 }

 private OrchestrationObject inlineObject;

 public OrchestrationObject getInlineObject ()
 {
  return inlineObject;
 }

 public void setInlineObject (OrchestrationObject inlineObject)
 {
  this.inlineObject = inlineObject;
 }

 private List<TypeStateConstraint> constraints;

 public List<TypeStateConstraint> getConstraints ()
 {
  if (constraints == null)
  {
   constraints = new ArrayList ();
  }
  return constraints;
 }

 public void setConstraints (List<TypeStateConstraint> constraints)
 {
  this.constraints = constraints;
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

 public String getFullyQualifiedName ()
 {
  //TODO: Get grand parent
  StringBuffer buf = new StringBuffer ();
  buf.append (getParent ().getName ());
  buf.append (".");
  buf.append (getName ());
  return buf.toString ();
 }

}
