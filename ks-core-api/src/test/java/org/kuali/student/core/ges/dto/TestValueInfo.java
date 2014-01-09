package org.kuali.student.core.ges.dto;


import org.junit.Test;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.student.r2.common.dto.AmountInfo;
import org.kuali.student.r2.common.dto.CurrencyAmountInfo;
import org.kuali.student.r2.common.dto.TimeAmountInfo;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class TestValueInfo {

    @Test
    public void testBooleanValues() {
        ValueInfo value = new ValueInfo();
        value.setBooleanValue(true);

        assertTrue(value.getBooleanValue());

        value.setBooleanValue(false);
        assertFalse(value.getBooleanValue());
    }

    @Test
    public void testDateValues() {
        ValueInfo value = new ValueInfo();
        Date date = new Date();
        value.setDateValue(date);

        assertEquals(date, value.getDateValue());
    }

    @Test
    public void testNumericValues() {
        ValueInfo value = new ValueInfo();
        value.setNumericValue(123456789L);

        assertEquals(Long.valueOf(123456789L), value.getNumericValue());

        value.setNumericValue(-123456789L);

        assertEquals(Long.valueOf(-123456789L), value.getNumericValue());

        value.setNumericValue(0L);

        assertEquals(Long.valueOf(0L), value.getNumericValue());
    }

    @Test
    public void testDecimalValues() {
        ValueInfo value = new ValueInfo();
        KualiDecimal decimal = new KualiDecimal(5.5D);
        value.setDecimalValue(decimal);

        assertEquals(decimal, value.getDecimalValue());

        decimal = KualiDecimal.ZERO;
        value.setDecimalValue(decimal);

        assertEquals(decimal, value.getDecimalValue());

        decimal = new KualiDecimal(-5.05D);
        value.setDecimalValue(decimal);

        assertEquals(decimal, value.getDecimalValue());
    }

    @Test
    public void testAmountValues() {
        ValueInfo value = new ValueInfo();
        AmountInfo info = new AmountInfo();
        info.setUnitQuantity("10.5");
        info.setUnitTypeKey("kuali.org.amount.feet");
        value.setAmountValue(info);

        assertEquals(info.getUnitQuantity(), value.getAmountValue().getUnitQuantity());
        assertEquals(info.getUnitTypeKey(), value.getAmountValue().getUnitTypeKey());
    }

    @Test
    public void testCurrencyAmountValues() {
        ValueInfo value = new ValueInfo();
        CurrencyAmountInfo info = new CurrencyAmountInfo();
        info.setCurrencyQuantity(42);
        info.setCurrencyTypeKey("kuali.org.rupee");
        value.setCurrencyAmountValue(info);

        assertEquals(info.getCurrencyQuantity(), value.getCurrencyAmountValue().getCurrencyQuantity());
        assertEquals(info.getCurrencyTypeKey(), value.getCurrencyAmountValue().getCurrencyTypeKey());

        info = new CurrencyAmountInfo();
        info.setCurrencyQuantity(0);
        info.setCurrencyTypeKey("kuali.org.rupee");
        value.setCurrencyAmountValue(info);

        assertEquals(info.getCurrencyQuantity(), value.getCurrencyAmountValue().getCurrencyQuantity());
        assertEquals(info.getCurrencyTypeKey(), value.getCurrencyAmountValue().getCurrencyTypeKey());

        info.setCurrencyQuantity(-238900);
        info.setCurrencyTypeKey("kuali.org.rupee");
        value.setCurrencyAmountValue(info);

        assertEquals(info.getCurrencyQuantity(), value.getCurrencyAmountValue().getCurrencyQuantity());
        assertEquals(info.getCurrencyTypeKey(), value.getCurrencyAmountValue().getCurrencyTypeKey());
    }

    @Test
    public void testTimeAmountValues() {
        ValueInfo value = new ValueInfo();
        TimeAmountInfo info = new TimeAmountInfo();
        info.setAtpDurationTypeKey("org.kuali.time.eon");
        info.setTimeQuantity(35);

        value.setTimeAmountValue(info);
        assertEquals(info.getAtpDurationTypeKey(), value.getTimeAmountValue().getAtpDurationTypeKey());
        assertEquals(info.getTimeQuantity(), value.getTimeAmountValue().getTimeQuantity());

        info = new TimeAmountInfo();
        info.setAtpDurationTypeKey("org.kuali.time.weeks");
        info.setTimeQuantity(0);

        value.setTimeAmountValue(info);
        assertEquals(info.getAtpDurationTypeKey(), value.getTimeAmountValue().getAtpDurationTypeKey());
        assertEquals(info.getTimeQuantity(), value.getTimeAmountValue().getTimeQuantity());
    }

    @Test
    public void testTimeOfDayValues() {
        ValueInfo value = new ValueInfo();
        TimeOfDayInfo info = new TimeOfDayInfo(1,2,3);
        value.setTimeOfDayValue(info);

        assertEquals(info, value.getTimeOfDayValue());

        info = new TimeOfDayInfo(1);
        value.setTimeOfDayValue(info);

        assertEquals(info, value.getTimeOfDayValue());

        info = new TimeOfDayInfo(0,0,0);
        value.setTimeOfDayValue(info);

        assertEquals(info, value.getTimeOfDayValue());

    }

    @Test
    public void testStringValues() {
        ValueInfo value = new ValueInfo();
        value.setStringValue("testValue");

        assertEquals("testValue", value.getStringValue());

    }
}
