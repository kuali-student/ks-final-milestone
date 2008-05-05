package org.kuali.student.rules.validate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.rules.BRMSCore.util.UtilBRMSDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.jpa.AbstractJpaTests;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:validate-rule-context.xml","classpath:application-context.xml"})
@Transactional
@TransactionConfiguration(transactionManager = "JtaTxManager")
public class EnforceRuleTest extends AbstractJpaTests {

    @Autowired
    private EnforceRule enforceRule;
    
    @Autowired
    private UtilBRMSDatabase brmsDatabaseUtil;
    
    @Test
    public void testValidation() throws Exception {
        enforceRule.validateLuiPersonRelation("John", "Math 101", "1", null);
    }

    @Override
    @Before
    public void onSetUpInTransaction() throws Exception {
        brmsDatabaseUtil.populateDatabase();
        brmsDatabaseUtil.compileDroolsRule();
    }

    @Override
    // @After
    public void onTearDownAfterTransaction() throws Exception {
        brmsDatabaseUtil.deleteRules();
        super.onTearDownInTransaction();
        setDirty();
    }

    /**
     * @return the enforceRule
     */
    public EnforceRule getEnforceRule() {
        return enforceRule;
    }

    /**
     * @param enforceRule the enforceRule to set
     */
    public void setEnforceRule(EnforceRule enforceRule) {
        this.enforceRule = enforceRule;
    }

    /**
     * @return the brmsDatabase
     */
    public UtilBRMSDatabase getBrmsDatabase() {
        return brmsDatabaseUtil;
    }

    /**
     * @param brmsDatabase the brmsDatabase to set
     */
    public void setBrmsDatabase(UtilBRMSDatabase brmsDatabase) {
        this.brmsDatabaseUtil = brmsDatabase;
    }
}
