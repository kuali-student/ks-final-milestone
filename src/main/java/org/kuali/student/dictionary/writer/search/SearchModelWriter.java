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

import org.kuali.student.dictionary.model.validation.DictionaryValidationException;
import org.kuali.student.dictionary.model.SearchResult;
import org.kuali.student.dictionary.model.SearchImplementation;
import org.kuali.student.dictionary.model.validation.SearchModelValidator;
import org.kuali.student.dictionary.model.SearchCriteriaParameter;
import org.kuali.student.dictionary.model.SearchModel;
import org.kuali.student.dictionary.model.SearchCriteria;
import org.kuali.student.dictionary.model.SearchType;
import org.kuali.student.dictionary.model.SearchResultColumn;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.kuali.student.dictionary.writer.XmlWriter;

/**
 * This writes out the entire search xml file
 * @author nwright
 */
public class SearchModelWriter
{

 private SearchModel model;
 private XmlWriter writer;

 public SearchModelWriter (PrintStream out, SearchModel model)
 {
  this.writer = new XmlWriter (out, 0);
  this.model = model;
 }

 /**
  * Write out the entire file
  * @param out
  */
 public void write ()
 {
  Collection<String> errors = new SearchModelValidator (model).validate ();
  if (errors.size () > 0)
  {
   StringBuffer buf = new StringBuffer ();
   buf.append (errors.size () +
    " errors found while validating the spreadsheet.");
   int cnt = 0;
   for (String msg : errors)
   {
    cnt ++;
    buf.append ("\n");
    buf.append ("*error*" + cnt + ":" + msg);
   }
   throw new DictionaryValidationException (buf.toString ());
  }
  writeHeader ();
  writeSearchTypes ();
  writeSearchCriteria ();
  writeSearchCriteriaParameters ();
  writeSearchResultTypes ();
  writeSearchResultColumns ();
  writeSqlQueryMap ();
  writeFooter ();
 }

 private void indentPrint (String str)
 {
  writer.indentPrintln (str);
 }

 private void indentPrintln (String str)
 {
  writer.indentPrintln (str);
 }

 private void println (String str)
 {
  writer.indentPrintln (str);
 }

 private void writeComment (String str)
 {
  writer.writeComment (str);
 }

 private void incrementIndent ()
 {
  writer.incrementIndent ();
 }
 private void decrementIndent ()
 {
  writer.decrementIndent ();
 }

 private void writeTag (String tag, String value)
 {
  writer.writeTag (tag, value);
 }

 private void writeAttribute (String attribute, String value)
 {
  writer.writeAttribute (attribute, value);
 }


 /**
   * write out the header
   * @param out
   */
 protected void writeHeader ()
 {
  indentPrintln ("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
  indentPrintln ("<beans xmlns=\"http://www.springframework.org/schema/beans\"");
  indentPrintln ("xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
  indentPrintln ("xmlns:search=\"http://student.kuali.org/xsd/search-extension\"");
  indentPrintln ("xmlns:dict=\"http://student.kuali.org/xsd/dictionary-extension\"");
  indentPrintln ("xsi:schemaLocation=\"");
  indentPrintln ("http://student.kuali.org/xsd/search-extension http://student.kuali.org/xsd/search-extension/search-extension.xsd");
  indentPrintln ("http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.5.xsd");
  indentPrintln ("\">");
  StringBuffer buf = new StringBuffer ();
  buf.append ("*** Automatically Generated ***");
  buf.append ("\n");
  buf.append ("on: " + (new Date ()));
  buf.append ("\n");
  buf.append ("by: " + this.getClass ().getName ());
  buf.append ("\n");
  buf.append ("using: ");
  String comma = "";
  for (String soureName : model.getSourceNames ())
  {
   buf.append (soureName);
   buf.append (comma);
   comma = ", ";
  }
  buf.append ("\n");
  writeComment (buf.toString ());
 }

 protected void writeFooter ()
 {
  indentPrintln ("</beans>");
 }

 /**
  * write out the the base fields
  * @param out
  */
 protected void writeSearchTypes ()
 {
  println ("");
  writeComment ("Search Types");
  for (SearchType st : model.getSearchTypes ())
  {
   new SearchTypeWriter (writer.getOut (), writer.getIndent () + 1, st).write ();
  }
 }

 /**
  * write out the the object structure
  * @param out
  */
 protected void writeSearchResultTypes ()
 {
  println ("");
  writeComment ("Search Results");
  for (SearchResult sr : getSearchResults ())
  {
   new SearchResultWriter (writer.getOut (), writer.getIndent () + 1, sr).write ();
  }

 }

 private List<SearchResult> getSearchResults ()
 {
  List<SearchResult> list = new ArrayList ();
  Set<String> keys = new HashSet ();
  for (SearchType st : model.getSearchTypes ())
  {
   if (keys.add (st.getSearchResult ().getKey ()))
   {
    list.add (st.getSearchResult ());
   }
  }
  return list;
 }

 /**
  * write out the the object structure
  * @param out
  */
 protected void writeSearchResultColumns ()
 {
  println ("");
  writeComment ("Search Result Columns");
  for (SearchResultColumn col : getSearchResultColumns ())
  {
   new SearchResultColumnWriter (writer.getOut (), writer.getIndent () + 1, col).
    write ();
  }
 }

 private List<SearchResultColumn> getSearchResultColumns ()
 {
  List<SearchResultColumn> list = new ArrayList ();
  Set<String> keys = new HashSet ();
  for (SearchResult searchResult : getSearchResults ())
  {
   for (SearchResultColumn col : searchResult.getResultColumns ())
   {
    if (keys.add (col.getKey ()))
    {
     list.add (col);
    }

   }
  }
  return list;
 }

 /**
  * write out the the object structure
  * @param out
  */
 protected void writeSearchCriteria ()
 {
  println ("");
  writeComment ("Search Criteria");
  for (SearchCriteria sr : getSearchCriteria ())
  {
   new SearchCriteriaWriter (writer.getOut (), writer.getIndent () + 1, sr).
    write ();
  }
 }

 private List<SearchCriteria> getSearchCriteria ()
 {
  List<SearchCriteria> list = new ArrayList ();
  Set<String> keys = new HashSet ();
  for (SearchType st : model.getSearchTypes ())
  {
   if (keys.add (st.getSearchCriteria ().getKey ()))
   {
    list.add (st.getSearchCriteria ());
   }
  }
  return list;
 }

 /**
  * write out the the object structure
  * @param out
  */
 protected void writeSearchCriteriaParameters ()
 {
  println ("");
  writeComment ("Search Criteria Parameters");
  for (SearchCriteriaParameter col : getSearchCriteriaParameters ())
  {
   new SearchCriteriaParameterWriter (writer.getOut (), writer.getIndent () + 1, col).
    write ();
  }
 }

 private List<SearchCriteriaParameter> getSearchCriteriaParameters ()
 {
  List<SearchCriteriaParameter> list = new ArrayList ();
  Set<String> keys = new HashSet ();
  for (SearchCriteria criteria : getSearchCriteria ())
  {
   for (SearchCriteriaParameter parm : criteria.getParameters ())
   {
    if (keys.add (parm.getKey ()))
    {
     list.add (parm);
    }

   }
  }
  return list;
 }

 /**
  * write out the the object structure
  * @param out
  */
 protected void writeSqlQueryMap ()
 {
  println ("");
  incrementIndent ();
  writeComment ("Query Map");
  indentPrintln ("<bean id=\"queryMap\" class=\"org.springframework.beans.factory.config.MapFactoryBean\">");
  indentPrintln ("<property name=\"sourceMap\">");
  indentPrintln ("<map>");
  incrementIndent ();
  for (SearchImplementation impl : getJPQLImplementations ())
  {
   println ("");
   indentPrint ("<entry");
   writeAttribute ("key", impl.getKey ());
   indentPrintln (">");
   writeComment (impl.getComments ());
   incrementIndent ();
   writeTag ("value", "\n" + impl.getDescription () + "\n");
   decrementIndent ();
   indentPrintln ("</entry>");
  }
  decrementIndent ();
  indentPrintln ("</map>");
  indentPrintln ("</property>");
  indentPrintln ("</bean>");
  decrementIndent ();
 }

 private List<SearchImplementation> getImplementations ()
 {
  List<SearchImplementation> list = new ArrayList ();
  Set<String> keys = new HashSet ();
  for (SearchType st : model.getSearchTypes ())
  {
   if (keys.add (st.getImplementation ().getKey ()))
   {
    list.add (st.getImplementation ());
   }
  }
  return list;
 }

 private List<SearchImplementation> getJPQLImplementations ()
 {
  List<SearchImplementation> list = new ArrayList ();
  Set<String> keys = new HashSet ();
  for (SearchImplementation impl : getImplementations ())
  {
   if (impl.getType ().equals ("JPQL"))
   {
    list.add (impl);
   }
  }
  return list;
 }

}
