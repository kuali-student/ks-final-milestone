package org.kuali.student.core.dictionary.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import static org.junit.Assert.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.kuali.student.core.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.core.search.dto.SearchResultTypeInfo;
import org.kuali.student.core.search.dto.SearchTypeInfo;
import org.kuali.student.core.search.service.impl.SearchConfigFormatter;
import org.kuali.student.core.search.service.impl.SearchConfigValidator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SearchConfigTesterHelper
{

 private String outputFileName;
 private File file;
 private OutputStream os;
 private PrintStream out;
 private String searchConfigFileName;
 private String projectLocation;
 private Map<String, SearchTypeInfo> searchInfoTypeMap;
 private Map<String, SearchCriteriaTypeInfo> searchCriteriaTypeMap;
 private Map<String, SearchResultTypeInfo> searchResultTypeInfoMap;
 private Map<String, String> queryMap;

 public SearchConfigTesterHelper (String outputFileName,
                                  String projectLocation,
                                  String searchConfigFileName)
 {
  this.outputFileName = outputFileName;
  this.projectLocation = projectLocation;
  this.searchConfigFileName = searchConfigFileName;
  // get printstream from file
  this.file = new File (this.outputFileName);
  try
  {
   os = new FileOutputStream (file, false);
  }
  catch (FileNotFoundException ex)
  {
   throw new IllegalArgumentException (ex);
  }
  this.out = new PrintStream (os);
 }

 public void doTest ()
 {
  ApplicationContext ac = new ClassPathXmlApplicationContext (
    "classpath:" + searchConfigFileName);
  searchInfoTypeMap = ac.getBeansOfType (SearchTypeInfo.class);
  searchCriteriaTypeMap = ac.getBeansOfType (SearchCriteriaTypeInfo.class);
  searchResultTypeInfoMap = ac.getBeansOfType (SearchResultTypeInfo.class);
  queryMap = (Map<String, String>) ac.getBean ("queryMap");

  out.println ("(!) This page was automatically generated on " + new Date ());
  out.println ("DO NOT UPDATE MANUALLY!");
  out.println ("");
  out.print ("This page represents a formatted view of [" + searchConfigFileName
             + "|https://test.kuali.org/svn/student/trunk/" + projectLocation
             + "/src/main/resources/"
             + searchConfigFileName + "]");
  out.println ("");
  out.println ("----");
  out.println ("{toc}");
  out.println ("----");
  SearchConfigValidator validator = new SearchConfigValidator (searchInfoTypeMap,
                                                               queryMap);

  List<String> errors = validator.validate ();
  if (errors.size () > 0)
  {
   fail (searchConfigFileName + " failed search config validation:\n"
         + this.formatAsString (errors));
  }

  SearchConfigFormatter formatter = new SearchConfigFormatter (searchInfoTypeMap,
                                                               queryMap);
  out.println (formatter.formatForWiki ());
 }

 private String formatAsString (List<String> discrepancies)
 {
  int i = 0;
  StringBuilder builder = new StringBuilder ();
  for (String discrep : discrepancies)
  {
   i ++;
   builder.append (i + ". " + discrep + "\n");
  }
  return builder.toString ();
 }
}
