
package org.kuali.student.poc.common.cxf.security;

import java.util.Vector;

import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.apache.ws.security.WSSecurityEngineResult;
import org.apache.ws.security.WSUsernameTokenPrincipal;
import org.apache.ws.security.handler.WSHandlerConstants;
import org.apache.ws.security.handler.WSHandlerResult;
import org.kuali.student.poc.common.ws.security.PrincipalWrapper;

/**
 * 
 * @author Will Gomes
 *
 */
public class PrincipalWrapperImpl extends PrincipalWrapper {

    @Override
    public void setPrincipal(WebServiceContext wsContext) {
        MessageContext context = wsContext.getMessageContext();
        Vector result = (Vector)context.get(WSHandlerConstants.RECV_RESULTS);
        
        for (int i = 0; i < result.size(); i++) { 
            WSHandlerResult res = (WSHandlerResult) result.get(i); 
            for (int j = 0; j < res.getResults().size(); j++) { 
                WSSecurityEngineResult secRes = (WSSecurityEngineResult) res.getResults().get(j);
                //WSSecurityEngineResult secRes =  WSSecurityUtil.fetchActionResult(result, WSConstants.UT);
                principal = (WSUsernameTokenPrincipal)secRes.get("principal");                 
            }
        }       
    }
    
}
