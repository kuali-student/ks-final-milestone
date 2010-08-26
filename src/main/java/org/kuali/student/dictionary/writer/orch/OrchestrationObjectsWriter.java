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
package org.kuali.student.dictionary.writer.orch;

import org.kuali.student.dictionary.model.impl.OrchestrationObjectsLoader;
import org.kuali.student.dictionary.model.OrchestrationObject;
import org.kuali.student.dictionary.model.validation.DictionaryValidationException;
import org.kuali.student.dictionary.model.validation.OrchestrationModelValidator;
import org.kuali.student.dictionary.model.DictionaryModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Map;
import org.kuali.student.dictionary.model.OrchestrationModel;

/**
 *
 * @author nwright
 */
public class OrchestrationObjectsWriter
{

 private DictionaryModel model;
 private String directory;
 private String rootPackage;

 public OrchestrationObjectsWriter (DictionaryModel model, String directory,
                                    String rootPackage)
 {
  this.model = model;
  this.directory = directory;
  this.rootPackage = rootPackage;
 }

 private OrchestrationModel getOrchestrationModel ()
 {
  return new OrchestrationObjectsLoader (model, rootPackage);
 }

 /**
  * Write out the entire file
  * @param out
  */
 public void write ()
 {
  this.validate ();

  // first do from message structures
  Map<String, OrchestrationObject> orchObjs = getOrchestrationModel ().
   getOrchestrationObjects ();

  // do the helpers first
  for (OrchestrationObject oo : orchObjs.values ())
  {
   System.out.println ("Writing out helper class: " + oo.
    getFullyQualifiedJavaClassHelperName ());
   OrchestrationObjectHelperWriter writer =
    new OrchestrationObjectHelperWriter (model, directory, orchObjs, oo);
   writer.write ();
  }

  // do the helpers first
  for (OrchestrationObject oo : orchObjs.values ())
  {
   System.out.println ("Writing out constants: " + oo.
    getFullyQualifiedJavaClassConstantsName ());
   OrchestrationObjectConstantsWriter writer =
    new OrchestrationObjectConstantsWriter (model, directory, orchObjs, oo);
   writer.write ();
  }


  // do the bank of constraints next
  new ConstraintMetadataBankWriter (model, directory, rootPackage).write ();

  // do the bank of constraints next
  new LookupMetadataBankWriter (model, getOrchestrationModel (), directory, rootPackage).
   write ();

  // do the metadata next
  for (OrchestrationObject oo : orchObjs.values ())
  {
   System.out.println ("Writing out metadata class: " + oo.
    getFullyQualifiedJavaClassMetadataName ());
   OrchestrationObjectMetadataWriter writer =
    new OrchestrationObjectMetadataWriter (model, directory, rootPackage, orchObjs, oo);
   writer.write ();
  }


//  // do the assemblers next
//  for (OrchestrationObject oo : orchObjs.values ())
//  {
//   if (oo.getSource ().equals (OrchestrationObject.Source.MESSAGE_STRUCTURE))
//   {
//    System.out.println ("Writing out " + oo.getFullyQualifiedJavaClassAssemblerName ());
//    OrchestrationObjectAssemblerWriter writer =
//     new OrchestrationObjectAssemblerWriter (model, directory, orchObjs, oo);
//    writer.write ();
//   }
//  }
 }

 private void validate ()
 {
  Collection<String> errors =
   new OrchestrationModelValidator (model).validate ();
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

 }

}
