package org.kuali.student.core.statement.service.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.Daos;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.statement.config.CluInfo;
import org.kuali.student.core.statement.config.CluSetInfo;
import org.kuali.student.core.statement.config.contexts.CourseListContextImpl;
import org.kuali.student.core.statement.service.StatementService;

@Daos({@Dao(value = "org.kuali.student.core.statement.dao.impl.StatementDaoImpl", testSqlFile = "classpath:ks-statement.sql")})
@PersistenceFileLocation("classpath:META-INF/statement-persistence.xml")
public class TestStatementServiceImpl extends AbstractServiceTest {

    @Client(value = "org.kuali.student.core.statement.service.impl.StatementServiceImpl", additionalContextFile="classpath:statement-additional-context.xml")
    public StatementService statementService;
    
	@BeforeClass
	public static void beforeClass() {
		// Add test data

		// Add CLUs
		List<CluInfo> cluList = new ArrayList<CluInfo>();
		CluInfo clu1 = new CluInfo("1", "MATH152", "MATH 152", "MATH 152 Linear Systems");
		cluList.add(clu1);
		CluInfo clu2 = new CluInfo("2", "MATH180", "MATH 180", "MATH 180 Differential Calculus with Physical Applications");
		cluList.add(clu2);
		CourseListContextImpl.setCluInfo(cluList);
		
		// Add CLU Sets
		List<CluSetInfo> cluSetList = new ArrayList<CluSetInfo>();
		CluSetInfo cluSet1 = new CluSetInfo("CLUSET-NL-1", cluList);
		cluSetList.add(cluSet1);
		CourseListContextImpl.setCluSetInfo(cluSetList);
	}
	
	@Test
	public void testTranslateReqComponent() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-NL-1", "KUALI.CATALOG", "en");
    	assertEquals("Student must have completed 1 of MATH 152, MATH 180", nl);
    }

	@Test
	public void testGetNaturalLanguageForReqComponent_DefaultEnglish() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		String naturalLanguage = statementService.getNaturalLanguageForReqComponent("REQCOMP-NL-1", "KUALI.CATALOG", null);
        assertEquals("Student must have completed 1 of MATH 152, MATH 180", naturalLanguage);
	}

	@Test
	public void testGetNaturalLanguageForReqComponent_EnglishGerman() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		String naturalLanguage = statementService.getNaturalLanguageForReqComponent("REQCOMP-NL-1", "KUALI.CATALOG", null);
        assertEquals("Student must have completed 1 of MATH 152, MATH 180", naturalLanguage);
		
        naturalLanguage = statementService.getNaturalLanguageForReqComponent("REQCOMP-NL-1", "KUALI.CATALOG", "de");
        assertEquals("Student muss abgeschlossen 1 von MATH 152, MATH 180", naturalLanguage);

		naturalLanguage = statementService.getNaturalLanguageForReqComponent("REQCOMP-NL-1", "KUALI.CATALOG", "en");
        assertEquals("Student must have completed 1 of MATH 152, MATH 180", naturalLanguage);
	}
}
