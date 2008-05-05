package org.kuali.student.rules.brms.parser;

import java.net.URL;
import java.util.Hashtable;

import javax.jcr.Credentials;
import javax.jcr.SimpleCredentials;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.brms.repository.RuleEngineRepository;
import org.kuali.student.brms.repository.drools.DroolsJackrabbitRepository;
import org.kuali.student.brms.repository.drools.RuleEngineRepositoryDroolsImpl;
import org.kuali.student.brms.repository.rule.RuleSet;

public class GenerateRuleSetTest {

    private static DroolsJackrabbitRepository repo;
    
    private Credentials getCredentials() {
        String id = "superuser";
        char[] password = "superuser".toCharArray();
        return new SimpleCredentials(id, password);
    }

    @BeforeClass
    public static void setUpOnce() throws Exception {
        // Start and initialize a new repository
        URL url = DroolsJackrabbitRepository.class.getResource("/repository");
        System.out.println("repository URL: " + url.toString());
        repo = new DroolsJackrabbitRepository(url);
        // Remove all repository files
        repo.clearAll();
        repo.startupRepository();
    }

    @AfterClass
    public static void tearDownOnce() throws Exception {
        //Shutdown repository:
        repo.shutdownRepository();
    }

    @Before
    public void setUp() throws Exception {
        //Login to repository:
        repo.login(getCredentials());
        // Clear all data from repository
        repo.clearData();
    }

    @After
    public void tearDown() throws Exception {
        //Logout from repository:
        repo.logout();
    }

    @Test
    public void testParse() throws Exception {
        RuleEngineRepository brmsRepository = new RuleEngineRepositoryDroolsImpl( repo.getRepository() );
        String rulePackage = "org.kuali.student.rules.enrollment";
        
        GenerateRuleSet grs = new GenerateRuleSet("A0*B4+(C*D)");
        grs.setRuleSetName(rulePackage);
        grs.setRuleSetDescription("A rule set description");
        grs.setRuleName("Enrollment Physics");
        grs.setRuleDescription("A rule description"); // Rule description cannot be empty
        grs.setRuleCategory(null);
        grs.setRuleAttributes(null);
        
        Hashtable<String, String> funcConstraintsMap = new Hashtable<String, String>();
        funcConstraintsMap.put("A0", "A constraintID from DB");
        funcConstraintsMap.put("B4", "A constraintID from DB");
        funcConstraintsMap.put("C", "A constraintID from DB");
        funcConstraintsMap.put("D", "A constraintID from DB");
        grs.setLhsFuncConstraintMap(funcConstraintsMap);
        grs.setRuleOutcome("System.out.println(\"I'm Enrolled\");");
        
        grs.parse();
        
        // Print rule set
        System.out.println("***** Rule source before compilation *****");
        System.out.println(grs.getRuleSet().getContent());
        System.out.println("\n******************************************");
        
        // Create, compile and save a new rule set to the repository
        String rulesetUuid = brmsRepository.createRuleSet(grs.getRuleSet());
        
        // Load rule set from repository and print it
        System.out.println("***** Rule source after compilation *****");
        RuleSet ruleset = brmsRepository.loadRuleSet(rulesetUuid);
        System.out.println(ruleset.getContent());
        System.out.println("******************************************");
    }

}
