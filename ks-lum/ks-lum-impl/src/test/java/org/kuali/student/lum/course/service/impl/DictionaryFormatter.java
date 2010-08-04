package org.kuali.student.lum.course.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.kuali.student.core.dictionary.dto.CaseConstraint;
import org.kuali.student.core.dictionary.dto.CommonLookupParam;
import org.kuali.student.core.dictionary.dto.Constraint;
import org.kuali.student.core.dictionary.dto.DataType;
import org.kuali.student.core.dictionary.dto.FieldDefinition;
import org.kuali.student.core.dictionary.dto.LookupConstraint;
import org.kuali.student.core.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.core.dictionary.dto.RequiredConstraint;
import org.kuali.student.core.dictionary.dto.WhenConstraint;

public class DictionaryFormatter
{

 private StringBuilder builder = new StringBuilder (5000);
 private ObjectStructureDefinition os;
 private String tab;

 public DictionaryFormatter (ObjectStructureDefinition os, String tab)
 {
  this.os = os;
  this.tab = tab;
 }
 public static final String UNBOUNDED = "unbounded";

 private String pad (String str, int size)
 {
  StringBuilder padStr = new StringBuilder (size);
  padStr.append (str);
  while (padStr.length () < size)
  {
   padStr.append (' ');
  }
  return padStr.toString ();
 }

 private void add (String s)
 {
  builder.append (s);
 }

 private void line ()
 {
  add ("\n");
 }

 private void tab ()
 {
  add (tab);
 }

 public String formatForWiki ()
 {
  line ();
//  add ("======= start dump of object structure definition ========");
  line ();
  add ("h2. " + calcSimpleName (os.getName ()));
  line ();
  tab ();
  tab ();
  add ("Field");
  tab ();
  tab ();
  add ("Required?");
  tab ();
  tab ();
  add ("DataType");
  tab ();
  tab ();
  add ("Length");
  tab ();
  tab ();
  add ("Dynamic");
  tab ();
  tab ();
  add ("Default");
  tab ();
  tab ();
  add ("Repeats?");
  tab ();
  tab ();
  add ("Valid Characters");
  tab ();
  tab ();
  add ("Lookup");
  tab ();
  tab ();
  add ("Cross Field");
  tab ();
  tab ();
  line ();
  for (FieldDefinition fd : os.getAttributes ())
  {
   tab ();
   add (pad (fd.getName (), 30));
   tab ();
   add (pad (calcRequired (fd), 10));
   tab ();
   add (pad (calcDataType (fd), 25));
   tab ();
   add (pad (calcLength (fd), 15));
   tab ();
   add (pad (calcDynamic (fd), 7));
   tab ();
   add (pad (calcDefaultValue (fd), 15));
   tab ();
   add (calcRepeating (fd));
   tab ();
   add (calcValidChars (fd));
   tab ();
   add (calcLookup (fd));
   tab ();
   add (calcCrossField (fd));
   tab ();
   line ();
  }
//  add ("======= end dump of object structure definition ========");
  line ();
  return builder.toString ();
 }

 private String calcDataType (FieldDefinition fd)
 {
  if (fd.getDataType ().equals (DataType.COMPLEX))
  {
   if (fd.getDataObjectStructure () == null)
   {
    throw new IllegalArgumentException (
      fd.getName () + " is complex but does not have a sub-structure defined");
   }
   return "[#" + calcSimpleName (fd.getDataObjectStructure ().getName ()) + "]";
  }
  return fd.getDataType ().toString ();
 }

 private String calcDefaultValue (FieldDefinition fd)
 {
  if (fd.getDefaultValue () != null)
  {
   return fd.getDefaultValue ().toString ();
  }
  return " ";
 }

 private String calcDynamic (FieldDefinition fd)
 {
  if (fd.isDynamic ())
  {
   return "dynamic";
  }
  return " ";
 }

 private String calcSimpleName (String name)
 {
  if (name.lastIndexOf (".") != -1)
  {
   name = name.substring (name.lastIndexOf (".") + 1);
  }
  return name;
 }

 private String calcRequired (FieldDefinition fd)
 {
  if (fd.getMaxOccurs () != null)
  {
   if ( ! fd.getMaxOccurs ().equals (UNBOUNDED))
   {
    if (Integer.parseInt (fd.getMaxOccurs ()) == 0)
    {
     return "NOT USED";
    }
   }
  }

  if (fd.getMinOccurs () != null)
  {
   if (fd.getMinOccurs () >= 1)
   {
    return "required";
   }
  }

  return " ";
//  return "optional";
 }

 private String calcValidChars (FieldDefinition fd)
 {
  if (fd.getValidChars () == null)
  {
   return " ";
  }
  String escaped = escapeWiki (fd.getValidChars ().getValue ());
  return "[" + escaped
         + "|KULSTG:Formatted View of Base Dictionary#Valid Character Definitions]";
 }

 private String escapeWiki (String str)
 {
  StringBuilder builder = new StringBuilder (str.length ());
  for (int i = 0; i < str.length (); i ++)
  {
   char c = str.charAt (i);
   switch (c)
   {
    case '[':
    case ']':
     builder.append ('\\');
   }
   builder.append (c);
  }
  return builder.toString ();
 }

 private String calcLookup (FieldDefinition fd)
 {
  if (fd.getLookupDefinition () == null)
  {
   return " ";
  }
  StringBuilder builder = new StringBuilder ();
  LookupConstraint lc = fd.getLookupDefinition ();
  builder.append (lc.getId ());
//  this is the search description not the lookup description
//  builder.append (" - ");
//  builder.append (lc.getDesc ());
  String and = "";
  builder.append ("\\\\");
  builder.append ("\n");
  builder.append ("Implemented using search:");
  builder.append (lc.getSearchTypeId ());
  List<CommonLookupParam> configuredParameters = filterConfiguredParams (
    lc.getParams ());
  if (configuredParameters.size () > 0)
  {
   builder.append ("\\\\");
   builder.append ("\n");
   builder.append (" where ");
   and = "";
   for (CommonLookupParam param : configuredParameters)
   {
    builder.append (and);
    and = " and ";
    builder.append (param.getName ());
    builder.append ("=");
    if (param.getDefaultValueString () != null)
    {
     builder.append (param.getDefaultValueString ());
     continue;
    }
    if (param.getDefaultValueList () != null)
    {
     String comma = "";
     for (String defValue : param.getDefaultValueList ())
     {
      builder.append (comma);
      comma = ", ";
      builder.append (defValue);
     }
    }
   }
  }
  return builder.toString ();
 }

 private List<CommonLookupParam> filterConfiguredParams (
   List<CommonLookupParam> params)
 {
  List list = new ArrayList ();
  if (params == null)
  {
   return list;
  }
  if (params.size () == 0)
  {
   return list;
  }
  for (CommonLookupParam param : params)
  {
   if (param.getDefaultValueString () != null)
   {
    list.add (param);
    continue;
   }
   if (param.getDefaultValueList () != null)
   {
    list.add (param);
   }
  }
  return list;
 }

 private String calcRepeating (FieldDefinition fd)
 {
  if (fd.getMaxOccurs () == null)
  {
   return "???";
  }
  if (fd.getMaxOccurs ().equals (UNBOUNDED))
  {
   if (fd.getMinOccurs () != null && fd.getMinOccurs () > 1)
   {
    return "repeating: minimum " + fd.getMinOccurs () + " times";
   }
   return "repeating: unlimited";
  }
  if (Integer.parseInt (fd.getMaxOccurs ()) == 0)
  {
   return "NOT USED";
  }
  if (Integer.parseInt (fd.getMaxOccurs ()) == 1)
  {
   return " ";
//   return "single";
  }

  if (fd.getMinOccurs () != null)
  {
   if (fd.getMinOccurs () > 1)
   {
    return "repeating: " + fd.getMinOccurs () + " to " + fd.getMaxOccurs ()
           + " times";
   }
  }
  return "repeating: maximum " + fd.getMaxOccurs () + " times";
 }

 private String calcLength (FieldDefinition fd)
 {
  if (fd.getMaxLength () != null)
  {
   if (fd.getMinLength () != null && fd.getMinLength () != 0)
   {
    if (Integer.parseInt (fd.getMaxLength ()) == fd.getMinLength ())
    {
     return ("(must be " + fd.getMaxLength () + ")");
    }
    return "(" + fd.getMinLength () + " to " + fd.getMaxLength () + ")";
   }
   return "(up to " + fd.getMaxLength () + ")";
  }
  if (fd.getMinLength () != null)
  {
   return "(over " + fd.getMinLength () + ")";
  }
  return " ";
 }

 private String calcCrossField (FieldDefinition fd)
 {
  StringBuilder b = new StringBuilder ();
  String semicolon = "";
  String cfr = calcCrossFieldRequire (fd);
  if (cfr != null)
  {
   b.append (semicolon);
   semicolon = "; ";
   b.append (cfr);
  }
  String cfw = calcCrossFieldWhen (fd);
  if (cfw != null)
  {
   b.append (semicolon);
   semicolon = "; ";
   b.append (cfw);
  }
  if (b.length () == 0)
  {
   return " ";
  }
  return b.toString ();
 }

 private String calcCrossFieldRequire (FieldDefinition fd)
 {
  if (fd.getRequireConstraint () == null)
  {
   return null;
  }
  if (fd.getRequireConstraint ().size () == 0)
  {
   return null;
  }
  StringBuilder b = new StringBuilder ();
  String comma = "";
  b.append ("if not empty then ");
  for (RequiredConstraint rc : fd.getRequireConstraint ())
  {
   b.append (comma);
   comma = ", ";
   b.append (rc.getFieldPath ());
  }
  if (fd.getRequireConstraint ().size () == 1)
  {
   b.append (" is");
  }
  else
  {
   b.append (" are");
  }
  b.append (" also required");
  return b.toString ();
 }

 private String calcCrossFieldWhen (FieldDefinition fd)
 {
  if (fd.getCaseConstraint () == null)
  {
   return null;
  }
  StringBuilder b = new StringBuilder ();
  CaseConstraint cc = fd.getCaseConstraint ();
  for (WhenConstraint wc : cc.getWhenConstraint ())
  {
   b.append ("\\\\");
   b.append ("\n");
   b.append ("when ");
   b.append (cc.getFieldPath ());
   b.append (" ");
   if ( ! cc.isCaseSensitive ())
   {
    b.append ("ignoring case ");
   }
   b.append (cc.getOperator ());
   b.append (" ");

   b.append ("\\\\");
   b.append ("\n");
   String comma = "";
   for (Object value : wc.getValues ())
   {
    b.append (comma);
    comma = " or ";
    b.append (asString (value));
   }
   b.append ("\\\\");
   b.append ("\n");
   b.append ("then override constraint:"
             + calcOverride (fd, wc.getConstraint ()));
  }
  return b.toString ();
 }

 private String calcOverride (FieldDefinition fd, Constraint cons)
 {
  StringBuilder b = new StringBuilder ();
  b.append (calcOverride ("serviceSide", fd.isServerSide (), cons.isServerSide ()));
  b.append (calcOverride ("exclusiveMin", fd.getExclusiveMin (), cons.getExclusiveMin ()));
  b.append (calcOverride ("inclusiveMax", fd.getInclusiveMax (), cons.getInclusiveMax ()));
  b.append (calcOverride ("minOccurs", fd.getMinOccurs (), cons.getMinOccurs ()));
  b.append (calcOverride ("validchars", fd.getValidChars (), cons.getValidChars ()));
  b.append (calcOverride ("validchars", fd.getLookupDefinition (), cons.getLookupDefinition ()));
  //TODO: other more complex constraints
  return b.toString ();
 }

 private String calcOverride (String attribute, boolean val1, boolean val2)
 {
  if (val1 == val2)
  {
   return "";
  }
  return " " + attribute + "=" + val2;
 }

 private String calcOverride (String attribute, String val1, String val2)
 {
  if (val1 == null && val2 == null)
  {
   return "";
  }
  if (val1 == val2)
  {
   return "";
  }
  if (val1 == null)
  {
   return attribute + "=" + val2;
  }
  if (val1.equals (val2))
  {
   return "";
  }
  return " " + attribute + "=" + val2;
 }

 private String calcOverride (String attribute, Object val1, Object val2)
 {
  if (val1 == null && val2 == null)
  {
   return "";
  }
  if (val1 == val2)
  {
   return "";
  }
  if (val1 == null)
  {
   return attribute + "=" + val2;
  }
  if (val1.equals (val2))
  {
   return "";
  }
  return " " + attribute + "=" + asString (val2);
 }

 private String asString (Object value)
 {
  if (value == null)
  {
   return "null";
  }
  if (value instanceof String)
  {
   return (String) value;
  }
  return value.toString ();
 }
}
