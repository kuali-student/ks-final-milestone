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
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

/**
 * This reads a single tab of an Excel spreadsheet using the JDBC-ODBC bridge
 * @author nwright
 */
public class ExcelWorksheetReader implements WorksheetReader
{

 private ExcelSpreadsheetReader excelSpreadsheetReader;
 private String name;
 private ResultSet rs;

 public ExcelWorksheetReader (ExcelSpreadsheetReader excelSpreadsheetReader,
                              String name)
 {
  this.excelSpreadsheetReader = excelSpreadsheetReader;
  this.name = name;
  this.rs = this.getResultSet (name);
 }

 private ResultSet getResultSet (String name)
 {
  try
  {
   Connection con = this.excelSpreadsheetReader.getConnection ();
   Statement stmt = con.createStatement ();
   ResultSet resultSet = stmt.executeQuery ("select * from " + name);
   return resultSet;
  }
  catch (SQLException ex)
  {
   throw new DictionaryExecutionException (ex);
  }
 }

 @Override
 public int getEstimatedRows ()
 {
  return 100;
 }

 private int getColumn (String name)
 {
  try
  {
   ResultSetMetaData meta = rs.getMetaData ();
   for (int i = 1; i < meta.getColumnCount ();
        i ++)
   {
    if (meta.getColumnName (i).equals (name))
    {
     return i;
    }
   }
   throw new DictionaryValidationException (name + " is not the name of a column found in the worksheet");
  }
  catch (SQLException ex)
  {
   throw new DictionaryExecutionException (ex);
  }
 }

 private int getColumnType (String name)
 {
  try
  {
   int col = getColumn (name);
   ResultSetMetaData meta = rs.getMetaData ();
   int type = meta.getColumnType (col);
   return type;
  }
  catch (SQLException ex)
  {
   throw new DictionaryExecutionException (ex);
  }
 }

 @Override
 public String getValue (String name)
 {
  try
  {
   int type = getColumnType (name);
   switch (type)
   {
    case Types.VARCHAR:
     String valStr = rs.getString (name);
     if (valStr == null)
     {
      return "";
     }
     return valStr.trim ();
    case Types.BIT:
     Boolean b = rs.getBoolean (name);
     if (rs.wasNull ())
     {
      return "";
     }
     if (b == null)
     {
      return "";
     }
     if (b.booleanValue ())
     {
      return "true";
     }
     return "false";
    case Types.DOUBLE:
     Double d = rs.getDouble (name);
     if (rs.wasNull ())
     {
      return "";
     }
     if (d == null)
     {
      return "";
     }
     // TODO: figure out how to move this so it only applies to fields that are supposed to only be integers
     // Right now this is ok becuase all numeric fields are integers but...
     if (Math.floor (d) == d)
     {
      valStr = "" + d.intValue ();
     }
     else
     {
      throw new DictionaryValidationException (name + " is not an integer but should be " + d);
     }
     return valStr;
    default:
     throw new DictionaryExecutionException (
      "unhandled type " + type + " for " + name);
   }
  }
  catch (SQLException ex)
  {
   throw new DictionaryExecutionException (ex);
  }
 }

 @Override
 public boolean next ()
 {
  try
  {
   return rs.next ();
  }
  catch (SQLException ex)
  {
   throw new DictionaryExecutionException (ex);
  }
 }

}
