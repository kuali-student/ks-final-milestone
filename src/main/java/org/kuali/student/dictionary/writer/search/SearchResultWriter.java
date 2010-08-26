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

import org.kuali.student.dictionary.model.SearchResult;
import org.kuali.student.dictionary.model.SearchResultColumn;
import java.io.PrintStream;
import org.kuali.student.dictionary.writer.XmlWriter;
import org.kuali.student.dictionary.writer.XmlWriter;

/**
 * Writes out a constraint in XML format.
 * @author nwright
 */
public class SearchResultWriter extends XmlWriter
{

 private SearchResult searchResult;

 public SearchResultWriter (PrintStream out, int indent,
                            SearchResult searchResult)
 {
  super (out, indent);
  this.searchResult = searchResult;
 }

 public void write ()
 {

  println ("");
  indentPrint ("<search:searchResultTypeInfo");
  //TODO: not sure what to put in the key attribute
  writeAttribute ("id", searchResult.getKey ());
  println (">");
  incrementIndent ();
  // write out comments
  writeComment (searchResult.getComments ());

  writeTag ("search:name", searchResult.getName ());
  writeTag ("search:desc", searchResult.getDescription ());
  indentPrintln ("<search:resultColumns>");
  incrementIndent ();
  for (SearchResultColumn col : searchResult.getResultColumns ())
  {
   indentPrintln (calcRefBean (col.getKey ()));
  }
  decrementIndent ();
  indentPrintln ("</search:resultColumns>");
  indentPrintln ("</search:searchResultTypeInfo>");
  decrementIndent ();
 }

 private String calcRefBean (String id)
 {
  return "<ref bean=\"" + id + "\" />";
 }

}
