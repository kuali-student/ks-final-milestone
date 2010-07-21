/**
 * 
 */
package org.kuali.student.service.test.soap
import groovyx.net.ws.WSClient
import org.codehaus.groovy.runtime.InvokerHelper


/**
 * @author randy
 *
 */
public class ProtoTypeComplexParam{
	WSClient proxy
    Closure invoker

	/**
	 * 
	 */
	public ProtoTypeComplexParam(String url){
		init(url)
		def complexParam = createComplexParam()
		println 'created complexParm: ' + complexParam.dump()
		def result = proxy.invokeMethod('getNaturalLanguageForReqComponentInfo', 
				complexParam, 'KUALI.CATALOG', 'en' )
		println 'invokedMethod: ' + result
		def res2 = proxy.getNaturalLanguageForReqComponentInfo(
				complexParam, 'KUALI.CATALOG', 'de')
		 println 'direct call: ' + res2
//		 ServiceIterationsTest st = new ServiceIterationsTest(url)
//	 def res3 = st.execute(2, 'getNaturalLanguageForReqComponentInfo', 
//	                complexParam, 'KUALI.CATALOG', 'en')
//	     println 'result from ServiceIterationsTest.execute: ' + res3
		
	}
	void init(url) {
        proxy = new WSClient(url, this.class.classLoader)
        proxy.
        println 'created proxy: ' + proxy + ' for ' + url
        proxy.initialize()
        invoker = proxy.&invokeMethod
        println 'proxy dump: ' + proxy.dump()
    }
	Object createComplexParam() {
		def rval = proxy.create('org.kuali.student.wsdl.nlt.ReqComponentInfo')
		rval.id = 'REQCOMP-NL-1'
		rval.type = 'kuali.reqCompType.courseList.nof'
		List fieldInfo = []
		fieldInfo.add(createReqCompFieldInfo('reqCompFieldType.requiredCount', '1'))
		fieldInfo.add(createReqCompFieldInfo('reqCompFieldType.operator',
				'greater_than_or_equal_to'))
		fieldInfo.add(createReqCompFieldInfo('reqCompFieldType.cluSet', 
				'CLUSET-NL-1'))
		rval.reqCompFields = fieldInfo
		return rval
	}
	Object createReqCompFieldInfo(def id, def value) {
		def rval = proxy.create('org.kuali.student.wsdl.nlt.ReqCompFieldInfo')
		println 'created bean: ' + rval.dump()

		def metaClass = InvokerHelper.getMetaClass(rval)
		println 'bean metaclass is: '  + metaClass.dump()
		rval.key = id
		rval.value = value
//      	metaClass.invokeMethod('setKey', id)
//      	metaClass.invokeMethod('setValue', value)
		println 'created bean: ' + rval.dump()
		
		return rval
	}
	Object createBean(String name, Map props) {
		
	}

	/**
	 * @param args
	 */
	public static void main(def args){
//		 ks milestone 2 
        def urlBase = 'http://kuali.berkeley.edu:8080'
        def servicePath = '/ks-embedded-dev/services'
        def url = urlBase + servicePath +  '/TranslationService?wsdl'
      
		new ProtoTypeComplexParam(url)
	}
	
}
