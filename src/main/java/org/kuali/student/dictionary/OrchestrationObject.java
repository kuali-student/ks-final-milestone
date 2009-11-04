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
}
