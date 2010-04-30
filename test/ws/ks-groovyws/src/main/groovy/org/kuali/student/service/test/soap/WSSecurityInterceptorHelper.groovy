/**
 * 
 */
package org.kuali.student.service.test.soap
import groovyx.net.ws.WSClient
import org.apache.cxf.endpoint.Client 
import org.apache.cxf.endpoint.Endpoint


/**
 * Helper class to add WSCallbackHandler and 
 * in/out wss4j security interceptors to a WSClient proxy
 * @author randy
 *
 */
public class WSSecurityInterceptorHelper{

	/**
	 * 
	 */
	private WSSecurityInterceptorHelper(){
		// TODO Auto-generated method stub
	}
	static void addInterceptors(WSClient proxy) {
		
		WSSecurityInInterceptor inInterceptor  = 
			new WSSecurityInInterceptor(WSCallbackHandler.class,
				MerlinCryptoHelper.keystoreAlias)
		println ' created in interceptor:  ' + inInterceptor.dump()
		WSSecurityOutInterceptor outInterceptor  = 
            new WSSecurityOutInterceptor(WSCallbackHandler.class,
                MerlinCryptoHelper.keystoreAlias)
		println ' created out interceptor:  ' + outInterceptor.dump()
		 Client client = proxy.client
        println 'got proxy client: ' + client
        println ' dump client: ' + client.dump()
        
		Endpoint cxfEndpoint = client.endpoint
		println 'got endpoint: ' + cxfEndpoint
		println 'dump endpoint: ' + cxfEndpoint.dump()
		def inInter = cxfEndpoint.inInterceptors
		def outInter = cxfEndpoint.outInterceptors
	
		println  'got in interceptors: ' + inInter
        println ' got out interceptors: ' + outInter
        if (inInter == null ) inInter = []
        if (outInter == null ) outInter = []
        inInter.add(inInterceptor)
        outInter.add(outInterceptor)
        cxfEndpoint.inInterceptors = inInter
        cxfEndpoint.outInterceptors = outInter
		/*cxfEndpoint.inInterceptors.add(inInterceptor)
		cxfEndpoint.outInterceptors.add(outInterceptor)*/
		println ' interceptors added to endpoint: ' + cxfEndpoint.dump()
		println ' in interceptors are: ' + cxfEndpoint.inInterceptors
		println ' out interceptors are: ' + cxfEndpoint.outInterceptors
		println 'interceptors added to proxy: ' + proxy.dump()
	}
	
	
}
