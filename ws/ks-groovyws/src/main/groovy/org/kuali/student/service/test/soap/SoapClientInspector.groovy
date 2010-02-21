/**
 * 
 */
package org.kuali.student.service.test.soap
import groovyx.net.ws.WSClient
import javax.xml.namespace.QName
import org.apache.cxf.endpoint.Client 
import org.apache.cxf.endpoint.Endpoint

/**
 * @author randy
 *
 */
public class SoapClientInspector{
	WSClient proxy
	Closure invoker
	public SoapClientInspector(String url) {
		init(url)
	}
	   void init(url) {
	        proxy = new WSClient(url, this.class.classLoader)
	        
	        println 'created proxy: ' + proxy + ' for ' + url
	        proxy.initialize()
	        invoker = proxy.&invokeMethod
	        println 'proxy dump: ' + proxy.dump()
      
       Client client = proxy.client
        println 'created client: ' + client
	    }
	/**
	 * @param args
	 */
	public static void main(def args){
		SoapClientInspector sci = new SoapClientInspector('http://kuali.berkeley.edu:18080/ks-brms/services/RuleManagementService?wsdl')
	}
	
}
