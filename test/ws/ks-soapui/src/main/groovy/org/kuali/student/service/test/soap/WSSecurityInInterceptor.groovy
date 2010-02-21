/**
 * 
 */
package org.kuali.student.service.test.soap

import java.util.Map
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor
import java.util.Properties

import javax.security.auth.callback.CallbackHandler


import org.apache.ws.security.components.crypto.Crypto
import org.apache.ws.security.components.crypto.Merlin
import org.apache.ws.security.handler.RequestData
import org.apache.ws.security.handler.WSHandlerConstants


/**
 * @author randy
 *
 */
public class WSSecurityInInterceptor extends WSS4JInInterceptor{

	
	/* (non-Javadoc)
	 * @see org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor#WSS4JInInterceptor()
	 */
	public WSSecurityInInterceptor(){
		// TODO Auto-generated method stub
	}
	
	/* (non-Javadoc)
	 * @see org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor#WSS4JInInterceptor(java.util.Map)
	 */
	public WSSecurityInInterceptor(Map properties){
		// TODO Auto-generated method stub
	}
	public WSSecurityInInterceptor(Class<? extends CallbackHandler> securityCallback, 
			String keystoreAlias) {
        this.setProperty(WSHandlerConstants.ACTION, WSHandlerConstants.SIGNATURE);
        this.setProperty(WSHandlerConstants.PW_CALLBACK_CLASS, securityCallback.getName());
        this.setProperty(WSHandlerConstants.SIG_KEY_ID, "IssuerSerial");
        this.setProperty(WSHandlerConstants.USER, keystoreAlias);
    }
	@Override
    public Crypto loadSignatureCrypto(RequestData reqData) {
		println 'InInterceptor loadSignatureCrypto: ' + reqData
        try {
            return MerlinCryptoHelper.create()
        } catch (Exception e) {
            throw new RuntimeException(e)
        }
    }

    @Override
    public Crypto loadDecryptionCrypto(RequestData reqData) {
        return loadSignatureCrypto(reqData)
    }

	
	
}
