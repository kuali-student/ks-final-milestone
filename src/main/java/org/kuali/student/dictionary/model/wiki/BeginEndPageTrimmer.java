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
package org.kuali.student.dictionary.model.wiki;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import org.kuali.student.dictionary.DictionaryExecutionException;

/**
 *
 * @author nwright
 */
public class BeginEndPageTrimmer implements PageTrimmer
{

 private String startString;
 private String endString;

 public BeginEndPageTrimmer (String startString, String endString)
 {
  this.startString = startString;
  this.endString = endString;
 }

 @Override
 public InputStream trim (InputStream in)
 {
  BufferedReader reader =
   new BufferedReader (new InputStreamReader (in));
  StringBuilder builder = new StringBuilder ();
  String line;
  boolean foundStart = false;
  boolean foundEnd = false;
  boolean inContract = false;
  try
  {
   int lineno = 0;
   int linesAppended = 0;
   while ((line = reader.readLine ()) != null)
   {
    lineno ++;
    //System.out.println (lineno + ":" + line);
    if ( ! inContract)
    {
     if (line.contains (startString))
     {
      foundStart = true;
      inContract = true;
      builder.append (line);
      builder.append ("\n");
      System.out.println ("found startString=[" + startString + "] on line " +
       lineno);
      linesAppended ++;
     }
    }
    else
    {
     if (line.contains (endString))
     {
      foundEnd = true;
      inContract = false;
      builder.append (line);
      builder.append ("\n");
      linesAppended ++;
      System.out.println ("found endString=[" + endString + "] on line " +
       lineno);
     }
     else
     {
      builder.append (line);
      builder.append ("\n");
      linesAppended ++;
     }
    }
   }
  }
  catch (IOException ex)
  {
   throw new DictionaryExecutionException (ex);
  }
  if ( ! foundStart)
  {
   throw new DictionaryExecutionException ("Did not find start string [" +
    startString + "] in input");
  }
  if ( ! foundEnd)
  {
   throw new DictionaryExecutionException ("Did not find end string [" +
    endString + "] in input");
  }
  String text = builder.toString ();
  return new ByteArrayInputStream (text.getBytes ());
 }

}
