package org.kuali.student.ap.framework.context;


import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.enumerationmanagement.dto.EnumeratedValueInfo;

import java.util.HashMap;
import java.util.List;

public interface EnumerationHelper {

    public HashMap<String, List<EnumeratedValueInfo>> getEnumServiceCache();

    /**
     * Sets the cache to a set value
     */
    public void setEnumServiceCache(
            HashMap<String, List<EnumeratedValueInfo>> enumServiceCache);

    /**
     * Gets the enumeration value info for the passed in key.  If the value is not
     * in cache it is retrieved form the database.
     *
     * @return The information found for the key
     */
    public EnumeratedValueInfo getGenEdReqEnumInfo(String key,
                                                          ContextInfo context);

    /**
     * Gets information for entries matching the passed in param.
     *
     * @return All entries found.
     */
    public List<EnumeratedValueInfo> getEnumerationValueInfoList(
            String param);

    /**
     * Gets the AbbrVal for an entry with the matching code
     *
     * @return AbbrVal of the found entry
     */
    public String getEnumAbbrValForCode(String code);

    /**
     * Gets the EnumVal for an entry with the matching code
     *
     * @return EnumVal of the found entry
     */
    public String getEnumValForCode(String code, ContextInfo context);

    /**
     * Gets the Code for an entry with the matching abbrVal
     *
     * @return Code of the found entry
     */
    public String getEnumCodeForAbbrVal(String abbrVal) ;

}
