package org.kuali.student.common.conversion;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.kuali.student.r2.common.dto.AmountInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;

public class R1TestDataUtil {

    public static RichTextInfo getRichTextInfoData() {
        RichTextInfo r1RichText = new RichTextInfo();
        r1RichText.setPlain("R1 Plain");
        r1RichText.setFormatted("R1 Formatted");
        return r1RichText;
    }

    public static Map<String, String> getAttributeData() {
        Map<String, String> r1Attributes = new HashMap<String, String>();
        r1Attributes.put("R1-Key", "R1-Value");
        return r1Attributes;
    }

    public static MetaInfo getMetadataInfoData() {
        MetaInfo r1MetaInfo = new MetaInfo();
        r1MetaInfo.setCreateId("R1 Meta Create Id");
        r1MetaInfo.setCreateTime(new Date());
        r1MetaInfo.setUpdateId("R1 Update Id");
        r1MetaInfo.setUpdateTime(new Date());
        r1MetaInfo.setVersionInd("R1 Meta Info Version Id");
        return r1MetaInfo;
    }

    public static AmountInfo getAmountInfoData() {
        AmountInfo r1AmountInfo = new AmountInfo();
        r1AmountInfo.setUnitQuantity("R1 Unit Quantity");
        r1AmountInfo.setUnitTypeKey("R1 Unit Type");
        return r1AmountInfo;
    }

}
