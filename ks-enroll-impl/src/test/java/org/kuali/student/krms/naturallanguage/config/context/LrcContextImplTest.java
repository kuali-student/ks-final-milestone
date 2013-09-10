package org.kuali.student.krms.naturallanguage.config.context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.util.AttributeTester;
import org.kuali.student.r2.core.krms.naturallanguage.TermParameterTypes;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.lum.lrc.dto.ResultScaleInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.annotation.Resource;
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:lrc-service-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
public class LrcContextImplTest extends AbstractServiceTest {

    @Resource
    private LRCService lrcService;
    private LrcContextImpl lrcContext = new LrcContextImpl();

	private Map<String, Object> term;
	private Map<String, Object> term2;
	
	
	private void setupTerm1() {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(TermParameterTypes.GRADE_KEY.getId(),LrcServiceConstants.RESULT_VALUE_KEY_GRADE_LETTER_A);
        parameters.put(TermParameterTypes.GRADE_TYPE_KEY.getId(),LrcServiceConstants.RESULT_SCALE_KEY_GRADE_LETTER);
        term = parameters;
	}

	private void setupTerm2() {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(TermParameterTypes.GRADE_KEY.getId(),null);
        parameters.put(TermParameterTypes.GRADE_TYPE_KEY.getId(),null);
		term2 = parameters;
	}

	@Before
	public void beforeMethod() {
		lrcContext.setLrcService(lrcService);
        ContextInfo context = new ContextInfo();
        context.setPrincipalId("testUser1");
        context.setCurrentDate(new Date());
        //Create the ResultScale
        ResultScaleInfo resScale = new ResultScaleInfo();
        resScale.setKey(LrcServiceConstants.RESULT_SCALE_KEY_GRADE_LETTER);
        resScale.setTypeKey(LrcServiceConstants.RESULT_SCALE_TYPE_KEY_GRADE);
        resScale.setStateKey(LrcServiceConstants.RESULT_SCALE_STATE_APPROVED);
        resScale.setEffectiveDate(new Date());
        resScale.setExpirationDate(new Date(new Date().getTime() + 1000));
        resScale.setName("A-F Letter Graded Scale");
        resScale.setDescr(new RichTextHelper().fromPlain("Letter Graded Scale description"));
        new AttributeTester().add2ForCreate(resScale.getAttributes());


        //Create the resultValue
        ResultValueInfo reaValue = new ResultValueInfo();
        reaValue.setKey(LrcServiceConstants.RESULT_VALUE_KEY_GRADE_LETTER_A);
        reaValue.setTypeKey(LrcServiceConstants.RESULT_VALUE_TYPE_KEY_VALUE);
        reaValue.setStateKey(LrcServiceConstants.RESULT_VALUE_STATE_APPROVED);
        reaValue.setEffectiveDate(new Date());
        reaValue.setExpirationDate(new Date(new Date().getTime() + 1000));
        reaValue.setName("Grade A+");
        reaValue.setDescr(new RichTextHelper().fromPlain("value A+ description"));
        reaValue.setResultScaleKey(LrcServiceConstants.RESULT_SCALE_KEY_GRADE_LETTER);
        reaValue.setValue("A+");
        reaValue.setNumericValue("4.5");

        try {
            lrcService.createResultScale(resScale.getTypeKey(), resScale, context);
            lrcService.createResultValue(reaValue.getResultScaleKey(), reaValue.getTypeKey(), reaValue,
                    context);
        } catch (AlreadyExistsException e) {
            //Might already be inserted
        } catch (Exception e) {
            Assert.fail("Problem creating test data in the LRC Service");
        }
        setupTerm1();
		setupTerm2();
	}

	@Test
    public void testCreateContextMap_Lrc() throws OperationFailedException {
		Map<String, Object> contextMap = lrcContext.createContextMap(term);
        String grade = (String) contextMap.get(LrcContextImpl.GRADE_TOKEN);
        ResultScaleInfo gradeType = (ResultScaleInfo) contextMap.get(LrcContextImpl.GRADE_TYPE_TOKEN);

		Assert.assertNotNull(contextMap);
		Assert.assertEquals("A+", grade);
        Assert.assertEquals(LrcServiceConstants.RESULT_SCALE_KEY_GRADE_LETTER, gradeType.getKey());
	}
	
	@Test
    public void testCreateContextMap_NullTokenValues()  {
        Map<String, Object> contextMap = lrcContext.createContextMap(term2);
        String grade = (String) contextMap.get(LrcContextImpl.GRADE_TOKEN);
        ResultScaleInfo gradeType = (ResultScaleInfo) contextMap.get(LrcContextImpl.GRADE_TYPE_TOKEN);

        Assert.assertNotNull(contextMap);
        Assert.assertEquals(null, grade);
        Assert.assertEquals(null, gradeType);

	}

}
