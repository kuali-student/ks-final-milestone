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
package org.kuali.student.dictionary.model.validation;

import org.kuali.student.dictionary.model.OrchObj;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This validates a constraint.
 * @author nwright
 */
public class OrchObjValidator implements ModelValidator
{

 private OrchObj orch;

 public OrchObjValidator (OrchObj orch)
 {
  this.orch = orch;
 }

 private Collection errors;

 @Override
 public Collection<String> validate ()
 {
  this.errors = new ArrayList ();
  if (orch.getXmlType ().equals (""))
  {
    this.addError ("XML Type is required");
  }
  // TODO: more validation
  return this.errors;
 }

  private void addError (String msg)
 {
  String key = orch.getId ();
  if (key.equals (""))
  {
   key = "";
  }
  String error = "Error in: " + key + ": " + msg;
  if ( ! errors.contains (error))
  {
   errors.add (error);
  }
 }

}
