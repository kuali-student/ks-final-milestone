package org.kuali.student.ap.framework.context;


import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.enumerationmanagement.dto.EnumeratedValueInfo;

import java.util.HashMap;
import java.util.List;

public interface EnumerationHelper {

    public HashMap<String, List<EnumeratedValueInfo>> getEnumServiceCache();

    public void setEnumServiceCache(
            HashMap<String, List<EnumeratedValueInfo>> enumServiceCache);

    public EnumeratedValueInfo getGenEdReqEnumInfo(String key,
                                                          ContextInfo context);

    public List<EnumeratedValueInfo> getEnumerationValueInfoList(
            String param);

    public String getEnumAbbrValForCode(String code);

    public String getEnumValForCode(String code, ContextInfo context);

    public String getEnumCodeForAbbrVal(String abbrVal) ;

}
