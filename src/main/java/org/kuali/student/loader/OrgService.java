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
import org.kuali.student.wsdl.organization.OrgInfo;
import org.kuali.student.wsdl.organization.OrgTypeInfo;
import org.kuali.student.wsdl.organization.OrganizationService;
import org.kuali.student.wsdl.organization.OrganizationService_Service;
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


 public List<OrgResultGeneric> getAll ()
 {
  OrganizationService port = getOrganizationService ();

//  System.out.println ("Invoking search request to get all org result generics...");
  SearchRequest req = new SearchRequest ();
  req.setSearchKey ("org.search.generic");
//  System.out.println ("getSearchKey ()=" + req.getSearchKey ());
  SearchResult result = null;
  try
  {
   result = port.search (req);
  }
  catch (Exception ex)
  {
   throw new RuntimeException (ex);
  }
//  System.out.println (result.getRows ().size () + " rows returned");
  List<OrgResultGeneric> list = new ArrayList ();
  for (SearchResultRow row : result.getRows ())
  {
   OrgResultGeneric org = new OrgResultGeneric ();
   org.setOrgId (row.getCells ().get (0).getValue ());
   org.setShortName (row.getCells ().get (1).getValue ());
   org.setLongName (row.getCells ().get (2).getValue ());
   org.setType (row.getCells ().get (3).getValue ());
  }
  return list;
 }

 public List<OrgResultGeneric> getAllWithType (String type)
 {
  OrganizationService port = getOrganizationService ();

//  System.out.println ("Invoking search request to get all org result generics...");
  SearchRequest req = new SearchRequest ();
  req.setSearchKey ("org.search.generic");
  SearchParam param = new SearchParam ();
  param.setKey ("org.queryParam.orgOptionalType");
  param.setValue (type);
  req.getParams ().add (param);
//  System.out.println ("getSearchKey ()=" + req.getSearchKey ());
  SearchResult result = null;
  try
  {
   result = port.search (req);
  }
  catch (Exception ex)
  {
   throw new RuntimeException (ex);
  }
//  System.out.println (result.getRows ().size () + " rows returned");
  List<OrgResultGeneric> list = new ArrayList ();
  for (SearchResultRow row : result.getRows ())
  {
   OrgResultGeneric org = new OrgResultGeneric ();
   org.setOrgId (row.getCells ().get (0).getValue ());
   org.setShortName (row.getCells ().get (1).getValue ());
   org.setLongName (row.getCells ().get (2).getValue ());
   org.setType (row.getCells ().get (3).getValue ());
  }
  return list;
 }



 /**
  * find matching org
  * @param orgName
  * @param types
  * @return the orgId of the matching org, null if none found
  */
 public String findMatch (String orgName, List<String> types)
 {
  if (orgName == null)
  {
   return null;
  }

  OrgResultGeneric org = new OrgMatcher ().findMatch (orgName, types, getAll ());
  return org.getOrgId ();
 }

 public List<SearchTypeInfo> getSearchTypes ()
 {
  OrganizationService port = getOrganizationService ();

//  System.out.println ("Invoking get search types search request...");
  GetSearchTypes parameters = new GetSearchTypes ();
  GetSearchTypesResponse result = null;
  try
  {
   result = port.getSearchTypes (parameters);
  }
  catch (Exception ex)
  {
   throw new RuntimeException (ex);
  }
  return result.getReturn ();
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
 {
  OrganizationService port = getOrganizationService ();

//  System.out.println ("Invoking get org request...");
  OrgInfo result = null;
  try
  {
   result = port.getOrganization (id);
  }
  catch (Exception ex)
  {
   throw new RuntimeException (ex);
  }
  return result;
 }

 public OrgInfo createOrganization (OrgInfo info)
 {
  OrganizationService port = getOrganizationService ();

//  System.out.println ("Invoking create org request...");
  OrgInfo result = null;
  try
  {
   result = port.createOrganization (info.getType (), info);
  }
  catch (Exception ex)
  {
   throw new RuntimeException (ex);
  }
  return result;
 }
}
