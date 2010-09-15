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
package org.kuali.student.loader;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import javax.xml.namespace.QName;

import org.kuali.student.wsdl.enumerationmanagement.AlreadyExistsException;
import org.kuali.student.wsdl.enumerationmanagement.DoesNotExistException;
import org.kuali.student.wsdl.enumerationmanagement.EnumeratedValueInfo;
import org.kuali.student.wsdl.enumerationmanagement.EnumerationManagementService;
import org.kuali.student.wsdl.enumerationmanagement.EnumerationManagementService_Service;
import org.kuali.student.wsdl.enumerationmanagementservice.AddEnumeratedValue;
import org.kuali.student.wsdl.enumerationmanagementservice.AddEnumeratedValueResponse;
import org.kuali.student.wsdl.enumerationmanagementservice.GetEnumeration;
import org.kuali.student.wsdl.enumerationmanagementservice.GetEnumerationResponse;
import org.kuali.student.wsdl.enumerationmanagementservice.UpdateEnumeratedValue;
import org.kuali.student.wsdl.enumerationmanagementservice.UpdateEnumeratedValueResponse;

/**
 *
 * @author nwright
 */
public class EnumManService
{

 private static final String ENUMERATION_MANAGEMENT_SERVICE_NAME = "EnumerationManagementService";
 private static final QName ENUMERATION_MANAGEMENT_SERVICE_QNAME =
                            EnumerationManagementService_Service.SERVICE;
 private String hostUrl;

 public String getHostUrl ()
 {
  return hostUrl;
 }

 public void setHostUrl (String hostUrl)
 {
  this.hostUrl = hostUrl;
 }

 public String calcWsdlUrl (String serviceName)
 {
  String url = getHostUrl () + "/services/" + serviceName + "?wsdl";
//  System.out.println ("url is " + url);
  return url;
 }

 private EnumerationManagementService getEnumerationManagementService ()
 {
  URL wsdlURL;
  try
  {
   wsdlURL = new URL (calcWsdlUrl (ENUMERATION_MANAGEMENT_SERVICE_NAME));
  }
  catch (MalformedURLException ex)
  {
   throw new IllegalArgumentException (ex);
  }

//  System.out.println (wsdlURL);
  EnumerationManagementService_Service oss =
                        new EnumerationManagementService_Service (wsdlURL,
                                                   ENUMERATION_MANAGEMENT_SERVICE_QNAME);
  EnumerationManagementService port = oss.getEnumerationManagementServicePort ();
  return port;
 }

 public EnumeratedValueInfo addEnumeratedValue (String enumerationKey, EnumeratedValueInfo info)
   throws AlreadyExistsException
 {
  EnumerationManagementService port = getEnumerationManagementService ();
//  System.out.println ("Invoking get course request...");
  EnumeratedValueInfo result = null;
  AddEnumeratedValueResponse response = null;
  try
  {
   AddEnumeratedValue param = new AddEnumeratedValue ();
   param.setEnumeratedValue (info);
   param.setEnumerationKey (enumerationKey);
   response = port.addEnumeratedValue (param);
   result = response.getReturn ();
  }
  catch (AlreadyExistsException ex)
  {
   throw ex;
  }
  catch (Exception ex)
  {
   throw new RuntimeException (ex);
  }
  return result;
 }

  public EnumeratedValueInfo updateEnumeratedValue (String enumerationKey, EnumeratedValueInfo info)
    throws DoesNotExistException
 {
  EnumerationManagementService port = getEnumerationManagementService ();
//  System.out.println ("Invoking get course request...");
  EnumeratedValueInfo result = null;
  UpdateEnumeratedValueResponse response = null;
  try
  {
   UpdateEnumeratedValue param = new UpdateEnumeratedValue ();
   param.setEnumeratedValue (info);
   param.setEnumerationKey (enumerationKey);
   param.setCode (info.getCode ());
   response = port.updateEnumeratedValue (param);
   result = response.getReturn ();
  }
  catch (DoesNotExistException ex)
  {
   throw ex;
  }
  catch (Exception ex)
  {
   throw new RuntimeException (ex);
  }
  return result;
 }

 public List<EnumeratedValueInfo> getEnumeration (String enumerationKey, String contextType, String contextValue, Date contextDate)
   throws DoesNotExistException
 {
  EnumerationManagementService port = getEnumerationManagementService ();
//  System.out.println ("Invoking get course request...");
  List <EnumeratedValueInfo> result = null;
  GetEnumerationResponse response = null;
  try
  {
   GetEnumeration param = new GetEnumeration ();
   param.setEnumerationKey (enumerationKey);
   param.setContextType (contextType);
   param.setContextValue (contextValue);
   param.setContextDate (new DateHelper ().asXmlDate (contextDate));
   response = port.getEnumeration (param);
   result = response.getReturn ();
  }
  catch (DoesNotExistException ex)
  {
   throw ex;
  }
  catch (Exception ex)
  {
   throw new RuntimeException (ex);
  }
  return result;
 }


}
