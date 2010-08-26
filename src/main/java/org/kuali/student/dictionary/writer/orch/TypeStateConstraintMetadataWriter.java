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

import org.kuali.student.dictionary.model.TypeStateConstraint;
import org.kuali.student.core.assembly.data.ConstraintMetadata;
import org.kuali.student.dictionary.writer.StringQuoter;

/**
 *
 * @author nwright
 */
public class TypeStateConstraintMetadataWriter
{

 private OrchestrationObjectMetadataWriter out;
 private TypeStateConstraint tsCons;
 private boolean writeIfTypeStateMatcher;
 private String rootPackage;

 public TypeStateConstraintMetadataWriter (OrchestrationObjectMetadataWriter out,
                                           TypeStateConstraint tsCons,
                                           boolean writeIfTypeStateMatcher,
                                           String rootPackage
                                           )
 {
  this.out = out;
  this.tsCons = tsCons;
  this.rootPackage = rootPackage;
  this.writeIfTypeStateMatcher = writeIfTypeStateMatcher;
 }

 public void write ()
 {
  if (writeIfTypeStateMatcher)
  {
   out.indentPrintln ("if (this.matches (type, state, " +
    quote (tsCons.getType ()) + ", " + quote (tsCons.getState ()) + "))");
   out.openBrace ();
  }
  if (tsCons.getConstraint ().isInline ())
  {
   out.indentPrintln ("ConstraintMetadata consMeta = new ConstraintMetadata ();");
   ConstraintMetadata consMeta =
    new ConstraintToConstraintMetadataConverter (tsCons.getConstraint ()).
    convert ();
   new ConstraintMetadataWriter (out, "consMeta", consMeta).write ();
   out.indentPrintln ("childMeta.getConstraints ().add (consMeta);");
  }
  else
  {
   out.importsAdd (rootPackage + "." +
    ConstraintMetadataBankWriter.JAVA_CLASS_NAME);
   String bankGetter = "ConstraintMetadataBank.BANK.get (" + quote (tsCons.getConstraint ().getId ().
    toLowerCase ()) + ")";
   //TODO: don't write out duplicate constraints
   out.indentPrintln ("childMeta.getConstraints ().add (" + bankGetter + ");");
  }
 }

 private String quote (String str)
 {
  return StringQuoter.quote (str);
 }

}
