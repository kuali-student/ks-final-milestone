package org.kuali.student.core.dictionary.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
 private String rowSeperator = "\n";
 private String colSeperator = "|";
 private String name;
 private Class<?> clazz;
 private boolean processSubstructures = false;
 private int level;
 private Map<String, ObjectStructureDefinition> subStructuresToProcess =
                                                new LinkedHashMap ();
 private Set<ObjectStructureDefinition> subStructuresAlreadyProcessed;

 public DictionaryFormatter (String name,
                             Class<?> clazz,
                             ObjectStructureDefinition os,
                             Set<ObjectStructureDefinition> subStructuresAlreadyProcessed,
                             int level,
                             boolean processSubstructures)
 {
  this.name = name;
  this.clazz = clazz;
  this.os = os;
  this.subStructuresAlreadyProcessed = subStructuresAlreadyProcessed;
  this.level = level;
  this.processSubstructures = processSubstructures;
 }
 public static final String UNBOUNDED = "unbounded";

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
  if (clazz != null)
  {
   builder.append ("The corresponding java class for this dictionary object is " + os.getName ());
  }
  if (os.isHasMetaData ())
  {
   builder.append (rowSeperator);
   builder.append ("The dictionary says this object holds metadata");
  }
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
  for (FieldDefinition fd : os.getAttributes ())
  {
   builder.append (colSeperator);
   builder.append (pad (fd.getName (), 30));
   builder.append (colSeperator);
   builder.append (pad (calcRequired (fd), 10));
   builder.append (colSeperator);
   builder.append (pad (calcDataType (fd), 25));
   builder.append (colSeperator);
   builder.append (pad (calcLength (fd), 15));
   builder.append (colSeperator);
   builder.append (pad (calcDynamic (fd), 7));
   builder.append (colSeperator);
   builder.append (pad (calcDefaultValue (fd), 15));
   builder.append (colSeperator);
   builder.append (calcRepeating (fd));
   builder.append (colSeperator);
   builder.append (calcValidCharsMinMax (fd));
   builder.append (colSeperator);
   builder.append (calcLookup (fd));
   builder.append (colSeperator);
   builder.append (calcCrossField (fd));
   builder.append (colSeperator);
   builder.append (rowSeperator);
  }
  List<String> discrepancies =
               new Dictionary2BeanComparer (clazz, os).compare ();
  if (discrepancies.size () > 0)
  {
   builder.append ("h" + (level + 1) + ". " + discrepancies.size ()
                   + " discrepancie(s) found in "
                   + calcSimpleName (name));
   builder.append (rowSeperator);
   builder.append (formatAsString (discrepancies));
   builder.append (rowSeperator);
  }

//  builder.append ("======= end dump of object structure definition ========");
  builder.append (rowSeperator);
  Set<ObjectStructureDefinition> subStructuresAlreadyProcessedBeforeProcessingSubStructures = new HashSet ();
  subStructuresAlreadyProcessedBeforeProcessingSubStructures.addAll (
    subStructuresAlreadyProcessed);
  for (String subName : this.subStructuresToProcess.keySet ())
  {
   ObjectStructureDefinition subOs = this.subStructuresToProcess.get (subName);
   if ( ! subStructuresAlreadyProcessedBeforeProcessingSubStructures.contains (subOs))
   {
    this.subStructuresAlreadyProcessed.add (subOs);
//    System.out.println ("formatting substructure " + subName);
    Class<?> subClazz = getClass (subOs.getName ());
    DictionaryFormatter formatter =
                        new DictionaryFormatter (subName, subClazz, subOs,
                                                 subStructuresAlreadyProcessed,
                                                 level + 1,
                                                 this.processSubstructures);
    builder.append (formatter.formatForWiki ());
    builder.append (rowSeperator);
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
   return null;
//   throw new IllegalArgumentException ("Could not find class for " + className);
  }
 }

 private String formatAsString (List<String> discrepancies)
 {
  int i = 0;
  StringBuilder builder = new StringBuilder ();
  for (String discrep : discrepancies)
  {
   i ++;
   builder.append (i + ". " + discrep + "\n");
  }
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
   String subStrucName = calcComplexSubStructureName (fd);
   if (this.processSubstructures)
   {
    if ( ! this.subStructuresAlreadyProcessed.contains (
      fd.getDataObjectStructure ()))
    {
//     System.out.println ("Adding " + subStrucName + " to set to be processed");
     this.subStructuresToProcess.put (subStrucName, fd.getDataObjectStructure ());
    }
   }
   return "[" + calcNotSoSimpleName (subStrucName) + "|#" + subStrucName + "]";
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

 private String calcComplexSubStructureName (FieldDefinition fd)
 {
  if (this.processSubstructures)
  {
   return name + "." + fd.getName () + "." + calcSimpleName (
     fd.getDataObjectStructure ().getName ());
  }
  return calcSimpleName (fd.getDataObjectStructure ().getName ());
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
 private static final String LINK_TO_DEFINITIONS =
                             "KULSTG:Formatted View of Base Dictionary#Valid Character Definitions";

 private String calcValidChars (FieldDefinition fd)
 {
  if (fd.getValidChars () == null)
  {
   return " ";
  }
  String labelKey = fd.getValidChars ().getLabelKey ();
  if (labelKey == null)
  {
   labelKey = "validation.validChars";
  }
  String validChars = escapeWiki (fd.getValidChars ().getValue ());
  String descr = "[" + labelKey + "|" + LINK_TO_DEFINITIONS + "]" + "\\\\\n"
                 + validChars;
  return descr;
 }

 private String escapeWiki (String str)
 {
  StringBuilder bldr = new StringBuilder (str.length ());
  for (int i = 0; i < str.length (); i ++)
  {
   char c = str.charAt (i);
   switch (c)
   {
    case '[':
    case ']':
    case '|':
     bldr.append ('\\');
   }
   bldr.append (c);
  }
  return bldr.toString ();
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
  builder.append ("Implemented using search: ");
  String searchPage = calcWikiSearchPage (lc.getSearchTypeId ());
  builder.append ("[" + lc.getSearchTypeId () + "|" + searchPage + "#"
                  + lc.getSearchTypeId () + "]");
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

 private String calcValidCharsMinMax (FieldDefinition fd)
 {
  String validChars = calcValidChars (fd);
  String minMax = calcMinMax (fd);
  String and = " and ";
  if (validChars.trim ().equals (""))
  {
   return minMax;
  }
  if (minMax.trim ().equals (""))
  {
   return validChars;
  }
  return validChars + "\\\\\n" + minMax;
 }

 private String calcMinMax (FieldDefinition fd)
 {
  if (fd.getExclusiveMin () == null)
  {
   if (fd.getInclusiveMax () == null)
   {
    return " ";
   }
   return "Must be <= " + fd.getInclusiveMax ();
  }
  if (fd.getInclusiveMax () == null)
  {
   return "Must be > " + fd.getExclusiveMin ();
  }
  return "Must be > " + fd.getExclusiveMin () + " and < "
         + fd.getInclusiveMax ();
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
  throw new IllegalArgumentException ("Unknown type of search: " + searchType);
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
  b.append (calcOverride ("serviceSide", fd.isServerSide (),
                          cons.isServerSide ()));
  b.append (calcOverride ("exclusiveMin", fd.getExclusiveMin (),
                          cons.getExclusiveMin ()));
  b.append (calcOverride ("inclusiveMax", fd.getInclusiveMax (),
                          cons.getInclusiveMax ()));
  b.append (calcOverride ("minOccurs", fd.getMinOccurs (), cons.getMinOccurs ()));
  b.append (calcOverride ("validchars", fd.getValidChars (),
                          cons.getValidChars ()));
  b.append (calcOverride ("validchars", fd.getLookupDefinition (),
                          cons.getLookupDefinition ()));
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
