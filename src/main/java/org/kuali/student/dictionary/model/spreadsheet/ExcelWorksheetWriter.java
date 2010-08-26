/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.dictionary.model.spreadsheet;

import java.util.List;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 *
 * @author nwright
 */
public class ExcelWorksheetWriter extends ExcelWorksheetReader
 implements WorksheetWriter
{

 private String name;
 private List<String> colNames;
 private ExcelSpreadsheetWriter writer;
 private WritableSheet writableSheet;

 public ExcelWorksheetWriter (ExcelSpreadsheetWriter writer,
                              WritableSheet writableSheet,
                              String name,
                              List<String> colNames)
 {
  super (writer, name);
  this.writableSheet = writableSheet;
  this.name = name;
  this.writer = writer;
  this.colNames = colNames;
 }

 @Override
 public void close ()
 {
  super.close ();
 }

 @Override
 public boolean next ()
 {
  return super.next ();
 }

 @Override
 public void reopen ()
 {
  super.reopen ();
 }

 @Override
 public void insert ()
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public void setValue (String name, String value)
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

}
