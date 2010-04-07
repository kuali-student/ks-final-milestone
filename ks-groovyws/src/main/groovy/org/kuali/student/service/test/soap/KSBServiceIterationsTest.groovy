/**
 * 
 */
package org.kuali.student.service.test.soap



/**
 * @author randy
 *
 */
public class KSBServiceIterationsTest extends ServiceIterationsTest{

	
	/* (non-Javadoc)
	 * @see org.kuali.student.service.test.soap.ServiceIterationsTest#ServiceIterationsTest(java.lang.String)
	 */
	public KSBServiceIterationsTest(String url){
		super(url)
		WSSecurityInterceptorHelper.addInterceptors(this.proxy)
	}
	


	/**
	 * @param args
	 */
	public static void main(def args){
	   
	 //   KSBServiceIterationsTest t1 = new KSBServiceIterationsTest('http://localhost:8080/ks-lum-web/services/TranslationService?wsdl')
     //   t1.execute(2, "getNaturalLanguageForReqComponent", 'REQCOMP-NL-1', 'KUALI.CATALOG', 'en')
        KSBServiceIterationsTest t2 = new KSBServiceIterationsTest('http://kuali.berkeley.edu:8080/ks-lum-web/services/TranslationService?wsdl')
		 t2.execute(2, "getNaturalLanguageForReqComponent", 'REQCOMP-NL-1', 'KUALI.CATALOG', 'de')
	}
	
}
