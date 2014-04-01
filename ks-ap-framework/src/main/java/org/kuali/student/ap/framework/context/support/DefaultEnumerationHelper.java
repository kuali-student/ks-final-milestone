package org.kuali.student.ap.framework.context.support;

import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.EnumerationHelper;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.enumerationmanagement.dto.EnumeratedValueInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

public class DefaultEnumerationHelper implements EnumerationHelper{


    private static final Logger logger = LoggerFactory.getLogger(DefaultEnumerationHelper.class);
    private HashMap<String, List<EnumeratedValueInfo>> enumServiceCache;

    private final String GEN_EDU_REQ_KEY = "kuali.lu.genedreq";


    @Override
    public HashMap<String, List<EnumeratedValueInfo>> getEnumServiceCache() {
        if (this.enumServiceCache == null) {
            this.enumServiceCache = new HashMap<String, List<EnumeratedValueInfo>>();
        }
        return enumServiceCache;
    }

    @Override
    public void setEnumServiceCache(
            HashMap<String, List<EnumeratedValueInfo>> enumServiceCache) {
        this.enumServiceCache = enumServiceCache;
    }

    @Override
    public EnumeratedValueInfo getGenEdReqEnumInfo(String key,
                                                          ContextInfo context) {
        EnumeratedValueInfo enumValueInfo = null;
        try {
            List<EnumeratedValueInfo> enumeratedValueInfoList = null;
            if (!getEnumServiceCache().containsKey(GEN_EDU_REQ_KEY)) {
                enumeratedValueInfoList = getEnumerationValueInfoList(GEN_EDU_REQ_KEY);
            } else {
                enumeratedValueInfoList = getEnumServiceCache().get(
                        GEN_EDU_REQ_KEY);
            }
            for (EnumeratedValueInfo enumVal : enumeratedValueInfoList) {
                String enumCode = enumVal.getCode();
                if (enumCode.equalsIgnoreCase(key)) {
                    enumValueInfo = enumVal;
                    break;
                }
            }
        } catch (Exception e) {
            logger.error("Could not load genEdReqValue");
        }
        return enumValueInfo;
    }

    @Override
    public List<EnumeratedValueInfo> getEnumerationValueInfoList(
            String param) {
        List<EnumeratedValueInfo> enumeratedValueInfoList = null;
        try {
            enumeratedValueInfoList = KsapFrameworkServiceLocator
                    .getEnumerationManagementService().getEnumeratedValues(
                            param,
                            null,
                            null,
                            null,
                            KsapFrameworkServiceLocator.getContext()
                                    .getContextInfo());
            getEnumServiceCache().put(param, enumeratedValueInfoList);
        } catch (Exception e) {
            logger.error("No Values for campuses found", e);
        }
        return enumeratedValueInfoList;
    }

    @Override
    public String getEnumAbbrValForCode(String code) {
        String enumAbbrValue = null;
        try {

            List<EnumeratedValueInfo> enumeratedValueInfoList = null;
            if (!getEnumServiceCache().containsKey(GEN_EDU_REQ_KEY)) {
                enumeratedValueInfoList = getEnumerationValueInfoList(GEN_EDU_REQ_KEY);
            } else {
                enumeratedValueInfoList = getEnumServiceCache().get(
                        GEN_EDU_REQ_KEY);
            }
            for (EnumeratedValueInfo enumVal : enumeratedValueInfoList) {
                String enumCode = enumVal.getCode();
                if (enumCode.equalsIgnoreCase(code)) {
                    enumAbbrValue = enumVal.getAbbrevValue();
                    break;
                }
            }
        } catch (Exception e) {
            logger.error("Could not load genEdReqValue", e);
        }
        return enumAbbrValue;

    }

    @Override
    public String getEnumValForCode(String code, ContextInfo context) {
        String enumAbbrValue = null;
        try {

            List<EnumeratedValueInfo> enumeratedValueInfoList = null;
            if (!getEnumServiceCache().containsKey(GEN_EDU_REQ_KEY)) {
                enumeratedValueInfoList = getEnumerationValueInfoList(GEN_EDU_REQ_KEY);
            } else {
                enumeratedValueInfoList = getEnumServiceCache().get(
                        GEN_EDU_REQ_KEY);
            }
            for (EnumeratedValueInfo enumVal : enumeratedValueInfoList) {
                String enumCode = enumVal.getCode();
                if (enumCode.equalsIgnoreCase(code)) {
                    enumAbbrValue = enumVal.getValue();
                    break;
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load genEdReqValue", e);
        }
        return enumAbbrValue;

    }

    @Override
    public String getEnumCodeForAbbrVal(String abbrVal) {
        String enumCode = null;
        try {

            List<EnumeratedValueInfo> enumeratedValueInfoList = null;
            if (!getEnumServiceCache().containsKey(GEN_EDU_REQ_KEY)) {
                enumeratedValueInfoList = getEnumerationValueInfoList(GEN_EDU_REQ_KEY);
            } else {
                enumeratedValueInfoList = getEnumServiceCache().get(
                        GEN_EDU_REQ_KEY);
            }
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
