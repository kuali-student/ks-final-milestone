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

import org.kuali.student.common.assembly.client.LookupImplMetadata;
import org.kuali.student.common.assembly.client.LookupMetadata;
import org.kuali.student.common.assembly.client.LookupParamMetadata;
import org.kuali.student.common.assembly.client.LookupResultMetadata;

/**
 *
 * @author nwright
 */
public class LookupMetadataWriter
{

 private JavaClassWriter out;
 private String name;
 private LookupMetadata lookupMeta;

 public LookupMetadataWriter (JavaClassWriter out,
                              String name,
                              LookupMetadata lookupMeta)
 {
  this.out = out;
  this.name = name;
  this.lookupMeta = lookupMeta;
 }

 public void write ()
 {
  out.imports.add (LookupMetadata.class.getName ());
  writeSetValue ("LookupKey", lookupMeta.getLookupKey ());
  writeSetValue ("Key", lookupMeta.getKey ());
  writeSetValue ("Name", lookupMeta.getName ());
  writeSetValue ("Desc", lookupMeta.getDesc ());
  writeSetValue ("ResultReturnKey", lookupMeta.getResultReturnKey ());

  out.imports.add (LookupImplMetadata.class.getName ());
  out.indentPrintln ("");
  out.indentPrintln ("impl = new LookupImplMetadata ();");
  new LookupImplMetadataWriter (out, "impl", lookupMeta.getImpl ()).write ();
  out.indentPrintln ("lookup.setImpl (impl);");
  
  out.imports.add (LookupParamMetadata.class.getName ());
  for (LookupParamMetadata paramMeta : lookupMeta.getParams ())
  {
   out.indentPrintln ("");
   out.indentPrintln ("param = new LookupParamMetadata ();");
   new LookupParamMetadataWriter (out, "param", paramMeta).write ();
   out.indentPrintln ("lookup.getParams ().add (param);");
  }
  for (LookupResultMetadata resultMeta : lookupMeta.getResults ())
  {
   out.indentPrintln ("");
   out.indentPrintln ("result = new LookupResultMetadata ();");
   new LookupResultMetadataWriter (out, "result", resultMeta).write ();
   out.indentPrintln ("lookup.getResults ().add (result);");
  }
 }

 private void writeSetValue (String property, Integer value)
 {
  if (value == null)
  {
   return;
  }
  writeSetValueInternal (property, value);
 }

 private void writeSetValue (String property, Boolean value)
 {
  if (value == null)
  {
   return;
  }
  // don't write if false because that is the default
  if ( ! value)
  {
   return;
  }
  writeSetValueInternal (property, value);
 }

 private void writeSetValue (String property, String value)
 {
  if (value == null)
  {
   return;
  }
  writeSetValueInternal (property, quote (value));
 }

 private void writeSetValueInternal (String property, Object value)
 {
  if (value == null)
  {
   return;
  }
  out.indentPrintln (name + ".set" + property + " (" + value.toString () + ");");
 }

 private String quote (String str)
 {
  return StringQuoter.quote (str);
 }

}
