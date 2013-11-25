package org.kuali.student.ap.test.mock;

import org.kuali.student.ap.framework.context.EnumerationHelper;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.enumerationmanagement.dto.EnumeratedValueInfo;

import java.util.ArrayList;
import java.util.Date;
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

    private HashMap<String, List<EnumeratedValueInfo>> enumServiceCache;
    private final String GEN_EDU_REQ_KEY = "kuali.lu.genedreq";

    @Override
    public HashMap<String, List<EnumeratedValueInfo>> getEnumServiceCache() {
        if (this.enumServiceCache == null) {
            this.enumServiceCache = new HashMap<String, List<EnumeratedValueInfo>>();
            List<EnumeratedValueInfo> genEds = new ArrayList<EnumeratedValueInfo>();
            genEds.add(createEnumeratedValue("ABC", "ABC", "Test ABC", "000", "kuali.enum.type.test", null, null));
            genEds.add(createEnumeratedValue("XYZ", "XYZ", "Test XYZ", "000", "kuali.enum.type.test", null, null));
            this.enumServiceCache.put(GEN_EDU_REQ_KEY, genEds);
        }
        return enumServiceCache;
    }

    private EnumeratedValueInfo createEnumeratedValue(String code, String abbrevValue, String value, String sortKey, String enumerationKey, Date effectiveDate, Date expirationDate){
        EnumeratedValueInfo newEnum = new EnumeratedValueInfo();
        newEnum.setCode(code);
        newEnum.setAbbrevValue(abbrevValue);
        newEnum.setValue(value);
        newEnum.setSortKey(sortKey);
        newEnum.setEnumerationKey(enumerationKey);
        newEnum.setEffectiveDate(effectiveDate);
        newEnum.setExpirationDate(expirationDate);
        return newEnum;
    }

    @Override
    public void setEnumServiceCache(HashMap<String, List<EnumeratedValueInfo>> enumServiceCache) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public EnumeratedValueInfo getGenEdReqEnumInfo(String key, ContextInfo context) {
        EnumeratedValueInfo enumValueInfo = null;
        try {
            List<EnumeratedValueInfo> enumeratedValueInfoList = null;
            enumeratedValueInfoList = getEnumServiceCache().get(
                        GEN_EDU_REQ_KEY);
            for (EnumeratedValueInfo enumVal : enumeratedValueInfoList) {
                String enumCode = enumVal.getCode();
                if (enumCode.equalsIgnoreCase(key)) {
                    enumValueInfo = enumVal;
                    break;
                }
            }
        } catch (Exception e) {
        }
        return enumValueInfo;
    }

    @Override
    public List<EnumeratedValueInfo> getEnumerationValueInfoList(String param) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getEnumAbbrValForCode(String code) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getEnumValForCode(String code, ContextInfo context) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getEnumCodeForAbbrVal(String abbrVal) {
        String enumCode = null;
        try {

            List<EnumeratedValueInfo> enumeratedValueInfoList = null;
            enumeratedValueInfoList = getEnumServiceCache().get(GEN_EDU_REQ_KEY);
            for (EnumeratedValueInfo enumVal : enumeratedValueInfoList) {
                String enumAbbrVal = enumVal.getAbbrevValue();
                if (enumAbbrVal.equalsIgnoreCase(abbrVal)) {
                    enumCode = enumVal.getCode();
                    break;
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load genEdReqValue", e);
        }
        return enumCode;
    }
}
