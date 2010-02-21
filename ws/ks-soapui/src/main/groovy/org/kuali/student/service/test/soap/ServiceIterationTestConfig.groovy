/**
 * 
 */
package org.kuali.student.service.test.soap



/**
 *  Create a script that runs a set of ServiceIterationsTest instances
 * from xml configuration file
 * @author Kuali Student Deployment Lab
 * @author randy
 *
 */
public class ServiceIterationTestConfig{
	
	PrintWriter outWriter
	int testCount = 0
	public ServiceIterationTestConfig( String xmlResource){
		
		createTests(xmlResource)
		
	}
	void   createTests(xmlResource){
		println 'creating ' + xmlResource 
		def stream = this.class.getResourceAsStream(xmlResource)
		assert stream != null, "failed to getResourceAsStream: $xmlResource"
		def config = new XmlParser().parse(stream)
		assert config != null, "failed to parse resource: $xmlResource"
		println config.dump()
		outWriter = createScriptFile(config)
		
		outWriter.println 'import org.kuali.student.service.test.soap.*  '
		
		doCreate(config)
		//outWriter.flush()
		outWriter.close()
	}
	void doCreate (def config)  {
		
		config.'service-node'.each {  node ->
			def nodeInfo = node.'@hostname' + ':' + node.'@port' + '/'
			println ' service-node: ' + nodeInfo
			createDeployment(node, nodeInfo)
		}
	}
	void createDeployment(def node, def nodeInfo ) {
		node.'service-wsdl-uri'.each {  deployment ->
			def wsdlBase = 'http://' + nodeInfo + deployment.'@value'
			println 'wsdlBase: ' + wsdlBase
			
			deployment.'service'.each { svc ->
				createServiceTests(svc, wsdlBase)
			}
			
		}
		
	}
	void createServiceTests(def svc, def wsdlBase) {
		// println 'create service: ' + wsdlBase +  svc.dump()
		def clientUri = wsdlBase + '/' + svc.'@name' + '?wsdl'
		println 'clientUri:  ' + clientUri

        def testName = 't' + testCount++
        outWriter.println  '\tServiceIterationsTest ' + testName + ' = '
        outWriter.println "\t\tnew ServiceIterationsTest('"+ clientUri + "')"
		svc.'operation'.each { op ->
			def opName = op.'@name'
			
            
			def iterations = op.'@iterations'
			println 'create ' +iterations + ' iterations for ' + opName
			outWriter.printf("\t%s.execute( %s, '%s' ", testName, iterations, 
					opName)
			
			op.'parameter'.each { parm -> 
				def val = parm.'@value'
				println 'parm: ' + val
				outWriter.print( ',' + val)
				
			}
			outWriter.println(')')
		}
		
	}
	PrintWriter createScriptFile( def config) {
		String scriptFileName =  config.'@name' + '.groovy'
		
		def threadCount = config.'@thread-count'
		
		println 'scriptFile: ' + scriptFileName + ' threadCount: ' + threadCount
		File scriptFile = new File('/var/tmp/' + scriptFileName)
		assert scriptFile != null,  "failed to open: $scriptFile"
		PrintWriter rval = new PrintWriter(scriptFile)
		return rval
	}
	/**
	 * @param args
	 */
	public static void main(def args){
		
		println 'running parser'
		ServiceIterationTestConfig conf = 
				new ServiceIterationTestConfig('/test-harness-proto.xml')
	}
	
}
