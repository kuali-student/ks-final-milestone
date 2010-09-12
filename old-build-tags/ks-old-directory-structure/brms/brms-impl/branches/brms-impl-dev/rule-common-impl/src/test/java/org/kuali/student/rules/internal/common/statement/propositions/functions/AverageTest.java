package org.kuali.student.rules.internal.common.statement.propositions.functions;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.rules.factfinder.dto.FactResultDTO;
import org.kuali.student.rules.factfinder.dto.FactResultTypeInfoDTO;
import org.kuali.student.rules.internal.common.utils.CommonTestUtil;

public class AverageTest {

	@Test
	public void testX() throws Exception {
	}
	
	@Test
	public void testAverage1() throws Exception {
    	FactResultTypeInfoDTO columnMetaData = CommonTestUtil.createColumnMetaData(String.class.getName(), "resultColumn.grade");

    	FactResultDTO factResult = CommonTestUtil.createFactResult(
    			new String[] {"80.0", "85.0", "90.0"}, 
    			"resultColumn.grade");
    	factResult.setFactResultTypeInfo(columnMetaData);

    	Average<Number> avg = new Average<Number>();
		avg.setFact(factResult, "resultColumn.grade");
		Number output = avg.compute();
		
		Assert.assertEquals(new BigDecimal("85.0"), output);
	}

	@Test
	public void testAverage2() throws Exception {
    	FactResultTypeInfoDTO columnMetaData = CommonTestUtil.createColumnMetaData(String.class.getName(), "resultColumn.grade");

    	FactResultDTO factResult = CommonTestUtil.createFactResult(
    			new String[] {"80.0", "85.0", "95.0"}, 
    			"resultColumn.grade");
    	factResult.setFactResultTypeInfo(columnMetaData);

    	Average<Number> avg = new Average<Number>();
		avg.setFact(factResult, "resultColumn.grade");
		Number output = avg.compute();

		BigDecimal result = new BigDecimal(output.toString());
		
		Assert.assertEquals(new BigDecimal("86.67"), result.setScale(2, RoundingMode.HALF_UP));
	}
}