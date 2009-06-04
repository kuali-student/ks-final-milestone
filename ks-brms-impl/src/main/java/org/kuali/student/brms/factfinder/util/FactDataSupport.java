package org.kuali.student.brms.factfinder.util;

import java.util.List;

import org.kuali.student.brms.factfinder.dto.FactTypeInfo;

public class FactDataSupport {

    private List<FactTypeInfo> factTypeInfoList;

    /**
     * @return the factTypeInfoList
     */
    public List<FactTypeInfo> getFactTypeInfoList() {
        return factTypeInfoList;
    }

    /**
     * @param factTypeInfoList the factTypeInfoList to set
     */
    public void setFactTypeInfoList(List<FactTypeInfo> factTypeInfoList) {
        this.factTypeInfoList = factTypeInfoList;
    }        
}
