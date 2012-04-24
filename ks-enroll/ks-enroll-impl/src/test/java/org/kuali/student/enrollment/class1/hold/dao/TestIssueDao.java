package org.kuali.student.enrollment.class1.hold.dao;

import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.enrollment.class1.hold.model.HoldRichTextEntity;
import org.kuali.student.enrollment.class1.hold.model.HoldIssueEntity;
import org.kuali.student.r2.common.util.constants.HoldServiceConstants;

import java.util.List;

import static org.junit.Assert.*;

@PersistenceFileLocation("classpath:META-INF/hold-persistence.xml")

public class TestIssueDao extends AbstractTransactionalDaoTest {
    @Dao(value = "org.kuali.student.enrollment.class1.hold.dao.HoldIssueDao", testSqlFile = "classpath:ks-hold.sql")
    private HoldIssueDao dao;

    @Test
    public void testGetIssue() {
        HoldIssueEntity obj = dao.find("Hold-Issue-1");
        assertNotNull(obj);
        assertEquals("Issue one", obj.getName());
        assertEquals(HoldServiceConstants.ISSUE_ACTIVE_STATE_KEY, obj.getHoldIssueState());
        assertEquals(HoldServiceConstants.RESIDENCY_ISSUE_TYPE_KEY, obj.getHoldIssueType());
        assertEquals("Issue-1-Desc", obj.getDescrPlain());
    }

    @Test
    public void testCreateIssue() {
        HoldIssueEntity existingEntity = dao.find("Hold-Issue-1");

        HoldIssueEntity obj = new HoldIssueEntity();
        obj.setName("Issue Test");
        obj.setDescrPlain("plain");
        obj.setDescrFormatted("formatted");
        obj.setHoldIssueState(existingEntity.getHoldIssueState());
        obj.setHoldIssueType(existingEntity.getHoldIssueType());
        dao.persist(obj);
        assertNotNull(obj.getId());
        HoldIssueEntity obj2 = dao.find(obj.getId());
        assertEquals("Issue Test", obj2.getName());
        assertEquals("plain", obj2.getDescrPlain());
    }

    @Test
    public void testUpdateIssue() {
        HoldIssueEntity existingEntity = dao.find("Hold-Issue-1");

        existingEntity.setName("Issue Updated");
        existingEntity.setDescrPlain("plain");
        existingEntity.setDescrFormatted("formatted");
        dao.merge(existingEntity);

        HoldIssueEntity obj2 = dao.find(existingEntity.getId());
        assertEquals("Issue Updated", obj2.getName());
        assertEquals("plain", obj2.getDescrPlain());
    }

    @Test
    public void testDeleteIssue() {
        HoldIssueEntity obj = dao.find("Hold-Issue-2");
        assertNotNull(obj);
        dao.remove(obj);
        HoldIssueEntity old = dao.find("Hold-Issue-2");
        assertNull(old);
    }
    
    @Test
    public void testGetByOrgId() {
        List<HoldIssueEntity> result = dao.getByOrganizationId("102");
        assertNotNull(result);
        assertEquals(2, result.size());
        
        List<HoldIssueEntity> emptyResults = dao.getByOrganizationId("3");
        assertTrue(emptyResults == null || emptyResults.isEmpty());
    }
}
