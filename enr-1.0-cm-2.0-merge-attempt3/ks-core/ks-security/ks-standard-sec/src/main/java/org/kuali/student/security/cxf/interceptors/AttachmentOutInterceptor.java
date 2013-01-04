package org.kuali.student.security.cxf.interceptors;

import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

public class AttachmentOutInterceptor extends AbstractPhaseInterceptor<Message> {
    public AttachmentOutInterceptor() {
        super(Phase.PRE_PROTOCOL);
    }

    public void handleMessage(Message message) {
        System.out.println("\n\n In the  AttachmentOutInterceptor on the bus that does nothing ...... \n" +
                " inbound message ... "+ message.getContextualProperty(Message.INBOUND_MESSAGE) + "\n" +
                " wsdl service .... " + message.getContextualProperty(Message.WSDL_SERVICE) + "\n" +
                " wsdl operation .... " + message.getContextualProperty(Message.WSDL_OPERATION) + "\n" +
                " request uri .... " + message.getContextualProperty(Message.REQUEST_URI) + "\n" +
                " path info .... " + message.getContextualProperty(Message.PATH_INFO) + "\n"
        );
    }

    public void handleFault(Message messageParam) {
    }

}
