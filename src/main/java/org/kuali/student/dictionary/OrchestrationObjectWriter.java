/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.dictionary;

import com.sun.codemodel.ClassType;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import java.util.Collection;


/**
 *
 * @author nwright
 */
public class OrchestrationObjectWriter
{

 private DictionaryModel sheet;
 private String directory;
 private ModelFinder finder;

 public OrchestrationObjectWriter (DictionaryModel sheet, String directory)
 {
  this.sheet = sheet;
  this.directory = directory;
 }

 /**
  * Write out the entire file
  * @param out
  */
 public void write () throws JClassAlreadyExistsException
 {
  Collection<String> errors =
   new OrchestrationModelValidator (sheet).validate ();
  if (errors.size () > 0)
  {
   StringBuffer buf = new StringBuffer ();
   buf.append (errors.size () +
    " errors found while validating the spreadsheet.");
   int cnt = 0;
   for (String msg : errors)
   {
    cnt ++;
    buf.append ("\n");
    buf.append ("*error*" + cnt + ":" + msg);
   }
   throw new DictionaryValidationException (buf.toString ());
  }

  this.finder = new ModelFinder (sheet);


  JCodeModel jcm = new JCodeModel ();
  for (OrchObj orch : sheet.getOrchObjs ())
  {
   JDefinedClass jdc = jcm._class (directory + "." + orch.getParent () + "Data", ClassType.CLASS);
  }
 }


}
