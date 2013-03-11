package org.kuali.student.krms.naturallanguage.config.context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.rice.krms.api.repository.term.TermDefinitionContract;
import org.kuali.rice.krms.api.repository.term.TermParameterDefinitionContract;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.Daos;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.krms.naturallanguage.KRMSDataGenerator;
import org.kuali.student.r2.core.krms.naturallanguage.TermParameterTypes;
import org.kuali.student.krms.naturallanguage.config.context.util.NLCluSet;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.service.CluService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Daos( { @Dao(value = "org.kuali.student.r2.lum.lu.dao.impl.LuDaoImpl", testSqlFile = "classpath:ks-lu.sql") })
@PersistenceFileLocation("classpath:META-INF/lu-persistence.xml")
public class CluContextImplTest extends AbstractServiceTest {

    @Client(value = "org.kuali.student.r2.lum.lu.service.impl.CluServiceImpl", additionalContextFile = "classpath:nl-test-context.xml")
    private CluService cluService;
	private CluContextImpl cluContext = new CluContextImpl();

	private TermDefinitionContract term;
	private TermDefinitionContract term2;
	
	
	private void setupTerm1() {
        List<TermParameterDefinitionContract> parameterList = new ArrayList<TermParameterDefinitionContract>();
        parameterList.add(KRMSDataGenerator.createTermParameterDefinition(null,TermParameterTypes.CLU_KEY.getId(),"CLU-1",null,0L));
        parameterList.add(KRMSDataGenerator.createTermParameterDefinition(null,TermParameterTypes.COURSE_CLU_KEY.getId(),"CLU-1",null,0L));
        parameterList.add(KRMSDataGenerator.createTermParameterDefinition(null,TermParameterTypes.PROGRAM_CLU_KEY.getId(),"CLU-1",null,0L));
        parameterList.add(KRMSDataGenerator.createTermParameterDefinition(null,TermParameterTypes.TEST_CLU_KEY.getId(),"CLU-1",null,0L));
        parameterList.add(KRMSDataGenerator.createTermParameterDefinition(null,TermParameterTypes.CLUSET_KEY.getId(),"CLUSET-1",null,0L));
        parameterList.add(KRMSDataGenerator.createTermParameterDefinition(null,TermParameterTypes.COURSE_CLUSET_KEY.getId(),"CLUSET-1",null,0L));
        parameterList.add(KRMSDataGenerator.createTermParameterDefinition(null,TermParameterTypes.PROGRAM_CLUSET_KEY.getId(),"CLUSET-1",null,0L));
        parameterList.add(KRMSDataGenerator.createTermParameterDefinition(null,TermParameterTypes.TEST_CLUSET_KEY.getId(),"CLUSET-1",null,0L));
        term = KRMSDataGenerator.createTermDefinition(null,null,parameterList,null,0L);
	}

	private void setupTerm2() {
        List<TermParameterDefinitionContract> parameterList = new ArrayList<TermParameterDefinitionContract>();
        parameterList.add(KRMSDataGenerator.createTermParameterDefinition(null,TermParameterTypes.CLU_KEY.getId(),null,null,0L));
        parameterList.add(KRMSDataGenerator.createTermParameterDefinition(null,TermParameterTypes.COURSE_CLU_KEY.getId(),null,null,0L));
        parameterList.add(KRMSDataGenerator.createTermParameterDefinition(null,TermParameterTypes.PROGRAM_CLU_KEY.getId(),null,null,0L));
        parameterList.add(KRMSDataGenerator.createTermParameterDefinition(null,TermParameterTypes.TEST_CLU_KEY.getId(),null,null,0L));
        parameterList.add(KRMSDataGenerator.createTermParameterDefinition(null,TermParameterTypes.CLUSET_KEY.getId(),null,null,0L));
        parameterList.add(KRMSDataGenerator.createTermParameterDefinition(null,TermParameterTypes.COURSE_CLUSET_KEY.getId(),null,null,0L));
        parameterList.add(KRMSDataGenerator.createTermParameterDefinition(null,TermParameterTypes.PROGRAM_CLUSET_KEY.getId(),null,null,0L));
        parameterList.add(KRMSDataGenerator.createTermParameterDefinition(null,TermParameterTypes.TEST_CLUSET_KEY.getId(),null,null,0L));
		term2 = KRMSDataGenerator.createTermDefinition(null,null,parameterList,null,0L);
	}

	@Before
	public void beforeMethod() {
		cluContext.setCluService(cluService);
		setupTerm1();
		setupTerm2();
	}

	@Test
    public void testCreateContextMap_Clu() throws OperationFailedException {
		Map<String, Object> contextMap = cluContext.createContextMap(term, ContextUtils.getContextInfo());
		CluInfo clu = (CluInfo) contextMap.get(CluContextImpl.CLU_TOKEN);
		CluInfo courseClu = (CluInfo) contextMap.get(CluContextImpl.COURSE_CLU_TOKEN);
		CluInfo programClu = (CluInfo) contextMap.get(CluContextImpl.PROGRAM_CLU_TOKEN);
		CluInfo testClu = (CluInfo) contextMap.get(CluContextImpl.TEST_CLU_TOKEN);

		Assert.assertNotNull(contextMap);
		Assert.assertEquals("CLU-1", clu.getId());
		Assert.assertEquals("CLU-1", courseClu.getId());
		Assert.assertEquals("CLU-1", programClu.getId());
		Assert.assertEquals("CLU-1", testClu.getId());

		Assert.assertEquals("kuali.lu.type.CreditCourse", clu.getTypeKey());
		Assert.assertEquals("Chem 123", clu.getOfficialIdentifier().getShortName());
		Assert.assertEquals("Chemistry 123", clu.getOfficialIdentifier().getLongName());
	}

	@Test
    public void testCreateContextMap_CluSet() throws OperationFailedException {
		Map<String, Object> contextMap = cluContext.createContextMap(term, ContextUtils.getContextInfo());
		NLCluSet cluSet = (NLCluSet) contextMap.get(CluContextImpl.CLU_SET_TOKEN);
		NLCluSet courseCluSet = (NLCluSet) contextMap.get(CluContextImpl.COURSE_CLU_SET_TOKEN);
		NLCluSet programCluSet = (NLCluSet) contextMap.get(CluContextImpl.PROGRAM_CLU_SET_TOKEN);
		NLCluSet testCluSet = (NLCluSet) contextMap.get(CluContextImpl.TEST_CLU_SET_TOKEN);

		
		Assert.assertNotNull(contextMap);
		Assert.assertEquals("CLUSET-1", cluSet.getCluSetId());
		Assert.assertEquals("CLUSET-1", courseCluSet.getCluSetId());
		Assert.assertEquals("CLUSET-1", programCluSet.getCluSetId());
		Assert.assertEquals("CLUSET-1", testCluSet.getCluSetId());

		Assert.assertEquals("(CHEM456, CHEM456)", cluSet.getCluSetAsCode());
		Assert.assertEquals("(Chem 456, Chem 456)", cluSet.getCluSetAsShortName());
		Assert.assertEquals("(Chemistry 456, Chemistry 456)", cluSet.getCluSetAsLongName());

		Assert.assertEquals("CHEM456", cluSet.getCluAsCode(0));
		Assert.assertEquals("Chem 456", cluSet.getCluAsShortName(0));
		Assert.assertEquals("CHEM456", cluSet.getCluAsCode(1));
		Assert.assertEquals("Chem 456", cluSet.getCluAsShortName(1));
	}
	
	@Test
    public void testCreateContextMap_NullTokenValues() throws OperationFailedException {
		Map<String, Object> contextMap = cluContext.createContextMap(term2, ContextUtils.getContextInfo());
		CluInfo clu = (CluInfo) contextMap.get(CluContextImpl.CLU_TOKEN);
		CluInfo courseClu = (CluInfo) contextMap.get(CluContextImpl.COURSE_CLU_TOKEN);
		CluInfo programClu = (CluInfo) contextMap.get(CluContextImpl.PROGRAM_CLU_TOKEN);
		CluInfo testClu = (CluInfo) contextMap.get(CluContextImpl.TEST_CLU_TOKEN);
		NLCluSet cluSet = (NLCluSet) contextMap.get(CluContextImpl.CLU_SET_TOKEN);
		NLCluSet courseCluSet = (NLCluSet) contextMap.get(CluContextImpl.COURSE_CLU_SET_TOKEN);
		NLCluSet programCluSet = (NLCluSet) contextMap.get(CluContextImpl.PROGRAM_CLU_SET_TOKEN);
		NLCluSet testCluSet = (NLCluSet) contextMap.get(CluContextImpl.TEST_CLU_SET_TOKEN);

		Assert.assertNotNull(contextMap);
		Assert.assertEquals(null, clu);
		Assert.assertEquals(null, courseClu);
		Assert.assertEquals(null, programClu);
		Assert.assertEquals(null, testClu);
		Assert.assertEquals(null, cluSet);
		Assert.assertEquals(null, courseCluSet);
		Assert.assertEquals(null, programCluSet);
		Assert.assertEquals(null, testCluSet);

	}

}
