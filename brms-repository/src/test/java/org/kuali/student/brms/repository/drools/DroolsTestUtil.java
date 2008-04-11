package org.kuali.student.brms.repository.drools;

import java.io.BufferedReader;
import java.io.FileReader;

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.StatelessSession;

public class DroolsTestUtil 
{
	/**
	 * Gets two simple rules. 
	 * First rule determines whether the minutes of the hour is odd, 
	 * the second determines whether the minutes are even.
	 * Each rule takes java.util.Calendar as a fact (package import).
	 *  
	 * @return Two Drools' rules
	 */
	public static String getSimpleRules() {
		return 
			"rule \"HelloDroolsEven\"" +
			"     when" +
			"          now: Calendar()" +
			"          eval ( ( now.get(Calendar.MINUTE) % 2 == 0 ) )" +
			"     then" +
			"          System.out.println( \"Minute is even \" + now.get(Calendar.MINUTE) );" +
			"end" +
			"\n" +
			"rule \"HelloDroolsOdd\"" +
			"     when" +
			"          now: Calendar()" +
			"          eval ( ( now.get(Calendar.MINUTE) % 2 == 1 ) )" +
			"     then" +
			"          System.out.println( \"Minute is odd \" + now.get(Calendar.MINUTE) );" +
			"end";
	}
	
	/**
	 * Gets two simple rules. 
	 * First rule determines whether the minutes of the hour is odd, 
	 * the second determines whether the minutes are even.
	 * Each rule takes <code>java.util.Calendar</code> and 
	 * <code>org.kuali.student.brms.repository.test.Message</code> 
	 * as facts (package import).
	 * 
	 * @return Two Drools' rules
	 */
	public static String getSimpleRules2() {
		return 
			"rule \"HelloDroolsEven\"" +
			"     when" +
			"          msg: Message()" +
			"          now: Calendar()" +
			"          eval ( ( now.get(Calendar.MINUTE) % 2 == 0 ) )" +
			"     then" +
			"          msg.setMessage( \"Minute is even \" + now.get(Calendar.MINUTE) ); " +
			"end" +
			"\n" +
			"rule \"HelloDroolsOdd\"" +
			"     when" +
			"          msg: Message()" +
			"          now: Calendar()" +
			"          eval ( ( now.get(Calendar.MINUTE) % 2 == 1 ) )" +
			"     then" +
			"          msg.setMessage( \"Minute is even \" + now.get(Calendar.MINUTE) ); " +
			"end";
	}
	
	/**
	 * Return a Drools DRL file which includes 2 simple rules.
	 * 
	 * @param packageName Drools package name (ruleset)
	 * @return Drools DRL
	 */
	public static String getSimpleDRL( String packageName ) {
		return 
			"package " + packageName +
			"\n" +
			"import java.util.Calendar" +
			"\n\n" +
			getSimpleRules();
	}

	/**
	 * Returns a Drools validation rule.
	 * Rule takes <code>org.kuali.student.brms.repository.test.Email</code> and 
	 * <code>org.kuali.student.brms.repository.test.Message</code> 
	 * as facts (package import).
	 * 
	 * @return Drools validation rule
	 * @throws Exception
	 */
	public static String getValidationRule1() throws Exception {
		return loadFile( "/test-rule-1.txt" );
	}

	/**
	 * Returns a Drools validation rule.
	 * Rule takes <code>org.kuali.student.brms.repository.test.Email</code> and 
	 * <code>org.kuali.student.brms.repository.test.Message</code> 
	 * as facts (package import).
	 * 
	 * @return Drools validation rule
	 * @throws Exception
	 */
	public static String getValidationRule2() throws Exception {
		return loadFile( "/test-rule-2.txt" );
	}

	/**
	 * Loads a text file.
	 * 
	 * @param file File name and path
	 * @return A text file
	 * @throws Exception
	 */
	public static String loadFile( String file ) throws Exception
	{
        String filename = DroolsTestUtil.class.getResource( file ).getFile();
        System.out.println( "*****  filename = " +filename );

        String str = "";
        String drl = "";
        BufferedReader in = null;
		try
		{
			in = new BufferedReader( new FileReader( filename ) );
	        while ( ( str = in.readLine() ) != null ) 
	        {
	        	drl += str;
	        }
            System.out.println( drl );
		}
		finally
		{
	        if (in != null ) in.close();
		}
        return drl;
	}

	/**
	 * Executes a Drools package (rule set).
	 * 
	 * @param pkg Drools package (rule set)
	 * @param fact Facts to assert
	 * @throws Exception
	 */
	public static void executeRule( org.drools.rule.Package pkg, Object[] fact )
		throws Exception
	{
		RuleBase rb = RuleBaseFactory.newRuleBase();
		rb.addPackage( pkg );
        StatelessSession sess = rb.newStatelessSession();
        sess.execute( fact );
	}
	
}
