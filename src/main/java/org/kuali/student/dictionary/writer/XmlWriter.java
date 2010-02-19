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
package org.kuali.student.dictionary.writer;

import java.io.PrintStream;

/**
 * Base class for all XML writers
 * @author nwright
 */
public class XmlWriter
{

 private char indentChar = '\t';
 private int indent;
 private PrintStream out;

 public XmlWriter ()
 {

 }

 public XmlWriter (PrintStream out, int indent)
 {
  this.indent = indent;
  this.out = out;
 }

 public void setOut (PrintStream out)
 {
  this.out = out;
 }
 public void setIndent (int indent)
 {
  this.indent = indent;
 }
 
 public int getIndent ()
 {
  return indent;
 }

 public void incrementIndent ()
 {
  indent ++;
 }

 public void decrementIndent ()
 {
  indent --;
 }

 public PrintStream getOut ()
 {
  return out;
 }

 public void indent ()
 {
  indent (out, indentChar);
 }

 public void indent (PrintStream o, char indentCharacter)
 {
  for (int i = 0; i < indent; i ++)
  {
   o.print (indentCharacter);
  }
 }

 public void println (String str)
 {
  out.println (str);
 }

 public void indentPrintln (String str)
 {
  indent ();
  out.println (str);
 }

 public void indentPrint (String str)
 {
  indent ();
  out.print (str);
 }

 public void print (String str)
 {
  out.print (str);
 }

 public void writeAttribute (String attribute, String value)
 {
  if (value == null)
  {
   return;
  }
  if (value.equals (""))
  {
   return;
  }
  out.print (" ");
  out.print (attribute);
  out.print ("=\"");
  out.print (value);
  out.print ("\"");
 }

 public void writeTag (String tag, String value)
 {
  if (value == null)
  {
   return;
  }
  if (value.equals (""))
  {
   return;
  }
  indent ();
  out.print ("<" + tag + ">");
  out.print (escapeXML (value));
  out.print ("</" + tag + ">");
  out.println ("");
 }

 public void writeComment (String comment)
 {
  if (comment == null)
  {
   return;
  }
  if (comment.equals (""))
  {
   return;
  }
  indent ();
  out.print ("<!-- ");
  out.print (comment);
  out.println (" -->");
 }

 public String escapeXML (String s)
 {
  StringBuffer sb = new StringBuffer ();
  int n = s.length ();
  for (int i = 0; i < n; i ++)
  {
   // http://www.hdfgroup.org/HDF5/XML/xml_escape_chars.htm
   char c = s.charAt (i);
   switch (c)
   {
    case '"':
     sb.append ("&quot;");
     break;
    case '\'':
     sb.append ("&apos;");
     break;
    case '&':
     sb.append ("&amp;");
     break;
    case '<':
     sb.append ("&lt;");
     break;
    case '>':
     sb.append ("&gt;");
     break;


    //case ' ': sb.append("&nbsp;");break;

    default:
     sb.append (c);
     break;
   }
  }
  return sb.toString ();
 }


 public void writeCommentBox (String comment)
 {
  String border = "***************************************************************";
  while (border.length () < comment.length ())
  {
   border = border + border;
  }
  border = border.substring (0, comment.length ());
  writeComment (border);
  writeComment (comment);
  writeComment (border);
 }
}
