/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.common.ws.handler;

import java.io.PrintStream;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.apache.log4j.Logger;

public class DebugHandler implements SOAPHandler<SOAPMessageContext> {

    final Logger logger = Logger.getLogger(DebugHandler.class);
    
	private PrintStream err;
	private PrintStream out;
	
	public DebugHandler()	{
		this(System.out, System.err);
	}
	
	public DebugHandler(PrintStream ps)	{
		this(ps, ps);
	}
	
	public DebugHandler(PrintStream out, PrintStream err)	{
		this.out = out;
		this.err = err;
	}
	
	@Override
	public Set<QName> getHeaders() {
		// does not handle any headers
		return null;
	}

	@Override
	public void close(MessageContext context) {
		// no cleanup needed
	}

	@Override
	public boolean handleFault(SOAPMessageContext context) {
		return this.printMessage(context, this.err);
	}

	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		return this.printMessage(context, this.out);
	}
	
	private boolean printMessage(SOAPMessageContext context, PrintStream ps)	{
		String message = "DebugHandler ";
		
		Boolean out = (Boolean)context.get(SOAPMessageContext.MESSAGE_OUTBOUND_PROPERTY);
		if(out.booleanValue())
			message += "Out: ";
		else
			message += "In: ";
		
		ps.print(message);
		
		try	{
			context.getMessage().writeTo(ps);
			ps.println();
		}
		catch(Exception ex)	{
		    logger.error(this.err, ex);
		}
		
		return true;
	}

}
