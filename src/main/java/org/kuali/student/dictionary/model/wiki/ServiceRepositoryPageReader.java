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

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
  PageTrimmer trimmer =
   new BeginEndPageTrimmer ("ServiceDescriptionRepository-CurrentReleases",
                            "ServiceDescriptionRepository-Background");
  URL url = new UrlHelper (contractPath).getUrl ();
  doc =
   new PageHelper ().getDocument (url, jSessionId);
 }

 protected List<Service> getServices ()
 {
  List<Service> list = new ArrayList ();
  for (Node node : getHtmlLinkNodes ())
  {
   Service service = new Service ();
   Node hrefNode = new NodeHelper ().getAttribute (node, "href");
   service.setUrl (hrefNode.getNodeValue ());
   if (service.getUrl ().toLowerCase ().contains ("service"))
   {
    list.add (service);
    extractLoad (service, node);
   }
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
    service.setName (child.getNodeValue ());
   }
  }
 }


 protected List<Node> getHtmlLinkNodes ()
 {
  List<Node> list = new ArrayList (50);
  appendHtmlLinkNodes (list, doc);
  return list;
 }

 protected void appendHtmlLinkNodes (List<Node> list, Node node)
 {
  if (node.getNodeName ().equalsIgnoreCase ("a"))
  {
   list.add (node);
   // there are no nested method tables
   return;
  }
  NodeList children = node.getChildNodes ();
  for (int i = 0; i < children.getLength (); i ++)
  {
   Node child = children.item (i);
   appendHtmlLinkNodes (list, child);
  }
 }

}
