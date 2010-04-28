/*
 * Copyright 2010 The Kuali Foundation
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
package org.kuali.student.loader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.kuali.student.dictionary.model.spreadsheet.CompositeSpreadsheetReader;
import org.kuali.student.dictionary.model.spreadsheet.ExcelSpreadsheetReader;
import org.kuali.student.dictionary.model.spreadsheet.SpreadsheetReader;

/**
 *
 * @author nwright
 */
public class CreditCourseLoaderModelFactory
{

 public static final String RESOURCES_DIRECTORY = "resources/krudata";
 public static String COURSES = "Courses.xls";
 public static final String EXCEL_FILES_DEFAULT_DIRECTORY_KEY =
                            "loader.model.excel.default.directory";
 public static final String EXCEL_FILES_KEY =
                            "credit.course.loader.model.excel.file#";
 public static final String OUTPUT_DEFAULT_DIRECTORY_KEY =
                            "loader.output.default.directory";
 public static final String COURSES_SQL_INSERT_FILE = "CoursesInsert.sql";
 public static final String SQL_FILES_OUTPUT_KEY =
                            "credit.course.loader.model.sql.output";

 public static Properties getDefaultConfig ()
 {
  Properties defaultProps = new Properties ();
  defaultProps.put (EXCEL_FILES_DEFAULT_DIRECTORY_KEY, RESOURCES_DIRECTORY);
  defaultProps.put (EXCEL_FILES_KEY + "1", COURSES);
  defaultProps.put (OUTPUT_DEFAULT_DIRECTORY_KEY, RESOURCES_DIRECTORY);
  defaultProps.put (SQL_FILES_OUTPUT_KEY, COURSES_SQL_INSERT_FILE);
  return defaultProps;
 }

 private Properties config;

 public Properties getConfig ()
 {
  return config;
 }

 public void setConfig (Properties config)
 {
  this.config = config;
 }

 public CreditCourseLoaderModel getModel ()
 {
  List<SpreadsheetReader> list = new ArrayList ();
  String directory = config.getProperty (EXCEL_FILES_DEFAULT_DIRECTORY_KEY);
  for (int i = 0; i <= 100; i ++)
  {
   String excelFileName = config.getProperty (EXCEL_FILES_KEY + i);
   if (excelFileName != null)
   {
//    System.out.println ("excelFile(" + i + ")=" + excelFileName);
    if (directory != null)
    {
     if ( ! excelFileName.startsWith ("/"))
     {
//      System.out.println ("prepending with directory");
      excelFileName = directory + "/" + excelFileName;
     }
    }
//    System.out.println ("adding excelFile(" + i + ")=" + excelFileName);
    list.add (new ExcelSpreadsheetReader (excelFileName));
   }
  }
  SpreadsheetReader reader = new CompositeSpreadsheetReader (list);
  CreditCourseLoaderModelExcelImpl model =
                                   new CreditCourseLoaderModelExcelImpl (reader);
//  ContextFactory contextFactory = new ContextFactory ();
//  contextFactory.setConfig (config);
//  model.setContext (contextFactory.getInstance ());
  CreditCourseLoaderModelCache cache = new CreditCourseLoaderModelCache (model);
  return cache;
 }

 public PrintWriter getOut ()
 {
  List<SpreadsheetReader> list = new ArrayList ();
  String directory = config.getProperty (OUTPUT_DEFAULT_DIRECTORY_KEY);
  String fileName = config.getProperty (SQL_FILES_OUTPUT_KEY);
  if (fileName != null)
  {
//    System.out.println ("excelFile(" + i + ")=" + excelFileName);
   if (directory != null)
   {
    if ( ! fileName.startsWith ("/"))
    {
//      System.out.println ("prepending with directory");
     fileName = directory + "/" + fileName;
    }
   }
  }
  File file = new File (fileName);
  OutputStream os = null;
  try
  {
   os = new FileOutputStream (file, false);
  }
  catch (FileNotFoundException ex)
  {
   throw new IllegalArgumentException (ex);
  }
  PrintWriter out = new PrintWriter (os);
  return out;
 }

}
