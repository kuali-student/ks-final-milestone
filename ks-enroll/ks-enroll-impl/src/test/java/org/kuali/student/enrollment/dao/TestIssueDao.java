package org.kuali.student.enrollment.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.enrollment.class1.hold.dao.IssueDao;
import org.kuali.student.enrollment.class1.hold.model.HoldRichTextEntity;
import org.kuali.student.enrollment.class1.hold.model.IssueEntity;
import org.kuali.student.r2.common.util.constants.HoldServiceConstants;

@PersistenceFileLocation("classpath:META-INF/persistence_jta.xml")
public class TestIssueDao extends AbstractTransactionalDaoTest {
    @Dao(value = "org.kuali.student.enrollment.class1.hold.dao.IssueDao", testSqlFile = "classpath:ks-hold.sql")
    private IssueDao dao;

    @Test
    public void testGetIssue() {
        IssueEntity obj = dao.find("Hold-Issue-1");
        assertNotNull(obj);
        assertEquals("Issue one", obj.getName());
        assertEquals(HoldServiceConstants.ISSUE_ACIVE_STATE_KEY, obj.getIssueState().getId());
        assertEquals(HoldServiceConstants.RESIDENCY_ISSUE_TYPE_KEY, obj.getTypeId());
        assertEquals("Issue Desc 101", obj.getDescr().getPlain());
    }

    @Test
    public void testCreateIssue() {
        IssueEntity existingEntity = dao.find("Hold-Issue-1");

        IssueEntity obj = new IssueEntity();
        obj.setName("Issue Test");
        obj.setDescr(new HoldRichTextEntity("plain", "formatted"));
        obj.setIssueState(existingEntity.getIssueState());
        obj.setTypeId(existingEntity.getTypeId());
        dao.persist(obj);
        assertNotNull(obj.getId());
        IssueEntity obj2 = dao.find(obj.getId());
        assertEquals("Issue Test", obj2.getName());
        assertEquals("plain", obj2.getDescr().getPlain());
    }

    @Test
    public void testUpdateIssue() {
        IssueEntity existingEntity = dao.find("Hold-Issue-1");

        existingEntity.setName("Issue Updated");
        existingEntity.setDescr(new HoldRichTextEntity("plain", "formatted"));
        dao.merge(existingEntity);

        IssueEntity obj2 = dao.find(existingEntity.getId());
        assertEquals("Issue Updated", obj2.getName());
        assertEquals("plain", obj2.getDescr().getPlain());
    }

    @Test
    public void testDeleteIssue() {
        IssueEntity obj = dao.find("Hold-Issue-2");
        assertNotNull(obj);
        dao.remove(obj);
        IssueEntity old = dao.find("Hold-Issue-2");
        assertNull(old);
    }
    
    @Test
    public void testGetByOrgId() {
        List<IssueEntity> result = dao.getByOrganizationId("1");
        assertNotNull(result);
        assertEquals(1, result.size());
        
        List<IssueEntity> emptyResults = dao.getByOrganizationId("3");
        assertTrue(emptyResults == null || emptyResults.isEmpty());
    }

}
