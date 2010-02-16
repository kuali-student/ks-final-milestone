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
package org.kuali.student.dictionary.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nwright
 */
public class MockDictionaryModel implements DictionaryModel
{

 @Override
 public List<String> getSourceNames ()
 {
  List<String> list = new ArrayList ();
  list.add ("Mock Dictionary Spreadsheet");
  list.add ("Mock Orchestration Spreadsheet");
  return list;
 }


 @Override
 public List<Constraint> getConstraints ()
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<Dictionary> getDictionary ()
 {
  List<Dictionary> list = new ArrayList ();
  list.add (getTestDict ());
  return list;
 }

 private Dictionary getTestDict ()
 {
  Dictionary dict = new Dictionary ();
  dict.setId ("test.id");
  dict.setName ("test.name");
  dict.setDesc ("test.desc");
  dict.setType ("test.mainType");
  dict.setState ("test.mainState");
  dict.setSubType ("test.subType");
  dict.setSubState ("test.subState");
  dict.setXmlObject ("test.xmlObject");
  dict.setShortName ("test.shortName");
  dict.setPrimitive ("Primitive");
  dict.setBaseConstraintDescription ("test.baseConstraintDescription");
  dict.setSelector (true);
  List addlConstraints = new ArrayList (4);
  dict.setAdditionalConstraintIds (addlConstraints);
  addlConstraints.add ("test.additionalConstraintId1");
  addlConstraints.add ("test.additionalConstraintId2");
  addlConstraints.add ("test.additionalConstraintId3");
  addlConstraints.add ("test.additionalConstraintId4");
  dict.setAdditionalConstraintDescription ("test.additionalConstraintDescription	");
  dict.setCombinedConstraintDescription ("test.combinedConstraintDescription	");

  Constraint inline = new Constraint ();
  inline.setInline (true);
  dict.setInlineConstraint (inline);
  inline.setId ("");
  inline.setMinLength ("test.minLength");
  inline.setMaxLength ("test.maxLength");
  inline.setMinOccurs ("test.minOccurs");
  inline.setMaxOccurs ("test.maxOccurs");
  inline.setValidChars ("test.validChars");
  inline.setLookup ("test.lookup");
  dict.setComments ("test.comment");

  return dict;
 }

 @Override
 public List<Field> getFields ()
 {
  List<Field> list = new ArrayList ();
  list.add (getTestField ());
  return list;
 }

 private Field getTestField ()
 {
  Field field = new Field ();
  field.setId ("test.id");
  field.setXmlObject ("test.xmlObject");
  field.setShortName ("test.shortName");
  field.setName ("test.name");
  field.setXmlType ("test.xmlType");
  field.setPrimitive ("primitive");
  List constraints = new ArrayList (4);
  field.setConstraintIds (constraints);
  constraints.add ("test.additionalConstraintId1");
  constraints.add ("test.additionalConstraintId2");
  constraints.add ("test.additionalConstraintId3");
  constraints.add ("test.additionalConstraintId4");
  field.setConstraintDescription ("test.constraintDescription");
  field.setDynamic (true);
  Constraint inline = new Constraint ();
  inline.setInline (true);
  field.setInlineConstraint (inline);
  inline.setId ("");
  inline.setMinLength ("test.minLength");
  inline.setMaxLength ("test.maxLength");
  inline.setMinOccurs ("test.minOccurs");
  inline.setMaxOccurs ("test.maxOccurs");
  inline.setValidChars ("test.validChars");
  inline.setLookup ("test.lookup");
  field.setDesc ("test.desc");
  field.setComments ("test.comment");
  return field;
 }

 @Override
 public List<State> getStates ()
 {
  List<State> list = new ArrayList ();
  list.add (getTestState ());
  return list;
 }

 private State getTestState ()
 {
  State state = new State ();
  state.setName ("test.name");
  state.setDesc ("test.desc");
  state.setXmlObject ("test.xmlObject");
  state.setXmlObjectDesc ("test.xmlObjectDesc");
  state.setStatus ("test.status");
  state.setComments ("test.comment");
  return state;
 }

 @Override
 public List<Type> getTypes ()
 {
  List<Type> list = new ArrayList ();
  list.add (getTestType ());
  return list;
 }

 private Type getTestType ()
 {
  Type type = new Type ();
  type.setName ("test.name");
  type.setDesc ("test.desc");
  type.setXmlObject ("test.xmlObject");
  type.setPrimitive ("primitive");
  type.setTypeKey ("test.typeKey");
  type.setAliases ("test.aliases");
  type.setStatus ("test.status");
  type.setComments ("test.comment");
  return type;
 }

 @Override
 public List<XmlType> getXmlTypes ()
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<CrossObjectConstraint> getCrossObjectConstraints ()
 {
  List<CrossObjectConstraint> list = new ArrayList ();
  return list;
 }

 public List<OrchObj> getOrchObjs ()
 {
  return new ArrayList ();
 }

 public List<MessageStructure> getMessageStructures ()
 {
  return new ArrayList ();
 }

 public List<SearchType> getSearchTypes ()
 {
  return new ArrayList ();
 }

 public List<Project> getProjects ()
 {
  return new ArrayList ();
 }

 public List<Service> getServices ()
 {
  return new ArrayList ();
 }

 public List<ServiceMethod> getServiceMethods ()
 {
  return new ArrayList ();
 }



}
