package org.kuali.student.krms.naturallanguage.config.context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.rice.krms.api.repository.term.TermDefinitionContract;
import org.kuali.rice.krms.api.repository.term.TermParameterDefinitionContract;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.enrollment.academicrecord.dto.GPAInfo;
import org.kuali.student.krms.naturallanguage.KRMSDataGenerator;
import org.kuali.student.krms.naturallanguage.TermParameterTypes;
import org.kuali.student.r1.lum.statement.typekey.ReqComponentFieldTypes;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//@Daos( { @Dao(value = "org.kuali.student.r2.lum.lu.dao.impl.LuDaoImpl", testSqlFile = "classpath:ks-lu.sql") })
//@PersistenceFileLocation("classpath:META-INF/lu-persistence.xml")
@Ignore
public class LrcContextImplTest extends AbstractServiceTest {

    @Client(value = "org.kuali.student.r2.lum.lrc.service.impl.LRCServiceImpl", additionalContextFile = "classpath:nl-test-context.xml")
    private LRCService lrcService;
    private LrcContextImpl lrcContext = new LrcContextImpl();

	private TermDefinitionContract term;
	private TermDefinitionContract term2;
	
	
	private void setupTerm1() {
        List<TermParameterDefinitionContract> parameterList = new ArrayList<TermParameterDefinitionContract>();
        parameterList.add(KRMSDataGenerator.createTermParameterDefinition(null,TermParameterTypes.GRADE_KEY.getId(),"Credit 1",null,0L));
        parameterList.add(KRMSDataGenerator.createTermParameterDefinition(null,TermParameterTypes.GRADE_TYPE_KEY.getId(),"kuali.result.value.type.value",null,0L));
        term = KRMSDataGenerator.createTermDefinition(null,null,parameterList,null,0L);
	}

	private void setupTerm2() {
        List<TermParameterDefinitionContract> parameterList = new ArrayList<TermParameterDefinitionContract>();
        parameterList.add(KRMSDataGenerator.createTermParameterDefinition(null,TermParameterTypes.GRADE_KEY.getId(),null,null,0L));
        parameterList.add(KRMSDataGenerator.createTermParameterDefinition(null,TermParameterTypes.GRADE_TYPE_KEY.getId(),null,null,0L));
		term2 = KRMSDataGenerator.createTermDefinition(null,null,parameterList,null,0L);
	}

	@Before
	public void beforeMethod() {
		lrcContext.setLrcService(lrcService);
		setupTerm1();
		setupTerm2();
	}

	@Test
    public void testCreateContextMap_Lrc() throws OperationFailedException {
		Map<String, Object> contextMap = lrcContext.createContextMap(term, ContextUtils.getContextInfo());
        ResultValueInfo grade = (ResultValueInfo) contextMap.get(LrcContextImpl.GRADE_TOKEN);
        ResultValueInfo gradeType = (ResultValueInfo) contextMap.get(LrcContextImpl.GRADE_TYPE_TOKEN);

		Assert.assertNotNull(contextMap);
		Assert.assertEquals("Credit 1", grade.getValue());
        Assert.assertEquals("kuali.result.value.type.value", gradeType.getResultScaleKey());

//		Assert.assertEquals("kuali.lu.type.CreditCourse", clu.getTypeKey());
//		Assert.assertEquals("Chem 123", clu.getOfficialIdentifier().getShortName());
//		Assert.assertEquals("Chemistry 123", clu.getOfficialIdentifier().getLongName());
	}
	
	@Test
    public void testCreateContextMap_NullTokenValues() throws OperationFailedException {
        Map<String, Object> contextMap = lrcContext.createContextMap(term2, ContextUtils.getContextInfo());
        ResultValueInfo grade = (ResultValueInfo) contextMap.get(LrcContextImpl.GRADE_TOKEN);
        ResultValueInfo gradeType = (ResultValueInfo) contextMap.get(LrcContextImpl.GRADE_TYPE_TOKEN);

        Assert.assertNotNull(contextMap);
        Assert.assertEquals(null, grade.getValue());
        Assert.assertEquals(null, gradeType.getResultScaleKey());

	}

}
