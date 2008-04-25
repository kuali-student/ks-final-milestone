package org.kuali.student.poc.learningunit.lu;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.kuali.student.poc.common.test.spring.AbstractServiceTest;
import org.kuali.student.poc.common.test.spring.Client;
import org.kuali.student.poc.common.test.spring.Dao;
import org.kuali.student.poc.common.test.spring.Daos;
import org.kuali.student.poc.common.test.spring.PersistenceFileLocation;
import org.kuali.student.poc.common.ws.exceptions.OperationFailedException;
import org.kuali.student.poc.wsdl.learningunit.lu.LuService;
import org.kuali.student.poc.xsd.learningunit.lu.dto.LuTypeInfo;

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
	public static final String cluSet2_id = "11223344-1122-1122-1111-000000000013";
	public static final String luType2_id = "11223344-1122-1122-1111-000000000014";

	@Test
	public void testFindLuTypes() throws OperationFailedException {
		List<LuTypeInfo> luTypes = client.findLuTypes();
		assertEquals(2, luTypes.size());
		if (luType2_id.equals(luTypes.get(0).getLuTypeKey())) {

		} else if (luType2_id.equals(luTypes.get(1).getLuTypeKey())) {

		} else {
			assertTrue(false);
		}
	}

	@Test
	public void testFindLuRelationTypes() throws OperationFailedException {
		List<String> luRelationTypeIds = client.findLuRelationTypes();
		assertEquals(1, luRelationTypeIds.size());
		assertEquals(luRelationType1_id, luRelationTypeIds.get(0));
	}
}
