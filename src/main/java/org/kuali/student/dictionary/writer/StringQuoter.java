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

/**
 *
 * @author nwright
 */
public class StringQuoter
{

 public static String quote (String value)
 {
  if (value == null)
  {
   return null;
  }
  StringBuffer buf = new StringBuffer (value.length () + 3);
  buf.append ('"');
  boolean lastCharWasEscape = false;
  for (int i = 0; i < value.length (); i ++)
  {
   char c = value.charAt (i);
   switch (c)
   {
    case '"':
     if ( ! lastCharWasEscape)
     {
      buf.append ('\\');
     }
     lastCharWasEscape = false;
     break;
    case '\\':
     if ( ! lastCharWasEscape)
     {
      buf.append ('\\');
     }
     lastCharWasEscape = true;
     break;
    case '\n':
    case '\r':
     buf.append ('"');
     buf.append ("\n");
     buf.append ("\t + ");
     buf.append ('"');
     lastCharWasEscape = false;
     continue;
    default:
     lastCharWasEscape = false;
   }
   buf.append (c);
  }
  buf.append ('"');
  return buf.toString ();
 }

}
