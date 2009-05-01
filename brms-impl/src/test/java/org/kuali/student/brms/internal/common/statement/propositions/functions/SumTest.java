package org.kuali.student.brms.internal.common.statement.propositions.functions;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.brms.factfinder.dto.FactResultInfo;
import org.kuali.student.brms.factfinder.dto.FactResultTypeInfo;
import org.kuali.student.brms.internal.common.utils.CommonTestUtil;

public class SumTest {

	@Test
	public void testSum() throws Exception {
    	FactResultTypeInfo columnMetaData = CommonTestUtil.createColumnMetaData(String.class.getName(), "resultColumn.grade");

    	FactResultInfo factResult = CommonTestUtil.createFactResult(
    			new String[] {"80.0", "85.0", "90.0"}, 
    			"resultColumn.grade");
    	factResult.setFactResultTypeInfo(columnMetaData);

    	Sum<Number> sum = new Sum<Number>();
		sum.setFact(factResult, "resultColumn.grade");
		
		Number output = sum.compute();
		
		Assert.assertEquals("255.0", output.toString());
		Assert.assertEquals(new BigDecimal("255.0"), output);
	}

}