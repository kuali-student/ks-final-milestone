package org.kuali.student.krms.naturallanguage.config.context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.Daos;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.r2.core.krms.naturallanguage.TermParameterTypes;
import org.kuali.student.krms.naturallanguage.config.context.util.NLCluSet;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.service.CluService;

import java.util.HashMap;
import java.util.Map;

@Daos( { @Dao(value = "org.kuali.student.r2.lum.lu.dao.impl.LuDaoImpl", testSqlFile = "classpath:ks-lu.sql") })
@PersistenceFileLocation("classpath:META-INF/lu-persistence.xml")
public class CluContextImplTest extends AbstractServiceTest {

    @Client(value = "org.kuali.student.r2.lum.lu.service.impl.CluServiceImpl", additionalContextFile = "classpath:nl-test-context.xml")
    private CluService cluService;
	private CluContextImpl cluContext = new CluContextImpl();

	private Map<String, Object> term;
	private Map<String, Object> term2;
	
	
	private void setupTerm1() {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(TermParameterTypes.CLU_KEY.getId(),"CLU-1");
        parameters.put(TermParameterTypes.COURSE_CLU_KEY.getId(),"CLU-1");
        parameters.put(TermParameterTypes.PROGRAM_CLU_KEY.getId(),"CLU-1");
        parameters.put(TermParameterTypes.TEST_CLU_KEY.getId(),"CLU-1");
        parameters.put(TermParameterTypes.CLUSET_KEY.getId(),"CLUSET-1");
        parameters.put(TermParameterTypes.COURSE_CLUSET_KEY.getId(),"CLUSET-1");
        parameters.put(TermParameterTypes.PROGRAM_CLUSET_KEY.getId(),"CLUSET-1");
        parameters.put(TermParameterTypes.TEST_CLUSET_KEY.getId(),"CLUSET-1");
        term = parameters;
	}

	private void setupTerm2() {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(TermParameterTypes.CLU_KEY.getId(),null);
        parameters.put(TermParameterTypes.COURSE_CLU_KEY.getId(),null);
        parameters.put(TermParameterTypes.PROGRAM_CLU_KEY.getId(),null);
        parameters.put(TermParameterTypes.TEST_CLU_KEY.getId(),null);
        parameters.put(TermParameterTypes.CLUSET_KEY.getId(),null);
        parameters.put(TermParameterTypes.COURSE_CLUSET_KEY.getId(),null);
        parameters.put(TermParameterTypes.PROGRAM_CLUSET_KEY.getId(),null);
        parameters.put(TermParameterTypes.TEST_CLUSET_KEY.getId(),null);
		term2 = parameters;
	}

	@Before
	public void beforeMethod() {
		cluContext.setCluService(cluService);
		setupTerm1();
		setupTerm2();
	}

	@Test
    public void testCreateContextMap_Clu()  {
		Map<String, Object> contextMap = cluContext.createContextMap(term);
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
    public void testCreateContextMap_CluSet() {
		Map<String, Object> contextMap = cluContext.createContextMap(term);
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
    public void testCreateContextMap_NullTokenValues()  {
		Map<String, Object> contextMap = cluContext.createContextMap(term2);
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
