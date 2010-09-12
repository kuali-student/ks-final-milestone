package org.kuali.student.lum.assembly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.kuali.student.core.assembly.data.ConstraintMetadata;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.LookupMetadata;
import org.kuali.student.core.assembly.data.LookupParamMetadata;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.MetadataInterrogator;

public class MetadataFormatter
{

 private StringBuilder builder = new StringBuilder (5000);
 private Metadata structMeta;
 private String rowSeperator = "\n";
 private String colSeperator = "|";
 private String name;
 private Class<?> clazz;
 private boolean processSubstructures = false;
 private int level;
 private Map<String, Metadata> subStructuresToProcess =
                               new LinkedHashMap ();
 private Set<Metadata> subStructuresAlreadyProcessed;

 public MetadataFormatter (String name,
                           Class<?> clazz,
                           Metadata structMeta,
                           Set<Metadata> subStructuresAlreadyProcessed,
                           int level,
                           boolean processSubstructures)
 {
  this.name = name;
  this.clazz = clazz;
  this.structMeta = structMeta;
  this.subStructuresAlreadyProcessed = subStructuresAlreadyProcessed;
  this.level = level;
  this.processSubstructures = processSubstructures;
 }

 public String getRowSeperator ()
 {
  return rowSeperator;
 }

 public void setRowSeperator (String rowSeperator)
 {
  this.rowSeperator = rowSeperator;
 }

 public String getColSeparator ()
 {
  return colSeperator;
 }

 public void setColSeparator (String separator)
 {
  this.colSeperator = separator;
 }

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

 public String formatForWiki ()
 {
  builder.append (rowSeperator);
//  builder.append ("======= start dump of object structure definition ========");
  builder.append (rowSeperator);
  builder.append ("h" + level + ". " + calcNotSoSimpleName (name));
  builder.append ("{anchor:" + name + "}");
  builder.append (rowSeperator);
  builder.append ("The DTO for these fields is " + structMeta.getName ());
  builder.append (rowSeperator);
  builder.append (colSeperator);
  builder.append (colSeperator);
  builder.append ("Field");
  builder.append (colSeperator);
  builder.append (colSeperator);
  builder.append ("Required?");
  builder.append (colSeperator);
  builder.append (colSeperator);
  builder.append ("DataType");
  builder.append (colSeperator);
  builder.append (colSeperator);
  builder.append ("Length");
  builder.append (colSeperator);
  builder.append (colSeperator);
  builder.append ("Dynamic");
  builder.append (colSeperator);
  builder.append (colSeperator);
  builder.append ("Default");
  builder.append (colSeperator);
  builder.append (colSeperator);
  builder.append ("Repeats?");
  builder.append (colSeperator);
  builder.append (colSeperator);
  builder.append ("Valid Characters");
  builder.append (colSeperator);
  builder.append (colSeperator);
  builder.append ("Lookup");
  builder.append (colSeperator);
  builder.append (colSeperator);
  builder.append ("Cross Field");
  builder.append (colSeperator);
  builder.append (colSeperator);
  builder.append (rowSeperator);

  List<String> keys = new ArrayList ();
  keys.addAll (structMeta.getProperties ().keySet ());
  Collections.sort (keys);
  for (String key : keys)
  {
   Metadata fieldMeta = structMeta.getProperties ().get (key);
   builder.append (colSeperator);
   builder.append (pad (key, 30));
   builder.append (colSeperator);
   builder.append (pad (calcRequired (fieldMeta), 10));
   builder.append (colSeperator);
   builder.append (pad (calcDataType (fieldMeta), 25));
   builder.append (colSeperator);
   builder.append (pad (calcLength (fieldMeta), 15));
   builder.append (colSeperator);
   builder.append (pad (calcDynamic (fieldMeta), 7));
   builder.append (colSeperator);
   builder.append (pad (calcDefaultValue (fieldMeta), 15));
   builder.append (colSeperator);
   builder.append (calcRepeating (fieldMeta));
   builder.append (colSeperator);
   builder.append (calcValidChars (fieldMeta));
   builder.append (colSeperator);
   builder.append (calcLookup (fieldMeta));
   builder.append (colSeperator);
   builder.append (calcCrossField (fieldMeta));
   builder.append (colSeperator);
   builder.append (rowSeperator);
  }

//  builder.append ("======= end dump of object structure definition ========");
  builder.append (rowSeperator);
  for (String subName : this.subStructuresToProcess.keySet ())
  {
   Metadata subOs = this.subStructuresToProcess.get (subName);
   if (this.subStructuresAlreadyProcessed.add (subOs))
   {
//    System.out.println ("formatting substructure " + subName);
    Class<?> subClazz = getClass (subOs.getName ());
    MetadataFormatter formatter =
                      new MetadataFormatter (subName, subClazz, subOs,
                                             subStructuresAlreadyProcessed,
                                             level + 1,
                                             this.processSubstructures);
    builder.append (formatter.formatForWiki ());
    builder.append (rowSeperator);
   }
   else
   {
//    System.out.println ("skipping substructure because already processed it: "
//                        + subName);
   }
  }

  return builder.toString ();
 }

 private Class getClass (String className)
 {
  try
  {
   return Class.forName (className);
  }
  catch (ClassNotFoundException ex)
  {
   throw new IllegalArgumentException ("Could not find class for " + className);
  }
 }

 private String calcDataType (Metadata fieldMeta)
 {
//  if (fieldMeta.getDataType ().equals (Data.DataType.DATA))
//  {
//   if (fieldMeta.getProperties () == null)
//   {
//    throw new IllegalArgumentException (
//      fieldMeta.getName () + " is complex but does not have a sub-structure defined");
//   }
//   String subStrucName = calcComplexSubStructureName (fieldMeta);
//   if (this.processSubstructures)
//   {
//    if ( ! this.subStructuresAlreadyProcessed.contains (
//      meta.get))
//    {
////     System.out.println ("Adding " + subStrucName + " to set to be processed");
//     this.subStructuresToProcess.put (subStrucName, meta.getDataObjectStructure ());
//    }
//   }
//   return "[" + calcNotSoSimpleName (subStrucName) + "|#" + subStrucName + "]";
//  }
  return fieldMeta.getDataType ().toString ();
 }

 private String calcDefaultValue (Metadata fieldMeta)
 {
  if (fieldMeta.getDefaultValue () != null)
  {
   return fieldMeta.getDefaultValue ().toString ();
  }
  return " ";
 }

 private String calcDynamic (Metadata meta)
 {
  if (meta.isDynamic ())
  {
   return "dynamic";
  }
  return " ";
 }

 private String calcComplexSubStructureName (Metadata fieldMeta)
 {
//  if (this.processSubstructures)
//  {
//   return name + "." + meta.getName () + "." + calcSimpleName (
//     meta..getName ());
//  }
  return fieldMeta.getName ();
 }

 private String calcSimpleName (String name)
 {
  if (name.lastIndexOf (".") != -1)
  {
   name = name.substring (name.lastIndexOf (".") + 1);
  }
  return name;
 }

 private String calcNotSoSimpleName (String name)
 {
  if (name.lastIndexOf (".") == -1)
  {
   return name;
  }
  String simpleName = calcSimpleName (name);
  String fieldName = calcSimpleName (name.substring (0, name.length ()
                                                        - simpleName.length ()
                                                        - 1));
  return fieldName + "." + simpleName;
 }

 private String calcRequired (Metadata fieldMeta)
 {
  for (ConstraintMetadata cons : fieldMeta.getConstraints ())
  {
   if (cons.getMaxOccurs () != null)
   {
    if (cons.getMaxOccurs () == 0)
    {
     return "NOT USED";
    }
   }

   if (cons.getMinOccurs () != null)
   {
    if (cons.getMinOccurs () >= 1)
    {
     return "required";
    }
   }
  }
  return " ";
//  return "optional";
 }
 private static final String LINK_TO_DEFINITIONS =
                             "KULSTG:Formatted View of Base Dictionary#Valid Character Definitions";

 private String calcValidChars (Metadata fieldMeta)
 {
  for (ConstraintMetadata cons : fieldMeta.getConstraints ())
  {
   if (cons.getValidChars () == null)
   {
    continue;
   }
   String validChars = escapeWiki (cons.getValidChars ());
   String descr = "[" + "See" + "|" + LINK_TO_DEFINITIONS + "]" + "\\\\\n"
                  + validChars;
   return descr;
  }
  return " ";
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

 private String calcLookup (Metadata fieldMeta)
 {
  if (fieldMeta.getInitialLookup () == null)
  {
   return " ";
  }
  StringBuilder builder = new StringBuilder ();
  LookupMetadata lm = fieldMeta.getInitialLookup ();
  builder.append (lm.getId ());
//  this is the search description not the lookup description
//  builder.append (" - ");
//  builder.append (lc.getDesc ());
  String and = "";
  builder.append ("\\\\");
  builder.append ("\n");
  builder.append ("Implemented using search: ");
  String searchPage = calcWikiSearchPage (lm.getSearchTypeId ());
  builder.append ("[" + lm.getSearchTypeId () + "|" + searchPage + "#"
                  + lm.getSearchTypeId () + "]");
  List<LookupParamMetadata> configuredParameters = filterConfiguredParams (
    lm.getParams ());
  if (configuredParameters.size () > 0)
  {
   builder.append ("\\\\");
   builder.append ("\n");
   builder.append (" where ");
   and = "";
   for (LookupParamMetadata param : configuredParameters)
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
 private static final String PAGE_PREFIX = "Formatted View of ";
 private static final String PAGE_SUFFIX = " Searches";

 private String calcWikiSearchPage (String searchType)
 {
  return PAGE_PREFIX + calcWikigPageAbbrev (searchType) + PAGE_SUFFIX;
 }

 private String calcWikigPageAbbrev (String searchType)
 {
  if (searchType == null)
  {
   return null;
  }
  if (searchType.equals ("enumeration.management.search"))
  {
   return "EM";
  }
  if (searchType.startsWith ("lu."))
  {
   return "LU";
  }
  if (searchType.startsWith ("cluset."))
  {
   return "LU";
  }
  if (searchType.startsWith ("lo."))
  {
   return "LO";
  }
  if (searchType.startsWith ("lrc."))
  {
   return "LRC";
  }
  if (searchType.startsWith ("comment."))
  {
   return "Comment";
  }
  if (searchType.startsWith ("org."))
  {
   return "Organization";
  }
  if (searchType.startsWith ("atp."))
  {
   return "ATP";
  }
  if (searchType.startsWith ("person."))
  {
   return "Person";
  }
  throw new IllegalArgumentException ("Unknown type of search: " + searchType);
 }

 private List<LookupParamMetadata> filterConfiguredParams (
   List<LookupParamMetadata> params)
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
  for (LookupParamMetadata param : params)
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

 private String calcRepeating (Metadata fieldMeta)
 {
  if ( ! fieldMeta.getDataType ().equals (Data.DataType.LIST))
  {
   return " ";
  }
//  return "repeating";
  MetadataInterrogator mi = new MetadataInterrogator (fieldMeta);
  if (mi.getSmallestMaxOccurs () == null)
  {
   if (mi.getLargestMinOccurs () != null && mi.getLargestMinOccurs () > 1)
   {
    return "repeating: minimum " + mi.getLargestMinOccurs () + " times";
   }
   return "repeating: unlimited";
  }
  if (mi.getSmallestMaxOccurs () == 0)
  {
   return "NOT USED";
  }
  if (mi.getSmallestMaxOccurs () == 1)
  {
   return " ";
//   return "single";
  }

  if (mi.getLargestMinOccurs () != null)
  {
   if (mi.getLargestMinOccurs () > 1)
   {
    return "repeating: " + mi.getLargestMinOccurs () + " to " + mi.getSmallestMaxOccurs ()
           + " times";
   }
  }
  return "repeating: maximum " + mi.getSmallestMaxOccurs () + " times";
 }

 private String calcLength (Metadata fieldMeta)
 {
  MetadataInterrogator mi = new MetadataInterrogator (fieldMeta);
  if (mi.getSmallestMaxLength () != null)
  {
   if (mi.getLargestMinLength () != null && mi.getLargestMinLength () != 0)
   {
    if (mi.getSmallestMaxLength () == mi.getLargestMinLength ())
    {
     return ("(must be " + mi.getSmallestMaxLength () + ")");
    }
    return "(" + mi.getLargestMinLength () + " to " + mi.getSmallestMaxLength () + ")";
   }
   return "(up to " + mi.getSmallestMaxLength () + ")";
  }
  if (mi.getLargestMinLength () != null)
  {
   return "(over " + mi.getLargestMinLength () + ")";
  }
  return " ";
 }

 private String calcCrossField (Metadata fieldMeta)
 {
  StringBuilder b = new StringBuilder ();
  String semicolon = "";
  String cfr = calcCrossFieldRequire (fieldMeta);
  if (cfr != null)
  {
   b.append (semicolon);
   semicolon = "; ";
   b.append (cfr);
  }
  String cfw = calcCrossFieldWhen (fieldMeta);
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

 private String calcCrossFieldRequire (Metadata fieldMeta)
 {
  return " ";
 }

 private String calcCrossFieldWhen (Metadata fieldMeta)
 {
  return null;
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
