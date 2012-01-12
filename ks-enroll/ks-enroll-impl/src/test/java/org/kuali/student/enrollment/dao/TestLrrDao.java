package org.kuali.student.enrollment.dao;

import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.enrollment.class1.lrr.dao.LrrDao;
import org.kuali.student.enrollment.class1.lrr.model.LearningResultRecordEntity;
import org.kuali.student.enrollment.class1.lrr.model.LrrAttributeEntity;
import org.kuali.student.enrollment.class1.lrr.model.LrrRichTextEntity;
import org.kuali.student.enrollment.class1.lrr.model.LrrTypeEntity;
import org.kuali.student.r2.core.class1.state.model.StateEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@PersistenceFileLocation("classpath:META-INF/lrr-persistence.xml")
public class TestLrrDao extends AbstractTransactionalDaoTest {

    @Dao(value = "org.kuali.student.enrollment.class1.lrr.dao.LrrDao", testSqlFile = "classpath:ks-lrr.sql")
    private LrrDao dao;

    @Test
    public void testCrud() {

        // Read
        LearningResultRecordEntity lrr = dao.find("student1-grade-final-lecture");
        assertNotNull(lrr);
        assertNotNull(lrr.getResultValueId());
        assertNotNull(lrr.getName());
        assertNotNull(lrr.getDescr());
        assertNotNull(lrr.getLprId());
        assertNotNull(lrr.getLrrState());
        assertNotNull(lrr.getLrrType());
        assertNotNull(lrr.getId());

        // Create
        StateEntity lrrState = lrr.getLrrState();
        LrrTypeEntity lrrType = lrr.getLrrType();
        lrr = new LearningResultRecordEntity();

        String id = "new-lrr";
        String name = "New Lrr";
        String lprId = "lprId-1";
        LrrAttributeEntity attribute = new LrrAttributeEntity("foo", "bar");
        attribute.setOwner(lrr);
        List<LrrAttributeEntity> attributes = new ArrayList<LrrAttributeEntity>();
        attributes.add(attribute);
        LrrRichTextEntity descr = new LrrRichTextEntity("plain", "formatted");
        String resultValueId = "resultValueId";

        lrr.setId(id);
        lrr.setName(name);
        lrr.setLprId(lprId);
        lrr.setAttributes(attributes);
        lrr.setDescr(descr);
        lrr.setLrrState(lrrState);
        lrr.setLrrType(lrrType);
        lrr.setResultValueId(resultValueId);

        dao.persist(lrr);

        lrr = dao.find(id);
        assertNotNull("LRR is null after create.", lrr);
        assertEquals("ID does not match after create.", id, lrr.getId());
        assertEquals("Name does not match after create.", name, lrr.getName());
        assertEquals("LPR ID does not match after create.", lprId, lrr.getLprId());
        assertEquals("Attributes does not match after create.", attributes, lrr.getAttributes());
        assertEquals("Descr does not match after create.", descr, lrr.getDescr());
        assertEquals("LRR State does not match after create.", lrrState, lrr.getLrrState());
        assertEquals("LRR Type does not match after create.", lrrType, lrr.getLrrType());
        assertEquals("Result Value Key does not match after create.", resultValueId, lrr.getResultValueId());
        
        // Update
        lrr.setName("Updated LRR");
        dao.persist(lrr);
        lrr = dao.find(id);
        assertEquals("Name does not match after update.", "Updated LRR", lrr.getName());
        
        // Delete
        dao.remove(lrr);
        lrr = dao.find(id);
        assertNull(lrr);
    }

    @Test
    public void testGetLearningResultRecordsForLprIdList() {
        List<String> lprIds = new ArrayList<String>();
        lprIds.add("student1");
        lprIds.add("student2");

        List<LearningResultRecordEntity> expected = new ArrayList<LearningResultRecordEntity>();
        expected.add(dao.find("student1-grade-final-lecture"));
        expected.add(dao.find("student2-grade-final-lecture"));
        expected.add(dao.find("student1-grade-interim-lecture"));
        expected.add(dao.find("student2-grade-interim-lecture"));

        List<LearningResultRecordEntity> lrrs = dao.getLearningResultRecordsForLprIdList(lprIds);
        assertNotNull("Null list returned.", lrrs);
        assertEquals("Number of results not as expected.", expected.size(), lrrs.size());
        assertTrue("Expected LRRs not returned.", lrrs.containsAll(expected));
        assertTrue("Returned LRRs not expected.", expected.containsAll(lrrs));

    }


}
