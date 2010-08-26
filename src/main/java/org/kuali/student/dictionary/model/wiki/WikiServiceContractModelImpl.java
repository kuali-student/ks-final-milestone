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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.kuali.student.dictionary.DictionaryExecutionException;
import org.kuali.student.dictionary.model.MessageStructure;
import org.kuali.student.dictionary.model.Service;
import org.kuali.student.dictionary.model.ServiceContractModel;
import org.kuali.student.dictionary.model.ServiceMethod;
import org.kuali.student.dictionary.model.ServiceMethodParameter;
import org.kuali.student.dictionary.model.ServiceMethodReturnValue;
import org.kuali.student.dictionary.model.XmlType;
import org.kuali.student.dictionary.model.util.ServicesFilter;

/**
 *
 * @author nwright
 */
public class WikiServiceContractModelImpl implements ServiceContractModel
{

 private String serviceRepositoryPath;
 private String jSessionId;
 private ServicesFilter filter;

 public WikiServiceContractModelImpl (ServicesFilter filter,
                                      String serviceRepositoryPath,
                                      String jSessionId)
 {
  this.filter = filter;
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
  getMessageStructures (); // this loads xmlTypes as well
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
  services = filter.filter (list);
  return services;
 }

 private List<MessageStructure> messageStructures = null;

 @Override
 public List<MessageStructure> getMessageStructures ()
 {
  if (messageStructures != null)
  {
   return messageStructures;
  }
  List<XmlType> types = new ArrayList ();
  List<MessageStructure> msgs = new ArrayList ();
  Set<String> allUrls = getLevelOneMessageStructureUrls ();
  Set<String> newUrlsToBeProcessed = new HashSet ();
  newUrlsToBeProcessed.addAll (allUrls);
  int loop = 0;
  while (newUrlsToBeProcessed.size () > 0)
  {
   loop ++;
   System.out.println ("Loop " + loop + " has " + newUrlsToBeProcessed.size () +
    " new url's to be processed.");
   Set<String> newUrls = new HashSet ();
   for (String url : newUrlsToBeProcessed)
   {
    //System.out.println ("loading message structures for url " + url);
    Set<String> newUrlsFromThisLoad =
     loadMessageStructures (allUrls, msgs, types, url);
    //System.out.println ("   " + newUrlsFromThisLoad.size () + " new urls found");
    newUrls.addAll (newUrlsFromThisLoad);
   }
   newUrlsToBeProcessed = newUrls;
  }
  messageStructures = msgs;
  xmlTypes = types;
  return messageStructures;
 }

 protected Set<String> loadMessageStructures (Set<String> allUrls,
                                              List<MessageStructure> msgs,
                                              List<XmlType> types,
                                              String url)
 {
  Set<String> newUrls = new HashSet ();
  MessageStructurePageReader msReader =
   new MessageStructurePageReader (fixUrl (url), jSessionId);
  List<MessageStructure> newMsgs = msReader.getMessageStructures ();
  types.add (msReader.getXmlType ());
  msgs.addAll (newMsgs);
  // nos process for sub structures
  // to find the urls that have not yet been identified
  for (MessageStructure ms : newMsgs)
  {
   if (ms.getUrl () == null)
   {
    throw new DictionaryExecutionException (ms.getXmlObject () + "." + ms.
     getShortName () + " has null for the url for the type");
   }
   if (ms.getUrl ().equals (""))
   {
    throw new DictionaryExecutionException (ms.getXmlObject () + "." + ms.
     getShortName () + " has an empty url for the type");
   }
   if (allUrls.add (ms.getUrl ()))
   {
    newUrls.add (ms.getUrl ());
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
