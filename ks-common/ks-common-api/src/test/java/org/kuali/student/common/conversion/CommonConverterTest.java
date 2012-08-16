package org.kuali.student.common.conversion;

import java.util.Date;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.student.common.conversion.util.R1R2ConverterUtil;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.messages.dto.MessageInfo;

public class CommonConverterTest {
    
    @Test
    public void testStatusInfo() {
        org.kuali.student.r1.common.dto.StatusInfo r1 = new org.kuali.student.r1.common.dto.StatusInfo();
        r1.setMessage("R1 Message");
        r1.setSuccess(true);
        StatusInfo r2 = R1R2ConverterUtil.convert(r1, StatusInfo.class);
        Assert.assertEquals(r1.getMessage(), r2.getMessage());
        Assert.assertEquals(r1.getSuccess(), r2.getIsSuccess());
    }

    @Test
    public void testTypeInfo() {
        org.kuali.student.r1.common.dto.TypeInfo r1 = new org.kuali.student.r1.common.dto.TypeInfo() {
                };
        r1.setId("R1 Id");
        r1.setName("R1 Name");
        r1.setDescr("R1 Descr");
        r1.setEffectiveDate(new Date());
        r1.setExpirationDate(new Date());
        r1.setAttributes(R1TestDataUtil.getAttributeData());
        TypeInfo r2 = R1R2ConverterUtil.convert(r1, TypeInfo.class);
        Assert.assertEquals(r1.getId(), r2.getKey());
        Assert.assertEquals(r1.getName(), r2.getName());
        Assert.assertEquals(r1.getDescr(), r2.getDescr().getPlain());
        Assert.assertEquals(r1.getEffectiveDate(), r2.getEffectiveDate());
        Assert.assertEquals(r1.getExpirationDate(), r2.getExpirationDate());
        Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
        //R1 Object has no matching properties for these:
        //r2.setRefObjectUri(refObjectUri)
        //r2.setMeta(metaInfo)
    }

    @Ignore
    @Test
    public void testMessageInfo() {
        org.kuali.student.r1.common.messages.dto.Message r1 = new org.kuali.student.r1.common.messages.dto.Message();
        r1.setId("R1 Message Id");
        r1.setGroupName("R1 Group Name");
        r1.setLocale("R1 Locale");
        r1.setValue("R1 Value");
        MessageInfo r2 = R1R2ConverterUtil.convert(r1, MessageInfo.class);
        Assert.assertEquals(r1.getId(), r2.getKey());
        Assert.assertEquals(r1.getGroupName(), r2.getGroupName());
        Assert.assertEquals(r1.getLocale(), r2.getLocale().getLocaleLanguage());
        Assert.assertEquals(r1.getValue(), r2.getValue());
    }
    
}
