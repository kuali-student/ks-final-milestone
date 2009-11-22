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
package org.kuali.student.dictionary;

import org.kuali.student.common.assembly.client.ConstraintMetadata;

/**
 *
 * @author nwright
 */
public class TypeStateConstraintMetadataWriter
{

 private OrchestrationObjectMetadataWriter out;
 private TypeStateConstraint tsCons;
 private boolean writeIfTypeStateMatcher;

 public TypeStateConstraintMetadataWriter (OrchestrationObjectMetadataWriter out,
                                           TypeStateConstraint tsCons,
                                           boolean writeIfTypeStateMatcher
                                           )
 {
  this.out = out;
  this.tsCons = tsCons;
  this.writeIfTypeStateMatcher = writeIfTypeStateMatcher;
 }

 public void write ()
 {
  //out.imports.add (TypeStateMatcher.class.getName ());
  //TODO: replace below with above once common util makes it to snapshot
  if (writeIfTypeStateMatcher)
  {
   out.imports.add ("org.kuali.student.common.assembly.TypeStateMatcher");
   out.indentPrintln ("if (TypeStateMatcher.matches (type, state, " +
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
   out.imports.add (ConstraintMetadataBankWriter.ROOT_PACKAGE + "." +
    ConstraintMetadataBankWriter.JAVA_CLASS_NAME);
   String bankGetter = "ConstraintMetadataBank.BANK.get (" + quote (tsCons.getConstraint ().getId ().
    toLowerCase ()) + ")";
   out.indentPrintln ("childMeta.getConstraints ().add (" + bankGetter + ");");
  }
 }

 private String quote (String str)
 {
  return StringQuoter.quote (str);
 }

}
