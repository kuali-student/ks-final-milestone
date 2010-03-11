/*
 * Copyright 2010 The Kuali Foundation
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
public class JavaEnumConstantCalculator
{

 private String name;

 public JavaEnumConstantCalculator (String name)
 {
  this.name = name;
 }

 public String calc ()
 {
  StringBuilder buf = new StringBuilder (name.length () + 3);
  // do the first character so we don't prepend the first with a _ if it is upper
  char c = Character.toUpperCase (name.charAt (0));
  buf.append (c);
  boolean lastUpper = Character.isUpperCase (c);
  for (int i = 1; i < name.length (); i ++)
  {
   c = name.charAt (i);
   if (Character.isUpperCase (c))
   {
    if ( ! lastUpper)
    {
     buf.append ('_');
    }
    lastUpper = true;
   }
   else
   {
    lastUpper = false;
   }

   buf.append (Character.toUpperCase (c));
  }

  return buf.toString ();
 }

 public String reverse ()
 {
  StringBuffer buf = new StringBuffer (name.length ());
  boolean uppercase = true;
  for (int i = 0; i < name.length (); i ++)
  {
   char c = name.charAt (i);
   if (uppercase)
   {
    c = Character.toUpperCase (c);
    uppercase = false;
   }
   else
   {
    c = Character.toLowerCase (c);
   }
   if (c == '_')
   {
    uppercase = true;
    continue;
   }
   buf.append (c);
  }

  return buf.toString ();
 }

}
