package org.kuali.student.lum.nlt.naturallanguage.translators;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.lum.lu.dao.LuDao;
import org.kuali.student.lum.lu.entity.Clu;
import org.kuali.student.lum.lu.entity.CluIdentifier;
import org.kuali.student.lum.lu.entity.CluSet;
import org.kuali.student.lum.lu.entity.ReqComponent;
import org.kuali.student.lum.lu.entity.ReqComponentField;
import org.kuali.student.lum.lu.entity.ReqComponentType;
import org.kuali.student.lum.nlt.naturallanguage.ContextRegistry;
import org.kuali.student.lum.nlt.naturallanguage.NaturalLanguageUtil;
import org.kuali.student.lum.nlt.naturallanguage.context.Context;
import org.kuali.student.lum.nlt.naturallanguage.translators.ReqComponentTranslator;

@PersistenceFileLocation("classpath:META-INF/lu-persistence.xml")
public class ReqComponentTranslatorTest extends AbstractTransactionalDaoTest {
	@Dao(value = "org.kuali.student.lum.lu.dao.impl.LuDaoImpl", testSqlFile = "classpath:ks-lu.sql") 
	public LuDao luDao;

	private ReqComponentTranslator englishTranslator;
	private ReqComponentTranslator germanTranslator;
	private String cluSetId1;
	private String cluId1;
	private String cluId2;
	private ReqComponent reqComponent;

    @Before
    public void setUp() throws Exception {
    	createTranslator();
    	createCluSet();
    }
    
    @After
    public void tearDown() throws Exception {
    }
    
    private void createTranslator() {
    	ContextRegistry<Context<ReqComponent>> contextRegistry = NaturalLanguageUtil.getReqComponentContextRegistry(this.luDao);
		this.englishTranslator = new ReqComponentTranslator();
		this.englishTranslator.setContextRegistry(contextRegistry);
		this.englishTranslator.setLanguage("en");

		this.germanTranslator = new ReqComponentTranslator();
		this.germanTranslator.setContextRegistry(contextRegistry);
		this.germanTranslator.setLanguage("de");
    }

    private void createCluSet() {
    	Clu clu1 = new Clu();
    	CluIdentifier cluId1 = new CluIdentifier();
    	cluId1.setCode("MATH152");
    	cluId1.setDivision("MATH");
    	cluId1.setLevel("152");
    	cluId1.setShortName("MATH 152");
    	cluId1.setLongName("MATH 152 Linear Systems");
    	clu1.setOfficialIdentifier(cluId1);
    	
    	clu1 = this.luDao.create(clu1);
    	this.cluId1 = clu1.getId();
    	
    	Clu clu2 = new Clu();
    	CluIdentifier cluId2 = new CluIdentifier();
    	cluId2.setCode("MATH221");
    	cluId2.setDivision("MATH");
    	cluId2.setLevel("221");
    	cluId2.setShortName("MATH 221");
    	cluId2.setLongName("MATH 221 Matrix Algebra");
    	clu2.setOfficialIdentifier(cluId2);
    	
    	clu2 = this.luDao.create(clu2);
    	this.cluId2 = clu2.getId();

    	List<Clu> cluList = new ArrayList<Clu>();
    	cluList.add(clu1);
    	cluList.add(clu2);

    	
    	CluSet cluSet = new CluSet();
    	cluSet.setName("Math 152,221 CLU Set");
    	cluSet.setClus(cluList);
    	
    	cluSet = this.luDao.create(cluSet);
    	this.cluSetId1 = cluSet.getId();
    }

    private void createReqComponent(String nlUsageTypeKey, String reqComponentType) throws DoesNotExistException {
    	this.reqComponent = new ReqComponent();
		ReqComponentType reqCompType = this.luDao.fetch(ReqComponentType.class, reqComponentType);
		this.reqComponent.setRequiredComponentType(reqCompType);
		this.reqComponent = this.luDao.create(this.reqComponent);
    }
    
    private ReqComponent createReqComponentFromType(String nlUsageTypeKey, String reqComponentType) throws DoesNotExistException {
    	ReqComponent reqComponent = new ReqComponent();
		ReqComponentType reqCompType = this.luDao.fetch(ReqComponentType.class, reqComponentType);
		reqComponent.setRequiredComponentType(reqCompType);
		return reqComponent;
    }
    
    private void createReqComponentFields(String expectedValue, String operator) {
		List<ReqComponentField> fieldList = new ArrayList<ReqComponentField>();
		ReqComponentField field1 = new ReqComponentField();
		field1.setKey("reqCompFieldType.requiredCount");
		field1.setValue(expectedValue);
		field1.prePersist();
		fieldList.add(field1);
		this.luDao.create(field1);
		
		ReqComponentField field2 = new ReqComponentField();
		field2.setKey("reqCompFieldType.operator");
		field2.setValue(operator);
		field2.prePersist();
		fieldList.add(field2);
		this.luDao.create(field2);
		
		ReqComponentField field3 = new ReqComponentField();
		field3.setKey("reqCompFieldType.cluSet");
		field3.setValue(cluSetId1);
		field3.prePersist();
		fieldList.add(field3);
		this.luDao.create(field3);
		
		this.reqComponent.setReqCompField(fieldList);
		this.reqComponent = this.luDao.update(reqComponent);
    }

    private void createReqComponentFieldsForClu(String expectedValue, String operator, String cluIds) {
		List<ReqComponentField> fieldList = new ArrayList<ReqComponentField>();
		ReqComponentField field1 = new ReqComponentField();
		field1.setKey("reqCompFieldType.requiredCount");
		field1.setValue(expectedValue);
		field1.prePersist();
		fieldList.add(field1);
		//this.luDao.create(field1);
		
		ReqComponentField field2 = new ReqComponentField();
		field2.setKey("reqCompFieldType.operator");
		field2.setValue(operator);
		field2.prePersist();
		fieldList.add(field2);
		//this.luDao.create(field2);
		
		ReqComponentField field3 = new ReqComponentField();
		field3.setKey("reqCompFieldType.clu");
		field3.setValue(cluIds);
		field3.prePersist();
		fieldList.add(field3);
		//this.luDao.create(field3);
		
		this.reqComponent.setReqCompField(fieldList);
		//this.reqComponent = this.luDao.update(reqComponent);
    }

	@Test
	public void testInvalidReqComponentType() throws DoesNotExistException, OperationFailedException {
		String nlUsageTypeKey = "KUALI.CATALOG";
    	ReqComponent reqComponent = new ReqComponent();
		ReqComponentType reqCompType = new ReqComponentType();
		reqCompType.setId("xxx.xxx.xxx");
		reqComponent.setRequiredComponentType(reqCompType);

		try {
			this.englishTranslator.translate(reqComponent, nlUsageTypeKey);
			Assert.fail("Translate method should have thrown a DoesNotExistException for requirement component type id xxx.xxx.xxx");
		} catch(DoesNotExistException e) {
			Assert.assertEquals("Requirement component context not found in registry for requirement component type key: xxx.xxx.xxx", e.getMessage());
		}
	}

	@Test
	public void testTranslate_OneOf_English() throws DoesNotExistException, OperationFailedException {
		String nlUsageTypeKey = "KUALI.CATALOG";
    	createReqComponent("KUALI.CATALOG", "kuali.reqCompType.courseList.nof");
		createReqComponentFields("1", "greater_than_or_equal_to");
		
		String text = this.englishTranslator.translate(reqComponent, nlUsageTypeKey);

		Assert.assertEquals("Student must have completed 1 of MATH 152, MATH 221", text);
	}

	@Test
	public void testTranslate_OneOf_German() throws DoesNotExistException, OperationFailedException {
		String nlUsageTypeKey = "KUALI.CATALOG";
    	createReqComponent("KUALI.CATALOG", "kuali.reqCompType.courseList.nof");
		createReqComponentFields("1", "greater_than_or_equal_to");
		
		String text = this.germanTranslator.translate(reqComponent, nlUsageTypeKey);

		Assert.assertEquals("Student muss abgeschlossen 1 von MATH 152, MATH 221", text);
	}

	@Test
	public void testTranslate_OneOf_EnglishGerman() throws DoesNotExistException, OperationFailedException {
		String nlUsageTypeKey = "KUALI.CATALOG";
    	createReqComponent("KUALI.CATALOG", "kuali.reqCompType.courseList.nof");
		createReqComponentFields("1", "greater_than_or_equal_to");
		
		String text = this.englishTranslator.translate(reqComponent, nlUsageTypeKey);
		Assert.assertEquals("Student must have completed 1 of MATH 152, MATH 221", text);

		this.englishTranslator.setLanguage("de");
		
		text = this.englishTranslator.translate(reqComponent, nlUsageTypeKey);
		Assert.assertEquals("Student muss abgeschlossen 1 von MATH 152, MATH 221", text);

		this.englishTranslator.setLanguage("en");
		
		text = this.englishTranslator.translate(reqComponent, nlUsageTypeKey);
		Assert.assertEquals("Student must have completed 1 of MATH 152, MATH 221", text);
}

	@Test
	public void testTranslate_OneOf_1Clu() throws DoesNotExistException, OperationFailedException {
		String nlUsageTypeKey = "KUALI.CATALOG";
    	createReqComponent("KUALI.CATALOG", "kuali.reqCompType.courseList.all");
		createReqComponentFieldsForClu("1", "greater_than_or_equal_to", cluId1);
		
		String text = this.englishTranslator.translate(reqComponent, nlUsageTypeKey);

		Assert.assertEquals("Student must have completed all of MATH 152", text);
	}

	@Test
	public void testTranslate_OneOf_2Clus() throws DoesNotExistException, OperationFailedException {
		String nlUsageTypeKey = "KUALI.CATALOG";
		String clus = cluId1 + "," + cluId2;
    	createReqComponent("KUALI.CATALOG", "kuali.reqCompType.courseList.nof");
		createReqComponentFieldsForClu("1", "greater_than_or_equal_to", clus);
		
		String text = this.englishTranslator.translate(reqComponent, nlUsageTypeKey);

		Assert.assertEquals("Student must have completed 1 of MATH 152, MATH 221", text);
	}

	@Test
	public void testTranslate_AllOf() throws DoesNotExistException, OperationFailedException {
		String nlUsageTypeKey = "KUALI.CATALOG";
    	createReqComponent("KUALI.CATALOG", "kuali.reqCompType.courseList.all");
		createReqComponentFields("2", "equal_to");
		
		String text = this.englishTranslator.translate(reqComponent, nlUsageTypeKey);

		Assert.assertEquals("Student must have completed all of MATH 152, MATH 221", text);
	}

	@Test
	public void testTranslate_NoneOf() throws DoesNotExistException, OperationFailedException {
		String nlUsageTypeKey = "KUALI.CATALOG";
    	createReqComponent("KUALI.CATALOG", "kuali.reqCompType.courseList.none");
		createReqComponentFields("0", "less_than_or_equal_to");
		
		String text = this.englishTranslator.translate(reqComponent, nlUsageTypeKey);

		Assert.assertEquals("Student must have completed none of MATH 152, MATH 221", text);
	}

	@Test
	public void testTranslate_InvalidReqComponentId() throws DoesNotExistException, OperationFailedException {
		try {
			this.englishTranslator.translate(null, "KUALI.CATALOG");
			Assert.fail("Requirement component translation should have failed since requirement component is null");
		} catch (DoesNotExistException e) {
			Assert.assertNotNull(e.getMessage());
		}
	}

	@Test
	public void testTranslate_InvalidNlUsageTypeKey() throws DoesNotExistException, OperationFailedException {
    	createReqComponent("KUALI.CATALOG", "kuali.reqCompType.courseList.none");
		createReqComponentFields("0", "less_than_or_equal_to");

		try {
			this.englishTranslator.translate(reqComponent, "KUALI.xxx.CATALOG");
			Assert.fail("Requirement component translation should have failed since 'KUALI.xxx.CATALOG' is not a valid nlUsageTypeKey");
		} catch (DoesNotExistException e) {
			Assert.assertNotNull(e.getMessage());
		}
	}
	
	@Test
	public void testTranslate1_1Of2() throws DoesNotExistException, OperationFailedException {
		String nlUsageTypeKey = "KUALI.CATALOG";
		String cluIds = "CLU-NL-1, CLU-NL-2";
		this.reqComponent = createReqComponentFromType("KUALI.CATALOG", "kuali.reqCompType.courseList.1of2");
		createReqComponentFieldsForClu("1", "greater_than_or_equal_to", cluIds);
		
		String text = this.englishTranslator.translate(this.reqComponent, nlUsageTypeKey);

		Assert.assertEquals("Student must have completed MATH 152 or MATH 221", text);
	}

	@Test
	public void testTranslate2_1Of2() throws DoesNotExistException, OperationFailedException {
		String nlUsageTypeKey = "KUALI.CATALOG";
		String cluIds = "d9dbf300-b75a-4c50-ae18-d45f7cc81cc7, 8e108d5e-44d0-40eb-bd3e-01c4e8dd7472";
		this.reqComponent = createReqComponentFromType("KUALI.CATALOG", "kuali.reqCompType.courseList.1of2");
		createReqComponentFieldsForClu("1", "greater_than_or_equal_to", cluIds);
		
		String text = this.englishTranslator.translate(this.reqComponent, nlUsageTypeKey);

		Assert.assertEquals("Student must have completed INTRO ASIAN AMERICAN LIT or FILIPINO AMER STUDIES", text);
	}

	@Test
	public void testTranslate_TotalCredits() throws DoesNotExistException, OperationFailedException {
		String nlUsageTypeKey = "KUALI.CATALOG";
		String cluIds = "CLU-NL-1, CLU-NL-2";
		this.reqComponent = createReqComponentFromType("KUALI.CATALOG", "kuali.reqCompType.grdCondCourseList");
		List<ReqComponentField> fieldList = new ArrayList<ReqComponentField>();
		ReqComponentField field1 = new ReqComponentField();
		field1.setKey("reqCompFieldType.clu");
		field1.setValue(cluIds);
		field1.prePersist();
		fieldList.add(field1);
		ReqComponentField field2 = new ReqComponentField();
		field2.setKey("reqCompFieldType.totalCredits");
		field2.setValue("6");
		field2.prePersist();
		fieldList.add(field2);

		this.reqComponent.setReqCompField(fieldList);
		
		String text = this.englishTranslator.translate(this.reqComponent, nlUsageTypeKey);

		Assert.assertEquals("Students must take 6 credits from MATH 152, MATH 221", text);
	}


	@Test
	public void testTranslate_GPA() throws DoesNotExistException, OperationFailedException {
		String nlUsageTypeKey = "KUALI.CATALOG";
		this.reqComponent = createReqComponentFromType("KUALI.CATALOG", "kuali.reqCompType.gradecheck");
		List<ReqComponentField> fieldList = new ArrayList<ReqComponentField>();
		ReqComponentField field1 = new ReqComponentField();
		field1.setKey("reqCompFieldType.gpa");
		field1.setValue("70.0%");
		field1.prePersist();
		fieldList.add(field1);

		this.reqComponent.setReqCompField(fieldList);
		
		String text = this.englishTranslator.translate(this.reqComponent, nlUsageTypeKey);

		Assert.assertEquals("Student needs a minimum GPA of 70.0%", text);
	}
}
