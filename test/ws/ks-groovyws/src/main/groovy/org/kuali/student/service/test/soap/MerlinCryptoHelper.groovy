/**
 * 
 */
package org.kuali.student.service.test.soap
import org.apache.ws.security.components.crypto.Merlin



/**
 * @author randy
 *
 */
public class MerlinCryptoHelper{
	
	static Properties props
	static String keystorePassword = 'changeit'
	static String keystoreAlias = 'tomcat'
	static {
		def keyFile = 
	        '/Users/randy/Projects/workspace-service-test-3.5/ks-service-test-harness/src/main/resources/x509.jks'
//		TODO load from file
        props = new Properties();
       props.put("org.apache.ws.security.crypto.merlin.keystore.type", "jks");
       props.put("org.apache.ws.security.crypto.merlin.keystore.password", 'changeit')
       props.put("org.apache.ws.security.crypto.merlin.alias.password", 'changeit')
       props.put("org.apache.ws.security.crypto.merlin.keystore.alias", 'tomcat')
       props.put("org.apache.ws.security.crypto.merlin.file", keyFile)
	}
	
	private MerlinCryptoHelper() {
		
	}
	public static String getKeystorePassword() {
		return keystorePassword
	}
	public static String getKeystoreAlias() {
		return keystoreAlias
	}
	public static Merlin create() {
		return new Merlin(props)
	}
	
}
