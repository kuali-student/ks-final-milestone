package org.kuali.student.brms.repository;

import java.util.List;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.StatelessSession;
import org.drools.jsr94.rules.RuleServiceProviderImpl;
import org.drools.repository.RulesRepository;

import javax.rules.RuleRuntime;
import javax.rules.RuleServiceProvider;
import javax.rules.RuleServiceProviderManager;
import javax.rules.StatelessRuleSession;
import javax.rules.admin.LocalRuleExecutionSetProvider;
import javax.rules.admin.RuleAdministrator;
import javax.rules.admin.RuleExecutionSet;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.brms.repository.BRMSRepository;
import org.kuali.student.brms.repository.BRMSRepositoryException;
import org.kuali.student.brms.repository.Rule;
import org.kuali.student.brms.repository.RuleSet;
import org.kuali.student.brms.repository.drools.BRMSRepositoryDroolsImpl;
import org.kuali.student.brms.repository.test.Email;
import org.kuali.student.brms.repository.test.Message;

import static org.junit.Assert.*;

public class BRMSRepositoryTest extends DroolsJackRabbitRepository
{
	private static BRMSRepository brmsRepository;

    /*public static void main(String[] args) throws Exception
    {
		RulesRepository repo = createRepository();   
		
		PackageItem item = repo.createPackage( "testPackage", "A test" );
		item.archiveItem( true );
		item.checkin( "Archive test" );
		
		System.out.println( "Archived=" + repo.loadPackageByUUID( item.getUUID() ).isArchived());

		String sql = "SELECT " 
			+ PackageItem.TITLE_PROPERTY_NAME + ", " 
			+ PackageItem.DESCRIPTION_PROPERTY_NAME + ", " 
			+ PackageItem.CONTENT_PROPERTY_ARCHIVE_FLAG; 
		sql += " FROM " + PackageItem.RULE_PACKAGE_TYPE_NAME;
		sql += " WHERE ";
		sql += " jcr:path LIKE '/" 
			+ RulesRepository.RULES_REPOSITORY_NAME + "/" 
			+ RulesRepository.RULE_PACKAGE_AREA + "/%'";
		//sql += " AND " + PackageItem.CONTENT_PROPERTY_ARCHIVE_FLAG + " = 'true'";
		
		Query q = repo.getSession().getWorkspace().getQueryManager().createQuery( sql, Query.SQL );
		
		QueryResult res = q.execute();
		
		Iterator<PackageItem> it = new PackageIterator( repo, res.getNodes() );
		
		while( it.hasNext() )
		{
			PackageItem pi = it.next();
			System.out.println( "*****  item=" +pi.getUUID() + ", name=" +pi.getName() + ", isArchived=" + pi.isArchived() );
		}
    }*/
    
	private String getSimpleRule()
	{
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
	
	private String getSimpleRule2()
	{
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
	
	private String getSimpleDRL( String packageName )
	{
		return 
			"package " + packageName +
			"\n" +
			"import java.util.Calendar" +
			"\n\n" +
			getSimpleRule();
	}

	private String getValidationRule1() throws Exception
	{
		return loadRule( "/test-rule-1.txt" );
	}

	private String getValidationRule2() throws Exception
	{
		return loadRule( "/test-rule-2.txt" );
	}

	private static String loadRule( String file ) throws Exception
	{
        String filename = BRMSRepositoryTest.class.getResource( file ).getFile();
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

	private String createRuleSet( String categoryName ) throws BRMSRepositoryException
	{
		return createRuleSet( categoryName, null, true );
	}

	private String createRuleSet( String categoryName, String facts, boolean checkin ) throws BRMSRepositoryException
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

	private String createRule( String ruleSetUUID, String categoryName ) throws BRMSRepositoryException
	{
		return createRule( ruleSetUUID, categoryName, true );
	}
	
	private String createRule( String ruleSetUUID, String categoryName, boolean checkin ) throws BRMSRepositoryException
	{
		// Create rule
        String ruleSource = getSimpleRule();
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
	
	private void executeRule( org.drools.rule.Package pkg, Object[] fact )
		throws Exception
	{
		RuleBase rb = RuleBaseFactory.newRuleBase();
		rb.addPackage( pkg );
        StatelessSession sess = rb.newStatelessSession();
        sess.execute( fact );
	}
	
	private String getErrorMessage( BuilderResultList result )
	{
		return ( result == null ? "Null Error Message" : result.toString() );
	}

	@BeforeClass
	public static void setUpOnce() throws Exception 
	{
		setupRepository();
	}
	
	@AfterClass
	public static void tearDownOnce() throws Exception 
	{
		shutdownRepository();
	}

	@Before
	public void setUp() throws Exception 
	{
		login();
		clearRepository();
		brmsRepository = new BRMSRepositoryDroolsImpl( getRepository() );
	}

	@After
	public void tearDown() throws Exception 
	{
		logout();
	}

	@Test
	public void testCreateAndLoadCategories() throws Exception
	{
		boolean b = brmsRepository.createCategory( "/", "EnrollmentRules", "A test category 1.0 description" );
		assertTrue( b );
		b = brmsRepository.createCategory( "/EnrollmentRules", "Math", "A Math category description" );
		assertTrue( b );
		b = brmsRepository.createCategory( "/EnrollmentRules", "English", "An English category description" );
		assertTrue( b );
		b = brmsRepository.createCategory( "/EnrollmentRules/Math", "PreReq", "A PreReq category description" );
		assertTrue( b );
		b = brmsRepository.createCategory( "/EnrollmentRules/Math", "CoReq", "A CoReq category description" );
		assertTrue( b );
		
		List<String> category = brmsRepository.loadChildCategories( "/" );
		//assertTrue( category.length == 1 );
		assertEquals( "EnrollmentRules", category.get( 0 ) );

		category = brmsRepository.loadChildCategories( "/EnrollmentRules" );
		assertTrue( category.size() == 2 );
		assertEquals( "Math", category.get( 0 ) );
		assertEquals( "English", category.get( 1 ) );

		category = brmsRepository.loadChildCategories( "/EnrollmentRules/Math" );
		assertTrue( category.size() == 2 );
		assertEquals( "PreReq", category.get( 0 ) );
		assertEquals( "CoReq", category.get( 1 ) );
	}

	@Test
	public void testRemoveCategory() throws Exception
	{
		boolean b = brmsRepository.createCategory( "/", "testRemoveCategory", "A test category description" );
		assertTrue( b );
		brmsRepository.removeCategory( "testRemoveCategory" );
		List<String> list = brmsRepository.loadChildCategories( "/" );
		assertFalse( list.contains( "testRemoveCategory" ) );
	}

	@Test
	public void testLoadCompiledRuleSetSnapshotAndExecuteSnapshot() throws Exception 
    {
        String rulesetUuid = brmsRepository.createRuleSet( 
        		"testBinaryPackageCompile", "" );
        String ruleUuid = brmsRepository.createRule( 
        		rulesetUuid, "rule_1", "", getSimpleRule2(), null );
        brmsRepository.setFactsToRuleSet( rulesetUuid, 
        		"import java.util.Calendar " +
    			"import org.kuali.student.brms.repository.test.Message" );
        brmsRepository.checkinRule( ruleUuid, null );

        BuilderResultList results = brmsRepository.compileRuleSet( rulesetUuid );

        // No errors
		assertNull( getErrorMessage( results ), results );

		org.drools.rule.Package binPkg = 
			(org.drools.rule.Package) brmsRepository.loadCompiledRuleSet( rulesetUuid );
        
        assertNotNull( binPkg );

        assertNotNull( binPkg );
        assertTrue( binPkg.isValid() );

        executeRule( binPkg, new Object[] { Calendar.getInstance() } );

        brmsRepository.createRuleSetSnapshot( "testBinaryPackageCompile",
                                    "SNAP1",
                                    false,
                                    "A snapshot" );

        binPkg = (org.drools.rule.Package)brmsRepository.loadCompiledRuleSetSnapshot( 
        		"testBinaryPackageCompile", "SNAP1" );

        assertNotNull( binPkg );
        assertTrue( binPkg.isValid() );

        Message message = new Message();
        Calendar calendar = Calendar.getInstance();
        executeRule( binPkg, new Object[] { message, calendar } );
		assertTrue( message.getMessage().startsWith( "Minute is " ) );
    }

	@Test
    public void testLoadCompiledRuleSetAndExecute() throws Exception 
    {
    	// Create category
		boolean b = brmsRepository.createCategory( "/", "testLoadCompiledRuleSetAndExecute", "A test category 1.0 description" );
		assertTrue( b );
		
        String rulesetUuid = brmsRepository.createRuleSet( 
        		"testBinaryPackageCompile2", "Email Validation Rule Package" );
        brmsRepository.setFactsToRuleSet( rulesetUuid, 
        		"import java.util.regex.Pattern " +
        		"import org.kuali.student.brms.repository.test.Email " +
        		"import org.kuali.student.brms.repository.test.Message" );
        String ruleUuid1 = brmsRepository.createRule( 
        		rulesetUuid, "rule_1", "Email Initialization Rule", 
        		getValidationRule1(), "testLoadCompiledRuleSetAndExecute" );
        brmsRepository.checkinRule( ruleUuid1, null );
        String ruleUuid2 = brmsRepository.createRule( 
        		rulesetUuid, "rule_2", "Email Validation Rule", 
        		getValidationRule2(), "testLoadCompiledRuleSetAndExecute" );
        brmsRepository.checkinRule( ruleUuid2, null );
		
        BuilderResultList results = brmsRepository.compileRuleSet( rulesetUuid );
		
		// No errors
		assertNull( getErrorMessage( results ), results );

		org.drools.rule.Package binPkg = 
			(org.drools.rule.Package) brmsRepository.loadCompiledRuleSet( rulesetUuid );

        assertNotNull( binPkg );
        assertTrue( binPkg.isValid() );

        Email email = new Email( "len.carlsen@ubc.ca" );
        Message message = new Message();
        executeRule( binPkg, new Object[] { email, message } );
		assertEquals( "Valid Email Address: len.carlsen@ubc.ca", message.getMessage() );
    }
	
	@Test
	public void testJSR94() throws Exception
	{
    	//************************************************************************************
    	//* Load binary rule set (org.drools.rule.Package) from a repository (e.g. database) *
    	//************************************************************************************

    	// Create category
		boolean b = brmsRepository.createCategory( "/", "testJSR94", "A test category 1.0 description" );
		assertTrue( b );
		
        String rulesetUuid = brmsRepository.createRuleSet( 
        		"MyPackage", "My package description" );
        String ruleUuid1 = brmsRepository.createRule( 
        		rulesetUuid, "rule_1", "", getSimpleRule(), "testJSR94" );
        brmsRepository.checkinRule( ruleUuid1, null );
        brmsRepository.setFactsToRuleSet( rulesetUuid, "import java.util.Calendar" );
        
        BuilderResultList results = brmsRepository.compileRuleSet( rulesetUuid );

        // No errors
		assertNull( getErrorMessage( results ), results );
        
		org.drools.rule.Package binPkg = 
			(org.drools.rule.Package) brmsRepository.loadCompiledRuleSet( rulesetUuid );

        assertNotNull( binPkg );
	
    	//******************************************************
    	//* JSR 94 API for registering and executing rule sets *
    	//******************************************************

		String RULE_SERVICE_PROVIDER = "http://drools.org/";
		String RULE_URI = "MyPackage";
		
		RuleServiceProviderManager.registerRuleServiceProvider(
                RULE_SERVICE_PROVIDER, RuleServiceProviderImpl.class );

        RuleServiceProvider ruleServiceProvider = RuleServiceProviderManager.getRuleServiceProvider(RULE_SERVICE_PROVIDER);

        RuleAdministrator ruleAdministrator = ruleServiceProvider.getRuleAdministrator( );

        LocalRuleExecutionSetProvider ruleSetProvider =
                ruleAdministrator.getLocalRuleExecutionSetProvider(null);

        RuleExecutionSet ruleExecutionSet = 
        	ruleSetProvider.createRuleExecutionSet( binPkg, null);

        ruleAdministrator.registerRuleExecutionSet( RULE_URI, ruleExecutionSet, null);

        RuleRuntime ruleRuntime = ruleServiceProvider.getRuleRuntime();

        StatelessRuleSession statelessRuleSession = 
        	(StatelessRuleSession) ruleRuntime.createRuleSession(
        			RULE_URI, null, RuleRuntime.STATELESS_SESSION_TYPE );
        
        Calendar now = Calendar.getInstance();
        List<Calendar> facts = new ArrayList<Calendar>();
        facts.add( now );
        statelessRuleSession.executeRules( facts );
	}
    
	@Test
	public void testCreateRuleSet() throws Exception
	{
        String rulesetUuid = brmsRepository.createRuleSet( "testCreateRuleSet", "Rule set description" );
        assertNotNull( rulesetUuid );
        
        RuleSet ruleSet = brmsRepository.loadRuleSet( rulesetUuid );
		assertEquals( ruleSet.getUUID(), rulesetUuid );
        
        try 
        {
			brmsRepository.loadRuleSet( "XYZ" );
		} 
        catch ( BRMSRepositoryException e ) 
        {
			assertTrue( true );
		}
	}
	
	@Test
	public void testCreateNewRule() throws Exception
	{
		String ruleSetUUID = createRuleSet( "testCreateNewRule" );
		String ruleUUID = createRule( ruleSetUUID, "testCreateNewRule" );
        
        RuleSet ruleSet = brmsRepository.loadRuleSet( ruleSetUUID );
        List<Rule> list = ruleSet.getRules();
        assertNotNull( list );
        assertTrue( list.size() > 0 );

        Rule rule = brmsRepository.loadRule( ruleUUID );
        assertEquals( rule.getUUID(), ruleUUID );
        assertEquals( rule.getContent(), getSimpleRule() );
        assertEquals( rule.getName(), "rule_1" );
        assertEquals( rule.getDescription(), "Rule set description" );
        assertEquals( rule.getCheckinComment(), "Checkin rule comments" );
	}
	
	@Test
	public void testRenameRuleSet() throws Exception
	{
        String rulesetUuid = brmsRepository.createRuleSet( "testRenameRuleSet", "Rule set description" );
        assertNotNull( rulesetUuid );

		String newUUID = brmsRepository.renameRuleSet( rulesetUuid, "testRenameRuleSet_NEW_NAME" );
		RuleSet ruleSet = brmsRepository.loadRuleSet( newUUID );
		assertEquals( ruleSet.getUUID(), newUUID );
		assertEquals( ruleSet.getName(), "testRenameRuleSet_NEW_NAME" );
	}

	@Test
	public void testRenameRule() throws Exception
	{
		String ruleSetUUID = createRuleSet( "testRenameRule" );
		String ruleUUID = createRule( ruleSetUUID, "testRenameRule" );

        String newRuleUUID = brmsRepository.renameRule( ruleUUID, "rule_1_NEW_NAME" );
        Rule rule = brmsRepository.loadRule( newRuleUUID );
		assertEquals( rule.getUUID(), newRuleUUID );
		assertEquals( rule.getName(), "rule_1_NEW_NAME" );
	}

	@Test
	public void testListStates() throws Exception
	{
        brmsRepository.createStatus( "Active" );
        brmsRepository.createStatus( "Inactive" );
        
        String[] states = brmsRepository.loadStates();

        assertEquals( 3, states.length );
        assertEquals( "Draft", states[0] ); // default state is Draft
        assertEquals( "Active", states[1] );
        assertEquals( "Inactive", states[2] );
	}
	
	@Test
	public void testChangeRuleSetStatus() throws Exception
	{
        String ruleSetUUID = brmsRepository.createRuleSet( "testChangeRuleSetStatus", "Rule set description" );
        assertNotNull( ruleSetUUID );

        brmsRepository.createStatus( "Active" );
        brmsRepository.createStatus( "Inactive" );
        
        brmsRepository.changeRuleSetStatus( ruleSetUUID, "Active" );
        RuleSet ruleSet = brmsRepository.loadRuleSet( ruleSetUUID );
		assertEquals( ruleSet.getStatus(), "Active" );

        brmsRepository.changeRuleSetStatus( ruleSetUUID, "Inactive" );
        ruleSet = brmsRepository.loadRuleSet( ruleSetUUID );
		assertEquals( ruleSet.getStatus(), "Inactive" );
	}

	@Test
	public void testChangeRuleStatus() throws Exception
	{
		String ruleSetUUID = createRuleSet( "testChangeRuleStatus" );
		String ruleUUID = createRule( ruleSetUUID, "testChangeRuleStatus" );

        brmsRepository.createStatus( "Active" );
        brmsRepository.createStatus( "Inactive" );

        brmsRepository.changeRuleStatus( ruleUUID, "Active" );
        Rule rule = brmsRepository.loadRule( ruleUUID );
		assertEquals( rule.getStatus(), "Active" );

        brmsRepository.changeRuleStatus( ruleUUID, "Inactive" );
        rule = brmsRepository.loadRule( ruleUUID );
		assertEquals( rule.getStatus(), "Inactive" );
	}

	@Test
	public void testCompileValidRuleSetSource() throws Exception
	{
		String ruleSetUUID = createRuleSet( "testCompileValidRuleSetSource", 
				"import java.util.Calendar", false );
		createRule( ruleSetUUID, "testCompileValidRuleSetSource" );

		try
		{
			String drl1 = getSimpleDRL( "testCompileValidRuleSetSource" );
			String drl2 = brmsRepository.compileRuleSetSource( ruleSetUUID );
			assertTrue( drl2.indexOf( drl1 ) > -1 );
		}
		catch( BRMSRepositoryException e )
		{
			fail( e.getMessage() );
		}
	}

	@Test
	public void testCompileInvalidRuleSetSource() throws Exception
	{
		String ruleSetUUID = createRuleSet( "testCompileRuleSetSource", 
				"import jav.util.Calend", false );
		createRule( ruleSetUUID, "testCompileRuleSetSource" );

		try
		{
			String drl = brmsRepository.compileRuleSetSource( ruleSetUUID );
		}
		catch( BRMSRepositoryException e )
		{
			assertTrue( e.getMessage() != null );
		}
	}

	@Test
	public void testCompileValidRuleSet() throws Exception
	{
		String ruleSetUUID = createRuleSet( "testCompileRuleSet", 
				"import java.util.Calendar", false );
		createRule( ruleSetUUID, "testCompileRuleSet" );

		try
		{
	        BuilderResultList results = brmsRepository.compileRuleSet( ruleSetUUID );
			assertNull( results );
		}
		catch( BRMSRepositoryException e )
		{
			fail( e.getMessage() );
		}
	}

	@Test
	public void testCompileInvalidRuleSet() throws Exception
	{
		String ruleSetUUID = createRuleSet( "testCompileInvalidRuleSet", 
				"import java.util.Calendar", false );
		String ruleUUID = createRule( ruleSetUUID, "testCompileInvalidRuleSet" );

		Rule rule = brmsRepository.loadRule( ruleUUID );
		rule.setContent( "InvalidRuleSource" );
		brmsRepository.checkinRule( rule, "Some invalid source" );
		
		try
		{
	        BuilderResultList results = brmsRepository.compileRuleSet( ruleSetUUID );
			assertNotNull( results );
			assertTrue( results.toString().indexOf( "InvalidRuleSource" ) > -1 );
		}
		catch( BRMSRepositoryException e )
		{
			fail( e.getMessage() );
		}
	}

	@Test
	public void testRemoveRule() throws Exception
	{
		String ruleSetUUID = createRuleSet( "testRemoveRule" );
		String ruleUUID = createRule( ruleSetUUID, "testRemoveRule" );

		brmsRepository.removeRule( ruleUUID );

        try
        {
	        brmsRepository.loadRule( ruleUUID );
		} 
        catch ( BRMSRepositoryException e ) 
        {
			assertTrue( true );
		}
	}

	@Test
	public void testArchiveUnArchiveRuleSet() throws Exception
	{
		String ruleSetUUID = createRuleSet( "testArchiveUnArchiveRuleSet", null, false );
		
        RuleSet ruleSet = brmsRepository.loadRuleSet( ruleSetUUID );
        assertEquals( ruleSetUUID, ruleSet.getUUID() );

        String ruleSource = getSimpleRule();
        String ruleUUID = brmsRepository.createRule( 
        		ruleSetUUID, "rule_1", "Rule set description", ruleSource,  null );
        brmsRepository.checkinRule( ruleUUID, null );
        
        // Archived Rule set
        brmsRepository.archiveRuleSet( ruleSetUUID, null );
        
        assertTrue( brmsRepository.loadRuleSet( ruleSetUUID ).isArchived() );
        
        List<RuleSet> list = brmsRepository.loadArchivedRuleSets();
        assertNotNull( list );
        assertTrue( list.size() > 0 );
        assertEquals( ruleSet.getUUID(), list.get( 0 ).getUUID() );
        assertEquals( !ruleSet.isArchived(), list.get( 0 ).isArchived() );
        assertEquals( "Archived", list.get( 0 ).getCheckinComment() );

        // UnArchived Rule set
        brmsRepository.unArchiveRuleSet( ruleSetUUID, null );
        
        assertFalse( brmsRepository.loadRuleSet( ruleSetUUID ).isArchived() );

        RuleSet ruleSet2 = brmsRepository.loadRuleSet( ruleSetUUID );
        assertEquals( ruleSet.getUUID(), ruleSet2.getUUID() );
        assertEquals( !ruleSet.isArchived(), !ruleSet2.isArchived() );
        assertEquals( "Unarchived", ruleSet2.getCheckinComment() );
	}

	@Test
	public void testArchiveUnArchiveRule() throws Exception
	{
		String ruleSetUUID = createRuleSet( "testArchiveUnArchiveRule" );
		String ruleUUID = createRule( ruleSetUUID, "testArchiveUnArchiveRule" );
        
        Rule rule = brmsRepository.loadRule( ruleUUID );

        assertFalse( brmsRepository.loadRule( ruleUUID ).isArchived() );
        // Archived Rule
        brmsRepository.archiveRule( ruleUUID, null );
        assertTrue( brmsRepository.loadRule( ruleUUID ).isArchived() );
        
        List<Rule> list = brmsRepository.loadArchivedRules();
        assertNotNull( list );
        assertTrue( list.size() > 0 );
        assertEquals( rule.getUUID(), list.get( 0 ).getUUID() );
        assertEquals( !rule.isArchived(), list.get( 0 ).isArchived() );
        assertEquals( "Archived", list.get( 0 ).getCheckinComment() );

        // UnArchived Rule
        brmsRepository.unArchiveRule( ruleUUID, null );
        
        Rule rule2 = brmsRepository.loadRule( ruleUUID );
        assertEquals( rule.getUUID(), rule2.getUUID() );
        assertEquals( !rule.isArchived(), !rule2.isArchived() );
        assertEquals( "Unarchived", rule2.getCheckinComment() );
	}

	@Test
	public void testLoadRuleHistoryAndRestore() throws Exception
	{
		String ruleSetUUID = createRuleSet( "testLoadRuleHistoryAndRestore", "import java.util.Calendar", false );
		String ruleUUID = createRule( ruleSetUUID, "testLoadRuleHistoryAndRestore", false );

        // Version 1
        Rule rule1 = brmsRepository.loadRule( ruleUUID );
        brmsRepository.checkinRule( rule1.getUUID(), "Checkin version 1" );
        
        // Version 2
        Rule rule2 = brmsRepository.loadRule( ruleUUID );
        brmsRepository.checkinRule( rule2, "Checkin version 2" );

        // Version 3
        Rule rule3Head = brmsRepository.loadRule( ruleUUID );
        brmsRepository.checkinRule( rule3Head, "Checkin version 3 - HEAD version" );

        List<Rule> ruleHistory = brmsRepository.loadRuleHistory( ruleUUID );
        assertEquals( 2, ruleHistory.size() );
        
        long version1 = ruleHistory.get( 0 ).getVersionNumber();
        long version2 = ruleHistory.get( 1 ).getVersionNumber();
        assertTrue( version1 != version2 );

        Rule head = brmsRepository.loadRule( ruleUUID );
        brmsRepository.restoreVersion( 
        		ruleHistory.get( 0 ).getVersionSnapshotUUID(), 
        		head.getUUID(), 
        		"Restoring head version 3 with version 1" );

        // Version 4
        Rule rule = brmsRepository.loadRule( ruleUUID );
        assertEquals( "Restoring head version 3 with version 1", rule.getCheckinComment() );
        assertEquals( 4, rule.getVersionNumber() );
	}

	@Test
	public void testRebuildAllSnapshots() throws Exception
	{
		String ruleSetUUID = createRuleSet( "testRebuildAllSnapshots", "import java.util.Calendar", true );
		String ruleUUID = createRule( ruleSetUUID, "testRebuildAllSnapshots" );
		
        brmsRepository.createRuleSetSnapshot( 
        		"testRebuildAllSnapshots", "SNAPSHOT-1", false, "Build snapshot 1" );
        
        long snapshotTime1 = brmsRepository.loadRuleSetSnapshot( 
        		"testRebuildAllSnapshots", "SNAPSHOT-1" ).getLastModifiedDate().getTimeInMillis();
        
        brmsRepository.rebuildAllSnapshots();
        
        long snapshotTime2 = brmsRepository.loadRuleSetSnapshot( 
        		"testRebuildAllSnapshots", "SNAPSHOT-1" ).getLastModifiedDate().getTimeInMillis();
        
        assertTrue( snapshotTime2 > snapshotTime1 );
        
        Rule rule = brmsRepository.loadRule( ruleUUID );
        rule.setContent( "Some invalid source code" );
        brmsRepository.checkinRule( rule, "Checkin invalid source" );
        
        brmsRepository.createRuleSetSnapshot( 
        		"testRebuildAllSnapshots", "SNAPSHOT-2", false, "Build invalid snapshot 2" );
        
        try
        {
        	brmsRepository.rebuildAllSnapshots();
        }
        catch( BRMSRepositoryException e )
        {
			assertTrue( true );
        }
	}

	@Test
	public void testExportImportAsXmlRulesRepository() throws Exception
	{
		String ruleSetUUID = createRuleSet( "testExportImportAsXmlRulesRepository" );
		String ruleUUID = createRule( ruleSetUUID, "testExportImportAsXmlRulesRepository" );
        
        try
        {
	        // Export repository
	        byte[] export = brmsRepository.exportRulesRepositoryAsXml();
	        brmsRepository.removeRule( ruleUUID );
        	brmsRepository.importRulesRepository( export );
        }
        catch( BRMSRepositoryException e )
        {
			fail( e.getMessage() );
        }
        
        RuleSet ruleSet = brmsRepository.loadRuleSet( ruleSetUUID );
        assertFalse( ruleSet.getUUID() == null );
	}

	@Test
	public void testExportImportRulesRepository() throws Exception
	{
		String ruleSetUUID = createRuleSet( "testExportImportRulesRepository" );
		String ruleUUID = createRule( ruleSetUUID, "testExportImportRulesRepository" );

        try
        {
	        // Export repository
	        ByteArrayOutputStream export = 
	        	brmsRepository.exportRulesRepositoryAsZip( "repository-export.xml" );
	        assertNotNull( export );

	        byte[] byteExport = toByteArrayFromZip( export, "repository-export.xml" );
	        assertNotNull( byteExport );
	        
	        brmsRepository.removeRule( ruleUUID );
        	brmsRepository.importRulesRepository( byteExport );
        }
        catch( BRMSRepositoryException e )
        {
			fail( e.getMessage() );
        }
        
        RuleSet ruleSet = brmsRepository.loadRuleSet( ruleSetUUID );
        assertFalse( ruleSet.getUUID() == null );
	}
	
	private byte[] toByteArrayFromZip( ByteArrayOutputStream baos, String filename )
		throws IOException
	{
        ByteArrayInputStream bin = new ByteArrayInputStream( baos.toByteArray() );
        ZipInputStream zis = new ZipInputStream( bin );

        ZipEntry entry =  zis.getNextEntry();
        assertEquals( entry.getName() , filename );
        assertFalse( entry.isDirectory() );

        // Print XML repository file
        //FileOutputStream out = new FileOutputStream("C:/repository-export.zip");
		//out.write( export.toByteArray() );
		//out.close();
        
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int len;
        while ( ( len = zis.read( buf ) ) > 0 ) 
        {
        	bout.write( buf, 0, len );
        }
        return bout.toByteArray();
	}
	
	@Test
	public void testCompileSourceValid() throws Exception
	{
		String packageName = "testCompileSourceValid";
		Reader drl = new StringReader( getSimpleDRL( packageName ) );
		try 
		{
			org.drools.rule.Package pkg = 
				(org.drools.rule.Package) brmsRepository.compileSource( drl );
			assertTrue( pkg != null );
			assertTrue( pkg.isValid() );
			assertTrue( pkg.getName().equals( packageName ) );
		} 
		catch ( BRMSRepositoryException e) 
		{
			fail( e.getMessage() );
		}
	}
	
	@Test
	public void testCompileSourceInvalidMissingPackage() throws Exception
	{
		String src = "Some invalid source code";
		Reader drl = new StringReader( src );
		try 
		{
			brmsRepository.compileSource( drl );
		} 
		catch ( BRMSRepositoryException e) 
		{
			assertTrue( e.getMessage() != null );
		}
	}

	@Test
	public void testCompileSourceInvalidPackage() throws Exception
	{
		String src = "package x y z";
		Reader drl = new StringReader( src );
		
		try 
		{
			brmsRepository.compileSource( drl );
		} 
		catch ( BRMSRepositoryException e) 
		{
			assertTrue( e.getMessage() != null );
		}
	}

	@Test
	public void testCompileSourceInvalidImport() throws Exception
	{
		StringBuilder sb = new StringBuilder();
		sb.append( "package testCompileSourceInvalidImport" );
		sb.append( "\n" );
		sb.append( "rule \"HelloDrools\"" );
		sb.append( "\n" );
		sb.append( "     when" );
		sb.append( "\n" );
		sb.append( "          now: Calendar()" );
		sb.append( "\n" );
		sb.append( "          eval ( ( now.get(Calendar.MINUTE) % 2 == 0 ) )" );
		sb.append( "\n" );
		sb.append( "     then" );
		sb.append( "\n" );
		sb.append( "          System.out.println(\"Minute is even \" + now.get(Calendar.MINUTE));" );
		sb.append( "\n" );
		sb.append( "end" );
		
		Reader drl = new StringReader( sb.toString() );
		
		try 
		{
			brmsRepository.compileSource( drl );
		} 
		catch ( BRMSRepositoryException e) 
		{
			assertTrue( e.getMessage() != null );
		}
	}

	@Test
	public void testCompileSourceInvalidRule() throws Exception
	{
		StringBuilder sb = new StringBuilder();
		sb.append( "package testCompileSourceInvalidRule" );
		sb.append( "\n" );
		sb.append( "rule \"HelloDrools\"" );
		sb.append( "\n" );
		sb.append( "     when" );
		sb.append( "\n" );
		sb.append( "          now( x = 1 )" );
		sb.append( "\n" );
		sb.append( "     then" );
		sb.append( "\n" );
		sb.append( "          System.out.println(\"Success \");" );
		sb.append( "\n" );
		sb.append( "end" );
		
		Reader drl = new StringReader( sb.toString() );
		
		try 
		{
			brmsRepository.compileSource( drl );
		} 
		catch ( BRMSRepositoryException e) 
		{
			assertTrue( e.getMessage() != null );
		}
	}

}
