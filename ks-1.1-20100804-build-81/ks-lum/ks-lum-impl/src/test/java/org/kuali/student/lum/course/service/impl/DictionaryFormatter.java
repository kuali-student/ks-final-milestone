package org.kuali.student.lum.course.service.impl;

import org.kuali.student.core.dictionary.dto.DataType;
import org.kuali.student.core.dictionary.dto.FieldDefinition;
import org.kuali.student.core.dictionary.dto.ObjectStructureDefinition;

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

 public String format ()
 {
  line ();
//  add ("======= start dump of object structure definition ========");
  line ();
  add (calcSimpleName (os.getName ()));
  line ();
  for (FieldDefinition fd : os.getAttributes ())
  {
   add (pad (fd.getName (), 30));
   tab ();
   add (pad (calcRequired (fd), 10));
   tab ();
   add (pad (calcDataType (fd), 25));
   tab ();
   add (pad (calcLength (fd), 15));
   tab ();
   add (calcRepeating (fd));
   tab ();
   add (calcValidChars (fd));
   tab ();
   add (calcLookup (fd));
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
    throw new IllegalArgumentException (fd.getName () + " is complex but does not have a sub-structure defined");
   }
   return calcSimpleName (fd.getDataObjectStructure ().getName ());
  }
  return fd.getDataType ().toString ();
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

  return "";
//  return "optional";
 }

 private String calcValidChars (FieldDefinition fd)
 {
  if (fd.getValidChars () == null)
  {
   return "";
  }
  return fd.getValidChars ().getValue ();
 }

 private String calcLookup (FieldDefinition fd)
 {
  if (fd.getLookupDefinition () == null)
  {
   return "";
  }
  return fd.getLookupDefinition ().getId ();
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
   return "";
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
  return "";
 }
}
