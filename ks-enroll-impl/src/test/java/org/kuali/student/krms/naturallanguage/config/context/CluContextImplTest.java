package org.kuali.student.krms.naturallanguage.config.context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.rice.krms.impl.repository.TermBo;
import org.kuali.rice.krms.impl.repository.TermParameterBo;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.Daos;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.krms.naturallanguage.config.context.util.NLCluSet;
import org.kuali.student.r1.lum.statement.typekey.ReqComponentFieldTypes;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.service.CluService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//@Daos( { @Dao(value = "org.kuali.student.r2.lum.lu.dao.impl.LuDaoImpl", testSqlFile = "classpath:ks-lu.sql") })
//@PersistenceFileLocation("classpath:META-INF/lu-persistence.xml")
@Ignore
public class CluContextImplTest extends AbstractServiceTest {

    @Client(value = "org.kuali.student.r2.lum.lu.service.impl.CluServiceImpl", additionalContextFile = "classpath:clu-additional-context.xml")
    private CluService cluService;
	private CluContextImpl cluContext = new CluContextImpl();

	private TermBo term;
	private TermBo term2;
	
	
	private void setupReqComponent1() {
		term = new TermBo();
        List<TermParameterBo> parameters = new ArrayList<TermParameterBo>();
        TermParameterBo parameter1 = new TermParameterBo();
        parameter1.setName(ReqComponentFieldTypes.CLU_KEY.getId());
        parameter1.setValue("CLU-1");
        parameters.add(parameter1);
		term.setParameters(parameters);

        TermParameterBo parameter2 = new TermParameterBo();
        parameter2.setName(ReqComponentFieldTypes.COURSE_CLU_KEY.getId());
        parameter2.setValue("CLU-1");
        parameters.add(parameter2);
		term.setParameters(parameters);

        TermParameterBo parameter3 = new TermParameterBo();
        parameter3.setName(ReqComponentFieldTypes.PROGRAM_CLU_KEY.getId());
        parameter3.setValue("CLU-1");
        parameters.add(parameter3);
		term.setParameters(parameters);

        TermParameterBo parameter4 = new TermParameterBo();
        parameter4.setName(ReqComponentFieldTypes.TEST_CLU_KEY.getId());
        parameter4.setValue("CLU-1");
        parameters.add(parameter4);
		term.setParameters(parameters);
		
		TermParameterBo parameter5 = new TermParameterBo();
        parameter5.setName(ReqComponentFieldTypes.CLUSET_KEY.getId());
        parameter5.setValue("CLUSET-1");
        parameters.add(parameter5);
		term.setParameters(parameters);

		TermParameterBo parameter6 = new TermParameterBo();
        parameter6.setName(ReqComponentFieldTypes.COURSE_CLUSET_KEY.getId());
        parameter6.setValue("CLUSET-1");
        parameters.add(parameter6);
		term.setParameters(parameters);

		TermParameterBo parameter7 = new TermParameterBo();
        parameter7.setName(ReqComponentFieldTypes.PROGRAM_CLUSET_KEY.getId());
        parameter7.setValue("CLUSET-1");
        parameters.add(parameter7);
		term.setParameters(parameters);

		TermParameterBo parameter8 = new TermParameterBo();
        parameter8.setName(ReqComponentFieldTypes.TEST_CLUSET_KEY.getId());
        parameter8.setValue("CLUSET-1");
        parameters.add(parameter8);
		term.setParameters(parameters);
	}

	private void setupReqComponent2() {
		term2 = new TermBo();
        List<TermParameterBo> parameterList = new ArrayList<TermParameterBo>();
        TermParameterBo parameter1 = new TermParameterBo();
        parameter1.setName(ReqComponentFieldTypes.CLU_KEY.getId());
        parameter1.setValue(null);
        parameterList.add(parameter1);
		term2.setParameters(parameterList);

        TermParameterBo parameter2 = new TermParameterBo();
        parameter2.setName(ReqComponentFieldTypes.COURSE_CLU_KEY.getId());
        parameter2.setValue(null);
        parameterList.add(parameter2);
		term2.setParameters(parameterList);

        TermParameterBo parameter3 = new TermParameterBo();
        parameter3.setName(ReqComponentFieldTypes.PROGRAM_CLU_KEY.getId());
        parameter3.setValue(null);
        parameterList.add(parameter3);
		term2.setParameters(parameterList);

        TermParameterBo parameter4 = new TermParameterBo();
        parameter4.setName(ReqComponentFieldTypes.TEST_CLU_KEY.getId());
        parameter4.setValue(null);
        parameterList.add(parameter4);
		term2.setParameters(parameterList);
		
		TermParameterBo parameter5 = new TermParameterBo();
        parameter5.setName(ReqComponentFieldTypes.CLUSET_KEY.getId());
        parameter5.setValue(null);
        parameterList.add(parameter5);
		term2.setParameters(parameterList);

		TermParameterBo parameter6 = new TermParameterBo();
        parameter6.setName(ReqComponentFieldTypes.COURSE_CLUSET_KEY.getId());
        parameter6.setValue(null);
        parameterList.add(parameter6);
		term2.setParameters(parameterList);

		TermParameterBo parameter7 = new TermParameterBo();
        parameter7.setName(ReqComponentFieldTypes.PROGRAM_CLUSET_KEY.getId());
        parameter7.setValue(null);
        parameterList.add(parameter7);
		term2.setParameters(parameterList);

		TermParameterBo parameter8 = new TermParameterBo();
        parameter8.setName(ReqComponentFieldTypes.TEST_CLUSET_KEY.getId());
        parameter8.setValue(null);
        parameterList.add(parameter8);
		term2.setParameters(parameterList);
	}

	@Before
	public void beforeMethod() {
		cluContext.setCluService(cluService);
		setupReqComponent1();
		setupReqComponent2();
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
