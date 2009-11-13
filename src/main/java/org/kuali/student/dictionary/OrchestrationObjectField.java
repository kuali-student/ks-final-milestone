/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.dictionary;

/**
 *
 * @author nwright
 */
public class OrchestrationObjectField
{
 private OrchestrationObject parent;
 private String name;
 private String type;



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

 private boolean isList;

 public boolean isIsList ()
 {
  return isList;
 }

 public void setIsList (boolean isList)
 {
  this.isList = isList;
 }

}
