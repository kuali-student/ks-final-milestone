/**
 * 
 */
package org.kuali.student.services.test.lum

import groovyx.net.ws.WSClient
import java.sql.SQLException



/**
 * @author randy
 *
 */
public class OrgIterations{
	WSClient proxy
    
    def url = 'http://kuali.berkeley.edu:8080/ks-lum/services/OrgService?wsdl'
    
    
	/**
	 * 
	 */
	public OrgIterations(){
    	
		 init()
	}
	void init() {
		println 'init'
		proxy = new WSClient(url, this.class.classLoader)
		println 'created proxy: ' + proxy
		proxy.initialize()
		// proxy.setPreferredSoapVersion("1.2")
    	println 'end init'
	}
	public void run() {
		println 'hello my love, my love goodbye'
		testClu()
		
	}
	void testClu() {
		
		def result = proxy.getOrgTree("4", "kuali.org.hierarchy.Main", 1)
		println 'got result: ' + result
		//println 'orgTree i ' + result[0]
	   // def counts = [10, 100, 1000, 10000]
	    // def counts = [10, 100, 100000]
		def counts = [10, 1000]
		for (c in counts) {
			testCluIteration(c)
		}
	}
	void testCluIteration(int count) {
		println 'trying ' + count
		def ok = 0
		def badSql  = 0
		def badOther = 0
		def totalTime = 0
		def avgTime = 0
		
		1.upto(count) {
			try {
				def startTime = System.currentTimeMillis()
				def result = proxy.getOrgTree("4", "kuali.org.hierarchy.Main", 1)
				assert result != null, "failed after '$ok' tries for count '$count' totalTime: $totalTime, averTime: $avgTime"
				ok++
				def elapsed = System.currentTimeMillis() - startTime
				totalTime += elapsed
				avgTime = totalTime / ok
				Thread.sleep(500)
			}catch (SQLException sqle) {
				println 'exception ' + sqle.message
				badSql++
			}catch (Exception e) {
				println 'exception ' + e.message
				badOther++
			}
		}
		
		println('attempts:  ' + count + ' success: ' 
			+ ok + ' sql exceptions: ' + badSql + " other exceptions: " + badOther)
		println 'average round trip time ' + avgTime + ' ms.'
	}

	/**
	 * @param args
	 */
	public static void main(def args){
		new OrgIterations().run()
		println 'run completed'
		return
	}
	
}
