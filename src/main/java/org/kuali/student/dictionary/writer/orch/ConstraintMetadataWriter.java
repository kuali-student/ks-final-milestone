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

//import org.kuali.student.core.assembly.data.ConstraintMetadata;
import org.kuali.student.dictionary.writer.JavaClassWriter;
import org.kuali.student.dictionary.writer.StringQuoter;

/**
 *
 * @author nwright
 */
public class ConstraintMetadataWriter
{

 private JavaClassWriter out;
 private String name;
// private ConstraintMetadata meta;

// public ConstraintMetadataWriter (JavaClassWriter out,
//                                  String name,
//                                  ConstraintMetadata meta)
// {
//  this.out = out;
//  this.name = name;
//  this.meta = meta;
// }

 public void write ()
 {
//  out.importsAdd (ConstraintMetadata.class.getName ());
//  writeSetValue ("Id", meta.getId ());
//  writeSetValue ("MessageId", meta.getMessageId ());
//  writeSetValue ("Desc", meta.getDesc ());
//  writeSetValue ("Comments", meta.getComments ());
//  writeSetValue ("MinLength", meta.getMinLength ());
//  writeSetValue ("MaxLength", meta.getMaxLength ());
//  writeSetValue ("MinOccurs", meta.getMinOccurs ());
//  writeSetValue ("MaxOccurs", meta.getMaxOccurs ());
//  writeSetValue ("MinValue", meta.getMinValue ());
//  writeSetValue ("MaxValue", meta.getMaxValue ());
//  writeSetValue ("ServerSide", meta.getServerSide ());
//  writeSetValue ("SpecialValidator", meta.getSpecialValidator ());
//  writeSetValue ("ValidChars", meta.getValidChars ());
// }
//
// private void writeSetValue (String property, Integer value)
// {
//  if (value == null)
//  {
//   return;
//  }
//  writeSetValueInternal (property, value);
// }
//
// private void writeSetValue (String property, Boolean value)
// {
//  if (value == null)
//  {
//   return;
//  }
//  // don't write if false because that is the default
//  if ( ! value)
//  {
//   return;
//  }
//  writeSetValueInternal (property, value);
// }
//
// private void writeSetValue (String property, String value)
// {
//  if (value == null)
//  {
//   return;
//  }
//  writeSetValueInternal (property, quote (value));
// }
//
// private void writeSetValueInternal (String property, Object value)
// {
//  if (value == null)
//  {
//   return;
//  }
//  out.indentPrintln (name + ".set" + property + " (" + value.toString () + ");");
// }
//
// private String quote (String str)
// {
//  return StringQuoter.quote (str);
 }

}
