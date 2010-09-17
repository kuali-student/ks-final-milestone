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
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.kuali.student.wsdl.organization.AlreadyExistsException;
import org.kuali.student.wsdl.organization.DataValidationErrorException;
import org.kuali.student.wsdl.organization.DoesNotExistException;
import org.kuali.student.wsdl.organization.OrgInfo;
import org.kuali.student.wsdl.organization.OrgTypeInfo;
import org.kuali.student.wsdl.organization.OrganizationService;
import org.kuali.student.wsdl.organization.OrganizationService_Service;
import org.kuali.student.wsdl.organization.StatusInfo;
import org.kuali.student.wsdl.organization.VersionMismatchException;
import org.kuali.student.wsdl.search.GetSearchTypes;
import org.kuali.student.wsdl.search.GetSearchTypesResponse;
import org.kuali.student.wsdl.search.SearchParam;
import org.kuali.student.wsdl.search.SearchRequest;
import org.kuali.student.wsdl.search.SearchResult;
import org.kuali.student.wsdl.search.SearchResultRow;
import org.kuali.student.wsdl.search.SearchTypeInfo;

/**
 *
 * @author nwright
 */
public class OrgService
{

 private static final String ORGANIZATION_SERVICE_NAME = "OrganizationService";
 private static final QName ORGANIZATION_SERVICE_QNAME =
                            OrganizationService_Service.SERVICE;
 public static final String ORG_GENERIC_SEARCH = "org.search.generic";
 public static final String LOCAL_HOST_URL =
                            "http://localhost:9393/ks-embedded-dev";
 private String hostUrl = LOCAL_HOST_URL;

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

 private OrganizationService getOrganizationService ()
 {
  URL wsdlURL;
  try
  {
   wsdlURL = new URL (calcWsdlUrl (ORGANIZATION_SERVICE_NAME));
  }
  catch (MalformedURLException ex)
  {
   throw new IllegalArgumentException (ex);
  }

//  System.out.println (wsdlURL);
  OrganizationService_Service oss =
                              new OrganizationService_Service (wsdlURL,
                                                               ORGANIZATION_SERVICE_QNAME);
  OrganizationService port = oss.getOrganizationServicePort ();
  return port;
 }

 public List<OrgTypeInfo> getOrgTypes ()
 {
  OrganizationService port = getOrganizationService ();

//  System.out.println ("Invoking get org types request...");
  List<OrgTypeInfo> result = null;
  try
  {
   result = port.getOrgTypes ();
  }
  catch (Exception ex)
  {
   throw new RuntimeException (ex);
  }
  return result;
 }

 public OrgInfo getOrganization (String id)
   throws DoesNotExistException
 {
  OrganizationService port = getOrganizationService ();

//  System.out.println ("Invoking get org request...");
  OrgInfo result = null;
  try
  {
   result = port.getOrganization (id);
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

 public OrgInfo createOrganization (OrgInfo info)
   throws AlreadyExistsException
 {
  OrganizationService port = getOrganizationService ();

//  System.out.println ("Invoking create org request...");
  OrgInfo result = null;
  try
  {
   result = port.createOrganization (info.getType (), info);
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

 public OrgInfo updateOrganization (OrgInfo info)
   throws DoesNotExistException,
          VersionMismatchException,
          DataValidationErrorException
 {
  OrganizationService port = getOrganizationService ();

//  System.out.println ("Invoking create org request...");
  OrgInfo result = null;
  try
  {
   result = port.updateOrganization (info.getId (), info);
  }
  catch (DoesNotExistException ex)
  {
   throw ex;
  }
  catch (VersionMismatchException ex)
  {
   throw ex;
  }
  catch (DataValidationErrorException ex)
  {
   throw ex;
  }
  catch (Exception ex)
  {
   throw new RuntimeException (ex);
  }
  return result;
 }

 public boolean deleteOrganization (String id)
   throws DoesNotExistException,
          DataValidationErrorException
 {
  OrganizationService port = getOrganizationService ();

//  System.out.println ("Invoking create org request...");
  StatusInfo result = null;
  try
  {
   result = port.deleteOrganization (id);
  }
  catch (DoesNotExistException ex)
  {
   throw ex;
  }
  catch (Exception ex)
  {
   throw new RuntimeException (ex);
  }
  return result.isSuccess ();
 }
}
