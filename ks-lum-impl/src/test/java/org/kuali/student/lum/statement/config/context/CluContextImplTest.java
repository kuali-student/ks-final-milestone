package org.kuali.student.lum.statement.config.context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.Daos;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.r2.lum.statement.config.context.util.NLCluSet;
import org.kuali.student.r1.core.statement.dto.ReqCompFieldInfo;
import org.kuali.student.r1.core.statement.dto.ReqComponentInfo;
import org.kuali.student.r1.lum.statement.typekey.ReqComponentFieldTypes;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.statement.config.context.CluContextImpl;

@Daos( { @Dao(value = "org.kuali.student.lum.lu.dao.impl.LuDaoImpl", testSqlFile = "classpath:ks-lu.sql") })
@PersistenceFileLocation("classpath:META-INF/lu-persistence.xml")
public class CluContextImplTest extends AbstractServiceTest {

    @Client(value = "org.kuali.student.lum.lu.service.impl.CluServiceImpl", additionalContextFile = "classpath:clu-additional-context.xml")
    private CluService cluService;
	private CluContextImpl cluContext = new CluContextImpl();

	private ReqComponentInfo reqComponent1;
	private ReqComponentInfo reqComponent2;
	
	
	private void setupReqComponent1() {
		reqComponent1 = new ReqComponentInfo();
        List<ReqCompFieldInfo> reqCompFieldList = new ArrayList<ReqCompFieldInfo>();
        ReqCompFieldInfo reqCompField1 = new ReqCompFieldInfo();
        reqCompField1.setType(ReqComponentFieldTypes.CLU_KEY.getId());
        reqCompField1.setValue("CLU-1");
        reqCompFieldList.add(reqCompField1);
		reqComponent1.setReqCompFields(reqCompFieldList);

        ReqCompFieldInfo reqCompField2 = new ReqCompFieldInfo();
        reqCompField2.setType(ReqComponentFieldTypes.COURSE_CLU_KEY.getId());
        reqCompField2.setValue("CLU-1");
        reqCompFieldList.add(reqCompField2);
		reqComponent1.setReqCompFields(reqCompFieldList);

        ReqCompFieldInfo reqCompField3 = new ReqCompFieldInfo();
        reqCompField3.setType(ReqComponentFieldTypes.PROGRAM_CLU_KEY.getId());
        reqCompField3.setValue("CLU-1");
        reqCompFieldList.add(reqCompField3);
		reqComponent1.setReqCompFields(reqCompFieldList);

        ReqCompFieldInfo reqCompField4 = new ReqCompFieldInfo();
        reqCompField4.setType(ReqComponentFieldTypes.TEST_CLU_KEY.getId());
        reqCompField4.setValue("CLU-1");
        reqCompFieldList.add(reqCompField4);
		reqComponent1.setReqCompFields(reqCompFieldList);
		
		ReqCompFieldInfo reqCompField5 = new ReqCompFieldInfo();
        reqCompField5.setType(ReqComponentFieldTypes.CLUSET_KEY.getId());
        reqCompField5.setValue("CLUSET-1");
        reqCompFieldList.add(reqCompField5);
		reqComponent1.setReqCompFields(reqCompFieldList);

		ReqCompFieldInfo reqCompField6 = new ReqCompFieldInfo();
        reqCompField6.setType(ReqComponentFieldTypes.COURSE_CLUSET_KEY.getId());
        reqCompField6.setValue("CLUSET-1");
        reqCompFieldList.add(reqCompField6);
		reqComponent1.setReqCompFields(reqCompFieldList);

		ReqCompFieldInfo reqCompField7 = new ReqCompFieldInfo();
        reqCompField7.setType(ReqComponentFieldTypes.PROGRAM_CLUSET_KEY.getId());
        reqCompField7.setValue("CLUSET-1");
        reqCompFieldList.add(reqCompField7);
		reqComponent1.setReqCompFields(reqCompFieldList);

		ReqCompFieldInfo reqCompField8 = new ReqCompFieldInfo();
        reqCompField8.setType(ReqComponentFieldTypes.TEST_CLUSET_KEY.getId());
        reqCompField8.setValue("CLUSET-1");
        reqCompFieldList.add(reqCompField8);
		reqComponent1.setReqCompFields(reqCompFieldList);
	}

	private void setupReqComponent2() {
		reqComponent2 = new ReqComponentInfo();
        List<ReqCompFieldInfo> reqCompFieldList = new ArrayList<ReqCompFieldInfo>();
        ReqCompFieldInfo reqCompField1 = new ReqCompFieldInfo();
        reqCompField1.setType(ReqComponentFieldTypes.CLU_KEY.getId());
        reqCompField1.setValue(null);
        reqCompFieldList.add(reqCompField1);
		reqComponent2.setReqCompFields(reqCompFieldList);

        ReqCompFieldInfo reqCompField2 = new ReqCompFieldInfo();
        reqCompField2.setType(ReqComponentFieldTypes.COURSE_CLU_KEY.getId());
        reqCompField2.setValue(null);
        reqCompFieldList.add(reqCompField2);
		reqComponent2.setReqCompFields(reqCompFieldList);

        ReqCompFieldInfo reqCompField3 = new ReqCompFieldInfo();
        reqCompField3.setType(ReqComponentFieldTypes.PROGRAM_CLU_KEY.getId());
        reqCompField3.setValue(null);
        reqCompFieldList.add(reqCompField3);
		reqComponent2.setReqCompFields(reqCompFieldList);

        ReqCompFieldInfo reqCompField4 = new ReqCompFieldInfo();
        reqCompField4.setType(ReqComponentFieldTypes.TEST_CLU_KEY.getId());
        reqCompField4.setValue(null);
        reqCompFieldList.add(reqCompField4);
		reqComponent2.setReqCompFields(reqCompFieldList);
		
		ReqCompFieldInfo reqCompField5 = new ReqCompFieldInfo();
        reqCompField5.setType(ReqComponentFieldTypes.CLUSET_KEY.getId());
        reqCompField5.setValue(null);
        reqCompFieldList.add(reqCompField5);
		reqComponent2.setReqCompFields(reqCompFieldList);

		ReqCompFieldInfo reqCompField6 = new ReqCompFieldInfo();
        reqCompField6.setType(ReqComponentFieldTypes.COURSE_CLUSET_KEY.getId());
        reqCompField6.setValue(null);
        reqCompFieldList.add(reqCompField6);
		reqComponent2.setReqCompFields(reqCompFieldList);

		ReqCompFieldInfo reqCompField7 = new ReqCompFieldInfo();
        reqCompField7.setType(ReqComponentFieldTypes.PROGRAM_CLUSET_KEY.getId());
        reqCompField7.setValue(null);
        reqCompFieldList.add(reqCompField7);
		reqComponent2.setReqCompFields(reqCompFieldList);

		ReqCompFieldInfo reqCompField8 = new ReqCompFieldInfo();
        reqCompField8.setType(ReqComponentFieldTypes.TEST_CLUSET_KEY.getId());
        reqCompField8.setValue(null);
        reqCompFieldList.add(reqCompField8);
		reqComponent2.setReqCompFields(reqCompFieldList);
	}

	@Before
	public void beforeMethod() {
		cluContext.setCluService(cluService);
		setupReqComponent1();
		setupReqComponent2();
	}

	@Test
    public void testCreateContextMap_Clu() throws OperationFailedException {
		Map<String, Object> contextMap = cluContext.createContextMap(reqComponent1, ContextUtils.getContextInfo());
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
		Map<String, Object> contextMap = cluContext.createContextMap(reqComponent1, ContextUtils.getContextInfo());
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
		Map<String, Object> contextMap = cluContext.createContextMap(reqComponent2, ContextUtils.getContextInfo());
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
