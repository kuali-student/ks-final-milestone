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

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.kuali.student.dictionary.DictionaryExecutionException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
//import org.w3c.tidy.Tidy;

/**
 *
 * @author nwright
 */
public class NodeHelper
{

 public void dump (Node node, PrintStream out)
 {
  dump (node, out, 1);
 }

 public void dump (Node node, PrintStream out, int level)
 {
  dumpNode (node, out, level);
  NodeList children = node.getChildNodes ();
  for (int i = 0; i < children.getLength (); i ++)
  {
   Node child = children.item (i);
   dump (child, out, level + 1);
  }
 }

 public void dumpNode (Node node, PrintStream out, int level)
 {
  for (int i = 0; i < level; i ++)
  {
   out.print (" ");
  }
  out.print (node.getNodeName () + "=" + node.getNodeValue ());
  NamedNodeMap attributes = node.getAttributes ();
  if (attributes != null)
  {
   String comma = " ";
   for (int i = 0; i < attributes.getLength (); i ++)
   {
    Node attr = attributes.item (i);
    out.print (comma + attr.getNodeName () + "=" + attr.getNodeValue ());
    comma = ", ";
   }
  }
  out.println ("");
 }

 public DocumentBuilder getBuilder ()
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
  return builder;
 }

 public Document getDocument (InputStream in)
 {
  Document doc;
  // this is to use TagSoup but couldn't quite get it working
//  Parser parser = new Parser ();
//  SAX2DOM sax2dom = null;
//  try
//  {
//   sax2dom = new SAX2DOM ();
//   parser.setContentHandler (sax2dom);
//   parser.setFeature (Parser.namespacesFeature, false);
//   parser.parse (in);
//  }
//  catch (Exception e)
//  {
//   e.printStackTrace ();
//  }
//  doc = sax2dom.getDOM ();
//
//  Tidy tidy = new Tidy ();
//  tidy.setQuiet (true);
//  tidy.setShowWarnings (false);
//  doc = tidy.parseDOM (in, null);

// this uses the default SAX parser
//  but had problems with it because it insisted on no html coding errors
  try
  {
   doc = getBuilder ().parse (in);
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

 public Node getNodeWithAttribute (Node node, String name, String attrName,
                                   String attrValue)
 {
  if (node.getNodeName ().equalsIgnoreCase (name))
  {
   if (hasAttribute (node, attrName, attrValue))
   {
    return node;
   }
  }
  NodeList children = node.getChildNodes ();
  for (int i = 0; i < children.getLength (); i ++)
  {
   Node child = children.item (i);
   Node found = getNodeWithAttribute (child, name, attrName, attrValue);
   if (found != null)
   {
    return found;
   }
  }
  return null;
 }

 public List<Node> findNodesWithAttribute (Node node, String name,
                                           String attrName,
                                           String attrValue)
 {
  List<Node> list = new ArrayList ();
  appendNodesWithAttribute (list, node, name, attrName, attrValue);
  return list;
 }

 private List<Node> appendNodesWithAttribute (List<Node> list, Node node,
                                              String name, String attrName,
                                              String attrValue)
 {
  if (node.getNodeName ().equalsIgnoreCase (name))
  {
   if (hasAttribute (node, attrName, attrValue))
   {
    list.add (node);
   }
  }
  NodeList children = node.getChildNodes ();
  for (int i = 0; i < children.getLength (); i ++)
  {
   Node child = children.item (i);
   appendNodesWithAttribute (list, child, name, attrName, attrValue);
  }
  return list;
 }

 public List<Node> findNodesWithAttribute (Node node, String name,
                                           String attrName)
 {
  List<Node> list = new ArrayList ();
  appendNodesWithAttribute (list, node, name, attrName);
  return list;
 }

 private List<Node> appendNodesWithAttribute (List<Node> list, Node node,
                                              String name, String attrName)
 {
  if (node.getNodeName ().equalsIgnoreCase (name))
  {
   if (hasAttribute (node, attrName))
   {
    list.add (node);
   }
  }
  NodeList children = node.getChildNodes ();
  for (int i = 0; i < children.getLength (); i ++)
  {
   Node child = children.item (i);
   appendNodesWithAttribute (list, child, name, attrName);
  }
  return list;
 }

 public List<Node> findNodesWithNameValue (Node node, String name,
                                           String value)
 {
  List<Node> list = new ArrayList ();
  appendNodesWithNameValue (list, node, name, value);
  return list;
 }

 private List<Node> appendNodesWithNameValue (List<Node> list, Node node,
                                              String name, String value)
 {
  if (node.getNodeName ().equalsIgnoreCase (name))
  {
   if (node.getNodeValue ().equals (value))
   {
    list.add (node);
   }
  }
  NodeList children = node.getChildNodes ();
  for (int i = 0; i < children.getLength (); i ++)
  {
   Node child = children.item (i);
   appendNodesWithNameValue (list, child, name, value);
  }
  return list;
 }

 public Node getNodeWithAttributeAndValue (Node node, String name,
                                           String attrName,
                                           String attrValue, String value)
 {
  if (node.getNodeName ().equalsIgnoreCase (name))
  {
   if (node.getNodeValue ().equalsIgnoreCase (value))
   {
    if (hasAttribute (node, attrName, attrValue))
    {
     return node;
    }
   }
  }
  NodeList children = node.getChildNodes ();
  for (int i = 0; i < children.getLength (); i ++)
  {
   Node child = children.item (i);
   Node found =
    getNodeWithAttributeAndValue (child, name, attrName, attrValue, value);
   if (found != null)
   {
    return found;
   }
  }
  return null;
 }

 public boolean hasAttribute (Node node, String attrName, String attrValue)
 {
  NamedNodeMap attributes = node.getAttributes ();
  if (attributes == null)
  {
   return false;
  }
  for (int i = 0; i < attributes.getLength (); i ++)
  {
   Node attr = attributes.item (i);
   if (attr.getNodeName ().equalsIgnoreCase (attrName))
   {
    if (attr.getNodeValue ().equalsIgnoreCase (attrValue))
    {
     return true;
    }
   }
  }
  return false;
 }

 public boolean hasAttribute (Node node, String attrName)
 {
  NamedNodeMap attributes = node.getAttributes ();
  if (attributes == null)
  {
   return false;
  }
  for (int i = 0; i < attributes.getLength (); i ++)
  {
   Node attr = attributes.item (i);
   if (attr.getNodeName ().equalsIgnoreCase (attrName))
   {
    return true;
   }
  }
  return false;
 }

 public Node getAttribute (Node node, String name)
 {
  NamedNodeMap attributes = node.getAttributes ();
  if (attributes == null)
  {
   return null;
  }
  for (int i = 0; i < attributes.getLength (); i ++)
  {
   Node attr = attributes.item (i);
   if (attr.getNodeName ().equalsIgnoreCase (name))
   {
    return attr;
   }
  }
  return null;
 }

}
