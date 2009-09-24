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

import java.util.List;

/**
 * This provides expansion functionality to the spreadsheet model by expanding
 * the dictionary entries when it encounters types or states that are not
 * individual types or states but actually groups.
 * @author nwright
 */
public class ExpandedSpreadsheet implements Spreadsheet
{

 private Spreadsheet spreadsheet;
 private DictionaryExpander expander;

 public ExpandedSpreadsheet (Spreadsheet spreadsheet, DictionaryExpander expander)
 {
  this.spreadsheet = spreadsheet;
  this.expander = expander;
 }

 @Override
 public List<Constraint> getConstraints ()
 {
  return spreadsheet.getConstraints ();
 }

 @Override
 public List<CrossObjectConstraint> getCrossObjectConstraints ()
 {
  return spreadsheet.getCrossObjectConstraints ();
 }

 private List<Dictionary> dicts = null;

 @Override
 public List<Dictionary> getDictionary ()
 {
  if (dicts == null)
  {
   dicts = expander.expand ();
  }
  return dicts;
 }

 @Override
 public List<Field> getFields ()
 {
  return spreadsheet.getFields ();
 }

 private List<State> states = null;

 @Override
 public List<State> getStates ()
 {
  return spreadsheet.getStates ();
 }

 @Override
 public List<Type> getTypes ()
 {
  return spreadsheet.getTypes ();
 }

 @Override
 public List<XmlType> getXmlTypes ()
 {
  return spreadsheet.getXmlTypes ();
 }

 public String getSourceName ()
 {
  return spreadsheet.getSourceName ();
 }
}
