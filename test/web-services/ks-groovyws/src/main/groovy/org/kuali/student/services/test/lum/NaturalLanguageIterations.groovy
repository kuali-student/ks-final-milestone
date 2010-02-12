/**
 * 
 */
package org.kuali.student.services.test.lum

import groovyx.net.ws.WSClient



/**
 * @author randy
 *
 */
public class NaturalLanguageIterations{
	WSClient proxy
    def urlBase = 'http://kuali.berkeley.edu:8080'
    def servicePath = '/ks-embedded-dev/services'
    def url = 
    	urlBase + servicePath+  '/TranslationService?wsdl'
    
    
	/**
	 * 
	 */
	public NaturalLanguageIterations(){
    	
		 init()
	}
	void init() {
		println 'init'
		proxy = new WSClient(url, this.class.classLoader)
		println 'created proxy: ' + proxy
		proxy.initialize()
    	println 'end init'
	}
	public void run() {
		testTranslateReqComponent()
		
	}
	void testTranslateReqComponent() {
		def result = proxy.getNaturalLanguageForReqComponent("REQCOMP-NL-1", "KUALI.CATALOG", "en")
		println 'got nl: ' + result
	
	   // def counts = [10, 100, 1000, 10000]
	    // def counts = [10, 100, 100000]
		def counts = [1, 10]
		for (c in counts) {
			testTranslateReqComponentIteration(c)
		}
	}
	void testTranslateReqComponentIteration(int count) {
		println 'trying ' + count
		def ok = 0
		def exceptionCount = 0
		def totalTime = 0
		def avgTime = 0
		
		1.upto(count) {
			try {
				def startTime = System.currentTimeMillis()
				def result = proxy.getNaturalLanguageForReqComponent("REQCOMP-NL-1", "KUALI.CATALOG", "en")
				assert result != null, "failed after '$ok' tries for count '$count' totalTime: $totalTime, averTime: $avgTime"
				ok++
				def elapsed = System.currentTimeMillis() - startTime
				totalTime += elapsed
				avgTime = totalTime / ok
				Thread.sleep(500)
			
			}catch (Exception e) {
				println 'exception ' + e.message
				exceptionCount++
			}
		}
		
		println('attempts:  ' + count + ' success: ' 
			+ ok + ' exceptions: ' + exceptionCount)
		println 'average round trip time ' + avgTime + ' ms.'
	}

	/**
	 * @param args
	 */
	public static void main(def args){
		NaturalLanguageIterations nli =	new NaturalLanguageIterations()
		nli.run()
		println 'run completed'
		return
	}
	
}
