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
import org.kuali.student.dictionary.model.MessageStructure;
import org.kuali.student.dictionary.model.XmlType;
import org.kuali.student.dictionary.model.validation.DictionaryValidationException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author nwright
 */
public class MessageStructurePageReader
{

 private String contractPath;
 private String jSessionId;
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

 protected Document getDocument ()
 {
  return doc;
 }


 protected MessageStructurePageReader (File contractFile)
 {
  contractPath = contractFile.getName ();
  doc = new PageHelper ().getDocument (contractFile);

 }

 public MessageStructurePageReader (String contractPath, String jSessionId)
 {
  this.contractPath = contractPath;
  URL url = new UrlHelper (contractPath).getUrl ();
  doc = new PageHelper ().getDocument (url, jSessionId);
 }

 protected List<MessageStructure> convertStructureTable (
  Node messageStructureTable)
 {
  List<MessageStructure> list = new ArrayList ();
  MessageStructure messageStructure = null;
  for (NameValue nv :
       getNameValuePairsFromStructureTable (messageStructureTable))
  {
// messageStructureName=getAtpTypes - null
// messageStructureDesc=Retrieves the list of academic time period types known by this service - null
// messageStructureParamType=None - null
// messageStructureParamName=None - null
// messageStructureParamDesc=No parameters - null
// messageStructureReturnType=atpTypeInfoList - /confluence/display/KULSTU/atpTypeInfoList+Structure
// messageStructureReturnDesc=list of academic time period types - null
// messageStructureErrorType=OPERATION_FAILED - null
// messageStructureErrorDesc=unable to complete request - null
// capabilityDesc=null - null
// usecaseDesc=null - null
// commentsDesc=null - null
   if (nv.name.equalsIgnoreCase ("structSName"))
   {
    messageStructure = new MessageStructure ();
    list.add (messageStructure);
    messageStructure.setXmlObject (getXmlType ().getName ());
    messageStructure.setShortName (fixup (nv.value));
   }
   else if (nv.name.equalsIgnoreCase ("structLName"))
   {
    messageStructure.setName (fixup (nv.value));
   }
   else if (nv.name.equalsIgnoreCase ("structType"))
   {
    messageStructure.setType (fixup (nv.value));
    messageStructure.setUrl (fixup (nv.url));
   }
   else if (nv.name.equalsIgnoreCase ("structDesc"))
   {
    messageStructure.setDescription (fixup (nv.value));
   }
   else if (nv.name.equalsIgnoreCase ("structOpt"))
   {
    messageStructure.setRequired (fixup (nv.value));
   }
   else if (nv.name.equalsIgnoreCase ("structCard"))
   {
    messageStructure.setCardinality (fixup (nv.value));
   }
   else if (nv.name.equalsIgnoreCase ("structAttr"))
   {
    messageStructure.setXmlAttribute (fixup (nv.value));
   }
   else if (nv.name.equalsIgnoreCase ("structStatus"))
   {
    messageStructure.setStatus (fixup (nv.value));
   }
   else if (nv.name.equalsIgnoreCase ("commentsDesc"))
   {
    messageStructure.setFeedback (fixup (nv.value));
   }
   else
   {
    throw new DictionaryValidationException ("unknown td class='" + nv.name +
     "'.");
   }
  }
  return list;
 }

 private String fixup (String value)
 {
  if (value == null)
  {
   return "";
  }
  return value.trim ();
 }


 private transient XmlType xmlType = null;

 protected XmlType getXmlType ()
 {
  if (xmlType != null)
  {
   return xmlType;
  }
  xmlType = new XmlTypePageReader (contractPath, doc).getXmlType ();
  return xmlType;
 }

 private transient List<MessageStructure> messageStructures = null;

 protected List<MessageStructure> getMessageStructures ()
 {
  if (messageStructures != null)
  {
   return messageStructures;
  }

  messageStructures = new ArrayList (50);
  for (Node messageStructureTable : getStructureTableNodes ())
  {
   messageStructures.addAll (convertStructureTable (messageStructureTable));
  }
  return messageStructures;
 }

 protected List<Node> getStructureTableNodes ()
 {
  List<Node> list = new ArrayList (50);
  appendStructureTableNodes (list, doc);
  return list;
 }

 private void appendStructureTableNodes (List<Node> list, Node node)
 {
  if (hasAttribute (node, "class", "structTable"))
  {
   list.add (node);
   // there are no nested messageStructure tables
   return;
  }
  NodeList children = node.getChildNodes ();
  for (int i = 0; i < children.getLength (); i ++)
  {
   Node child = children.item (i);
   appendStructureTableNodes (list, child);
  }
 }

 protected List<NameValue> getNameValuePairsFromStructureTable (
  Node messageStructureTable)
 {
  List<NameValue> list = new ArrayList ();
  appendNameValuePairsFromStructureTable (list, messageStructureTable);
  return list;
 }

 private void appendNameValuePairsFromStructureTable (List<NameValue> list,
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
   appendNameValuePairsFromStructureTable (list, child);
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
