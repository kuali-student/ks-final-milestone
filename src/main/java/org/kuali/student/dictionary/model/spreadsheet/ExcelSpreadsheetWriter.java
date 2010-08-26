/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.dictionary.model.spreadsheet;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import org.kuali.student.dictionary.DictionaryExecutionException;

/**
 *
 * @author nwright
 */
public class ExcelSpreadsheetWriter extends ExcelSpreadsheetReader
 implements SpreadsheetWriter
{

 private String spreadsheetFileName;

 public ExcelSpreadsheetWriter (String spreadsheetFileName)
 {
  super (spreadsheetFileName);
  this.spreadsheetFileName = spreadsheetFileName;
 }

 public WorksheetWriter createWorksheet (String name,
                                         List<String> colNames)
  throws WorksheetAlreadyExistsException
 {
  WritableSheet writableSheet = getWriteableWorkbook ().createSheet (name, 0);
  writableSheet.insertRow (0);
  for (int i = 0; i < colNames.size (); i ++)
  {
   writableSheet.insertColumn (i);
   Label cell = new Label (i, 0, colNames.get (i));
   try
   {
    writableSheet.addCell (cell);
   }
   catch (RowsExceededException ex)
   {
    throw new DictionaryExecutionException ("Rows exceeded exception writing column headers", ex);
   }
   catch (WriteException ex)
   {
    throw new DictionaryExecutionException (ex);
   }

  }
  return new ExcelWorksheetWriter (this, writableSheet, name, colNames);
 }

 private WritableWorkbook writeableWorkbook = null;

 private WritableWorkbook getWriteableWorkbook ()
 {
  if (writeableWorkbook != null)
  {
   return writeableWorkbook;
  }
  File file = new File (spreadsheetFileName);
  try
  {
   if (file.exists ())
   {
    writeableWorkbook = Workbook.createWorkbook (file, getWorkbook ());
    return writeableWorkbook;
   }
   writeableWorkbook = Workbook.createWorkbook (file);
   return writeableWorkbook;
  }
  catch (IOException ex)
  {
   throw new DictionaryExecutionException ("Could not create spreadsheet " +
    spreadsheetFileName, ex);
  }

 }

 @Override
 public void save ()
 {
  try
  {
   getWriteableWorkbook ().write ();
  }
  catch (IOException ex)
  {
   throw new DictionaryExecutionException (ex);
  }
 }

}
