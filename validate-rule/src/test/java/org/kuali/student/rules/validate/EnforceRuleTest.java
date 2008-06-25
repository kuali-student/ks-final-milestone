package org.kuali.student.rules.validate;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.poc.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.poc.common.test.spring.PersistenceFileLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@PersistenceFileLocation("classpath:META-INF/brms-persistence.xml")
@ContextConfiguration(locations = {"classpath:validate-rule-context.xml"})
@Transactional
@TransactionConfiguration(transactionManager = "JtaTxManager")
public class EnforceRuleTest extends AbstractTransactionalDaoTest {

    @Autowired
    private EnforceRule enforceRule;

    @Autowired
    private UtilBRMSDatabase brmsDatabaseUtil;

    @Test
    public void testValidation() throws Exception {
        // Only thing that matters is the 3rd argument which maps to the functional rule Id
        ValidationResult result1 = enforceRule.validateLuiPersonRelation("John", "EMS 1001", "student", "enrolled");
        assertEquals(result1.isSuccess(), false);
        //        
        // ValidationResult result2 = enforceRule.validateLuiPersonRelation("John", "Math 101", "2", null);
        // assertEquals(result2.isSuccess(), false);
    }

    @Before
    public void onSetUpInTransaction() throws Exception {
        brmsDatabaseUtil.compileDroolsRule();
    }

    /**
     * @return the enforceRule
     */
    public EnforceRule getEnforceRule() {
        return enforceRule;
    }

    /**
     * @param enforceRule
     *            the enforceRule to set
     */
    public void setEnforceRule(EnforceRule enforceRule) {
        this.enforceRule = enforceRule;
    }

    /**
     * @return the brmsDatabase
     */
    public UtilBRMSDatabase getBrmsDatabaseUttil() {
        return brmsDatabaseUtil;
    }

    /**
     * @param brmsDatabase
     *            the brmsDatabase to set
     */
    public void setBrmsDatabaseUtils(UtilBRMSDatabase brmsDatabase) {
        this.brmsDatabaseUtil = brmsDatabase;
    }

}
