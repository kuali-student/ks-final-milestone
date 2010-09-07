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

import org.kuali.student.dictionary.model.DictionaryModel;
import org.kuali.student.dictionary.model.Constraint;
import java.util.HashMap;
import java.util.Map;
//import org.kuali.student.core.assembly.data.ConstraintMetadata;
import org.kuali.student.dictionary.writer.JavaClassWriter;

/**
 *
 * @author nwright
 */
public class ConstraintMetadataBankWriter extends JavaClassWriter
{

 private DictionaryModel model;
 private String directory;
 private String rootPackage;
 public static final String JAVA_CLASS_NAME = "ConstraintMetadataBank";

 public ConstraintMetadataBankWriter (DictionaryModel model,
                                      String directory,
                                      String rootPackage)
 {
  super (directory, rootPackage, JAVA_CLASS_NAME);
  this.model = model;
  this.directory = directory;
  this.rootPackage = rootPackage;
 }

 /**
  * Write out the entire file
  * @param out
  */
 public void write ()
 {
  indentPrintln ("public class " + JAVA_CLASS_NAME);
  openBrace ();

  importsAdd (Map.class.getName ());
  importsAdd (HashMap.class.getName ());
//  importsAdd (ConstraintMetadata.class.getName ());
  indentPrintln ("public static final Map <String, ConstraintMetadata> BANK = new HashMap ();");
  indentPrintln ("// static initiliazer");
  indentPrintln ("static");
  openBrace ();
  indentPrintln ("ConstraintMetadata consMeta = null;");
  for (Constraint cons : model.getConstraints ())
  {
   indentPrintln ("");
   indentPrintln ("consMeta = new ConstraintMetadata ();");
//   ConstraintMetadata consMeta =
//    new ConstraintToConstraintMetadataConverter (cons).convert ();
//   new ConstraintMetadataWriter (this, "consMeta", consMeta).write ();
   indentPrintln ("BANK.put (consMeta.getId ().toLowerCase (), consMeta);");
  }
  closeBrace (); // end getMetadata method

  closeBrace (); // end class

  this.writeJavaClassAndImportsOutToFile ();
  this.getOut ().close ();
 }

}

