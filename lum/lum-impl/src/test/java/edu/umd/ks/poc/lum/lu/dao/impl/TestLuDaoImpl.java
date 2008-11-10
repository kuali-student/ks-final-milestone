package edu.umd.ks.poc.lum.lu.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.kuali.student.poc.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.poc.common.test.spring.Dao;
import org.kuali.student.poc.common.test.spring.PersistenceFileLocation;
import edu.umd.ks.poc.lum.lu.dao.LuDao;
import edu.umd.ks.poc.lum.lu.entity.Atp;
import edu.umd.ks.poc.lum.lu.entity.Clu;
import edu.umd.ks.poc.lum.lu.entity.CluAttribute;
import edu.umd.ks.poc.lum.lu.entity.CluCrit;
import edu.umd.ks.poc.lum.lu.entity.CluRelation;
import edu.umd.ks.poc.lum.lu.entity.CluSet;
import edu.umd.ks.poc.lum.lu.entity.LuAttributeType;
import edu.umd.ks.poc.lum.lu.entity.LuRelationType;
import edu.umd.ks.poc.lum.lu.entity.LuType;
import edu.umd.ks.poc.lum.lu.entity.Lui;
import edu.umd.ks.poc.lum.lu.entity.LuiRelation;
import edu.umd.ks.poc.lum.lu.entity.SearchKeyValue;
import edu.umd.ks.poc.lum.lu.dto.LuiCriteria;

@PersistenceFileLocation("classpath:META-INF/lu-persistence.xml")
public class TestLuDaoImpl extends AbstractTransactionalDaoTest {
	@Dao(value = "edu.umd.ks.poc.lum.lu.dao.impl.LuDaoImpl", testDataFile = "classpath:test-beans.xml")
	public LuDao dao;

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

	@Test
	public void testCreateClu() {

		Clu clu = new Clu();
		CluAttribute at1 = new CluAttribute();
		at1.setClu(clu);
		at1.setLuAttributeType(em.find(LuAttributeType.class, luAttrTyp1_id));
		List<String> attrList = new ArrayList<String>();
		attrList.add("Attr VALUE 1");
		at1.setValues(attrList);
		clu.setEffectiveStartCycle(em.find(Atp.class, atpStart_id));
		clu.setEffectiveEndCycle(em.find(Atp.class, atpEnd_id));
		clu.getAttributes().add(at1);
		clu.setApprovalStatus("Approved");
		clu.setApprovalStatusTime("ShouldBeDate?");
		clu.setCluLongName("Clu LongName 2345");
		clu.setCluShortName("Clu ShortName 2345");
		clu.setDescription("CREATED CLU DESCRIPTION LA LA LA");
		clu.setEffectiveEndDate(new Date());
		clu.setEffectiveStartDate(new Date());
		clu.setCreateTime(new Date());
		clu.setCreateUserComment("CreateUserComment");
		clu.setCreateUserId("CreateUserId");
		clu.setUpdateTime(new Date());
		clu.setUpdateUserComment("UpdateUserComment");
		clu.setUpdateUserId("UpdateUserId");
		clu.setLuType(em.find(LuType.class, luType1_id));
		dao.createClu(clu);
		assertEquals(em.find(Clu.class, clu.getCluId()), clu);
	}

	@Test
	public void testCreateAtp() {
		Atp atp = new Atp();
		atp.setAtpName("CREATED Atp");
		dao.createAtp(atp);
		assertEquals(em.find(Atp.class, atp.getAtpId()), atp);
	}

	@Test
	public void testCreateLuType() {
		LuType luType = new LuType();
		luType.setCreateTime(new Date());
		luType.setCreateUserComment("Created");
		luType.setCreateUserId("USER-12345");
		luType.setUpdateTime(new Date());
		luType.setUpdateUserComment("Created");
		luType.setUpdateUserId("USER-12345");
		luType.getLuAttributeTypes().add(
				em.find(LuAttributeType.class, luAttrTyp1_id));
		dao.createLuType(luType);
		assertEquals(em.find(LuType.class, luType.getLuTypeId()), luType);
	}

	@Test
	public void testCreateLui() {
		Lui lui = new Lui();
		lui.setAtp(em.find(Atp.class, atp1_id));
		lui.setClu(em.find(Clu.class, clu1_id));
		lui.setLuiCode("CREATED Lui Code");
		lui.setMaxSeats(128);
		dao.createLui(lui);
		assertEquals(em.find(Lui.class, lui.getLuiId()), lui);
	}

	@Test
	public void testCreateLuiRelation() {
		LuiRelation luiRelation = new LuiRelation();
		luiRelation.setCreateTime(new Date());
		luiRelation.setCreateUserComment("CreateUserComment");
		luiRelation.setCreateUserId("CreateUserId");
		luiRelation.setEffectiveEndDate(new Date());
		luiRelation.setEffectiveStartDate(new Date());
		luiRelation.setLui(em.find(Lui.class, lui1_id));
		luiRelation.setLuRelationType(em.find(LuRelationType.class,
				luRelationType1_id));
		luiRelation.setRelatedLui(em.find(Lui.class, lui2_id));
		luiRelation.setUpdateTime(new Date());
		luiRelation.setUpdateUserComment("UpdateUserComment");
		luiRelation.setUpdateUserId("UpdateUserId");
		dao.createLuiRelation(luiRelation);
		assertEquals(em.find(LuiRelation.class, luiRelation.getId()),
				luiRelation);
	}

	@Test
	public void testCreateLuRelationType() {
		LuRelationType luRelationType = new LuRelationType();
		luRelationType.setRelation("Relation Type One");
		dao.createLuRelationType(luRelationType);
		assertEquals(em.find(LuRelationType.class, luRelationType.getId()),
				luRelationType);
	}

	@Test
	public void testCreateCluRelation() {
		CluRelation cluRelation = new CluRelation();
		cluRelation.setCreateTime(new Date());
		cluRelation.setCreateUserComment("CreateUserComment");
		cluRelation.setCreateUserId("CreateUserId");
		cluRelation.setEffectiveEndDate(new Date());
		cluRelation.setEffectiveStartDate(new Date());
		cluRelation.setClu(em.find(Clu.class, clu1_id));
		cluRelation.setLuRelationType(em.find(LuRelationType.class,
				luRelationType1_id));
		cluRelation.setRelatedClu(em.find(Clu.class, clu2_id));
		cluRelation.setUpdateTime(new Date());
		cluRelation.setUpdateUserComment("UpdateUserComment");
		cluRelation.setUpdateUserId("UpdateUserId");
		dao.createCluRelation(cluRelation);
		assertEquals(em.find(CluRelation.class, cluRelation.getId()),
				cluRelation);
	}

	@Test
	public void testCreateCluSet() {
		CluSet cluSet1 = new CluSet();

		CluCrit cluCrit1 = new CluCrit();
		cluCrit1.setLuTypeKey(luType1_id);
		SearchKeyValue searchKeyValue = new SearchKeyValue();
		searchKeyValue.setKeyName("Field1");
		searchKeyValue.setValue("Value1");
		// searchKeyValue.setCluCrit(cluCrit1);
		cluCrit1.getSearchKeyValues().add(searchKeyValue);
		searchKeyValue = new SearchKeyValue();
		searchKeyValue.setKeyName("Field2");
		searchKeyValue.setValue("Value2");
		// searchKeyValue.setCluCrit(cluCrit1);
		cluCrit1.getSearchKeyValues().add(searchKeyValue);
		// cluCrit1.setCluSet(cluSet1);

		cluSet1.setCluCriteria(cluCrit1);
		cluSet1.setCluSetName("Set Number 1");
		cluSet1.setDescription("Set1 Description");
		cluSet1.setEffectiveEndDate(new Date());
		cluSet1.setEffectiveStartDate(new Date());
		cluSet1.getCluList().add(em.find(Clu.class, clu1_id));
		dao.createCluSet(cluSet1);

		CluSet cluSet2 = new CluSet();

		CluCrit cluCrit2 = new CluCrit();
		cluCrit2.setLuTypeKey(luType1_id);
		searchKeyValue = new SearchKeyValue();
		searchKeyValue.setKeyName("Field1");
		searchKeyValue.setValue("Value1");
		// searchKeyValue.setCluCrit(cluCrit2);
		cluCrit2.getSearchKeyValues().add(searchKeyValue);
		searchKeyValue = new SearchKeyValue();
		searchKeyValue.setKeyName("Field2");
		searchKeyValue.setValue("Value2");
		// searchKeyValue.setCluCrit(cluCrit2);
		cluCrit2.getSearchKeyValues().add(searchKeyValue);
		// cluCrit2.setCluSet(cluSet2);

		cluSet2.setCluCriteria(cluCrit2);
		cluSet2.setCluSetName("Set Number 1");
		cluSet2.setDescription("Set1 Description");
		cluSet2.setEffectiveEndDate(new Date());
		cluSet2.setEffectiveStartDate(new Date());
		cluSet2.getCluList().add(em.find(Clu.class, clu2_id));
		cluSet2.getCluSetList().add(cluSet1);
		dao.createCluSet(cluSet2);

		assertEquals(em.find(CluSet.class, cluSet2.getCluSetId()), cluSet2);
		assertEquals(em.find(CluSet.class, cluSet2.getCluSetId())
				.getCluSetList().get(0), cluSet1);
	}

	public void testDeleteClu() {
		String cluId = clu1_id;
		dao.deleteClu(em.find(Clu.class, clu1_id));
		assertNull(em.find(Clu.class, cluId));
	}

	public void testDeleteCluRelation() {
		CluRelation cluRelation1 = em.find(CluRelation.class, cluRelation1_id);
		String id = cluRelation1.getId();
		dao.deleteCluRelation(cluRelation1);
		assertNull(em.find(CluRelation.class, id));
	}

	public void testDeleteCluSet() {
		CluSet cluSet2 = em.find(CluSet.class, cluSet2_id);
		String id = cluSet2.getCluSetId();
		dao.deleteCluSet(cluSet2);
		assertNull(em.find(CluSet.class, id));
	}

	public void testDeleteLui() {
		Lui lui1 = em.find(Lui.class, lui1_id);
		String id = lui1.getLuiId();
		dao.deleteLui(lui1);
		assertNull(em.find(Lui.class, id));
	}

	public void testDeleteLuiRelation() {
		LuiRelation luiRelation1 = em.find(LuiRelation.class, luiRelation1_id);
		String id = luiRelation1.getId();
		dao.deleteLuiRelation(luiRelation1);
		assertNull(em.find(CluRelation.class, id));
	}

	public void testUpdateClu() {
		Date currentTime = new Date();
		Clu clu1 = em.find(Clu.class, clu1_id);
		clu1.setEffectiveStartCycle(em.find(Atp.class, atpEnd_id));
		clu1.setEffectiveEndCycle(em.find(Atp.class, atpStart_id));
		
		List<String> attrList = new ArrayList<String>();
		attrList.add("UPDATED ATTRIBUTE VALUE");
		
		clu1.getAttributes().iterator().next().setValues(attrList);
		clu1.setCluLongName("UPDATED Clu code 2345");
		clu1.setCluShortName("UPDATED Clu ShortTitle");
		clu1.setDescription("UPDATED CLU DESCRIPTION LA LA LA");
		clu1.setEffectiveStartDate(currentTime);
		clu1.setEffectiveEndDate(currentTime);
		dao.updateClu(clu1);
		Clu updated = em.find(Clu.class, clu1.getCluId());
		assertEquals(em.find(Atp.class, atpEnd_id), updated
				.getEffectiveStartCycle());
		assertEquals(em.find(Atp.class, atpStart_id), updated
				.getEffectiveEndCycle());
		assertEquals("UPDATED ATTRIBUTE VALUE", updated.getAttributes()
				.iterator().next().getValues().get(0));

	}

	public void testUpdateCluRelation() {
		CluRelation cluRelation = em.find(CluRelation.class, cluRelation1_id);
		cluRelation.setCreateTime(new Date());
		cluRelation.setCreateUserComment("UpdatedCreateUserComment");
		cluRelation.setCreateUserId("UpdatedCreateUserId");
		cluRelation.setEffectiveEndDate(new Date());
		cluRelation.setEffectiveStartDate(new Date());
		cluRelation.setClu(em.find(Clu.class, clu2_id));
		cluRelation.setLuRelationType(em.find(LuRelationType.class,
				luRelationType1_id));
		cluRelation.setRelatedClu(em.find(Clu.class, clu1_id));
		cluRelation.setUpdateTime(new Date());
		cluRelation.setUpdateUserComment("UpdateUserComment");
		cluRelation.setUpdateUserId("UpdateUserId");
		dao.updateCluRelation(cluRelation);
		CluRelation updated = em.find(CluRelation.class, cluRelation1_id);
		assertEquals(em.find(Clu.class, clu2_id), updated.getClu());
		assertEquals(em.find(Clu.class, clu1_id), updated.getRelatedClu());
		assertEquals("UpdatedCreateUserComment", updated.getCreateUserComment());
	}

	public void testUpdateCluSet() {
		CluSet cluSet2 = em.find(CluSet.class, cluSet2_id);

		CluCrit cluCrit = new CluCrit();
		cluCrit.setLuTypeKey(luType1_id);
		SearchKeyValue searchKeyValue = new SearchKeyValue();
		searchKeyValue.setKeyName("Updated Field1");
		searchKeyValue.setValue("Updated Value1");
		cluCrit.getSearchKeyValues().add(searchKeyValue);
		searchKeyValue = new SearchKeyValue();
		searchKeyValue.setKeyName("Updated Field2");
		searchKeyValue.setValue("Updated Value2");
		cluCrit.getSearchKeyValues().add(searchKeyValue);

		cluSet2.setCluCriteria(cluCrit);
		cluSet2.setCluSetName("Updated Set Number 1");
		cluSet2.setDescription("Updated Set1 Description");
		cluSet2.setEffectiveEndDate(new Date());
		cluSet2.setEffectiveStartDate(new Date());
		cluSet2.getCluList().add(em.find(Clu.class, clu1_id));
		cluSet2.getCluSetList().clear();
		dao.updateCluSet(cluSet2);
		CluSet updated = em.find(CluSet.class, cluSet2_id);
		assertEquals("Updated CLU*LA%", updated.getCluCriteria());
		assertEquals(2, updated.getCluList().size());
		assertEquals(0, updated.getCluSetList().size());
	}

	public void testUpdateLui() {
		Lui lui = em.find(Lui.class, lui1_id);
		lui.setAtp(em.find(Atp.class, atpStart_id));
		lui.setClu(em.find(Clu.class, clu2_id));
		lui.setLuiCode("Updated Lui Code");
		lui.setMaxSeats(222);
		dao.updateLui(lui);
		Lui updated = em.find(Lui.class, lui1_id);
		assertEquals("Updated Lui Code", updated.getLuiCode());
		assertEquals(em.find(Atp.class, atpStart_id), updated.getAtp());
		assertEquals(em.find(Clu.class, clu2_id), updated.getClu());
		assertEquals(222, updated.getMaxSeats());
	}

	public void testUpdateLuiRelation() {
		LuiRelation luiRelation = em.find(LuiRelation.class, luiRelation1_id);
		luiRelation.setCreateTime(new Date());
		luiRelation.setCreateUserComment("Updated CreateUserComment");
		luiRelation.setCreateUserId("Updated CreateUserId");
		luiRelation.setEffectiveEndDate(new Date());
		luiRelation.setEffectiveStartDate(new Date());
		luiRelation.setLui(em.find(Lui.class, lui2_id));
		luiRelation.setLuRelationType(em.find(LuRelationType.class,
				luRelationType1_id));
		luiRelation.setRelatedLui(em.find(Lui.class, lui1_id));
		luiRelation.setUpdateTime(new Date());
		luiRelation.setUpdateUserComment("Updated UpdateUserComment");
		luiRelation.setUpdateUserId("Updated UpdateUserId");
		dao.updateLuiRelation(luiRelation);
		LuiRelation updated = em.find(LuiRelation.class, luiRelation1_id);
		assertEquals(em.find(Lui.class, lui1_id), updated.getRelatedLui());
		assertEquals(em.find(Lui.class, lui2_id), updated.getLui());
		assertEquals("Updated CreateUserComment", updated
				.getCreateUserComment());

	}

	@Test
	public void testFetchLuiRelation() {
		LuiRelation luiRelation = dao.fetchLuiRelation(lui1_id, lui2_id,
				luRelationType1_id);
		assertNotNull(luiRelation);
		assertEquals(luRelationType1_id,luiRelation.getLuRelationType().getId());
		assertEquals(luiRelation1_id,luiRelation.getId());
	}
	
	@Test
	public void testFetchCluRelation() {
		CluRelation cluRelation = dao.fetchCluRelation(clu1_id, clu2_id,
				luRelationType1_id);
		assertNotNull(cluRelation);
		assertEquals(luRelationType1_id,cluRelation.getLuRelationType().getId());
		assertEquals(cluRelation1_id,cluRelation.getId());
	}
	
	@Test
	public void testSearchForLuis(){
		LuiCriteria luiCriteria = new LuiCriteria();
		luiCriteria.setDescription("%CLU%");
		luiCriteria.setLuTypeKey(luType1_id);
		List<Lui> results = dao.searchForLuis(luiCriteria);
		assertEquals(2,results.size());
	}
}
