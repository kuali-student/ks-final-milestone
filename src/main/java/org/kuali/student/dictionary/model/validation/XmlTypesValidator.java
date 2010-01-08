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
package org.kuali.student.dictionary.model.validation;

import java.util.ArrayList;
import java.util.Collection;
import org.kuali.student.dictionary.model.DictionaryModel;
import org.kuali.student.dictionary.model.XmlType;
import org.kuali.student.dictionary.model.util.ModelFinder;

/**
 *
 * @author nwright
 */
public class XmlTypesValidator implements ModelValidator
{
 private DictionaryModel model;
 private XmlType xmlType;

 public XmlTypesValidator (XmlType xmlType, DictionaryModel model)
 {
  this.model = model;
  this.xmlType = xmlType;
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
  if (xmlType.getName ().equals (""))
  {
   addError ("Name is required");
  }
  if ( ! xmlType.getService ().equals (""))
  {
   if (new ModelFinder (model).findService (xmlType.getService ()) == null)
   {
     addError ("Service, [" + xmlType.getService ()
      + "] could not be found in the list of services");
   }
  }
 }

 private void addError (String msg)
 {
  String error = "Error in xmlType entry: " + xmlType.getName () + ": " + msg;
  if ( ! errors.contains (error))
  {
   errors.add (error);
  }
 }


}
