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

import java.util.ArrayList;
import java.util.List;

/**
 * Create a regular expression pattern matching any positive number that
 * is less than or equal to the given max number.
 * Based on http://www.bytemycode.com/snippets/snippet/661/
 * @author nwright
 */
public class MaximumNumberRegularExpressionGenerator
{

 public String generate (int maxNum)
 {
  String maxStr = "" + maxNum;

  String fixedNumbers = "";
  List<String> groups = new ArrayList ();
  // allow zero
  groups.add ("(0*)");
  // allow any number with fewer digits than the max
  for (int i = 1; i < maxStr.length (); i ++)
  {
   String group = "";
   for (int j = 0; j < i; j++)
   {
    group += "\\d";
   }
   if (i > 1)
   {
    group = "(" + group + ")";
   }
   groups.add (group);
  }

  // Iterate over the digits in the max number and produce a group
  //from each digit that represents all numbers up to and not including
  // the number the current digit represents
  for (int i = 0; i < maxStr.length (); i ++)
  {
   String digit = maxStr.substring (i, i + 1);

   if (i >= 1)
   {
    fixedNumbers += maxStr.substring (i - 1, i);
   }
   // the rest are zeros so there are no more groups to produce
   if (Integer.parseInt (maxStr.substring (i)) == 0)
   {
    break;
   }
   if ( ! digit.equals ("0"))
   {
    String numericSet = "0";
    int lastDigit = Integer.parseInt (digit) - 1;
    if (lastDigit != 0)
    {
     numericSet += "-" + lastDigit;
    }
    if (numericSet.length () > 1)
    {
     numericSet = "[" + numericSet + "]";
    }
    int numberOfFullNumbers = maxStr.length () - i - 1;
    String regExGroup = "" + fixedNumbers + numericSet;
    for (int j = 0; j < numberOfFullNumbers; j ++)
    {
     regExGroup += "\\d";
    }

    boolean justTheRange = false;
    if (fixedNumbers.length () == 0 && numberOfFullNumbers == 0)
    {
     justTheRange = true;
    }
    if (regExGroup.length () > 1 &&  ! justTheRange)
    {
     regExGroup = "(" + regExGroup + ")";
    }
    groups.add (regExGroup);
   }
  }
  if (maxStr.length () > 1)
  {
   maxStr = "(" + maxStr + ")";
  }
  groups.add (maxStr);
  StringBuffer result = new StringBuffer ();
  String joiner = "";
  for (String group : groups)
  {
   result.append (joiner);
   result.append (group);
   joiner = "|";
  }
  return result.toString ();
 }

}
