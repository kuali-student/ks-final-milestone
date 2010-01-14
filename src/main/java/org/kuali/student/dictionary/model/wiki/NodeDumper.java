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

import java.io.PrintStream;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author nwright
 */
public class NodeDumper
{

 private Node node;
 private PrintStream out;

 public NodeDumper (Node node, PrintStream out)
 {
  this.node = node;
  this.out = out;
 }

 public void dump ()
 {
  dump (node, 1);
 }

 public void dump (Node node, int level)
 {
  print (node, level);
  NodeList children = node.getChildNodes ();
  for (int i = 0; i < children.getLength (); i ++)
  {
   Node child = children.item (i);
   dump (child, level + 1);
  }
 }

 private void print (Node node, int level)
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
   for (int i = 0; i < attributes.getLength (); i++)
   {
    Node attr = attributes.item (i);
    out.print (comma + attr.getNodeName () + "=" + attr.getNodeValue ());
    comma = ", ";
   }
  }
  out.println ("");
 }

}
