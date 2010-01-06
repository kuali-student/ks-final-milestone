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
public class OrchestrationObject
{

 public enum Source
 {
  MESSAGE_STRUCTURE, ORCH_OBJS;
 }

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

 public String getJavaClassInfoName ()
 {
  return name.substring (0, 1).toUpperCase () + name.substring (1);
 }

 public String getFullyQualifiedJavaClassInfoName ()
 {
  return this.infoPackagePath + "." + this.getJavaClassInfoName ();
 }

 public String getJavaClassHelperName ()
 {
  if (inlineField == null)
  {
   return getJavaClassInfoName () + "Helper";
  }
  return inlineField.getParent ().getJavaClassInfoName ()
    + getJavaClassInfoName () + "Helper";
 }

 public String getFullyQualifiedJavaClassHelperName ()
 {
   return orchestrationPackagePath + "." + getJavaClassHelperName ();
 }

  public String getJavaClassConstantsName ()
 {
  if (inlineField == null)
  {
   return getJavaClassInfoName () + "Constants";
  }
  return inlineField.getParent ().getJavaClassInfoName ()
    + getJavaClassInfoName () + "Constants";
 }

 public String getFullyQualifiedJavaClassConstantsName ()
 {
   return orchestrationPackagePath + "." + getJavaClassConstantsName ();
 }


  public String getJavaClassMetadataName ()
 {
  if (inlineField == null)
  {
   return getJavaClassInfoName () + "Metadata";
  }
  return inlineField.getParent ().getJavaClassInfoName ()
    + getJavaClassInfoName () + "Metadata";
 }

 public String getFullyQualifiedJavaClassMetadataName ()
 {
   return orchestrationPackagePath + "." + getJavaClassMetadataName ();
 }

 public String getJavaClassAssemblerName ()
 {
  if (inlineField == null)
  {
   return getJavaClassInfoName () + "Assembler";
  }
  return inlineField.getParent ().getJavaClassInfoName ()
    + getJavaClassInfoName () + "Assembler";
 }

 public String getFullyQualifiedJavaClassAssemblerName ()
 {
   return orchestrationPackagePath + ".assembler." + getJavaClassAssemblerName ();
 }


 private Source source;

 public Source getSource ()
 {
  return source;
 }

 public void setSource (Source source)
 {
  this.source = source;
 }

}
