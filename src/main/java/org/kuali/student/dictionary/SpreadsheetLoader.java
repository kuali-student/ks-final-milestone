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
package org.kuali.student.dictionary;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 *
 * @author nwright
 */
public class SpreadsheetLoader implements Spreadsheet
{

 private SpreadsheetReader spreadsheetReader;

 public SpreadsheetLoader (SpreadsheetReader spreadsheetReader)
 {
  this.spreadsheetReader = spreadsheetReader;
 }

 /**
  * load the dictionary entries for the default state
  * @return
  */
 @Override
 public List<Dictionary> getDictionary ()
 {
  WorksheetReader worksheetReader =
   spreadsheetReader.getWorksheetReader ("Dictionary");
  List<Dictionary> list = new ArrayList (worksheetReader.getEstimatedRows ());
  while (worksheetReader.next ())
  {
   Dictionary dict = this.loadDictionaryObject (worksheetReader);
   if (dict != null)
   {
    list.add (dict);
   }
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
  if (str.equals ("FALSE"))
  {
   return "false";
  }
  if (str.equals ("TRUE"))
  {
   return "true";
  }
  return str;
 }

 private Dictionary loadDictionaryObject (WorksheetReader worksheetReader)
 {
  Dictionary dict = new Dictionary ();
  dict.setId (getFixup (worksheetReader, "id"));
  if (dict.getId ().equals (""))
  {
   return null;
  }
  dict.setParentId (getFixup (worksheetReader, "parentId"));
  dict.setName (getFixup (worksheetReader, "name"));
  dict.setDesc (getFixup (worksheetReader, "desc"));
  if (dict.getDesc ().equals ("ignore this row"))
  {
   return null;
  }
  dict.setMainType (getFixup (worksheetReader, "mainType"));
  dict.setMainState (getFixup (worksheetReader, "mainState"));
  dict.setParentObject (getFixup (worksheetReader, "parentObject"));
  dict.setParentShortName (getFixup (worksheetReader, "parentShortName"));
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
  inline.setId ("");
  inline.setKey ("in-line.constraint.for.dictionary." + dict.getId ());
  inline.setMinLength (getFixup (worksheetReader, "minLength"));
  inline.setMaxLength (getFixup (worksheetReader, "maxLength"));
  if (inline.getMaxLength ().equals ("999999999"))
  {
   inline.setMaxLength ("(unbounded)");
  }
  inline.setMinValue (getFixup (worksheetReader, "minValue"));
  inline.setMaxValue (getFixup (worksheetReader, "maxValue"));
  inline.setMinOccurs (getFixup (worksheetReader, "minOccurs"));
  inline.setMaxOccurs (getFixup (worksheetReader, "maxOccurs"));
  if (inline.getMaxOccurs ().equals ("999999999"))
  {
   inline.setMaxOccurs ("(unbounded)");
  }
  inline.setValidChars (getFixup (worksheetReader, "validChars"));
  if ( ! isValidChars (inline.getValidChars ()))
  {
   throw new RuntimeException ("Field " + dict.getId () +
    " contains an invalid regular expression " + inline.getValidChars ());
  }
  inline.setLookup (getFixup (worksheetReader, "lookup"));
  dict.setInlineConstraint (inline);

  // finish
  dict.setComments (getFixup (worksheetReader, "comments"));

  return dict;
 }

 /**
  * load lu States
  * @return
  */
 @Override
 public List<State> getStates ()
 {
  WorksheetReader worksheetReader =
   spreadsheetReader.getWorksheetReader ("States");
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

   state.setXmlObject (getFixup (worksheetReader, "xmlObject"));
   state.setXmlObjectDesc (getFixup (worksheetReader, "xmlObjectDesc"));
   state.setInclude (getFixup (worksheetReader, "include"));
   state.setStatus (getFixup (worksheetReader, "status"));
   state.setComments (getFixup (worksheetReader, "comments"));
   if ( ! state.getDesc ().equals ("ignore this row"))
   {
    list.add (state);
   }
  }
  return list;
 }

 /**
  * get Types
  * @return
  */
 @Override
 public List<Type> getTypes ()
 {
  WorksheetReader worksheetReader =
   spreadsheetReader.getWorksheetReader ("Types");
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
   type.setXmlObject (getFixup (worksheetReader, "xmlObject"));
   type.setPrimitive (getFixup (worksheetReader, "primitive"));
   type.setInclude (getFixup (worksheetReader, "include"));
   type.setTypeKey (get (worksheetReader, "typeKey"));
   type.setAliases (getFixup (worksheetReader, "aliases"));
   type.setStatus (getFixup (worksheetReader, "status"));
   type.setComments (getFixup (worksheetReader, "comments"));
   if ( ! type.getDesc ().equals ("ignore this row"))
   {
    list.add (type);
   }
  }
  return list;
 }

 /**
  * load info types
  * @return
  */
 @Override
 public List<XmlType> getXmlTypes ()
 {
  WorksheetReader worksheetReader =
   spreadsheetReader.getWorksheetReader ("XmlTypes");
  List<XmlType> list = new ArrayList (worksheetReader.getEstimatedRows ());
  while (worksheetReader.next ())
  {
   XmlType info = new XmlType ();
   info.setName (getFixup (worksheetReader, "name"));
   if (info.getName ().equals (""))
   {
    continue;
   }
   info.setDesc (getFixup (worksheetReader, "desc"));
   info.setPrimitive (getFixup (worksheetReader, "primitive"));
   info.setHasOwnType (getFixup (worksheetReader, "hasOwnType"));
   info.setHasOwnState (getFixup (worksheetReader, "hasOwnState"));
   info.setHasOwnCreateUpdate (getFixup (worksheetReader, "hasOwnCreateUpdate"));

   info.setExamples (getFixup (worksheetReader, "examples"));
   if ( ! info.getDesc ().equals ("ignore this row"))
   {
    list.add (info);
   }
   info.setComments (getFixup (worksheetReader, "comments"));
  }
  return list;
 }

 /**
  * load message structure fields
  * @return
  */
 @Override
 public List<Field> getFields ()
 {
  WorksheetReader worksheetReader =
   spreadsheetReader.getWorksheetReader ("Fields");
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
   // do in-line constraint
   Constraint inline = new Constraint ();
   inline.setId ("");
   inline.setKey ("in-line.constraint.for.field." + field.getId ());
   inline.setMinLength (getFixup (worksheetReader, "minLength"));
   inline.setMaxLength (getFixup (worksheetReader, "maxLength"));
   if (inline.getMaxLength ().equals ("999999999"))
   {
    inline.setMaxLength ("(unbounded)");
   }
   inline.setMinValue (getFixup (worksheetReader, "minValue"));
   inline.setMaxValue (getFixup (worksheetReader, "maxValue"));
   inline.setMinOccurs (getFixup (worksheetReader, "minOccurs"));
   inline.setMaxOccurs (getFixup (worksheetReader, "maxOccurs"));
   if (inline.getMaxOccurs ().equals ("999999999"))
   {
    inline.setMaxOccurs ("(unbounded)");
   }
   inline.setValidChars (getFixup (worksheetReader, "validChars"));
   if ( ! isValidChars (inline.getValidChars ()))
   {
    throw new RuntimeException ("Field " + field.getId () +
     " contains an invalid regular expression " + inline.getValidChars ());
   }
   inline.setLookup (getFixup (worksheetReader, "lookup"));
   field.setInlineConstraint (inline);
   // finish up
   field.setDesc (getFixup (worksheetReader, "desc"));
   field.setComments (getFixup (worksheetReader, "comments"));
   if ( ! field.getDesc ().equals ("ignore this row"))
   {
    list.add (field);
   }
  }
  return list;
 }

 /**
  * load message structure fields
  * @return
  */
 @Override
 public List<Constraint> getConstraints ()
 {
  WorksheetReader worksheetReader =
   spreadsheetReader.getWorksheetReader ("Constraints");
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
   constraint.setMinLength (getFixup (worksheetReader, "minLength"));
   constraint.setMaxLength (getFixup (worksheetReader, "maxLength"));
   if (constraint.getMaxLength ().equals ("999999999"))
   {
    constraint.setMaxLength ("(unbounded)");
   }
   constraint.setMinValue (getFixup (worksheetReader, "minValue"));
   constraint.setMaxValue (getFixup (worksheetReader, "maxValue"));
   constraint.setMinOccurs (getFixup (worksheetReader, "minOccurs"));
   constraint.setMaxOccurs (getFixup (worksheetReader, "maxOccurs"));
   if (constraint.getMaxOccurs ().equals ("999999999"))
   {
    constraint.setMaxOccurs ("(unbounded)");
   }
   constraint.setValidChars (getFixup (worksheetReader, "validChars"));
   if ( ! isValidChars (constraint.getValidChars ()))
   {
    throw new RuntimeException ("Constraint " + constraint.getId () +
     " contains an invalid regular expression " + constraint.getValidChars ());
   }
   constraint.setLookup (getFixup (worksheetReader, "lookup"));
   constraint.setComments (getFixup (worksheetReader, "comments"));
   if ( ! constraint.getDesc ().equals ("ignore this row"))
   {
    list.add (constraint);
   }
  }
  return list;
 }

 /**
  * load cross object constraints
  * @return
  */
 @Override
 public List<CrossObjectConstraint> getCrossObjectConstraints ()
 {
  WorksheetReader worksheetReader =
   spreadsheetReader.getWorksheetReader ("CrossObjectConstraints");
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
   if (coc.getMaxOccurs ().equals ("999999999"))
   {
    coc.setMaxOccurs ("(unbounded)");
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

}
