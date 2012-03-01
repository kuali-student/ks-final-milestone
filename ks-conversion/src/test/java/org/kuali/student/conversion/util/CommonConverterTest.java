package org.kuali.student.conversion.util;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.r2.common.dto.AmountInfo;
import org.kuali.student.r2.common.dto.CurrencyAmountInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.messages.dto.MessageInfo;

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
    
    @Test
    public void testCurrencyAmountInfo() {
        org.kuali.student.r1.common.dto.CurrencyAmountInfo r1 = new org.kuali.student.r1.common.dto.CurrencyAmountInfo();
        org.kuali.student.r1.common.dto.MetaInfo r1MetaInfo = new org.kuali.student.r1.common.dto.MetaInfo();
        r1MetaInfo.setVersionInd("R1 Version Ind");
        r1MetaInfo.setCreateTime(new Date());
        r1.setMetaInfo(r1MetaInfo);
        CurrencyAmountInfo r2 = R1R2ConverterUtil.convert(r1, CurrencyAmountInfo.class);
        Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
        Assert.assertEquals(r1.getMetaInfo().getCreateTime(), r2.getMeta().getCreateTime());
    }
    
    @Test
    public void testStatusInfo() {
        org.kuali.student.r1.common.dto.StatusInfo r1 = new org.kuali.student.r1.common.dto.StatusInfo();
        r1.setSuccess(true);
        StatusInfo r2 = R1R2ConverterUtil.convert(r1, StatusInfo.class);
        Assert.assertEquals(r1.getSuccess(), r2.getIsSuccess());
    }
    
    @Test
    public void testMessageInfo() {
        org.kuali.student.r1.common.messages.dto.Message r1 = new org.kuali.student.r1.common.messages.dto.Message();
        r1.setId("R1 Message Id");
        MessageInfo r2 = R1R2ConverterUtil.convert(r1, MessageInfo.class);
        Assert.assertEquals(r1.getId(), r2.getKey());
    }

}
