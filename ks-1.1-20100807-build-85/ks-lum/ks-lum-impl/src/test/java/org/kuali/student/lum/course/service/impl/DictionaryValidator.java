package org.kuali.student.lum.course.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.kuali.student.core.dictionary.dto.CaseConstraint;
import org.kuali.student.core.dictionary.dto.DataType;

import org.kuali.student.core.dictionary.dto.FieldDefinition;
import org.kuali.student.core.dictionary.dto.LookupConstraint;
import org.kuali.student.core.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.core.dictionary.dto.WhenConstraint;

public class DictionaryValidator
{

 private ObjectStructureDefinition os;

 public DictionaryValidator (ObjectStructureDefinition os)
 {
  this.os = os;
 }

 public List<String> validate ()
 {
  List<String> errors = new ArrayList ();

  if (os.getAttributes () == null)
  {
   errors.add ("getAttribues () is null -- null for field defintion");
   return errors;
  }
  if (os.getAttributes ().size () == 0)
  {
   errors.add ("No fields defined for complex object structure");
   return errors;
  }
  Set<String> fieldNames = new HashSet ();
  for (FieldDefinition fd : os.getAttributes ())
  {
   if (fd.getName () != null)
   {
    if ( ! fieldNames.add (fd.getName ()))
    {
     errors.add (fd.getName () + " is defined more than once");
    }
   }
   errors.addAll (validateField (fd));
  }
  return errors;
 }

 private List<String> validateField (FieldDefinition fd)
 {
  List<String> errors = new ArrayList ();
  if (fd.getName () == null)
  {
   errors.add ("name cannot be null");
  }
  else if (fd.getName ().trim ().equals (""))
  {
   errors.add ("name cannot be blank");
  }
  if (fd.getDataType ().equals (DataType.COMPLEX))
  {
   errorIfNotNull (errors, fd, "exclusiveMin", fd.getExclusiveMin ());
   errorIfNotNull (errors, fd, "inclusiveMax", fd.getInclusiveMax ());
   errorIfNotNull (errors, fd, "max length", fd.getMaxLength ());
   errorIfNotNull (errors, fd, "min length", fd.getMinLength ());
   errorIfNotNull (errors, fd, "valid chars", fd.getValidChars ());
   errorIfNotNull (errors, fd, "lookup", fd.getLookupDefinition ());
  }
  if (fd.getLookupDefinition () != null)
  {
   errors.addAll (validateLookup (fd, fd.getLookupDefinition ()));
  }
  if (fd.getCaseConstraint () != null)
  {
   errors.addAll (validateCase (fd, fd.getCaseConstraint ()));
  }
  return errors;
 }

 private void errorIfNotNull (List<String> errors, FieldDefinition fd,
                              String validation,
                              Object value)
 {
  if (value != null)
  {
   errors.add ("field " + fd.getName () + " has a " + validation
               + " but it cannot be specified on a complex type");
  }
 }

 private List<String> validateLookup (FieldDefinition fd, LookupConstraint lc)
 {
  List<String> errors = new ArrayList ();
  if (lc.getParams () == null)
  {
   errors.add ("field " + fd.getName () + " has a lookup with null parameters");
  }
  //TODO: more validation
  return errors;
 }
 public static final String GREATER_THAN_EQUAL = "greater_than_equal";
 public static final String LESS_THAN_EQUAL = "less_than_equal";
 public static final String GREATER_THAN = "greater_than";
 public static final String LESS_THAN = "less_than";
 public static final String EQUALS = "equals";
 private static final String[] VALID_OPERATORS =
 {
  EQUALS, GREATER_THAN_EQUAL, LESS_THAN_EQUAL, GREATER_THAN, LESS_THAN
 };

 private List<String> validateCase (FieldDefinition fd, CaseConstraint cc)
 {
  List<String> errors = new ArrayList ();
  if (cc.getOperator () == null)
  {
   errors.add ("field " + fd.getName ()
               + " has a case constraint with no operator");
  }
  else
  {
   boolean found = false;
   for (int i = 0; i < VALID_OPERATORS.length; i ++)
   {
    if (VALID_OPERATORS[i].equalsIgnoreCase (cc.getOperator ()))
    {
     found = true;
     break;
    }
   }
   if ( ! found)
   {
    errors.add ("field " + fd.getName ()
                + " has a case constraint with an unknown operator "
                + cc.getOperator ());
   }
  }
  if (cc.getFieldPath () == null)
  {
   errors.add (
     "field " + fd.getName ()
     + " has a case constraint with a null for the field to use for the comparison");
  }
  else if (cc.getFieldPath ().trim ().equals (""))
  {
   errors.add (
     "field " + fd.getName ()
     + " has a case constraint with blanks for the field to use for the comparison");
  }
  if (cc.getWhenConstraint () == null)
  {
   errors.add ("field " + fd.getName ()
               + " has a case constraint but null when statements");
   return errors;
  }
  if (cc.getWhenConstraint ().size () == 0)
  {
   errors.add ("field " + fd.getName ()
               + " has a case constraint but has no when statements");
  }
  for (WhenConstraint wc : cc.getWhenConstraint ())
  {
   if (wc.getConstraint () == null)
   {
    errors.add (
      "field " + fd.getName ()
      + " has a as case constraint with a when statement that has no overriding constraints specified");
   }
  }
  //TODO: more validation
  return errors;
 }
}
