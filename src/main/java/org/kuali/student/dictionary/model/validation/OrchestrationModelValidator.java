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

import org.kuali.student.dictionary.model.util.ModelFinder;
import org.kuali.student.dictionary.model.OrchObj;
import org.kuali.student.dictionary.model.DictionaryModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Validates the entire spreadsheet model
 * @author nwright
 */
public class OrchestrationModelValidator implements ModelValidator
{

 private DictionaryModel model;
 private ModelFinder finder;

 public OrchestrationModelValidator (DictionaryModel model)
 {
  this.model = model;
  this.finder = new ModelFinder (model);
 }

 List<String> errors;

 @Override
 public Collection<String> validate ()
 {
  errors = new ArrayList ();
  validateOrchObjs ();
  errors.addAll (new DictionaryModelValidator (model).validate ());
  return errors;
 }

 private void validateOrchObjs ()
 {
  if (model.getOrchObjs ().size () == 0)
  {
   addError ("No orchestration objects found");
  }
  for (OrchObj orch : model.getOrchObjs ())
  {
   OrchObjValidator cv = new OrchObjValidator (orch);
   errors.addAll (cv.validate ());
  }
 }

 private void addError (String msg)
 {
  String error = "Error in orchestration dictionary spreadsheet: " + msg;
  if ( ! errors.contains (error))
  {
   errors.add (error);
  }
 }

}
