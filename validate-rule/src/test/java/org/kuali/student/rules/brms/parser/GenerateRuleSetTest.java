package org.kuali.student.rules.brms.parser;

import static org.junit.Assert.*;

import java.net.URL;
import java.util.Hashtable;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kuali.student.brms.repository.RuleEngineRepository;
import org.kuali.student.brms.repository.drools.DroolsJackrabbitRepository;
import org.kuali.student.brms.repository.drools.RuleEngineRepositoryDroolsImpl;
import org.kuali.student.brms.repository.rule.RuleSet;

public class GenerateRuleSetTest {

    @Before
    public void setUp() throws Exception {
        // Initialize repository
        DroolsJackrabbitRepository repo;
        URL url = DroolsJackrabbitRepository.class.getResource("/repository");
        System.out.println("the url " + url.toString());
        repo = new DroolsJackrabbitRepository(url);
        repo.initialize();
        
        repo.clearData();
        RuleEngineRepository brmsRepository = new RuleEngineRepositoryDroolsImpl( repo.getRepository() );
        String rulePackage = "org.kuali.student.rules.enrollment";
        //String rulesetUuid = brmsRepository.createRuleSet(rulePackage, "My package description" );
        
        
        GenerateRuleSet grs = new GenerateRuleSet("A0*B4+(C*D)");
        //grs.setRuleEngineRepository(brmsRepository);
        //grs.setRuleSetUuid(rulesetUuid);
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
        
        // compile rule and save in repository
        //CompilerResultList results = brmsRepository.compileRuleSet(rulesetUuid);

        System.out.println("***** Rule source before compilation *****");
        System.out.println(grs.getRuleSet().getContent());
        System.out.println("\n******************************************");
        
        String rulesetUuid = brmsRepository.createRuleSet(grs.getRuleSet());
        
        // load rule from repo and print
        System.out.println("***** Rule source after compilation *****");
        RuleSet ruleset = brmsRepository.loadRuleSet(rulesetUuid);
        System.out.println(ruleset.getContent());
        System.out.println("******************************************");
        //Shutdown (and logout from) the repository:
        repo.shutdownRepository();
    }

    @After
    public void tearDown() throws Exception {}

    @Test
    public void testParse() {
        //fail("Not yet implemented");
    }

}
