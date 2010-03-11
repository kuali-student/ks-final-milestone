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
package org.kuali.student.dictionary.writer.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.kuali.student.dictionary.model.DictionaryModel;
import org.kuali.student.dictionary.model.MessageStructure;
import org.kuali.student.dictionary.model.Service;
import org.kuali.student.dictionary.model.ServiceMethod;
import org.kuali.student.dictionary.model.ServiceMethodError;
import org.kuali.student.dictionary.model.ServiceMethodParameter;
import org.kuali.student.dictionary.model.ServiceMethodReturnValue;
import org.kuali.student.dictionary.model.XmlType;
import org.kuali.student.dictionary.model.util.ModelFinder;
import org.kuali.student.dictionary.model.validation.DictionaryValidationException;

/**
 *
 * @author nwright
 */
public class ServiceWriter
{

 private DictionaryModel model;
 private String directory;
 private String rootPackage;
 private Service service;

 public ServiceWriter (DictionaryModel model,
                       String directory,
                       String rootPackage,
                       Service service)
 {
  this.model = model;
  this.directory = directory;
  this.rootPackage = rootPackage;
  this.service = service;
 }

 /**
  * Write out the entire file
  * @param out
  */
 public void write ()
 {
  List<ServiceMethod> methods = new ModelFinder (model).
   getServiceMethodsInService (service.getKey ());
  if (methods.size () == 0)
  {
   System.out.println ("No methods defined for service: " + service.getKey ());
   return;
  }

  // the main service
  System.out.println ("Generating services API's for " + service.getKey ());
  new ServiceMethodsWriter (model, directory, rootPackage, service, methods).write ();

  // the beans's
  System.out.println ("Generating info interfaces");
  for (XmlType xmlType : getXmlTypesUsedByService (methods))
  {
   System.out.println ("Generating Beans for " + xmlType.getName ());
   new ServiceBeanWriter (model, directory, rootPackage, service, xmlType).write ();
  }

    // the Info interfaces's
  System.out.println ("Generating Info interfaces");
  for (XmlType xmlType : getXmlTypesUsedByService (methods))
  {
   System.out.println ("Generating info interface for " + xmlType.getName ());
   new ServiceInfoWriter (model, directory, rootPackage, service, xmlType).write ();
  }


  // exceptions
  for (ServiceMethodError error : getServiceMethodErrors (methods).values ())
  {
   System.out.println ("generating exception class: " + service.getKey () + "." + error.
    getType ());
   new ServiceExceptionWriter (model, directory, rootPackage, service, error).write ();
  }

 }

 private Map<String, ServiceMethodError> getServiceMethodErrors (
  List<ServiceMethod> methods)
 {
  Map<String, ServiceMethodError> errors = new HashMap ();
  for (ServiceMethod method : methods)
  {
   for (ServiceMethodError error : method.getErrors ())
   {
    errors.put (error.getType (), error);
   }
  }
  return errors;
 }

 private Set<XmlType> getXmlTypesUsedByService (List<ServiceMethod> methods)
 {
  Set<XmlType> set = new HashSet ();
  ModelFinder finder = new ModelFinder (model);
  for (ServiceMethod method : methods)
  {
   if (method.getReturnValue () != null)
   {
    ServiceMethodReturnValue ret = method.getReturnValue ();
    XmlType xmlType = finder.findXmlType (stripListFromType (ret.getType ()));
    if (xmlType == null)
    {
     throw new DictionaryValidationException ("Method " + method.getService ()
      + "." + method.getName () + "returns an unknown type, " + ret.getType ());
    }
    addTypeAndAllSubTypes (set, xmlType);
   }
   for (ServiceMethodParameter param : method.getParameters ())
   {
    XmlType xmlType = finder.findXmlType (stripListFromType (param.getType ()));
    if (xmlType == null)
    {
     throw new DictionaryValidationException ("Parameter "
      + method.getService () + "." + method.getName () + "." + param.getName ()
      + "has an unknown type, " + param.getType ());
    }
    addTypeAndAllSubTypes (set, xmlType);
   }
  }
  return set;
 }

 private void addTypeAndAllSubTypes (Set<XmlType> set, XmlType xmlType)
 {
  if (xmlType.getPrimitive ().equalsIgnoreCase (XmlType.COMPLEX))
  {
   if (set.add (xmlType))
   {
    addXmlTypesUsedByMessageStructure (set, xmlType);
   }
  }
 }

 private String stripListFromType (String type)
 {
  if (type.endsWith ("List"))
  {
   type = type.substring (0, type.length () - "List".length ());
  }
  return type;
 }

 private void addXmlTypesUsedByMessageStructure (Set<XmlType> set,
                                                 XmlType xmlType)
 {
  ModelFinder finder = new ModelFinder (model);
  for (MessageStructure ms : finder.findMessageStructures (xmlType.getName ()))
  {
   XmlType subType = finder.findXmlType (stripListFromType (ms.getType ()));
   if (subType == null)
   {
    throw new DictionaryValidationException ("MessageStructure field " + ms.getId ()
     + " has an unknown type, " + ms.getType ());
   }
   addTypeAndAllSubTypes (set, subType);
  }
 }

}
