package org.kuali.student.poc.learningunit.luipersonrelation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.kuali.student.poc.common.test.spring.AbstractServiceTest;
import org.kuali.student.poc.common.test.spring.Client;
import org.kuali.student.poc.common.test.spring.Dao;
import org.kuali.student.poc.common.test.spring.Daos;
import org.kuali.student.poc.common.test.spring.PersistenceFileLocation;
import org.kuali.student.poc.common.ws.exceptions.DoesNotExistException;
import org.kuali.student.poc.wsdl.learningunit.luipersonrelation.LuiPersonRelationService;
import org.kuali.student.poc.xsd.learningunit.lu.dto.Status;
import org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.LuiPersonRelationCreateInfo;
import org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.LuiPersonRelationDisplay;
import org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.LuiPersonRelationInfo;
import org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.LuiPersonRelationTypeInfo;
import org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.RelationStateInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonDisplay;

@Daos({@Dao(value="org.kuali.student.poc.learningunit.luipersonrelation.dao.impl.LuiPersonRelationDAOImpl", testDataFile = "classpath:test-beans.xml")})
@PersistenceFileLocation("classpath:META-INF/luipersonrelation-persistence.xml")   
public class TestLuiPersonRelationServiceImpl extends AbstractServiceTest{

    @Client(value="org.kuali.student.poc.learningunit.luipersonrelation.service.LuiPersonRelationServiceImpl", port="9191")
    public LuiPersonRelationService client;
       
    public static final String person_id1 = "Person 1";
    public static final String person_id2 = "Person 2";
    public static final String person_id3 = "Person 3";
    public static final String lui_id1 = "Lui 1";
    public static final String lui_id2 = "Lui 2";
    public static final String lpr_id1 = "11223344-1122-1122-1111-000000000001";
    public static final String lpr_id2 = "11223344-1122-1122-1111-000000000002";
    public static final String lpr_id3 = "11223344-1122-1122-1111-000000000003";
    public static final String lpr_id4 = "11223344-1122-1122-1111-000000000004";
    public static final String lpr_id5 = "11223344-1122-1122-1111-000000000005";

    public LuiPersonRelationTypeInfo lprTypeInfo;
    public RelationStateInfo relationStateInfo;
    
    @Before
    public void setup(){
        lprTypeInfo = new LuiPersonRelationTypeInfo();
        lprTypeInfo.setName("student");
        
        relationStateInfo = new RelationStateInfo();
        relationStateInfo.setState("add");        
    }
    
    
    @Test
    public void testIsValidLuiPersonRelation() throws Exception{
        
        assertTrue(client.isValidLuiPersonRelation(person_id3, lui_id2, lprTypeInfo, relationStateInfo));
       
        assertFalse(client.isValidLuiPersonRelation(person_id3, lui_id1, lprTypeInfo, relationStateInfo));     
    }


    @Test (expected=DoesNotExistException.class)
    public void testCreateDeleteUpdateLuiPersonRelation() throws Exception{
       
        LuiPersonRelationCreateInfo lprCreateInfo = new LuiPersonRelationCreateInfo();
        lprCreateInfo.setEffectiveStartDate(new Date());
        lprCreateInfo.setEffectiveEndDate(new Date());        
        
        //Create relation
        String lpr_id = client.createLuiPersonRelation(person_id3, lui_id2, relationStateInfo, lprTypeInfo, lprCreateInfo);
        
        LuiPersonRelationInfo lprInfo = client.fetchLUIPersonRelation(lpr_id);
        assertEquals(lpr_id, lprInfo.getLuiPersonRelationId()); 
        
        //TODO: Test update function
        
        //Remove relation
        Status status = client.deleteLuiPersonRelation(lprInfo.getLuiPersonRelationId());
        assertTrue(status.isSuccess());
        
        //should throw dne exception
        client.fetchLUIPersonRelation(lprInfo.getLuiPersonRelationId());
    }
    
    
    @Test
    public void testCreateBulkRelationships(){
        //TODO: Add this test
    }
    
    @Test
    public void testFindValidPeopleForLui() throws Exception{
          List<String> personIds = client.findAllValidPersonIdsForLui(lui_id1, lprTypeInfo, relationStateInfo);
        assertTrue(personIds.size() == 3);
        assertTrue(personIds.contains(person_id1));
        assertTrue(personIds.contains(person_id2));
        assertTrue(personIds.contains(person_id3));
        

        personIds = client.findAllValidPersonIdsForLui(lui_id2, lprTypeInfo, relationStateInfo);
        assertTrue(personIds.size() == 2);
        assertTrue(personIds.contains(person_id1));
        assertTrue(personIds.contains(person_id2));       
        
        
        //TODO: Fix since this is a bad test, mock person service can't return "valid" list of people
        List<PersonDisplay> personDisp = client.findAllValidPeopleForLui(lui_id1, lprTypeInfo, relationStateInfo);
        assertNotNull(personDisp);
    }
    
    @Test 
    public void testFindValidLuisForPerson() throws Exception{
        List<String> luiIds = client.findLuiIdsRelatedToPerson(person_id1, lprTypeInfo, relationStateInfo);
        assertTrue(luiIds.size()== 2);
        assertTrue(luiIds.contains(lui_id1));
        assertTrue(luiIds.contains(lui_id2));
    }

    @Test
    public void testFindLuiPersonRelationDisplay() throws Exception{
        List<LuiPersonRelationDisplay> lprDispList;
        List<String> idList;
        
        lprDispList = client.findLuiPersonRelations(person_id2, lui_id2);
        assertTrue(lprDispList.size() == 1);
        assertEquals(lpr_id5, lprDispList.get(0).getLuiPersonRelationId());
        
        lprDispList = client.findLuiPersonRelationsForLui(lui_id2);        
        assertTrue(lprDispList.size() == 2);                 
        idList = new ArrayList<String>();
        idList.add(lprDispList.get(0).getLuiPersonRelationId());
        idList.add(lprDispList.get(1).getLuiPersonRelationId());
        assertTrue(idList.contains(lpr_id2));
        assertTrue(idList.contains(lpr_id5));
        LuiPersonRelationDisplay lprDisp = lprDispList.get(0);
        assertEquals(lui_id2, lprDisp.getLuiDisplay().getLuiId());

        lprDispList = client.findLuiPersonRelationsForPerson(person_id3);
        assertTrue(lprDispList.size() == 1);
        assertEquals(lpr_id4, lprDispList.get(0).getLuiPersonRelationId());
        
    }

    @Test
    public void testFindLuiPersonRelationids() throws Exception{
        List<String> idList;
        
        idList = client.findLuiPersonRelationIds(person_id1, lui_id1);
        assertTrue(idList.size() == 1);
        assertTrue(idList.contains(lpr_id1));        
       
        idList = client.findLuiPersonRelationIdsForLui(lui_id1);
        assertTrue(idList.size() == 3);
        assertTrue(idList.contains(lpr_id1));
        assertTrue(idList.contains(lpr_id3));
        assertTrue(idList.contains(lpr_id4));
        
        idList = client.findLuiPersonRelationIdsForPerson(person_id3);
        assertTrue(idList.size() == 1);
        assertTrue(idList.contains(lpr_id4));
        
        idList = client.findPersonIdsRelatedToLui(lui_id2, lprTypeInfo, relationStateInfo);
        assertTrue(idList.size() == 2);
        assertTrue(idList.contains(person_id1));
        assertTrue(idList.contains(person_id2));
        
        //Sneak in test for isRelated operation
        assertTrue(client.isRelated(person_id1, lui_id1, lprTypeInfo, relationStateInfo));
        
        RelationStateInfo relationStateInfo2 = new RelationStateInfo();
        relationStateInfo2.setState("drop");        
        assertFalse(client.isRelated(person_id1, lui_id1, lprTypeInfo, relationStateInfo2));
    }

    @Test
    public void testRelationStates() throws Exception{
        assertTrue(client.findRelationStates().size() == 2);

        //TODO: Add test for ordered relation state
        //TODO: Add test for valid relation states
    }
    
    @Test
    public void testLuiPersonRelationSearch(){
        //TODO: Add test for search
    }
    

    @Test 
    public void testValidateLuiPersonRelation(){
        //TODO: Add test for validation
    }
}
