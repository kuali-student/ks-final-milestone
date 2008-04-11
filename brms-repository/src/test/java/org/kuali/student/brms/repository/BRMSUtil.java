package org.kuali.student.brms.repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.kuali.student.brms.repository.drools.DroolsTestUtil;

public class BRMSUtil 
{
	/**
	 * Creates a rule set (Drools package) in a category and returns the
	 * rule set UUID.
	 * 
	 * @param brmsRepository BRMS repository
	 * @param categoryName Category for the rules set
	 * @param facts Rule facts
	 * @param checkin Checkin rule set if true
	 * @return Rule set UUID
	 * @throws BRMSRepositoryException
	 */
	public static String createRuleSet( BRMSRepository brmsRepository, 
										String categoryName, 
										String facts, 
										boolean checkin ) throws BRMSRepositoryException
	{
		// Create category
		boolean b = brmsRepository.createCategory( "/", categoryName, "A test category description" );
		assertTrue( b );
		// Create rule set
        String ruleSetUUID = brmsRepository.createRuleSet( categoryName, "Rule set description" );
        if ( facts != null )
        {
        	brmsRepository.setFactsToRuleSet( ruleSetUUID, facts );
        }
        if ( checkin )
        {
        	brmsRepository.checkinRuleSet( ruleSetUUID, "Checkin ruleset comments" );
        }
        assertNotNull( ruleSetUUID );
        
        return ruleSetUUID;
	}

	/**
	 * Creates a rule in a rule set and returns the rule UUID.
	 * 
	 * @param brmsRepository BRMS Repository
	 * @param ruleSetUUID Rule set UUID the rule belongs to
	 * @param categoryName Category the rule set belongs to
	 * @param checkin Checkin rule if true
	 * @return Rule UUID
	 * @throws BRMSRepositoryException
	 */
	public static String createRule( BRMSRepository brmsRepository, 
									 String ruleSetUUID, 
									 String categoryName, 
									 boolean checkin ) throws BRMSRepositoryException
	{
		// Create rule
        String ruleSource = DroolsTestUtil.getSimpleRules();
        String ruleUUID = brmsRepository.createRule( 
        		ruleSetUUID, "rule_1", "Rule set description", ruleSource, 
        		categoryName );
        if ( checkin )
        {
        	brmsRepository.checkinRule( ruleUUID, "Checkin rule comments" );
        }
        assertNotNull( ruleUUID );
        
        return ruleUUID;
	}
	
	/**
	 * Return a messages of the <code>result</code> as a string.
	 * 
	 * @param result Builder result list
	 * @return Errors messages
	 */
	public static String getErrorMessage( BuilderResultList result )
	{
		return ( result == null ? "Null Error Message" : result.toString() );
	}

}
