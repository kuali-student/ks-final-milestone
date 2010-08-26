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
package org.kuali.student.dictionary.model.util;

/**
 * Matches patterns that define groups of types or groups of states
 * Currently supports comma separated lists and wildcard * at the end
 * @author nwright
 */
public class GroupTypeStatePatternMatcher
{

 private String pattern;

 public GroupTypeStatePatternMatcher (String pattern)
 {
  this.pattern = pattern;
 }

 public boolean matches (String key)
 {
  if (pattern.equals ("*"))
  {
   return true;
  }
  // check for wildcard * at the end
  if (pattern.endsWith ("*"))
  {
   if (key.toLowerCase ().startsWith (pattern.substring (0, pattern.length ()
    - 1).toLowerCase ()))
   {
    return true;
   }
  }
  // Check if key is in a comma separated list of keys
  // changed it so the list could contain a wildard as one of it's elements
  // for example kuali.type.foo.*,kuali.type.bar.joe
  if (pattern.indexOf (",") != -1)
  {
   String[] patterns = pattern.split (",");
   for (String pat : patterns)
   {
    GroupTypeStatePatternMatcher matcher =
     new GroupTypeStatePatternMatcher (pat);
    if (matcher.matches (key))
    {
     return true;
    }
   }
  }
  //ok the pattern is the key so just compare
  if (pattern.equalsIgnoreCase (key))
  {
   return true;
  }
  return false;
 }

}
