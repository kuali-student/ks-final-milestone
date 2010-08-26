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
package org.kuali.student.dictionary.model;

import org.kuali.student.dictionary.model.CrossObjectConstraint;
import java.util.List;

/**
 * Models a single type state constraint that combines multiple entries into a single
 * constraint statement
 * @author nwright
 */
public class TypeStateCaseConstraint
{

 private List<CrossObjectConstraint> typeStateWhens;

 public TypeStateCaseConstraint (List<CrossObjectConstraint> typeStateWhens)
 {
  this.typeStateWhens = typeStateWhens;
 }

 public List<CrossObjectConstraint> getTypeStateWhens ()
 {
  return typeStateWhens;
 }


 public String getId ()
 {
  StringBuffer buf = new StringBuffer ();
  String separator = "";
  for (CrossObjectConstraint cons: typeStateWhens)
  {
   buf.append (separator);
   separator = ".";
   buf.append (cons.getId ());
  }
  return buf.toString ();
 }

 public String getKey ()
 {
  return getId ();
 }
}
