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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.kuali.student.dictionary.XmlWriter;

/**
 * This writes out the entire dictionary xml file
 * @author nwright
 */
public class SearchSpreadsheetWriter extends XmlWriter
{

 private SearchSpreadsheet sheet;

 public SearchSpreadsheetWriter (PrintStream out, SearchSpreadsheet sheet)
 {
  super (out, 0);
  this.sheet = sheet;
 }

 /**
  * Write out the entire file
  * @param out
  */
 public void write ()
 {
  Collection<String> errors = new SearchSpreadsheetValidator (sheet).validate ();
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
   throw new SearchValidationException (buf.toString ());
  }
  writeHeader ();
  writeSearchTypes ();
  writeSearchResultTypes ();
  writeSearchResultColumns ();
  writeSearchCriteria ();
  writeSearchCriteriaParameters ();
  writeSqlQueryMap ();
  writeFooter ();
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
  indentPrintln ("xmlns:dict=\"http://student.kuali.org/xsd/dictionary-extension\"");
  indentPrint ("xsi:schemaLocation=\"\nhttp://student.kuali.org/xsd/dictionary-extension ");
  indentPrintln ("dictionary-extension.xsd");
  //indentPrintln ("https://test.kuali.org/svn/student/ks-core/branches/ks-core-dev/ks-common-impl/src/main/resources/dictionary-extension.xsd");
  indentPrintln ("http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.5.xsd");
  indentPrintln ("\">");
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
  for (SearchType st : sheet.getSearchTypes ())
  {
   SearchTypeWriter writer =
    new SearchTypeWriter (getOut (), getIndent () + 1, st);
   writer.write ();
  }
 }

 /**
  * write out the the object structure
  * @param out
  */
 protected void writeSearchResultTypes ()
 {
  for (SearchResult sr : getSearchResults ())
  {
   SearchResultWriter writer =
    new SearchResultWriter (getOut (), getIndent () + 1, sr);
   writer.write ();
  }

 }

 private List<SearchResult> getSearchResults ()
 {
  List<SearchResult> list = new ArrayList ();
  Set<String> keys = new HashSet ();
  for (SearchType st : sheet.getSearchTypes ())
  {
   if (keys.add (st.getResults ().getKey ()))
   {
    list.add (st.getResults ());
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
  for (SearchResultColumn col : getSearchResultColumns ())
  {
   SearchResultColumnWriter writer =
    new SearchResultColumnWriter (getOut (), getIndent () + 1, col);
   writer.write ();
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
  for (SearchCriteria sr : getSearchCriteria ())
  {
   SearchCriteriaWriter writer =
    new SearchCriteriaWriter (getOut (), getIndent () + 1, sr);
   writer.write ();
  }
 }

 private List<SearchCriteria> getSearchCriteria ()
 {
  List<SearchCriteria> list = new ArrayList ();
  Set<String> keys = new HashSet ();
  for (SearchType st : sheet.getSearchTypes ())
  {
   if (keys.add (st.getCriteria ().getKey ()))
   {
    list.add (st.getCriteria ());
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
  for (SearchCriteriaParameter col : getSearchCriteriaParameters ())
  {
   SearchCriteriaParameterWriter writer =
    new SearchCriteriaParameterWriter (getOut (), getIndent () + 1, col);
   writer.write ();
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
  indentPrintln ("<bean id=\"queryMap\" class=\"org.springframework.beans.factory.config.MapFactoryBean\">");
  indentPrintln ("<property name=\"sourceMap\">");
  indentPrintln ("<map>");
  for (SearchImplementation impl: getJPQLImplementations ())
  {
   indentPrintln ("<entry");
   writeAttribute ("key", impl.getKey ());
   indentPrint (">");
   writeComment (impl.getComments ());
   writeTag ("value", impl.getDescription ());
   indentPrintln ("</entry>");
  }
  indentPrintln ("</map>");
  indentPrintln ("</property>");
  indentPrintln ("</bean>");
 }

 private List<SearchImplementation> getImplementations ()
 {
  List<SearchImplementation> list = new ArrayList ();
  Set<String> keys = new HashSet ();
  for (SearchType st : sheet.getSearchTypes ())
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
