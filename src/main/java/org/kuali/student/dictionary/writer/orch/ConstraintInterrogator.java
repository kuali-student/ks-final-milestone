/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.dictionary.writer.orch;

import org.kuali.student.dictionary.model.Constraint;

/**
 *
 * @author nwright
 */
public class ConstraintInterrogator
{
 private Constraint cons;

 public ConstraintInterrogator (Constraint cons)
 {
  this.cons = cons;
 }

 public boolean constrainsSomething ()
 {
  if ( ! isEmpty (cons.getMinOccurs ()))
  {
   return true;
  }
  if ( ! isEmpty (cons.getMaxOccurs ()))
  {
   return true;
  }
  if ( ! isEmpty (cons.getMinLength ()))
  {
   return true;
  }
  if ( !isEmpty (cons.getMaxLength ()))
  {
   return true;
  }
  if ( ! isEmpty (cons.getMinValue ()))
  {
   return true;
  }
  if ( ! isEmpty (cons.getMaxValue ()))
  {
   return true;
  }
  if ( ! isEmpty (cons.getValidChars ()))
  {
   return true;
  }
  if ( ! isEmpty (cons.getLookup ()))
  {
   return true;
  }
 if ( ! isEmpty (cons.getClassName ()))
  {
   return true;
  }
  return false;
 }

 private boolean isEmpty (String value)
 {
  if (value == null)
  {
   return true;
  }
  if (value.trim ().equals (""))
  {
   return true;
  }
  return false;
 }

}
