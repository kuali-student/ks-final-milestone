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
package org.kuali.student.dictionary.model.spreadsheet;

import org.kuali.student.dictionary.model.validation.DictionaryValidationException;
import java.util.ArrayList;
import java.util.List;
import jxl.Sheet;
import jxl.Workbook;

/**
 * This reads a single tab of an Excel spreadsheet using the JDBC-ODBC bridge
 * @author nwright
 */
public class ExcelWorksheetReader implements WorksheetReader
{

 private ExcelSpreadsheetReader reader;
 private String name;
 private Sheet sheet;
 protected int row = 0;
 private List<String> columnNames;

 public ExcelWorksheetReader (
  ExcelSpreadsheetReader reader,
  String name)
 {
  this.reader = reader;
  this.name = name;
  reopen ();
 }
 

 @Override
 public void reopen ()
 {
  close ();
  getSheet (name);
  getColumnNames ();
  row = 0;
 }

 @Override
 public void close ()
 {
  sheet = null;
  columnNames = null;
 }

 private List<String> getColumnNames ()
 {
  if (columnNames != null)
  {
   return columnNames;
  }
  int cols = sheet.getColumns ();
  List<String> names = new ArrayList (cols);
  for (int i = 0; i < cols; i ++)
  {
   names.add (sheet.getCell (i, 0).getContents ());
  }
  columnNames = names;
  return columnNames;
 }

 private Sheet getSheet (String name)
 {
  if (sheet != null)
  {
   return sheet;
  }
  Workbook workbook = reader.getWorkbook ();
  sheet = workbook.getSheet (name);
  if (sheet != null)
  {
   return sheet;
  }
  for (int i = 0; i < workbook.getNumberOfSheets (); i ++)
  {
   Sheet aSheet = workbook.getSheet (i);
   if (aSheet.getName ().equalsIgnoreCase (name))
   {
    sheet = aSheet;
    return sheet;
   }
  }
  return null;
 }

 @Override
 public int getEstimatedRows ()
 {
  // subtract 1 for the header row
  return sheet.getRows () - 1;
 }

 @Override
 public int getIndex (String name)
 {
  name = name.toLowerCase ().trim ();
  int col = 0;
  for (String colName : getColumnNames ())
  {
   if (name.equalsIgnoreCase (colName))
   {
    return col;
   }
   col ++;
  }
  return -1;
 }

 @Override
 public String getValue (String name)
 {
  name = name.toLowerCase ().trim ();
  int col = getIndex (name);
  if (col == -1)
  {
   throw new DictionaryValidationException ("ColName=" + name +
    " does not exist in " +
    getColumnNames ());
  }
  String val = sheet.getCell (col, row).getContents ();
  if (val == null)
  {
   return "";
  }
  return val.trim ();
 }

 @Override
 public boolean next ()
 {
  row ++;
  if (row < sheet.getRows ())
  {
   return true;
  }

  return false;
 }

}
