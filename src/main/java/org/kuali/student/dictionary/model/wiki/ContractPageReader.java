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

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.kuali.student.dictionary.model.ServiceMethod;
import org.kuali.student.dictionary.model.ServiceMethodError;

import org.kuali.student.dictionary.model.ServiceMethodParameter;
import org.kuali.student.dictionary.model.ServiceMethodReturnValue;
import org.kuali.student.dictionary.model.validation.DictionaryValidationException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author nwright
 */
public class ContractPageReader
{

 private String serviceKey;
 private Document doc;

 protected class NameValue
 {

  protected String name;
  protected String value;
  protected String url;

  public NameValue (String name, String value, String url)
  {
   this.name = name;
   this.value = value;
   this.url = url;
  }

 }

 public ContractPageReader (String serviceKey, File contractFile)
 {
  this.serviceKey = serviceKey;
  doc = new PageHelper ().getDocument (contractFile);

 }

 public ContractPageReader (String serviceKey, String contractPath, String jSessionId)
 {
  this.serviceKey = serviceKey;
  URL url = new UrlHelper (contractPath).getUrl ();
  doc = new PageHelper ().getDocument (url, jSessionId);

 }

 protected ServiceMethod convertMethodTable (Node methodTable)
 {
  ServiceMethod method = new ServiceMethod ();
  method.setService (serviceKey);
  ServiceMethodParameter param = null;
  ServiceMethodReturnValue retVal = null;
  ServiceMethodError error = null;
  for (NameValue nv : getNameValuePairsFromMethodTable (methodTable))
  {
// methodName=getAtpTypes - null
// methodDesc=Retrieves the list of academic time period types known by this service - null
// methodParamType=None - null
// methodParamName=None - null
// methodParamDesc=No parameters - null
// methodReturnType=atpTypeInfoList - /confluence/display/KULSTU/atpTypeInfoList+Structure
// methodReturnDesc=list of academic time period types - null
// methodErrorType=OPERATION_FAILED - null
// methodErrorDesc=unable to complete request - null
// capabilityDesc=null - null
// usecaseDesc=null - null
// commentsDesc=null - null
   if (nv.name.equalsIgnoreCase ("methodName"))
   {
    method.setName (fixup (nv.value));
   }
   else if (nv.name.equalsIgnoreCase ("methodDesc"))
   {
    method.setDescription (fixup (nv.value));
   }
   else if (nv.name.equalsIgnoreCase ("methodParamType"))
   {
    param = new ServiceMethodParameter ();
    method.getParameters ().add (param);
    param.setType (fixup (nv.value));
    if (nv.url != null)
    {
     param.setUrl (fixup (nv.url));
    }
   }
   else if (nv.name.equalsIgnoreCase ("methodParamName"))
   {
    param.setName (fixup (nv.value));
   }
   else if (nv.name.equalsIgnoreCase ("methodParamDesc"))
   {
    param.setDescription (fixup (nv.value));
    if (nv.url != null)
    {
     param.setUrl (fixup (nv.url));
    }
   }
   else if (nv.name.equalsIgnoreCase ("methodReturnType"))
   {
    retVal = new ServiceMethodReturnValue ();
    method.setReturnValue (retVal);
    retVal.setType (fixup (nv.value));
    if (nv.url != null)
    {
     retVal.setUrl (fixup (nv.url));
    }
   }
   else if (nv.name.equalsIgnoreCase ("methodReturnDesc"))
   {
    retVal.setDescription (fixup (nv.value));
    if (nv.url != null)
    {
     retVal.setUrl (fixup (nv.url));
    }
   }
   else if (nv.name.equalsIgnoreCase ("methodErrorType"))
   {
    error = new ServiceMethodError ();
    method.getErrors ().add (error);
    error.setType (fixup (nv.value));
   }
   else if (nv.name.equalsIgnoreCase ("methodErrorDesc"))
   {
    error.setDescription (fixup (nv.value));
   }
   else if (nv.name.equalsIgnoreCase ("capabilityDesc"))
   {
    // ignore for now
   }
   else if (nv.name.equalsIgnoreCase ("usecaseDesc"))
   {
    // ignore for now
   }
   else if (nv.name.equalsIgnoreCase ("commentsDesc"))
   {
    // ignore for now
   }
   else
   {
    throw new DictionaryValidationException ("unknown td class='" + nv.name +
     "'.");
   }
  }
  return method;
 }

 private String fixup (String value)
 {
  if (value == null)
  {
   return "";
  }
  return value.trim ();
 }

 private transient List<ServiceMethod> methods = null;

 protected List<ServiceMethod> getServiceMethods ()
 {
  if (methods != null)
  {
   return methods;
  }
  methods = new ArrayList (50);
  for (Node methodTable : getMethodTableNodes ())
  {
   methods.add (convertMethodTable (methodTable));
  }
  return methods;
 }

 protected List<Node> getMethodTableNodes ()
 {
  List<Node> list = new ArrayList (50);
  appendMethodTableNodes (list, doc);
  return list;
 }

 private void appendMethodTableNodes (List<Node> list, Node node)
 {
  if (hasAttribute (node, "class", "methodTable"))
  {
   list.add (node);
   // there are no nested method tables
   return;
  }
  NodeList children = node.getChildNodes ();
  for (int i = 0; i < children.getLength (); i ++)
  {
   Node child = children.item (i);
   appendMethodTableNodes (list, child);
  }
 }

 protected List<NameValue> getNameValuePairsFromMethodTable (Node methodTable)
 {
  List<NameValue> list = new ArrayList ();
  appendNameValuePairsFromMethodTable (list, methodTable);
  return list;
 }

 private void appendNameValuePairsFromMethodTable (List<NameValue> list,
                                                   Node node)
 {
  if (node.getNodeName ().equalsIgnoreCase ("td"))
  {
   NamedNodeMap attributes = node.getAttributes ();
   if (attributes != null)
   {
    for (int i = 0; i < attributes.getLength (); i ++)
    {
     Node attr = attributes.item (i);
     if (attr.getNodeName ().equalsIgnoreCase ("class"))
     {
      NameValue nv = new NameValue (attr.getNodeValue (), null, null);
      extractLoadNameValue (nv, node);
      list.add (nv);
     }
    }
   }
  }
  // now recursively call children
  NodeList children = node.getChildNodes ();
  for (int i = 0; i < children.getLength (); i ++)
  {
   Node child = children.item (i);
   appendNameValuePairsFromMethodTable (list, child);
  }
 }

 private void extractLoadNameValue (NameValue nv, Node node)
 {
  NodeList children = node.getChildNodes ();
  for (int i = 0; i < children.getLength (); i ++)
  {
   Node child = children.item (i);
   if (child.getNodeName ().equalsIgnoreCase ("#text"))
   {
    nv.value = child.getNodeValue ();
   }
   else if (child.getNodeName ().equalsIgnoreCase ("a"))
   {
    NamedNodeMap attributes = child.getAttributes ();
    if (attributes != null)
    {
     for (int j = 0; j < attributes.getLength (); j ++)
     {
      Node attr = attributes.item (j);
      if (attr.getNodeName ().equalsIgnoreCase ("href"))
      {
       nv.url = attr.getNodeValue ();
      }
     }
    }
   }
   extractLoadNameValue (nv, child);
  }

 }

 private boolean hasAttribute (Node node, String name, String value)
 {
  return new NodeHelper ().hasAttribute (node, name, value);
 }

}
