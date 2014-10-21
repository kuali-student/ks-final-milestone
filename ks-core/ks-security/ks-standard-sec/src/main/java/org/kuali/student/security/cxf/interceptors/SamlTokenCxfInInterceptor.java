/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.security.cxf.interceptors;

import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.wsdl.extensions.soap.SOAPBody;
import javax.wsdl.extensions.soap.SOAPHeader;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.ws.security.WSSecurityEngineResult;
import org.apache.ws.security.WSSecurityException;
import org.apache.ws.security.handler.RequestData;
import org.apache.ws.security.processor.SAMLTokenProcessor;
import org.apache.ws.security.validate.Credential;
import org.opensaml.SAMLAssertion;
import org.springframework.security.cas.authentication.CasAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SamlTokenCxfInInterceptor extends WSS4JInInterceptor {

	private String samlIssuerForUser = null;

	public SamlTokenCxfInInterceptor(Map<String, Object> properties) {
		super(properties);
	}

	@Override
	protected void computeAction(SoapMessage msg, RequestData reqData) throws WSSecurityException {
		super.computeAction(msg, reqData);
	}

	@Override
    protected void doResults(SoapMessage msg, String actor, Element soapHeader, Element soapBody, List<WSSecurityEngineResult> wsResult) throws javax.xml.soap.SOAPException, javax.xml.stream.XMLStreamException, org.apache.ws.security.WSSecurityException {
		super.doResults(msg, actor, soapHeader, soapBody, wsResult);

		QName wsseQN = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Security");
		if (msg.hasHeader(wsseQN)) {
			Header wsseHeader = msg.getHeader(wsseQN);

			if (wsseHeader != null) {
				Node domSecurityHeader = (Node) wsseHeader.getObject();
				NodeList nodeList = domSecurityHeader.getChildNodes();
				Node childNode = null;

				for (int i = 0; i < nodeList.getLength(); i++) {
					childNode = nodeList.item(i);

					if ((childNode.getNodeName().equals("Assertion")) && (childNode.getNodeType() == Node.ELEMENT_NODE)) {
						SAMLTokenProcessor stp = new SAMLTokenProcessor();

						try {
                            //TODO: KSENROLL-4172 this will probably fail if it ever gets invoked!!!
                            Credential credential = stp.handleSAMLToken((Element) childNode, null, null, null);
							SAMLAssertion samlAssertion = null;

							if (samlAssertion.getIssuer().equals(samlIssuerForUser)) {
								CasAuthenticationToken cat = (CasAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
								cat.setDetails(samlAssertion);
								break;
							}
						} catch (Exception e) {
							throw new RuntimeException(e);
						}
					}
				}
			}

			System.out.println("\n\n THE WHOLE MESSAGE RECEIVED IN INTERCEPTOR ...... ");
			Node env = msg.getContent(Node.class);
			DOMSource domSource = new DOMSource(env);
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);

			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer;
			try {
				transformer = tf.newTransformer();
				transformer.transform(domSource, result);
			} catch (Exception e) {
				e.printStackTrace();
			}
			writer.flush();
			System.out.println(writer.toString());

		}
	}

	@Override
	public void handleMessage(SoapMessage msg) throws Fault {
		super.handleMessage(msg);
	}

	@Override
	public void setIgnoreActions(boolean i) {
		super.setIgnoreActions(i);
	}

	public void setSamlIssuerForUser(String samlIssuerForUser) {
		this.samlIssuerForUser = samlIssuerForUser;
	}

    public String getSamlIssuerForUser() {
        return samlIssuerForUser;
    }

}
