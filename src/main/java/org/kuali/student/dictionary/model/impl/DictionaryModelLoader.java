/*
 * Copyright 2009 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.dictionary.model.impl;

import java.text.ParseException;
import org.kuali.student.dictionary.model.*;
import org.kuali.student.dictionary.model.spreadsheet.WorksheetNotFoundException;
import org.kuali.student.dictionary.writer.ClassNameDecorator;
import org.kuali.student.dictionary.model.validation.DictionaryValidationException;
import org.kuali.student.dictionary.model.spreadsheet.SpreadsheetReader;
import org.kuali.student.dictionary.model.spreadsheet.WorksheetReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.kuali.student.dictionary.DictionaryExecutionException;
import org.kuali.student.dictionary.model.util.DateUtility;
import org.kuali.student.dictionary.model.util.ModelFinder;

/**
 * Loads a spreadsheet using either a google or excel reader
 * @author nwright
 */
public class DictionaryModelLoader implements DictionaryModel
{

 private SpreadsheetReader reader;

 public DictionaryModelLoader (SpreadsheetReader reader)
 {
  this.reader = reader;
 }

 @Override
 public List<Dictionary> getDictionary ()
 {
  WorksheetReader worksheetReader;
  try
  {
   worksheetReader = reader.getWorksheetReader ("Dictionary");
  }
  catch (WorksheetNotFoundException ex)
  {
   throw new DictionaryExecutionException (ex);
  }
  List<Dictionary> list = new ArrayList (worksheetReader.getEstimatedRows ());
  Stack<Dictionary> parents = new Stack ();
  Stack<String> xmlObjects = new Stack ();
  Dictionary lastDict = null;
  ModelFinder finder = new ModelFinder (this);
  while (worksheetReader.next ())
  {
   Dictionary dict = this.loadDictionaryObject (worksheetReader);
   if (dict != null)
   {
    dict.setParent (null);
    list.add (dict);
    String xmlObject = dict.getXmlObject ().toLowerCase ();
    XmlType xmlType = finder.findXmlType (xmlObject);
    if (xmlType == null)
    {
     throw new DictionaryExecutionException ("Dictionary entry, " + dict.getId () + " has no entry in XmlTypes");
    }
    if (xmlType.hasOwnCreateUpdate ())
    {
     lastDict = dict;
     parents.push (lastDict);
     xmlObjects.push (lastDict.getXmlObject ().toLowerCase ());
     System.out.println (dict.getId () + " " + xmlObject + " first row");
     continue;
    }
     //  check if the object changed
    if (xmlObject.equals (lastDict.getXmlObject ().toLowerCase ()))
    {
     if ( ! parents.empty ())
     {
      dict.setParent (parents.peek ());
     }
     System.out.println (dict.getId () + " " + xmlObject
      + " no change in object -- keep the same parent");
     lastDict = dict;
     continue;
    }
    // if going down the hierarchy
    if (xmlObjects.search (xmlObject) == -1)
    {
     parents.push (lastDict);
     xmlObjects.push (lastDict.getXmlObject ().toLowerCase ());
     dict.setParent (parents.peek ());
     System.out.println (dict.getId () + " " + xmlObject
      + " new object is not on stack so must be going down hierarchy " + xmlObjects.
      peek ());
     lastDict = dict;
     continue;
    }
    // if coming back up the stack pop until you are back to the object
    while (xmlObjects.search (xmlObject) != -1)
    {
     System.out.println (dict.getId () + " " + xmlObject
      + " not on stack - so popping parents off the stack - "
      + xmlObjects.peek ());
     parents.pop ();
     xmlObjects.pop ();
    }
    if (parents.empty ())
    {
     dict.setParent (null);
     System.out.println (dict.getId () + " " + xmlObject
      + " popped everything off the stack");
     lastDict = dict;
     continue;
    }
    dict.setParent (parents.peek ());
    lastDict = dict;
    continue;
   }
  }
  return list;
 }

 private Dictionary loadDictionaryObject (WorksheetReader worksheetReader)
 {
  Dictionary dict = new Dictionary ();
  dict.setId (getFixup (worksheetReader, "id"));
  if (dict.getId ().equals (""))
  {
   return null;
  }
  dict.setName (getFixup (worksheetReader, "name"));
  dict.setDesc (getFixup (worksheetReader, "desc"));
  if (dict.getDesc ().equals ("ignore this row"))
  {
   return null;
  }
  dict.setType (getFixup (worksheetReader, "type"));
  dict.setState (getFixup (worksheetReader, "state"));
  dict.setSubType (getFixup (worksheetReader, "subType"));
  dict.setSubState (getFixup (worksheetReader, "subState"));
  dict.setXmlObject (getFixup (worksheetReader, "xmlObject"));
  dict.setShortName (getFixup (worksheetReader, "shortName"));
  dict.setPrimitive (getFixup (worksheetReader, "primitive"));
  dict.setBaseConstraintDescription (getFixup (worksheetReader, "baseConstraintDescription"));
  dict.setSelector (getFixup (worksheetReader, "selector"));

  // do additional constriants
  List<String> additionalConstraintIds = new ArrayList (4);
  for (int i = 1; i <= 4; i ++)
  {
   String additionalConstraintId =
    getFixup (worksheetReader, "additionalConstraintId" + i);
   if ( ! additionalConstraintId.equals (""))
   {
    additionalConstraintIds.add (additionalConstraintId);
   }
  }
  dict.setAdditionalConstraintIds (additionalConstraintIds);
  dict.setAdditionalConstraintDescription (getFixup (worksheetReader, "additionalConstraintDescription"));

  // do in-line constraint
  Constraint inline = new Constraint ();
  inline.setInline (true);
  inline.setId ("");
  inline.setKey ("in-line.constraint.for.dictionary." + dict.getId ());
  inline.setMessageId (getFixup (worksheetReader, "messageId"));
  inline.setMinLength (getFixup (worksheetReader, "minLength"));
  inline.setMaxLength (getFixup (worksheetReader, "maxLength"));
  if (inline.getMaxLength ().equals (Constraint.NINE_NINES))
  {
   inline.setMaxLength (Constraint.UNBOUNDED);
  }
  inline.setMinValue (getFixup (worksheetReader, "minValue"));
  inline.setMaxValue (getFixup (worksheetReader, "maxValue"));
  inline.setMinOccurs (getFixup (worksheetReader, "minOccurs"));
  inline.setMaxOccurs (getFixup (worksheetReader, "maxOccurs"));
  if (inline.getMaxOccurs ().equals (Constraint.NINE_NINES))
  {
   inline.setMaxOccurs (Constraint.UNBOUNDED);
  }
  inline.setValidChars (getFixup (worksheetReader, "validChars"));
  if ( ! isValidChars (inline.getValidChars ()))
  {
   throw new DictionaryValidationException ("Field " + dict.getId ()
    + " contains an invalid regular expression " + inline.getValidChars ());
  }
  inline.setLookup (getFixup (worksheetReader, "lookup"));
  dict.setInlineConstraint (inline);

  // finish
  dict.setComments (getFixup (worksheetReader, "comments"));

  return dict;
 }

 @Override
 public List<State> getStates ()
 {
  WorksheetReader worksheetReader;
  try
  {
   worksheetReader = reader.getWorksheetReader ("States");
  }
  catch (WorksheetNotFoundException ex)
  {
   throw new DictionaryExecutionException (ex);
  }
  List<State> list = new ArrayList (worksheetReader.getEstimatedRows ());
  while (worksheetReader.next ())
  {
   State state = new State ();
   state.setName (get (worksheetReader, "name"));
   if (state.getName ().equals (""))
   {
    continue;
   }
   state.setDesc (getFixup (worksheetReader, "desc"));


   if (state.getDesc ().equals ("ignore this row"))
   {
    continue;
   }
   list.add (state);
   state.setXmlObject (getFixup (worksheetReader, "xmlObject"));
   state.setXmlObjectDesc (getFixup (worksheetReader, "xmlObjectDesc"));
   state.setStateKey (getFixup (worksheetReader, "stateKey"));
   state.setEffectiveDate (getFixupDate (worksheetReader, "effective"));
   state.setExpirationDate (getFixupDate (worksheetReader, "expiration"));
   state.setInclude (getFixupBoolean (worksheetReader, "include"));
   state.setStatus (getFixup (worksheetReader, "status"));
   state.setComments (getFixup (worksheetReader, "comments"));

  }
  return list;
 }

 private String get (WorksheetReader worksheetReader, String colName)
 {
  String value = worksheetReader.getValue (colName);
  if (value == null)
  {
   return "";
  }
  value = value.trim ();
  return value;
 }

 private String getFixup (WorksheetReader worksheetReader, String colName)
 {
  return fixup (get (worksheetReader, colName));
 }

 private String fixup (String str)
 {
  if (str == null)
  {
   return "";
  }

  // TODO: figure out why I am getting an unprintable character that gets translated to a 63 which is a ?
  //       when there is a calculation that returns null
  if (str.length () == 1)
  {
   byte[] bytes = str.getBytes ();
   if (bytes[0] == 63)
   {
    return "";
   }
  }
  if (str.equalsIgnoreCase ("FALSE"))
  {
   return "false";
  }
  if (str.equalsIgnoreCase ("TRUE"))
  {
   return "true";
  }
  return str;
 }

 private boolean getFixupBoolean (WorksheetReader reader, String name)
 {
  String str = getFixup (reader, name);
  if (str == null)
  {
   return false;
  }
  if (str.equals (""))
  {
   return false;
  }
  if (str.equalsIgnoreCase ("true"))
  {
   return true;
  }
  if (str.equalsIgnoreCase ("false"))
  {
   return false;
  }
  throw new DictionaryExecutionException ("Could not parse " + name
   + " as a boolean");
 }

 @Override
 public List<Type> getTypes ()
 {
  WorksheetReader worksheetReader;
  try
  {
   worksheetReader = reader.getWorksheetReader ("Types");
  }
  catch (WorksheetNotFoundException ex)
  {
   throw new DictionaryExecutionException (ex);
  }
  List<Type> list = new ArrayList (worksheetReader.getEstimatedRows ());
  while (worksheetReader.next ())
  {
   Type type = new Type ();
   type.setName (get (worksheetReader, "name"));
   if (type.getName ().equals (""))
   {
    continue;
   }
   type.setDesc (getFixup (worksheetReader, "desc"));
   if (type.getDesc ().equals ("ignore this row"))
   {
    continue;
   }
   list.add (type);
   type.setXmlObject (getFixup (worksheetReader, "xmlObject"));
   type.setPrimitive (getFixup (worksheetReader, "primitive"));
   type.setInclude (getFixupBoolean (worksheetReader, "include"));
   type.setTypeKey (get (worksheetReader, "typeKey"));
   type.setEffectiveDate (getFixupDate (worksheetReader, "effective"));
   type.setExpirationDate (getFixupDate (worksheetReader, "expiration"));
   type.setAliases (getFixup (worksheetReader, "aliases"));
   type.setStatus (getFixup (worksheetReader, "status"));
   type.setComments (getFixup (worksheetReader, "comments"));
  }
  return list;
 }

 private String getFixupDate (WorksheetReader reader, String name)
 {
  String dateStr = getFixup (reader, name);
  if (dateStr == null)
  {
   return "";
  }
  if (dateStr.equals (""))
  {
   return "";
  }
  try
  {
   return new DateUtility ().asYMD (dateStr);
  }
  catch (ParseException ex)
  {
   throw new DictionaryExecutionException ("Could not parse " + name
    + " as a date");
  }
 }

 @Override
 public List<Field> getFields ()
 {
  WorksheetReader worksheetReader;
  try
  {
   worksheetReader = reader.getWorksheetReader ("MessageStructure");
  }
  catch (WorksheetNotFoundException ex)
  {
   throw new DictionaryExecutionException (ex);
  }
  List<Field> list = new ArrayList (worksheetReader.getEstimatedRows ());
  while (worksheetReader.next ())
  {
   Field field = new Field ();
   field.setId (getFixup (worksheetReader, "id"));
   if (field.getId ().equals (""))
   {
    continue;
   }
   field.setXmlObject (getFixup (worksheetReader, "xmlObject"));
   field.setShortName (getFixup (worksheetReader, "shortName"));
   field.setName (getFixup (worksheetReader, "name"));
   field.setXmlType (getFixup (worksheetReader, "xmlType"));
   field.setPrimitive (getFixup (worksheetReader, "primitive"));
   // do additional constriants
   List<String> constraintIds = new ArrayList (4);
   for (int i = 1; i <= 4; i ++)
   {
    String constraintId = getFixup (worksheetReader, "constraintId" + i);
    if ( ! constraintId.equals (""))
    {
     constraintIds.add (constraintId);
    }
   }
   field.setConstraintIds (constraintIds);
   field.setConstraintDescription (getFixup (worksheetReader, "constraintDescription"));
   field.setDynamic (getFixup (worksheetReader, "dynamic"));
   field.setSelector (getFixup (worksheetReader, "selector"));

   // do in-line constraint
   Constraint inline = new Constraint ();
   inline.setInline (true);
   inline.setId ("");
   inline.setKey ("in-line.constraint.for.field." + field.getId ());
   inline.setMessageId (getFixup (worksheetReader, "messageId"));
   inline.setMinLength (getFixup (worksheetReader, "minLength"));
   inline.setMaxLength (getFixup (worksheetReader, "maxLength"));
   if (inline.getMaxLength ().equals (Constraint.NINE_NINES))
   {
    inline.setMaxLength (Constraint.UNBOUNDED);
   }
   inline.setMinValue (getFixup (worksheetReader, "minValue"));
   inline.setMaxValue (getFixup (worksheetReader, "maxValue"));
   inline.setMinOccurs (getFixup (worksheetReader, "minOccurs"));
   inline.setMaxOccurs (getFixup (worksheetReader, "maxOccurs"));
   if (inline.getMaxOccurs ().equals (Constraint.NINE_NINES))
   {
    inline.setMaxOccurs (Constraint.UNBOUNDED);
   }
   inline.setValidChars (getFixup (worksheetReader, "validChars"));
   if ( ! isValidChars (inline.getValidChars ()))
   {
    throw new DictionaryValidationException ("Field " + field.getId ()
     + " contains an invalid regular expression " + inline.getValidChars ());
   }
   inline.setLookup (getFixup (worksheetReader, "lookup"));
   field.setInlineConstraint (inline);
   // finish up
   field.setDesc (getFixup (worksheetReader, "description"));
   field.setComments (getFixup (worksheetReader, "comments"));
   if ( ! field.getDesc ().equals ("ignore this row"))
   {
    list.add (field);
   }
  }
  return list;
 }

 @Override
 public List<Constraint> getConstraints ()
 {
  WorksheetReader worksheetReader;
  try
  {
   worksheetReader = reader.getWorksheetReader ("Constraints");
  }
  catch (WorksheetNotFoundException ex)
  {
   throw new DictionaryExecutionException (ex);
  }
  List<Constraint> list = new ArrayList (worksheetReader.getEstimatedRows ());
  while (worksheetReader.next ())
  {
   Constraint constraint = new Constraint ();
   constraint.setId (getFixup (worksheetReader, "id"));
   if (constraint.getId ().equals (""))
   {
    continue;
   }
   constraint.setKey (constraint.getId ());
   constraint.setDesc (getFixup (worksheetReader, "desc"));
   constraint.setServerSide (getFixup (worksheetReader, "serverSide"));
   constraint.setMessageId (getFixup (worksheetReader, "messageId"));
   constraint.setMinLength (getFixup (worksheetReader, "minLength"));
   constraint.setMaxLength (getFixup (worksheetReader, "maxLength"));
   if (constraint.getMaxLength ().equals (Constraint.NINE_NINES))
   {
    constraint.setMaxLength (Constraint.UNBOUNDED);
   }
   constraint.setMinValue (getFixup (worksheetReader, "minValue"));
   constraint.setMaxValue (getFixup (worksheetReader, "maxValue"));
   constraint.setMinOccurs (getFixup (worksheetReader, "minOccurs"));
   constraint.setMaxOccurs (getFixup (worksheetReader, "maxOccurs"));
   if (constraint.getMaxOccurs ().equals (Constraint.NINE_NINES))
   {
    constraint.setMaxOccurs (Constraint.UNBOUNDED);
   }
   constraint.setValidChars (getFixup (worksheetReader, "validChars"));
   if ( ! isValidChars (constraint.getValidChars ()))
   {
    throw new DictionaryValidationException ("Constraint " + constraint.getId ()
     + " contains an invalid regular expression " + constraint.getValidChars ());
   }
   constraint.setClassName (decorateClassName (getFixup (worksheetReader, "className")));
   constraint.setLookup (getFixup (worksheetReader, "lookup"));
   constraint.setComments (getFixup (worksheetReader, "comments"));
   if ( ! constraint.getDesc ().equals ("ignore this row"))
   {
    list.add (constraint);
   }
  }
  return list;
 }

 protected String decorateClassName (String className)
 {
  return new ClassNameDecorator (className).decorate ();
 }

 @Override
 public List<CrossObjectConstraint> getCrossObjectConstraints ()
 {
  WorksheetReader worksheetReader;
  try
  {
   worksheetReader = reader.getWorksheetReader ("CrossObjectConstraints");
  }
  catch (WorksheetNotFoundException ex)
  {
   throw new DictionaryExecutionException (ex);
  }
  List<CrossObjectConstraint> list = new ArrayList (worksheetReader.
   getEstimatedRows ());
  while (worksheetReader.next ())
  {
   CrossObjectConstraint coc = new CrossObjectConstraint ();
   coc.setId (getFixup (worksheetReader, "id"));
   if (coc.getId ().equals (""))
   {
    continue;
   }
   coc.setImplementation (getFixup (worksheetReader, "implementation"));
   coc.setDictionaryId (getFixup (worksheetReader, "dictionaryId"));
   coc.setObject1 (getFixup (worksheetReader, "object1"));
   coc.setType1 (getFixup (worksheetReader, "type1"));
   coc.setState1 (getFixup (worksheetReader, "state1"));
   coc.setRelationType (getFixup (worksheetReader, "relationType"));
   coc.setCardinalityType (getFixup (worksheetReader, "cardinalityType"));

   coc.setObject2 (getFixup (worksheetReader, "object2"));
   coc.setType2 (getFixup (worksheetReader, "type2"));
   coc.setState2 (getFixup (worksheetReader, "state2"));
   coc.setDesc (getFixup (worksheetReader, "desc"));
   if ( ! coc.getDesc ().equals ("ignore this row"))
   {
    list.add (coc);
   }
   coc.setComments (getFixup (worksheetReader, "comments"));
   coc.setMinOccurs (getFixup (worksheetReader, "minOccurs"));
   coc.setMaxOccurs (getFixup (worksheetReader, "maxOccurs"));
   if (coc.getMaxOccurs ().equals (Constraint.NINE_NINES))
   {
    coc.setMaxOccurs (Constraint.UNBOUNDED);
   }
  }
  return list;
 }

 private boolean isValidChars (String validChars)
 {
  if (validChars.equals (""))
  {
   return true;
  }
  try
  {
   Pattern.compile (validChars);
   return true;
  }
  catch (PatternSyntaxException ex)
  {
   return false;
  }
 }

 @Override
 public List<String> getSourceNames ()
 {
  List<String> list = new ArrayList ();
  list.add (reader.getSourceName ());
  if (reader != null)
  {
   list.add (reader.getSourceName ());
  }
  return list;
 }

 @Override
 public List<OrchObj> getOrchObjs ()
 {
  if (reader == null)
  {
   return new ArrayList (0);
  }
  WorksheetReader worksheetReader;
  try
  {
   worksheetReader = reader.getWorksheetReader ("OrchObj");
  }
  catch (WorksheetNotFoundException ex)
  {
   throw new DictionaryExecutionException (ex);
  }
  List<OrchObj> list = new ArrayList (worksheetReader.getEstimatedRows ());

  while (worksheetReader.next ())
  {
   OrchObj orchObj = new OrchObj ();
   orchObj.setDesc (getFixup (worksheetReader, "desc"));
   if (orchObj.getDesc ().equals ("ignore this row"))
   {
    continue;
   }
   list.add (orchObj);
   orchObj.setParent (getFixup (worksheetReader, "Parent"));
   orchObj.setCard1 (getFixup (worksheetReader, "Card1"));
   orchObj.setChild (getFixup (worksheetReader, "child"));
   orchObj.setCard2 (getFixup (worksheetReader, "Card2"));
   orchObj.setGrandChild (getFixup (worksheetReader, "grandChild"));
   orchObj.setXmlType (getFixup (worksheetReader, "xmlType"));
   orchObj.setRecursions (getFixup (worksheetReader, "recursions"));
   orchObj.setStatus (getFixup (worksheetReader, "status"));
   orchObj.setDefaultValue (getFixup (worksheetReader, "defaultValue"));
   orchObj.setDefaultValuePath (getFixup (worksheetReader, "defaultValuePath"));
   orchObj.setLookup (getFixup (worksheetReader, "lookup"));
   orchObj.setLookupContextPath (getFixup (worksheetReader, "lookupContextPath"));
   orchObj.setWriteAccess (getFixup (worksheetReader, "writeAccess"));
   orchObj.setDictionaryId (getFixup (worksheetReader, "dictionaryId"));
   orchObj.setMessageStructureKey (getFixup (worksheetReader, "messageStructureKey"));
   if (orchObj.getDictionaryId ().equalsIgnoreCase ("TBD"))
   {
    orchObj.setDictionaryId ("");
   }
   orchObj.setSelector (getFixup (worksheetReader, "selector"));
   // do additional constriants
   List<String> constraintIds = new ArrayList (4);
   for (int i = 1; i <= 4; i ++)
   {
    String constraintId = getFixup (worksheetReader, "constraintId" + i);
    if ( ! constraintId.equals (""))
    {
     constraintIds.add (constraintId);
    }
   }
   orchObj.setConstraintIds (constraintIds);
   // do in-line constraint
   Constraint inline = new Constraint ();
   inline.setInline (true);
   inline.setMinLength (getFixup (worksheetReader, "minLength"));
   inline.setMaxLength (getFixup (worksheetReader, "maxLength"));
   if (inline.getMaxLength ().equals (Constraint.NINE_NINES))
   {
    inline.setMaxLength (Constraint.UNBOUNDED);
   }
   inline.setMinValue (getFixup (worksheetReader, "minValue"));
   inline.setMaxValue (getFixup (worksheetReader, "maxValue"));
   inline.setMinOccurs (getFixup (worksheetReader, "minOccurs"));
   inline.setMaxOccurs (getFixup (worksheetReader, "maxOccurs"));
   if (inline.getMaxOccurs ().equals (Constraint.NINE_NINES))
   {
    inline.setMaxOccurs (Constraint.UNBOUNDED);
   }
   inline.setValidChars (getFixup (worksheetReader, "validChars"));
   if ( ! isValidChars (inline.getValidChars ()))
   {
    throw new DictionaryValidationException ("Field " + orchObj.getId ()
     + " contains an invalid regular expression " + inline.getValidChars ());
   }
   // removed from constraint because lookup is now it's own thing
   // and is on the orchObj directly
   // it is not just a search but a fully configured search
   //inline.setLookup (getFixup (worksheetReader, "lookup"));
   orchObj.setInlineConstraint (inline);
   orchObj.setComments (getFixup (worksheetReader, "comments"));
  }
  // set the id of this thing
  String parent = null;
  String child = null;
  for (OrchObj orch : list)
  {
   if ( ! orch.getParent ().equals (""))
   {
    orch.setId (orch.getParent ());
    parent = orch.getParent ();
    child = null;
    continue;
   }
   if ( ! orch.getChild ().equals (""))
   {
    orch.setId (parent + "." + orch.getChild ());
    child = orch.getChild ();
    continue;
   }
   orch.setId (parent + "." + child + "." + orch.getGrandChild ());
  }
  return list;
 }

 private transient ServiceContractModel serviceContractModel = null;

 private ServiceContractModel getServiceContractModel ()
 {
  if (serviceContractModel == null)
  {
   serviceContractModel =
    new ServiceContractModelCache (new ServiceContractModelLoader (reader));
  }
  return serviceContractModel;
 }

 @Override
 public List<ServiceMethod> getServiceMethods ()
 {
  return this.getServiceContractModel ().getServiceMethods ();
 }

 @Override
 public List<XmlType> getXmlTypes ()
 {
  return getServiceContractModel ().getXmlTypes ();
 }

 @Override
 public List<MessageStructure> getMessageStructures ()
 {
  return getServiceContractModel ().getMessageStructures ();
 }

 @Override
 public List<Service> getServices ()
 {
  return getServiceContractModel ().getServices ();
 }

 @Override
 public List<Project> getProjects ()
 {
  WorksheetReader worksheetReader;
  try
  {
   worksheetReader = reader.getWorksheetReader ("Projects");
  }
  catch (WorksheetNotFoundException ex)
  {
   throw new DictionaryExecutionException (ex);
  }
  List<Project> list =
   new ArrayList (worksheetReader.getEstimatedRows ());
  while (worksheetReader.next ())
  {
   Project proj = new Project ();
   proj.setKey (getFixup (worksheetReader, "key"));
   if (proj.getKey ().equals (""))
   {
    continue;
   }
   proj.setStatus (getFixup (worksheetReader, "status"));
   if (proj.getStatus ().equals ("ignore"))
   {
    continue;
   }
   proj.setType (getFixup (worksheetReader, "type"));
   proj.setName (getFixup (worksheetReader, "name"));
   proj.setDescription (getFixup (worksheetReader, "description"));
   proj.setDirectory (getFixup (worksheetReader, "directory"));
   proj.setJavaDirectory (getFixup (worksheetReader, "javaDirectory"));
   proj.setResourcesDirectory (getFixup (worksheetReader, "resourcesDirectory"));
   proj.setComments (getFixup (worksheetReader, "comments"));
   list.add (proj);
  }
  return list;


 }

 @Override
 public List<SearchType> getSearchTypes ()
 {
  SearchModelLoader loader = new SearchModelLoader (reader);
  return loader.getSearchTypes ();
 }

}
