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

import org.kuali.student.dictionary.model.ServiceMethodError;
import org.kuali.student.dictionary.model.ServiceMethod;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This validates a single criteriainoary entry
 * @author nwright
 */
public class ServiceMethodErrorValidator implements ModelValidator
{

 private ServiceMethodError error;
 private ServiceMethod method;

 public ServiceMethodErrorValidator (ServiceMethodError error,
                               ServiceMethod method)
 {
  this.error = error;
  this.method = method;
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
  if (error.getDescription ().equals (""))
  {
   addError ("Description is required");
  }
  if (error.getType ().equals (""))
  {
   addError ("Type is required");
  }
 }

 private void addError (String msg)
 {
  String error = "Error in error " + method.getService () + "." +
   method.getName () +
   ": " + msg;
  if ( ! errors.contains (error))
  {
   errors.add (error);
  }
 }

}
