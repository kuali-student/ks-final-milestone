package edu.umd.ks.poc.lum.lu.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.kuali.student.poc.common.test.spring.AbstractServiceTest;
import org.kuali.student.poc.common.test.spring.Client;
import org.kuali.student.poc.common.test.spring.Dao;
import org.kuali.student.poc.common.test.spring.Daos;
import org.kuali.student.poc.common.test.spring.PersistenceFileLocation;
import org.kuali.student.poc.common.ws.exceptions.AlreadyExistsException;
import org.kuali.student.poc.common.ws.exceptions.CircularReferenceException;
import org.kuali.student.poc.common.ws.exceptions.DoesNotExistException;
import org.kuali.student.poc.common.ws.exceptions.InvalidParameterException;
import org.kuali.student.poc.common.ws.exceptions.MissingParameterException;
import org.kuali.student.poc.common.ws.exceptions.OperationFailedException;
import org.kuali.student.poc.common.ws.exceptions.PermissionDeniedException;

import edu.umd.ks.poc.lum.lu.dto.CluCreateInfo;
import edu.umd.ks.poc.lum.lu.dto.CluInfo;
import edu.umd.ks.poc.lum.lu.dto.CluUpdateInfo;
import edu.umd.ks.poc.lum.lu.dto.LuAttributeTypeInfo;
import edu.umd.ks.poc.lum.lu.dto.LuTypeInfo;
import edu.umd.ks.poc.lum.lu.dto.LuiCreateInfo;
import edu.umd.ks.poc.lum.lu.dto.LuiCriteria;
import edu.umd.ks.poc.lum.lu.dto.LuiDisplay;
import edu.umd.ks.poc.lum.lu.dto.LuiInfo;
import edu.umd.ks.poc.lum.lu.service.LuService;

@Daos( { @Dao(value = "edu.umd.ks.poc.lum.lu.dao.impl.LuDaoImpl", testDataFile = "classpath:test-beans.xml") })
@PersistenceFileLocation("classpath:META-INF/lu-persistence.xml")
public class TestLuServiceImpl extends AbstractServiceTest {
	@Client(value = "edu.umd.ks.poc.lum.lu.service.impl.LuServiceImpl", port = "8181")
	public LuService client;

	// Bean Ids (defined and preloaded in test-beans.xml)
	public static final String atp1_id = "11223344-1122-1122-1111-000000000000";
	public static final String atpStart_id = "11223344-1122-1122-1111-000000000001";
	public static final String atpEnd_id = "11223344-1122-1122-1111-000000000002";
	public static final String luType1_id = "11223344-1122-1122-1111-000000000003";
	public static final String luAttrTyp1_id = "11223344-1122-1122-1111-000000000004";
	public static final String clu1_id = "11223344-1122-1122-1111-000000000005";
	public static final String clu2_id = "11223344-1122-1122-1111-000000000006";
	public static final String lui1_id = "11223344-1122-1122-1111-000000000007";
	public static final String lui2_id = "11223344-1122-1122-1111-000000000008";
	public static final String luRelationType1_id = "11223344-1122-1122-1111-000000000009";
	public static final String cluRelation1_id = "11223344-1122-1122-1111-000000000010";
	public static final String luiRelation1_id = "11223344-1122-1122-1111-000000000011";
	public static final String cluSet1_id = "11223344-1122-1122-1111-000000000012";
	public static final String cluSet2_id = "11223344-1122-1122-1111-000000000013";
	public static final String luType2_id = "11223344-1122-1122-1111-000000000014";
	public static final String luRelationType2_id = "11223344-1122-1122-1111-000000000017";
	public static final String luRelationType3_id = "11223344-1122-1122-1111-000000000018";
	public static final String luRelationType4_id = "11223344-1122-1122-1111-000000000019";
	public static final String cluSet3_id = "11223344-1122-1122-1111-000000000020";

	@Test
	public void testFindLuTypes() throws OperationFailedException {
		List<LuTypeInfo> luTypes = client.findLuTypes();
		assertEquals(2, luTypes.size());
		List<String> idList = new ArrayList<String>();
		idList.add(luTypes.get(0).getLuTypeKey());
		idList.add(luTypes.get(1).getLuTypeKey());
		assertTrue(idList.contains(luType1_id));
		assertTrue(idList.contains(luType2_id));
	}

	@Test
	public void testFindLuRelationTypes() throws OperationFailedException {
		List<String> luRelationTypeIds = client.findLuRelationTypes();
		assertEquals(4, luRelationTypeIds.size());
		assertTrue(luRelationTypeIds.contains(luRelationType1_id));
		assertTrue(luRelationTypeIds.contains(luRelationType2_id));
		assertTrue(luRelationTypeIds.contains(luRelationType3_id));
		assertTrue(luRelationTypeIds.contains(luRelationType4_id));
	}

	@Test
	public void testFetchLuType() throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		LuTypeInfo luTypeInfo = client.fetchLuType(luType1_id);
		assertNotNull(luTypeInfo);
		assertEquals("USER-12345", luTypeInfo.getCreateUserId());
	}

	@Test
	public void testFindAllowedLuLuRelationTypesForLuType()
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		List<String> luRelationTypeIds = client
				.findAllowedLuLuRelationTypesForLuType(luType1_id, luType2_id);
		assertEquals(2, luRelationTypeIds.size());
		assertTrue(luRelationTypeIds.contains(luRelationType2_id));
		assertTrue(luRelationTypeIds.contains(luRelationType3_id));
	}

	@Test
	public void testCreateClu() throws AlreadyExistsException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException, DoesNotExistException {
		CluCreateInfo cluCreate = new CluCreateInfo();
		cluCreate.setCluLongName("Long Name Qwerty");
		cluCreate.setCluShortName("Clu Short Name");
		cluCreate.setDescription("Description");
		cluCreate.setEffectiveEndCycle(atpEnd_id);
		cluCreate.setEffectiveStartCycle(atpStart_id);
		cluCreate.setEffectiveEndDate(new Date());
		cluCreate.setEffectiveStartDate(new Date());
		List<String> attrList = new ArrayList<String>();
		attrList.add("Value2");
		cluCreate.getAttributes().put("Attribute Type Two", attrList);
		attrList = new ArrayList<String>();
		attrList.add("Value3");
		cluCreate.getAttributes().put("Attribute Type Three", attrList);
		String createdId = client.createClu(luType2_id, cluCreate);
		CluInfo foundClu  = client.fetchClu(createdId);
		assertNotNull(foundClu);
		assertEquals("Value2",foundClu.getAttributes().get("Attribute Type Two").get(0));
		assertEquals("Value3",foundClu.getAttributes().get("Attribute Type Three").get(0));
		assertEquals(atpEnd_id,foundClu.getEffectiveEndCycle());
		assertEquals(atpStart_id,foundClu.getEffectiveStartCycle());
		assertEquals("Long Name Qwerty",foundClu.getCluLongName());
		assertEquals(luType2_id,foundClu.getLuTypeId());
		
	}
	
	public void testCreateLui() throws AlreadyExistsException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException{
		LuiCreateInfo luiCreateInfo = new LuiCreateInfo();
		
		Map<String,List<String>> attributes = new HashMap<String,List<String>>();
		List<String> attrList = new ArrayList<String>();
		attrList.add("TestTestTest");
		attributes.put("Attribute Type One", attrList);
		attrList = new ArrayList<String>();
		attrList.add("ERROR!");
		attributes.put("Attribute Type NotReal", attrList);
		luiCreateInfo.setAttributes(attributes);
		String luiId = client.createLui(clu1_id, atp1_id, luiCreateInfo);
		LuiInfo luiInfo = client.fetchLui(luiId);
		assertEquals("TestTestTest",luiInfo.getAttributes().get("Attribute Type One").get(0));
		assertEquals(null,luiInfo.getAttributes().get("Attribute Type NotReal"));
	}
	
	@Test 
	public void testUpdateClu() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
		CluUpdateInfo cluUpdateInfo = new CluUpdateInfo();
		cluUpdateInfo.setCluLongName("Updated Long Name!!");
		cluUpdateInfo.setEffectiveEndCycle(atp1_id);
		cluUpdateInfo.getAttributes();
		assertTrue(client.updateClu(clu1_id, cluUpdateInfo).isSuccess());
		CluInfo clu = client.fetchClu(clu1_id);
		assertEquals("Updated Long Name!!",clu.getCluLongName());
	}
	
	@Test
	public void testFindLuisForClu() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException{
		List<LuiDisplay> luis = client.findLuisForClu(clu1_id, atp1_id);
		assertNotNull(luis);
	}

	@Test(expected=CircularReferenceException.class)
	public void testAddCluSetToCluSet() throws DoesNotExistException, CircularReferenceException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
		assertEquals(true,client.addCluSetToCluSet(cluSet2_id, cluSet3_id).isSuccess());
		client.addCluSetToCluSet(cluSet3_id, cluSet1_id);
	}
	
	@Test
	public void testIsCluInCluSet() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
		assertTrue(client.isCluInCluSet(clu1_id, cluSet1_id));
	}
	
	@Test
	public void testSearchForLuis() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
		LuiCriteria luiCriteria = new LuiCriteria();
		luiCriteria.setDescription("%CLU%");
		luiCriteria.setLuTypeKey(luType1_id);
		List<LuiDisplay> results = client.searchForLuis(luiCriteria);
		assertEquals(2,results.size());
	}
	
	@Test
	public void testFetchLui() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
		LuiInfo luiInfo = client.fetchLui(lui1_id);
		assertEquals("LUI Specific value",luiInfo.getAttributes().get("Attribute Type One").get(0));
	}
	
	@Test
	public void testCrudLuType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException{
		// Create an lutype info
		LuTypeInfo luType = new LuTypeInfo();
		luType.setDescription("Description for LuType");
		String luTypeId = client.createLuTypeInfo(luType);
		
		// Create an lu attribute
		LuAttributeTypeInfo luAttrType = new LuAttributeTypeInfo();
		luAttrType.setDataType("STRING");
		luAttrType.setDisplayType("DROPDOWN");
		luAttrType.setList(true);
		luAttrType.setName("PREREQUISITES");
		luAttrType.setScatId("FK_SCAT01");
		luAttrType.setGroupingCd("FACULTY");
		String luAttrTypeId = client.createLuAttributeTypeInfo(luAttrType);
		
		// make some updates
		
		// fetch the lu type, add the key and resave
		LuTypeInfo foundLuTypeInfo = client.fetchLuType(luTypeId);
		foundLuTypeInfo.getLuAttributeTypeIds().add(luAttrTypeId);
		client.updateLuTypeInfo(foundLuTypeInfo);
		
		foundLuTypeInfo = client.fetchLuType(luTypeId);
		assertEquals(luAttrTypeId,foundLuTypeInfo.getLuAttributeTypeIds().get(0));
		
		// Update the attribute type 
		luAttrType = client.findLuAttributeTypesForLuTypeId(luTypeId).get(0);
		luAttrType.setDataType("DIFFERENT");
		client.updateLuAttributeTypeInfo(luAttrType);

		LuAttributeTypeInfo foundLuAttrTypeInfo = client.fetchLuAttributeType(luAttrTypeId);
		assertEquals("DIFFERENT",foundLuAttrTypeInfo.getDataType());
		assertEquals("FACULTY",foundLuAttrTypeInfo.getGroupingCd());
		
		assertEquals(1,client.searchForLuTypesByDescription("N FoR L").size());
		
		
	}
	
}
