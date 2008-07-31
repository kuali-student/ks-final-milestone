package org.kuali.student.rules.performance.repository.drools;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URL;

import javax.jcr.SimpleCredentials;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.rules.repository.RuleEngineRepository;
import org.kuali.student.rules.repository.drools.DroolsJackrabbitRepository;
import org.kuali.student.rules.repository.drools.RuleEngineRepositoryDroolsImpl;
import org.kuali.student.rules.repository.rule.RuleSet;
import org.kuali.student.rules.util.RuleSetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>This class tests the time it takes to create and load 
 * 1, 10, 100, 200 etc. rules.</p>
 *
 * <p>Please note that when creating and  loading more than 1000 rules the 
 * following Java VM settings must be set:</p>
 * 
 * <pre>
 * -Xmx1024m
 * -Xms1024m
 * -XX:PermSize=128m
 * -XX:MaxPermSize=384m
 * </pre>
 * 
 * @author Kuali Student Team (len.kuali@googlegroups.com)
 *
 */
public class RuleEngineRepositoryPerfTest {
    /** SLF4J logging framework */
    final static Logger logger = LoggerFactory.getLogger(RuleEngineRepositoryPerfTest.class);
    /** Drools Jackrabbit repository */
    private static DroolsJackrabbitRepository jackrabbitRepository;
    /** Rule engine repository interface */
    private static RuleEngineRepository brmsRepository;
    /** Rule set utility class */
    private final RuleSetUtil ruleSetUtil = RuleSetUtil.getInstance();
    
    @BeforeClass
    public static void setUpOnce() throws Exception {
        URL url = RuleEngineRepositoryPerfTest.class.getResource("/drools-repository");
        jackrabbitRepository = new DroolsJackrabbitRepository(url);
        //jackrabbitRepository.clearAll();
        jackrabbitRepository.startupRepository();
        jackrabbitRepository.login(new SimpleCredentials("superuser", "superuser".toCharArray()) );
    }

    @AfterClass
    public static void tearDownOnce() throws Exception {
        jackrabbitRepository.shutdownRepository();
        //jackrabbitRepository.clearAll();
    }

    @Before
    public void setUp() throws Exception {
        jackrabbitRepository.login(jackrabbitRepository.getCredentials());
        jackrabbitRepository.clearData();
        brmsRepository = new RuleEngineRepositoryDroolsImpl(jackrabbitRepository.getRepository());
    }

    @After
    public void tearDown() throws Exception {
        jackrabbitRepository.logout();
    }

    @Test
    public void testCreateAndLoadRuleSet() throws Exception {
        int[] iterations = new int[] {1, 10, 50, 100};
        //int[] iterations = new int[] {1, 10, 50, 100, 200, 500, 1000, 
        //        2000, 3000, 4000, 5000, 10000};
        int c = 0;
        for(int ruleCount : iterations) {
            c++;
            int droolsRuleCount = ruleCount * 2;
            RuleSet actualRuleSet = ruleSetUtil.createRuleSet(ruleCount);
            logger.info(c+": 1-Creating ruleset: Rule count=" + droolsRuleCount);
        
            // Create rule set
            long start = System.currentTimeMillis();
            RuleSet expectedRuleSet = brmsRepository.createRuleSet(actualRuleSet);
            long now = System.currentTimeMillis();
            logger.info(c+": 2-Creating ruleset: Time=" + ((now - start) / 1000d) + " secs");
            assertNotNull(expectedRuleSet);
            assertEquals(expectedRuleSet.getRules().size(), actualRuleSet.getRules().size());

            // Load rule set
            start = System.currentTimeMillis();
            expectedRuleSet = brmsRepository.loadRuleSet(expectedRuleSet.getUUID());
            now = System.currentTimeMillis();
            logger.info(c+": 3-Loading rule set: Time=" + ((now - start) / 1000d) + " secs");

            // Assert rule set
            assertNotNull(expectedRuleSet);
            ruleSetUtil.assertRuleSetEquals(actualRuleSet, expectedRuleSet);
            // Remove the rule set
            brmsRepository.removeRuleSet(expectedRuleSet.getUUID());
            logger.info(c+": 4-Rule set removed");
        }
    }

    @Test
    public void testCreateAndLoadRuleSetSnapshot() throws Exception {
        int[] iterations = new int[] {1, 10, 50, 100};
        //int[] iterations = new int[] {1, 10, 50, 100, 200, 500, 1000, 
        //        2000, 3000, 4000, 5000, 10000};
        int c = 0;
        for(int ruleCount : iterations) {
            c++;
            int droolsRuleCount = ruleCount * 2;
            RuleSet actualRuleSet = ruleSetUtil.createRuleSet(ruleCount);
            logger.info(c+": 1-Creating ruleset: Rule count=" + droolsRuleCount);
        
            // Create rule set
            long start = System.currentTimeMillis();
            RuleSet expectedRuleSet = brmsRepository.createRuleSet(actualRuleSet);
            long now = System.currentTimeMillis();
            logger.info(c+": 2-Creating ruleset: Time=" + ((now - start) / 1000d) + " secs");
            assertNotNull(expectedRuleSet);
            assertEquals(expectedRuleSet.getRules().size(), actualRuleSet.getRules().size());

            // Create rule set snapshot
            String snapshotName = "SNAPSHOT-1";
            start = System.currentTimeMillis();
            brmsRepository.createRuleSetSnapshot(actualRuleSet.getName(), snapshotName, "Rule set Snapshot");
            now = System.currentTimeMillis();
            logger.info(c+": 3-Creating rule set snapshot: Time=" + ((now - start) / 1000d) + " secs");
            
            // Load rule set snapshot
            start = System.currentTimeMillis();
            RuleSet expectedRuleSetSnapshot = brmsRepository.loadRuleSetSnapshot(expectedRuleSet.getName(), snapshotName);
            now = System.currentTimeMillis();
            logger.info(c+": 4-Loading rule set snapshot: Time=" + ((now - start) / 1000d) + " secs");

            // Assert rule set snapshot
            assertNotNull(expectedRuleSetSnapshot);
            ruleSetUtil.assertRuleSetEquals(actualRuleSet, expectedRuleSetSnapshot);
            // Remove rule set snapshot
            brmsRepository.removeRuleSetSnapshot(expectedRuleSetSnapshot.getName(), expectedRuleSetSnapshot.getSnapshotName());
            // Remove rule set
            brmsRepository.removeRuleSet(expectedRuleSet.getUUID());
            logger.info(c+": 5-Rule set and snapshot removed");
        }
    }
}
