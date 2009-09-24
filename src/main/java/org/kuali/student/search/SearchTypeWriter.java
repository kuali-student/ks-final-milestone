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
package org.kuali.student.search;

import java.io.PrintStream;
import org.kuali.student.dictionary.XmlWriter;

/**
 * Writes out a constraint in XML format.
 * @author nwright
 */
public class SearchTypeWriter extends XmlWriter
{

 private SearchType searchType;

 public SearchTypeWriter (PrintStream out, int indent, SearchType searchType)
 {
  super (out, indent);
  this.searchType = searchType;
 }

 public void write ()
 {
  indentPrint ("<search:searchType");
  //TODO: not sure what to put in the key attribute
  incrementIndent ();
  writeAttribute ("id", searchType.getKey ());
  println (">");

  // write out comments
  writeComment (searchType.getComments ());

  //TODO: deal with locale
  //writeAttribute ("locale", constraint.getLocale ());
  writeTag ("search:name", searchType.getName ());
  writeTag ("search:desc", searchType.getDescription ());
  indentPrint ("<search:resultTypeInfo>");
  print (calcRefBean (searchType.getResults ().getKey ()));
  println ("</search:resultTypeInfo>");
  indentPrint ("<search:searchCriteriaTypeInfo>");
  print (calcRefBean (searchType.getCriteria ().getKey ()));
  println ("</search:searchCriteriaTypeInfo>");

  // end the constraint
  indentPrintln ("</search:searchType>");
  decrementIndent ();
 }

 private String calcRefBean (String id)
 {
  return "<ref bean=\"" + id + "\" />";
 }
}
