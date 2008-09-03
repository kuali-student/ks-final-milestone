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

import javax.jcr.Credentials;
import javax.jcr.SimpleCredentials;

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.StatelessSession;

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
			"rule \"" + ruleName + "\"" +
			"     when" +
			"          now: Calendar()" +
			"          eval ( ( now.get(Calendar.MINUTE) % 2 == 0 ) )" +
			"     then" +
			"          System.out.println( \"Minute is even \" + now.get(Calendar.MINUTE) );" +
			"end";
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
            "rule \"" + ruleName + "\"" +
			"     when" +
			"          now: Calendar()" +
			"          eval ( ( now.get(Calendar.MINUTE) % 2 == 1 ) )" +
			"     then" +
			"          System.out.println( \"Minute is odd \" + now.get(Calendar.MINUTE) );" +
			"end";
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
            "rule \"" + ruleName + "\"" +
			"     when" +
			"          msg: Message()" +
			"          now: Calendar()" +
			"          eval ( ( now.get(Calendar.MINUTE) % 2 == 0 ) )" +
			"     then" +
			"          msg.setMessage( \"Minute is even \" + now.get(Calendar.MINUTE) ); " +
			"end";
	}
	
	/**
	 * Gets a simple rule. 
	 * rule determines whether the minutes of the hour is odd.
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
     * rule determines whether the minutes of the hour is odd.
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
        "rule \"" + ruleName + "\"" +
			"     when" +
			"          msg: Message()" +
			"          now: Calendar()" +
			"          eval ( ( now.get(Calendar.MINUTE) % 2 == 1 ) )" +
			"     then" +
			"          msg.setMessage( \"Minute is even \" + now.get(Calendar.MINUTE) ); " +
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
			"\n\n" +
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
        //System.out.println( "*****  filename = " +filename );

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
            //System.out.println( drl );
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
	public void executeRule( org.drools.rule.Package pkg, Object[] fact )
		throws Exception
	{
		RuleBase rb = RuleBaseFactory.newRuleBase();
		rb.addPackage( pkg );
        StatelessSession sess = rb.newStatelessSession();
        sess.execute( fact );
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
    
}
