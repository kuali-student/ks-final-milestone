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
import java.util.List;
import java.util.Map;
import org.kuali.student.dictionary.model.MessageStructure;
import org.kuali.student.dictionary.model.Service;
import org.kuali.student.dictionary.model.ServiceContractModel;
import org.kuali.student.dictionary.model.ServiceMethod;
import org.kuali.student.dictionary.model.ServiceMethodParameter;
import org.kuali.student.dictionary.model.XmlType;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 *
 * @author nwright
 */
public class WikiServiceContractModelImpl implements ServiceContractModel
{

 private String contractPath;
 private String jSessionId;
 private ContractPageReader contractReader;

 public WikiServiceContractModelImpl (String contractPath, String jSessionId)
 {
  this.contractPath = contractPath;
  this.jSessionId = jSessionId;
  this.contractReader = new ContractPageReader (contractPath, jSessionId);
 }

 @Override
 public List<String> getSourceNames ()
 {
  List<String> list = new ArrayList (1);
  list.add (this.contractPath);
  return list;
 }

 @Override
 public List<ServiceMethod> getServiceMethods ()
 {
  return contractReader.getServiceMethods ();
 }

 @Override
 public List<XmlType> getXmlTypes ()
 {
  return null;
 }

 @Override
 public List<Service> getServices ()
 {
  return null;
 }

 @Override
 public List<MessageStructure> getMessageStructures ()
 {
  Map<String, MessageStructure> map = new HashMap ();
  for (String url : getLevelOneMessageStructureUrls ())
  {
   MessageStructurePageReader msReader =
    new MessageStructurePageReader (url, jSessionId);
  }
  return new ArrayList (map.values ());
 }

 private List<String> getLevelOneMessageStructureUrls ()
 {
  List<String> urls = new ArrayList ();
  for (ServiceMethod method : getServiceMethods ())
  {
   for (ServiceMethodParameter param : method.getParameters ())
   {
    if (param.getUrl () != null &&  ! param.getUrl ().endsWith (""))
    {
     urls.add (param.getUrl ());
    }
   }
   if (method.getReturnValue ().getUrl () != null &&  ! method.getReturnValue ().
    getUrl ().equals (""))
   {
    urls.add (method.getReturnValue ().getUrl ());
   }
  }
  return urls;
 }

}
