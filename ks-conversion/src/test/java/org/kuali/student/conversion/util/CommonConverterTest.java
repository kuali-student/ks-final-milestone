package org.kuali.student.conversion.util;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.common.util.R1R2ConverterUtil;
import org.kuali.student.r2.common.dto.AmountInfo;

public class CommonConverterTest {
    
    @Test
    public void testAmountInfo() {
        org.kuali.student.r1.common.dto.AmountInfo r1 = new org.kuali.student.r1.common.dto.AmountInfo();
        r1.setUnitQuantity("R1 Unit Quantity");
        r1.setUnitType("R1 Unit Type");
        AmountInfo r2 = R1R2ConverterUtil.convert(r1, AmountInfo.class);
        Assert.assertEquals(r1.getUnitQuantity(), r2.getUnitQuantity());
        Assert.assertEquals(r1.getUnitType(), r2.getUnitTypeKey());
    }

}
