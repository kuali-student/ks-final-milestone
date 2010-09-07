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

import java.text.SimpleDateFormat;
//import org.kuali.student.core.assembly.data.Data;
//import org.kuali.student.core.assembly.data.LookupResultMetadata;
//import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.dictionary.writer.JavaClassWriter;
import org.kuali.student.dictionary.writer.StringQuoter;

/**
 *
 * @author nwright
 */
public class LookupResultMetadataWriter
{

 private JavaClassWriter out;
 private String name;
// private LookupResultMetadata paramMeta;

// public LookupResultMetadataWriter (JavaClassWriter out,
//                                   String name,
//                                   LookupResultMetadata paramMeta)
// {
//  this.out = out;
//  this.name = name;
////  this.paramMeta = paramMeta;
// }

 public void write ()
 {
//  out.importsAdd (LookupResultMetadata.class.getName ());
//  writeSetValue ("Key", paramMeta.getKey ());
//  writeSetValue ("Name", paramMeta.getName ());
//  writeSetValue ("Desc", paramMeta.getDesc ());
//  writeSetValue ("DataType", paramMeta.getDataType ());
//  writeSetValue ("Hidden", paramMeta.isHidden ());
 }

// private void writeSetValue (String property, Data.DataType value)
// {
//  out.importsAdd (Data.class.getName ());
//  if (value == null)
//  {
//   return;
//  }
//  writeSetValueInternal (property, "Data.DataType." + value);
// }

// private void writeSetValue (String property, Metadata.WriteAccess value)
// {
//  out.importsAdd (Metadata.class.getName ());
//  if (value == null)
//  {
//   return;
//  }
//  writeSetValueInternal (property, "Metadata.WriteAccess." + value);
// }
//
// private void writeSetValue (String property, Data.Value value)
// {
//  out.importsAdd (Data.class.getName ());
//  if (value == null)
//  {
//   return;
//  }
//  if (value instanceof Data.StringValue)
//  {
//   writeSetValueInternal (property, "new Data.StringValue (" + quote (value.toString ()) + ")");
//   return;
//  }
//  if (value instanceof Data.DateValue)
//  {
//   Data.DateValue dateValue = (Data.DateValue) value;
//   String strVal = new SimpleDateFormat ("yyyy-MM-dd").format (dateValue.get ());
//   writeSetValueInternal (property, "new Data.DateValue (asDate (" + quote (strVal) + "))");
//   return;
//  }
//  if (value instanceof Data.IntegerValue)
//  {
//   writeSetValueInternal (property, "new Data.IntegerValue (" + Integer.parseInt (value.toString ()) + ")");
//   return;
//  }
//  if (value instanceof Data.BooleanValue)
//  {
//   writeSetValueInternal (property, "new Data.BooleanValue (" + Boolean.parseBoolean (value.toString ()) + ")");
//   return;
//  }
//
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
