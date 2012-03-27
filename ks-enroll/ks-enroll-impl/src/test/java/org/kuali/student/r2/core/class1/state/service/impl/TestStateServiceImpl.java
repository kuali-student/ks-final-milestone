package org.kuali.student.r2.core.class1.state.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertNotSame;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.kuali.student.r2.core.state.dto.LifecycleInfo;
import org.kuali.student.r2.core.state.dto.StateInfo;
import org.kuali.student.r2.core.state.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:state-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class TestStateServiceImpl {

    @Autowired
    private StateService stateService;
    private static String PRINCIPAL_ID = "123";
    private ContextInfo callContext = null;

    @Before
    public void setUp() {
        callContext = new ContextInfo();
        callContext.setPrincipalId(PRINCIPAL_ID);
        callContext.setCurrentDate(new Date());
    }

    @Test
    public void testCRUDState() throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException,
            AlreadyExistsException, DataValidationErrorException, ReadOnlyException,
            VersionMismatchException {

        // create
        LifecycleInfo origLife = new LifecycleInfo();
        RichTextInfo rti = new RichTextInfo();
        rti.setFormatted("<b>Formatted</b> state for testing purposes");
        rti.setPlain("Plain state for testing purposes");
        origLife.setDescr(rti);
        origLife.setKey(AtpServiceConstants.ATP_LIFECYCLE_KEY);
        origLife.setName("Testing");
        origLife.setRefObjectUri(AtpServiceConstants.REF_OBJECT_URI_ATP);
        AttributeInfo attr = new AttributeInfo();
        attr.setKey("attribute.key");
        attr.setValue("attribute value");
        origLife.getAttributes().add(attr);
        LifecycleInfo infoLife = stateService.createLifecycle(origLife.getKey(), origLife, callContext);
        assertNotNull(infoLife);
        assertEquals(origLife.getKey(), infoLife.getKey());
        assertEquals(origLife.getName(), infoLife.getName());
        assertEquals(origLife.getDescr().getPlain(), infoLife.getDescr().getPlain());
        assertEquals(origLife.getDescr().getFormatted(), infoLife.getDescr().getFormatted());
        assertEquals(origLife.getRefObjectUri(), infoLife.getRefObjectUri());
        assertEquals(origLife.getAttributes().size(), infoLife.getAttributes().size());
        assertNotNull(infoLife.getAttributes().get(0).getId());
        assertEquals(origLife.getAttributes().get(0).getKey(), infoLife.getAttributes().get(0).getKey());
        assertEquals(origLife.getAttributes().get(0).getValue(), infoLife.getAttributes().get(0).getValue());
        assertNotNull(infoLife.getMeta());
        assertNotNull(infoLife.getMeta().getCreateId());
        assertNotNull(infoLife.getMeta().getCreateTime());
        assertNotNull(infoLife.getMeta().getUpdateId());
        assertNotNull(infoLife.getMeta().getUpdateTime());
        assertNotNull(infoLife.getMeta().getVersionInd());

        // get
        origLife = infoLife;
        infoLife = stateService.getLifecycle(origLife.getKey(), callContext);
        assertNotNull(infoLife);
        assertEquals(origLife.getKey(), infoLife.getKey());
        assertEquals(origLife.getName(), infoLife.getName());
        assertEquals(origLife.getDescr().getPlain(), infoLife.getDescr().getPlain());
        assertEquals(origLife.getDescr().getFormatted(), infoLife.getDescr().getFormatted());
        assertEquals(origLife.getRefObjectUri(), infoLife.getRefObjectUri());
        assertEquals(origLife.getAttributes().size(), infoLife.getAttributes().size());
        assertNotNull(infoLife.getAttributes().get(0).getId());
        assertEquals(origLife.getAttributes().get(0).getKey(), infoLife.getAttributes().get(0).getKey());
        assertEquals(origLife.getAttributes().get(0).getValue(), infoLife.getAttributes().get(0).getValue());
        assertNotNull(infoLife.getMeta());
        assertNotNull(infoLife.getMeta().getCreateId());
        assertNotNull(infoLife.getMeta().getCreateTime());
        assertNotNull(infoLife.getMeta().getUpdateId());
        assertNotNull(infoLife.getMeta().getUpdateTime());
        assertNotNull(infoLife.getMeta().getVersionInd());

        // update
        origLife = infoLife;
        origLife.setName("Different Lifecycle");
        infoLife = stateService.updateLifecycle(origLife.getKey(), origLife, callContext);
        assertNotNull(infoLife);
        assertEquals(origLife.getKey(), infoLife.getKey());
        assertEquals(origLife.getName(), infoLife.getName());
        assertEquals(origLife.getDescr().getPlain(), infoLife.getDescr().getPlain());
        assertEquals(origLife.getDescr().getFormatted(), infoLife.getDescr().getFormatted());
        assertEquals(origLife.getRefObjectUri(), infoLife.getRefObjectUri());
        assertEquals(origLife.getAttributes().size(), infoLife.getAttributes().size());
        assertNotNull(infoLife.getAttributes().get(0).getId());
        assertEquals(origLife.getAttributes().get(0).getKey(), infoLife.getAttributes().get(0).getKey());
        assertEquals(origLife.getAttributes().get(0).getValue(), infoLife.getAttributes().get(0).getValue());
        assertNotNull(infoLife.getMeta());
        assertNotNull(infoLife.getMeta().getCreateId());
        assertNotNull(infoLife.getMeta().getCreateTime());
        assertNotNull(infoLife.getMeta().getUpdateId());
        assertNotNull(infoLife.getMeta().getUpdateTime());
        assertNotSame(origLife.getMeta().getVersionInd(), infoLife.getMeta().getVersionInd());

        LifecycleInfo atpLife = infoLife;

        // create state
        StateInfo orig = new StateInfo();
        orig.setKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        orig.setLifecycleKey(atpLife.getKey());
        rti = new RichTextInfo();
        rti.setFormatted("<b>Formatted again</b> state for testing purposes");
        rti.setPlain("Plain state again for testing purposes");
        orig.setDescr(rti);
        orig.setName("Testing state");
        Date effDate = new Date();
        orig.setEffectiveDate(effDate);
        Calendar cal = Calendar.getInstance();
        cal.set(2022, 8, 23);
        orig.setExpirationDate(cal.getTime());
        attr = new AttributeInfo();
        attr.setKey("attribute.key");
        attr.setValue("attribute value");
        orig.getAttributes().add(attr);
        StateInfo info = stateService.createState(orig.getLifecycleKey(), orig.getKey(), orig, callContext);
        assertNotNull(info);
        assertEquals(orig.getKey(), info.getKey());
        assertEquals(orig.getLifecycleKey(), info.getLifecycleKey());
        assertEquals(orig.getName(), info.getName());
        assertEquals(orig.getEffectiveDate(), info.getEffectiveDate());
        assertEquals(orig.getExpirationDate(), info.getExpirationDate());
        assertEquals(orig.getAttributes().size(), info.getAttributes().size());
        assertNotNull(info.getAttributes().get(0).getId());
        assertEquals(orig.getAttributes().get(0).getKey(), info.getAttributes().get(0).getKey());
        assertEquals(orig.getAttributes().get(0).getValue(), info.getAttributes().get(0).getValue());
        assertNotNull(info.getMeta());
        assertNotNull(info.getMeta().getCreateId());
        assertNotNull(info.getMeta().getCreateTime());
        assertNotNull(info.getMeta().getUpdateId());
        assertNotNull(info.getMeta().getUpdateTime());
        assertNotNull(info.getMeta().getVersionInd());

        // get
        orig = info;
        info = stateService.getState(orig.getKey(), callContext);
        assertNotNull(info);
        assertEquals(orig.getKey(), info.getKey());
        assertEquals(orig.getLifecycleKey(), info.getLifecycleKey());
        assertEquals(orig.getName(), info.getName());
        assertEquals(orig.getDescr().getPlain(), info.getDescr().getPlain());
        assertEquals(orig.getEffectiveDate(), info.getEffectiveDate());
        assertEquals(orig.getExpirationDate(), info.getExpirationDate());
        assertEquals(orig.getAttributes().size(), info.getAttributes().size());
        assertEquals(orig.getAttributes().get(0).getId(), info.getAttributes().get(0).getId());
        assertEquals(orig.getAttributes().get(0).getKey(), info.getAttributes().get(0).getKey());
        assertEquals(orig.getAttributes().get(0).getValue(), info.getAttributes().get(0).getValue());
        assertNotNull(info.getMeta().getCreateId());
        assertNotNull(info.getMeta().getCreateTime());
        assertNotNull(info.getMeta().getUpdateId());
        assertNotNull(info.getMeta().getUpdateTime());
        assertNotSame(orig.getMeta().getVersionInd(), info.getMeta().getVersionInd());


        // update
        orig = info;
        orig.setName("DifferentName");
        info = stateService.updateState(orig.getKey(), orig, callContext);
        assertNotNull(info);
        assertEquals(orig.getKey(), info.getKey());
        assertEquals(orig.getLifecycleKey(), info.getLifecycleKey());
        assertEquals(orig.getName(), info.getName());
        assertEquals(orig.getEffectiveDate(), info.getEffectiveDate());
        assertEquals(orig.getExpirationDate(), info.getExpirationDate());
        assertEquals(orig.getAttributes().size(), info.getAttributes().size());
        assertNotNull(info.getAttributes().get(0).getId());
        assertEquals(orig.getAttributes().get(0).getKey(), info.getAttributes().get(0).getKey());
        assertEquals(orig.getAttributes().get(0).getValue(), info.getAttributes().get(0).getValue());
        assertNotNull(info.getMeta());
        assertNotNull(info.getMeta().getCreateId());
        assertNotNull(info.getMeta().getCreateTime());
        assertNotNull(info.getMeta().getUpdateId());
        assertNotNull(info.getMeta().getUpdateTime());
        assertNotNull(info.getMeta().getVersionInd());

        StateInfo atpDraftState = info;

        // delete state
        StatusInfo result = stateService.deleteState(atpDraftState.getKey(), callContext);
        assertEquals(true, result.getIsSuccess());
        try {
            info = stateService.getState(atpDraftState.getKey(), callContext);
            fail("should have thrown dne exception");
        } catch (DoesNotExistException e) {
            // expected
        }
        // delete life 
        result = stateService.deleteLifecycle(atpLife.getKey(), callContext);
        assertEquals(true, result.getIsSuccess());
        try {
            infoLife = stateService.getLifecycle(atpLife.getKey(), callContext);
            fail("should have thrown dne exception");
        } catch (DoesNotExistException e) {
            // expected
        }
    }
}
