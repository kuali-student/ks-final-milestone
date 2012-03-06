package org.kuali.student.conversion.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.kuali.student.r1.common.dto.RichTextInfo;
import org.kuali.student.r1.common.dto.MetaInfo;
import org.kuali.student.common.versionmanagement.dto.VersionInfo;

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
    
    public static VersionInfo getVersionInfoData() {
        VersionInfo r1VersionInfo = new VersionInfo();
        r1VersionInfo.setVersionIndId("R1 Version Ind Id");
        r1VersionInfo.setVersionedFromId("R1 Versioned From Id");
        r1VersionInfo.setVersionComment("R1 Version Comment");
        r1VersionInfo.setSequenceNumber(Long.parseLong("1"));
        r1VersionInfo.setCurrentVersionStart(new Date());
        r1VersionInfo.setCurrentVersionEnd(new Date());
        return r1VersionInfo;
    }

}
