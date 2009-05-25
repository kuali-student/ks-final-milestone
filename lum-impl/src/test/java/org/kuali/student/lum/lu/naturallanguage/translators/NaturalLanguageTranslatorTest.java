package org.kuali.student.lum.lu.naturallanguage.translators;

import static org.junit.Assert.assertEquals;

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
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.lum.lu.dao.LuDao;
import org.kuali.student.lum.lu.dto.NLTranslationNodeInfo;
import org.kuali.student.lum.lu.entity.ReqComponent;
import org.kuali.student.lum.lu.naturallanguage.ContextRegistry;
import org.kuali.student.lum.lu.naturallanguage.NaturalLanguageUtil;
import org.kuali.student.lum.lu.naturallanguage.translators.NaturalLanguageTranslatorImpl;
import org.kuali.student.lum.lu.naturallanguage.translators.ReqComponentTranslator;
import org.kuali.student.lum.lu.naturallanguage.translators.StatementTranslator;

@PersistenceFileLocation("classpath:META-INF/lu-persistence.xml")
public class NaturalLanguageTranslatorTest extends AbstractTransactionalDaoTest {
	@Dao(value = "org.kuali.student.lum.lu.dao.impl.LuDaoImpl", testSqlFile = "classpath:ks-lu.sql") 
	public LuDao luDao;
	private NaturalLanguageTranslatorImpl translator;;

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
    	this.translator = new NaturalLanguageTranslatorImpl();
    	
    	ContextRegistry contextRegistry = NaturalLanguageUtil.getContextRegistry(this.luDao);

    	ReqComponentTranslator reqComponentTranslator = new ReqComponentTranslator();
    	reqComponentTranslator.setLuDao(this.luDao);
    	reqComponentTranslator.setContextRegistry(contextRegistry);

    	StatementTranslator statementTranslator = new StatementTranslator();
    	statementTranslator.setLuDao(this.luDao);
    	statementTranslator.setReqComponentTranslator(reqComponentTranslator);

    	this.translator.setReqComponentTranslator(reqComponentTranslator);
    	this.translator.setStatementTranslator(statementTranslator);

    	this.reqComponent = luDao.fetch(ReqComponent.class, "REQCOMP-NL-1");
    }
    
    @After
    public void tearDown() throws Exception {
    }
    
	@Test
	public void testTranslateReqComponent() throws DoesNotExistException, OperationFailedException {
		String nlUsageTypeKey = "KUALI.CATALOG";
		NaturalLanguageUtil.createReqComponentFields(this.luDao, this.reqComponent, "1", "greater_than_or_equal_to", this.cluSetId1);
		
		String text = translator.translateReqComponent(reqComponent.getId(), nlUsageTypeKey);

		Assert.assertEquals("Student must have completed 1 of MATH 152, MATH 180", text);
	}

	@Test
	public void testTranslateReqComponent_InvalidReqComponentId() throws DoesNotExistException, OperationFailedException {
		try {
			translator.translateReqComponent("InvalidId", "KUALI.CATALOG");
			Assert.fail("Requirement component translation should have failed since 'InvalidId' is not a valid requirement component id");
		} catch (DoesNotExistException e) {
			Assert.assertNotNull(e.getMessage());
		}
	}

	@Test
	public void testTranslateReqComponent_InvalidNlUsageTypeKey() throws DoesNotExistException, OperationFailedException {
		try {
			translator.translateReqComponent(reqComponent.getId(), "KUALI.xxx.CATALOG");
			Assert.fail("Requirement component translation should have failed since 'KUALI.xxx.CATALOG' is not a valid nlUsageTypeKey");
		} catch (DoesNotExistException e) {
			Assert.assertNotNull(e.getMessage());
		}
	}

	@Test
	public void testTranslateStatement() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		String naturalLanguage = translator.translateStatement("CLU-NL-1", "STMT-5", "KUALI.CATALOG");
		assertEquals("Requirement for MATH 152 Linear Systems: Student must have completed 1 of MATH 152, MATH 180 or Student must have completed 2 of MATH 152, MATH 221, MATH 180", naturalLanguage);
	}

	@Test
	public void testTranslateStatementTree() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		NLTranslationNodeInfo rootNode = translator.translateToTree("CLU-NL-1", "STMT-5", "KUALI.CATALOG");

		assertEquals("STMT-5", rootNode.getId());
		assertEquals("R1 + R2", rootNode.getBooleanExpression());
		assertEquals("R1 or R2", rootNode.getProperBooleanExpression());
		assertEquals(2, rootNode.getChildNodes().size());
		assertEquals("Requirement for MATH 152 Linear Systems: Student must have completed 1 of MATH 152, MATH 180 or Student must have completed 2 of MATH 152, MATH 221, MATH 180", rootNode.getNLTranslation());
	}

}
