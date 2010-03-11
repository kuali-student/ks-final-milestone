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

import org.kuali.student.dictionary.model.ServiceMethod;
import java.util.ArrayList;
import java.util.Collection;
import org.kuali.student.dictionary.model.DictionaryModel;
import org.kuali.student.dictionary.model.ServiceContractModel;
import org.kuali.student.dictionary.model.Service;
import org.kuali.student.dictionary.model.ServiceMethodError;
import org.kuali.student.dictionary.model.ServiceMethodParameter;
import org.kuali.student.dictionary.model.util.ModelFinder;

/**
 * This validates a single serviceMethodinoary entry
 * @author nwright
 */
public class ServiceMethodsValidator implements ModelValidator
{

 private ServiceContractModel model;

 public ServiceMethodsValidator (ServiceContractModel model)
 {
  this.model = model;
 }

 private Collection errors;

 @Override
 public Collection<String> validate ()
 {
  errors = new ArrayList ();
  basicValidation ();
  for (ServiceMethod method : model.getServiceMethods ())
  {
   errors.addAll (new ServiceMethodValidator (method, model).validate ());
  }
  return errors;
 }

 private void basicValidation ()
 {
  if (model.getServiceMethods ().size () == 0)
  {
   addError ("no service methods have been defined");
  }
 }

 private void addError (String msg)
 {
  String error = "Error in service methods: " + msg;
  if ( ! errors.contains (error))
  {
   errors.add (error);
  }
 }

}
