package org.kuali.student.security.cxf.interceptors;

import java.io.StringWriter;
import java.util.Vector;

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
import org.apache.ws.security.handler.RequestData;
import org.apache.ws.security.processor.SAMLTokenProcessor;
import org.opensaml.SAMLAssertion;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.cas.CasAuthenticationToken;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class SamlTokenCxfInInterceptor extends WSS4JInInterceptor{

    @Override
    protected void computeAction(SoapMessage msg, RequestData reqData) {
        super.computeAction(msg, reqData);
    }

    @Override
    protected void doResults(SoapMessage msg, String actor, SOAPMessage doc, Vector wsResult) throws SOAPException, XMLStreamException {
        super.doResults(msg, actor, doc, wsResult);
        
        QName wsseQN = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Security");
        if(msg.hasHeader(wsseQN)){
            Header wsseHeader = msg.getHeader(wsseQN);
            System.out.println("THE HEADER : " + wsseHeader.getName().toString());
            
            if(wsseHeader != null){
                Node domSecurityHeader = (Node) wsseHeader.getObject();
                NodeList nodeList = domSecurityHeader.getChildNodes();
                Node childNode = null;
                for(int i=0; i < nodeList.getLength(); i++){
                    childNode = nodeList.item(i);
                    System.out.println("CHILD NODE " + i + " : " + childNode.getLocalName());
                    if(childNode.getNodeName().equals("Assertion")){
                        break;
                    }
                }
                
                if(childNode.getNodeType() == Node.ELEMENT_NODE ){
                    System.out.println("ITS an ELEMENT NODE");
                
                
                    SAMLTokenProcessor stp = new SAMLTokenProcessor();
                    try {
                        SAMLAssertion samlAssertion = stp.handleSAMLToken((Element)childNode);
                        System.out.println("THE SAML ISSUER : " + samlAssertion.getIssuer());
                        
                        CasAuthenticationToken cat = (CasAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
                        cat.setDetails(samlAssertion);
                        
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                
                // This prints out the security header, used for testing, leave here for now.
                DOMSource domSource = new DOMSource(domSecurityHeader);
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
                System.out.println(writer.toString());
            }
                       
            // This code below gets the entire SOAP Mesage, the msg.getContentFormats() Classes are
            // org.w3c.dom.Node, javax.xml.stream.XMLStreamReader, java.io.InputStream
            //Node doc = msg.getContent(Node.class);
        }
        
        System.out.println("LAST PRINT IN HANDLE MESSAGE, the phase : " + this.getPhase() );
    }

    @Override
    public void handleMessage(SoapMessage msg) throws Fault {
        super.handleMessage(msg);
    }

    @Override
    public void setIgnoreActions(boolean i) {
        super.setIgnoreActions(i);
    }

}
