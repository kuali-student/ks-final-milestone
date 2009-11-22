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

import java.util.List;

/**
 *
 * @author nwright
 */
public class OrchestrationObject
{

 private List<OrchestrationObjectField> fields;
 private String name;

 public void setName (String name)
 {
  this.name = name;
 }

 public String getName ()
 {
  return name;
 }

 public void setFields (List<OrchestrationObjectField> fields)
 {
  this.fields = fields;
 }

 public List<OrchestrationObjectField> getFields ()
 {
  return fields;
 }

 private boolean hasOwnCreateUpdate;

 public boolean hasOwnCreateUpdate ()
 {
  return hasOwnCreateUpdate;
 }

 public void setHasOwnCreateUpdate (boolean hasOwnCreateUpdate)
 {
  this.hasOwnCreateUpdate = hasOwnCreateUpdate;
 }

 private OrchestrationObjectField inlineField;

 public OrchestrationObjectField getInlineField ()
 {
  return inlineField;
 }

 public void setInlineField (OrchestrationObjectField inlineField)
 {
  this.inlineField = inlineField;
 }


 private String orchestrationPackagePath;

 public String getOrchestrationPackagePath ()
 {
  return orchestrationPackagePath;
 }

 public void setOrchestrationPackagePath (String packagePath)
 {
  this.orchestrationPackagePath = packagePath;
 }


 private String infoPackagePath;

 public String getInfoPackagePath ()
 {
  return infoPackagePath;
 }

 public void setInfoPackagePath (String infoPackagePath)
 {
  this.infoPackagePath = infoPackagePath;
 }

 public String getInfoJavaClassName ()
 {
  return name.substring (0, 1).toUpperCase () + name.substring (1);
 }

 public String getFullyQualifiedInfoJavaClassName ()
 {
  return this.infoPackagePath + "." + this.getInfoJavaClassName ();
 }

 public String getFullyQualifiedJavaClassAssemblerName ()
 {
  return orchestrationPackagePath + "." + getInfoJavaClassName () + "Assembler";
 }

 public String getJavaClassHelperName ()
 {
  if (inlineField == null)
  {
   return getInfoJavaClassName () + "Helper";
  }
  return inlineField.getParent ().getInfoJavaClassName ()
    + getInfoJavaClassName () + "Helper";
 }

 public String getFullyQualifiedJavaClassHelperName ()
 {
   return orchestrationPackagePath + "." + getJavaClassHelperName ();
 }

  public String getJavaClassMetadataName ()
 {
  if (inlineField == null)
  {
   return getInfoJavaClassName () + "Metadata";
  }
  return inlineField.getParent ().getInfoJavaClassName ()
    + getInfoJavaClassName () + "Metadata";
 }

 public String getFullyQualifiedJavaClassMetadataName ()
 {
   return orchestrationPackagePath + "." + getJavaClassMetadataName ();
 }

 private String assembleFromClass;

 public String getAssembleFromClass ()
 {
  return assembleFromClass;
 }

 public void setAssembleFromClass (String assembleFromClass)
 {
  this.assembleFromClass = assembleFromClass;
 }

 @Override
 public boolean equals (Object obj)
 {
  OrchestrationObject that = (OrchestrationObject) obj;
  return this.name.equals (that.name);
 }

 @Override
 public int hashCode ()
 {
  return this.name.hashCode ();
 }

 @Override
 public String toString ()
 {
  return this.name;
 }


}
