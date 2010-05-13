/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.brms.repository.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.StatefulSession;
import org.drools.compiler.PackageBuilder;
import org.drools.event.DebugWorkingMemoryEventListener;
import org.drools.guvnor.client.modeldriven.brl.RuleModel;
import org.drools.guvnor.server.util.BRDRLPersistence;
import org.drools.guvnor.server.util.BRLPersistence;
import org.drools.guvnor.server.util.BRXMLPersistence;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.brms.repository.drools.util.DroolsUtil;

public class DroolsRuleTest 
{
    @Test
	public void testSerializeDeserializeDroolsPackage() throws Exception {
		RuleBase[] businessRules = new RuleBase[2];
		
		businessRules[0] = getRules1(); // Test dslr and dsl files
		businessRules[1] = getRules2(); // Test drl file
		//businessRules[2] = getRules3(); // Corrupt brl file
		
		for( int i=0; i<businessRules.length; i++ ) {
			StatefulSession workingMemory = businessRules[i].newStatefulSession();

			workingMemory.addEventListener( new DebugWorkingMemoryEventListener() );

			Email email = new Email( "tom@ubc.ca" );
	        Message message = new Message( false, "Invalid email" );

			//Let the rule engine know about the facts
			workingMemory.insert( email );
			workingMemory.insert( message );

			//Let the rule engine do its stuff!!
			workingMemory.fireAllRules();

			workingMemory.dispose();
		}
		Assert.assertTrue(true);
	}
	
	private static RuleBase getRules1() throws Exception {
		InputStream drl = DroolsRuleTest.class.getResourceAsStream( "/drools/drls/org/kuali/student/brms/repository/test/hello-email.dslr" );
		InputStream dsl = DroolsRuleTest.class.getResourceAsStream( "/drools/drls/org/kuali/student/brms/repository/test/hello-drools.dsl" );

		// read in the source
		Reader sourceDrl = new InputStreamReader(drl);
		Reader sourceDsl = new InputStreamReader(dsl);
		PackageBuilder builder = new PackageBuilder();

        // This will parse and compile
        builder.addPackageFromDrl( sourceDrl, sourceDsl );

        // Deserialize a Drools package
        byte[] bytes = DroolsUtil.deserialize( builder.getPackage() );
        // Serialize a Drools package
        org.drools.rule.Package pkg2 = (org.drools.rule.Package) DroolsUtil.serialize( bytes );
		assert( pkg2 != null );
		
		// deploy the rulebase
		RuleBase businessRules = RuleBaseFactory.newRuleBase();
		businessRules.addPackage(builder.getPackage());
		return businessRules;
	}

	private static RuleBase getRules2() throws Exception {
		InputStream drl = DroolsRuleTest.class.getResourceAsStream( "/drools/drls/org/kuali/student/brms/repository/test/test.drl" );

		// read in the source
		Reader sourceDrl = new InputStreamReader(drl);
		PackageBuilder builder = new PackageBuilder();

		// this will parse and compile
		builder.addPackageFromDrl( sourceDrl );

		// Deploy the rulebase
		RuleBase businessRules = RuleBaseFactory.newRuleBase();
		businessRules.addPackage(builder.getPackage());
		return businessRules;
	}

	private static RuleBase getRules3() throws Exception {
		String packageName = "package org.kuali.student.test\n";
		String brl = loadRule( "/drools/drls/org/kuali/student/brms/repository/test/hello-drools.brl" );
		String packageImports = 
			"import java.util.regex.Pattern;\n" + 
			"import org.kuali.student.brms.repository.test.Email;\n" +
			"import org.kuali.student.brms.repository.test.Message;\n";

		BRLPersistence read = BRXMLPersistence.getInstance();
		BRLPersistence write = BRDRLPersistence.getInstance();
		RuleModel ruleModel = read.unmarshal( brl );
		String sourceDrl = write.marshal( ruleModel );
		
		sourceDrl = packageName + "\n" + packageImports + "\n" + sourceDrl;
		
		// Read in the source
		PackageBuilder builder = new PackageBuilder();
		Reader drl = new StringReader( sourceDrl );

		// this will parse and compile
		builder.addPackageFromDrl( drl );

		// Deploy the rulebase
		RuleBase businessRules = RuleBaseFactory.newRuleBase();
		businessRules.addPackage(builder.getPackage());
		return businessRules;
	}

	private static String loadRule( String file ) throws Exception {
        String filename = DroolsRuleTest.class.getResource( file ).getFile();

        String str = "";
        StringBuilder drl = new StringBuilder();
        BufferedReader in = null;
		try {
			in = new BufferedReader( new FileReader( filename ) );
	        while ( ( str = in.readLine() ) != null ) {
	            drl.append(str);
	        }
		}
		finally {
	        if (in != null ) in.close();
		}
        return drl.toString();
	}

}
