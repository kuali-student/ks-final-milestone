package org.kuali.student.lum.lu.naturallanguage;

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
import org.kuali.student.lum.lu.entity.ReqComponent;

@PersistenceFileLocation("classpath:META-INF/lu-persistence.xml")
public class NaturalLanguageTranslatorTest extends AbstractTransactionalDaoTest {
	@Dao(value = "org.kuali.student.lum.lu.dao.impl.LuDaoImpl", testSqlFile = "classpath:ks-lu.sql") 
	public LuDao luDao;

	private String cluSetId1 = "CLUSET-NL-1";
	private ReqComponent reqComponent;

    @BeforeClass
    public static void setUpOnce() throws Exception {
    }

    @AfterClass
    public static void tearDownOnce() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
		this.reqComponent = luDao.fetch(ReqComponent.class, "REQCOMP-NL-1");
    }
    
    @After
    public void tearDown() throws Exception {
    }
    
	@Test
	public void testTranslateReqComponent() throws DoesNotExistException, OperationFailedException {
		String nlUsageTypeKey = "KUALI.CATALOG";
		CluUtil.createReqComponentFields(this.luDao, this.reqComponent, "1", "greater_than_or_equal_to", this.cluSetId1);
		
		NaturalLanguageTranslatorImpl translator = new NaturalLanguageTranslatorImpl();
		translator.setLuDao(this.luDao);
		String text = translator.translateReqComponent(reqComponent.getId(), nlUsageTypeKey);

		Assert.assertEquals("Student must have completed 1 of MATH 152, MATH 221", text);
	}

	@Test
	public void testTranslateReqComponent_InvalidReqComponentId() throws DoesNotExistException, OperationFailedException {
		NaturalLanguageTranslatorImpl translator = new NaturalLanguageTranslatorImpl();
		translator.setLuDao(this.luDao);
		
		try {
			translator.translateReqComponent("InvalidId", "KUALI.CATALOG");
			Assert.fail("Requirement component translation should have failed since 'InvalidId' is not a valid requirement component id");
		} catch (DoesNotExistException e) {
			Assert.assertNotNull(e.getMessage());
		}
	}

	@Test
	public void testTranslateReqComponent_InvalidNlUsageTypeKey() throws DoesNotExistException, OperationFailedException {
		NaturalLanguageTranslatorImpl translator = new NaturalLanguageTranslatorImpl();
		translator.setLuDao(this.luDao);
		
		try {
			translator.translateReqComponent(reqComponent.getId(), "KUALI.xxx.CATALOG");
			Assert.fail("Requirement component translation should have failed since 'KUALI.xxx.CATALOG' is not a valid nlUsageTypeKey");
		} catch (DoesNotExistException e) {
			Assert.assertNotNull(e.getMessage());
		}
	}

	@Test
	public void testTranslateStatement() throws DoesNotExistException, OperationFailedException {
	}
}
