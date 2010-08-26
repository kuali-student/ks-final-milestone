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

import org.kuali.student.dictionary.model.OrchestrationObject;
import org.kuali.student.dictionary.model.OrchestrationObjectField;
import org.kuali.student.dictionary.model.DictionaryModel;
import java.util.Map;
import org.kuali.student.dictionary.writer.JavaClassWriter;
import org.kuali.student.dictionary.writer.JavaEnumConstantCalculator;

/**
 *
 * @author nwright
 */
public class OrchestrationObjectConstantsWriter extends JavaClassWriter
{

 private DictionaryModel model;
 private String directory;
 public static final String ROOT_PACKAGE = "org.kuali.student.orchestration";
 private OrchestrationObject orchObj;
 private Map<String, OrchestrationObject> orchObjs;

 public OrchestrationObjectConstantsWriter (DictionaryModel model,
                                            String directory,
                                            Map<String, OrchestrationObject> orchObjs,
                                            OrchestrationObject orchObj)
 {
  super (directory, orchObj.getOrchestrationPackagePath (), orchObj.
   getJavaClassConstantsName ());
  this.model = model;
  this.directory = directory;
  this.orchObjs = orchObjs;
  this.orchObj = orchObj;
 }

 /**
  * Write out the entire file
  * @param out
  */
 public void write ()
 {
  // recursively call any in-line orchestration objects
  for (OrchestrationObjectField field : orchObj.getFields ())
  {
   if (field.getInlineObject () != null)
   {
    OrchestrationObjectConstantsWriter inlineWriter =
     new OrchestrationObjectConstantsWriter (model,
                                             directory,
                                             orchObjs,
                                             field.getInlineObject ());
    inlineWriter.write ();
   }
  }

  indentPrintln ("public interface " + orchObj.getJavaClassConstantsName ());
  openBrace ();

  for (OrchestrationObjectField field : orchObj.getFields ())
  {
   String constant = new JavaEnumConstantCalculator (field.getName ()).calc ();
   indentPrintln ("public static final String " + constant + " = \"" + field.
    getPropertyName () + "\";");
  }

  closeBrace (); // end class

  this.writeJavaClassAndImportsOutToFile ();
  this.getOut ().close ();
 }

}
