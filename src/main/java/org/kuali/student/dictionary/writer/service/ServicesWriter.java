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
package org.kuali.student.dictionary.writer.service;

import java.util.Collection;
import java.util.List;
import org.kuali.student.dictionary.model.DictionaryModel;
import org.kuali.student.dictionary.model.Service;
import org.kuali.student.dictionary.model.util.ServicesFilter;
import org.kuali.student.dictionary.model.validation.DictionaryValidationException;
import org.kuali.student.dictionary.model.validation.ServiceMethodsValidator;

/**
 *
 * @author nwright
 */
public class ServicesWriter
{

 private DictionaryModel model;
 private String directory;
 private String rootPackage;
 public static final String DEFAULT_ROOT_PACKAGE = "org.kuali.student.service";
 private ServicesFilter filter;

 public ServicesWriter (DictionaryModel model,
                        String directory,
                        String rootPackage,
                        ServicesFilter filter)
 {
  this.model = model;
  this.directory = directory;
  this.rootPackage = rootPackage;
  this.filter = filter;
 }

 /**
  * Write out the entire file
  * @param out
  */
 public void write ()
 {
  this.validate ();

 for (Service service : filterServices ())
  {
   new ServiceWriter (model, directory, rootPackage, service).write ();
  }

 }

 private List<Service> filterServices ()
 {
  if (filter == null)
  {
   return model.getServices ();
  }
  return filter.filter (model.getServices ());
 }


 private void validate ()
 {
  Collection<String> errors =
   new ServiceMethodsValidator (model).validate ();
  if (errors.size () > 0)
  {
   StringBuffer buf = new StringBuffer ();
   buf.append (errors.size () +
    " errors found while validating the data.");
   int cnt = 0;
   for (String msg : errors)
   {
    cnt ++;
    buf.append ("\n");
    buf.append ("*error*" + cnt + ":" + msg);
   }

   throw new DictionaryValidationException (buf.toString ());
  }
 }

}
