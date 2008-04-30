package org.kuali.student.poc.learningunit.lu;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import org.kuali.student.poc.wsdl.learningunit.lu.LuService;
import org.kuali.student.poc.xsd.learningunit.lu.dto.CluCreateInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.CluInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.CluUpdateInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.LuTypeInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.LuiDisplay;
import org.springframework.test.annotation.ExpectedException;

@Daos( { @Dao(value = "org.kuali.student.poc.learningunit.lu.dao.impl.LuDaoImpl", testDataFile = "classpath:test-beans.xml") })
@PersistenceFileLocation("classpath:META-INF/lu-persistence.xml")
public class TestLuServiceImpl extends AbstractServiceTest {
	@Client(value = "org.kuali.student.poc.learningunit.lu.service.impl.LuServiceImpl", port = "9191")
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
		cluCreate.getAttributes().put("Attribute Type Two", "Value2");
		cluCreate.getAttributes().put("Attribute Type Three", "Value3");
		String createdId = client.createClu(luType2_id, cluCreate);
		CluInfo foundClu  = client.fetchClu(createdId);
		assertNotNull(foundClu);
		assertEquals("Value2",foundClu.getAttributes().get("Attribute Type Two"));
		assertEquals("Value3",foundClu.getAttributes().get("Attribute Type Three"));
		assertEquals(atpEnd_id,foundClu.getEffectiveEndCycle());
		assertEquals(atpStart_id,foundClu.getEffectiveStartCycle());
		assertEquals("Long Name Qwerty",foundClu.getCluLongName());
		assertEquals(luType2_id,foundClu.getLuTypeId());
		
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

	@Test
	@ExpectedException(CircularReferenceException.class)
	public void testAddCluSetToCluSet() throws DoesNotExistException, CircularReferenceException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
		assertEquals(true,client.addCluSetToCluSet(cluSet2_id, cluSet3_id).isSuccess());
		client.addCluSetToCluSet(cluSet3_id, cluSet1_id);
	}
	
	@Test
	public void testIsCluInCluSet() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
		assertTrue(client.isCluInCluSet(clu1_id, cluSet1_id));
		assertFalse(client.isCluInCluSet(clu1_id, cluSet3_id));
	}
}
