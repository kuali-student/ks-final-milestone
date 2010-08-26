/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.dictionary.model.spreadsheet;

/**
 *
 * @author nwright
 */
public class WorksheetNotFoundException extends Exception
{

 /**
  * Creates a new instance of <code>WorksheetNotFoundException</code> without detail message.
  */
 public WorksheetNotFoundException ()
 {
 }

 /**
  * Constructs an instance of <code>WorksheetNotFoundException</code> with the specified detail message.
  * @param msg the detail message.
  */
 public WorksheetNotFoundException (String msg)
 {
  super (msg);
 }

}
