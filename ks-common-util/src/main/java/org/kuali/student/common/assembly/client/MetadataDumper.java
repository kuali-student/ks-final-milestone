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
package org.kuali.student.common.assembly.client;


import java.io.PrintStream;

/**
 *
 * @author nwright
 */
public class MetadataDumper
{
 private PrintStream out;
 private Metadata meta;
 private String key;
 private int indent;

 public MetadataDumper (PrintStream out, Metadata meta, String key, int indent)
 {
  this.out = out;
  this.meta = meta;
  this.key = key;
  this.indent = indent;
 }

 private void indent ()
 {
  for (int i = 0; i < indent; i++)
  {
   out.print (" ");
  }
 }

 private void indentPrint (String val)
 {
 indent ();
 out.print (val);
 }


 private void indentPrintln (String val)
 {
 indent ();
 out.println (val);
 }

 private void print (String val)
 {
  out.print (val);
 }

 private void println (String val)
 {
  out.println (val);
 }

 public void dump ()
 {
  indentPrint (key);
  print (" (" + meta.getDataType () + "/" + meta.getWriteAccess () + ")");

  MetadataInterrogator interrogator = new MetadataInterrogator (meta);
  if (interrogator.isRepeating ())
  {
   out.print ("\t");
   print ("repeats");
  }
  if (interrogator.isMultilined ())
  {
   out.print ("\t");
   print ("multilined");
  }
    if (interrogator.isRequired ())
  {
   out.print ("\t");
   print ("required");
  }
  out.println ("");
  for (String key : meta.getProperties ().keySet ())
  {
   Metadata child = meta.getProperties ().get (key);
   new MetadataDumper (out, child, key, indent + 1).dump ();
  }
 }
}

