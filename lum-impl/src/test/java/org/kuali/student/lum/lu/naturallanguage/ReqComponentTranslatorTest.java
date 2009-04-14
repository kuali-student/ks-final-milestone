package org.kuali.student.lum.lu.naturallanguage;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
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
import org.kuali.student.lum.lu.entity.ReqComponentTypeNLTemplate;

@PersistenceFileLocation("classpath:META-INF/lu-persistence.xml")
public class ReqComponentTranslatorTest extends AbstractTransactionalDaoTest {
	@Dao(value = "org.kuali.student.lum.lu.dao.impl.LuDaoImpl") 
	public LuDao luDao;

	private String cluSetId1;
	private ReqComponent reqComponent;
    private String courseTemplate = 
		"#if($cluSet.getCluSet().getClus().size() == $mathTool.toNumber($expectedValue)) \n" +
		"  Student must have completed all of $cluSet.getCluSetAsString() \n" +
		"#elseif($mathTool.toNumber($expectedValue) <= 0 && ($relationalOperator == 'less_than' || $relationalOperator == 'less_than_or_equal_to') ) \n" +
		"  Student must have completed none of $cluSet.getCluSetAsString() \n" +
		"#else \n" +
		"  Student must have completed $expectedValue of $cluSet.getCluSetAsString() \n" +
		"#end";

    @BeforeClass
    public static void setUpOnce() throws Exception {
    }

    @AfterClass
    public static void tearDownOnce() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    	createCluSet();
    	createReqComponent("KUALI.CATALOG", "kuali.reqCompType.courseList.nof");
    }
    
    @After
    public void tearDown() throws Exception {
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

    	Clu clu2 = new Clu();
    	CluIdentifier cluId2 = new CluIdentifier();
    	cluId2.setCode("MATH221");
    	cluId2.setDivision("MATH");
    	cluId2.setLevel("221");
    	cluId2.setShortName("MATH 221");
    	cluId2.setLongName("MATH 221 Matrix Algebra");
    	clu2.setOfficialIdentifier(cluId2);
    	
    	clu2 = this.luDao.create(clu2);

    	List<Clu> cluList = new ArrayList<Clu>();
    	cluList.add(clu1);
    	cluList.add(clu2);

    	
    	CluSet cluSet = new CluSet();
    	cluSet.setName("Math 158,221 CLU Set");
    	cluSet.setClus(cluList);
    	
    	cluSet = this.luDao.create(cluSet);
    	this.cluSetId1 = cluSet.getId();
    }

    private void createReqComponent(String nlUsageTypeKey, String reqComponentType) throws DoesNotExistException {
    	this.reqComponent = new ReqComponent();
		ReqComponentType reqCompType = new ReqComponentType();
		reqCompType.setId("kuali.reqCompType.courseList.nof");
		reqCompType.setName(reqComponentType);
		
		ReqComponentTypeNLTemplate templateType = new ReqComponentTypeNLTemplate();
		templateType.setNlUsageTypeKey(nlUsageTypeKey);
		templateType.setTemplate(this.courseTemplate);
		templateType = this.luDao.create(templateType);

		List<ReqComponentTypeNLTemplate> templateList = new ArrayList<ReqComponentTypeNLTemplate>();
		templateList.add(templateType);
		
		reqCompType.setNlUsageTemplates(templateList);
		reqCompType = this.luDao.create(reqCompType);
		
		this.reqComponent.setRequiredComponentType(reqCompType);
		this.reqComponent = this.luDao.create(this.reqComponent);
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

	@Test
	public void testTranslate_OneOf() throws DoesNotExistException, OperationFailedException {
		String nlUsageTypeKey = "KUALI.CATALOG";
		createReqComponentFields("1", "greater_than_or_equal_to");
		
		ReqComponentTranslator translator = new ReqComponentTranslator();
		translator.setLuDao(this.luDao);
		String text = translator.translate(reqComponent.getId(), nlUsageTypeKey);

		Assert.assertEquals("Student must have completed 1 of MATH 152, MATH 221", text);
	}

	@Test
	public void testTranslate_AllOf() throws DoesNotExistException, OperationFailedException {
		String nlUsageTypeKey = "KUALI.CATALOG";
		createReqComponentFields("2", "equal_to");
		
		ReqComponentTranslator translator = new ReqComponentTranslator();
		translator.setLuDao(this.luDao);
		String text = translator.translate(reqComponent.getId(), nlUsageTypeKey);

		Assert.assertEquals("Student must have completed all of MATH 152, MATH 221", text);
	}

	@Test
	public void testTranslate_NoneOf() throws DoesNotExistException, OperationFailedException {
		String nlUsageTypeKey = "KUALI.CATALOG";
		createReqComponentFields("0", "less_than_or_equal_to");
		
		ReqComponentTranslator translator = new ReqComponentTranslator();
		translator.setLuDao(this.luDao);
		String text = translator.translate(reqComponent.getId(), nlUsageTypeKey);

		Assert.assertEquals("Student must have completed none of MATH 152, MATH 221", text);
	}
}
