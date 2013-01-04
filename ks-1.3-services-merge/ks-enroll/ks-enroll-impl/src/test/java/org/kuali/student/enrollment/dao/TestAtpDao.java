package org.kuali.student.enrollment.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
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
import org.kuali.student.r2.core.atp.dto.AtpAtpRelationInfo;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.class1.atp.dao.AtpDao;
import org.kuali.student.r2.core.class1.atp.model.AtpAttributeEntity;
import org.kuali.student.r2.core.class1.atp.model.AtpEntity;
import org.kuali.student.r2.core.class1.atp.service.impl.AtpTestDataLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:atp-test-context.xml"})
@Transactional
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
public class TestAtpDao {

    @Resource(name = "atpServiceAuthDecorator")
    public AtpService atpService;
    public static String principalId = "123";
    public ContextInfo callContext = null;
    @Resource(name = "atpDao")
    public AtpDao dao;

    @Before
    public void setUp() {
        callContext = new ContextInfo();
        callContext.setPrincipalId(principalId);
        try {
            loadData();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private void loadData() throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException,
            DataValidationErrorException, ReadOnlyException, VersionMismatchException, AlreadyExistsException {
        AtpTestDataLoader loader = new AtpTestDataLoader (this.atpService);
        loader.loadData();
    }


    @Test
    public void testGetAtp() {
        AtpEntity atp = dao.find("testAtpId1");
        assertNotNull(atp);
        assertEquals("testAtp1", atp.getName());
        assertEquals("Desc 101", atp.getDescrPlain());
    }

    @Test
    public void testCreateAtp()
    {
        AtpEntity existingEntity = dao.find("testAtpId1");

        AtpEntity atp = new AtpEntity();
        atp.setName("atpTest");
        atp.setDescrPlain("plain");
        atp.setDescrFormatted("formatted");
        atp.setAtpState(existingEntity.getAtpState());
        atp.setAtpType(existingEntity.getAtpType());
        atp.setEndDate(existingEntity.getEndDate());
        atp.setStartDate(existingEntity.getStartDate());
        atp.setCreateId(principalId);
        atp.setCreateTime(new Date());
        AtpAttributeEntity attr = new AtpAttributeEntity();
        attr.setKey("CredentialProgramType");
        attr.setValue("kuali.lu.type.credential.Baccalaureate");
        attr.setOwner(atp);
        atp.getAttributes().add(attr);
        dao.persist(atp);
        assertNotNull(atp.getId());

        AtpEntity atp2 = dao.find(atp.getId());
        assertEquals("atpTest", atp2.getName());
        assertEquals("plain", atp2.getDescrPlain());
        assertEquals(1, atp2.getAttributes().size());
        assertEquals("kuali.lu.type.credential.Baccalaureate", atp2.getAttributes().iterator().next().getValue());
    }

    @Test
    public void testDeleteAtp()
    {
        AtpEntity atp = dao.find("testAtpId2");
        assertNotNull(atp);
        dao.remove(atp);
        atp = dao.find("testAtpId2");
        assertNull(atp);
    }

    @Test
    public void testUpdateAtp()
    {
        AtpEntity atp = dao.find("testAtpId2");
        assertNotNull(atp);
        AtpAttributeEntity attr = new AtpAttributeEntity(new AttributeInfo("foo", "bar"), atp);
        atp.getAttributes().add(attr);
        dao.update(atp);

        AtpEntity atp2 = dao.find("testAtpId2");
        assertNotNull(atp2);
        assertEquals(1, atp2.getAttributes().size());
        assertEquals("bar", atp2.getAttributes().iterator().next().getValue());
    }
}
