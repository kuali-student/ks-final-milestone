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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This reads an excel spreadsheet using the JDBC-ODBC bridge
 * @author nwright
 */
public class ExcelSpreadsheetReader implements SpreadsheetReader
{

 private String spreadsheetFileName;

 public ExcelSpreadsheetReader (String spreadsheetFileName)
 {
  this.spreadsheetFileName = spreadsheetFileName;
 }

 private transient Connection connection = null;
 private static final String JDBC_ODBC_BRIDGE_DRIVER =
  "sun.jdbc.odbc.JdbcOdbcDriver";
 private static final String JDBC_ODBC_URL_PREFIX = "jdbc:odbc:";
 public static final String MS_EXCEL_DRIVER = "Microsoft Excel Driver (*.xls)";

 protected Connection getConnection ()
 {
  if (this.connection != null)
  {
   return this.connection;
  }
  try
  {
   // Load the database driver
   Class.forName (JDBC_ODBC_BRIDGE_DRIVER);
  }
  catch (ClassNotFoundException ex)
  {
   throw new DictionaryExecutionException (ex);
  }
  try
  {
   // Get a connection to the database
   String url = JDBC_ODBC_URL_PREFIX + "Driver={" + MS_EXCEL_DRIVER + "}" +
    ";DBQ=" + spreadsheetFileName;
   connection =
    DriverManager.getConnection (url);
  }
  catch (SQLException ex)
  {
   throw new DictionaryExecutionException (ex);
  }
  return connection;
 }

 @Override
 public WorksheetReader getWorksheetReader (String name)
 {
  return new ExcelWorksheetReader (this, name);
 }

 @Override
 public void close ()
 {
  if (connection == null)
  {
   return;
  }
  try
  {
   connection.close ();
  }
  catch (SQLException ex)
  {
   throw new DictionaryExecutionException (ex);
  }
  // there is no explicit close but this should free up memory
  connection = null;
 }

}
