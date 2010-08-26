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
import java.util.List;
import javax.xml.namespace.QName;
import org.kuali.student.wsdl.organization.MissingParameterException;
import org.kuali.student.wsdl.organization.OrganizationService;
import org.kuali.student.wsdl.organization.OrganizationService_Service;
import org.kuali.student.wsdl.search.SearchRequest;
import org.kuali.student.wsdl.search.SearchResult;
import org.kuali.student.wsdl.search.SearchResultRow;

/**
 *
 * @author nwright
 */
public class OrgFinder
{

 private static final QName SERVICE_NAME =
                            new QName ("http://student.kuali.org/wsdl/organization",
                                       "OrganizationService");

 public static final String ORG_GENERIC_SEARCH = "org.search.generic";

 public String find (String orgName, List<String> types)
 {
  if (orgName == null)
  {
   return null;
  }
  URL wsdlURL;
  try
  {
   wsdlURL =
   new URL (
     "http://localhost:9393/ks-embedded-dev/services/OrganizationService?wsdl");
  }
  catch (MalformedURLException ex)
  {
   throw new IllegalArgumentException (ex);
  }

  System.out.println (wsdlURL);
  OrganizationService_Service oss =
                              new OrganizationService_Service (wsdlURL,
                                                               SERVICE_NAME);
  OrganizationService port = oss.getOrganizationServicePort ();
  String resp;

  System.out.println ("Invoking sayHi...");
  SearchRequest req = new SearchRequest ();
  req.setSearchKey ("org.search.generic");
  SearchResult result = null;
  try
  {
   result = port.search (req);
  }
  catch (MissingParameterException ex)
  {
   throw new RuntimeException (ex);
  }
  for (SearchResultRow row : result.getRows ())
  {
   
  }
}

}
