package org.kuali.student.enrollment.class1.lrc.service.impl;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:lrc-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class TestLRCServiceImpl {

    public ContextInfo contextInfo = ContextInfo.newInstance();

    @Before
    public void setUp() throws Exception {
        contextInfo = ContextInfo.newInstance();
        contextInfo.setPrincipalId("123");
    }

    @Test
    @Ignore("Not implemented.") // TODO implement method
    public void testGetResultValuesGroup() throws Exception {

    }

    @Test
    @Ignore("Not implemented.") // TODO implement method
    public void testGetResultValuesGroupsByIdList() throws Exception {

    }

    @Test
    @Ignore("Not implemented.") // TODO implement method
    public void testGetResultValuesGroupsByResultValue() throws Exception {

    }

    @Test
    @Ignore("Not implemented.") // TODO implement method
    public void testGetResultValuesGroupIdsByType() throws Exception {

    }

    @Test
    @Ignore("Not implemented.") // TODO implement method
    public void testCreateResultValuesGroup() throws Exception {

    }

    @Test
    @Ignore("Not implemented.") // TODO implement method
    public void testUpdateResultValuesGroup() throws Exception {

    }

    @Test
    @Ignore("Not implemented.") // TODO implement method
    public void testDeleteResultValuesGroup() throws Exception {

    }

    @Test
    @Ignore("Not implemented.") // TODO implement method
    public void testValidateResultValuesGroup() throws Exception {

    }

    @Test
    @Ignore("Not implemented.") // TODO implement method
    public void testGetResultValue() throws Exception {

    }

    @Test
    @Ignore("Not implemented.") // TODO implement method
    public void testGetResultValuesByIdList() throws Exception {

    }

    @Test
    @Ignore("Not implemented.") // TODO implement method
    public void testGetResultValuesForResultValuesGroup() throws Exception {

    }

    @Test
    @Ignore("Not implemented.") // TODO implement method
    public void testCreateResultValue() throws Exception {

    }

    @Test
    @Ignore("Not implemented.") // TODO implement method
    public void testUpdateResultValue() throws Exception {

    }

    @Test
    @Ignore("Not implemented.") // TODO implement method
    public void testDeleteResultValue() throws Exception {

    }

    @Test
    @Ignore("Not implemented.") // TODO implement method
    public void testValidateResultValue() throws Exception {

    }

    @Test
    @Ignore("Not implemented.") // TODO implement method
    public void testGetResultScale() throws Exception {

    }

    @Test
    @Ignore("Not implemented.") // TODO implement method
    public void testGetResultValuesForScale() throws Exception {

    }

    @Test
    @Ignore("Not implemented.") // TODO implement method
    public void testGetDataDictionaryEntryKeys() throws Exception {

    }

    @Test
    @Ignore("Not implemented.") // TODO implement method
    public void testGetDataDictionaryEntry() throws Exception {

    }

    @Test
    @Ignore("Not implemented.") // TODO implement method
    public void testGetProcessByKey() throws Exception {

    }

    @Test
    @Ignore("Not implemented.") // TODO implement method
    public void testGetProcessByObjectType() throws Exception {

    }

    @Test
    @Ignore("Not implemented.") // TODO implement method
    public void testGetState() throws Exception {

    }

    @Test
    @Ignore("Not implemented.") // TODO implement method
    public void testGetStatesByProcess() throws Exception {

    }

    @Test
    @Ignore("Not implemented.") // TODO implement method
    public void testGetInitialValidStates() throws Exception {

    }

    @Test
    @Ignore("Not implemented.") // TODO implement method
    public void testGetNextHappyState() throws Exception {

    }

    @Test
    @Ignore("Not implemented.") // TODO implement method
    public void testGetType() throws Exception {

    }

    @Test
    @Ignore("Not implemented.") // TODO implement method
    public void testGetTypesByRefObjectURI() throws Exception {

    }

    @Test
    @Ignore("Not implemented.") // TODO implement method
    public void testGetAllowedTypesForType() throws Exception {

    }

    @Test
    @Ignore("Not implemented.") // TODO implement method
    public void testGetTypeRelationsByOwnerType() throws Exception {

    }
}
