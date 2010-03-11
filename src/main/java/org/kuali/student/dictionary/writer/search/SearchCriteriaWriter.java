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
package org.kuali.student.dictionary.writer.search;

import org.kuali.student.dictionary.model.SearchCriteriaParameter;
import org.kuali.student.dictionary.model.SearchCriteria;
import java.io.PrintStream;
import org.kuali.student.dictionary.writer.XmlWriter;
import org.kuali.student.dictionary.writer.XmlWriter;

/**
 * Writes out a constraint in XML format.
 * @author nwright
 */
public class SearchCriteriaWriter extends XmlWriter
{

 private SearchCriteria searchCriteria;

 public SearchCriteriaWriter (PrintStream out, int indent,
                              SearchCriteria searchCriteria)
 {
  super (out, indent);
  this.searchCriteria = searchCriteria;
 }

 public void write ()
 {

  println ("");
  indentPrint ("<search:searchCriteriaTypeInfo");
  //TODO: not sure what to put in the key attribute
  writeAttribute ("id", searchCriteria.getKey ());
  println (">");
  incrementIndent ();

  // write out comments
  writeComment (searchCriteria.getComments ());

  writeTag ("search:name", searchCriteria.getName ());
  writeTag ("search:desc", searchCriteria.getDescription ());
  indentPrintln ("<search:queryParams>");
  incrementIndent ();
  for (SearchCriteriaParameter col : searchCriteria.getParameters ())
  {
   indentPrintln (calcRefBean (col.getKey ()));
  }
  decrementIndent ();
  indentPrintln ("</search:queryParams>");
  // end 
  indentPrintln ("</search:searchCriteriaTypeInfo>");
  decrementIndent ();
 }

 private String calcRefBean (String id)
 {
  return "<ref bean=\"" + id + "\" />";
 }

}
