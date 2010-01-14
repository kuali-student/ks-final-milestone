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

import org.kuali.student.dictionary.model.ServiceMethodReturnValue;
import org.kuali.student.dictionary.model.ServiceMethod;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This validates a single resultinoary entry
 * @author nwright
 */
public class ServiceMethodReturnValueValidator implements ModelValidator
{

 private ServiceMethodReturnValue returnValue;
 private ServiceMethod serviceMethod;

 public ServiceMethodReturnValueValidator (ServiceMethodReturnValue returnValue,
                                           ServiceMethod serviceMethod)
 {
  this.returnValue = returnValue;
  this.serviceMethod = serviceMethod;
 }

 private Collection errors;

 @Override
 public Collection<String> validate ()
 {
  errors = new ArrayList ();
  basicValidation ();
  return errors;
 }

 private void basicValidation ()
 {
  if (returnValue.getType ().equals (""))
  {
   addError ("return type is required");
  }
  if (returnValue.getDescription ().equals (""))
  {
   addError ("Description is required");
  }
 
 }

 private void addError (String msg)
 {
  String error = "Error in return value for method: " + serviceMethod.getService ()
   + "." + serviceMethod.getName () +  ": " + msg;
  if ( ! errors.contains (error))
  {
   errors.add (error);
  }
 }

}
