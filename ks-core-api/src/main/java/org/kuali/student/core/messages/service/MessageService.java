package org.kuali.student.core.messages.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.core.messages.dto.LocaleKeyList;
import org.kuali.student.core.messages.dto.Message;
import org.kuali.student.core.messages.dto.MessageGroupKeyList;
import org.kuali.student.core.messages.dto.MessageList;


@WebService(name = "MessageService", targetNamespace = "http://student.kuali.org/wsdl/messages")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface MessageService {

	 @WebMethod
	    public LocaleKeyList getLocales();

	    @WebMethod
	    public MessageGroupKeyList getMessageGroups();

	    @WebMethod
	    public Message getMessage(
	            @WebParam(name = "localeKey") String localeKey, 
	            @WebParam(name = "messageGroupKey") String messageGroupKey, 
	            @WebParam(name = "messageKey")   String messageKey);
	    
	    @WebMethod
	    public MessageList getMessages(
	            @WebParam(name = "localeKey") String localeKey, 
	            @WebParam(name = "messageGroupKey") String messageGroupKey);
	    
	    @WebMethod
	    public MessageList getMessagesByGroups(
	            @WebParam(name = "localeKey") String localeKey, 
	            @WebParam(name = "messageGroupKeyList") MessageGroupKeyList messageGroupKeyList);
	    
	    @WebMethod
	    public Message updateMessage(
	    		@WebParam(name = "localeKey") String localeKey, 
	            @WebParam(name = "messageGroupKey") String messageGroupKey, 
	            @WebParam(name = "messageKey")  String messageKey,
	            @WebParam(name = "messageInfo") Message messageInfo);
	    
	    @WebMethod
	    public Message addMessage(@WebParam(name = "messageInfo") Message messageInfo);
	    
}
