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
package org.kuali.student.dictionary.model.util;

import java.util.ArrayList;
import java.util.List;
import org.kuali.student.dictionary.model.Service;

/**
 *
 * @author nwright
 */
public class ServicesFilterLatestVersionOnly implements ServicesFilter
{

 @Override
 public List<Service> filter (List<Service> services)
 {
   List<Service> list = new ArrayList ();
  for (Service target : services)
  {
   int i = findService (list, target.getKey ());
   if (i == -1)
   {
    list.add (target);
    continue;
   }
   Service source = list.get (i);
   if (compare (source.getVersion (), target.getVersion ()) < 0)
   {
    list.set (i, target);
   }
  }
  return list;
 }

 protected int compare (String source, String target)
 {
  if (source.equalsIgnoreCase (target))
  {
   return 0;
  }
  if (target.equalsIgnoreCase ("dev"))
  {
   return  + 1;
  }
  return source.compareToIgnoreCase (target);
 }

 private int findService (List<Service> list, String key)
 {
  for (int i = 0; i < list.size (); i ++)
  {
   Service service = list.get (i);
   if (service.getKey ().equals (key))
   {
    return i;
   }
  }
  return -1;
 }

}
