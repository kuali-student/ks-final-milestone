package org.kuali.student.common.dictionary.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kuali.student.common.dictionary.dto.CaseConstraint;
import org.kuali.student.common.dictionary.dto.CommonLookupParam;
import org.kuali.student.common.dictionary.dto.Constraint;
import org.kuali.student.common.dictionary.dto.DataType;
import org.kuali.student.common.dictionary.dto.FieldDefinition;
import org.kuali.student.common.dictionary.dto.LookupConstraint;
import org.kuali.student.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.common.dictionary.dto.RequiredConstraint;
import org.kuali.student.common.dictionary.dto.ValidCharsConstraint;
import org.kuali.student.common.dictionary.dto.WhenConstraint;

public class DictionaryFormatter
{

 private StringBuilder builder = new StringBuilder (5000);
 private ObjectStructureDefinition os;
 private String rowSeperator = "\n";
 private String colSeperator = "|";
 private String name;
 private String className;
 private boolean processSubstructures = false;
 private int level;
 private Map<String, ObjectStructureDefinition> subStructuresToProcess =
                                                new LinkedHashMap ();
 private Set<ObjectStructureDefinition> subStructuresAlreadyProcessed;

 public DictionaryFormatter (String name,
                             String className,
                             ObjectStructureDefinition os,
                             Set<ObjectStructureDefinition> subStructuresAlreadyProcessed,
                             int level,
                             boolean processSubstructures)
 {
  this.name = name;
  this.className = className;
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
  if (className != null)
  {
   builder.append ("The corresponding java class for this dictionary object is "
                   + os.getName ());
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
  builder.append ("Dynamic or Hidden");
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
  for (FieldDefinition fd : getSortedFields ())
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
   builder.append (pad (calcDynamicOrHidden (fd), 7));
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
  List<String> discrepancies = null;
  if (className == null)
  {
   discrepancies = new ArrayList (1);
   discrepancies.add (
     "There is no corresponding java class for this dictionary object structure");
  }
  else
  {
   discrepancies = new Dictionary2BeanComparer (className, os).compare ();
  }
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
  Set<ObjectStructureDefinition> subStructuresAlreadyProcessedBeforeProcessingSubStructures =
                                 new HashSet ();
  subStructuresAlreadyProcessedBeforeProcessingSubStructures.addAll (
    subStructuresAlreadyProcessed);
  for (String subName : this.subStructuresToProcess.keySet ())
  {
   ObjectStructureDefinition subOs = this.subStructuresToProcess.get (subName);
   if ( ! subStructuresAlreadyProcessedBeforeProcessingSubStructures.contains (
     subOs))
   {
    this.subStructuresAlreadyProcessed.add (subOs);
//    System.out.println ("formatting substructure " + subName);
    Class<?> subClazz = getClass (subOs.getName ());
    DictionaryFormatter formatter =
                        new DictionaryFormatter (subName, subOs.getName (),
                                                 subOs,
                                                 subStructuresAlreadyProcessed,
                                                 level + 1,
                                                 this.processSubstructures);
    builder.append (formatter.formatForWiki ());
    builder.append (rowSeperator);
   }
  }

  return builder.toString ();
 }



 private List<FieldDefinition> getSortedFields ()
 {
   List<FieldDefinition> fields = os.getAttributes ();
   Collections.sort (fields, new FieldDefinitionNameComparator ());
   return fields;
 }

 private static class FieldDefinitionNameComparator implements Comparator <FieldDefinition>
 {
  @Override
  public int compare (FieldDefinition o1, FieldDefinition o2)
  {
   return o1.getName ().toLowerCase ().compareTo (o2.getName ().toLowerCase ());
  }

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
   Class subClazz = this.getClass (fd.getDataObjectStructure ().getName ());
   String subStrucName = calcComplexSubStructureName (fd);
   // process if explicity asking for substructures OR the field is a freestanding field
   // so it won't be processed by just processing all of the DTO's and their sub-objects
   if (this.processSubstructures || subClazz == null)
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


 private String calcDynamicOrHidden (FieldDefinition fd)
 {
  if (fd.isHide ())
  {
   if (fd.isDynamic ())
   {
    return "dynamic and hidden";
   }
   return "hidden";
  }
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
     return "Not allowed";
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
  return calcValidChars (fd.getValidChars ());
 }

 private String calcValidChars (ValidCharsConstraint cons)
 {
  String labelKey = cons.getLabelKey ();
  if (labelKey == null)
  {
   labelKey = "validation.validChars";
  }
  String validChars = escapeWiki (cons.getValue ());
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
    case '{':
    case '}':
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
  return calcLookup (fd.getLookupDefinition ());
 }

 private String calcLookup (LookupConstraint lc)
 {
  StringBuilder bldr = new StringBuilder ();
  bldr.append (lc.getId ());
//  this is the search description not the lookup description
//  builder.append (" - ");
//  builder.append (lc.getDesc ());
  String and = "";
  bldr.append ("\\\\");
  bldr.append ("\n");
  bldr.append ("Implemented using search: ");
  String searchPage = calcWikiSearchPage (lc.getSearchTypeId ());
  bldr.append ("[" + lc.getSearchTypeId () + "|" + searchPage + "#"
                  + lc.getSearchTypeId () + "]");
  List<CommonLookupParam> configuredParameters = filterConfiguredParams (
    lc.getParams ());
  if (configuredParameters.size () > 0)
  {
   bldr.append ("\\\\");
   bldr.append ("\n");
   bldr.append (" where ");
   and = "";
   for (CommonLookupParam param : configuredParameters)
   {
    bldr.append (and);
    and = " and ";
    bldr.append (param.getName ());
    bldr.append ("=");
    if (param.getDefaultValueString () != null)
    {
     bldr.append (param.getDefaultValueString ());
     continue;
    }
    if (param.getDefaultValueList () != null)
    {
     String comma = "";
     for (String defValue : param.getDefaultValueList ())
     {
      bldr.append (comma);
      comma = ", ";
      bldr.append (defValue);
     }
    }
   }
  }
  return bldr.toString ();
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
  String minOccursMessage = calcOverride ("minOccurs", fd.getMinOccurs (),
                                          cons.getMinOccurs ());
  if ( ! minOccursMessage.trim ().equals (""))
  {
   if (cons.getMinOccurs () != null && cons.getMinOccurs () == 1)
   {
    minOccursMessage = " REQUIRED";
   }
  }
  b.append (minOccursMessage);
  b.append (calcOverride ("validchars", fd.getValidChars (),
                          cons.getValidChars ()));
  b.append (calcOverride ("lookup", fd.getLookupDefinition (),
                          cons.getLookupDefinition ()));
  //TODO: other more complex constraints
  return b.toString ();
 }

 private String calcOverride (String attribute, LookupConstraint val1,
                              LookupConstraint val2)
 {
  if (val1 == val2)
  {
   return "";
  }
  if (val1 == null && val2 != null)
  {
   return " add lookup " + this.calcLookup (val2);
  }
  if (val1 != null && val2 == null)
  {
   return " remove lookup constraint";
  }
  return " change lookup to " + calcLookup (val2);
 }

 private String calcOverride (String attribute, ValidCharsConstraint val1,
                              ValidCharsConstraint val2)
 {
  if (val1 == val2)
  {
   return "";
  }
  if (val1 == null && val2 != null)
  {
   return " add validchars " + calcValidChars (val2);
  }
  if (val1 != null && val2 == null)
  {
   return " remove validchars constraint";
  }
  return " change validchars to " + calcValidChars (val2);
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
   return " " + attribute + "=" + val2;
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
   return " " + attribute + "=" + val2;
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
