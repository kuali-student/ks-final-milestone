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

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.kuali.student.dictionary.DictionaryExecutionException;
import org.kuali.student.dictionary.model.ServiceMethod;
import org.kuali.student.dictionary.model.ServiceMethodError;


import org.kuali.student.dictionary.model.ServiceMethodParameter;
import org.kuali.student.dictionary.model.ServiceMethodReturnValue;
import org.kuali.student.dictionary.model.validation.DictionaryValidationException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author nwright
 */
public class ContractReaderOldSave 
{

 private String contractPath;
 private String jSessionId;
 private String contractText;

 public ContractReaderOldSave (String contractPath, String jSessionId)
 {
  this.contractPath = contractPath;
  this.jSessionId = jSessionId;
  URL url;
  try
  {
   url = new URL (contractPath);
  }
  catch (MalformedURLException ex)
  {
   throw new DictionaryExecutionException (ex);
  }

  URLConnection connection;
  try
  {
   connection = url.openConnection ();
  }
  catch (IOException ex)
  {
   throw new DictionaryExecutionException (ex);
  }
  connection.setRequestProperty ("Cookie", "JSESSIONID=" + jSessionId);

  InputStreamReader myReader;
  try
  {
   myReader = new InputStreamReader (connection.getInputStream ());
  }
  catch (IOException ex)
  {
   throw new DictionaryExecutionException (ex);
  }
  BufferedReader reader = new BufferedReader (myReader);

  this.contractText = trimContract (reader);
 }

 private String trimContract (BufferedReader reader)
 {
  StringBuilder builder = new StringBuilder ();
  String line;
  boolean inContract = false;
  try
  {
   while ((line = reader.readLine ()) != null)
   {
    if ( ! inContract)
    {
     if (line.contains ("<em>Setup</em>"))
     {
      inContract = true;
      builder.append ("<contents>");
     }
    }
    else
    {
     if (line.contains ("</a>Capabilities</h3>"))
     {
      inContract = false;
     }
     else
     {
      builder.append (line);
     }
    }
   }
  }
  catch (IOException ex)
  {
   throw new DictionaryExecutionException (ex);
  }
  builder.append ("</contents>");
  return builder.toString ();
 }

 public List<ServiceMethod> getServiceMethods ()
 {
  List<ServiceMethod> methods = new ArrayList (50);
  Document doc = getDocument ();
  for (Node methodTable : getMethodTableNodes (doc))
  {
   methods.add (convertMethodTable (methodTable));
  }
  return methods;
 }

 private ServiceMethod convertMethodTable (Node methodTable)
 {
  ServiceMethod method = new ServiceMethod ();
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
    method.setName (nv.value);
   }
   else if (nv.name.equalsIgnoreCase ("methodDesc"))
   {
    method.setDescription (nv.value);
   }
   else if (nv.name.equalsIgnoreCase ("methodParamType"))
   {
    param = new ServiceMethodParameter ();
    method.getParameters ().add (param);
    param.setType (nv.value);
   }
   else if (nv.name.equalsIgnoreCase ("methodParamName"))
   {
    param.setName (nv.value);
   }
   else if (nv.name.equalsIgnoreCase ("methodParamDesc"))
   {
    param.setDescription (nv.value);
   }
   else if (nv.name.equalsIgnoreCase ("methodReturnType"))
   {
     retVal = new ServiceMethodReturnValue ();
     method.setReturnValue (retVal);
     retVal.setType (nv.value);
   }
   else if (nv.name.equalsIgnoreCase ("methodReturnDesc"))
   {
    retVal.setDescription (nv.value);
   }
   else if (nv.name.equalsIgnoreCase ("methodErrorType"))
   {
    error = new ServiceMethodError ();
    method.getErrors ().add (error);
    error.setType (nv.value);
   }
   else if (nv.name.equalsIgnoreCase ("methodErrorDesc"))
   {
    error.setDescription (nv.value);
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
    throw new DictionaryValidationException ("unknown td class='" + nv.name + "'.");
   }
  }
  return method;
 }

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

 protected List<NameValue> getNameValuePairsFromMethodTable (Node methodTable)
 {
  List<NameValue> list = new ArrayList ();
  appendNameValuePairsFromMethodTable (list, methodTable);
  return list;
 }

 protected void appendNameValuePairsFromMethodTable (List<NameValue> list,
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

      String name = attr.getNodeValue ();
      String value = null;
      String url = null;
      NodeList children = node.getChildNodes ();
      for (int j = 0; j < children.getLength (); j ++)
      {
       Node child = children.item (j);
       if (child.getNodeName ().equalsIgnoreCase ("#text"))
       {
        value = child.getNodeValue ();
       }
       else if (child.getNodeName ().equalsIgnoreCase ("a"))
       {
       }
      }
      NameValue nv = new NameValue (attr.getNodeValue (), null, null);
      extractNameValue (nv, node);
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

 protected void extractNameValue (NameValue nv, Node node)
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
   extractNameValue (nv, child);
  }

 }

 protected List<Node> getMethodTableNodes (Document doc)
 {
  List<Node> list = new ArrayList (50);
  appendMethodTableNodes (list, doc);
  return list;
 }

 protected void appendMethodTableNodes (List<Node> list, Node node)
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

 private boolean hasAttribute (Node node, String name, String value)
 {
  NamedNodeMap attributes = node.getAttributes ();
  if (attributes == null)
  {
   return false;
  }
  for (int i = 0; i < attributes.getLength (); i ++)
  {
   Node attr = attributes.item (i);
   if (attr.getNodeName ().equalsIgnoreCase (name))
   {
    if (attr.getNodeValue ().equalsIgnoreCase (value))
    {
     return true;
    }
   }
  }
  return false;
 }

 protected Document getDocument ()
 {
  DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance ();
  DocumentBuilder builder;

  try
  {
   builder = factory.newDocumentBuilder ();
  }
  catch (ParserConfigurationException ex)
  {
   throw new DictionaryExecutionException (ex);
  }

  ByteArrayInputStream baos;
  try
  {
   baos = new ByteArrayInputStream (contractText.getBytes ("UTF-8"));
  }
  catch (UnsupportedEncodingException ex)
  {
   throw new DictionaryExecutionException (ex);
  }

  Document doc;
  try
  {
   doc = builder.parse (baos);
  }
  catch (SAXException ex)
  {
   throw new DictionaryExecutionException (ex);
  }
  catch (IOException ex)
  {
   throw new DictionaryExecutionException (ex);
  }

  return doc;
 }

 public List<String> getSourceNames ()
 {
  List<String> list = new ArrayList (1);
  list.add (this.contractPath);
  return list;
 }

}
