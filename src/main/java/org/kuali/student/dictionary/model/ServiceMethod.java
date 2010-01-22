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
package org.kuali.student.dictionary.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nwright
 */
public class ServiceMethod
{
 private String service;

 public String getService ()
 {
  return service;
 }

 public void setService (String service)
 {
  this.service = service;
 }


 private String name;

 /**
  * Get the value of name
  *
  * @return the value of name
  */
 public String getName ()
 {
  return name;
 }

 /**
  * Set the value of name
  *
  * @param name new value of name
  */
 public void setName (String name)
 {
  this.name = name;
 }

 private String url;

 public String getUrl ()
 {
  return url;
 }

 public void setUrl (String url)
 {
  this.url = url;
 }


 private String description;

 public String getDescription ()
 {
  return description;
 }

 public void setDescription (String description)
 {
  this.description = description;
 }


 private List<ServiceMethodParameter> parameters;

 public List<ServiceMethodParameter> getParameters ()
 {
  if (parameters == null)
  {
   parameters = new ArrayList ();
  }
  return parameters;
 }

 public void setParameters (List<ServiceMethodParameter> parameters)
 {
  this.parameters = parameters;
 }

 private ServiceMethodReturnValue returnValue;

 public ServiceMethodReturnValue getReturnValue ()
 {
  return returnValue;
 }

 public void setReturnValue (ServiceMethodReturnValue returnValue)
 {
  this.returnValue = returnValue;
 }

 private List<ServiceMethodError> errors;

 public List<ServiceMethodError> getErrors ()
 {
  if (errors == null)
  {
   errors = new ArrayList ();
  }
  return errors;
 }

 public void setErrors (List<ServiceMethodError> errors)
 {
  this.errors = errors;
 }


}
