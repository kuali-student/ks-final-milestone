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

  PRIMITIVE,
  MAPPED_STRING,
  DYNAMIC_ATTRIBUTE,
  COMPLEX,
  COMPLEX_INLINE,
  LIST_OF_PRIMITIVE,
  LIST_OF_MAPPED_STRING,
  LIST_OF_COMPLEX,
  LIST_OF_COMPLEX_INLINE;
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

 public String getPropertyName ()
 {
  return name.substring (0, 1).toLowerCase () + name.substring (1);
 }

 public String getProperName ()
 {
  return name.substring (0, 1).toUpperCase () + name.substring (1);
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

 private String defaultValuePath;

 public String getDefaultValuePath ()
 {
  return defaultValuePath;
 }

 public void setDefaultValuePath (String defaultValuePath)
 {
  this.defaultValuePath = defaultValuePath;
 }

 public String getFullyQualifiedName ()
 {
  //TODO: Get grand parent
  StringBuffer buf = new StringBuffer ();
  buf.append (getParent ().getName ());
  buf.append (".");
  buf.append (getProperName ());
  return buf.toString ();
 }

 public enum WriteAccess
 {

  ALWAYS, NEVER, ON_CREATE;
 }
 private WriteAccess writeAccess;

 public WriteAccess getWriteAccess ()
 {
  return writeAccess;
 }

 public void setWriteAccess (WriteAccess writeAccess)
 {
  this.writeAccess = writeAccess;
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

 private List<String> additionalLookups;

 public List<String> getAdditionalLookups ()
 {
  return additionalLookups;
 }

 public void setAdditionalLookups (List<String> additionalLookups)
 {
  this.additionalLookups = additionalLookups;
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

 private Integer maxRecursions;

 public Integer getMaxRecursions ()
 {
  return maxRecursions;
 }

 public void setMaxRecursions (Integer maxRecursions)
 {
  this.maxRecursions = maxRecursions;
 }

}
