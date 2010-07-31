/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.lum.lu.assembly.data.client.refactorme;


import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author nwright
 */
public class RecursionCounter
{

 private Map<String, Integer> recursions = new HashMap ();

 public Integer set (String key, int recursionsLeft)
 {
  return recursions.put (key, recursionsLeft);
 }



 public int increment (String key)
 {
  Integer recursionsLeft = recursions.get (key);
  if (recursionsLeft == null)
  {
   return 1;
  }
  recursionsLeft ++;
  recursions.put (key, recursionsLeft.intValue ());
  return recursionsLeft;
 }

 public int decrement (String key)
 {
  Integer recursionsLeft = recursions.get (key);
  if (recursionsLeft == null)
  {
   return 0;
  }
  recursionsLeft --;
  recursions.put (key, recursionsLeft.intValue ());
  return recursionsLeft;
 }

}
