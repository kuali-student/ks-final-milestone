package org.kuali.student.enrollment.class1.lrc.service.impl;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.constants.LrcServiceConstants;
import org.kuali.student.r2.lum.lrc.dto.ResultScaleInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.infc.ResultValuesGroup;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:lrc-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class TestLRCServiceImpl {

    public ContextInfo contextInfo = null;

    @Autowired
    public LRCService lrcService;

    @Before
    public void setUp() throws Exception {
        contextInfo = new ContextInfo ();
        contextInfo.setPrincipalId("123");
    }

    @Test
    public void testGetResultValuesGroup() throws Exception {

        ResultValuesGroup group = lrcService.getResultValuesGroup("kuali.resultComponent.grade.passFail",contextInfo);
        assertEquals(group.getName(), "Pass/Fail Grading");
        assertEquals(group.getTypeKey(), LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_MULTIPLE);
        assertEquals(group.getStateKey(),"kuali.result.values.group.state.approved");
        assertEquals(group.getResultScaleKey(), LrcServiceConstants.RESULT_SCALE_KEY_GRADE_LETTER);
    }

    @Test
    public void testGetResultValuesGroupsByIds() throws Exception {

        List<String> resultValuesGroupKeys = new ArrayList<String>();
        resultValuesGroupKeys.add(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_LETTER);
        resultValuesGroupKeys.add(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PASSFAIL);

        List<ResultValuesGroupInfo> groups = lrcService.getResultValuesGroupsByIds(resultValuesGroupKeys,contextInfo);

        assertNotNull(groups);
        assertEquals(2,groups.size());


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
    @Ignore("Not yet implemented fully.")
    public void testCreateResultValuesGroup() throws Exception {
        ResultValuesGroupInfo info = new ResultValuesGroupInfo();
        info.setName("TestName");
        info.setKey("test.key");
        info.setStateKey("kuali.result.values.group.state.approved");
        info.setTypeKey(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_MULTIPLE);
        info.setResultScaleKey(LrcServiceConstants.RESULT_SCALE_KEY_GRADE_LETTER);

        List<String> resultValues = new ArrayList<String>();
        resultValues.add("result.values.a");
        resultValues.add("result.values.b");

        info.setResultValueKeys(resultValues);

        lrcService.createResultValuesGroup(info,contextInfo);

        ResultValuesGroupInfo newInfo = lrcService.getResultValuesGroup(info.getKey(),contextInfo);
        assertNotNull(newInfo);
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
    public void testGetResultValue() throws Exception {
        ResultValueInfo info = lrcService.getResultValue(LrcServiceConstants.RESULT_VALUE_KEY_GRADE_LETTER_A,contextInfo);

        assertNotNull(info);
        assertEquals(info.getName(),"Excellent");
        assertEquals(info.getStateKey(),"kuali.result.value.state.approved");
        assertEquals(info.getTypeKey(),LrcServiceConstants.RESULT_VALUE_TYPE_KEY_VALUE);
        assertEquals(info.getResultScaleKey(),LrcServiceConstants.RESULT_SCALE_KEY_GRADE_LETTER);
        assertEquals(info.getValue(),"A");
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
    public void testGetResultScale() throws Exception {
        ResultScaleInfo info = lrcService.getResultScale(LrcServiceConstants.RESULT_SCALE_KEY_GRADE_LETTER,contextInfo);

        assertNotNull(info);
        assertEquals(info.getName(),"A-F Grading Scale");
        assertEquals(info.getStateKey(),"kuali.result.scale.state.approved");
        assertEquals(info.getTypeKey(),LrcServiceConstants.RESULT_SCALE_TYPE_KEY_GRADE);
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

    @Test
    @Ignore("Not implemented.") // TODO implement method
    public void testGetResultValuesByIds() throws Exception {

    }

    public LRCService getLrcService() {
        return lrcService;
    }

    public void setLrcService(LRCService lrcService) {
        this.lrcService = lrcService;
    }
}
