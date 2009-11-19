/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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

 private String dataPackagePath;

 public String getDataPackagePath ()
 {
  return dataPackagePath;
 }

 public void setDataPackagePath (String packagePath)
 {
  this.dataPackagePath = packagePath;
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
  return this.getInfoPackagePath () + "." + this.getInfoJavaClassName ();
 }

 public String getFullyQualifiedAssemblerName ()
 {
  return dataPackagePath + ".assembler" +
   "." + getInfoJavaClassName () + "Assembler";
 }

 public String getJavaClassDataHelperName ()
 {
  return getInfoJavaClassName () + "DataHelper";
 }

 public String getFullyQualifiedJavaClassDataName ()
 {
  return dataPackagePath + "." + getJavaClassDataHelperName ();
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
