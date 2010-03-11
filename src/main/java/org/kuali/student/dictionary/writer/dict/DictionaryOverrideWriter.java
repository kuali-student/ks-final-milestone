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
package org.kuali.student.dictionary.writer.dict;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.kuali.student.dictionary.DictionaryExecutionException;
import org.kuali.student.dictionary.model.util.ModelFinder;
import org.kuali.student.dictionary.model.XmlType;
import org.kuali.student.dictionary.model.DictionaryModel;
import org.kuali.student.dictionary.model.Type;
import org.kuali.student.dictionary.model.State;
import org.kuali.student.dictionary.model.Dictionary;
import org.kuali.student.dictionary.model.Field;
import org.kuali.student.dictionary.model.util.DateUtility;
import org.kuali.student.dictionary.writer.XmlTypeNameBuilder;
import org.kuali.student.dictionary.writer.XmlWriter;

/**
 * Writes an object structure entity in the output XML document, either the in-line or root ones.
 * @author nwright
 */
public class DictionaryOverrideWriter
{

 private DictionaryModel model;
 private ModelFinder finder;
 private XmlType xmlType;
 private XmlWriter writer;
 private DictionaryOverrideFieldWriter parentField;
 private Set<String> dictionaryEntriesWritten;

 /**
  * construct writer for a particular type
  * @param model holds the entire spreadsheet information
  * @param xmlType data structure to write out
  * @param dictionary list of entries to write out
  * @param pwrent field if this is a sub-object
  */
 public DictionaryOverrideWriter (XmlWriter writer,
                                  DictionaryModel model,
                                  XmlType xmlType,
                                  DictionaryOverrideFieldWriter parentField,
                                  Set<String> dictionaryEntriesWritten)
 {
  this.writer = writer;
  this.model = model;
  this.finder = new ModelFinder (model);
  this.xmlType = xmlType;
  this.parentField = parentField;
  this.dictionaryEntriesWritten = dictionaryEntriesWritten;
 }

 protected DictionaryOverrideFieldWriter getParentField ()
 {
  return parentField;
 }

 protected XmlType getXmlType ()
 {
  return xmlType;
 }

 /**
  * Write out the entire file
  * @param out
  */
 public void write ()
 {
  writeObjectStructure ();
  writeTypeStructures ();
  writeStateStructures ();
  writeSubStructureObjects ();
  writeDictionaryEntries ();
 }

 protected void writeRef ()
 {
  writeObjectStructureRef ();
 }

 protected String calcObjectId ()
 {
  if (parentField != null)
  {
   return "object." + parentField.calcFieldId ();
  }
  return "object." + xmlType.getName ();
 }

 private String calcTypeId (Type type)
 {
  return "type." + calcObjectId ().substring ("object.".length ()) + "." + type.
   getName ();
 }

 private String calcStateId (Type type, State state)
 {
  return "state." + calcTypeId (type).substring ("type.".length ()) + "." + state.
   getName ();
 }

 protected String calcFieldId (Field field, Type type, State state)
 {
  String id = "field." + field.getId ();
  if (parentField != null)
  {
   id = parentField.calcFieldId () + "." + field.getShortName ();
  }
  id = id + "." + type.getName () + "." + state.getName ();
  return id;
 }

 protected String calcDictionaryFieldId (Dictionary dict)
 {
  return "dict." + dict.getId ();
 }

 private void writeObjectStructure ()
 {
  writer.indentPrintln ("");
  String key = new XmlTypeNameBuilder (xmlType.getName (), xmlType.
   getJavaPackage ()).build ();
  String id = calcObjectId ();
  writer.writeCommentBox ("object structure for " + id);

  writer.writeComment (xmlType.getDesc ());
  writer.indentPrint ("<dict:objectStructure");
  writer.writeAttribute ("key", key);
  writeAbstractAttributeId (id);
  writer.println (">");
  if (xmlType.hasOwnType ())
  {
   Set<Type> expanded = getExpandedConfiguredTypes ();
   for (Type type : getIncludedTypes ())
   {
    if ( ! type.getStatus ().equalsIgnoreCase (Type.GROUPING))
    {
     if (expanded.contains (type))
     {
      writeRefBean ("dict:typeRef", calcTypeId (type));
     }
    }
   }
  }
  else
  {
   writeRefBean ("dict:typeRef", calcTypeId (finder.getDefaultType ()));
  }
  writer.indentPrintln ("</dict:objectStructure>");
  // concrete version
  writer.indentPrint ("<dict:objectStructure");
  writer.writeAttribute ("key", key);
  writeAttributeId (id);
  writeParentToAbstract (id);
  writer.println ("/>");
 }

 private Set<Type> expandedConfiguredTypes = null;

 private Set<Type> getExpandedConfiguredTypes ()
 {
  if (expandedConfiguredTypes != null)
  {
   return expandedConfiguredTypes;
  }
  Set<Type> set = new HashSet ();
  for (Type type : getConfiguredTypes ())
  {
   set.add (type);
   if (type.getStatus ().equalsIgnoreCase (Type.GROUPING))
   {
    set.addAll (expandType (type));
   }
  }
  expandedConfiguredTypes = set;
  return expandedConfiguredTypes;
 }

 private Set<Type> expandType (Type type)
 {
  if (type.getName ().equalsIgnoreCase (finder.getDefaultType ().getName ()))
  {
   return getIncludedTypes ();
  }
  return finder.expandType (type);
 }

 private Set<Type> includedTypes = null;

 private Set<Type> getIncludedTypes ()
 {
  if (includedTypes != null)
  {
   return includedTypes;
  }
  Set<Type> set = new LinkedHashSet ();
  for (Type type : finder.findTypes (xmlType.getName ()))
  {
   if (type.getInclude ())
   {
    set.add (type);
   }
  }
  includedTypes = set;
  return includedTypes;
 }

 private Set<State> getExpandedConfiguredStates (Type type)
 {
  Set<State> set = new HashSet ();
  for (State state : getConfiguredStates (type))
  {
   set.add (state);
   if (state.getStatus ().equalsIgnoreCase (State.GROUPING))
   {
    set.addAll (expandState (state));
   }
  }
  return set;
 }

 private Set<State> expandState (State state)
 {
  if (state.getName ().equalsIgnoreCase (finder.getDefaultState ().getName ()))
  {
   return getIncludedStates ();
  }
  return finder.expandState (state);
 }

 private Set<State> includedStates = null;

 private Set<State> getIncludedStates ()
 {
  if (includedStates != null)
  {
   return includedStates;
  }
  Set<State> set = new LinkedHashSet ();
  for (State state : finder.findStates (xmlType.getName ()))
  {
   if (state.getInclude ())
   {
    set.add (state);
   }
  }
  includedStates = set;
  return includedStates;
 }

 private void writeObjectStructureRef ()
 {
  writeRefBeanNoIndent ("dict:objectStructureRef", calcObjectId ());
 }

 private void writeTypeStructures ()
 {
  Set<String> alreadyWritten = new HashSet ();
  if ( ! xmlType.hasOwnType ())
  {
   writeTypeStructure (finder.getDefaultType (), null, true);
   return;
  }
//  String parentId = calcTypeId (finder.getDefaultType ());
  //writeTypeStructure (finder.getDefaultType (), null, true);
//  if (alreadyWritten.add (finder.getDefaultState ().getName ().toLowerCase ()))
//  {
//   writeTypeStructure (finder.getDefaultType (), parentId, true);
//  }
//  parentId = calcTypeId (finder.getDefaultType ());
  for (Type type : getConfiguredTypes ())
  {
   if (alreadyWritten.add (type.getName ().toLowerCase ()))
   {
    writeTypeStructure (type, null, true);
   }
  }
  for (Type type : getConfiguredTypes ())
  {
   if (type.getStatus ().equalsIgnoreCase (Type.GROUPING))
   {
    String parentId = calcTypeId (type);
    for (Type expandedType : expandType (type))
    {
     if (alreadyWritten.add (expandedType.getName ().toLowerCase ()))
     {
      writeTypeStructure (expandedType, parentId, false);
     }
    }
   }
  }
 }

 private Set<Type> configuredTypes = null;

 private Set<Type> getConfiguredTypes ()
 {
  if (configuredTypes != null)
  {
   return configuredTypes;
  }
  Set<Type> set = new LinkedHashSet ();
  Set<String> alreadyFound = new HashSet ();
  if (getMatchingDictionaryEntries (finder.getDefaultType (),
                                    finder.getDefaultState (), true).size () > 0)
  {
   if (alreadyFound.add (finder.getDefaultType ().getName ().toLowerCase ()))
   {
    set.add (finder.getDefaultType ());
   }
  }
  for (Type type : getIncludedTypes ())
  {
   if (alreadyFound.contains (type.getName ().toLowerCase ()))
   {
    continue;
   }
   List<Dictionary> dicts = getMatchingDictionaryEntries (type, finder.
    getDefaultState (), true);
   if (dicts.size () > 0)
   {
    if (alreadyFound.add (type.getName ().toLowerCase ()))
    {
     set.add (type);
    }
    continue;
   }
  }
  configuredTypes = set;
  return configuredTypes;
 }

 private void writeTypeStructure (Type type,
                                  String parentId,
                                  boolean writeStateRefs)
 {
  String id = calcTypeId (type);
  String key = type.getTypeKey ();
  System.out.println ("Writing out TYPE: " + id);
  writer.println ("");
  writer.writeComment (type.getComments ());
  writer.indentPrint ("<dict:type");
  writer.writeAttribute ("key", key);
  writeAbstractAttributeId (id);
  writeParentToAbstract (parentId);
  writer.println (">");
  writer.incrementIndent ();
  writer.writeTag ("dict:name", type.getName ());
  writer.writeTag ("dict:desc", type.getDesc ());
  writer.writeTag ("dict:effectiveDate", fixupDate (type.getEffectiveDate ()));
  writer.writeTag ("dict:expirationDate", fixupDate (type.getExpirationDate ()));
  if (writeStateRefs)
  {
   if ( ! xmlType.hasOwnState ())
   {
    writeRefBean ("dict:stateRef", calcStateId (type, finder.getDefaultState ()));
   }
   else
   {
    Set<State> expandedConfiguredStates = getExpandedConfiguredStates (type);
    for (State state : getIncludedStates ())
    {
     if ( ! state.getStatus ().equalsIgnoreCase (State.GROUPING))
     {
      if (expandedConfiguredStates.contains (state))
      {
       writeRefBean ("dict:stateRef", calcStateId (type, state));
      }
     }
    }
   }
  }
  writer.decrementIndent ();
  writer.println ("</dict:type>");

  // write concrete version if not a grouping
  if (shouldWriteConcreteVersion (type))
  {
   writer.indentPrint ("<dict:type");
   writer.writeAttribute ("key", key);
   writeAttributeId (id);
   writeParentToAbstract (id);
   writer.println ("/>");
  }
 }

 private boolean shouldWriteConcreteVersion (Type type)
 {
  if ( ! type.getStatus ().equalsIgnoreCase (Type.GROUPING))
  {
   return true;
  }
  // this is for xmlTypes like RichTextInfo that don't have a type so we
  // use Default as a proxy but in this situation Default is not really
  // used as a Group it is really it.
  if ( ! xmlType.hasOwnType ())
  {
   return true;
  }
  return false;
 }

 private void writeStateStructures ()
 {
  if ( ! xmlType.hasOwnType ())
  {
   writeStateStructures (finder.getDefaultType ());
   return;
  }
  for (Type type : getConfiguredTypes ())
  {
   writeStateStructures (type);
  }
 }

 private void writeStateStructures (Type type)
 {
  Set<String> alreadyWritten = new HashSet ();
//  String parentId = calcStateId (finder.getDefaultType (), finder.
//   getDefaultState ());
  if (alreadyWritten.add (finder.getDefaultState ().getName ().toLowerCase ()))
  {
   writeStateStructure (type, finder.getDefaultState (), null, true);
  }
  String parentId = calcStateId (type, finder.getDefaultState ());
  Set<State> configuredStates = getConfiguredStates (type);
  for (State state : configuredStates)
  {
   if (alreadyWritten.add (state.getName ().toLowerCase ()))
   {
    writeStateStructure (type, state, parentId, true);
   }
  }
  for (State state : configuredStates)
  {
   if (state.getStatus ().equalsIgnoreCase (State.GROUPING))
   {
    parentId = calcStateId (type, state);
    for (State expandedState : expandState (state))
    {
     if (alreadyWritten.add (expandedState.getName ().toLowerCase ()))
     {
      writeStateStructure (type, expandedState, parentId, false);
     }
    }
   }
  }
 }

 private Set<State> getConfiguredStates (Type type)
 {
  Set<State> set = new LinkedHashSet ();
  Set<State> statesToCheck = new LinkedHashSet ();
  statesToCheck.add (finder.getDefaultState ());
  statesToCheck.addAll (getIncludedStates ());
  Set<String> alreadyFound = new HashSet ();
  for (State state : statesToCheck)
  {
   if (alreadyFound.contains (state.getName ().toLowerCase ()))
   {
    continue;
   }
   List<Dictionary> dicts = getMatchingDictionaryEntries (type, state, true);
   if (dicts.size () > 0)
   {
    if (alreadyFound.add (state.getName ().toLowerCase ()))
    {
     set.add (state);
    }
    continue;
   }
   dicts = getSubjObjectsEntriesThatUseState (type, state);
   if (dicts.size () > 0)
   {
    if (alreadyFound.add (state.getName ().toLowerCase ()))
    {
     set.add (state);
    }
   }
  }
  return set;
 }

 private List<Dictionary> getSubjObjectsEntriesThatUseState (Type type,
                                                             State state)
 {
  List<Dictionary> list = new ArrayList ();
  for (Dictionary dict : getMatchingDictionaryEntries (type, state, false))
  {
   for (Dictionary d : finder.findChildDictionaryEntries (dict))
   {
    if (matchesMainTypeState (d, type, state))
    {
     list.add (d);
    }
   }
  }
  return list;
 }

 protected boolean matchesMainTypeState (Dictionary dict, Type type, State state)
 {
  if (type.getName ().equalsIgnoreCase (dict.getType ()))
  {
   if (state.getName ().equalsIgnoreCase (dict.getState ()))
   {
    return true;
   }
  }
  return false;
 }

 private void writeStateStructure (Type type,
                                   State state,
                                   String parentId,
                                   boolean writeFieldRefs)
 {
  String id = calcStateId (type, state);
  writer.indentPrintln ("");
  writer.writeCommentBox ("State for " + id);
  //System.out.println ("Writing out State: " + id);
  String key = state.getStateKey ();
  writer.indentPrintln ("");
  writer.writeComment (state.getComments ());
  writer.indentPrint ("<dict:state");
  writer.writeAttribute ("key", key);
  writeParentToAbstract (parentId);
  writeAbstractAttributeId (id);
  writer.println (">");
  if (writeFieldRefs)
  {
   writeFieldRefs (type, state);
  }
  writer.indentPrintln ("</dict:state>");

// write concrete version
  if (shouldWriteConcreteVersion (state))
  {
   writer.indentPrint ("<dict:state");
   writer.writeAttribute ("key", key);
   writeAttributeId (id);
   writeParentToAbstract (id);
   writer.println ("/>");
  }
 }

 private boolean shouldWriteConcreteVersion (State state)
 {
  if ( ! state.getStatus ().equalsIgnoreCase (State.GROUPING))
  {
   return true;
  }
  // this is to cover things like RichText that don't have a state so the default is used
  // as a proxy so it isn't really a grouping.
  if ( ! xmlType.hasOwnState ())
  {
   return true;
  }
  return false;
 }

 public class RightTypeState
 {

  protected Dictionary parent;
  protected String type;
  protected String state;
  protected String subType;
  protected String subState;

  public RightTypeState ()
  {
  }

  public RightTypeState (Dictionary parent,
                         String type,
                         String state,
                         String subType,
                         String subState)
  {
   this.parent = parent;
   this.type = type;
   this.state = state;
   this.subType = subType;
   this.subState = subState;
  }

 }

 private void writeFieldRefs (Type type, State state)
 {
  List<Dictionary> dicts = getMatchingDictionaryEntries (type, state, false);
  checkIfGotAllTheRightFields (dicts, type, state);
  for (Dictionary dict : dicts)
  {
   writeRefBean ("dict:fieldRef", calcDictionaryFieldId (dict));
  }
 }

 private List<Dictionary> dictionaryEntries = null;

 private List<Dictionary> getDictionaryEntries ()
 {
  if (dictionaryEntries == null)
  {
   dictionaryEntries = finder.findDictionaryEntriees (xmlType.getName ());
  }
  return dictionaryEntries;
 }

 private List<Dictionary> getMatchingDictionaryEntries (Type type, State state,
                                                        boolean exact)
 {
  RightTypeState right1 = calcRightTypeState (type, state);
  RightTypeState right2 =
   new RightTypeState (right1.parent,
                       right1.type,
                       right1.state,
                       right1.subType,
                       finder.getDefaultState ().getName ());
  RightTypeState right3 =
   new RightTypeState (right1.parent,
                       right1.type,
                       finder.getDefaultState ().getName (),
                       right1.subType,
                       right1.subState);
  RightTypeState right4 =
   new RightTypeState (right1.parent,
                       right1.type,
                       finder.getDefaultState ().getName (),
                       right1.subType,
                       finder.getDefaultState ().getName ());
  List<Dictionary> dicts = new ArrayList ();
  Set<String> alreadyFound = new HashSet ();
  for (int i = getDictionaryEntries ().size () - 1; i > -1; i --)
  {
   Dictionary dict = getDictionaryEntries ().get (i);
   if (alreadyFound.contains (dict.getShortName ().toLowerCase ()))
   {
    continue;
   }
   if (matches (dict, right1))
   {
    if (alreadyFound.add (dict.getShortName ().toLowerCase ()))
    {
     dicts.add (dict);
     continue;
    }
   }
   if (exact)
   {
    continue;
   }
   if (matches (dict, right2))
   {
    if (alreadyFound.add (dict.getShortName ().toLowerCase ()))
    {
     dicts.add (dict);
     continue;
    }
   }
   if (matches (dict, right3))
   {
    if (alreadyFound.add (dict.getShortName ().toLowerCase ()))
    {
     dicts.add (dict);
     continue;
    }
   }
   if (matches (dict, right4))
   {
    if (alreadyFound.add (dict.getShortName ().toLowerCase ()))
    {
     dicts.add (dict);
     continue;
    }
   }
  }
  Collections.reverse (dicts);
  return dicts;
 }

 private void checkIfGotAllTheRightFields (List<Dictionary> dicts, Type type,
                                           State state)
 {
  // double check that the list of dictionary entries found matches the fields
  // in the message structure (same order too)
  Set<String> matchingFields = new LinkedHashSet (dicts.size ());
  for (Dictionary dict : dicts)
  {
   // don't try to compare dynamic fields
   if ( ! dict.isDynamic ())
   {
    if ( ! matchingFields.add (dict.getShortName ().toLowerCase ()))
    {
     throw new DictionaryExecutionException ("Dictionary entry " + dict.getId ()
      + " in the default dictionary entries for "
      + xmlType.getName () + " is a repeat of field " + dict.getShortName ());
    }
   }
  }
  Set<String> allFields = new LinkedHashSet ();
  for (Field field : finder.findFields (xmlType.getName ()))
  {
   if ( ! field.getShortName ().equalsIgnoreCase ("attributes"))
   {
    if ( ! allFields.add (field.getShortName ().toLowerCase ()))
    {
     throw new DictionaryExecutionException ("Field entry " + field.getId ()
      + " for "
      + xmlType.getName () + " is a repeat of field " + field.getShortName ());
    }
   }
  }
  if (matchingFields.equals (allFields))
  {
   return;
  }
  getMatchingDictionaryEntries (type, state, false);
  StringBuilder buf = new StringBuilder ();
  buf.append ("Default dictionary for ");
  buf.append (xmlType.getName ());
  buf.append (" type ");
  buf.append (type.getName ());
  buf.append (" state ");
  buf.append (state.getName ());
  buf.append (" does not match list of message structure feilds");
  String comma = "\n  ";
  buf.append ("\nDictionary has ");
  buf.append (matchingFields.size ());
  buf.append (" fields:");
  for (String shortName : matchingFields)
  {
   buf.append (comma);
   comma = "\n ,";
   buf.append (shortName);
  }
  comma = "\n  ";
  buf.append ("\nFields have ");
  buf.append (allFields.size ());
  buf.append (" fields:");
  for (String shortName : allFields)
  {
   buf.append (comma);
   comma = "\n ,";
   buf.append (shortName);
  }
  RightTypeState right = calcRightTypeState (type, state);
  buf.append ("\nThe right type-state used to compare was");
  buf.append ("\ntype=");
  buf.append (right.type);
  buf.append ("\nstate=");
  buf.append (right.state);
  buf.append ("\nsub-type=");
  buf.append (right.subType);
  buf.append ("\nsub-state=");
  buf.append (right.subState);
  throw new DictionaryExecutionException (buf.toString ());
 }

 private RightTypeState calcRightTypeState (Type type, State state)
 {
  RightTypeState right = new RightTypeState ();
  if (this.parentField == null)
  {
   right.type = type.getName ();
   right.state = state.getName ();
   right.subType = finder.getDefaultType ().getName ();
   right.subState = finder.getDefaultState ().getName ();
   return right;
  }
  right.parent = parentField.getDict ();
  right.type = parentField.getMainType ().getName ();
  right.state = parentField.getMainState ().getName ();
  if (xmlType.hasOwnType ())
  {
   right.subType = type.getName ();
  }
  else
  {
   right.subType = parentField.getSubType ().getName ();
  }
  if (xmlType.hasOwnState ())
  {
   right.subState = state.getName ();
  }
  else
  {
   right.subState = parentField.getSubState ().getName ();
  }
  return right;
 }

 private boolean parentsSameFieldOrNulls (Dictionary dict1, Dictionary dict2)
 {
  if (dict1 == null && dict2 == null)
  {
   return true;
  }
  if (dict1 == null && dict2 != null)
  {
   return false;
  }
  if (dict1 != null && dict2 == null)
  {
   return false;
  }
  if (dict1.getXmlObject ().equalsIgnoreCase (dict2.getXmlObject ()))
  {
   if (dict1.getShortName ().equalsIgnoreCase (dict2.getShortName ()))
   {
    return true;
   }
  }
  return false;
 }

 protected boolean matches (Dictionary dict, RightTypeState right)
 {
  if (right.type.equalsIgnoreCase (dict.getType ()))
  {
   if (right.state.equalsIgnoreCase (dict.getState ()))
   {
    if (right.subType.equalsIgnoreCase (dict.getSubType ()))
    {
     if (right.subState.equalsIgnoreCase (dict.getSubState ()))
     {
      if (parentsSameFieldOrNulls (right.parent, dict.getParent ()))
      {
       return true;
      }
     }
    }
   }
  }
  return false;
 }

 private void writeDictionaryEntries ()
 {
  Set<Type> types = new LinkedHashSet ();
  types.add (finder.getDefaultType ());
  types.addAll (getConfiguredTypes ());
  for (Type type : types)
  {
   Set<State> states = new LinkedHashSet ();
   states.add (finder.getDefaultState ());
   states.addAll (getConfiguredStates (type));
   for (State state : states)
   {
    for (Dictionary dict : getMatchingDictionaryEntries (type, state, true))
    {
     DictionaryOverrideFieldWriter few =
      new DictionaryOverrideFieldWriter (writer,
                                         model,
                                         dict,
                                         type,
                                         state,
                                         this,
                                         dictionaryEntriesWritten);
     dictionaryEntriesWritten.add (dict.getId ());
     few.writeFieldStructure ();
    }
   }
  }
 }

 private void writeSubStructureObjects ()
 {
  for (Type type : getConfiguredTypes ())
  {
   for (State state : getConfiguredStates (type))
   {
    writeSubStructureObject (type, state);
   }
  }
 }

 private void writeSubStructureObject (Type type, State state)
 {
  writer.indentPrintln ("");
  // no write out each field
  for (Dictionary dict : getMatchingDictionaryEntries (type, state, false))
  {
   if ( ! dict.getPrimitive ().equalsIgnoreCase (XmlType.COMPLEX))
   {
    continue;
   }
   if (isNotUsed (dict))
   {
    continue;
   }
   if (dict.isDynamic ())
   {
    continue;
   }
   DictionaryOverrideFieldWriter few =
    new DictionaryOverrideFieldWriter (writer,
                                       model,
                                       dict,
                                       type,
                                       state,
                                       this,
                                       dictionaryEntriesWritten);
   few.writeSubObjectStructure ();
  }
 }

 private boolean isNotUsed (Dictionary dict)
 {
  if (dict.getAdditionalConstraintIds ().contains ("not.used"))
  {
   return true;
  }
  return false;
 }

 private void writeAbstractAttributeId (String id)
 {
  new AttributeIdUtil ().writeAbstractAttribute (writer, id);
 }

 private void writeParentToAbstract (String id)
 {
  if (id != null)
  {
   new AttributeIdUtil ().writeParentToAbstract (writer, id);
  }
 }

 private void writeAttributeId (String id)
 {
  new AttributeIdUtil ().writeAttribute (writer, id);
 }

 private void writeRefBeanNoIndent (String refType, String id)
 {
  new AttributeIdUtil ().writeRefBeanNoIndent (writer, refType, id);
 }

 private void writeRefBean (String refType, String id)
 {
  new AttributeIdUtil ().writeRefBean (writer, refType, id);
 }

 private String fixupDate (Date date)
 {
  if (date == null)
  {
   return "";
  }
  return new DateUtility ().asYMD (date);
 }

}
