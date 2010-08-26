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
import java.io.PrintStream;
import org.kuali.student.dictionary.writer.XmlWriter;
import org.kuali.student.dictionary.writer.XmlWriter;

/**
 * Writes out a constraint in XML format.
 * @author nwright
 */
public class SearchCriteriaParameterWriter extends XmlWriter
{

 private SearchCriteriaParameter parm;

 public SearchCriteriaParameterWriter (PrintStream out, int indent,
                                  SearchCriteriaParameter parm)
 {
  super (out, indent);
  this.parm = parm;
 }

 public void write ()
 {
  println ("");
  indentPrint ("<search:queryParam");
  //TODO: not sure what to put in the key attribute
  incrementIndent ();
  writeAttribute ("id", parm.getKey ());
  println (">");
  incrementIndent ();

  // write out comments
  writeComment (parm.getComments ());
  indentPrintln ("<dict:fieldDescriptor>");
  
  writeTag ("dict:name", parm.getName ());
  writeTag ("dict:desc", parm.getDescription ());
  writeTag ("dict:dataType", parm.getDataType ());
  indentPrintln ("</dict:fieldDescriptor>");
  decrementIndent ();
  indentPrintln ("</search:queryParam>");
  decrementIndent ();
 }
}
