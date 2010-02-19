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
import org.kuali.student.dictionary.model.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author nwright
 */
public class ServiceRepositoryPageReader
{

 private String contractPath;
 private String jSessionId;
 private Document doc;

 public ServiceRepositoryPageReader (String contractPath, String jSessionId)
 {
  this.contractPath = contractPath;
  this.jSessionId = jSessionId;
  URL url = new UrlHelper (contractPath).getUrl ();
  doc = new PageHelper ().getDocument (url, jSessionId);
 }

  public ServiceRepositoryPageReader (File contractFile)
 {
 contractPath = contractFile.getName ();
  doc = new PageHelper ().getDocument (contractFile);
 }



 protected List<Service> getServices ()
 {
  List<Service> list = new ArrayList ();
  for (Node node : getHtmlLinkNodes ())
  {
   Service service = new Service ();
   Node hrefNode = new NodeHelper ().getAttribute (node, "href");
   service.setUrl (hrefNode.getNodeValue ());
   if (service.getUrl ().endsWith ("LU+Stuff+Service"))
   {
    System.out.println ("Not processing link because it is a STUFF service" +
     service.getUrl ());
    continue;
   }
   if ( ! service.getUrl ().contains ("KULSTU"))
   {
    System.out.println ("Not processing service because not KULSTU " + service.
     getUrl ());
    continue;
   }
   if (service.getUrl ().contains ("+Service+v"))
   {
    list.add (service);
    extractLoad (service, node);
    continue;
   }
   if (service.getUrl ().endsWith ("+Service"))
   {
    list.add (service);
    extractLoad (service, node);
    continue;
   }
   System.out.println ("Not processing link because not end in [+Service[+v*] " +
    service.getUrl ());
  }
  return list;
 }

 private void extractLoad (Service service, Node node)
 {
  NodeList children = node.getChildNodes ();
  for (int i = 0; i < children.getLength (); i ++)
  {
   Node child = children.item (i);
   if (child.getNodeName ().equalsIgnoreCase ("#text"))
   {
    extractLoad (service, child.getNodeValue ());
   }
  }
 }

 protected void extractLoad (Service service, String value)
 {
  int loc = value.indexOf (" Service v");
  if (loc != -1)
  {
   service.setName (value.substring (0, loc).trim ());
   service.setVersion (value.substring (loc + " Service".length ()).trim ());
   service.setKey (calculateServiceKey (service.getName ()));
   return;
  }
  loc = value.indexOf (" Service");
  if (loc == -1)
  {
   throw new DictionaryExecutionException ("url does not match pattern value=" +
    value);
  }
  service.setName (value.substring (0, loc).trim ());
  service.setVersion ("Dev");
  service.setKey (calculateServiceKey (service.getName ()));
 }

 protected String calculateServiceKey (String name)
 {
  // hard coded maps
  if (name.equals ("AZ Group"))
  {
   return "azgroup";
  }
  if (name.equals ("Business Rules Management"))
  {
   return "brms";
  }
  if (name.equals ("Enumeration Management"))
  {
   return "enumerationmanagement";
  }

  //
  // only one word just lowercase it
  if (name.indexOf (" ") == -1)
  {
   return name.toLowerCase ();
  }

  // multiple words
  // convert to initials
  // i.e. Academic Time Period => atp
  // include any internal uppercase
  StringBuffer key = new StringBuffer ();
  boolean foundSpace = true;
  for (int i = 0; i <
   name.length (); i ++)
  {
   char c = name.charAt (i);
   if (foundSpace)
   {
    key.append (c);
    foundSpace =
     false;
   }
   else if (Character.isUpperCase (c))
   {
    key.append (c);
   }

   foundSpace = Character.isWhitespace (c);
  }

  return key.toString ().toLowerCase ();
 }

 protected List<Node> getHtmlLinkNodes ()
 {
  return new NodeHelper ().findNodesWithAttribute (doc, "a", "href");
 }

}
