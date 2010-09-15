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

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.kuali.student.dictionary.model.spreadsheet.CompositeSpreadsheetReader;
import org.kuali.student.dictionary.model.spreadsheet.ExcelSpreadsheetReader;
import org.kuali.student.dictionary.model.spreadsheet.SpreadsheetReader;

/**
 *
 * @author nwright
 */
public class EnumeratedValueLoaderModelFactory
{

 public static final String RESOURCES_DIRECTORY = "resources/krudata";
 public static String ENUMERATED_VALUES_CIP_2010_XLS = "EnumeratedValues.xls";
 public static final String EXCEL_FILES_DEFAULT_DIRECTORY_KEY =
                            "loader.model.excel.default.directory";
 public static final String EXCEL_FILES_KEY =
                            "enumerated.value.loader.model.excel.file#";
 public static final String SERVICE_HOST_URL =
                            "loader.service.host.url";
 public static final String LOCAL_HOST_URL =
                            "http://localhost:9393/ks-embedded-dev";

 public static Properties getDefaultConfig ()
 {
  Properties defaultProps = new Properties ();
  defaultProps.put (EXCEL_FILES_DEFAULT_DIRECTORY_KEY, RESOURCES_DIRECTORY);
  defaultProps.put (EXCEL_FILES_KEY + "1", ENUMERATED_VALUES_CIP_2010_XLS);
  defaultProps.put (SERVICE_HOST_URL, LOCAL_HOST_URL);
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

 public EnumeratedValueLoaderModel getModel ()
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
  EnumeratedValueLoaderModelExcelImpl model =
                                   new EnumeratedValueLoaderModelExcelImpl (reader);
//  ContextFactory contextFactory = new ContextFactory ();
//  contextFactory.setConfig (config);
//  model.setContext (contextFactory.getInstance ());
  EnumeratedValueLoaderModelCache cache = new EnumeratedValueLoaderModelCache (model);
  return cache;
 }
}
