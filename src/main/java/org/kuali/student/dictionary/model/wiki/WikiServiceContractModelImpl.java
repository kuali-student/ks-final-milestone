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
package org.kuali.student.dictionary.model.wiki;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.kuali.student.dictionary.model.MessageStructure;
import org.kuali.student.dictionary.model.Service;
import org.kuali.student.dictionary.model.ServiceContractModel;
import org.kuali.student.dictionary.model.ServiceMethod;
import org.kuali.student.dictionary.model.ServiceMethodParameter;
import org.kuali.student.dictionary.model.ServiceMethodReturnValue;
import org.kuali.student.dictionary.model.XmlType;
import org.w3c.dom.Document;

/**
 *
 * @author nwright
 */
public class WikiServiceContractModelImpl implements ServiceContractModel
{

 private String serviceRepositoryPath;
 private String jSessionId;
 private List<String> serviceKeys;

 public WikiServiceContractModelImpl (List<String> serviceKeys,
                                      String serviceRepositoryPath,
                                      String jSessionId)
 {
  this.serviceKeys = serviceKeys;
  this.serviceRepositoryPath = serviceRepositoryPath;
  this.jSessionId = jSessionId;
 }

 @Override
 public List<String> getSourceNames ()
 {
  List<String> list = new ArrayList (1);
  list.add (this.serviceRepositoryPath);
  return list;
 }

 List<ServiceMethod> methods = null;

 @Override
 public List<ServiceMethod> getServiceMethods ()
 {
  if (methods != null)
  {
   return methods;
  }
  List<ServiceMethod> list = new ArrayList ();
  for (Service service : getServices ())
  {
   System.out.println ("getting service methods for " + service.getKey () + "-" +
    service.getName () + " (" + service.getVersion () + ")");
   ContractPageReader rdr =
    new ContractPageReader (service.getKey (), fixUrl (service.getUrl ()), jSessionId);
   list.addAll (rdr.getServiceMethods ());
  }
  methods = list;
  return methods;
 }

 public static final String URL_PREFIX =
  "https://test.kuali.org";

 protected String fixUrl (String url)
 {
  if (url.startsWith ("/"))
  {
   return URL_PREFIX + url;
  }
  return url;
 }

 private List<XmlType> xmlTypes;

 @Override
 public List<XmlType> getXmlTypes ()
 {
  if (xmlTypes != null)
  {
   return xmlTypes;
  }
  List<XmlType> list = new ArrayList ();
  getMessageStructures (); // make sure urlDocumentMap is loaded
  for (String url : urlDocumentMap.keySet ())
  {
   Document doc = urlDocumentMap.get (url);
   System.out.println ("Getting xmlType at url=" + url);
   XmlTypePageReader rdr = new XmlTypePageReader (url, doc);
   list.add (rdr.getXmlType ());
  }
  xmlTypes = list;
  return xmlTypes;
 }

 private List<Service> services = null;

 @Override
 public List<Service> getServices ()
 {
  if (services != null)
  {
   return services;
  }
  ServiceRepositoryPageReader rdr =
   new ServiceRepositoryPageReader (serviceRepositoryPath, jSessionId);
  List<Service> list = rdr.getServices ();
  List<ServicesFilter> filters = new ArrayList ();
  filters.add (new ServicesFilterExcludeDev ());
  filters.add (new ServicesFilterLatestVersionOnly ());
  filters.add (new ServicesFilterByKeys (serviceKeys));
  ServicesFilterChained filter = new ServicesFilterChained (filters);
  services = filter.filter (list);
  return services;
 }

 private List<MessageStructure> messageStructures = null;
 private Map<String, Document> urlDocumentMap = null;

 @Override
 public List<MessageStructure> getMessageStructures ()
 {
  if (messageStructures != null)
  {
   return messageStructures;
  }
  List<MessageStructure> list = new ArrayList ();
  Set<String> allUrls = getLevelOneMessageStructureUrls ();
  urlDocumentMap = new HashMap ();
  Set<String> urlsToBeProcessed = new HashSet (allUrls);
  while (urlsToBeProcessed.size () > 0)
  {
   Set<String> newUrls = new HashSet ();
   for (String url : urlsToBeProcessed)
   {
    System.out.println ("loading message structures for url " + url);
    newUrls.addAll (loadMessageStructures (allUrls, urlDocumentMap, list, url));
   }
   urlsToBeProcessed = newUrls;
  }
  messageStructures = list;
  return messageStructures;
 }

 protected Set<String> loadMessageStructures (Set<String> allUrls,
                                              Map<String, Document> urlDocumentMap,
                                              List<MessageStructure> msgs,
                                              String url)
 {
  Set<String> newUrls = new HashSet ();
  MessageStructurePageReader msReader =
   new MessageStructurePageReader (fixUrl (url), jSessionId);
  urlDocumentMap.put (url, msReader.getDocument ());
  List<MessageStructure> list = msReader.getMessageStructures ();
  msgs.addAll (list);
  // no process sub structures
  // to find the urls that have not yet been identified
  for (MessageStructure ms : list)
  {
   if (ms.getUrl () != null &&  ! ms.getUrl ().equals (""))
   {
    if (allUrls.add (url))
    {
     newUrls.add (url);
    }
   }
  }
  return newUrls;
 }

 protected Set<String> getLevelOneMessageStructureUrls ()
 {
  Set<String> urls = new HashSet ();
  for (ServiceMethod method : getServiceMethods ())
  {
   for (ServiceMethodParameter param : method.getParameters ())
   {
    if (param.getUrl () != null &&  ! param.getUrl ().equals (""))
    {
     urls.add (param.getUrl ());
    }
   }
   ServiceMethodReturnValue retVal = method.getReturnValue ();
   if (retVal.getUrl () != null &&  ! retVal.getUrl ().equals (""))
   {
    urls.add (method.getReturnValue ().getUrl ());
   }
  }
  return urls;
 }

}
