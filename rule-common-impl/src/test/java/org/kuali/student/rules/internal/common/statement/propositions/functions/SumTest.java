package org.kuali.student.rules.internal.common.statement.propositions.functions;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.rules.factfinder.dto.FactResultDTO;
import org.kuali.student.rules.factfinder.dto.FactResultTypeInfoDTO;
import org.kuali.student.rules.internal.common.utils.CommonTestUtil;

public class SumTest {

	@Test
	public void testSum() throws Exception {
    	FactResultTypeInfoDTO columnMetaData = CommonTestUtil.createColumnMetaData(String.class.getName(), "resultColumn.grade");

    	FactResultDTO factResult = CommonTestUtil.createFactResult(
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