/**
 * 
 */
package org.kuali.student.service.test.soap
import groovyx.net.ws.WSClient


/**
 * Iteratively test a SOAP service method
 * @author Kuali Student Deployment Lab
 *
 */
public class ServiceIterationsTest{
	WSClient proxy
	Closure invoker
	
	/**
	 * Create a ServiceIterationsTest instance
	 * @param url the url to fetch WSDL
	 */
	public ServiceIterationsTest(String url){
		init(url)
		
	}
	/**
	 * Create the client and method invocation closure
	 * @param  url the url to fetch WSDL
	 */
	void init(url) {
		proxy = new WSClient(url, this.class.classLoader)
		
		println 'created proxy: ' + proxy + ' for ' + url
		proxy.initialize()
		invoker = proxy.&invokeMethod
	}
	/**
	 * Execute an operation given number of times
	 * @param count number of executions
	 * @param methodName the operation to invoke
	 * @param args varargs array of parameters
	 */
	public void execute( int count, String methodName, Object... args ) {
		println 'execute method: ' + methodName + ' with args: ' + args
		def ok = 0
		def exceptions = 0
		def totalTime = 0
		def avgTime = 0
		
		
		1.upto(count) {
			try {
				def startTime = System.currentTimeMillis()
				def result = invoker(methodName, args)
				assert result != null, "failed after '$ok' tries for count '$count' totalTime: $totalTime, averTime: $avgTime"
				if (ok < 1) 
					println 'result: ' + result
				ok++
				def elapsed = System.currentTimeMillis() - startTime
				totalTime += elapsed
				avgTime = totalTime / ok
				Thread.sleep(500)
			}catch (Exception e) {
				println 'exception ' + e.message
				exceptions++
			}
		}
		println('attempts:  ' + count + ' success: ' 
				+ ok + ' exceptions: ' + exceptions)
		println 'average round trip time ' + avgTime + ' ms.'
	}


	/**
	 * @param args
	 */
	public static void main(def args){
		 // BRMS deployed with CXF
       // ServiceIterationsTest t0 = new ServiceIterationsTest(
        //		'http://kuali.berkeley.edu:18080/ks-brms/services/RuleManagementService?wsdl')
       //  t0.execute(2, "getAgendaTypes")
          
		// ks milestone 2 
		 def urlBase = 'http://kuali.berkeley.edu:8080'
	     def servicePath = '/ks-embedded-dev/services'
	     def url = urlBase + servicePath +  '/LuService?wsdl'
	   
        // LU Service
		ServiceIterationsTest t1 = new ServiceIterationsTest(url)
		t1.execute(2, "getClu", "CLU-1" )
       
		// Rules translation service
		url = urlBase + servicePath +  '/TranslationService?wsdl'
		ServiceIterationsTest t2 = new ServiceIterationsTest(url)
		
		// english
		t2.execute(2, "getNaturalLanguageForReqComponent", 
				'REQCOMP-NL-1', 'KUALI.CATALOG', 'en')
		// german
 		t2.execute(2, "getNaturalLanguageForReqComponent", 
 				'REQCOMP-NL-1', 'KUALI.CATALOG', 'de')
	}
	
}
