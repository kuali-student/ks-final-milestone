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
import org.kuali.student.dictionary.DictionaryExecutionException;
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
public class XmlTypePageReader
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

 /**
  * used for testing based on contract stored in a file
  * @param contractPath
  * @param contractFile
  */
 protected XmlTypePageReader (String contractPath, File contractFile)
 {
  this.contractPath = contractPath;
  doc = new PageHelper ().getDocument (contractFile);
 }

 /**
  * used to read contract from the wiki
  * @param contractPath
  * @param jSessionId
  */
 public XmlTypePageReader (String contractPath, String jSessionId)
 {
  this.contractPath = contractPath;
  URL url = new UrlHelper (contractPath).getUrl ();
  doc = new PageHelper ().getDocument (url, jSessionId);
 }

 /**
  * used if already have the parsed document because we parsed it for the message structure.
  * @param contractPath
  * @param doc
  */
 public XmlTypePageReader (String contractPath, Document doc)
 {
  this.contractPath = contractPath;
  this.doc = doc;
 }

 protected XmlType convertStructureMetaTable (Node metaTable)
 {
  XmlType type = new XmlType ();
  type.setHasOwnType (false);
  type.setHasOwnState (false);
  type.setHasOwnCreateUpdate (false);
  type.setService ("");
  type.setExamples ("");
  type.setComments ("");
  type.setUrl (contractPath);
  for (NameValue nv : getNameValuePairs ())
  {
   if (nv.name.equalsIgnoreCase ("structureName"))
   {
    type = new XmlType ();
    type.setUrl (contractPath);
    type.setName (fixup (nv.value));
   }
   else if (nv.name.equalsIgnoreCase ("structureVersion"))
   {
    type.setVersion (fixup (nv.value));
   }
   else if (nv.name.equalsIgnoreCase ("type"))
   {
    type.setPrimitive (fixup (nv.value));
   }
   else if (nv.name.equalsIgnoreCase ("description"))
   {
    type.setDesc (fixup (nv.value));
   }
   else if (nv.name.equalsIgnoreCase ("structureVersionHistory"))
   {
    // ignore
   }
   else
   {
    throw new DictionaryValidationException ("unknown td id='" + nv.name +
     "'.");
   }
  }
  return type;
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

  Node metaTable = getStructureMetaTableNode ();
  if (metaTable == null)
  {
   throw new DictionaryValidationException ("Could not find table that holds the structureMetaTable in URL " +
    contractPath);
  }
  xmlType = this.convertStructureMetaTable (metaTable);
  return xmlType;
 }

 protected Node getStructureMetaTableNode ()
 {
  return new NodeHelper ().getNodeWithAttribute (doc, "table", "id", "structureMetaTable");
 }

 private Node getTypeThNode ()
 {
  for (Node node :
       new NodeHelper ().findNodesWithAttribute (doc, "th", "class", "confluenceTh"))
  {
   Node firstChild = node.getFirstChild ();
   if (firstChild != null)
   {
    if (firstChild.getNodeName ().equalsIgnoreCase ("#Text"))
    {
     if (firstChild.getNodeValue ().equalsIgnoreCase ("Type"))
     {
      return node;
     }
    }
   }
  }
  return null;
 }

 private String getType ()
 {
  Node typeThNode = getTypeThNode ();
  if (typeThNode == null)
  {
   new NodeHelper ().dump (typeThNode, System.out);
   throw new DictionaryExecutionException ("Could not find a Table Header cell with a value of Type");
  }
  Node tableHeaderRow = typeThNode.getParentNode ();
  if (tableHeaderRow == null)
  {
   new NodeHelper ().dump (typeThNode, System.out);
   throw new DictionaryExecutionException ("table header cell does not have a parent");
  }
  if ( ! tableHeaderRow.getNodeName ().equalsIgnoreCase ("tr"))
  {
   new NodeHelper ().dump (tableHeaderRow, System.out);
   throw new DictionaryExecutionException ("table header row is not named tr");
  }
  Node tableDataRow = tableHeaderRow.getNextSibling ();
  if (tableDataRow == null)
  {
   new NodeHelper ().dump (tableHeaderRow, System.out);
   throw new DictionaryExecutionException ("table header row does not have a next sibling");
  }
  if ( ! tableDataRow.getNodeName ().equalsIgnoreCase ("tr"))
  {
   new NodeHelper ().dump (tableDataRow, System.out);
   throw new DictionaryExecutionException ("table data row is not named tr");
  }
  Node typeTdNode = tableDataRow.getFirstChild ();
  if (typeTdNode == null)
  {
   new NodeHelper ().dump (tableDataRow, System.out);
   throw new DictionaryExecutionException ("table data row does not have a first child");
  }
  if ( ! typeTdNode.getNodeName ().equalsIgnoreCase ("td"))
  {
   new NodeHelper ().dump (typeTdNode, System.out);
   throw new DictionaryExecutionException ("Type value node was not a TD as expected was " +
    typeTdNode.getNodeName ());
  }
  //new NodeHelper ().dump (typeTdNode, System.out);
  Node firstChild = typeTdNode.getFirstChild ();
  if (firstChild.getNodeName ().equalsIgnoreCase ("#Text"))
  {
   String primitive = firstChild.getNodeValue ();
   if ( ! primitive.toLowerCase ().startsWith ("mapped"))
   {
    return primitive;
   }
   Node nextSibling = firstChild.getNextSibling ();
   if (nextSibling == null)
   {
    new NodeHelper ().dump (typeTdNode, System.out);
    throw new DictionaryExecutionException ("Type value node started with 'mapped' but it didn't have a sibling that described what it was mapped to " +
     typeTdNode.getNodeName ());
   }
   Node firstChildOfSibling = nextSibling.getFirstChild ();
   if (firstChildOfSibling == null)
   {
    new NodeHelper ().dump (typeTdNode, System.out);
    throw new DictionaryExecutionException ("Type value node started with 'mapped' but it's sibling didn't have a first child to hold what it was mapped to " +
     typeTdNode.getNodeName ());
   }
   if (firstChildOfSibling.getNodeName ().equalsIgnoreCase ("#Text"))
   {
    return "Mapped " + firstChildOfSibling.getNodeValue ();
   }
   new NodeHelper ().dump (typeTdNode, System.out);
   throw new DictionaryExecutionException ("Type value node started with 'mapped' but it's sibling didn't have a first child named #Text to hold what it was mapped to " +
    typeTdNode.getNodeName ());
  }
  throw new DictionaryExecutionException ("type value node does not have a first child named #Text, was " +
   firstChild.getNodeName ());
 }

 protected String getDescription ()
 {
  List<Node> list =
   new NodeHelper ().findNodesWithNameValue (doc, "#text", "Description");
  if (list.size () == 0)
  {
   throw new DictionaryExecutionException ("No text nodes with description found for xmltype " +
    contractPath);
  }
  for (Node node : list)
  {
   Node h3Node = node.getParentNode ();
   if (h3Node.getNodeName ().equalsIgnoreCase ("h3"))
   {
    Node pNode = h3Node.getNextSibling ();
    if (pNode != null)
    {
     NodeList children = pNode.getChildNodes ();
     for (int i = 0; i < children.getLength (); i ++)
     {
      Node textNode = children.item (i);
      if (textNode.getNodeName ().equalsIgnoreCase ("#Text"))
      {
       return textNode.getNodeValue ();
      }
     }
    }
   }
  }
  System.out.println ("these are the nodes that I did find...");
  for (Node node : list)
  {
   new NodeHelper ().dump (node, System.out);
  }
  throw new DictionaryExecutionException ("Could not locate description of xmlType " +
   contractPath);
 }

 protected List<NameValue> getNameValuePairs ()
 {
  List<NameValue> list = new ArrayList ();
  appendNameValuePairsFromStructureTable (list, getStructureMetaTableNode ());
  list.add (new NameValue ("type", getType (), null));
  list.add (new NameValue ("description", getDescription (), null));
  return list;
 }

 protected List<NameValue> getNameValuePairsFromStructureMetaTable (
  Node metaTable)
 {
  List<NameValue> list = new ArrayList ();
  appendNameValuePairsFromStructureTable (list, metaTable);
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
     if (attr.getNodeName ().equalsIgnoreCase ("id"))
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
    if (nv.value == null)
    {
     nv.value = "";
    }
    nv.value = nv.value + child.getNodeValue ();
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
