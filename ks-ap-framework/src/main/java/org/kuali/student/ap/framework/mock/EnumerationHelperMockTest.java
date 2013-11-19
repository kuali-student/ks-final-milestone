package org.kuali.student.ap.framework.mock;

import org.kuali.student.ap.framework.context.EnumerationHelper;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.enumerationmanagement.dto.EnumeratedValueInfo;

import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: johglove
 * Date: 11/19/13
 * Time: 9:33 AM
 * To change this template use File | Settings | File Templates.
 */
public class EnumerationHelperMockTest implements EnumerationHelper {
    @Override
    public HashMap<String, List<EnumeratedValueInfo>> getEnumServiceCache() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Sets the cache to a set value
     */
    @Override
    public void setEnumServiceCache(HashMap<String, List<EnumeratedValueInfo>> enumServiceCache) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Gets the enumeration value info for the passed in key.  If the value is not
     * in cache it is retrieved form the database.
     *
     * @return The information found for the key
     */
    @Override
    public EnumeratedValueInfo getGenEdReqEnumInfo(String key, ContextInfo context) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Gets information for entries matching the passed in param.
     *
     * @return All entries found.
     */
    @Override
    public List<EnumeratedValueInfo> getEnumerationValueInfoList(String param) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Gets the AbbrVal for an entry with the matching code
     *
     * @return AbbrVal of the found entry
     */
    @Override
    public String getEnumAbbrValForCode(String code) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Gets the EnumVal for an entry with the matching code
     *
     * @return EnumVal of the found entry
     */
    @Override
    public String getEnumValForCode(String code, ContextInfo context) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Gets the Code for an entry with the matching abbrVal
     *
     * @return Code of the found entry
     */
    @Override
    public String getEnumCodeForAbbrVal(String abbrVal) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
