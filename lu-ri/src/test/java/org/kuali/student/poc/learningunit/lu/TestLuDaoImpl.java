package org.kuali.student.poc.learningunit.lu;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;

import javax.persistence.Query;

import org.junit.Test;
import org.kuali.student.poc.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.poc.common.test.spring.Dao;
import org.kuali.student.poc.learningunit.lu.dao.LuDao;
import org.kuali.student.poc.learningunit.lu.entity.Atp;
import org.kuali.student.poc.learningunit.lu.entity.Clu;
import org.kuali.student.poc.learningunit.lu.entity.CluRelation;
import org.kuali.student.poc.learningunit.lu.entity.LuAttribute;
import org.kuali.student.poc.learningunit.lu.entity.LuAttributeType;
import org.kuali.student.poc.learningunit.lu.entity.LuRelationType;
import org.kuali.student.poc.learningunit.lu.entity.LuType;
import org.kuali.student.poc.learningunit.lu.entity.Lui;
import org.kuali.student.poc.learningunit.lu.entity.LuiRelation;

public class TestLuDaoImpl extends AbstractTransactionalDaoTest {
	@Dao("org.kuali.student.poc.learningunit.lu.dao.impl.LuDaoImpl")
	public LuDao dao;

	public LuType luType1;
	public Atp atpStart;
	public Atp atpEnd;
	public Atp atp1;
	public LuAttributeType luAttrTyp1;

	@Test
	public void testCreateClu() {

		Clu clu = new Clu();
		LuAttribute at1 = new LuAttribute();
		at1.setClu(clu);
		at1.setLuAttributeType(luAttrTyp1);
		at1.setValue("Attr VALUE 1");
		clu.setAtpStart(atpStart);
		clu.setAtpEnd(atpEnd);
		clu.getAttributes().add(at1);
		clu.setCluCategory("Category 123");
		clu.setCluCode("Clu code 2345");
		clu.setCluShortTitle("Clu ShortTitle");
		clu.setDescription("CREATED CLU DESCRIPTION LA LA LA");
		clu.setLearningResultType(new Long(8675309));
		clu.setLuType(luType1);
		assertNull(clu.getCluId());
		dao.createClu(clu);
		assertNotNull(clu.getCluId());
	}

	@Test
	public void testCreateAtp() {
		Atp atp = new Atp();
		atp.setAtpName("CREATED Atp");

		assertNull(atp.getAtpId());
		dao.createAtp(atp);
		assertNotNull(atp.getAtpId());
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
		luType.getLuAttributeTypes().add(luAttrTyp1);
		assertNull(luType.getLuTypeId());
		dao.createLuType(luType);
		assertNotNull(luType.getLuTypeId());
	}

	@Test
	public void testCreateLui() {

	}

	@Test
	public void testCreateLuiRelation() {

	}

	@Test
	public void testCreateLuRelationType() {

	}

	@Test
	public void testCreateCluRelation() {

	}
}
