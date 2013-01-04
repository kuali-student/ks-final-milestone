package org.kuali.student.r2.core.atp.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;

import java.util.List;
import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
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
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.atp.dto.AtpAtpRelationInfo;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:atp-test-context.xml"})
@Transactional
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
public class TestAtpServiceJpaPersistenceImpl {

    @Resource(name = "atpServiceImpl")
    public AtpService atpService;
    private static String principalId = "123";
    private ContextInfo callContext = null;

    @Before
    public void setUp() {
        callContext = new ContextInfo();
        callContext.setPrincipalId(principalId);
    }

    @Test
    public void testCrud() throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException,
            DataValidationErrorException, VersionMismatchException, ReadOnlyException,
            AlreadyExistsException {
        // test atp create
        AtpInfo origA = new AtpInfo();
        origA.setCode("CodeA");
        origA.setName("new Atp");
        origA.setTypeKey(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY);
        origA.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        origA.setStartDate(new Date());
        origA.setEndDate(new Date(new Date().getTime() + 1000));
        origA.setDescr(new RichTextHelper().fromPlain("test description"));
        AttributeInfo attr = new AttributeInfo();
        attr.setKey("attribute.key");
        attr.setValue("attribute value");
        origA.getAttributes().add(attr);
        AtpInfo resultA = atpService.createAtp(origA.getTypeKey(), origA, callContext);
        assertNotNull(resultA);
        assertNotNull(resultA.getId());
        assertEquals(origA.getTypeKey(), resultA.getTypeKey());
        assertEquals(origA.getStateKey(), resultA.getStateKey());
        assertEquals(origA.getName(), resultA.getName());
        assertEquals(origA.getCode(), resultA.getCode());
        assertEquals(origA.getDescr().getPlain(), resultA.getDescr().getPlain());
        assertEquals(origA.getStartDate(), resultA.getStartDate());
        assertEquals(origA.getEndDate(), resultA.getEndDate());
        assertEquals(origA.getAttributes().size(), resultA.getAttributes().size());
        assertNotNull(resultA.getAttributes().get(0).getId());
        assertEquals(origA.getAttributes().get(0).getKey(), resultA.getAttributes().get(0).getKey());
        assertEquals(origA.getAttributes().get(0).getValue(), resultA.getAttributes().get(0).getValue());
        assertNotNull(resultA.getMeta());
        assertNotNull(resultA.getMeta().getCreateId());
        assertNotNull(resultA.getMeta().getCreateTime());
        assertNotNull(resultA.getMeta().getUpdateId());
        assertNotNull(resultA.getMeta().getUpdateTime());
        assertNotNull(resultA.getMeta().getVersionInd());

        // test atp read
        origA = resultA;
        resultA = atpService.getAtp(resultA.getId(), callContext);
        assertNotNull(resultA);
        assertNotNull(resultA.getId());
        assertEquals(origA.getTypeKey(), resultA.getTypeKey());
        assertEquals(origA.getStateKey(), resultA.getStateKey());
        assertEquals(origA.getName(), resultA.getName());
        assertEquals(origA.getCode(), resultA.getCode());
        assertEquals(origA.getDescr().getPlain(), resultA.getDescr().getPlain());
        assertEquals(origA.getStartDate(), resultA.getStartDate());
        assertEquals(origA.getEndDate(), resultA.getEndDate());
        assertEquals(origA.getAttributes().size(), resultA.getAttributes().size());
        assertNotNull(resultA.getAttributes().get(0).getId());
        assertEquals(origA.getAttributes().get(0).getKey(), resultA.getAttributes().get(0).getKey());
        assertEquals(origA.getAttributes().get(0).getValue(), resultA.getAttributes().get(0).getValue());
        assertNotNull(resultA.getMeta());
        assertNotNull(resultA.getMeta().getCreateId());
        assertNotNull(resultA.getMeta().getCreateTime());
        assertNotNull(resultA.getMeta().getUpdateId());
        assertNotNull(resultA.getMeta().getUpdateTime());
        assertNotNull(resultA.getMeta().getVersionInd());

        // test atp update
        origA = resultA;
        origA.setName("updated name");
        origA.setDescr(new RichTextHelper().fromPlain("test description"));
        origA.setEndDate(new Date(new Date().getTime() + 2000));
        resultA = atpService.updateAtp(origA.getId(), origA, callContext);
        assertNotNull(resultA);
        assertNotNull(resultA.getId());
        assertEquals(origA.getTypeKey(), resultA.getTypeKey());
        assertEquals(origA.getStateKey(), resultA.getStateKey());
        assertEquals(origA.getName(), resultA.getName());
        assertEquals(origA.getCode(), resultA.getCode());
        assertEquals(origA.getDescr().getPlain(), resultA.getDescr().getPlain());
        assertEquals(origA.getStartDate(), resultA.getStartDate());
        assertEquals(origA.getEndDate(), resultA.getEndDate());
        assertEquals(origA.getAttributes().size(), resultA.getAttributes().size());
        assertNotNull(resultA.getAttributes().get(0).getId());
        assertEquals(origA.getAttributes().get(0).getKey(), resultA.getAttributes().get(0).getKey());
        assertEquals(origA.getAttributes().get(0).getValue(), resultA.getAttributes().get(0).getValue());
        assertNotNull(resultA.getMeta());
        assertNotNull(resultA.getMeta().getCreateId());
        assertNotNull(resultA.getMeta().getCreateTime());
        assertNotNull(resultA.getMeta().getUpdateId());
        assertNotNull(resultA.getMeta().getUpdateTime());
        assertNotSame(origA.getMeta().getVersionInd(), resultA.getMeta().getVersionInd());

        // test atp delete
        StatusInfo status = atpService.deleteAtp(origA.getId(), callContext);
        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        try {
            resultA = atpService.getAtp(origA.getId(), callContext);
            fail("Did not receive DoesNotExistException when attempting to get already-deleted AtpEntity");
        } catch (DoesNotExistException dnee) {
            // expected
        }

        // test milestone create
        MilestoneInfo origM = new MilestoneInfo();
        origM.setName("new Milestone");
        origM.setTypeKey(AtpServiceConstants.MILESTONE_ADVANCE_REGISTRATION_PERIOD_TYPE_KEY);
        origM.setStateKey(AtpServiceConstants.MILESTONE_DRAFT_STATE_KEY);
        origM.setStartDate(new Date());
        origM.setEndDate(new Date(new Date().getTime() + 1000));
        origM.setIsDateRange(Boolean.TRUE);
        origM.setIsAllDay(Boolean.FALSE);
        origM.setDescr(new RichTextHelper().fromPlain("test description"));
        attr = new AttributeInfo();
        attr.setKey("attribute.key");
        attr.setValue("attribute value");
        origM.getAttributes().add(attr);
        MilestoneInfo resultM = atpService.createMilestone(origM.getTypeKey(), origM, callContext);
        assertNotNull(resultM);
        assertNotNull(resultM.getId());
        assertEquals(origM.getTypeKey(), resultM.getTypeKey());
        assertEquals(origM.getStateKey(), resultM.getStateKey());
        assertEquals(origM.getName(), resultM.getName());
        assertEquals(origM.getDescr().getPlain(), resultM.getDescr().getPlain());
        assertEquals(origM.getStartDate(), resultM.getStartDate());
        assertEquals(origM.getEndDate(), resultM.getEndDate());
        assertEquals(origM.getAttributes().size(), resultM.getAttributes().size());
        assertNotNull(resultM.getAttributes().get(0).getId());
        assertEquals(origM.getAttributes().get(0).getKey(), resultM.getAttributes().get(0).getKey());
        assertEquals(origM.getAttributes().get(0).getValue(), resultM.getAttributes().get(0).getValue());
        assertNotNull(resultM.getMeta());
        assertNotNull(resultM.getMeta().getCreateId());
        assertNotNull(resultM.getMeta().getCreateTime());
        assertNotNull(resultM.getMeta().getUpdateId());
        assertNotNull(resultM.getMeta().getUpdateTime());
        assertNotNull(resultM.getMeta().getVersionInd());

        // test milestone read
        origM = resultM;
        resultM = atpService.getMilestone(resultM.getId(), callContext);
        assertNotNull(resultM);
        assertNotNull(resultM.getId());
        assertEquals(origM.getTypeKey(), resultM.getTypeKey());
        assertEquals(origM.getStateKey(), resultM.getStateKey());
        assertEquals(origM.getName(), resultM.getName());
        assertEquals(origM.getDescr().getPlain(), resultM.getDescr().getPlain());
        assertEquals(origM.getStartDate(), resultM.getStartDate());
        assertEquals(origM.getEndDate(), resultM.getEndDate());
        assertEquals(origM.getAttributes().size(), resultM.getAttributes().size());
        assertNotNull(resultM.getAttributes().get(0).getId());
        assertEquals(origM.getAttributes().get(0).getKey(), resultM.getAttributes().get(0).getKey());
        assertEquals(origM.getAttributes().get(0).getValue(), resultM.getAttributes().get(0).getValue());
        assertNotNull(resultM.getMeta());
        assertNotNull(resultM.getMeta().getCreateId());
        assertNotNull(resultM.getMeta().getCreateTime());
        assertNotNull(resultM.getMeta().getUpdateId());
        assertNotNull(resultM.getMeta().getUpdateTime());
        assertNotNull(resultM.getMeta().getVersionInd());

        // test milestone update
        origM = resultM;
        origM.setName("updated name");
        origM.setDescr(new RichTextHelper().fromPlain("test description"));
        origM.setEndDate(new Date(new Date().getTime() + 2000));
        resultM = atpService.updateMilestone(origM.getId(), origM, callContext);
        assertNotNull(resultM);
        assertNotNull(resultM.getId());
        assertEquals(origM.getTypeKey(), resultM.getTypeKey());
        assertEquals(origM.getStateKey(), resultM.getStateKey());
        assertEquals(origM.getName(), resultM.getName());
        assertEquals(origM.getDescr().getPlain(), resultM.getDescr().getPlain());
        assertEquals(origM.getStartDate(), resultM.getStartDate());
        assertEquals(origM.getEndDate(), resultM.getEndDate());
        assertEquals(origM.getAttributes().size(), resultM.getAttributes().size());
        assertNotNull(resultM.getAttributes().get(0).getId());
        assertEquals(origM.getAttributes().get(0).getKey(), resultM.getAttributes().get(0).getKey());
        assertEquals(origM.getAttributes().get(0).getValue(), resultM.getAttributes().get(0).getValue());
        assertNotNull(resultM.getMeta());
        assertNotNull(resultM.getMeta().getCreateId());
        assertNotNull(resultM.getMeta().getCreateTime());
        assertNotNull(resultM.getMeta().getUpdateId());
        assertNotNull(resultM.getMeta().getUpdateTime());
        assertNotSame(origM.getMeta().getVersionInd(), resultM.getMeta().getVersionInd());

        // test milestone delete
        status = atpService.deleteMilestone(origM.getId(), callContext);
        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        try {
            resultM = atpService.getMilestone(origM.getId(), callContext);
            fail("Did not receive DoesNotExistException when attempting to get already-deleted Milestone");
        } catch (DoesNotExistException dnee) {
            // expected
        }


        // test atp atp relation create
        AtpInfo atp1 = new AtpInfo();
        atp1.setName("new Atp1");
        atp1.setTypeKey(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY);
        atp1.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        atp1.setStartDate(new Date());
        atp1.setEndDate(new Date(new Date().getTime() + 1000));
        atp1.setDescr(new RichTextHelper().fromPlain("test description"));
        atp1 = atpService.createAtp(atp1.getTypeKey(), atp1, callContext);
        atp1 = atpService.getAtp(atp1.getId(), callContext);
        System.out.println ("atp1.id=" + atp1.getId());

        AtpInfo atp2 = new AtpInfo();
        atp2.setName("new Atp2");
        atp2.setTypeKey(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY);
        atp2.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        atp2.setStartDate(new Date());
        atp2.setEndDate(new Date(new Date().getTime() + 1000));
        atp2.setDescr(new RichTextHelper().fromPlain("test description"));
        atp2 = atpService.createAtp(atp2.getTypeKey(), atp2, callContext);
        atp2 = atpService.getAtp(atp2.getId(), callContext);
        System.out.println ("atp2.id=" + atp2.getId());

        AtpAtpRelationInfo origR = new AtpAtpRelationInfo();
        origR.setAtpId(atp1.getId());
        origR.setRelatedAtpId(atp2.getId());
        origR.setTypeKey(AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY);
        origR.setStateKey(AtpServiceConstants.ATP_ATP_RELATION_ACTIVE_STATE_KEY);
        origR.setEffectiveDate(new Date());
        origR.setExpirationDate(new Date(new Date().getTime() + 1000));
        attr = new AttributeInfo();
        attr.setKey("attribute.key");
        attr.setValue("attribute value");
        origR.getAttributes().add(attr);
        AtpAtpRelationInfo resultR = atpService.createAtpAtpRelation(origR.getAtpId(),
                origR.getRelatedAtpId(),
                origR.getTypeKey(),
                origR, callContext);
        assertNotNull(resultR);
        assertNotNull(resultR.getId());
        assertEquals(origR.getAtpId(), resultR.getAtpId());
        assertEquals(origR.getRelatedAtpId(), resultR.getRelatedAtpId());
        assertEquals(origR.getTypeKey(), resultR.getTypeKey());
        assertEquals(origR.getStateKey(), resultR.getStateKey());
        assertEquals(origR.getEffectiveDate(), resultR.getEffectiveDate());
        assertEquals(origR.getExpirationDate(), resultR.getExpirationDate());
        assertEquals(origR.getAttributes().size(), resultR.getAttributes().size());
        assertNotNull(resultR.getAttributes().get(0).getId());
        assertEquals(origR.getAttributes().get(0).getKey(), resultR.getAttributes().get(0).getKey());
        assertEquals(origR.getAttributes().get(0).getValue(), resultR.getAttributes().get(0).getValue());
        assertNotNull(resultR.getMeta());
        assertNotNull(resultR.getMeta().getCreateId());
        assertNotNull(resultR.getMeta().getCreateTime());
        assertNotNull(resultR.getMeta().getUpdateId());
        assertNotNull(resultR.getMeta().getUpdateTime());
        assertNotNull(resultR.getMeta().getVersionInd());

        // test atp atp relation read
        origR = resultR;
        resultR = atpService.getAtpAtpRelation(resultR.getId(), callContext);
        assertNotNull(resultR);
        assertNotNull(resultR.getId());
        assertEquals(origR.getAtpId(), resultR.getAtpId());
        assertEquals(origR.getRelatedAtpId(), resultR.getRelatedAtpId());
        assertEquals(origR.getTypeKey(), resultR.getTypeKey());
        assertEquals(origR.getStateKey(), resultR.getStateKey());
        assertEquals(origR.getEffectiveDate(), resultR.getEffectiveDate());
        assertEquals(origR.getExpirationDate(), resultR.getExpirationDate());
        assertEquals(origR.getAttributes().size(), resultR.getAttributes().size());
        assertNotNull(resultR.getAttributes().get(0).getId());
        assertEquals(origR.getAttributes().get(0).getKey(), resultR.getAttributes().get(0).getKey());
        assertEquals(origR.getAttributes().get(0).getValue(), resultR.getAttributes().get(0).getValue());
        assertNotNull(resultR.getMeta());
        assertNotNull(resultR.getMeta().getCreateId());
        assertNotNull(resultR.getMeta().getCreateTime());
        assertNotNull(resultR.getMeta().getUpdateId());
        assertNotNull(resultR.getMeta().getUpdateTime());
        assertNotNull(resultR.getMeta().getVersionInd());

        // test atp atp rel update
        origR = resultR;
        origR.setExpirationDate(new Date(new Date().getTime() + 2000));
        resultR = atpService.updateAtpAtpRelation(origR.getId(), origR, callContext);
        assertNotNull(resultR);
        assertNotNull(resultR.getId());
        assertEquals(origR.getAtpId(), resultR.getAtpId());
        assertEquals(origR.getRelatedAtpId(), resultR.getRelatedAtpId());
        assertEquals(origR.getTypeKey(), resultR.getTypeKey());
        assertEquals(origR.getStateKey(), resultR.getStateKey());
        assertEquals(origR.getEffectiveDate(), resultR.getEffectiveDate());
        assertEquals(origR.getExpirationDate(), resultR.getExpirationDate());
        assertEquals(origR.getAttributes().size(), resultR.getAttributes().size());
        assertNotNull(resultR.getAttributes().get(0).getId());
        assertEquals(origR.getAttributes().get(0).getKey(), resultR.getAttributes().get(0).getKey());
        assertEquals(origR.getAttributes().get(0).getValue(), resultR.getAttributes().get(0).getValue());
        assertNotNull(resultR.getMeta());
        assertNotNull(resultR.getMeta().getCreateId());
        assertNotNull(resultR.getMeta().getCreateTime());
        assertNotNull(resultR.getMeta().getUpdateId());
        assertNotNull(resultR.getMeta().getUpdateTime());
        assertNotSame(origR.getMeta().getVersionInd(), resultR.getMeta().getVersionInd());

        // test atp delete
        status = atpService.deleteAtpAtpRelation(origR.getId(), callContext);
        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        try {
            resultR = atpService.getAtpAtpRelation(origR.getId(), callContext);
            fail("Did not receive DoesNotExistException when attempting to get already-deleted AtpAtpRelation");
        } catch (DoesNotExistException dnee) {
            // expected
        }

        // adding and removing milestones from types

        MilestoneInfo m1 = new MilestoneInfo();
        m1.setName("new Milestone1");
        m1.setTypeKey(AtpServiceConstants.MILESTONE_ADVANCE_REGISTRATION_PERIOD_TYPE_KEY);
        m1.setStateKey(AtpServiceConstants.MILESTONE_DRAFT_STATE_KEY);
        m1.setStartDate(new Date());
        m1.setEndDate(new Date(new Date().getTime() + 1000));
        m1.setIsDateRange(Boolean.TRUE);
        m1.setIsAllDay(Boolean.FALSE);
        m1.setDescr(new RichTextHelper().fromPlain("test description1"));
        m1 = atpService.createMilestone(m1.getTypeKey(), m1, callContext);
        m1 = atpService.getMilestone(m1.getId(), callContext);
        System.out.println ("m1.id=" + m1.getId());

        MilestoneInfo m2 = new MilestoneInfo();
        m2.setName("new Milestone2");
        m2.setTypeKey(AtpServiceConstants.MILESTONE_ADVANCE_REGISTRATION_PERIOD_TYPE_KEY);
        m2.setStateKey(AtpServiceConstants.MILESTONE_DRAFT_STATE_KEY);
        m2.setStartDate(new Date());
        m2.setEndDate(new Date(new Date().getTime() + 1000));
        m2.setIsDateRange(Boolean.TRUE);
        m2.setIsAllDay(Boolean.FALSE);
        m2.setDescr(new RichTextHelper().fromPlain("test description2"));
        m2 = atpService.createMilestone(m2.getTypeKey(), m2, callContext);
        m2 = atpService.getMilestone(m2.getId(), callContext);
        System.out.println ("m2.id=" + m2.getId());

        status = atpService.addMilestoneToAtp(m1.getId(), atp1.getId(), callContext);
        assertNotNull(status);
        assertTrue(status.getIsSuccess());

        try {
            status = atpService.addMilestoneToAtp(m1.getId(), atp1.getId(), callContext);
            fail("relationship should already exist");
        } catch (AlreadyExistsException ex) {
            // expected
        }

        List<MilestoneInfo> milestones = atpService.getMilestonesForAtp(atp1.getId(), callContext);
        assertNotNull(milestones);
        assertEquals(1, milestones.size());
        assertEquals(m1.getId(), milestones.get(0).getId());

        status = atpService.addMilestoneToAtp(m2.getId(), atp1.getId(), callContext);
        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        try {
            status = atpService.addMilestoneToAtp(m2.getId(), atp1.getId(), callContext);
            fail("relationship should already exist");
        } catch (AlreadyExistsException ex) {
            // expected
        }
        milestones = atpService.getMilestonesForAtp(atp1.getId(), callContext);
        assertNotNull(milestones);
        assertEquals(2, milestones.size());
        MilestoneInfo found1 = null;
        MilestoneInfo found2 = null;
        for (MilestoneInfo info : milestones) {
            if (info.getId().equals(m1.getId())) {
                found1 = info;
            } else if (info.getId().equals(m2.getId())) {
                found2 = info;
            } else {
                fail("unknow milestone found " + info.getId());
            }
        }
        assertNotNull (found1);
        assertNotNull (found2);
    }
}
