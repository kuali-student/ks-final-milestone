package org.kuali.student.poc.learningunit.luipersonrelation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.kuali.student.poc.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.poc.common.test.spring.Dao;
import org.kuali.student.poc.common.test.spring.PersistenceFileLocation;
import org.kuali.student.poc.learningunit.luipersonrelation.dao.LuiPersonRelationDAO;
import org.kuali.student.poc.learningunit.luipersonrelation.entity.LuiPersonRelation;

@PersistenceFileLocation("classpath:META-INF/luipersonrelation-persistence.xml")
public class TestLuiPersonRelationDAOImpl extends AbstractTransactionalDaoTest {
    @Dao(value = "org.kuali.student.poc.learningunit.luipersonrelation.dao.impl.LuiPersonRelationDAOImpl", testDataFile = "classpath:test-beans.xml")
    public LuiPersonRelationDAO dao;

    public static final String person_id1 = "Person 1";
    public static final String person_id2 = "Person 2";
    public static final String lui_id1 = "Lui 1";
    public static final String lui_id2 = "Lui 2";
    public static final String lpr_id1 = "11223344-1122-1122-1111-000000000001";
    public static final String lpr_id2 = "11223344-1122-1122-1111-000000000002";
    public static final String lpr_id3 = "11223344-1122-1122-1111-000000000003";
    public static final String lpr_id4 = "11223344-1122-1122-1111-000000000004";

    
    @Test
    public void testCreateLuiPersonRelation() {
        LuiPersonRelation lpr = new LuiPersonRelation();
        lpr.setEffectiveEndDate(new Date());
        lpr.setEffectiveStartDate(new Date());
        lpr.setLuiId("anything1");
        lpr.setLuiPersonRelationType("anything3");
        lpr.setPersonId("anything2");
        lpr.setRelationState("anything4");

        dao.createLuiPersonRelation(lpr);
        
        assertEquals(dao.lookupLuiPersonRelation(lpr.getId()), lpr);      
    }
    
    @Test
    public void testDeletePersonRelation(){
        assertNotNull(dao.lookupLuiPersonRelation(lpr_id1));
        dao.deleteLuiPersonRelation(lpr_id1);
        assertNull(dao.lookupLuiPersonRelation(lpr_id1));        
    }
    
    @Test
    public void testFindLuiPersonRelations(){
       List<String> idList = new ArrayList<String>();
       List<LuiPersonRelation> lprList;
       
       lprList = dao.findLuiPersonRelations(person_id1, lui_id1, "student", "add");       
       assertEquals(lpr_id1, ((LuiPersonRelation)lprList.get(0)).getId());
       
       lprList = dao.findLuiPersonRelationsByLui(lui_id2, "student", "add");
       assertTrue(lprList.size()==2);
       idList.add(((LuiPersonRelation)lprList.get(0)).getPersonId());
       idList.add(((LuiPersonRelation)lprList.get(1)).getPersonId());
       assertTrue(idList.contains(person_id1));
       assertTrue(idList.contains(person_id2));
       
       lprList = dao.findLuiPersonRelationsByPerson(person_id1, "student", "add");
       assertTrue(lprList.size()==2);
       idList.add(((LuiPersonRelation)lprList.get(0)).getLuiId());
       idList.add(((LuiPersonRelation)lprList.get(1)).getLuiId());
       assertTrue(idList.contains(lui_id1));
       assertTrue(idList.contains(lui_id2));
    }
    
    @Test
    public void testUpdateLuiPersonRelation() {
        LuiPersonRelation lpr = new LuiPersonRelation();
        lpr.setEffectiveEndDate(new Date());
        lpr.setEffectiveStartDate(new Date());
        lpr.setLuiId("whatever1");
        lpr.setPersonId("whatever2");
        lpr.setLuiPersonRelationType("whatever3");
        lpr.setRelationState("whatever4");
        
        dao.createLuiPersonRelation(lpr);
        LuiPersonRelation lpr2 = dao.lookupLuiPersonRelation(lpr.getId());
        
        assertEquals(lpr,lpr2);
        
        lpr2.setRelationState("why not?");
        dao.updateLuiPersonRelation(lpr2);
        lpr = dao.lookupLuiPersonRelation(lpr2.getId());
        
        assertEquals(lpr2.getRelationState(),lpr.getRelationState());
        
    }
    
    @Test
    public void testSearchLuiPersonRelation() {
        LuiPersonRelation lpr = new LuiPersonRelation();
        Date toSearch = new Date();
        
        lpr.setEffectiveEndDate(toSearch);
        lpr.setEffectiveStartDate(toSearch);
        lpr.setLuiId("whoever1");
        lpr.setPersonId("whoever2");
        lpr.setLuiPersonRelationType("whoever3");
        lpr.setRelationState("whoever4");
        
        dao.createLuiPersonRelation(lpr);
        
        List<String> searchIds = dao.searchForLuiPersonRelationIds(null, null, null, null, toSearch, null);
        assertEquals(1, searchIds.size());
        List<LuiPersonRelation> searchLuis = dao.searchForLuiPersonRelations(null, null, null, null, null, toSearch);
        assertEquals(1, searchLuis.size());
        assertEquals(searchIds.get(0), searchLuis.get(0).getId());
        
        searchIds = dao.searchForLuiPersonRelationIds("whoever1", null, "whoever3", null, toSearch, null);
        assertEquals(searchIds.get(0), searchLuis.get(0).getId());
        searchLuis = dao.searchForLuiPersonRelations(null, "whoever2", null, "whoever4", null, toSearch);
        assertEquals(searchIds.get(0), searchLuis.get(0).getId());
    }
}
