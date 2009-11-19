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

}
