/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp.impl.swing;

import javax.swing.JOptionPane;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.kuali.student.comp.infc.KSUnrecoverableExceptionHandler;

/**
 *
 * @author nwright
 */
public class SwingUnrecoverableExceptionHandler implements
 KSUnrecoverableExceptionHandler
{

 @Override
 public void handle (String source, Exception ex)
 {
  Logger.getLogger (source).log (Level.FATAL, null, ex);
  JOptionPane.showMessageDialog (null,
                                 ex,
                                 "Unrecoverable Error in " + source,
                                 JOptionPane.ERROR_MESSAGE);
  System.exit (99);
 }

}
