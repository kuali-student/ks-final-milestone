package org.kuali.student.common.dictionary.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.kuali.student.common.validator.ServerDateParser;
import org.kuali.student.common.validator.ValidatorUtils;
import org.kuali.student.core.dictionary.dto.CaseConstraint;
import org.kuali.student.core.dictionary.dto.DataType;

import org.kuali.student.core.dictionary.dto.FieldDefinition;
import org.kuali.student.core.dictionary.dto.LookupConstraint;
import org.kuali.student.core.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.core.dictionary.dto.ValidCharsConstraint;
import org.kuali.student.core.dictionary.dto.WhenConstraint;

public class DictionaryValidator
{

 private ObjectStructureDefinition os;
 private boolean processSubStructures = false;
 private Set<ObjectStructureDefinition> alreadyValidated;

 public DictionaryValidator (ObjectStructureDefinition os,
                             Set<ObjectStructureDefinition> alreadyValidated,
                             boolean processSubstructures)
 {
  this.os = os;
  this.alreadyValidated = alreadyValidated;
  this.processSubStructures = processSubstructures;
 }

 public List<String> validate ()
 {
  List<String> errors = new ArrayList ();
  if (os.getName () == null)
  {
   errors.add ("The name cannbe be left null");
  }
  if (os.getBusinessObjectClass () != null)
  {
   errors.add (
     "The business object class is not used and should not be filled in");
  }
//  else if (this.getClass (os.getName ()) == null)
//  {
//   errors.add ("The name does not exist on the class path");
//  }

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

// private Class getClass (String className)
// {
//  try
//  {
//   return Class.forName (className);
//  }
//  catch (ClassNotFoundException ex)
//  {
//   return null;
//  }
// }

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
   if (fd.getDataObjectStructure () == null)
   {
    errors.add (
      "field " + fd.getName ()
      + " does not have an object structure definition but it required on a complex type");
   }
   else
   {
    if (this.processSubStructures)
    {
     if (alreadyValidated.add (fd.getDataObjectStructure ()))
     {
      errors.addAll (new DictionaryValidator (fd.getDataObjectStructure (),
                                              alreadyValidated,
                                              processSubStructures).validate ());
     }
    }
   }
  }
  validateConversion (errors, fd.getName (), "defaultValue", fd.getDataType (), fd.getDefaultValue ());
  validateConversion (errors, fd.getName (), "exclusiveMin", fd.getDataType (), fd.getExclusiveMin ());
  validateConversion (errors, fd.getName (), "inclusiveMax", fd.getDataType (), fd.getInclusiveMax ());
  //TODO: Cross compare to make sure min is not greater than max and that default value is valid itself
  if (fd.getMaxLength () != null)
  {
   try
   {
    Integer.parseInt (fd.getMaxLength ());
   }
   catch (NumberFormatException ex)
   {
    errors.add (
      "field " + fd.getName ()
      + " has a maxlength that is not an integer");
   }
  }

  if (fd.getLookupDefinition () != null)
  {
   errors.addAll (validateLookup (fd, fd.getLookupDefinition ()));
  }
  if (fd.getCaseConstraint () != null)
  {
   errors.addAll (validateCase (fd, fd.getCaseConstraint ()));
  }
  if (fd.getValidChars () != null)
  {
   errors.addAll (validateValidChars (fd, fd.getValidChars ()));
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

 private Object validateConversion (List<String> errors, String fieldName,
                                    String propertyName, DataType dataType,
                                    Object value)
 {
  if (value == null)
  {
   return null;
  }
  switch (dataType)
  {
   case STRING:
    return value.toString ().trim ();
//    case DATE, TRUNCATED_DATE, BOOLEAN, INTEGER, FLOAT, DOUBLE, LONG, COMPLEX
   case LONG:
    try
    {
     return ValidatorUtils.getLong (value);
    }
    catch (NumberFormatException ex)
    {
     errors.add (
       "field " + fieldName
       + " has a " + propertyName
       + " that cannot be converted into a long integer");
    }
    return null;
   case INTEGER:
    try
    {
     return ValidatorUtils.getInteger (value);
    }
    catch (NumberFormatException ex)
    {
     errors.add (
       "field " + fieldName
       + " has a " + propertyName + " that cannot be converted into an integer");
    }
    return null;
   case FLOAT:
    try
    {
     return ValidatorUtils.getFloat (value);
    }
    catch (NumberFormatException ex)
    {
     errors.add (
       "field " + fieldName
       + " has a " + propertyName
       + " that cannot be converted into a floating point value");
    }
    return null;
   case DOUBLE:
    try
    {
     return ValidatorUtils.getFloat (value);
    }
    catch (NumberFormatException ex)
    {
     errors.add (
       "field " + fieldName
       + " has a " + propertyName
       + " that cannot be converted into a double sized floating point value");
    }
    return null;
   case BOOLEAN:
    if (value instanceof Boolean)
    {
     return ((Boolean) value).booleanValue ();
    }
    if (value instanceof String)
    {
     if (((String) value).trim ().equalsIgnoreCase ("true"))
     {
      return true;
     }
     if (((String) value).trim ().equalsIgnoreCase ("false"))
     {
      return true;
     }
    }
    errors.add (
      "field " + fieldName
      + " has a " + propertyName
      + " that cannot be converted into a boolean true/false");
    return null;
   case DATE:
   case TRUNCATED_DATE:
    if (value instanceof Date)
    {
     return (Date) value;
    }
    try
    {
     // TODO: make the date parser configurable like the validator is
     return new ServerDateParser ().parseDate (value.toString ());
    }
    catch (Exception e)
    {
     errors.add (
       "field " + fieldName
       + " has a " + propertyName
       + " that cannot be converted into a date");
    }
    return null;
   default:
     errors.add (
       "field " + fieldName
       + " has a " + propertyName
       + " that cannot be converted into an unknown/unhandled data type");
    return null;
  }
 }

 private List<String> validateValidChars (FieldDefinition fd,
                                          ValidCharsConstraint vc)
 {
  List<String> errors = new ArrayList ();
  String validChars = vc.getValue ();
  int typIdx = validChars.indexOf (":");
  String processorType = "regex";
  if (-1 == typIdx)
  {
   validChars = "[" + validChars + "]*";
  }
  else
  {
   processorType = validChars.substring (0, typIdx);
   validChars = validChars.substring (typIdx + 1);
  }
  if ( ! processorType.equalsIgnoreCase ("regex"))
  {
   errors.add (
     "field " + fd.getName ()
     + " has an invalid valid chars processor type: a simple list of characters or a regex: is supported");
   return errors;
  }
  try
  {
   Pattern pattern = Pattern.compile (validChars);
  }
  catch (PatternSyntaxException ex)
  {
   errors.add ("field " + fd.getName ()
               + " has in invalid character pattern for a regular expression: "
               + validChars);
  }
  return errors;
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
 public static final String NOT_EQUAL = "not_equal";
 private static final String[] VALID_OPERATORS =
 {
  NOT_EQUAL, EQUALS, GREATER_THAN_EQUAL, LESS_THAN_EQUAL, GREATER_THAN, LESS_THAN
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
