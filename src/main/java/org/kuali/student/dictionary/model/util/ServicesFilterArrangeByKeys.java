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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.kuali.student.dictionary.model.Service;

/**
 *
 * @author nwright
 */
public class ServicesFilterArrangeByKeys implements ServicesFilter
{

 List<String> keys;

 public ServicesFilterArrangeByKeys (List<String> keys)
 {
  this.keys = keys;
  for (int i = 0; i < keys.size (); i ++)
  {
   keys.set (i, keys.get (i).toLowerCase ());
  }
 }

 @Override
 public List<Service> filter (List<Service> services)
 {
  List<Service> sorted = new ArrayList (services);
  Collections.sort (sorted, new CompareService ());
  return sorted;
 }

 public class CompareService implements Comparator<Service>
 {

  public int compare (Service service1, Service service2)
  {
   int index1 = keys.indexOf (service1.getKey ().toLowerCase ());
   int index2 = keys.indexOf (service2.getKey ().toLowerCase ());
   if (index1 < index2)
   {
    return -1;
   }
   if (index2 < index1)
   {
    return +1;
   }
   return 0;
  }

 }
}
