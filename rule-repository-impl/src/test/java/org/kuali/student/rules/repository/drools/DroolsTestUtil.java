/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.rules.repository.drools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.jcr.Credentials;
import javax.jcr.SimpleCredentials;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.command.Command;
import org.drools.command.CommandFactory;
import org.drools.definition.KnowledgePackage;
import org.drools.runtime.StatelessKnowledgeSession;

public class DroolsTestUtil 
{
    /** A simple validation rule */
    private static String validationRule1;
    /** A simple validation rule */
    private static String validationRule2;
    
    /**
     * Returns a new instance of <code>DroolsTestUtil</code>
     * 
     * @return A new instance of <code>DroolsTestUtil</code>
     */
    public static DroolsTestUtil getInstance() {
        return new DroolsTestUtil();
    }
    
	/**
	 * Gets a simple rule. 
	 * First rule determines whether the minutes of the hour is even.
	 * Rule takes <code>java.util.Calendar</code> as fact (package import).
	 *  
	 * @return a Drools rule
	 */
    public String getSimpleRule1() {
        return getSimpleRule1(null);
    }

    /**
     * Gets a simple rule. 
     * First rule determines whether the minutes of the hour is even.
     * Rule takes <code>java.util.Calendar</code> as fact (package import).
     * 
     * @param ruleName Rule name
     * @return a Drools rule
     */
    public String getSimpleRule1(String ruleName) {
        ruleName = (ruleName == null ? "HelloDroolsEven" : ruleName);
        return 
			"rule \"" + ruleName + "\"\n" +
			"     when\n" +
			"          now: Calendar()\n" +
			"          eval ( ( now.get(Calendar.MINUTE) % 2 == 0 ) )\n" +
			"     then\n" +
			"          System.out.println( \"Minute is even \" + now.get(Calendar.MINUTE) );\n" +
			"end\n";
	}
	
	/**
	 * Gets a simple rule. 
	 * Rule determines whether the minutes of the hour is odd.
	 * Rule takes <code>java.util.Calendar</code> as fact (package import).
	 *  
	 * @return a Drools' rule
	 */
    public String getSimpleRule2() {
        return getSimpleRule2(null);
    }

	public String getSimpleRule2(String ruleName) {
        ruleName = (ruleName == null ? "HelloDroolsOdd" : ruleName);
		return 
            "rule \"" + ruleName + "\"\n" +
			"     when\n" +
			"          now: Calendar()\n" +
			"          eval ( ( now.get(Calendar.MINUTE) % 2 == 1 ) )\n" +
			"     then\n" +
			"          System.out.println( \"Minute is odd \" + now.get(Calendar.MINUTE) );\n" +
			"end\n";
	}
	
	/**
	 * Gets a simple rule. 
	 * Rule determines whether the minutes of the hour is even.
	 * Rule takes <code>java.util.Calendar</code> and 
	 * <code>org.kuali.student.rules.repository.test.Message</code> 
	 * as fact (package import).
	 * 
	 * @return a Drools rule
	 */
    public String getSimpleRule3() {
        return getSimpleRule3(null);
    }

    /**
     * Gets a simple rule. 
     * Rule determines whether the minutes of the hour is even.
     * Rule takes <code>java.util.Calendar</code> and 
     * <code>org.kuali.student.rules.repository.test.Message</code> 
     * as fact (package import).
     * 
     * @param ruleName Rule name
     * @return a Drools rule
     */
	public String getSimpleRule3(String ruleName) {
        ruleName = (ruleName == null ? "HelloDroolsEven" : ruleName);
		return 
            "rule \"" + ruleName + "\"\n" +
			"     when\n" +
			"          msg: Message()\n" +
			"          now: Calendar()\n" +
			"          eval ( ( now.get(Calendar.MINUTE) % 2 == 0 ) )\n" +
			"     then\n" +
			"          msg.setMessage( \"Minute is even \" + now.get(Calendar.MINUTE) );\n" +
			"end\n";
	}
	
	/**
	 * Gets a simple rule. 
	 * Rule determines whether the minutes of the hour is odd.
	 * Rule takes <code>java.util.Calendar</code> and 
	 * <code>org.kuali.student.rules.repository.test.Message</code> 
	 * as fact (package import).
	 * 
	 * @return a Drools rule
	 */
    public String getSimpleRule4() {
        return getSimpleRule4(null);
    }

    /**
     * Gets a simple rule. 
     * Rule determines whether the minutes of the hour is odd.
     * Rule takes <code>java.util.Calendar</code> and 
     * <code>org.kuali.student.rules.repository.test.Message</code> 
     * as fact (package import).
     * 
     * @param ruleName Rule name
     * @return a Drools rule
     */
	public String getSimpleRule4(String ruleName) {
        ruleName = (ruleName == null ? "HelloDroolsOdd" : ruleName);
		return 
        "rule \"" + ruleName + "\"\n" +
			"     when\n" +
			"          msg: Message()\n" +
			"          now: Calendar()\n" +
			"          eval ( ( now.get(Calendar.MINUTE) % 2 == 1 ) )\n" +
			"     then\n" +
			"          msg.setMessage( \"Minute is even \" + now.get(Calendar.MINUTE) );\n" +
			"end";
	}
	
	/**
	 * Return a Drools DRL file which includes 
	 * <code>getSimpleRule1</code> and <code>getSimpleRule2</code>.
	 * 
	 * @param packageName Drools package name (ruleset)
	 * @return Drools DRL
	 */
	public String getSimpleDRL( String packageName ) {
		return 
			"package " + packageName +
			"\n" +
			"import java.util.Calendar; " +
			//"\n\n" +
			"\n" +
			getSimpleRule1() +
			"\n" +
			getSimpleRule2() +
            "\n";
	}

	/**
	 * Returns a Drools validation rule.
	 * Rule takes <code>org.kuali.student.rules.repository.test.Email</code> and 
	 * <code>org.kuali.student.rules.repository.test.Message</code> 
	 * as facts (package import).
	 * 
	 * @return Drools validation rule
	 * @throws Exception
	 */
	public String getValidationRule1() throws Exception {
		if ( validationRule1 == null || validationRule1.trim().isEmpty() ) {
		    validationRule1 = loadFile( "/drools/drls/org/kuali/student/rules/brms/repository/drools/test-rule-1.txt" );
		}
	    return validationRule1;
	}

	/**
	 * Returns a Drools validation rule.
	 * Rule takes <code>org.kuali.student.rules.repository.test.Email</code> and 
	 * <code>org.kuali.student.rules.repository.test.Message</code> 
	 * as facts (package import).
	 * 
	 * @return Drools validation rule
	 * @throws Exception
	 */
	public String getValidationRule2() throws Exception {
        if ( validationRule2 == null || validationRule2.trim().isEmpty() ) {
            validationRule2 = loadFile( "/drools/drls/org/kuali/student/rules/brms/repository/drools/test-rule-2.txt" );
        }
        return validationRule2;
	}

	/**
	 * Loads a text file.
	 * 
	 * @param file File name and path
	 * @return A text file
	 * @throws Exception
	 */
	public String loadFile( String file ) throws Exception
	{
        String filename = DroolsTestUtil.class.getResource( file ).getFile();

        String str = "";
        StringBuilder drl = new StringBuilder();
        BufferedReader in = null;
		try
		{
			in = new BufferedReader( new FileReader( filename ) );
	        while ( ( str = in.readLine() ) != null ) 
	        {
                drl.append(str);
	        }
		}
		finally
		{
	        if (in != null ) in.close();
		}
        return drl.toString();
	}

	/**
	 * Executes a Drools package (rule set).
	 * 
	 * @param pkg Drools package (rule set)
	 * @param fact Facts to assert
	 * @throws Exception
	 */
	public void executeRule(KnowledgePackage pkg, Object[] facts)
		throws Exception
	{
		KnowledgeBase rb = KnowledgeBaseFactory.newKnowledgeBase();
    	Collection<KnowledgePackage> pkgs = new ArrayList<KnowledgePackage>();
		pkgs.add(pkg);
    	rb.addKnowledgePackages(pkgs);
        StatelessKnowledgeSession session = rb.newStatelessKnowledgeSession();

        List<Command<?>> commands = new ArrayList<Command<?>>();
        for(Object fact : facts) {
            commands.add(CommandFactory.newInsertObject(fact));
        }
        Command<?> cmd = CommandFactory.newBatchExecution(commands);
        session.execute(cmd);
	}
	
    /**
     * Gets default superuser credentials.
     * 
     * @return Superuser credentials
     */
	public Credentials getSuperUserCredentials() {
        String id = "superuser";
        char[] password = "superuser".toCharArray();
        return new SimpleCredentials(id, password);
    }
	
	/**
	 * Determines whether a class implements a specific interface.
	 * 
	 * @param clazz Class to test
	 * @param intrface Interface
	 * @return True if class implements interface; otherwise false
	 */
	public boolean containsInterface(Class<?> clazz, Class<?> intrface) {
		for(Class<?> c : clazz.getInterfaces()) {
			if(c.getName().equals(intrface.getName())) {
				return true;
			}
		}
		return false;
	}
    
}
