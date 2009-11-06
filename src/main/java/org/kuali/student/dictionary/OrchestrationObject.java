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

 private String packagePath;

 public String getPackagePath ()
 {
  return packagePath;
 }

 public void setPackagePath (String packagePath)
 {
  this.packagePath = packagePath;
 }

 public String getFullyQualifiedName ()
 {
  return packagePath +
   "." + name.substring (0, 1).toUpperCase () + name.substring (1);
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

}
